package blue.lapis.common.economy.event.impl;

import blue.lapis.common.economy.account.EconomyAccount;
import blue.lapis.common.economy.event.EconomyEvent;
import com.google.common.base.Preconditions;
import org.spongepowered.api.event.BaseEvent;

import javax.annotation.Nonnull;

public class BaseEconomyEvent extends BaseEvent implements EconomyEvent {
    // TODO:
    //   We should override isCancellable() here and return true, but the BaseEvent class has defined ALL
    //   methods as final so there is basically nothing we can do about that right now.
    //   Result is also not implemented in BaseEvent either...

    private final EconomyAccount account;

    protected BaseEconomyEvent(@Nonnull EconomyAccount account) {
        this.account = Preconditions.checkNotNull(account, "account");
    }

    @Override
    @Nonnull
    public EconomyAccount getAccount() {
        return account;
    }
}
