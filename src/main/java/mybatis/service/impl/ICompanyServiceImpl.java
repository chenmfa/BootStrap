package mybatis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bean.Company;
import mybatis.dao.CompanyDao;
import mybatis.service.ICompanyService;

@Service("companyService")
public class ICompanyServiceImpl implements ICompanyService{
  
  @Resource
  private CompanyDao companyDao;
  public List<Company> queryCompanyAll() {
    return companyDao.queryCompany();
  }

}
