package cn.esthe.service;

import cn.esthe.dao.CompanyDao;
import cn.esthe.entity.Company;
import cn.esthe.cache.CompanyCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyCache companyCache;

//    @Transactional
    public String update(String id, String name) {
        //调用同一个类下的方法，方法上不加注解会导致事务失效
        //解决方案  可以这个方法上加Transactional注解
        //String result = this.test(id, name + "test");

        //调用同一个类下的方法，方法上不加注解会导致事务失效
        //解决方案  可以这个方法上加Transactional注解
        String result = companyCache.trans(id, name + "test");

        return result;
    }

    @Transactional
    public String test(String id, String name) {
        Company company = companyDao.findById(id).orElse(null);
        company.setName(name);
        companyDao.save(company);
        int i = 1 / 0;
        return company.getName();
    }

}
