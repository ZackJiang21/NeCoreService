package org.iiai.ne.controller;

import org.iiai.ne.aop.Privilege;
import org.iiai.ne.model.Company;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    @Privilege
    public Response getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return new Response().success(companies);
    }
}
