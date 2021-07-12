package Products.Drinks;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
/**
 * This class describes the Coca-Cola product, which has a name, price and weight.
 */
public class Coke {
    private static final String name = "Coca-Cola";
    private static final double price = 6;
    private static final double weight = 2500;
}
