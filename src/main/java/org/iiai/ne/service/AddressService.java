package org.iiai.ne.service;

import org.iiai.ne.model.Address;

import java.util.List;

public interface AddressService {
    void insertAddress(int userId, Address address);

    List<Address> getAddressByUserId(int userId);

    void delDelAddrById(int userId, int addrId);

    void updateAddress(int userId, Address address);
}
