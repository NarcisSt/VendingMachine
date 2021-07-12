package Products.Chips;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
/**
 * This class describes the Nutline product, which has a name, price and weight.
 */
public class Nutline {
    private static final String name = "Nutline";
    private static final double price = 2.5;
    private static final double weight = 70;
}
