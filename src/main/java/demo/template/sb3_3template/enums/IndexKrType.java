package demo.template.sb3_3template.enums;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum IndexKrType {

    KOSPI("1", "코스피"),
    KOSDAQ("7", "코스닥");

    private String excngId;
    private String excngName;

    IndexKrType(String excngId, String excngName) {
        this.excngId = excngId;
        this.excngName = excngName;
    }

    private static final Map<String, IndexKrType> INDEX_KR_TYPE_MAP;

    static {
        INDEX_KR_TYPE_MAP = Stream.of(IndexKrType.values())
                .collect(Collectors.toMap(IndexKrType::getExcngId, Function.identity()));
    }

    public static IndexKrType findById(String excngId) {
        return INDEX_KR_TYPE_MAP.getOrDefault(excngId, null);
    }

}
