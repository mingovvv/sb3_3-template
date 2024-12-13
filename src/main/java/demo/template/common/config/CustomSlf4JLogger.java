//package demo.template.common.config;
//
//import com.p6spy.engine.logging.Category;
//import com.p6spy.engine.spy.appender.Slf4JLogger;
//import demo.template.common.aop.TrackQueriesAspect;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Slf4j
//@Configuration
//public class CustomSlf4JLogger extends Slf4JLogger {
//
//    private static boolean enableLogging;
//
//    @Value("${enable-logging:false}")
//    public void setEnableLogging(boolean enableLogging) {
//        CustomSlf4JLogger.enableLogging = enableLogging;
//    }
//
//    @Override
//    public void logSQL(int i, String s, long l, Category category, String s1, String s2, String s3) {
//
////        if (TrackQueriesAspect.isTargetQueryActive()) {
////            TrackQueriesAspect.addQuery(l, s2);
////        }
////
////        if (enableLogging) {
////            super.logSQL(i, s, l, category, s1, s2, s3);
////        }
//
//    }
//
//}
