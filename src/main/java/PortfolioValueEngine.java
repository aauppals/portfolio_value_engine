import calculations.*;
import instrument.OptionType;
import marketdata.MarketDataSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

public class PortfolioValueEngine {
    public static void main(String[] args) throws IOException {
        final InstrumentDefinitionProvider instrumentDefinitionProvider = prepareDataBase();
        final PositionFactory positionFactory = new PositionFactory(instrumentDefinitionProvider, "Positions.csv");
        final Positions positions = positionFactory.getPositions("Positions.csv");

        final ConsolePortfolioValueListener consolePortfolioValueListener = new ConsolePortfolioValueListener();
        final PortfolioValueCalculator portfolioValueCalculator = new PortfolioValueCalculator(consolePortfolioValueListener, positions);
        final PriceCalculator priceCalculator = new PriceCalculator(portfolioValueCalculator, instrumentDefinitionProvider, positions);
        final MarketDataSource marketDataSource = new MarketDataSource(Collections.singletonList(priceCalculator), positions, instrumentDefinitionProvider);


        //Todo: run this on a separate thread
        marketDataSource.start();

    }

    @NotNull
    private static InstrumentDefinitionProvider prepareDataBase() throws IOException {
        InstrumentDefinitionProvider instrumentDefinitionProvider = new InstrumentDefinitionProvider();
        instrumentDefinitionProvider.createInstrumentTables();
        instrumentDefinitionProvider.insertStocks("AAPL", 0.5);
        instrumentDefinitionProvider.insertStocks("TELSA", 0.75);
        instrumentDefinitionProvider.insertOptions("AAPL-OCT-2025-110-C", "2025-10-01T13:44:14.504Z", 110, OptionType.CALL, "AAPL");
        instrumentDefinitionProvider.insertOptions("AAPL-OCT-2025-110-P", "2025-10-01T13:44:14.504Z", 110, OptionType.PUT, "AAPL");
        instrumentDefinitionProvider.insertOptions("TELSA-NOV-2026-400-C", "2026-11-01T13:44:14.504Z", 400, OptionType.CALL, "TELSA");
        instrumentDefinitionProvider.insertOptions("TELSA-NOV-2026-400-P", "2026-11-01T13:44:14.504Z", 400, OptionType.PUT, "TELSA");
        return instrumentDefinitionProvider;
    }
}
