import java.util.*;

public class Pharmacy {
    private static final int max = 10;

    // Define a Node class to represent a customer and their order
    class Node {
        String customerName;
        int[] quantity = new int[max];
        String type = "OTC";
        int x;
        int[] menu2 = new int[max];
        double[] amount = new double[max];
        String[] medicineName = {"Paracetamol", "Saridon", "Crocin", "Benadryl", "Dolo(650)",
                "Allegra(180)", "Meftal Spas tablet", "Vomitril Tablet", "Cyclomeff Tablet", "Ibuprofen"};
        double[] Medicine = {2.00, 3.00, 1.00, 4.00, 1.00, 5.00, 7.00, 4.00, 3.00, 5.00, 7.00};
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
            symptomMedicineMap.put("Fever", Arrays.asList("Paracetamol", "Crocin", "Dolo(650)"));
            symptomMedicineMap.put("Headache", Arrays.asList("Saridon", "Paracetamol", "Asprin"));
            symptomMedicineMap.put("Cough", Arrays.asList("Benadryl", "Allegra(180)"));
            symptomMedicineMap.put("Nausea", Arrays.asList("Vomitril Tablet"));
            symptomMedicineMap.put("Menstural Pain", Arrays.asList("Meftal Spas tablet", "Cyclomeff Tablet", "Dysmen Tablet"));
            symptomMedicineMap.put("Stomach Ache", Arrays.asList("Ibuprofen", "Probiotics"));
        }
    }
    public int binarySearch(String[] arr, String x) {
        Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int res = x.compareToIgnoreCase(arr[m]);

            if (res == 0) {
                return m;
            } else if (res > 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }

    // Method to view all medicines
    void viewAllMedicines() {
        System.out.println("Medicine List");
        System.out.println("*********************************");
        System.out.println("Medicine Name\t\tMedicine Price");
        System.out.println("*********************************");

        Node temp = new Node("", 0);
        for (int i = 0; i < max; i++) {
            System.out.printf("%-20s  %.2f%n", temp.medicineName[i], temp.Medicine[i]);
        }

        // Input for searching a medicine
        System.out.print("Enter the name of the medicine to search: ");
        Scanner input = new Scanner(System.in);
        String searchMedicineName = input.nextLine();

        // Search for the medicine using binary search
        int searchResult = binarySearch(temp.medicineName, searchMedicineName);

        if (searchResult != -1) {
            System.out.println("Medicine found at index: " + searchResult);
        } else
        {
            System.out.println("Medicine not found in the list.");
        }

        System.out.print("Press 'B' to go back to the main menu OR Press 'A' to order: ");
        char choice = input.next().charAt(0);
        if (choice == 'B' || choice == 'b') {
            choice_inp();
        } else if (choice == 'A' || choice == 'a') {
            List<String> allMedicines = Arrays.asList(temp.medicineName);
            takeOrder(allMedicines);
        } else {
            exit();
        }
    }

    // take orders based on selected symptoms

    void takeOrder(List<String> medicinesForSymptom) {
        Scanner input = new Scanner(System.in);
        Node temp = new Node("", 0);

        if (medicinesForSymptom==null)
        {
            System.out.println("_____________________________________");
            System.out.println("order from the above medicine list");
        }
        else{

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
    }



        // Input order details
        System.out.print("Enter Customer Name: ");
        temp.customerName = input.nextLine();
        System.out.print("How many Medicines would you like to order from the above?: ");
        temp.x = input.nextInt();

        if (temp.x > max) {
            System.out.println("The number of medicines you ordered exceeds the maximum limit!");
            return;
        }

        for (int i = 0; i < temp.x; i++) {
            System.out.print("Please enter the Medicine ID: ");
            temp.menu2[i] = input.nextInt();

            // Ensure that the selected medicine ID is within the valid range
            if (temp.menu2[i] >= 1 && temp.menu2[i] <= max) {
                String selectedMedicineName = temp.medicineName[temp.menu2[i] - 1];

                if (medicinesForSymptom.contains(selectedMedicineName)) {
                    System.out.println("Medicine Name: " + selectedMedicineName);
                    System.out.print("Quantity of medicine you require?: ");
                    temp.quantity[i] = input.nextInt();
                    temp.amount[i] = temp.quantity[i] * temp.Medicine[temp.menu2[i] - 1];
                    System.out.println("The amount You need to pay is: " + temp.amount[i]);
                } else {
                    System.out.println("Invalid selection. This medicine is not associated with the selected symptom.");
                    return;
                }
            } else {
                System.out.println("Invalid Medicine ID. Please select a valid ID.");
            }
        }
        // Display "Order taken successfully" message
        System.out.println("_____________________________________");
        System.out.println("Order taken successfully");
        System.out.println("____________________________________");
        System.out.println("Do you wish to continue (y/n)");
        char choice2=input.next().charAt(0);
        if (choice2 =='y' || choice2=='Y')
        {
            choice_inp();
        }
        else
        {
            exit();
        }
    }



    // Method to handle symptoms
    void handleSymptoms() {
        Scanner input = new Scanner(System.in);

        System.out.println("Symptom Table");
        System.out.println("____________________________________");
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
                } else if (choice == 'n' || choice == 'N') {
                    System.out.println("Do you want to keep searching for more medicines for different symptoms (y/n):");
                    char choice1 = input.next().charAt(0);
                    if (choice1 == 'y' || choice1 == 'Y') {
                        handleSymptoms();
                    } else {
                        exit();
                    }
                }
            }
            else
            {
                System.out.println("No medicines associated with the selected symptom.");
            }
        }
    }



    // Method to exit the program
    void exit() {
        System.out.println("Thankyou for using the Pharmacy Management System.");
        System.exit(0);
    }

<<<<<<< HEAD
=======
    // Binary search method to find the index of a medicine in the list
    public int binarySearch(String[] arr, String x) {
<<<<<<< HEAD
        int l = 0, r = arr.length + 1 ;
=======
        Arrays.sort(arr); // Sort the array to ensure binary search works correctly
        int l = 0, r = arr.length - 1;
>>>>>>> 6a4f01d46aeea93779b255acd9aa1ee87a6cf63f
        while (l <= r) {
            int m = l + (r - l) / 2;
            int res = x.compareTo(arr[m]);

            if (res == 0) {
                return m;
            } else if (res > 0) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return -1;
    }
>>>>>>> 3e101b13991a874fa262a2076e5476dcf9376c82


    public static void main(String[] args) {
        choice_inp();

    }

    public static void  choice_inp() {
        Pharmacy pharmacy = new Pharmacy();
        Scanner input = new Scanner(System.in);
        int menu;

        do {
            System.out.println("\t\t\tPharmacy Management System");
            System.out.println("\t\t==================================================");
            System.out.println("\t\t--------------------------------------------------");
            System.out.println("\t\t||\t1. Check Symptoms\t\t\t\t ||");
            System.out.println("\t\t||\t2. View all the medicines\t\t\t ||");
            System.out.println("\t\t||\t3. Exit\t\t\t\t\t ||");
            System.out.println("\t\t--------------------------------------------------");
            System.out.print("Enter choice: ");
            menu = input.nextInt();

            switch (menu) {
                case 1:
                    pharmacy.handleSymptoms();
                    break;

                case 2:
                    pharmacy.viewAllMedicines();
                    break;

                case 3:
                    pharmacy.exit();
                    break;

                default:
                    System.out.println("You entered an invalid input. Re-enter the input.");

                    break;
            }
        } while (menu != 3);
        System.out.println("Thank you for using the Pharmacy Management System.");

    }
}
