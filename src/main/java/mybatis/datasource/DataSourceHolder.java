package mybatis.datasource;

/**
 * @refer https://github.com/tangyanbo/springmore
 */
import javax.sql.DataSource;

public class DataSourceHolder {
	
	private static final String Master = "master";
	
	private static final String Slave = "slave";
	
	//存放当前线程是属于master数据库还是slave数据库
	private static final ThreadLocal<String> dataSource = new ThreadLocal<String>();
	
	//主数据库连接
	private static final ThreadLocal<DataSource> masterSource = new ThreadLocal<DataSource>();
	
	//从数据库连接
	private static final ThreadLocal<DataSource> slaveSource = new ThreadLocal<DataSource>();
	
	/**
	 * @description 获取数据库的数据源
	 */
	public static String getDatasource() {
		return (String)dataSource.get();
	}
	
	/**
	 * @description 设置数据库的数据源
	 */
	public static void  setDataSource(String source){
		dataSource.set(source);
	}
	
	/**
	 * @description 标志当前数据库的源为master
	 */
	public static void setMasterSource() {
		setDataSource(Master);
	}
	
	/**
	 * @description 标志当前数据库的源为slave
	 */
	public static void setSlaveSource() {
		setDataSource(Slave);
	}

	/**
	 * @description 将master源放入对应的本地线程中
	 */
	public static void setMasterSource(DataSource master) {
		masterSource.set(master);
	}

	/**
	 * @description 将slave源放入对应的本地线程中
	 */
	public static void setSlaveSource(DataSource slave) {
		slaveSource.set(slave);
	}
	
	public static boolean isMaster(){
		return (String)dataSource.get() == Master;
	}
	
	public static boolean isSlave(){
		return (String)dataSource.get() == Slave;
	}
	
	/**
	 * @description 清空本地线程中的数据源数据
	 */
	public static void clearDataSource(){
		dataSource.remove();
		masterSource.remove();
		slaveSource.remove();
	}
}
