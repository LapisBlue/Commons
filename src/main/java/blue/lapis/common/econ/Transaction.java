package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface Transaction {

    double getOldBalance();

    double getNewBalanace();

    double getDelta();

    TransactionDetail getDetails();
}
