package chat.bot.order.service;

import chat.bot.core.entity.UObject;
import chat.bot.core.grpc.OrderService;

import java.util.List;

public interface ProcessOrder {
    int createOrder(List<OrderService.OrderItem> positionsList);
    void deleteOrder(int i);
    UObject getOrder(int i);
}
