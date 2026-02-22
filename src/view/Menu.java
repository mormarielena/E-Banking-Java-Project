package view;

import enums.AccountType;
import enums.Occupation;
import exceptions.CustomExceptions;
import model.Account;
import model.User;
import service.Bank;
import service.Statistics;

import java.io.Serializable;
import java.util.Scanner;
import java.io.IOException;

public class Menu implements Serializable {
    private Bank bank;
    private transient Scanner scanner;

    public Menu(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("\n--------- E-Banking Menu ---------");
        System.out.println("1. Create account");
        System.out.println("2. View account details");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Delete account");
        System.out.println("7. Statistics");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 0: viewAccounts(); break;
            case 1: createAccount(); break;
            case 2: viewAccountDetails(); break;
            case 3: deposit(); break;
            case 4: withdraw(); break;
            case 5: transfer(); break;
            case 6: deleteAccount(); break;
            case 7: displayStatisticsMenu(); break;
            case 8: System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
                displayMenu();
        }
    }

    private void displayStatisticsMenu() {
        Statistics statistics = new Statistics(this.bank.getAccounts());
        System.out.println("\n--- Statistics Menu ---");
        System.out.println("1. Average Balance");
        System.out.println("2. Number of accounts by type");
        System.out.println("3. Number of users by occupation");
        System.out.println("4. Number of users by age range");
        System.out.println("5. Generate Report (.txt)");
        System.out.println("6. Back");

        int response = scanner.nextInt();
        scanner.nextLine();

        switch (response) {
            case 1:
                System.out.printf("Average Balance: %.2f\n", statistics.calculateAverageBalance());
                break;
            case 2:
                System.out.println("Select account type (1 - SAVINGS | 2 - SALARY | 3 - FIXED DEPOSIT):");
                int typeNo = scanner.nextInt();
                scanner.nextLine();
                if (typeNo == 1) System.out.println("Count: " + statistics.accountsByType(AccountType.SAVINGS));
                else if (typeNo == 2) System.out.println("Count: " + statistics.accountsByType(AccountType.SALARY));
                else if (typeNo == 3) System.out.println("Count: " + statistics.accountsByType(AccountType.FIXED_DEPOSIT));
                break;
            case 3:
                System.out.println("Select occupation (1 - STUDENT | 2 - EMPLOYEE | 3 - RETIRED):");
                int occNo = scanner.nextInt();
                scanner.nextLine();
                if (occNo == 1) System.out.println("Count: " + statistics.countUsersByOccupation(Occupation.STUDENT));
                else if (occNo == 2) System.out.println("Count: " + statistics.countUsersByOccupation(Occupation.EMPLOYEE));
                else if (occNo == 3) System.out.println("Count: " + statistics.countUsersByOccupation(Occupation.RETIRED));
                break;
            case 4:
                System.out.print("Enter minimum age: ");
                int min = scanner.nextInt();
                System.out.print("Enter maximum age: ");
                int max = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Users in range: " + statistics.countUsersByAgeRange(min, max));
                break;
            case 5:
                System.out.print("Enter filename for report (e.g., report.txt): ");
                String fileName = scanner.nextLine();
                try {
                    statistics.generateStatisticsReport(fileName);
                    System.out.println("Report successfully generated.");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                break;
            case 6:
                displayMenu();
                return;
        }
        backStatistics();
    }

    public void createAccount() {
        try {
            System.out.print("Enter ID (13 digits): ");
            String ID = scanner.nextLine();
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Occupation (1-Student, 2-Employee, 3-Retired): ");
            int occ = scanner.nextInt();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            Occupation occupation = (occ == 1) ? Occupation.STUDENT : (occ == 2) ? Occupation.EMPLOYEE : Occupation.RETIRED;

            int accountNo = (int)(Math.random() * 9000 + 1000); // 4 digit random

            System.out.print("Type (1-Savings, 2-Salary, 3-Fixed): ");
            int type = scanner.nextInt();
            scanner.nextLine();
            AccountType accountType = (type == 1) ? AccountType.SAVINGS : (type == 2) ? AccountType.SALARY : AccountType.FIXED_DEPOSIT;

            User user = new User(ID, firstName, lastName, occupation, age);
            bank.createAccount(user, accountNo, accountType);
            System.out.println("Account created! Number: " + accountNo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        backMenu();
    }

    // Simplified helper methods
    public void backMenu() {
        System.out.println("Press Enter to return to menu...");
        scanner.nextLine();
        displayMenu();
    }

    public void backStatistics() {
        System.out.println("Press Enter to return to statistics...");
        scanner.nextLine();
        displayStatisticsMenu();
    }

    public void deposit() {
        System.out.print("Account Number: ");
        int num = scanner.nextInt();
        try {
            Account acc = bank.getAccountByNumber(num);
            System.out.print("Amount: ");
            double amt = scanner.nextDouble();
            scanner.nextLine();
            acc.deposit(amt);
            bank.saveDataToFile();
            System.out.println("Deposit successful.");
        } catch (Exception e) { System.out.println(e.getMessage()); scanner.nextLine(); }
        backMenu();
    }

    public void withdraw() {
        System.out.print("Account Number: ");
        int num = scanner.nextInt();
        try {
            Account acc = bank.getAccountByNumber(num);
            System.out.print("Amount: ");
            double amt = scanner.nextDouble();
            scanner.nextLine();
            acc.withdrawal(amt);
            bank.saveDataToFile();
            System.out.println("Withdrawal successful.");
        } catch (Exception e) { System.out.println(e.getMessage()); scanner.nextLine(); }
        backMenu();
    }

    public void transfer() {
        System.out.print("From Account Number: ");
        int from = scanner.nextInt();
        System.out.print("To Account Number: ");
        int to = scanner.nextInt();
        try {
            Account sender = bank.getAccountByNumber(from);
            Account receiver = bank.getAccountByNumber(to);
            System.out.print("Amount: ");
            double amt = scanner.nextDouble();
            scanner.nextLine();
            sender.transfer(receiver, amt);
            bank.saveDataToFile();
            System.out.println("Transfer complete.");
        } catch (Exception e) { System.out.println(e.getMessage()); scanner.nextLine(); }
        backMenu();
    }

    public void viewAccountDetails() {
        System.out.print("Account Number: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        try {
            bank.getAccountByNumber(num).viewAccountDetails();
        } catch (Exception e) { System.out.println(e.getMessage()); }
        backMenu();
    }

    public void deleteAccount() {
        System.out.print("Account Number to delete: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        bank.deleteAccount(num);
        System.out.println("Action completed.");
        backMenu();
    }

    public void viewAccounts() {
        System.out.println("Listing all accounts:");
        for(Account a : bank.getAccounts()) {
            System.out.println("No: " + a.getAccountNo() + " | Owner: " + a.getUser().getFirstName());
        }
        backMenu();
    }
}