package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface EconomyAccount {

    double getBalance();

    void setBalance();

    boolean hasCashFlow();

    CashFlow getCashFlow();
}
