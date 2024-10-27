package demo.template.sb3_3template.enums;

import demo.template.sb3_3template.entity.fs.Fs;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum FinancialClassifier {

    FI001("I", "C", "C", "A50", "BPS", GraphType.YOY_DIFFERENCE),
    FI002("I", "C", "C", "A100", "자산", GraphType.RAW_SERIES),
    FI003("I", "C", "C", "A200", "매출액", GraphType.YOY_PERCENT_CHANGE);

    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String nm;
    private final GraphType graphType;

    FinancialClassifier(String a, String b, String c, String d, String nm, GraphType graphType) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.nm = nm;
        this.graphType = graphType;
    }

    private static final Map<String, FinancialClassifier> FINANCIALCLASSIFIER_MAP;

    static {
        FINANCIALCLASSIFIER_MAP = Stream.of(FinancialClassifier.values())
                .collect(Collectors.toMap(FinancialClassifier::getNm, Function.identity()));
    }

    public static FinancialClassifier findByFsNm(String FsNm) {
        return FINANCIALCLASSIFIER_MAP.getOrDefault(FsNm, null);
    }

    enum GraphType {
        RAW_SERIES, // 원계열
        YOY_DIFFERENCE, // 전년 동분기대비 단순 차분
        YOY_PERCENT_CHANGE // 전년 동분기대비 증감율
    }

    public static void find(String fsNm, List<Fs> fsList) {

        Map<String, Fs> fsMap = fsList.stream()
                .collect(Collectors.toMap(
                        fs -> fs.getStockCd() + fs.getFsDt() + fs.getQuarter(),
                        Function.identity(),
                        (existing, replacement) -> existing));

        FinancialClassifier classifier = findByFsNm(fsNm);
        GraphType type = classifier.graphType;

        switch (type) {
            case RAW_SERIES:
                // 원계열 값을 그대로 출력
                fsList.forEach(fs -> System.out.println("원계열 값: " + fs.getData()));
                break;

            case YOY_DIFFERENCE:
                // 전년 동분기 대비 단순 차분 출력
                for (Fs current : fsList) {
                    String previousKey = current.getStockCd() + current.getQuarter() + (Integer.parseInt(current.getFsDt()) - 1);
                    Fs previous = fsMap.get(previousKey);
                    if (previous != null) {
                        BigDecimal difference = current.getData().subtract(previous.getData());
                        System.out.println("전년 동분기 대비 단순 차분: " + difference);
                    }
                }
                break;

            case YOY_PERCENT_CHANGE:
                // 전년 동분기 대비 증감율 계산 및 경우의 수 처리
                for (Fs current : fsList) {
                    String previousKey = current.getStockCd() + current.getQuarter() + (Integer.parseInt(current.getFsDt()) - 1);
                    Fs previous = fsMap.get(previousKey);
                    if (previous != null) {
                        BigDecimal currentValue = current.getData();
                        BigDecimal previousValue = previous.getData();

                        if (previousValue.compareTo(BigDecimal.ZERO) > 0 && currentValue.compareTo(BigDecimal.ZERO) > 0) {
                            // 경우의 수 1: 전년 동분기 원계열 값이 양수, 원년 동분기 원계열 값이 양수
                            BigDecimal percentChange = currentValue.subtract(previousValue)
                                    .divide(previousValue, MathContext.DECIMAL128)
                                    .multiply(BigDecimal.valueOf(100));
                            System.out.println("전년 동분기 대비 증감율: " + percentChange + "%");
                        } else if (previousValue.compareTo(BigDecimal.ZERO) < 0 && currentValue.compareTo(BigDecimal.ZERO) > 0) {
                            // 경우의 수 2: 전년 동분기 원계열 값이 음수, 원년 동분기 원계열 값이 양수
                            System.out.println("흑자전환");
                        } else if (previousValue.compareTo(BigDecimal.ZERO) > 0 && currentValue.compareTo(BigDecimal.ZERO) < 0) {
                            // 경우의 수 3: 전년 동분기 원계열 값이 양수, 원년 동분기 원계열 값이 음수
                            System.out.println("적자전환");
                        } else if (previousValue.compareTo(BigDecimal.ZERO) < 0 && currentValue.compareTo(BigDecimal.ZERO) < 0) {
                            // 경우의 수 4: 전년 동분기 원계열 값이 음수, 원년 동분기 원계열 값이 음수
                            System.out.println("적자지속");
                        }
                    }
                }
                break;

            default:
                throw new UnsupportedOperationException("지원되지 않는 GraphType: " + type);
        }
    }


}
