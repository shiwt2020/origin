package cn.esthe.service.impl;

import cn.esthe.dao.CompanyDao;
import cn.esthe.entity.Company;
import cn.esthe.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("companyServiceResource")
public class CompanyServiceResource implements CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public Company findOne(String id) {
        return companyDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Company add(Company company) {
        return companyDao.save(company);
    }




    @Override
    public Company save(Company company) {
      return  companyDao.save(company);
    }

}