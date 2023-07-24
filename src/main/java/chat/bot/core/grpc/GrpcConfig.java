package chat.bot.core.grpc;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {

    @Bean
    public Channel channel() {
        return ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
    }
}
