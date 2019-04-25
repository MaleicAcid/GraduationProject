package com.sqlist.web.service;

import com.sqlist.web.domain.Product;
import com.sqlist.web.domain.User;
import com.sqlist.web.vo.PageVO;
import com.sqlist.web.vo.ProductVO;

import java.util.List;
import java.util.Map;

/**
 * @author SqList
 * @date 2019/4/25 18:14
 * @description
 **/
public interface ProductService {

    /**
     * 获取产品
     * @param user
     * @param pageVO
     * @return
     */
    Map<String, Object> list(User user, PageVO pageVO);

    /**
     * 获取产品详细信息
     * @param pid
     * @return
     */
    Product detail(Integer pid);

    /**
     * 添加产品
     * @param user
     * @param productVO
     */
    void add(User user, ProductVO productVO);
}
