package calculations;

import instrument.Instrument;
import instrument.Option;
import instrument.OptionType;
import instrument.Stock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.io.FileUtils.forceDelete;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionFactoryTest {
    public static final String TEST_POSITIONS_CSV = "TestPositions.csv";
    //TODO: user before to create dummy positions csv in tmp dir
    private final Stock aapl_stock = new Stock("AAPL", 0);
    private final Stock telsa_stock = new Stock("TELSA", 0);
    private final Instrument aapl_call_option = new Option("AAPL-OCT-2020-110-C", Instant.now(), 110, OptionType.CALL, aapl_stock);
    private final Instrument telsa_put_option = new Option("TELSA-DEC-2020-400-P", Instant.now(), 400, OptionType.PUT, telsa_stock);
    private final Position position1 = new Position(1000, aapl_stock);
    private final Position position2 = new Position(-20000, aapl_call_option);
    private final Position position3 = new Position(-10000, telsa_put_option);

    private final InstrumentDefinitionProvider instrumentDefinitionProvider = Mockito.mock(InstrumentDefinitionProvider.class);

    @BeforeAll
    public static void createDummyCSV() {
        try (PrintWriter writer = new PrintWriter(TEST_POSITIONS_CSV)) {

            String csvValues = "symbol,positionSize" + '\n' +
                    "AAPL,1000" +
                    '\n' +
                    "AAPL-OCT-2020-110-C,-20000" +
                    '\n' +
                    "TELSA-DEC-2020-400-P,-10000" +
                    '\n';

            writer.write(csvValues);
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    static void removeTestFile() throws IOException {
        forceDelete(new File(TEST_POSITIONS_CSV));
    }

    @Test
    public void test_PositionFactory_readsCSV() {
        when(instrumentDefinitionProvider.getInstrumentByTicker("AAPL")).thenReturn(aapl_stock);
        when(instrumentDefinitionProvider.getInstrumentByTicker("AAPL-OCT-2020-110-C")).thenReturn(aapl_call_option);
        when(instrumentDefinitionProvider.getInstrumentByTicker("TELSA-DEC-2020-400-P")).thenReturn(telsa_put_option);
        final PositionFactory positionFactory = new PositionFactory(instrumentDefinitionProvider, TEST_POSITIONS_CSV);
        final Positions actualPositions = positionFactory.getPositions(TEST_POSITIONS_CSV);
        assertEquals(actualPositions.getPositions().size(), 3);
        Map<String, Position> expectedPositions = new HashMap<>();
        expectedPositions.put(position1.getInstrument().getTicker(), position1);
        expectedPositions.put(position2.getInstrument().getTicker(), position2);
        expectedPositions.put(position3.getInstrument().getTicker(), position3);

        assertEquals(expectedPositions, actualPositions.getPositions());
    }

}