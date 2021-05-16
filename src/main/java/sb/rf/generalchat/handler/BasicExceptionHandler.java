package sb.rf.generalchat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
@Slf4j
@ControllerAdvice
public class BasicExceptionHandler  {

    @ExceptionHandler(value
            = { IllegalArgumentException.class, IllegalStateException.class, NoSuchElementException.class})
    protected String  handleConflict(
            RuntimeException ex, HttpServletResponse response) {
        String bodyOfResponse = "This should be application specific";
        log.error("BE AWARE ILLEGAL STATE/ARGUMENT OR NO SUCH ELEMENT EXCEPTION ",ex);
        response.setStatus(HttpStatus.I_AM_A_TEAPOT.value());
        return "BasicErrorPage"/*handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request)*/;
    }





}

