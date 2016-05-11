package mybatis.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import bean.Company;
import mybatis.base.MyBatisSqlSessions;
import mybatis.dao.CompanyDao;

public class CompanyDaoImpl implements CompanyDao{
  private MyBatisSqlSessions session = MyBatisSqlSessions.getMybatisSqlSession();
  
  @Override
  public List<Company> queryCompany() {
    SqlSession sqlSession = session.getSqlSession();
    List<Company> companyList = sqlSession.getMapper(CompanyDao.class).queryCompany();
    sqlSession.close();
    return companyList;
  }
  @Override
  public int addCompany(Company company) {
    SqlSession sqlSession = session.getSqlSession();    
    sqlSession.getMapper(CompanyDao.class).addCompany(company);
    System.out.println(company.getCompanyId());
    return company.getCompanyId();
  }

}
