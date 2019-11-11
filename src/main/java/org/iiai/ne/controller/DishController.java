package org.iiai.ne.controller;

import org.iiai.ne.model.DishCategory;
import org.iiai.ne.model.Response;
import org.iiai.ne.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dish")
public class DishController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DishController.class);

    @Autowired
    private DishService dishService;

    @RequestMapping(method = RequestMethod.GET)
    public Response getDishesWithCategory() {
        List<DishCategory> dishCategories = dishService.getDishesWithCategory();
        return new Response().success(dishCategories);
    }
}
