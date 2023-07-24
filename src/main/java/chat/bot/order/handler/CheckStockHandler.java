package chat.bot.order.handler;

import chat.bot.core.grpc.OrderService;
import chat.bot.order.exception.CheckOrderException;

public class CheckStockHandler implements OrderHandler {

    private OrderHandler nextHandler;

    @Override
    public void process(OrderService.CreateOrderRequest request) {
        System.out.println("Checking stock...");

        if (false) {
            throw new CheckOrderException();
        }

        //checking

        System.out.println("Check stock is done");

        if (nextHandler != null) {
            nextHandler.process(request);
        }
    }

    @Override
    public void setNextHandler(OrderHandler handler) {
        nextHandler = handler;
    }
}
