package mybatis.datasource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager{

  private static final long serialVersionUID = 1L;
  
  /**
   * @description 根据事务管理器里是否是只读来动态推送到主或者从服务器
   */
  @Override
  protected void doBegin(Object transaction, TransactionDefinition definition){
  	boolean readOnly = definition.isReadOnly();
  	if(readOnly){
  		DataSourceHolder.setSlaveSource();
  	}else{
  		DataSourceHolder.setMasterSource();
  	}
  	super.doBegin(transaction, definition);
  }
  
  /**
   * @description 事务完成后清理掉本地线程的数据
   */
  @Override
  protected void doCleanupAfterCompletion(Object transaction){
  	super.doCleanupAfterCompletion(transaction);
  	DataSourceHolder.clearDataSource();
  }

}
