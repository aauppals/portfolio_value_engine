package calculations;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.collect.ImmutableSet;
import instrument.Option;
import javafx.geometry.Pos;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Positions {

    private final Set<Position> positions;

    public Positions(final Set<Position> positions) {
        this.positions = positions;//ImmutableSet.copyOf(positions);
    }

    public Set<Position> getPositions() {
        return positions;
    }
}
