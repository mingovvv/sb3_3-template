package demo.template.sb3_3template.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

public record PaginationRes<T>(

        @Schema(description = "전체 페이지 수", example = "8")
        int totalPages,

        @Schema(description = "전체 게시글 수", example = "359")
        int totalElements,

        @Schema(description = "현재 페이지", example = "3")
        int currentPage,

        @Schema(description = "페이지 당 게시글 수", example = "10")
        int size,

        @Schema(description = "첫번째 페이지 여부", example = "false")
        boolean isFirst,

        @Schema(description = "마지막 페이지 여부", example = "false")
        boolean isLast,

        @Schema(description = "페이지 상세")
        List<T> detail

) {

    public static <T> PaginationRes<T> of(int totalPages, int totalElements, int currentPage, int size, boolean isFirst, boolean isLast, List<T> detail) {
        return new PaginationRes<>(totalPages, totalElements, currentPage, size, isFirst, isLast, detail);
    }

    public static <T> PaginationRes<T> toRes(Page<T> page) {
        return PaginationRes.of(
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize(),
                page.getNumber() == 0,
                page.getNumber() == (page.getTotalPages() - 1),
                page.getContent()
        );
    }

}
