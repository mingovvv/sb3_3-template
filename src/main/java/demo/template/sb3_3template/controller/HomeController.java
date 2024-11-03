package demo.template.sb3_3template.controller;

import demo.template.common.model.BaseResponse;
import demo.template.common.model.BaseResponseFactory;
import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import demo.template.sb3_3template.dto.res.PaginationRes;
import demo.template.sb3_3template.service.HomeService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/financial-dictionary")
    public BaseResponse<PaginationRes<FinancialDictionaryDto>> getFinancialDictionary(
            @RequestParam(value = "page", defaultValue = "1") @Parameter(description = "페이지", example = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") @Parameter(description = "사이즈", example = "10") int size
    ) {
        return BaseResponseFactory.create(homeService.getFinancialDictionary(page, size));
    }

    @GetMapping("/financial-dictionary/search")
    public BaseResponse<PaginationRes<FinancialDictionaryDto>> getSearchFinancialDictionary(
            @RequestParam(value = "word") @Parameter(description = "외환헷지", example = "1") String word
    ) {
        return BaseResponseFactory.create(homeService.getSearchFinancialDictionary(word));
    }

    @GetMapping("/rc")
    public BaseResponse<List<String>> getReCom(
            @RequestParam(value = "userId", required = false) @Parameter(description = "사용자_ID", example = "1") String userId
    ) {
        return BaseResponseFactory.create(homeService.getRecom(userId));
    }

}
