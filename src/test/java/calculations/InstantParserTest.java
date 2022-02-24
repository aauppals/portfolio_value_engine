package calculations;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class InstantParserTest {
    //TODO: for all months of the year (use the map in InstantParser):
    // test that the correct instant is returned, it should be 12am of the 1st day of the month
    private final InstantParser instantParser = new InstantParser();

    @Test
    public void WHEN_correct_month_year_pair_given_THEN_correct_instance_returned() {
        ArrayList<String> months = new ArrayList<>(instantParser.monthToNum.keySet());
        for (int i = 0; i < months.size(); i++) {
            Instant actualInstant = instantParser.getInstant(months.get(i), "2001");
            assertEquals(2001, actualInstant.atZone(ZoneId.of(InstantParser.DEFAULT_TIMEZONE)).toLocalDate().getYear());
            assertEquals(Month.of(i + 1), actualInstant.atZone(ZoneId.of(InstantParser.DEFAULT_TIMEZONE)).toLocalDate().getMonth());
            assertEquals(1, actualInstant.atZone(ZoneId.of(InstantParser.DEFAULT_TIMEZONE)).toLocalDate().getDayOfMonth());

        }

    }

    @Test
    public void WHEN_incorrect_month_year_pair_given_THEN_exception_thrown() {

        testNegativeCase("XXX", "2001");
        testNegativeCase("OCT", "20j01");
    }

    private void testNegativeCase(String month, String year) {
        try {
            instantParser.getInstant(month, year);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("could not be parsed"));
        }
    }
}

