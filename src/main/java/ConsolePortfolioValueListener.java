import calculations.PortfolioValue;

import java.util.Set;
import java.util.function.Consumer;

public class ConsolePortfolioValueListener implements Consumer<Set<PortfolioValue>> {
    @Override
    public void accept(Set<PortfolioValue> portfolioValueSet) {
        //Todo: pretty print
        System.out.println("final pretty print");

    }
}
