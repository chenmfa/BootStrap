package mybatis.datasource;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;

public class DynamicDataSource extends AbstractRoutingDataSource{
	
	private DataSource master; //主库的数据源
	
	private List<DataSource> slaves; //从库的数据源
	
	private AtomicInteger counter = new AtomicInteger();
	
	//log4j
	//private final Logger log = LoggerFactory.getLogger(DynamicDataSource.getClass());
	@Override
  protected DataSource determineTargetDataSource() {
		Assert.notNull(master);
		Assert.notNull(slaves);
		DataSource dataSourceOutcome = null;
		
		if(DataSourceHolder.isMaster()){
			dataSourceOutcome = master;
		}else if(DataSourceHolder.isSlave()){
			int connectCount = counter.incrementAndGet();
			if(connectCount >100000){
				counter.set(0);
				
			}
			int n = (null == slaves)?0:slaves.size();
			if(n == 0){
				dataSourceOutcome = master;
			}
			dataSourceOutcome = slaves.get(connectCount%n);
		}else{
			dataSourceOutcome = master;
		}
	  return dataSourceOutcome;
  }

	public DataSource getMaster(){
		return master;
	}

	public void setMaster(DataSource master) {
		this.master = master;
	}

	public List<DataSource> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<DataSource> slaves) {
		this.slaves = slaves;
	}

	public AtomicInteger getCounter() {
		return counter;
	}

	public void setCounter(AtomicInteger counter) {
		this.counter = counter;
	}

  
	@Override
	protected Object determineCurrentLookupKey() {
		//do nothing
		return null;
	}
	
	@Override
	public void afterPropertiesSet(){
		//do nothing
	}
}
