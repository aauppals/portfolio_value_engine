package calculations;

import instrument.Option;
import instrument.OptionType;
import instrument.Stock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

import static org.apache.commons.io.FileUtils.forceDelete;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class InstrumentDefinitionProviderTest {
    private static InstrumentDefinitionProvider instrumentDefinitionProvider;

    @BeforeAll
    @SuppressWarnings("unused")
    static void createDataBase() throws IOException {
        instrumentDefinitionProvider = new InstrumentDefinitionProvider();
    }

    @AfterAll
    static void closeDatabaseConnection() throws SQLException, IOException {
        instrumentDefinitionProvider.closeConnection();
        forceDelete(new File(InstrumentDefinitionProvider.INSTRUMENTS_DB_FILE_NAME));
    }

    @Test
    public void WHEN_table_is_created_and_records_inserted_THEN_correct_entries_queryable() {
        instrumentDefinitionProvider.createInstrumentTables();
        Stock expectedStock = new Stock("AAPL", 0.5);
        instrumentDefinitionProvider.insertStocks("AAPL", 0.5);
        instrumentDefinitionProvider.insertStocks("TELSA", 0.75);

        Stock actualStock = (Stock) instrumentDefinitionProvider.getInstrumentByTicker("AAPL");
        assertEquals(expectedStock, actualStock);

        Option expectedOption = new Option("AAPL-OCT-2025-110-C", Instant.parse("2022-04-01T13:44:14.504Z"), 110, OptionType.CALL, expectedStock);
        instrumentDefinitionProvider.insertOptions("AAPL-OCT-2025-110-C", "2022-04-01T13:44:14.504Z", 110, OptionType.CALL, "AAPL");
        Option actualOption = (Option) instrumentDefinitionProvider.getInstrumentByTicker("AAPL-OCT-2025-110-C");
        assertEquals(expectedOption, actualOption);
    }
}
