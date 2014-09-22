package blue.lapis.common.econ.component;

import blue.lapis.common.econ.TransactionDetails;

/**
 * @author Dragonfire
 */
public interface CashFlow {

    TransactionDetails[] getFlow();

    TransactionDetails[] getFlow(int entrySize);

    void addFlow(TransactionDetails newCashFlow);
}
