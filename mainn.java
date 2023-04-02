import javax.swing.*;

import java.io.ObjectInputFilter.Status;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.regex.*;

public class mainn {

    public static int array_count = 0;

    public static void main(String[] args) {
        int mainMenu;
        BankAccount[] accounts = new BankAccount[10];
        while (true) {

            // Display main menu to user
            try {
                mainMenu = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Choose: \n 1. Create an Account \n 2. Make a Transaction \n 0. End Program (insert 0)"));
                if (mainMenu == 1) {
                    // creating account for the user
                    mainMenu = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Choose Account Type: \n 1. Saving Account \n 2. Current Account \n 0. End Program (insert 0)"));
                    if (mainMenu == 1) {
                        accounts[array_count] = objectInput("saving");
                        array_count++;

                    } else if (mainMenu == 2) {
                        accounts[array_count] = objectInput("current");
                        array_count++;

                    } else if (mainMenu == 0) {
                        break;
                    } else {
                        JOptionPane.showMessageDialog(null, "  Wrong option ");

                    }
                    continue;
                } else if (mainMenu == 2) {
                    // allowing the user to make transactions
                    mainMenu = Integer.parseInt(JOptionPane.showInputDialog(null,
                            "Choose: \n 1. Transfer \n 2. Withdraw \n 3. Deposit \n 4. Display \n 0. End Program (insert 0)"));

                    if (mainMenu == 1) {
                        transfer(accounts);
                    } else if (mainMenu == 2) {
                        // get sender.reciever object
                        String number = JOptionPane.showInputDialog(null,
                                "Account Number: \n");
                        BankAccount tempAccount = new BankAccount();

                        int account_found = 0;
                        for (int i = 0; i < array_count; i++) {
                            if (accounts[i].getAccountNumber() == Integer.parseInt(number)) {
                                tempAccount = accounts[i];
                                account_found = 1;
                                break;
                            }
                        }
                        if (account_found == 0) {
                            JOptionPane.showMessageDialog(null, "Error: The account doesn't exist");
                        } else {

                            int amount = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Amount: \n"));
                            tempAccount.withdraw(amount);

                        }
                    } else if (mainMenu == 3) {
                        // get sender.reciever object
                        String number = JOptionPane.showInputDialog(null,
                                "Account Number: \n");
                        BankAccount tempAccount = new BankAccount();

                        int account_found = 0;
                        for (int i = 0; i < array_count; i++) {
                            if (accounts[i].getAccountNumber() == Integer.parseInt(number)) {
                                tempAccount = accounts[i];
                                account_found = 1;
                                break;
                            }
                        }
                        if (account_found == 0) {
                            JOptionPane.showMessageDialog(null, "Error: The account doesn't exist");
                        } else {

                            int amount = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    "Amount: \n"));
                            tempAccount.deposit(amount);

                        }
                    } else if (mainMenu == 4) {
                        // get sender.reciever object
                        String number = JOptionPane.showInputDialog(null,
                                "Account Number: \n");

                        int account_found = 0;
                        for (int i = 0; i < array_count; i++) {
                            if (accounts[i].getAccountNumber() == Integer.parseInt(number)) {
                                accounts[i].display();
                                account_found = 1;
                                break;
                            }
                        }
                        if (account_found == 0) {
                            JOptionPane.showMessageDialog(null, "Error: The account doesn't exist");

                        }
                    } else if (mainMenu == 0) {
                        break;
                    }
                } else if (mainMenu == 0) {

                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "  Wrong option ");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: The input must be a number");
            } catch (Exception e) {

            }

        }
    }

    public static BankAccount objectInput(String type) {
        BankAccount account = new BankAccount();
        String fullName = JOptionPane.showInputDialog(null,
                "Enter full name: ");

        Random random = new Random();
        int RandomNumber = random.nextInt(1001, 10000);

        if (type.equalsIgnoreCase("saving")) {
            account = new SavingAccount(fullName, type, RandomNumber);
        } else if (type.equalsIgnoreCase("current")) {
            account = new CurrentAccount(fullName, type, RandomNumber);
        }

        // Displaying the account information
        account.display();

        return account;
    }

    public static void transfer(BankAccount[] accounts) {

        // get sender.reciever object
        BankAccount sender = new BankAccount(null, null, 0), receiver = new BankAccount(null, null, 0);
        int Sendernumber;

        // Error handling
        try {
            Sendernumber = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Sender Number: \n"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: The input must be a number");
            return;
        }

        for (int i = 0; i < array_count; i++) {
            if (accounts[i].getAccountNumber() == Sendernumber) {
                sender = accounts[i];
            }
        }
        if (sender.getFullName() == null) {
            JOptionPane.showMessageDialog(null, "Sorry, that account doesn't exist");
            return;

        }

        int receiverNumber;

        // Error handling
        try {
            receiverNumber = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Receiver Number: \n"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: The input must be a number");
            return;
        }

        for (int i = 0; i < array_count; i++) {
            if (accounts[i].getAccountNumber() == receiverNumber) {
                receiver = accounts[i];
            }
        }
        if (receiver.getFullName() == null) {
            JOptionPane.showMessageDialog(null, "Sorry, that account doesn't exist");
            return;

        }

        int amount;
        // Error handling
        try {
            amount = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Enter Amount: \n"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: The input must be a number");
            return;
        }

        if (sender.getBalance() >= amount){
            sender.withdraw(amount);
            receiver.deposit(amount);
        }
        else {
            JOptionPane.showMessageDialog(null, "Transaction Satus: Unsuccessful\n"+ "Error: Insufficient balance\n");

        }
        
        
    }

}

