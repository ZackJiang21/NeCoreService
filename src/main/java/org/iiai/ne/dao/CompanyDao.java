package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.Company;

import java.util.List;

@Mapper
public interface CompanyDao {
    List<Company> getAllCompanies();

    Company getCompanyById(@Param("id") int id);
}
