package com.travel.dao.impl;

import com.travel.dao.CategoryDao;
import com.travel.domain.Category;
import com.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryDaoImpl
 * @description:
 * @author: LJX
 * @time: 2020/3/23 18:43
 * @version: 1.0
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAllType() {
        String sql = "select * from tab_category order by cid ";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return list;
    }
}
