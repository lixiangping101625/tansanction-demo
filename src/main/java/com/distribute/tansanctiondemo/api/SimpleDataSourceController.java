package com.distribute.tansanctiondemo.api;

import com.distribute.tansanctiondemo.dao.db2.OrderMapper;
import com.distribute.tansanctiondemo.dao.db1.ProductMapper;
import com.distribute.tansanctiondemo.pojo.Order;
import com.distribute.tansanctiondemo.pojo.Product;
import com.distribute.tansanctiondemo.pojo.ProductExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lixiangping
 * @createTime 2022年03月31日 23:53
 * @decription: 单一数据源测试
 */
@Slf4j
@RestController
public class SimpleDataSourceController {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderMapper orderMapper;


    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    TransactionDefinition transactionDefinition;

    /**
     * 测试hikari跨连接事务
     * @return
     */
    @GetMapping("/add")
//    @Transactional(rollbackFor = Exception.class)
    public String add(){
        // 手动开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);

        Product product = new Product();
        product.setProductName("测试商品");
        product.setStock(100);
        int i = productMapper.insert(product);
        log.info("扣减库存：" + product.toString());


        int num = 10/0;

        Order order = new Order();
        order.setPrice(BigDecimal.valueOf(188l));
        order.setTitle("测试订单");
        int j = orderMapper.insert(order);
        log.info("保存订单：" + order.toString());

        // 提交事务
        dataSourceTransactionManager.commit(transactionStatus);
        return (i>0 && j>0) ? "添加商品成功" : "添加商品失败";
    }

    @GetMapping("/list")
    public List<Product> list(){
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductNameEqualTo("测试");
        List<Product> list = productMapper.selectByExample(productExample);
        return list;
    }

}
