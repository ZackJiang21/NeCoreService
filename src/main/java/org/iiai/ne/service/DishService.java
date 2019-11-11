package org.iiai.ne.service;

import org.iiai.ne.model.DishCategory;

import java.util.List;

public interface DishService {
    List<DishCategory> getDishesWithCategory();
}
