package com.distribute.tansanctiondemo;

import com.distribute.tansanctiondemo.dao.db1.ProductMapper;
import com.distribute.tansanctiondemo.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.*;
@Slf4j
@SpringBootTest
class TansanctionDemoApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    private CountDownLatch cdl = new CountDownLatch(35);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(35);

    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() throws InterruptedException, SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
        ExecutorService es = Executors.newFixedThreadPool(35);
        for (int i = 0; i < 35; i++) {
            es.execute(() -> {
                try {
                    Product product = new Product();
                    product.setProductName("测试商品");
                    product.setStock(100);
                    productMapper.insert(product);
                    cyclicBarrier.await();//等待，在某一时刻同时执行下面创建订单
                    Thread.currentThread().setName("业务线程");
                    log.info("开始写入");
                    productMapper.insert(product);//五个线程查到商品库存均为1，所以会创建5个订单！！！
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();//防止线程池关闭
        es.shutdown();
    }

}
