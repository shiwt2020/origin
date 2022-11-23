package cn.esthe.service;

import cn.esthe.annotation.ListCache;
import cn.esthe.dao.CompanyDao;
import cn.esthe.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NormalCompanyService {
    @Autowired
    private CompanyDao companyDao;

    @ListCache
    public List<Company> findAll() {
        return companyDao.findAll();
    }

}
