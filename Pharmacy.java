import java.util.Scanner;

class MedicineType {
    private static final int max = 10;
    private Node startPtr = null;

    class Node {
        String customerName;
        int[] quantity = new int[max];
        String type = "OTC";
        int x;
        int[] menu2 = new int[max];
        double[] amount = new double[max];
        String[] medicineName = {"Probiotics", "Vitamin C(500mg)", "Acid Free C(500mg)", "Women'S Multivate", "Marino Tablet",
            "Maxi Cal Tablet", "Amino Zinc Tablet", "Burnex", "Fabuloss 5", "Royal Propollen"};
        double[] Medicine = {2.00, 3.00, 1.00, 4.00, 1.00, 5.00, 7.00, 4.00, 3.00, 5.00};
        double total;
        Node next;

        Node( String customerName, int x) {
            this.customerName = customerName;
            this.x = x;
        }
    }

    void takeOrder() {
        Scanner input = new Scanner(System.in);
        Node temp = new Node( "", 0);

        // Input order details
        System.out.println("Add Order Details");
        System.out.println("_____________________________________");
        System.out.println("**************************************************************************");
        System.out.println("DRUGS ID\tDRUGS TYPE\tDRUGS NAME\t\tDRUGS PRICE(RM)");
        System.out.println("**************************************************************************");

        // Display medicine options
        for (int i = 0; i < max; i++) {
            System.out.printf("%04d\t%s\t\t%s\t\tRM %.2f%n", i + 1, temp.type, temp.medicineName[i], temp.Medicine[i]);
        }

        System.out.println();

        // Input order details
        System.out.print("Enter Customer Name: ");
        temp.customerName = input.next();
        System.out.print("How many Medicine would you like to order (Max 10): ");
        temp.x = input.nextInt();

        if (temp.x > max) {
            System.out.println("The Medicine you order exceed the maximum amount of order!");
            return;
        }

        for (int i = 0; i < temp.x; i++) {
            System.out.print("Please enter your selection: ");
            temp.menu2[i] = input.nextInt();
            System.out.println("Medicine Name: " + temp.medicineName[temp.menu2[i] - 1]);
            System.out.print("How many medicine do you want: ");
            temp.quantity[i] = input.nextInt();
            temp.amount[i] = temp.quantity[i] * temp.Medicine[temp.menu2[i] - 1];
            System.out.println("The amount You need to pay is: " + temp.amount[i] + " RM");
        }

        System.out.println("===========================================================================");
        System.out.println("Order Taken Successfully");
        System.out.println("===========================================================================");
       
       
    }

    

    void exit() {
        System.out.println("You choose to exit.");
    }

    public static void main(String[] args) {
        MedicineType medicine = new MedicineType();
        Scanner input = new Scanner(System.in);
        int menu;

        do {
            System.out.println("\t\t\tPharmacy Management System");
            System.out.println("\t\t==================================================");
            System.out.println("\t\t--------------------------------------------------");
            System.out.println("\t\t||\t1. Take new Medicine order\t\t ||");
            System.out.println("\t\t||\t2. Exit\t\t\t\t\t ||");
            System.out.println("\t\t--------------------------------------------------");
            System.out.print("Enter choice: ");
            menu = input.nextInt();

            switch (menu) {
                case 1:
                    medicine.takeOrder();
                    break;
                
                case 2:
                    medicine.exit();
                    break;
                default:
                    System.out.println("You entered invalid input. Re-enter the input.");
                    break;
            }
        } while (menu != 2);

        System.out.println("Thank you");
    }
}
