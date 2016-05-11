package mybatis.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SuppressWarnings({"rawtypes"})
public class DynamicSqlSessionTemplate implements SqlSession{
	
	private static final String INSERT = "insert";
	
	private static final String DELETE = "delete";
	
	private static final String UPDATE = "update";
	
	private static final String ALTER = "alter";
	
	//private static final String SELECT = "select";
	
	//private static final String QUERY = "query";

	private SqlSessionTemplate sqlSessionTemplate;
	
	private final SqlSession sqlSessionProxy;
	
	public DynamicSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
		this.sqlSessionTemplate =  sqlSessionTemplate;
		this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
				SqlSessionFactory.class.getClassLoader(),
				new Class[] { SqlSession.class }, new SqlSessionInterceptor());
	}
	
	
	/**
	 * @description 通过动态代理拦截sqlSessionTemplate的方法
	 * 根据方法名判断，如果是spring下已经开启的事务，则不处理，交由spring的
	 * AbstractRoutingDataSource来处理
	 */
	private class SqlSessionInterceptor implements InvocationHandler{

		@Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable {
			try {
	      boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
	      if(synchronizationActive){
	      	//如果spring事务开启
	      	return method.invoke(sqlSessionTemplate, args);
	      }else{
	      	String methodName = method.getName();
	      	//以增删改开头的方法名默认走主数据库
	      	if(methodName.startsWith(INSERT) || methodName.startsWith(DELETE) 
	      			||methodName.startsWith(UPDATE) || methodName.startsWith(ALTER)){
	      		DataSourceHolder.setMasterSource();
	      	}else{
	      		DataSourceHolder.setSlaveSource();
	      	}
	      	
	      	Object result = method.invoke(sqlSessionTemplate, args);
	      	DataSourceHolder.clearDataSource();
	      	return result; 
	      }
      } catch (Exception e){
      	throw e;
      }
    }	
	}
	
	public <T> T selectOne(String statement) {
		return sqlSessionProxy.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return sqlSessionProxy.selectOne(statement, parameter);
	}

	public <E> List<E> selectList(String statement) {
		return sqlSessionProxy.selectList(statement);
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		return sqlSessionProxy.selectList(statement, parameter);
	}

	public <E> List<E> selectList(String statement, Object parameter,
			RowBounds rowBounds) {
		return sqlSessionProxy.selectList(statement, parameter, rowBounds);
	}

	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return sqlSessionProxy.selectMap(statement, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter,
			String mapKey) {
		return sqlSessionProxy.selectMap(statement, parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter,
			String mapKey, RowBounds rowBounds) {
		return sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		sqlSessionProxy.select(statement, parameter, handler);		
	}

	public void select(String statement, ResultHandler handler) {
		sqlSessionProxy.select(statement, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds,
			ResultHandler handler) {
		sqlSessionProxy.select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
		return sqlSessionProxy.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return sqlSessionProxy.insert(statement, parameter);
	}

	public int update(String statement) {
		return sqlSessionProxy.update(statement);
	}

	public int update(String statement, Object parameter) {
		return sqlSessionProxy.update(statement, parameter);
	}

	public int delete(String statement) {
		return sqlSessionProxy.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		return sqlSessionProxy.delete(statement, parameter);
	}

	public void commit() {
		sqlSessionProxy.commit();
	}

	public void commit(boolean force) {
		sqlSessionProxy.commit(force);
	}

	public void rollback() {
		sqlSessionProxy.rollback();
	}

	public void rollback(boolean force) {
		sqlSessionProxy.rollback(force);
	}

	public List<BatchResult> flushStatements() {
		return sqlSessionProxy.flushStatements();
	}

	public void close() {
		sqlSessionProxy.close();
	}

	public void clearCache() {
		sqlSessionProxy.clearCache();
	}

	public Configuration getConfiguration() {
		return sqlSessionProxy.getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		return sqlSessionProxy.getMapper(type);
	}

	public Connection getConnection() {
		return sqlSessionProxy.getConnection();
	}
}
