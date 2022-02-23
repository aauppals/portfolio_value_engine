package calculations;

import infra.UnaryPubSub;
import marketdata.PriceUpdate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class PortfolioValueCalculator extends UnaryPubSub<Set<PortfolioValue>> implements Consumer<Set<PriceUpdate>> {
    private final Set<PortfolioValue> portfolioValues = new HashSet<>();
    private final Positions positions;

    public PortfolioValueCalculator(Consumer<Set<PortfolioValue>> listener, Positions positions) {
        super(Collections.singletonList(listener));
        this.positions = positions;
    }

    @Override
    public void accept(Set<PriceUpdate> priceUpdates) {
        //Todo: update map with new value based on positions, remember value=price*qty
        System.out.println("new portfolio prices update");
        update(portfolioValues);
    }
}
