package sb.rf.generalchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import sb.rf.generalchat.service.IMessageService;
import sb.rf.generalchat.service.IUserService;
import sb.rf.generalchat.webSocket.ChatServerEndPoint;

@Configuration
@EnableWebSocket
public class EndpointConfig
{
    @Autowired
    IMessageService messageService;

    @Autowired
    IUserService userService;
    @Bean
    @Lazy
    public ChatServerEndPoint chatEndpointNew(){
        return new ChatServerEndPoint(userService,messageService);
    }


    @Bean
  /*  @Lazy*/
    public ServerEndpointExporter endpointExporter(){
        ServerEndpointExporter endpointExporter = new ServerEndpointExporter();
        endpointExporter.setAnnotatedEndpointClasses(ChatServerEndPoint.class);
        return new ServerEndpointExporter();
    }

}