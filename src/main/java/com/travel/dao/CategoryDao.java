package com.travel.dao;

import com.travel.domain.Category;

import java.util.List;

/**
 * @ClassName CategoryDao
 * @description:
 * @author: LJX
 * @time: 2020/3/23 18:42
 * @version: 1.0
 */
public interface CategoryDao {
    List<Category> findAllType();
}
