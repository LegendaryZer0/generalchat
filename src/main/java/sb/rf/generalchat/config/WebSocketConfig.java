package sb.rf.generalchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.*;
import sb.rf.generalchat.interceptor.HandShakeInterceptor;


@EnableWebSocket
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private HandShakeInterceptor handShakeInterceptor;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // сюда можно подключаться вебсокет-клиентам (stomp-клиент)
        registry.addEndpoint("/chats").setAllowedOrigins("*").withSockJS();
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(handShakeInterceptor);
    }
}
