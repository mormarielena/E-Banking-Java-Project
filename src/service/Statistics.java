package service;

import enums.AccountType;
import enums.Occupation;
import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Statistics {
    private List<Account> accounts;

    public Statistics(List<Account> accounts){
        this.accounts = accounts;
    }

    public double calculateAverageBalance(){
        if (accounts.isEmpty()) return 0;
        double totalBalance = 0;
        for(Account account: accounts){
            totalBalance += account.getBalance();
        }
        return totalBalance/accounts.size();
    }

    public int accountsByType(AccountType accountType){
        int count = 0;
        for(Account account: accounts){
            if(account.getAccountType() == accountType) count++;
        }
        return count;
    }

    public int countUsersByOccupation(Occupation occupation) {
        int count = 0;
        for (Account account : accounts) {
            if (account.getUser().getOccupation() == occupation) count++;
        }
        return count;
    }

    public int countUsersByAgeRange(int minAge, int maxAge) {
        int count = 0;
        for (Account account : accounts) {
            int userAge = account.getUser().getAge();
            if (userAge >= minAge && userAge <= maxAge) count++;
        }
        return count;
    }

    public void generateStatisticsReport(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("--- E-BANKING STATISTICAL REPORT ---");
            writer.newLine();
            writer.write("Generated on: " + java.time.LocalDate.now());
            writer.newLine();
            writer.write("------------------------------------");
            writer.newLine();
            writer.newLine();

            writer.write(String.format("Average Balance (All Accounts): %.2f USD", calculateAverageBalance()));
            writer.newLine();
            writer.newLine();

            writer.write("Accounts by Type:");
            writer.newLine();
            writer.write("- SAVINGS: " + accountsByType(AccountType.SAVINGS));
            writer.newLine();
            writer.write("- SALARY: " + accountsByType(AccountType.SALARY));
            writer.newLine();
            writer.write("- FIXED DEPOSIT: " + accountsByType(AccountType.FIXED_DEPOSIT));
            writer.newLine();
            writer.newLine();

            writer.write("Users by Occupation:");
            writer.newLine();
            writer.write("- STUDENT: " + countUsersByOccupation(Occupation.STUDENT));
            writer.newLine();
            writer.write("- EMPLOYEE: " + countUsersByOccupation(Occupation.EMPLOYEE));
            writer.newLine();
            writer.write("- RETIRED: " + countUsersByOccupation(Occupation.RETIRED));
            writer.newLine();
            writer.newLine();

            writer.write("------------------------------------");
            writer.newLine();
            writer.write("TOTAL ACCOUNTS IN SYSTEM: " + accounts.size());
        }
    }
}