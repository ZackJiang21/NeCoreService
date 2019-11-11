package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.StoreActivity;

import java.util.List;

@Mapper
public interface ActivityDao {

    List<StoreActivity> getActivitiesByStoreId(@Param("storeId") int storeId);
}
