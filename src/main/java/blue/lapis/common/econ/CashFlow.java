package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface CashFlow {

    Transaction[] getFlow();

    Transaction[] getFlow(int entrySize);

    void addFlow(Transaction newCashFlow);
}
