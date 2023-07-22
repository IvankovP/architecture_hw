package chat.bot.order.handler;

import chat.bot.core.grpc.OrderService;

public interface OrderHandler {
    void process(OrderService.CreateOrderRequest request);
    void setNextHandler(OrderHandler handler);
}
