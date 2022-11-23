package cn.esthe.cache;

import cn.esthe.dao.CompanyDao;
import cn.esthe.entity.Company;
import cn.esthe.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CompanyCache {

    Logger logger = LoggerFactory.getLogger(CompanyCache.class);

    @Autowired
    @Qualifier("companyServiceQualifier")
    private CompanyService companyService;

    @Autowired
    private CompanyDao companyDao;


    @Autowired(required = false)
    private CacheManager cacheManager;


    @Cacheable(value = "companyCache", key = "#id")
    public Company getCompanyCache(String id) {
        logger.info("id is : " + id);
        return companyService.findOne(id);
    }

    public Company cacheUpdate(Company company) {
        Cache cache = this.cacheManager.getCache("companyCache");
        cache.put(company.getId(),company);
        return companyDao.save(company);
    }

    @Transactional
    public String trans(String id, String name) {
        Company company = companyDao.findById(id).orElse(null);
        company.setName(name);
        companyDao.save(company);
        int i = 1 / 0;
        return company.getName();
    }

}
