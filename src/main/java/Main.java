import machine.Machine;

public class Main {
    public static void main(String[] args) {
        Machine vm = new Machine();
        vm.welcomeMessage();
        vm.printOptions();
        vm.executeOption();

    }
}
