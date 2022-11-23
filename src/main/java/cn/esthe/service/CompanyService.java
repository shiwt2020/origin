package cn.esthe.service;

import cn.esthe.entity.Company;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface CompanyService {
    //查询所有
    List<Company> findAll();

    //单一查询
    Company findOne(String id);

    //更新
    Company save(Company company);

    //添加
    Company add(Company company);

}