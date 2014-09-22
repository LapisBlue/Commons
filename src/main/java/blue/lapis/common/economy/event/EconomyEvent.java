package blue.lapis.common.economy.event;

import blue.lapis.common.economy.EconomyAccount;
import org.spongepowered.api.event.Event;

public interface EconomyEvent extends Event {

    EconomyAccount getAccount();
}
