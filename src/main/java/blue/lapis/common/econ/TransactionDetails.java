package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface TransactionDetails {

    double getOldBalance();

    double getNewBalanace();

    String getReason();

    String getSource();
}
