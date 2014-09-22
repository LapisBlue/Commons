package blue.lapis.common.econ.component;

import blue.lapis.common.econ.Transaction;

/**
 * @author Dragonfire
 */
public interface CashFlow {

    Transaction[] getFlow();

    Transaction[] getFlow(int entrySize);

    void addFlow(Transaction newCashFlow);
}
