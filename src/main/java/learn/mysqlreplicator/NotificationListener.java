package learn.mysqlreplicator;

import com.google.code.or.binlog.BinlogEventListener;
import com.google.code.or.binlog.BinlogEventV4;
import com.google.code.or.binlog.impl.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Binlog事件监听器模板
 *
 * @author <a href="mailto:573511675@qq.com">menergy</a>
 *         DateTime: 13-12-26  下午2:34
 */
public class NotificationListener implements BinlogEventListener {
    private static Logger logger = LoggerFactory.getLogger(NotificationListener.class);
    private String eventDatabase;

    /**
     * 这里只是实现例子，该方法可以自由处理逻辑
     * @param event
     */
    @Override
    public void onEvents(BinlogEventV4 event) {
        Class<?> eventType = event.getClass();
        // 事务开始
        if (eventType == QueryEvent.class) {
            QueryEvent actualEvent = (QueryEvent) event;
            this.eventDatabase = actualEvent.getDatabaseName().toString();

            //TODO,这里可以获取事件数据库信息,可做其它逻辑处理
            logger.info("事件数据库名：{}",eventDatabase);

            return;
        }

        // 只监控指定数据库
        if (eventDatabase != null && !"".equals(eventDatabase.trim())) {
            if (eventType == TableMapEvent.class) {

                TableMapEvent actualEvent = (TableMapEvent) event;
                long tableId = actualEvent.getTableId();
                String tableName = actualEvent.getTableName().toString();

                //TODO,这里可以获取事件表信息,可做其它逻辑处理
                logger.info("事件数据表ID：{}， 事件数据库表名称：{}",tableId, tableName);

            } else if (eventType == WriteRowsEvent.class) { // 插入事件

                WriteRowsEvent actualEvent = (WriteRowsEvent) event;
                long tableId = actualEvent.getTableId();

                //TODO,这里可以获取写行事件信息,可做其它逻辑处理
                logger.info("写行事件ID：{}",tableId);

            } else if (eventType == UpdateRowsEvent.class) { // 更新事件

                UpdateRowsEvent actualEvent = (UpdateRowsEvent) event;
                long tableId = actualEvent.getTableId();

                //TODO,这里可以获取更新事件信息,可做其它逻辑处理
                logger.info("更新事件ID：{}",tableId);

            } else if (eventType == DeleteRowsEvent.class) {// 删除事件

                DeleteRowsEvent actualEvent = (DeleteRowsEvent) event;
                long tableId = actualEvent.getTableId();

                //TODO,这里可以获取删除事件信息,可做其它逻辑处理
                logger.info("删除事件ID：{}",tableId);

            } else if (eventType == XidEvent.class) {// 结束事务
                XidEvent actualEvent = (XidEvent) event;
                long xId = actualEvent.getXid();

                //TODO,这里可以获取结束事件信息,可做其它逻辑处理
                logger.info("结束事件ID：{}",xId);

            }
        }

    }
}