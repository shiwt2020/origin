package cn.esthe.service.impl;

import cn.esthe.dao.CompanyDao;
import cn.esthe.entity.Company;
import cn.esthe.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("companyServiceQualifier")
public class CompanyServiceQualifier implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAll() {
        List<Company> all = companyDao.findAll();
        List<Company> list=new ArrayList<Company>();
        list.add(all.get(0));
        return list;
    }

    @Override
    public Company findOne(String id) {
        return companyDao.findById(id).orElse(null);
    }

    @Override
    public Company save(Company company) {
        return null;
    }

    @Override
    public Company add(Company company) {
        return null;
    }

}
