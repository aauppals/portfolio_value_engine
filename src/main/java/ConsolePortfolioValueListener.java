import calculations.PortfolioValue;
import calculations.Position;
import instrument.Instrument;

import java.util.Set;
import java.util.function.Consumer;

public class ConsolePortfolioValueListener implements Consumer<Set<PortfolioValue>> {
    @Override
    public void accept(Set<PortfolioValue> portfolioValueSet) {
        //Todo: pretty print
        System.out.println("Portfolio Value updates:");
        System.out.printf("%-30.40s  %-30.40s %-30.40s %-30.40s%n", "symbol", "price", "qty", "Value");
        double portfolioTotalValue = 0;
        for (PortfolioValue portfolioValue : portfolioValueSet) {
            String ticker = portfolioValue.getInstrument().getTicker();
            double value = portfolioValue.getValue();
            Instrument instrument = portfolioValue.getInstrument();
            Position position = portfolioValue.getPositions().getPosition(ticker);
            long amount = position.getAmount();
            double price = portfolioValue.getPrice();
            System.out.printf("%-30.40s  %-30.40s %-30.40s %-30.40s%n", instrument.getTicker(),
                    price, amount, value);
            portfolioTotalValue += portfolioValue.getValue();
        }
        System.out.println("Total portfolio value: " + portfolioTotalValue);

    }
}
