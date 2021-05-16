package sb.rf.generalchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.socket.config.annotation.*;
import sb.rf.generalchat.interceptor.HandShakeInterceptor;

import javax.persistence.criteria.CriteriaBuilder;


@EnableWebSocket
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private HandShakeInterceptor handShakeInterceptor;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // включаем брокер сообщение на урле topic, на него можно будет подписаться  и если какой-то клиент отправил на этот урл сообщение,оно дойдёт до всех подписанных на этот урл
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app"); //указываем путь, куда ещё можно отправлять сообщения. ОТЛИЧИЕ от topic: В topic сообщение сразу отправлятся всем подписанным.А здесь оно идёт на сервер, и там анализируется...
        /*config.setUserDestinationPrefix("/simpleBroker");*/ //префикс для адреса, куда клиент отправляет сообщения

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // сюда можно подключаться вебсокет-клиентам (stomp-клиент)
        registry.addEndpoint("/wsstomp").setAllowedOrigins("*").withSockJS();  // адрес, куда клиенты будут подключатся
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(handShakeInterceptor);
    }
}
