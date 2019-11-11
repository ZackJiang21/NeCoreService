package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.aop.Privilege;
import org.iiai.ne.model.Address;

import java.util.List;
import java.util.Set;

@Mapper
public interface AddressDao {
    void insertDeliverAddr(@Param("address") Address address);

    List<Address> getDelAddrByUserId(@Param("userId") int userId);

    void deleteDelAddrById(@Param("addrId") int addrId);

    Set<Integer> getAddrIdsByUserId(@Param("userId") int userId);

    void updateDelAddr(@Param("address") Address address);

    Address getAddressById(@Param("addrId") int addrId, @Param("userId") int userId);
}
