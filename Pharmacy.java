import java.util.*;

public class Pharmacy {
    private static final int max = 10;

    class Node {
        String customerName;
        int[] quantity = new int[max];
        String type = "OTC";
        int x;
        int[] menu2 = new int[max];
        double[] amount = new double[max];
        String[] medicineName = {"Paracetamol", "Saridon", "Crocin", "Benadryl", "Dolo(650)",
                "Allegra(180)", "Meftal Spas tablet", "Vomitril Tablet", "Cyclomeff Tablet", "Ibuprofen"};
        double[] Medicine = {2.00, 3.00, 1.00, 4.00, 1.00, 5.00, 7.00, 4.00, 3.00, 5.00,7.00};
        String[] symptoms = {"Fever", "Headache", "Cough", "Nausea", "Menstural Pain", "Stomach Ache"};
        double total;
        Node next;

        // Create a mapping between symptoms and medicines
        Map<String, List<String>> symptomMedicineMap;

        Node(String customerName, int x) {
            this.customerName = customerName;
            this.x = x;
            initializeSymptomMedicineMap();
        }

        private void initializeSymptomMedicineMap() {
            symptomMedicineMap = new HashMap<>();
            symptomMedicineMap.put("Fever", Arrays.asList("Paracetamol", "Crocin","Dolo(650)"));
            symptomMedicineMap.put("Headache", Arrays.asList("Saridon", "Paracetamol","Asprin"));
            symptomMedicineMap.put("Cough", Arrays.asList("Benadryl","Allegra(180)"));
            symptomMedicineMap.put("Nausea", Arrays.asList("Vomitril Tablet"));
            symptomMedicineMap.put("Menstural Pain", Arrays.asList("Meftal Spas tablet", "Cyclomeff Tablet", "Dysmen Tablet"));
            symptomMedicineMap.put("Stomach Ache", Arrays.asList("Ibuprofen","Probiotics"));
        }
    }

    void takeOrder(List<String> medicinesForSymptom) {
        Scanner input = new Scanner(System.in);
        Node temp = new Node("", 0);

        // Input order details
        System.out.println("Add Order Details");
        System.out.println("_____________________________________");
        System.out.println("Medicine Table");
        System.out.println("**************************************************************************");
        System.out.println("ID\tMedicine TYPE\tMedicine NAME\t\tMedicine Price");
        System.out.println("**************************************************************************");

        // Display medicine options for the selected symptom
        for (int i = 0; i < max; i++) {
            String medicineName = temp.medicineName[i];
            if (medicinesForSymptom.contains(medicineName)) {
                System.out.printf("%-8d  %-10s  %-24s   %.2f%n", i + 1, temp.type, medicineName, temp.Medicine[i]);
            }
        }

        System.out.println();

        // Input order details
        System.out.print("Enter Customer Name: ");
        temp.customerName = input.next();
        System.out.print("How many Medicine would you like to order from the above?: ");
        temp.x = input.nextInt();

        if (temp.x > max) {
            System.out.println("The Medicine you order exceeds the maximum amount of order!");
            return;
        }

        for (int i = 0; i < temp.x; i++) {
            System.out.print("Please enter the Medicine ID: ");
            temp.menu2[i] = input.nextInt();
            String selectedMedicineName = temp.medicineName[temp.menu2[i] - 1];
            if (medicinesForSymptom.contains(selectedMedicineName)) {
                System.out.println("Medicine Name: " + selectedMedicineName);
                System.out.print("Quantity of medicine you require?: ");
                temp.quantity[i] = input.nextInt();
                temp.amount[i] = temp.quantity[i] * temp.Medicine[temp.menu2[i] - 1];
                System.out.println("The amount You need to pay is: " + temp.amount[i] + " RM");
            } else {
                System.out.println("Invalid selection. This medicine is not associated with the selected symptom.");
            }
        }

        System.out.println("===========================================================================");
        System.out.println("Order Taken Successfully");
        System.out.println("===========================================================================");
        System.out.println("Do you wish to continue(y/n)");
        char choice2=input.next().charAt(0);
        if (choice2=='y'|| choice2=='Y')
        {
            handleSymptoms();
        }
        else{
            System.out.println("Thankyou for your order");
            System.exit(0);
        }
    }

    void handleSymptoms() {
        Scanner input = new Scanner(System.in);

        System.out.println("Symptom Table");
        System.out.println("_____________________________________");
        System.out.println("Symptom ID\tSymptom Name");
        System.out.println("_____________________________________");

        Node temp = new Node("", 0);

        for (int i = 0; i < temp.symptoms.length; i++) {
            System.out.printf("%d\t\t%s%n", i + 1, temp.symptoms[i]);
        }

        System.out.print("Enter a symptom ID: ");
        int symptomID = input.nextInt();

        if (symptomID >= 1 && symptomID <= temp.symptoms.length) {
            String selectedSymptom = temp.symptoms[symptomID - 1];
            List<String> medicinesForSymptom = temp.symptomMedicineMap.get(selectedSymptom);

            if (medicinesForSymptom != null) {
                System.out.println("Medicines associated with " + selectedSymptom + ":");
                for (String medicine : medicinesForSymptom) {
                    if (Arrays.asList(temp.medicineName).contains(medicine)) {
                        System.out.println(medicine + " (Available)");
                    } else {
                        System.out.println(medicine + " (Not Available)");
                    }
                }

                System.out.print("Do you want to place an order (y/n): ");
                char choice = input.next().charAt(0);

                if (choice == 'y' || choice == 'Y') {
                    takeOrder(medicinesForSymptom);
                }
                else if (choice=='n' || choice=='N')
                {
                    System.out.println("Do you want to keep searching for more medicines for different symptoms (y/n):");
                    char choice1 =input.next().charAt(0);
                    if (choice1 =='y' || choice1=='Y')
                    {
                        handleSymptoms();
                    }
                    else{
                        exit();

                    }
                }
            } else {
                System.out.println("No medicines associated with the selected symptom.");
            }
        }
    }


    void exit() {
        System.out.println("You choose to exit.");
        System.exit(0);

    }

    public static void main(String[] args) {
        Pharmacy pharmacy = new Pharmacy();
        Scanner input = new Scanner(System.in);
        int menu;

        do {
            System.out.println("\t\t\tPharmacy Management System");
            System.out.println("\t\t==================================================");
            System.out.println("\t\t--------------------------------------------------");
            System.out.println("\t\t||\t1. Check Symptoms\t\t\t\t ||");
            System.out.println("\t\t||\t2. Exit\t\t\t\t\t ||");
            System.out.println("\t\t--------------------------------------------------");
            System.out.print("Enter choice: ");
            menu = input.nextInt();

            switch (menu) {
                case 1:
                    pharmacy.handleSymptoms();
                    break;

                case 2:
                    pharmacy.exit();
                    break;

                default:
                    System.out.println("You entered invalid input. Re-enter the input.");
                    break;
            }
        } while (menu != 2);
        System.out.println("Thank you");
    }
}
