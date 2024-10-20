package demo.template.sb3_3template.enums;

import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Tag {

    STOCK("OG_COMPANY"),
    INDEX("MK_INDEX"),
    SECTOR("MK_SECTOR"),
    FINANCE("FINANCE"),
    원자재("원자재"),
    채권("채권"),
    외환("외환"),
    경제("경제"),
        기준금리("기준금리"),
    배당("배당"),
    재무("재무"),
    팩터명("팩터명");

    private String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public static Tag findByName(String tagName) {
        return Tag.valueOf(tagName);
    }

    private static final Map<String, Tag> TAG_MAP;

    static {
        TAG_MAP = Stream.of(Tag.values())
                .collect(Collectors.toMap(Tag::getTagName, Function.identity()));
    }

    public static Tag findBy(String tagId) {
        return TAG_MAP.getOrDefault(tagId, null);
    }

}
