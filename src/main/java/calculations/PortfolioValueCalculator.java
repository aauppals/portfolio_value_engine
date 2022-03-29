package calculations;

import infra.UnaryPubSub;
import marketdata.PriceUpdate;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class PortfolioValueCalculator extends UnaryPubSub<Set<PortfolioValue>> implements Consumer<Set<PriceUpdate>> {
    private final Positions positions;

    public PortfolioValueCalculator(Consumer<Set<PortfolioValue>> listener, Positions positions) {
        super(Collections.singletonList(listener));
        this.positions = positions;
    }

    @Override
    public void accept(Set<PriceUpdate> priceUpdates) {
        final Set<PortfolioValue> portfolioValueSet = new LinkedHashSet<>();
        for (PriceUpdate priceUpdate : priceUpdates) {
            portfolioValueSet.add(new PortfolioValue(requireNonNull(positions.getPosition(priceUpdate.getTicker())).getInstrument(),
                    priceUpdate.getPrice() * requireNonNull(positions.getPosition(priceUpdate.getTicker())).getAmount(), positions, priceUpdate.getPrice()));
        }
        update(portfolioValueSet);
    }
}
