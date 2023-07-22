package chat.bot.order.grpc;

import chat.bot.core.grpc.OrderGrpc;
import chat.bot.core.grpc.OrderService;
import chat.bot.order.handler.*;
import chat.bot.order.service.ProcessOrder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends OrderGrpc.OrderImplBase {

    private final ProcessOrder processOrder;

    public OrderServiceImpl(ProcessOrder processOrder) {
        this.processOrder = processOrder;
    }

    @Override
    public void create(OrderService.CreateOrderRequest request, StreamObserver<OrderService.CreateOrderResponse> responseObserver) {
        System.out.println(request);

        OrderHandler handlerChain = getOrderHandlerChain();
        handlerChain.process(request);

        int order = processOrder.createOrder(request.getPositionsList());

        OrderService.CreateOrderResponse response = OrderService.CreateOrderResponse.newBuilder()
                .setOrderNumber(order)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private OrderHandler getOrderHandlerChain() {
        OrderHandler checkDuplicate = new CheckDuplicateOrderHandler();
        OrderHandler checkStock = new CheckStockHandler();
        OrderHandler checkPrice = new CheckPriceHandler();

        checkDuplicate.setNextHandler(checkStock);
        checkStock.setNextHandler(checkPrice);

        return checkDuplicate;
    }

    @Override
    public void delete(OrderService.DeleteOrderRequest request, StreamObserver<OrderService.DeleteOrderResponse> responseObserver) {
        System.out.println(request);

        processOrder.deleteOrder(request.getOrderNumber());

        OrderService.DeleteOrderResponse response = OrderService.DeleteOrderResponse.newBuilder().build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
