package chat.bot.core.service;

import chat.bot.core.entity.Position;
import chat.bot.core.entity.UObject;
import chat.bot.core.grpc.OrderGrpc;
import chat.bot.core.utils.IoC;
import io.grpc.Channel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnBean(Channel.class)
public class OrderServiceImpl implements OrderService {

    private final Channel channel;

    public OrderServiceImpl(Channel channel) {
        this.channel = channel;
    }

    @Override
    public int createOrder() {
        OrderGrpc.OrderBlockingStub stub = OrderGrpc.newBlockingStub(channel);

        chat.bot.core.grpc.OrderService.CreateOrderRequest.Builder requestBuilder = chat.bot.core.grpc.OrderService.CreateOrderRequest.newBuilder()
                .setClientName("Test");

        UObject order = IoC.resolve("order");
        List<Position> positions = (List<Position>) order.getProperty("positions");

        positions.forEach(position -> {
            chat.bot.core.grpc.OrderService.OrderItem orderItem = chat.bot.core.grpc.OrderService.OrderItem.newBuilder()
                    .setName(position.getName())
                    .setCount(position.getCount())
                    .build();
            requestBuilder.addPositions(orderItem);
        });

        return stub.create(requestBuilder.build()).getOrderNumber();
    }

    @Override
    public void deleteOrder(int orderNumber) {
        OrderGrpc.OrderBlockingStub stub = OrderGrpc.newBlockingStub(channel);
        chat.bot.core.grpc.OrderService.DeleteOrderRequest request = chat.bot.core.grpc.OrderService.DeleteOrderRequest.newBuilder()
                .setOrderNumber(orderNumber)
                .build();

        stub.delete(request);
    }
}
