package cn.esthe.dao;

import cn.esthe.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<Company, String> {

    @Query("update Company e set e.name = ?2 where e.id in ?1")
    @Modifying
    int update(String id,String name);

}