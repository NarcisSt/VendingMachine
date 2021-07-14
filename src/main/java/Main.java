import Machine.Machine;
import Machine.MachineFactory;
import Machine.VendingMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Machine vm = new Machine();
        vm.welcomeMessage();
        vm.printOptions();
        vm.executeOption("List");

    }
}
