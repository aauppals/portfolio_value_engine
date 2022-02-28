package calculations;

import org.junit.jupiter.api.Test;


public class OptionPriceCalculatorTest {

    @Test
    public void WHEN_new_underlying_prices_supplied_THEN_correct_option_price_returned() {
        OptionPriceCalculator optionPriceCalculator = new OptionPriceCalculator(priceUpdates -> {
            System.out.println("Actual option prices: " + priceUpdates);
            //TODO: assert correct prices
        });
        //TODO: call with test input
        optionPriceCalculator.accept(null);
    }
}
