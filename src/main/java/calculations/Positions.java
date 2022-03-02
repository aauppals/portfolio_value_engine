package calculations;

import javax.annotation.Nullable;
import java.util.*;

public class Positions {

    private final Map<String, Position> positions = new HashMap<>();

    public Positions(final Set<Position> positionSet) {
        positionSet.forEach(p -> this.positions.put(p.getInstrument().getTicker(), p));
    }

    @Nullable
    public Position getPosition(String ticker) {
        return positions.get(ticker);
    }

    public Map<String, Position> getPositions() {
        return positions;
    }
}
