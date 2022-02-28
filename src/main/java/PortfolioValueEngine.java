import calculations.*;
import marketdata.MarketDataSource;
import marketdata.PriceUpdate;

import java.util.*;
import java.util.function.Consumer;

public class PortfolioValueEngine {
    public static void main(String[] args) throws ClassNotFoundException {

        InstrumentDefinitionProvider.createNewDatabase("Instruments.db");
        InstrumentDefinitionProvider.deleteAllStocks("Instruments.db");
        InstrumentDefinitionProvider.deleteAllOptions("Instruments.db");
        InstrumentDefinitionProvider.createNewTable("Instruments.db");
        InstrumentDefinitionProvider.insertStocks("Instruments.db", "AAPL", 200, 0.5, 0.5);
        InstrumentDefinitionProvider.insertStocks("Instruments.db", "TELSA", 430, 0.75, 0.75);
        InstrumentDefinitionProvider.getAllStocks("Instruments.db");
//      CalcObject calcObject = InstrumentDefinitionProvider.getStockByTicker("Instruments.db","AAPL");
//      System.out.println(calcObject.toString());

        final PositionFactory positionFactory = new PositionFactory();
        Positions positions = positionFactory.getPositions();

        ConsolePortfolioValueListener consolePortfolioValueListener = new ConsolePortfolioValueListener();
        PortfolioValueCalculator portfolioValueCalculator = new PortfolioValueCalculator(consolePortfolioValueListener, positions);
        OptionPriceCalculator optionPriceCalculator = new OptionPriceCalculator(portfolioValueCalculator);
        List<Consumer<Set<PriceUpdate>>> marketDataListeners = new ArrayList<>();
        marketDataListeners.add(portfolioValueCalculator);
        marketDataListeners.add(optionPriceCalculator);
        MarketDataSource marketDataSource = new MarketDataSource(marketDataListeners, positions);


        //Todo: run this on a separate thread
        marketDataSource.run();

    }
}
