package chat.bot.order.service;

import chat.bot.core.entity.UObject;
import chat.bot.core.entity.UObjectImpl;
import chat.bot.core.grpc.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProcessOrderImpl implements ProcessOrder {

    private final AtomicInteger countOrder = new AtomicInteger();
    private final Map<Integer, UObjectImpl> orders = new ConcurrentHashMap<>();

    @Override
    public int createOrder(List<OrderService.OrderItem> positionsList) {
        int number = countOrder.incrementAndGet();
        UObjectImpl order = new UObjectImpl();
        order.setProperty("number", number);
        order.setProperty("positions", positionsList);
        orders.put(number, order);

        System.out.println("Order was created " + number);
        return number;
    }

    @Override
    public void deleteOrder(int i) {
        orders.remove(i);
        System.out.println("Order was deleted " + i);
    }

    @Override
    public UObject getOrder(int i) {
        return orders.get(i);
    }
}
