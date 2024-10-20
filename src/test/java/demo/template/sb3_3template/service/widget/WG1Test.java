package demo.template.sb3_3template.service.widget;

import demo.template.sb3_3template.dto.WidgetCreationDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class WG1Test {

    @Test
    void w6() {

        // example
        WidgetCreationDto dto = new WidgetCreationDto(1, List.of(), null, List.of(
                new WidgetCreationDto.Widget6("이벤트1", "20241001"),
                new WidgetCreationDto.Widget6("이벤트2", "20241001"),
                new WidgetCreationDto.Widget6("이벤트3", "20241001"),
                new WidgetCreationDto.Widget6("이벤트4", "20241002"),
                new WidgetCreationDto.Widget6("이벤트5", "20241003")
        ), null);

        List<WidgetCreationDto.Widget6> widget6 = dto.widget6();

        Map<String, List<String>> eventDateToNamesMap = widget6.stream()
                .collect(Collectors.groupingBy(
                        WidgetCreationDto.Widget6::date,
                        Collectors.mapping(WidgetCreationDto.Widget6::event, Collectors.toList())
                ));

        // RDB 조회
        List<StockReturn> stockReturnList = List.of(new StockReturn("20241001", 4.2), new StockReturn("20241002", 5.6));

        List<Dto> list = stockReturnList.stream().flatMap(stockReturn -> {
            List<String> eventNames = eventDateToNamesMap.get(stockReturn.stdDate);
            return eventNames.stream().map(s -> new Dto(s, stockReturn.stdDate, stockReturn.close));
        }).toList();

        list.forEach(System.out::println);

    }

    static class StockReturn {
        String stdDate;
        double close;

        public StockReturn(String stdDate, double close) {
            this.stdDate = stdDate;
            this.close = close;
        }
    }

    static class Dto {
        String eventName;
        String eventDate;
        double close;

        public Dto(String eventName, String eventDate, double close) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.close = close;
        }

        @Override
        public String toString() {
            return "Dto{" +
                    "eventName='" + eventName + '\'' +
                    ", eventDate='" + eventDate + '\'' +
                    ", close=" + close +
                    '}';
        }
    }




}