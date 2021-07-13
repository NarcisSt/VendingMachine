import Machine.Machine;
import Machine.MachineFactory;
import Machine.VendingMachine;

public class Main {
    public static void main(String[] args) {
        Machine vm = new Machine();
        vm.welcomeMessage();
        vm.printOptions();

    }
}
