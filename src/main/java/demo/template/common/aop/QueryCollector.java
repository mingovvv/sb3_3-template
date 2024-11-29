package demo.template.common.aop;

import java.util.ArrayList;
import java.util.List;

public class QueryCollector {

    private static final ThreadLocal<List<String>> threadLocalQueries = ThreadLocal.withInitial(ArrayList::new);

    public static void addQuery(String sql) {
        threadLocalQueries.get().add(sql);
    }

    public static List<String> getAndClearQueries() {
        List<String> queries = new ArrayList<>(threadLocalQueries.get());
        threadLocalQueries.remove(); // 초기화
        return queries;
    }

}

