package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
interface EconomyUtils {

    void printFlow(EconomyAccount account, Object receivier);

    void printFlow(EconomyAccount account, Object receivier, int entries);

    String getFormattedBalance(EconomyAccount account);

    // TODO: formatted balance for any amount?
}
