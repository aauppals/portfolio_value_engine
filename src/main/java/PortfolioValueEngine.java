import calculations.OptionPriceCalculator;
import calculations.PortfolioValueCalculator;
import calculations.PositionFactory;
import calculations.Positions;
import marketdata.MarketDataSource;
import marketdata.PriceUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PortfolioValueEngine {
    public static void main(String[] args) {
//        PositionFactory.
        Positions positions = new Positions(null);
        ConsolePortfolioValueListener consolePortfolioValueListener = new ConsolePortfolioValueListener();
        PortfolioValueCalculator portfolioValueCalculator = new PortfolioValueCalculator(consolePortfolioValueListener, positions);
        OptionPriceCalculator optionPriceCalculator = new OptionPriceCalculator(portfolioValueCalculator);
        List<Consumer<Set<PriceUpdate>>> marketDataListeners = new ArrayList<>();
        marketDataListeners.add(portfolioValueCalculator);
        marketDataListeners.add(optionPriceCalculator);
        MarketDataSource marketDataSource = new MarketDataSource(marketDataListeners);

        //Todo: run this on a separate thread
        marketDataSource.run();

    }
}
