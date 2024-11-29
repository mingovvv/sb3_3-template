package demo.template.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class TrackQueriesAspect {

    private static final ThreadLocal<Boolean> methodContext = ThreadLocal.withInitial(() -> false);

    // 현재 메서드 컨텍스트 활성화 상태 확인
    public static boolean isMethodContextActive() {
        return methodContext.get();
    }

    @Around("@annotation(demo.template.common.annotation.TrackQueries)")
    public Object trackQueriesForAnnotatedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        methodContext.set(true); // 컨텍스트 활성화
        try {
            Object result = joinPoint.proceed(); // 실제 메서드 실행
            List<String> queries = QueryCollector.getAndClearQueries(); // 쿼리 수집
            saveQueriesToDatabase(joinPoint.getSignature().getName(), queries); // 저장
            return result;
        } finally {
            methodContext.remove(); // 컨텍스트 비활성화
        }
    }

    private void saveQueriesToDatabase(String methodName, List<String> queries) {
        // RDB에 메서드명과 쿼리 저장 로직
        System.out.printf("Method: %s, Queries: %s%n", methodName, queries);
        // 예: repository.save(new QueryEntity(methodName, queries));
    }

}
