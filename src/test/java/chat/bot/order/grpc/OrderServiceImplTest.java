package chat.bot.order.grpc;

import chat.bot.core.grpc.OrderService;
import chat.bot.order.service.ProcessOrderImpl;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    private ProcessOrderImpl processOrder;
    private OrderServiceImpl orderService;

    @BeforeEach
    void before() {
        processOrder = new ProcessOrderImpl();
        orderService = new OrderServiceImpl(processOrder);
    }

    @Test
    void create() {
        createOrder();
        assertNotNull(processOrder.getOrder(1));
    }

    private void createOrder() {
        OrderService.CreateOrderRequest request = OrderService.CreateOrderRequest.newBuilder().build();
        Stub<OrderService.CreateOrderResponse> stub = new Stub<>();
        orderService.create(request, stub);
    }

    @Test
    void delete() {
        createOrder();

        OrderService.DeleteOrderRequest request = OrderService.DeleteOrderRequest.newBuilder().setOrderNumber(1).build();
        Stub<OrderService.DeleteOrderResponse> stub = new Stub<>();
        orderService.delete(request, stub);
        assertNull(processOrder.getOrder(1));
    }
}

class Stub<T> implements StreamObserver<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {

    }
}