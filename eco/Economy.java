package eco;

import java.util.UUID;

/**
 * @author Dragonfire
 */
public interface Economy {
    public static final String ACCOUNT_TYPE_PLAYER = "player";
    public static final String ACCOuNT_TYPE_BANK = "bank";

    public void createAccount(UUID player);

    public void createAccount(String type, String accountName);

    public void deleteAccount(UUID player);

    public void deleteAccount(String type, String accountName);

    public boolean accountExists(UUID player);

    public boolean accountExists(String type, String accountName);

    public double getBalance(UUID player);

    public double getBalance(String type, String accountName);

    public String getFormattedBalance(UUID player);

    public String getFormattedBalance(String type, String accountName);

    public String getFormattedAmount(double amount);

    public double parseCurrencyInput(String input);

    public boolean hasEnough(UUID player, double amount);

    public boolean hasEnough(String type, String accountName, double amount);

    public void substract(UUID player, double amount);

    public void substract(String type, String accountName, double amount);

    public void substract(UUID player, double amount, String source, String details);

    public void substract(String type, String accountName, double amount, String source, String details);

    public void add(UUID player, double amount);

    public void add(String type, String accountName, double amount);

    public void add(UUID player, double amount, String source, String detail);

    public void add(String type, String accountName, double amount, String source, String detail);

    public void modify(UUID player, double amount);

    public void modify(String type, String accountName, double amount);

    public void modify(UUID player, double amount, String source, String detail);

    public void modify(String type, String accountName, double amount, String source, String detail);

    public void set(UUID player, double amount);

    public void set(String type, String accountName, double amount);

    public void set(UUID player, double amount, String source, String detail);

    public void set(String type, String accountName, double amount, String source, String detail);

    public String getCurrencyNameSingular();

    public String getCurrencyNamePlural();

    public void printFlow(Object sender, String type, String accountName, int entries);

    public void printFlow(Object sender, UUID playerId, int entries);
}
