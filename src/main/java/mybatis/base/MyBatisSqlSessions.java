package mybatis.base;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import bean.Company;
import mybatis.dao.CompanyDao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisSqlSessions{
  private static MyBatisSqlSessions session = new MyBatisSqlSessions();
  private SqlSessionFactory factory;
  private MyBatisSqlSessions(){
    try {
      Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
      factory = new SqlSessionFactoryBuilder().build(reader);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public static MyBatisSqlSessions getMybatisSqlSession(){
    return session;
  }
  
  public SqlSession getSqlSession(){
    return factory.openSession();
  }
  
  public boolean closeSqlSession(SqlSession sqlSession){
    if(null != sqlSession){
      sqlSession.close();
    }
    return true;
  }
  
  public static void main(String[] args){
    System.out.println(1);
    
    MyBatisSqlSessions session = new MyBatisSqlSessions();
    SqlSession sqlSession = session.getSqlSession();    
    Company company=new Company();
    company.setCompanyName("测试公司陈苗发22sss");
    int addCompany = sqlSession.getMapper(CompanyDao.class).addCompany(company);
    System.out.println("Connection build");
    List<Company> companyList = sqlSession.getMapper(CompanyDao.class).queryCompany();
    for(Company com:companyList){
      System.out.println(com);
    }
    //sqlSession.commit();
    sqlSession.close();
  }
}
