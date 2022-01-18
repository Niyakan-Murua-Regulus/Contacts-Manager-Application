import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactsManager {

    public static void mainMenu() throws IOException {
        System.out.println("\n1. View contacts.\n" +
                "2. Add a new contact.\n" +
                "3. Search a contact by name.\n" +
                "4. Delete an existing contact.\n" +
                "5. Exit.\n");

        int userInput = getInt(1, 5);

        switch(userInput) {
            case 1:
                viewContacts();
                break;
            case 2:
                addNewContact();
                break;
            case 3:
                searchContacts();
                break;
            case 4:
                deleteContact();
                break;
            case 5:
                System.out.println("Application is now closing. Have a good day!");
                break;
        }
    }

    //Gets user int input, checks if int is a valid input
    public static int getInt(int min, int max){
        System.out.println("Enter an option (1, 2, 3, 4 or 5):");
        Scanner sc = new Scanner(System.in);
        int input;

        try{
            input = Integer.valueOf(sc.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Error: Input is not valid!");
            return getInt(min, max);
        }
        if(input >= min && input <= max){
            return input;
        }
        else{
            System.out.println("Error: Input is not recognized!");
            return getInt(min, max);
        }
    }

    //Shows all contacts.
    public static void viewContacts() throws IOException{
        Path contactPath = Paths.get("data","contact.txt");
        List<String> contactList = Files.readAllLines(contactPath);

        //Gets the longest name and uses that to determine spacing
        int longest = 0;
        for(int i = 0; i < contactList.size(); i++){
            String fullContactInfo = contactList.get(i);
            String [] arrOfContacts = fullContactInfo.split("\\|", 2);

            String fullName = arrOfContacts[0];
            if(fullName.length() >= longest){
                longest = fullName.length();
            }
        }

        String name = "Name";
        String phoneNumber = "Phone Number";
        System.out.printf(("%-" + (longest + 1) + "s| %-12s |\n"), name, phoneNumber);

        for(int i = 0; i <= longest + 16; i++){
            System.out.print("-");
        }
        System.out.print("\n");

        //Shows all the contacts with formatting
        for (int i = 0; i < contactList.size(); i ++) {
            String fullContactInfo = contactList.get(i);
            String [] arrOfContacts = fullContactInfo.split("\\|", 2);

            name = arrOfContacts[0];
            phoneNumber = arrOfContacts[1];

            System.out.printf(("%-" + (longest + 1) + "s| %-12s |\n"), name, phoneNumber);
        }
        mainMenu();
    }

    //Creates and confirms if the directory exists,
    public static void newDirectory() throws IOException {
        String directory = "data";
        String filename = "contact.txt";

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        if (Files.notExists(dataDirectory)) {
            Files.createDirectories(dataDirectory);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }

    //Gets String
    public static String getString(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    //Gets the phone number, checks if it's a valid number, checks length and formats with dashes.
    public static String getPhoneNumber() {
        System.out.println("Enter Phone Number: ");
        Scanner sc = new Scanner(System.in);
        int number = 0;
        try {
            number = Integer.valueOf(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Phone number must contain only numbers!");
            getPhoneNumber();
        }

        String phoneNumber = Integer.toString(number);

        if(phoneNumber.length() == 7) {
            phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3);
        }

        else if(phoneNumber.length() == 10) {
            phoneNumber = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6);
        }
        else{
            System.out.println("Error: Phone number must be 7 or 10 digits long!");
            getPhoneNumber();
        }
        return phoneNumber;
    }

    //Checks the yes or no answer
    public static boolean yesNo(){
        Scanner sc = new Scanner(System.in);
        String answer = sc.nextLine();
        if(answer.contains("y") || answer.contains("Y")){
            return true;
        }
        else if(!answer.contains("y") && !answer.contains("Y") && !answer.contains("n") && answer.contains("N")){
            System.out.println("Error: Selection not recognized. Please enter y or n!");
            yesNo();
        }
        return false;
    }

    //Adds a new contact
    public static void addNewContact() throws IOException {
        //Creates a new contact object
        System.out.println("Enter First Name: ");
        String firstName = getString();
        System.out.println("Enter Last Name: ");
        String lastName = getString();
        String phoneNumber = getPhoneNumber();

        Contacts contact = new Contacts(firstName, lastName, phoneNumber);

        //checks if user already exists
        ArrayList<String> fileContents = new ArrayList<String>();
        List<String> contacts = Files.readAllLines(Paths.get("data", "contact.txt"));
        boolean alreadyExists = false;

        for (String line: contacts){
            //Checks if user input already exists as a contact
            if(line.contains(firstName + " " + lastName)){
                System.out.println("User Already Exists. Do you want to override & continue? (y/n)");

                //if user exists, asks if they want to override
                if(yesNo()){
                    System.out.println("User will be added!");
                    fileContents.add(contact.getFirstName() + " " + contact.getLastName() + "|" + contact.getNumber());
                }
                else{
                    fileContents.add(line);
                }
                alreadyExists = true;
            }
            else{
                fileContents.add(line);
            }
        }

        if(!alreadyExists){
            fileContents.add(contact.getFirstName() + " " + contact.getLastName() + "|" + contact.getNumber());
        }
        Files.write(Paths.get("data", "contact.txt"), fileContents);
        mainMenu();
    }

    //Searches the contacts by name.
    public static void searchContacts() throws IOException {
        System.out.println("Enter the name of the contact you want to search for: ");
        String contactToSearch = getString();

        boolean inDatabase = false;
        List<String> contacts = Files.readAllLines(Paths.get("data", "contact.txt"));
        for (String contact  : contacts){
            if((contact.toLowerCase()).contains(contactToSearch.toLowerCase())){
                System.out.println(contact);
                inDatabase = true;
            }
        }
        if (!inDatabase) {
            System.out.println("User could not be found in contacts.");
        }
        mainMenu();
    }

    //Deletes the contact
    public static void deleteContact() throws IOException {
        System.out.println("Enter the first name of the contact you want to delete: ");
        String contactToDelete = getString();

        System.out.println("Are you sure you want to delete " + contactToDelete + " ?");
        if(yesNo()){
            List<String> contacts = Files.readAllLines(Paths.get("data", "contact.txt"));
            List<String> newContacts = new ArrayList<>();

            for (String contact  : contacts){
                if(!contact.contains(contactToDelete)){
                    newContacts.add(contact);
                }
            }

            Files.write(Paths.get("data", "contact.txt"), newContacts);
        }
        mainMenu();
    }

    public static void main(String[] args) throws IOException {
        newDirectory();
        mainMenu();

    }
}