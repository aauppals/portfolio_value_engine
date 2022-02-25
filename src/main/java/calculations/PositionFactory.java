package calculations;

import instrument.Instrument;
import instrument.Option;
import instrument.OptionType;
import instrument.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Double.parseDouble;

public class PositionFactory {

    private static final String OPTION_IDENTIFIER = "-";
    public static final int OPTION_TICKER_DASHES = 4;
    private final InstantParser instantParser;

    public PositionFactory(InstantParser instantParser) {
        this.instantParser = instantParser;
    }

    public Positions getPositions() {
        return new Positions(readPositions());
    }


    private Set<Position> readPositions() {
        HashSet<Position> positionSet = new HashSet<>();
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader("Positions.csv")); //TOdo: inject file path & in tests make file in tmp
            br.readLine();
            while ((line = br.readLine()) != null) {
                final String[] splitData = line.split(",");
                final String ticker = splitData[0];
                final long amount = Long.parseLong(splitData[1]);
                positionSet.add(new Position(amount, getInstrument(ticker)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException fileException) {
                fileException.printStackTrace();
            }
        }
        return positionSet;
    }

    private Instrument getInstrument(String tickerText) {
        if (isTickerOption(tickerText)) {
            String[] split = tickerText.split(OPTION_IDENTIFIER);
            final String name = split[0];
            final Instant maturityDate = instantParser.getInstant(split[1], split[2]);
            final double strikePrice = parseDouble(split[3]);
            final OptionType optionType = OptionType.fromText(split[4]);
            return new Option(name, maturityDate, strikePrice, optionType);
        }
        return new Stock(tickerText);
    }

    private boolean isTickerOption(String tickerText) {
        return tickerText.contains(OPTION_IDENTIFIER)
                && getNumberOfOccurrences(OPTION_IDENTIFIER, tickerText) == OPTION_TICKER_DASHES;
    }

    private int getNumberOfOccurrences(final String substring, final String string) {
        return string.length() - string.replace(substring, "").length();
    }

}
