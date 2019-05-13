package com.sqlist.web.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sqlist.web.domain.Device;
import com.sqlist.web.domain.Product;
import com.sqlist.web.domain.User;
import com.sqlist.web.mapper.ProductMapper;
import com.sqlist.web.service.DeviceService;
import com.sqlist.web.service.ProductService;
import com.sqlist.web.util.UUIDUtil;
import com.sqlist.web.vo.PageVO;
import com.sqlist.web.vo.ProductVO;
import com.sqlist.web.vo.search.ProductSearchVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SqList
 * @date 2019/4/25 18:16
 * @description
 **/
@Service
public class ProductServiceImpl implements ProductService {

    public static final String SPLIT = "-";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DeviceService deviceService;

    @Override
    public Map<String, Object> list(User user, ProductSearchVO productSearchVO) {
        Product product = new Product();
        product.setUid(user.getUid());
        product.setName(productSearchVO.getName());
        product.setProductKey(productSearchVO.getProductKey());

        HashMap<String, Object> map = new HashMap<>();

        if (productSearchVO.getLimit() != -1) {
            PageHelper.startPage(productSearchVO.getPage(), productSearchVO.getLimit());
        }
        List<Product> productList = productMapper.select(product);

        if (productSearchVO.getLimit() != -1) {
            map.put("total", ((Page)productList).getTotal());
        }
        map.put("list", productList);

        return map;
    }

    @Override
    public Product detail(Product product) {
        product = productMapper.selectOne(product);

        return product;
    }

    @Override
    public void add(User user, ProductVO productVO) {
        Product product = new Product();

        do {
            product.setTopic(user.getUsername() + SPLIT + RandomStringUtils.randomAlphabetic(8));
        } while (productMapper.selectCount(product) != 0);

        product.setUid(user.getUid());
        product.setName(productVO.getName());
        product.setProductKey(UUIDUtil.uuid());
        product.setCreateTime(new Date());

        productMapper.insert(product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteMultiple(User user, List<Integer> pidList) {
        productMapper.deleteMultiple(user, pidList);

        // 原本属于此产品的设备直接删除
        Device device = new Device();
        device.setUid(user.getUid());
        deviceService.deleteMultipleByPid(device, pidList);
    }

    @Override
    public Product get(Integer pid) {
        Product product = new Product();
        product.setPid(pid);
        return productMapper.selectByPrimaryKey(product);
    }

    @Override
    public Integer count(User user) {
        Product product = new Product();
        product.setUid(user.getUid());
        return productMapper.selectCount(product);
    }
}
