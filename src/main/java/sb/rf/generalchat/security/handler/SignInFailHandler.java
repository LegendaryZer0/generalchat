package sb.rf.generalchat.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;

@Slf4j
@Component
public class SignInFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // This is actually not an error, but an OK message. It is sent to avoid redirects.
        try {
            log.info("Given object");
            log.info(new ObjectInputStream(request.getInputStream()).readObject().toString());
        } catch (ClassNotFoundException e) {
            throw  new IOException(e);
        }
        response.sendError(HttpServletResponse.SC_OK);
    }



}
