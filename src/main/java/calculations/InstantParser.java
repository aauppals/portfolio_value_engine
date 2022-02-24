package calculations;

import com.google.common.annotations.VisibleForTesting;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class InstantParser {
    private static final String START_DAY = "01";
    private static final String DATE_SEPARATOR = "-";
    public static final String DEFAULT_TIMEZONE = "Asia/Shanghai";

    @VisibleForTesting
    final Map<String, String> monthToNum = new LinkedHashMap<>();
    private final StringBuilder stringBuilder = new StringBuilder();

    public InstantParser() {
        monthToNum.put("JAN", "01");
        monthToNum.put("FEB", "02");
        monthToNum.put("MAR", "03");
        monthToNum.put("APR", "04");
        monthToNum.put("MAY", "05");
        monthToNum.put("JUN", "06");
        monthToNum.put("JUL", "07");
        monthToNum.put("AUG", "08");
        monthToNum.put("SEP", "09");
        monthToNum.put("OCT", "10");
        monthToNum.put("NOV", "11");
        monthToNum.put("DEC", "12");
    }

    public Instant getInstant(String month, String year) {
        stringBuilder.setLength(0);
        LocalDate date = LocalDate.parse(stringBuilder
                .append(year)
                .append(DATE_SEPARATOR)
                .append(monthToNum.get(month))
                .append(DATE_SEPARATOR)
                .append(START_DAY));
        return date.atStartOfDay(ZoneId.of(DEFAULT_TIMEZONE)).toInstant();
    }
}
