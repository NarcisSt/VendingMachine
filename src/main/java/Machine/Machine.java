package Machine;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Machine {
    private static Machine INSTANCE;
    private static final String name = "Continental Vending Machine";
    private double stores = 1000;
    private int stock = 50;

    private Machine() {

    }

    public static Machine getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Machine();
        }

        return INSTANCE;
    }

}
