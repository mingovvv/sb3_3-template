package demo.template.sb3_3template.enums;

import lombok.Getter;

@Getter
public enum BsnsDays {

    ONE_DAY(1),
    ONE_WEEK(5),
    ONE_MONTH(20);

    private final int bsnsDays;

    BsnsDays(int bsnsDays) {
        this.bsnsDays = bsnsDays;
    }

}
