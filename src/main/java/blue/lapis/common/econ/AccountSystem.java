package blue.lapis.common.econ;

/**
 * @author Dragonfire
 */
public interface AccountSystem {
    EconomyAccount getAccount(Object gameObject, String accountName);

    boolean hasAccount(Object gameObject, String accountName);

    EconomyAccount createAccount(Object gameObject, String accountName);

    void deleteAccount(Object gameObject, String accountName);
}
