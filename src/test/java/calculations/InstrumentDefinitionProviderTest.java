package calculations;

import instrument.Instrument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.SQLException;

public class InstrumentDefinitionProviderTest {

    @Test
    public void WHEN_DB_is_created_THEN_DB_file_is_created() throws ClassNotFoundException {
        InstrumentDefinitionProvider.createNewDatabase("Test.db");
        File file = new File("C:/Users/N B C/Desktop/portfolio_value_engine/Test.db");
        assertTrue(file.exists());
    }

    @Test
    public void WHEN_table_is_created_and_records_inserted_THEN_correct_entries_are_made() {
        InstrumentDefinitionProvider.deleteAllStocks();
        InstrumentDefinitionProvider.deleteAllOptions();
        InstrumentDefinitionProvider.insertStocks("AAPL", 0.01);
        InstrumentDefinitionProvider.insertStocks("TELSA", 0.01);
        InstrumentDefinitionProvider.insertOptions("AAPL", "OCT-2020", 110, "C", 0.02, 0.01);
        InstrumentDefinitionProvider.insertOptions("AAPL", "OCT-2020", 110, "P", 0.02, 0.01);
        InstrumentDefinitionProvider.insertOptions("TELSA", "OCT-2020", 400, "C", 0.02, 0.01);
        InstrumentDefinitionProvider.insertOptions("TELSA", "OCT-2020", 400, "P", 0.02, 0.01);

        InstrumentDefinitionProvider.getAllStocks();
        InstrumentDefinitionProvider.getAllOptions();

    }

}
