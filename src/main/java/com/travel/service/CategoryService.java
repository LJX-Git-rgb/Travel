package com.travel.service;

import com.travel.domain.Category;
import com.travel.domain.Route;

import java.util.List;

public interface CategoryService {
    List<Category> findAllType ();
    List<Route> findByCid();
}
