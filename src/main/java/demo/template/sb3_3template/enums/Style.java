package demo.template.sb3_3template.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum Style {

    GOOD("good"),
    BAD("bad"),
    BOLD("bold"),
    NONE("");

    private String className;

    Style(String className) {
        this.className = className;
    }

    public static String makeCssStyle(Style... styles) {
        return Arrays.stream(styles).map(Style::getClassName).collect(Collectors.joining(" "));
    }

}
