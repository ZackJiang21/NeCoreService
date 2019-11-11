package org.iiai.ne.service;

import org.iiai.ne.dao.CompanyDao;
import org.iiai.ne.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAllCompanies();
    }
}
