package demo.template.common.handler;

import demo.template.common.enums.ResultCode;
import demo.template.common.model.BaseResponseFactory;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(METHOD_NOT_ALLOWED)
                .body(BaseResponseFactory.create(ResultCode.Error.METHOD_NOT_ALLOWED));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(BaseResponseFactory.create(ResultCode.Error.DATA_NOT_FOUND));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(BaseResponseFactory.create(ResultCode.Error.INVALID_JSON_FORMAT));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(BaseResponseFactory.create(ResultCode.Error.UNKNOWN));
    }

    // todo 인증 실패, 유효기간 만료 상세 구분
    @ExceptionHandler(ServletException.class)
    private ResponseEntity<Object> handleServletException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(BaseResponseFactory.create(ResultCode.Error.INVALID_CHANNEL_KEY));
    }

    //    @Override
//    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String decodedString = URLDecoder.decode(e.getResourcePath(), StandardCharsets.UTF_8);
//        log.error("Exception Occurred. -> [올바르지 않은 경로 요청(NoResourceFoundException)] message : {}, path : {}", e.getMessage(), decodedString, e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(
//                ResultCode.Error.NOT_FOUND,
//                ErrorMessage.combine(ErrorMessage.PATH, decodedString)
//        ));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [허용되지 않은 HTTP 메소드 요청(HttpRequestMethodNotSupportedException)] message : {}, meothd : {}", e.getMessage(), e.getMethod(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(
//                ResultCode.Error.METHOD_NOT_ALLOWED,
//                ErrorMessage.combine(ErrorMessage.METHOD, e.getMethod())
//        ));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(MissingPathVariableException]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> 잘못된 요청(ServletRequestBindingException). message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(ResultCode.Error.BAD_REQUEST, e.getMessage()));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        String causeStr = ErrorMessage.combineBindingResult(fieldErrors);
//        log.error("Exception Occurred. -> [잘못된 요청(MethodArgumentNotValidException)], message : {}, cause : {}", e.getMessage(), causeStr, e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(ResultCode.Error.BAD_REQUEST, causeStr));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String causeStr = ErrorMessage.combine(ErrorMessage.TYPE, Objects.requireNonNull(e.getRequiredType()).getSimpleName(), ErrorMessage.VALUE, String.valueOf(e.getValue()));
//        log.error("Exception Occurred. -> [잘못된 타입 요청(TypeMismatchException)], message : {}, cause : {}", e.getMessage(), causeStr, e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(ResultCode.Error.BAD_REQUEST, causeStr));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(HttpMediaTypeNotAcceptableException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(HttpMediaTypeNotSupportedException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(MissingServletRequestParameterException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(MissingServletRequestPartException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(HandlerMethodValidationException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.INVALID_PARAMETER));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(NoHandlerFoundException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(AsyncRequestTimeoutException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(ErrorResponseException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(MaxUploadSizeExceededException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(ConversionNotSupportedException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        // 타입 미스매칭
//        log.error("Exception Occurred. -> [잘못된 요청(HttpMessageNotReadableException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.createDetail(ResultCode.Error.BAD_REQUEST, e.getMessage()));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(HttpMessageNotWritableException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        log.error("Exception Occurred. -> [잘못된 요청(MethodValidationException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.BAD_REQUEST));
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception Occurred. -> [내부 에러 발생(Exception)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.UNKNOWN));
//    }
//
//    @ExceptionHandler(ApiErrorException.class)
//    public ResponseEntity<?> handleException(ApiErrorException e) {
//        log.error("Exception Occurred. -> [내부 API 호출 에러 발생(Exception)]. statusCode : {}, responseBody : {}", e.getStatusCode(), e.getMessage());
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.API_CALL_FAILED));
//    }
//
//    @ExceptionHandler(ApplicationErrorException.class)
//    public ResponseEntity<?> handleException(ApplicationErrorException e) {
//        log.error("Exception Occurred. -> [Application 에러 발생(ApplicationErrorException)]. message : {}, addtionalMessage : {}", e.getMessage(), e.getAdditionalMessage(), e);
//
//        if (Objects.isNull(e.getAdditionalMessage())) {
//            return ResponseEntity.ok().body(BaseResponseFactory.create(e.getErrorCode()));
//        } else {
//            return ResponseEntity.ok().body(BaseResponseFactory.createDetail(e.getErrorCode(), e.getAdditionalMessage()));
//        }
//
//    }
//
//    @ExceptionHandler(ResourceAccessException.class)
//    public ResponseEntity<?> handleException(ResourceAccessException e) {
//        log.error("Exception Occurred. -> [Read timed out 에러 발생(ResourceAccessException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.API_CALL_FAILED));
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<?> handleException(AccessDeniedException e) {
//        log.error("Exception Occurred. -> [권한 에러 발생(AccessDeniedException)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.AUTHORIZATION_IS_FAIL));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        log.error("Exception Occurred. -> [내부 에러 발생(Exception)]. message : {}", e.getMessage(), e);
//        return ResponseEntity.ok().body(BaseResponseFactory.create(ResultCode.Error.UNKNOWN));
//    }

}
