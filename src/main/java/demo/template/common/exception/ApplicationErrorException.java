//package demo.template.common.exception;
//
//import kr.co.skcc.common.enums.ResultCode;
//import lombok.Getter;
//
//@Getter
//public class ApplicationErrorException extends RuntimeException {
//
//    private final ResultCode.Error errorCode;
//    private final String additionalMessage;
//
//    private ApplicationErrorException(ResultCode.Error errorCode, Throwable cause) {
//        super(errorCode.getMessage(), cause);
//        this.errorCode = errorCode;
//        this.additionalMessage = null;
//    }
//
//    private ApplicationErrorException(ResultCode.Error errorCode, String additionalMessage, Throwable cause) {
//        super(errorCode.getMessage(), cause);
//        this.errorCode = errorCode;
//        this.additionalMessage = additionalMessage;
//    }
//
//    public static ApplicationErrorException of(ResultCode.Error errorCode) {
//        return new ApplicationErrorException(errorCode, null);
//    }
//
//    public static ApplicationErrorException of(ResultCode.Error errorCode, String additionalMessage) {
//        return new ApplicationErrorException(errorCode, additionalMessage, null);
//    }
//
//}
