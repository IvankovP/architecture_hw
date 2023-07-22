package chat.bot.order.grpc;

import chat.bot.order.service.ProcessOrder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcConfig {

    private final ProcessOrder processOrder;

    public GrpcConfig(ProcessOrder processOrder) {
        this.processOrder = processOrder;
    }

    @Bean
    public void serverGrpc() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9090).addService(new OrderServiceImpl(processOrder)).build();
        server.start();
        System.out.println("----- gRPC Server started -----");
        server.awaitTermination();
    }
}
