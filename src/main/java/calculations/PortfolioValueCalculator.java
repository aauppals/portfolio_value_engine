package calculations;

import infra.UnaryPubSub;
import marketdata.PriceUpdate;

import java.util.Collections;
import java.util.HashSet;
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
        System.out.println("new portfolio prices update");
        update(priceUpdates.stream().map(priceUpdate ->
                new PortfolioValue(requireNonNull(positions.getPosition(priceUpdate.getTicker())).getInstrument(),
                        priceUpdate.getPrice() * requireNonNull(positions.getPosition(priceUpdate.getTicker())).getAmount())).collect(Collectors.toSet()));
    }
}
