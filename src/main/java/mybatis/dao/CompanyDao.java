package mybatis.dao;

import java.util.List;

import bean.Company;

public interface CompanyDao {
  public List<Company> queryCompany();
  public int addCompany(Company company);
}
