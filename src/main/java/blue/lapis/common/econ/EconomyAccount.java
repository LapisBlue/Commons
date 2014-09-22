package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface EconomyAccount {

    String getAccountName();

    double getBalance();

    void setBalance();

    boolean hasCashFlow();

    CashFlow getCashFlow();

    String getCurrencyNameSingular();

    String getCurrencyNamePlural();
}
