package cn.esthe.controller;

import cn.esthe.entity.Company;
import cn.esthe.service.CompanyService;
import cn.esthe.service.NormalCompanyService;
import cn.esthe.service.TransactionService;
import cn.esthe.cache.CompanyCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api("Swagger示例CRUD操作")
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    @Qualifier("companyServiceQualifier")
    private CompanyService companyServiceQualifier;

    @Resource(name = "companyServiceResource")
    private CompanyService companyServiceResource;

    @Autowired
    private CompanyCache companyCache;

    @Autowired(required = false)
    private CacheManager cacheManager;

    @Autowired
    private TransactionService testTransaction;

    @Autowired
    private NormalCompanyService normalCompanyService;

    //查询所有
    @ApiOperation("获取公司列表")
    @GetMapping("/findAll")
    public List<Company> findAll(){
        return normalCompanyService.findAll();
    }


    //从缓存中获取数据
    @GetMapping("/cache/{id}")
    public Company getCache(@PathVariable String id){
        Cache cacheManager = this.cacheManager.getCache("companyCache");
        return companyCache.getCompanyCache(id);
    }
}