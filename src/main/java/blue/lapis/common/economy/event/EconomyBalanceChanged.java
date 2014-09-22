package blue.lapis.common.economy.event;

import blue.lapis.common.economy.Transaction;

public interface EconomyBalanceChanged extends EconomyEvent {

    Transaction getTransaction();
}
