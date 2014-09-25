package blue.lapis.common.economy.event.impl;

import blue.lapis.common.economy.account.EconomyAccount;
import blue.lapis.common.economy.event.AccountDeletedEvent;

import javax.annotation.Nonnull;

public class AccountDeletedEventImpl extends EconomyEventImpl implements AccountDeletedEvent {
    public AccountDeletedEventImpl(@Nonnull EconomyAccount account) {
        super(account);
    }
}
