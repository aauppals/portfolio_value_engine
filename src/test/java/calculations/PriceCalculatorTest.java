package calculations;

import instrument.Option;
import instrument.OptionType;
import instrument.Stock;
import marketdata.PriceUpdate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static calculations.PriceCalculator.PRECISION_DECIMAL_PLACES;
import static java.time.Instant.parse;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PriceCalculatorTest {
    private static final HashSet<PriceUpdate> priceUpdates = new LinkedHashSet<>();

    private static final Stock stock1 = new Stock("S1", 0.5);
    private static final Stock stock2 = new Stock("S2", 0.5);
    private static final Stock stock3 = new Stock("S3", 0.5);

    private static final Option option1Call = new Option("O1-C", parse("2023-01-01T13:44:14.504Z"), 10, OptionType.CALL, stock1);
    private static final Option option1Put = new Option("O1-P", parse("2023-01-01T13:44:14.504Z"), 10, OptionType.PUT, stock1);
    private final static Option option2 = new Option("O2", parse("2023-02-01T13:44:14.504Z"), 20, OptionType.PUT, stock2);
    private final static Option option3 = new Option("O3", parse("2023-03-01T13:44:14.504Z"), 30, OptionType.CALL, stock3);


    private static final InstrumentDefinitionProvider instrumentDefinitionProvider = Mockito.mock(InstrumentDefinitionProvider.class);
    private static final Positions positions = Mockito.mock(Positions.class);

    @BeforeEach
    public void prepareDataAndSetMocks() {
        PriceUpdate priceUpdate1 = new PriceUpdate("S1", 5.0);
        PriceUpdate priceUpdate2 = new PriceUpdate("S2", 10.0);
        PriceUpdate priceUpdate3 = new PriceUpdate("S3", 20.0);
        priceUpdates.add(priceUpdate1);
        priceUpdates.add(priceUpdate2);
        priceUpdates.add(priceUpdate3);

        final Position position1 = new Position(100, stock1);
        final Position position2 = new Position(200, stock2);
        final Position position3 = new Position(300, stock3);

        final Position optionPosition1C = new Position(100, option1Call);
        final Position optionPosition1P = new Position(100, option1Put);
        final Position optionPosition2 = new Position(200, option2);
        final Position optionPosition3 = new Position(300, option3);

        when(positions.getPosition("S1")).thenReturn(position1);
        when(positions.getPosition("S2")).thenReturn(position2);
        when(positions.getPosition("S3")).thenReturn(position3);

        when(positions.getPosition("O1-C")).thenReturn(optionPosition1C);
        when(positions.getPosition("O1-P")).thenReturn(optionPosition1P);
        when(positions.getPosition("O2")).thenReturn(optionPosition2);
        when(positions.getPosition("O3")).thenReturn(optionPosition3);
    }

    @Test
    public void WHEN_underlying_prices_supplied_for_one_call_option_THEN_correct_option_price_returned() {
        when(instrumentDefinitionProvider.getOptionByUnderlyingStock("S1")).thenReturn(singletonList(option1Call));
        executeTestCase(priceUpdates -> {
            assertEquals(PriceCalculatorTest.priceUpdates.size() + 1, priceUpdates.size());
            final List<PriceUpdate> actualPriceUpdate = extractActualOptionPrices(priceUpdates, singletonList("O1-C"));
            assertEquals(1, actualPriceUpdate.size());
            assertEquals(0.09649, actualPriceUpdate.get(0).getPrice(), PRECISION_DECIMAL_PLACES);
        });
    }

    @Test
    public void WHEN_underlying_prices_supplied_for_one_put_option_THEN_correct_option_price_returned() {
        when(instrumentDefinitionProvider.getOptionByUnderlyingStock("S1")).thenReturn(singletonList(option1Put));
        executeTestCase(priceUpdates -> {
            assertEquals(PriceCalculatorTest.priceUpdates.size() + 1, priceUpdates.size());
            final List<PriceUpdate> actualPriceUpdate = extractActualOptionPrices(priceUpdates, singletonList("O1-P"));
            assertEquals(1, actualPriceUpdate.size());
            assertEquals(4.9307, actualPriceUpdate.get(0).getPrice(), PRECISION_DECIMAL_PLACES);
        });
    }

    @Test
    public void WHEN_underlying_prices_supplied_multiple_options_THEN_correct_option_prices_returned() {
        when(instrumentDefinitionProvider.getOptionByUnderlyingStock("S1")).thenReturn(Arrays.asList(option1Call, option1Put));

        executeTestCase(priceUpdates -> {
            assertEquals(PriceCalculatorTest.priceUpdates.size() + 2, priceUpdates.size());
            final List<PriceUpdate> actualPriceUpdate = extractActualOptionPrices(priceUpdates, Arrays.asList("O1-C", "O1-P"));
            assertEquals(2, actualPriceUpdate.size());
            assertEquals(0.0964, actualPriceUpdate.get(0).getPrice(), PRECISION_DECIMAL_PLACES);
            assertEquals(4.9307, actualPriceUpdate.get(1).getPrice(), PRECISION_DECIMAL_PLACES);
        });
    }

    private static void executeTestCase(Consumer<Set<PriceUpdate>> tester) {
        PriceCalculator priceCalculator = new PriceCalculator(tester, instrumentDefinitionProvider, positions);
        assertNotNull(priceCalculator);
        priceCalculator.accept(priceUpdates);
    }

    private List<PriceUpdate> extractActualOptionPrices(final Set<PriceUpdate> actualPriceUpdates,
                                                        final List<String> optionTickers) {
        assertTrue(actualPriceUpdates.removeAll(PriceCalculatorTest.priceUpdates));
        final Map<String, PriceUpdate> optionPriceMap = actualPriceUpdates.stream().collect(toMap(PriceUpdate::getTicker, priceUpdate -> priceUpdate));
        assertEquals(optionTickers.size(), optionPriceMap.size());
        List<PriceUpdate> result = optionTickers.stream().map(optionPriceMap::get).collect(Collectors.toList());
        assertEquals(optionTickers.size(), result.size());
        return result;
    }
}
