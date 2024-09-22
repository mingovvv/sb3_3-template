package demo.template.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SessionAspect {

    @Around("execution(* org.springframework.session.jdbc.JdbcIndexedSessionRepository.save(..)) && args(session)")
    public Object aroundSave(ProceedingJoinPoint joinPoint, Object session) throws Throwable {

        boolean shouldSave = shouldSaveSession(session);

        if (shouldSave) {
            System.out.println("Saving session: " + session);
            return joinPoint.proceed(); // 원래의 save 메서드 호출
        } else {
            System.out.println("Skipping session save for: " + session);
            return null;
        }
    }

    private boolean shouldSaveSession(Object session) {
        Session se = (Session) session;
        return true;
    }

}
