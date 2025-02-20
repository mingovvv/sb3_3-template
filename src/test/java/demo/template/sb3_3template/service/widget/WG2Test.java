package demo.template.sb3_3template.service.widget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WG2Test {

    @Test
    void w9And10() {

        // example
        List<WG2Test.Fs> fsList = new ArrayList<>();

        fsList.add(new Fs("001", "2019", "1", BigDecimal.valueOf(900.000)));
        fsList.add(new Fs("001", "2019", "2", BigDecimal.valueOf(1500.000)));
        fsList.add(new Fs("001", "2019", "3", BigDecimal.valueOf(3500.000)));
        fsList.add(new Fs("001", "2019", "4", BigDecimal.valueOf(-1500.000)));

        fsList.add(new Fs("001", "2020", "1", BigDecimal.valueOf(1200.000)));
        fsList.add(new Fs("001", "2020", "2", BigDecimal.valueOf(1800.000)));
        fsList.add(new Fs("001", "2020", "3", BigDecimal.valueOf(3000.000)));
        fsList.add(new Fs("001", "2020", "4", BigDecimal.valueOf(-1000.000)));

        fsList.add(new Fs("001", "2021", "1", BigDecimal.valueOf(1100.000)));
        fsList.add(new Fs("001", "2021", "2", BigDecimal.valueOf(1700.000)));
        fsList.add(new Fs("001", "2021", "3", BigDecimal.valueOf(2500.000)));
        fsList.add(new Fs("001", "2021", "4", BigDecimal.valueOf(-800.000)));

        fsList.add(new Fs("001", "2022", "1", BigDecimal.valueOf(1000.000)));
        fsList.add(new Fs("001", "2022", "2", BigDecimal.valueOf(1600.000)));
        fsList.add(new Fs("001", "2022", "3", BigDecimal.valueOf(2000.000)));
        fsList.add(new Fs("001", "2022", "4", BigDecimal.valueOf(-1200.000)));

        fsList.add(new Fs("001", "2023", "1", BigDecimal.valueOf(1300.000)));
        fsList.add(new Fs("001", "2023", "2", BigDecimal.valueOf(1400.000)));
        fsList.add(new Fs("001", "2023", "3", BigDecimal.valueOf(2200.000)));
        fsList.add(new Fs("001", "2023", "4", BigDecimal.valueOf(-1100.000)));

        fsList.add(new Fs("001", "2024", "1", BigDecimal.valueOf(1250.000)));
        fsList.add(new Fs("001", "2024", "2", BigDecimal.valueOf(1750.000)));
        fsList.add(new Fs("001", "2024", "3", BigDecimal.valueOf(2750.000)));
        fsList.add(new Fs("001", "2024", "4", BigDecimal.valueOf(-900.000)));

        List<W11Dto> list = fsList.stream()
                .map(s -> new W11Dto(s.stockCd, s.fsDt, s.quarter, s.data))
                .toList();

//        List<W11Dto> list = fsList.stream()
//                .collect(Collectors.groupingBy(
//                        WG2Test.Fs::getFsDt,
//                        Collectors.summingDouble(fs -> Double.parseDouble(fs.getData())) // data 값 합산
//                ))
//                .entrySet()
//                .stream()
//                .map(entry -> new WG2Test.W11Dto("001", entry.getKey(), null, String.valueOf(entry.getValue()))) // W11Dto로 변환
//                .collect(Collectors.toList());

        // 전년동분기대비 단순 차분
        List<WG2Test.DiffResult> differences = calculateYearOverYearDifference(list);
        for (WG2Test.DiffResult result : differences) {
            System.out.println(result);
        }

        System.out.println("=================");

        // 전년동분기대비 증감율
        List<WG2Test.DiffResult> changeRates = calculateYearOverYearChangeRate(list);
        for (WG2Test.DiffResult result : changeRates) {
            System.out.println(result);
        }

    }

    public static List<WG2Test.DiffResult> calculateYearOverYearDifference(List<W11Dto> w11dto) {
        List<WG2Test.DiffResult> diffResults = new ArrayList<>();

        // 데이터를 년도별, 분기별로 관리하기 위해 Map을 사용
        Map<String, WG2Test.W11Dto> fsByQuarter = new HashMap<>();

        // 년도-분기별 데이터를 맵에 저장
        for (WG2Test.W11Dto w : w11dto) {
            fsByQuarter.put(w.fsDt + "-" + w.quarter, w);
        }

        // 현재 년도의 데이터와 전년 데이터를 비교하여 차분 계산
        for (WG2Test.W11Dto w : w11dto) {
            String previousYear = String.valueOf(Integer.parseInt(w.fsDt) - 1);
            String previousYearKey = previousYear + "-" + w.quarter;

            if (fsByQuarter.containsKey(previousYearKey)) {
                WG2Test.W11Dto previousFs = fsByQuarter.get(previousYearKey);
                BigDecimal subtract = w.data.subtract(previousFs.data);

                // 차분값을 String으로 변환하여 DTO에 저장
                String diff = String.valueOf(subtract);
                diffResults.add(new WG2Test.DiffResult(w.stockCd, w.fsDt, w.quarter, diff));
            }
        }

        return diffResults;
    }

    public static List<DiffResult> calculateYearOverYearChangeRate(List<W11Dto> w11dto) {
        List<DiffResult> diffResults = new ArrayList<>();

        // 데이터를 년도별, 분기별로 관리하기 위해 Map을 사용
        Map<String, W11Dto> fsByQuarter = new HashMap<>();

        // 년도-분기별 데이터를 맵에 저장
        for (W11Dto fs : w11dto) {
            fsByQuarter.put(fs.fsDt + "-" + fs.quarter, fs);
        }

        // 현재 년도의 데이터와 전년 데이터를 비교하여 증감율 계산
        for (W11Dto currentFs : w11dto) {
            String previousYear = String.valueOf(Integer.parseInt(currentFs.fsDt) - 1);
            String previousYearKey = previousYear + "-" + currentFs.quarter;

            if (fsByQuarter.containsKey(previousYearKey)) {
                W11Dto previousFs = fsByQuarter.get(previousYearKey);
                try {
                    String changeRate;

                    if (previousFs.data.compareTo(BigDecimal.ZERO) > 0 && currentFs.data.compareTo(BigDecimal.ZERO) > 0) {
                        // 양수 -> 양수 : 증감율 계산
                        BigDecimal rate = currentFs.data.subtract(previousFs.data).divide(previousFs.data, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                        changeRate = String.format("%.2f", rate);
                    } else if (previousFs.data.compareTo(BigDecimal.ZERO) < 0 && currentFs.data.compareTo(BigDecimal.ZERO) > 0) {
                        // 음수 -> 양수 : 흑자전환
                        changeRate = "흑자전환";
                    } else if (previousFs.data.compareTo(BigDecimal.ZERO) > 0 && currentFs.data.compareTo(BigDecimal.ZERO) < 0) {
                        // 양수 -> 음수 : 적자전환
                        changeRate = "적자전환";
                    } else if (previousFs.data.compareTo(BigDecimal.ZERO) < 0 && currentFs.data.compareTo(BigDecimal.ZERO) < 0) {
                        // 음수 -> 음수 : 적자지속
                        changeRate = "적자지속";
                    } else {
                        // 그 외 경우에는 증감율을 구할 수 없음
                        changeRate = "증감율 없음";
                    }

                    // 결과를 새로운 DTO에 저장
                    diffResults.add(new DiffResult(currentFs.stockCd, currentFs.fsDt, currentFs.quarter, changeRate));
                } catch (NumberFormatException e) {
                    System.out.println("데이터 변환 오류: " + e.getMessage());
                }
            }
        }

        return diffResults;
    }

    @Getter
    static class Fs {
        String acctStd;
        String fsType;
        String acctCd;
        String cumType;
        String stockCd;
        String fsDt; // yyyy
        String quarter;
        BigDecimal data;

        public Fs(String stockCd, String fsDt, String quarter, BigDecimal data) {
            this.stockCd = stockCd;
            this.fsDt = fsDt;
            this.quarter = quarter;
            this.data = data;
        }

        public Fs(String acctStd, String fsType, String acctCd, String cumType, String stockCd, String fsDt, String quarter, BigDecimal data) {
            this.acctStd = acctStd;
            this.fsType = fsType;
            this.acctCd = acctCd;
            this.cumType = cumType;
            this.stockCd = stockCd;
            this.fsDt = fsDt;
            this.quarter = quarter;
            this.data = data;
        }
    }

    @Getter
    @AllArgsConstructor
    static class W11Dto {
        String stockCd;
        String fsDt;
        String quarter;
        BigDecimal data;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    static class DiffResult {
        String stock_cd; // 주식종목코드
        String fsDt;    // 년도
        String quarter;  // 분기
        String diff;     // 차분값
    }

//    SELECT sc.isin, sc.stock_cd, sc.stock_nm_kr, si_max.theme_nm AS max_theme_nm, si_max.max_mk_cap
//    FROM stock_code sc
//    LEFT JOIN sector_stock_master ssm
//    ON sc.stock_cd = ssm.stock_cd
//    LEFT JOIN (
//    -- 종목별로 가장 큰 지수산정시가총액과 섹터명을 찾는 서브쿼리
//            SELECT ssm_inner.stock_cd, si_inner.theme_nm, MAX(CAST(si_inner.idx_cal_mk_cap AS DECIMAL(19, 2))) AS max_mk_cap
//    FROM sector_stock_master ssm_inner
//    JOIN sector_index si_inner
//    ON ssm_inner.theme_cd = si_inner.theme_cd
//    GROUP BY ssm_inner.stock_cd, si_inner.theme_nm
//    ) si_max
//    ON ssm.stock_cd = si_max.stock_cd
//    ORDER BY sc.stock_cd;
//
//
//    QStockCode stockCode = QStockCode.stockCode;
//    QSectorStockMaster sectorStockMaster = QSectorStockMaster.sectorStockMaster;
//    QSectorIndex sectorIndex = QSectorIndex.sectorIndex;
//
//    // 서브쿼리: 각 종목별로 가장 큰 지수산정시가총액을 가진 섹터 찾기
//    SubQueryExpression<Tuple> subQuery = JPAExpressions
//            .select(sectorStockMaster.stockCd, sectorIndex.themeNm, sectorIndex.idxCalMkCap.max())
//            .from(sectorStockMaster)
//            .join(sectorIndex).on(sectorStockMaster.themeCd.eq(sectorIndex.themeCd))
//            .groupBy(sectorStockMaster.stockCd, sectorIndex.themeNm);
//
//    // 메인 쿼리
//    JPAQuery<Tuple> query = new JPAQuery<>(entityManager);
//
//    List<Tuple> result = query
//            .select(stockCode.isin, stockCode.stockCd, stockCode.stockNmKr, subQuery.get(1), subQuery.get(2)) // 1: theme_nm, 2: max_mk_cap
//            .from(stockCode)
//            .leftJoin(sectorStockMaster).on(stockCode.stockCd.eq(sectorStockMaster.stockCd))
//            .leftJoin(subQuery).on(sectorStockMaster.stockCd.eq(subQuery.get(0))) // 0: stock_cd
//            .fetch();


}
