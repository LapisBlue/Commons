package blue.lapis.common.economy.event;

import blue.lapis.common.economy.Transaction;

/**
 *
 */
//TODO: extend Cancellable when our SpongeAPI submodule is up to date
public interface TransactionEvent extends EconomyEvent {
    public Transaction getTransaction();
}
