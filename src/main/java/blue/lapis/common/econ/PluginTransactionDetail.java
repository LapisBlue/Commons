package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface PluginTransactionDetail extends TransactionDetail {
    String getPluginName();

    String getReasonId();

    Object[] getCustomValues();
}
