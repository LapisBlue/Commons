package blue.lapis.common.economy.event.impl;

import blue.lapis.common.economy.account.EconomyAccount;
import blue.lapis.common.economy.event.AccountCreatedEvent;

import javax.annotation.Nonnull;

public class AccountCreatedEventImpl extends EconomyEventImpl implements AccountCreatedEvent {
    public AccountCreatedEventImpl(@Nonnull EconomyAccount account) {
        super(account);
    }
}
