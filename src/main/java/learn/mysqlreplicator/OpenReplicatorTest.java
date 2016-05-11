package learn.mysqlreplicator;

/**
 * MySQL binlog分析程序测试
 *
 * @author <a href="mailto:573511675@qq.com">menergy</a>
 *         DateTime: 13-12-26  下午2:26
 */
public class OpenReplicatorTest {
    public static void main(String args[]){
        // 配置从MySQL Master进行复制
        final AutoOpenReplicator aor = new AutoOpenReplicator();
        aor.setServerId(100001);
        aor.setHost("192.168.1.1");
        aor.setUser("admin");
        aor.setPassword("123456");
        aor.setAutoReconnect(true);
        aor.setDelayReconnect(5);
        aor.setBinlogEventListener(new NotificationListener());
        aor.start();
    }
}
