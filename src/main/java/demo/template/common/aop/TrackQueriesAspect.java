package demo.template.common.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class TrackQueriesAspect {

    private static final ThreadLocal<Boolean> IS_TARGET = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<AtomicInteger> QUERY_INDEX = ThreadLocal.withInitial(() -> new AtomicInteger(0));
    private static final ThreadLocal<List<Track>> TRACKED_QUERIES = ThreadLocal.withInitial(ArrayList::new);

    // 현재 메서드 컨텍스트 활성화 상태 확인
    public static boolean isTargetQueryActive() {
        return IS_TARGET.get();
    }

    @Around("@annotation(demo.template.common.annotation.TrackQueries)")
    public Object trackQueriesForAnnotatedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        IS_TARGET.set(true); // 컨텍스트 활성화
        try {
            Object result = joinPoint.proceed(); // 실제 메서드 실행
            return result;
        } finally {
            IS_TARGET.remove(); // 컨텍스트 비활성화
        }
    }

    public static void addQuery(long elapsed, String sql) {
        TRACKED_QUERIES.get().add(new Track(QUERY_INDEX.get().incrementAndGet(), elapsed, StringUtils.normalizeSpace(sql)));
    }

    public static List<Track> getQuery() {
        return TRACKED_QUERIES.get();
    }

    public static void clearIndex() {
        QUERY_INDEX.remove();
    }

    public static void clearTrackedQueries() {
        TRACKED_QUERIES.remove();
    }

}
