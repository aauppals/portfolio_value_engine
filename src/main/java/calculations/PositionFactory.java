package calculations;

import instrument.Instrument;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PositionFactory {
    private final InstrumentDefinitionProvider instrumentDefinitionProvider;
    private final String positionFile;

    public PositionFactory(InstrumentDefinitionProvider instrumentDefinitionProvider, String positionFile) {
        this.instrumentDefinitionProvider = instrumentDefinitionProvider;
        this.positionFile = positionFile;
    }


    public Positions getPositions(String positionFile) {
        return new Positions(readPositions(positionFile));
    }

    private Set<Position> readPositions(String positionFile) {
        HashSet<Position> positionSet = new HashSet<>();
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(positionFile));
            br.readLine();
            while ((line = br.readLine()) != null) {
                final String[] splitData = line.split(",");
                final String ticker = splitData[0];
                final long amount = Long.parseLong(splitData[1]);
                Instrument instrumentByTicker = instrumentDefinitionProvider.getInstrumentByTicker(ticker);
                positionSet.add(new Position(amount, instrumentByTicker));
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
}
