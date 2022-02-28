package calculations;

import instrument.Instrument;
import instrument.Option;
import instrument.OptionType;
import instrument.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PositionFactoryTest {
    //TODO: user before to create dummy positions csv in tmp dir
    private final InstantParser instantParserMock = mock(InstantParser.class);
    private final Instant time = new InstantParser().getInstant("OCT", "2020");
    private final Stock stock1 = new Stock("AAPL");
    private final Stock stock2 = new Stock("TESLA");
    private final Instrument instrument2 = new Option("AAPL", time, 110, OptionType.CALL, stock1);
    private final Instrument instrument3 = new Option("TELSA", time, 400, OptionType.PUT, stock2);
    private final Position position1 = new Position(1000, stock1);
    private final Position position2 = new Position(-20000, instrument2);
    private final Position position3 = new Position(-10000, instrument3);

    @BeforeAll
    public static void createDummyCSV() {
        try (PrintWriter writer = new PrintWriter(("Positions.csv"))) {

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

    @Test
    public void test_PositionFactory_readsCSV() {
        when(this.instantParserMock.getInstant(anyString(), anyString())).thenReturn(time);
        final PositionFactory positionFactory = new PositionFactory();
        final Positions actualPositions = positionFactory.getPositions();
        assertEquals(actualPositions.getPositions().size(), 3);
        Set<Position> expectedPositions = new HashSet<>();
        expectedPositions.add(position1);
        expectedPositions.add(position2);
        expectedPositions.add(position3);
        assertEquals(expectedPositions, actualPositions.getPositions());
    }

}