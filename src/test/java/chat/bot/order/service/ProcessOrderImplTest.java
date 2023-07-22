package chat.bot.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProcessOrderImplTest {

    private ProcessOrderImpl processOrder;

    @BeforeEach
    void before() {
        processOrder = new ProcessOrderImpl();
    }

    @Test
    void createOrder() {
        int order = processOrder.createOrder(Collections.emptyList());
        assertEquals(1, order);
        assertNotNull(processOrder.getOrder(order));
    }

    @Test
    void deleteOrder() {
        processOrder.createOrder(Collections.emptyList());
        processOrder.deleteOrder(1);
        assertNull(processOrder.getOrder(1));
    }

    @Test
    void getOrder() {
        int order = processOrder.createOrder(Collections.emptyList());
        assertNotNull(processOrder.getOrder(order));
    }
}