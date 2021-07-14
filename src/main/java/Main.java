import machine.Machine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Machine vm = new Machine();
        vm.welcomeMessage();
        vm.printOptions();
        vm.executeOption();

    }
}
