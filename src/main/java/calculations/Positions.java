package calculations;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.collect.ImmutableSet;
import javafx.geometry.Pos;

import java.util.HashSet;
import java.util.Set;

public class Positions {


    private final Set<Position> positions;

    public Positions() {
        positions = ImmutableSet.copyOf(readPositions());
    }


    private Set<Position> readPositions() {
        //Todo: read csv and populate the returned hash set
        HashSet<Position> positionSet = new HashSet<>();
        BufferedReader br = null;

        try {
            String line;
            br = new BufferedReader(new FileReader("Positions.csv"));
            br.readLine();
            line = null; // to skip headers
            while ((line = br.readLine()) != null) {
                System.out.println("Raw Position data: " + line);

                String[] splitData = line.split("\\s*,\\s*");
                for (int i = 0; i < splitData.length; i++) {
                    if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {

                       // positionSet.add((splitData[i].trim()));
                    }
                }
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
        System.out.println(positionSet);
        return new HashSet<>();
    }

    public Set<Position> getPositions() {
        return positions;
    }
}
