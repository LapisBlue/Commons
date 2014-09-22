package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface TransactionSystem {

    void addTransaction(Object gameObject, EconomyAccount account, Transaction transaction);
}
