package learn.mysqlreplicator;

import com.google.code.or.OpenReplicator;
import com.google.code.or.common.glossary.column.StringColumn;
import com.google.code.or.net.Packet;
import com.google.code.or.net.Transport;
import com.google.code.or.net.impl.packet.EOFPacket;
import com.google.code.or.net.impl.packet.ResultSetRowPacket;
import com.google.code.or.net.impl.packet.command.ComQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MySQL binlog分析程序 ,用到open-replicator包
 * 增加加自动配置binlog位置及重连机制
 *
 * @author <a href="mailto:573511675@qq.com">menergy</a>
 *         DateTime: 13-12-26  下午2:22
 */
public class AutoOpenReplicator extends OpenReplicator {
    // members
    private static Logger logger = LoggerFactory.getLogger(AutoOpenReplicator.class);

    private boolean autoReconnect = true;
    // timeout auto reconnect , default 30 second
    private int delayReconnect = 30;
    // default timeout is 60 second, after timeout will be reconnect!
    private int defaultTimeout = 120 * 1000;
    // COM Query Transport
    private Transport comQueryTransport;
    // static block

    // constructors

    // properties

    /**
     * 是否自动重连
     *
     * @return 自动重连
     */
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    /**
     * 设置自动重连
     *
     * @param autoReconnect 自动重连
     */
    public void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    /**
     * 断开多少秒后进行自动重连
     *
     * @param delayReconnect 断开后多少秒
     */
    public void setDelayReconnect(int delayReconnect) {
        this.delayReconnect = delayReconnect;
    }

    /**
     * 断开多少秒后进行自动重连
     *
     * @return 断开后多少秒
     */
    public int getDelayReconnect() {
        return delayReconnect;
    }

    // public methods

    // protected methods

    @Override
    public void start() {
      this.getClass().getSimpleName();
        do {
            try {
                long current = System.currentTimeMillis();
                if (!this.isRunning()) {
                    if (this.getBinlogFileName() == null) updatePosition();
                    logger.info("Try to startup dump binlog from mysql master[{}, {}] ...", this.binlogFileName, this.binlogPosition);
                    this.reset();
                    super.start();
                    logger.info("Startup successed! After {} second if nothing event fire will be reconnect ...", defaultTimeout / 1000);
                } else {
                    //这里的lastAlive没有了,下次研究
                    /*if (current - this.lastAlive >= this.defaultTimeout) {
                        this.stopQuietly(0, TimeUnit.SECONDS);
                    }*/
                }
                TimeUnit.SECONDS.sleep(this.getDelayReconnect());
            } catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("connect mysql failure!", e);
                }
                // reconnect failure, reget last binlog & position from master node and update cache!
                //LoadCenter.loadAll(); // just update all cache, not flush!
                updatePosition();
                try {
                    TimeUnit.SECONDS.sleep(this.getDelayReconnect());
                } catch (InterruptedException ignore) {
                    // NOP
                }
            }
        } while (this.autoReconnect);
    }

    @Override
    public void stopQuietly(long timeout, TimeUnit unit) {
        super.stopQuietly(timeout, unit);
        if (this.getBinlogParser() != null) {
            // 重置, 当MySQL服务器进行restart/stop操作时进入该流程
            this.binlogParser.setParserListeners(null); // 这句比较关键，不然会死循环
        }
    }

    // friendly methods

    // private methods

    /**
     * 自动配置binlog位置
     */
    private void updatePosition() {
        // 配置binlog位置
        try {
            ResultSetRowPacket binlogPacket = query("show master status");
            if (binlogPacket != null) {
                List<StringColumn> values = binlogPacket.getColumns();
                this.setBinlogFileName(values.get(0).toString());
                this.setBinlogPosition(Long.valueOf(values.get(1).toString()));
            }
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("update binlog position failure!", e);
            }
        }
    }

    /**
     * ComQuery 查询
     *
     * @param sql 查询语句
     * @return
     */
    private ResultSetRowPacket query(String sql) throws Exception {
        ResultSetRowPacket row = null;
        final ComQuery command = new ComQuery();
        command.setSql(StringColumn.valueOf(sql.getBytes()));
        if (this.comQueryTransport == null) this.comQueryTransport = getDefaultTransport();
        this.comQueryTransport.connect(this.host, this.port);
        this.comQueryTransport.getOutputStream().writePacket(command);
        this.comQueryTransport.getOutputStream().flush();
        // step 1
        this.comQueryTransport.getInputStream().readPacket();
        //
        Packet packet;
        // step 2
        while (true) {
            packet = comQueryTransport.getInputStream().readPacket();
            if (packet.getPacketBody()[0] == EOFPacket.PACKET_MARKER) {
                break;
            }
        }
        // step 3
        while (true) {
            packet = comQueryTransport.getInputStream().readPacket();
            if (packet.getPacketBody()[0] == EOFPacket.PACKET_MARKER) {
                break;
            } else {
                row = ResultSetRowPacket.valueOf(packet);
            }
        }
        this.comQueryTransport.disconnect();
        return row;
    }

    private void reset() {
        this.transport = null;
        this.binlogParser = null;
    }
    // inner class

    // test main
}