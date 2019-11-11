package org.iiai.ne.service;

import org.iiai.ne.dao.DishDao;
import org.iiai.ne.model.DishCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishServiceImpl.class);

    @Autowired
    private DishDao dishDao;

    @Override
    public List<DishCategory> getDishesWithCategory() {
        return dishDao.getDishesWithCategory();
    }
}
