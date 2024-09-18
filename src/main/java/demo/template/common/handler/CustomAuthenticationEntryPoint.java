//package demo.template.common.handler;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//@Slf4j
//@Component
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    private final HandlerExceptionResolver handlerExceptionResolver;
//
//    public CustomAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
//        this.handlerExceptionResolver = handlerExceptionResolver;
//    }
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
////        handlerExceptionResolver.resolveException(request, response, null, ApplicationErrorException.of(ResultCode.Error.AUTHENTICATION_IS_FAIL));
//    }
//
//}
