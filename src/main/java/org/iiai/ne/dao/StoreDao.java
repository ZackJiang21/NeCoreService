package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.Store;
import org.iiai.ne.model.StoreDetail;

import java.util.List;

@Mapper
public interface StoreDao {
    StoreDetail getStoreInfoById(@Param("storeId") int storeId);

    List<StoreDetail> getStoresInfo(@Param("start") int start, @Param("limit") int limit);

    void updateStoreStatus(@Param("stores") List<Store> stores);
}
