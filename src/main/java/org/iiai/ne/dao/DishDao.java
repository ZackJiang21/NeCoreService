package org.iiai.ne.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iiai.ne.model.Dish;
import org.iiai.ne.model.DishCategory;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DishDao {
    List<DishCategory> getDishesWithCategory();

    List<Dish> getDishByIds(@Param("dishIds") Collection<Integer> dishIds);
}
