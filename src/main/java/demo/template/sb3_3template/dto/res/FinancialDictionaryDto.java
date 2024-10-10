package demo.template.sb3_3template.dto.res;

import com.querydsl.core.annotations.QueryProjection;

public record FinancialDictionaryDto(
        String name,
        String id
) {

    @QueryProjection
    public FinancialDictionaryDto(String name, String id) {
        this.name = name;
        this.id = id;
    }

}
