package demo.template.sb3_3template.enums;

import lombok.Getter;

@Getter
public enum Tag {

    STOCK("OG_COMPANY"),
    INDEX("MK_INDEX"),
    SECTOR("MK_SECTOR");

    private String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public static Tag findByName(String tagName) {
        return Tag.valueOf(tagName);
    }

}
