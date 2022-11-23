package service;

import com.sise.microservice.orders.OrdersApplication;
import com.sise.microservice.orders.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Random;

@SpringBootTest(classes = OrdersApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void queryOrderTest() {
        System.out.println(orderService.queryOrder(1L));
    }

    @Test
    public void addOrderTest() {
        orderService.addOrder(new Random().nextInt(1000000), 100,
                new BigDecimal(new Random().nextDouble() * new Random().nextInt(1000)), 2);
    }

}