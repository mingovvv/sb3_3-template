package demo.template.sb3_3template.enums;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum IndexKrType {

    KOSPI("1", "코스피", "703000"),
    KOSDAQ("7", "코스닥", "940029"),
    DEFAULT(null, null, null);

    private String excngId;
    private String excngName;
    private String ecoCode;

    IndexKrType(String excngId, String excngName, String ecoCode) {
        this.excngId = excngId;
        this.excngName = excngName;
        this.ecoCode = ecoCode;
    }

    private static final Map<String, IndexKrType> INDEX_KR_TYPE_BY_EXCNGID_MAP;
    private static final Map<String, IndexKrType> INDEX_KR_TYPE_BY_ECOCODE_MAP;

    static {
        INDEX_KR_TYPE_BY_EXCNGID_MAP = Stream.of(IndexKrType.values())
                .collect(Collectors.toMap(IndexKrType::getExcngId, Function.identity()));
        INDEX_KR_TYPE_BY_ECOCODE_MAP = Stream.of(IndexKrType.values())
                .collect(Collectors.toMap(IndexKrType::getEcoCode, Function.identity()));
    }

    public static IndexKrType findByExcngId(String excngId) {
        return INDEX_KR_TYPE_BY_EXCNGID_MAP.getOrDefault(excngId, DEFAULT);
    }

    public static IndexKrType findByEcoCode(String ecoCode) {
        return INDEX_KR_TYPE_BY_ECOCODE_MAP.getOrDefault(ecoCode, DEFAULT);
    }

}
