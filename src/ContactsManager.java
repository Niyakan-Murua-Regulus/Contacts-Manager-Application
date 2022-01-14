import java.util.ArrayList;
import java.util.Scanner;

public class ContactsManager {

    private ArrayList<Contacts> contactsRecords = new ArrayList<Contacts>();

    public ContactsManager() {

        Scanner scanner = new Scanner(System.in);
        String menuOptions;


        do {
            System.out.println("Enter an option (1, 2, 3, 4, or 5):");
            System.out.println("1. View Contacts");
            System.out.println("2. Add a new contact");
            System.out.println("3. Search a contact by name");
            System.out.println("4. Delete an existing contact");
            System.out.println("5. Exit");

            menuOptions = scanner.nextLine();

            if (menuOptions.equals("1")) {
                viewContacts();

            } else if (menuOptions.equals("2")) {
                addContact();

            } else if (menuOptions.equals("3")) {
                searchContact();

            } else if (menuOptions.equals("4")) {
                deleteContact();
            }

        } while (menuOptions.equals("5") == false);

    }

    private void viewContacts() {

    }

    private void addContact() {

    }

    private void searchContact() {

    }

    private void deleteContact() {

    }




    public static void main(String[] args) {

        new ContactsManager();






    }




}
