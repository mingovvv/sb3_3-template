package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import demo.template.sb3_3template.dto.res.FinancialDictionaryDto;
import demo.template.sb3_3template.entity.Watchlist;
import demo.template.sb3_3template.enums.WidgetGroup;
import demo.template.sb3_3template.model.WidgetResponse;
import demo.template.sb3_3template.repository.WatchlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WidgetGroup1GeneratorV2 extends AbstractWidgetGenerator {

    private final WatchlistRepository watchlistRepository;

    public WidgetGroup1GeneratorV2(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }

    @Override
    public WidgetGroup getGroup() {
        return WidgetGroup.WIDGET_GROUP_1;
    }

    @Override
    protected WidgetResponse.Widget generateSpecificWidget(int widgetNo) {
        return switch (widgetNo) {
            case 5 -> generateWidget5();
            case 6 -> generateWidget6();
            case 7 -> generateWidget7();
            case 8 -> generateWidget8();
            default -> throw new IllegalStateException("Unexpected value: " + widgetNo);
        };
    }

//    private final WidgetResponse widgetResponse;
//    private final Map<Integer, Supplier<WidgetResponse.Widget>> widgetMap = new HashMap<>();
//
//    // repo
//
//    public WidgetGroup1GeneratorUpgrade() {
//        widgetResponse = new WidgetResponse(1, new ArrayList<>(), new ArrayList<>());
//        widgetMap.put(5, this::generateWidget5);
//        widgetMap.put(6, this::generateWidget6);
//        widgetMap.put(7, this::generateWidget7);
//        widgetMap.put(8, this::generateWidget8);
//    }
//
//    @Override
//    public WidgetGroup getGroup() {
//        return WidgetGroup.WIDGET_GROUP_1;
//    }
//
//    @Override
//    public WidgetResponse generate(Object object1, List<Integer> list) {
//
//        list.forEach(widgetNo -> {
//            Supplier<WidgetResponse.Widget> widgetSupplier = widgetMap.get(widgetNo);
//            widgetResponse.getWidgets().add(widgetSupplier.get());
//        });
//
//        return widgetResponse;
//
//    }

    private WidgetResponse.Widget generateWidget5() {

//        WidgetCreationDto.Widget5 widget5 = widgetCreationDto.widget5();
//
//        Optional<Watchlist> entity = watchlistRepository.findById(11L);
//
//        if (entity.isPresent()) {
//
//            List<Watchlist> list = watchlistRepository.findByUserId(widget5.date());
//
//            if (list.size() == 0) {
//                return null;
//            }
//
//            Optional<Watchlist> optional = watchlistRepository.findByWatchlistIdAndUserId(111L, widget5.event());
//            if (optional.isPresent()) {
//                Page<FinancialDictionaryDto> byCondition = watchlistRepository.findByCondition(1, 5);
//                return aaaaService.post(byCondition);
//            } else {
//                return null;
//            }
//
//        }
//
//        return null;

        // early return pattern
//        WidgetCreationDto.Widget5 widget5 = widgetCreationDto.widget5();
//
//        Optional<Watchlist> entity = watchlistService.findWatchlistById(11L);
//        if (entity.isEmpty()) {
//            return null;
//        }
//
//        List<Watchlist> list = watchlistService.findWatchlistByUserId(widget5.date());
//        if (list.isEmpty()) {
//            return null;
//        }
//
//        Optional<Watchlist> optional = watchlistService.findWatchlistByWatchlistIdAndUserId(111L, widget5.event());
//        if (optional.isEmpty()) {
//            return null;
//        }
//
//        Page<FinancialDictionaryDto> byCondition = watchlistService.findByCondition(1, 5);
//        return aaaaService.post(byCondition);

        return null;
    }

    private WidgetResponse.Widget generateWidget6() {

//        WidgetCreationDto dto = new WidgetCreationDto(1, List.of(), null, null, null);
//        List<WidgetCreationDto.Widget6> widget6 = dto.widget6();
//
//
//        Map<String, List<String>> eventDateToNamesMap = widget6.stream()
//                .collect(Collectors.groupingBy(
//                        WidgetCreationDto.Widget6::date,
//                        Collectors.mapping(WidgetCreationDto.Widget6::event, Collectors.toList())
//                ));
//
//        // RDB 조회
//        List<Object> stockReturnList = List.of();
//
//        stockReturnList.stream().flatMap(stockReturn -> {
//            List<String> eventNames = eventDateToNamesMap.get(stockReturn.);
//            return eventNames.stream().map(s -> new Dto(s.eventName, s.eventDate, s.close)).
////            if (eventNames != null && !eventNames.isEmpty()) {
////                // 첫 번째 이벤트 이름만 매핑할 수도 있고, 여러 개를 매핑할 수도 있음
////                stockReturn.setEventName(String.join(", ", eventNames)); // 여러 이름을 ','로 구분
////            }
//        });



        // 위젯 6 생성 로직
        return null;
    }

    private WidgetResponse.Widget generateWidget7() {
        // 위젯 7 생성 로직
        return null;
    }

    private WidgetResponse.Widget generateWidget8() {
        // 위젯 8 생성 로직
        return null;
    }

}
