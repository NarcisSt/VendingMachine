package machine;

public class MachineFactory {
    public static VendingMachine createVendingMachine() {
        return new Machine();
    }
}