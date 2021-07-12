package Products.Food;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
/**
 * This class describes the Sandwich product, which has a name, price and weight.
 */
public class Sandwich {
    private static final String name = "Sandwich";
    private static final double price = 13;
    private static final double weight = 150;
}