class BankAccount {
    protected String fullName, accountType, TransactionStatus;
    protected int accountNumber;
    protected double balance, interestRate;

    protected final static String bankName = "ABC BANK";

    public BankAccount() {
        fullName = "Unassigned";
        accountType = "unassigned";
        accountNumber = 0;
        balance = 0.0;
        interestRate = 0.0;

    }

    public BankAccount(String name, String type, int number) {
        fullName = name;
        accountType = type;
        accountNumber = number;
        balance = 0.0;
        interestRate = 0.0;

    }   

    public String toString() {
        // JOptionPane.showMessageDialog(null, " Wrong option ");
        return "Full Name: " + fullName +
                "\nBank Name: " + bankName +
                "\nAccount Type: " + accountType +
                "\nAccount Number: " + accountNumber +
                "\nBalance: " + balance +
                "\nInterest Rate: " + interestRate + "\nTransaction Status: " + TransactionStatus + "\n";

    }

    public void deposit(double amount) {

        double oldBalance = balance;
        try {
            balance += amount;
            if (oldBalance > balance) {
                throw new ArithmeticException();
            } else {
                TransactionStatus = "Successful";
            }
        } catch (ArithmeticException e) {
            TransactionStatus = "Unsuccessful";
        }
        JOptionPane.showMessageDialog(null, toString());

    }

    public void withdraw(double amount) {
        try {
            if (amount > balance) {
                throw new ArithmeticException();
            } else {
                balance -= amount;
                TransactionStatus = "Successful";

            }
        } catch (ArithmeticException e) {
            TransactionStatus = "Unsuccessful";
        }
        JOptionPane.showMessageDialog(null, toString());

    }

    public void display() {
        JOptionPane.showMessageDialog(null, "Full Name: " + fullName +
                "\nBank Name: " + bankName +
                "\nAccount Type: " + accountType +
                "\nAccount Number: " + accountNumber +
                "\nBalance: " + balance +
                "\nInterest Rate: " + interestRate + "\n");

    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getFullName() {
        return fullName;
    }

    public double getInterestRate() {
        return interestRate;
    }

}

class SavingAccount extends BankAccount {

    public SavingAccount(String name, String type, int number) {
        super(name, type, number);
        balance = 50.0;
        interestRate = 0.0005;

    }
}

class CurrentAccount extends BankAccount {
    public CurrentAccount(String name, String type, int number) {
        super(name, type, number);
        balance = 200.0;
        interestRate = 0.0001;

    }
}