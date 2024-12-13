package demo.template.common.aop;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SQLStorage {

    private int index;
    private String query;
    private List<String> parameters;

    public SQLStorage(int index, String query) {
        this.index = index;
        this.query = query;
        this.parameters = new ArrayList<>();
    }

    public static SQLStorage of() {
        return new SQLStorage(0, null);
    }

}
