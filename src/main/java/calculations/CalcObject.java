package calculations;

import java.util.Objects;

public class CalcObject {
    private final double price;
    private final double expectedReturn;
    private final double volatility;

    public CalcObject(double price, double expectedReturn, double volatility) {
        this.price = price;
        this.expectedReturn = expectedReturn;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return "CalcObject{" +
                "price=" + price +
                ", expectedReturn=" + expectedReturn +
                ", volatility=" + volatility +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalcObject)) return false;
        CalcObject that = (CalcObject) o;
        return Double.compare(that.price, price) == 0 && Double.compare(that.expectedReturn, expectedReturn) == 0 && Double.compare(that.volatility, volatility) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, expectedReturn, volatility);
    }

    public double getPrice() {
        return price;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    public double getVolatility() {
        return volatility;
    }
}
