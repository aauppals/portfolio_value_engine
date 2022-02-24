package calculations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PositionFactoryTest {
    //TODO: user before to create dummy positions csv in tmp dir

    @BeforeAll
    public static void createDummyCSV() {

        try (PrintWriter writer = new PrintWriter(("/tmp/test.csv"))) {

            StringBuilder sb = new StringBuilder();

            sb.append("AAPL,1000");
            sb.append('\n');

            sb.append("AAPL-OCT-2020-110-C,-20000");
            sb.append('\n');

            sb.append("AAPL-OCT-2020-110-P,20000");
            sb.append('\n');

            writer.write(sb.toString());

            System.out.println("Wrote to CSV");
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test // test 1
    public void test_PositionFactory_readsCSV() {
//        PositionFactory pf = new PositionFactory();


    }

}