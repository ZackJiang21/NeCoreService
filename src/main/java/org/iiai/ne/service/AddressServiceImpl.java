package org.iiai.ne.service;

import org.iiai.ne.dao.AddressDao;
import org.iiai.ne.dao.CompanyDao;
import org.iiai.ne.exception.HttpNotFoundException;
import org.iiai.ne.exception.NotPermittedException;
import org.iiai.ne.model.Address;
import org.iiai.ne.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AddressServiceImpl implements AddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public void insertAddress(int userId, Address address) {
        validateAddrCompany(address);
        address.setUserId(userId);
        addressDao.insertDeliverAddr(address);
    }

    private void validateAddrCompany(Address address) {
        int companyId = address.getCompany().getId();
        Company company = companyDao.getCompanyById(companyId);
        if (company == null) {
            LOGGER.error("Company id {} not found", companyId);
            throw new HttpNotFoundException();
        }
    }

    @Override
    public List<Address> getAddressByUserId(int userId) {
        return addressDao.getDelAddrByUserId(userId);
    }

    @Override
    public void delDelAddrById(int userId, int addrId) {
        validateAddress(userId, addrId);
        addressDao.deleteDelAddrById(addrId);
    }

    @Override
    public void updateAddress(int userId, Address address) {
        validateAddrCompany(address);
        validateAddress(userId, address.getId());
        addressDao.updateDelAddr(address);
    }

    private void validateAddress(int userId, int addrId) {
        Set<Integer> userAddrIds = addressDao.getAddrIdsByUserId(userId);
        if (!userAddrIds.contains(addrId)) {
            LOGGER.error("userId {} trying to modify address {}", userId, addrId);
            throw new NotPermittedException();
        }
    }

}
