import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("***********Welcome to the ATM Interface************");

        // Prompt user for user ID and PIN
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        // Verify user credentials
        if (atm.login(userID, pin)) {
            System.out.println("Login successful!");

            // Unlock ATM functionalities
            boolean quit = false;
            while (!quit) {
                // Display menu options
                System.out.println("\n=======Select an option:=======\n");
                System.out.println("1. View Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                
                switch (choice) {
                    case 1:
                        atm.viewTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientID = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        atm.transfer(recipientID, transferAmount);
                        break;
                    case 5:
                        System.out.println("\n\nThank You......!!!\n\nVisit Again....!!");
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Login failed. Invalid user ID or PIN.");
        }

        scanner.close();
    }
}

class User {
    private String userID;
    private String pin;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userID, String pin) {
        this.userID = userID;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserID() {
        return userID;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void subtractBalance(double amount) {
        balance -= amount;
    }
}

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class ATM {
    private User currentUser;

    public ATM() {
        // Initialize the ATM with a default user
        currentUser = new User("12345", "6789");
    }

    public boolean login(String userID, String pin) {
        return currentUser.getUserID().equals(userID) && currentUser.getPin().equals(pin);
    }

    public void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction.getType() + ": ₹" + transaction.getAmount());
        }
    }

    public void withdraw(double amount) {
        if (currentUser.getBalance() >= amount) {
            // Sufficient balance, proceed with withdrawal
            currentUser.subtractBalance(amount);
            currentUser.addTransaction(new Transaction("Withdrawal", amount));
            System.out.println("₹" + amount + " withdrawn successfully.");
        } else {
            // Insufficient balance
            System.out.println("Insufficient balance. Unable to withdraw ₹" + amount);
        }
    }

    public void deposit(double amount) {
        currentUser.deposit(amount);
        currentUser.addTransaction(new Transaction("Deposit", amount));
        System.out.println("₹" + amount + " deposited successfully.");
    }

    public void transfer(String recipientID, double amount) {
        if (currentUser.getBalance() >= amount) {
            // Sufficient balance, proceed with transfer
            currentUser.subtractBalance(amount);
            currentUser.addTransaction(new Transaction("Transfer to " + recipientID, amount));
            System.out.println("₹" + amount + " transferred to " + recipientID + " successfully.");
        } else {
            // Insufficient balance
            System.out.println("Insufficient balance. Unable to transfer ₹" + amount + " to " + recipientID);
        }
    }
}
