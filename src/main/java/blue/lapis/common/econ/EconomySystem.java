package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface EconomySystem {

    void addTransaction(Object gameObject, TransactionDetails transaction);
}
