package blue.lapis.common.economy;

import blue.lapis.common.economy.account.EconomyAccount;

/**
 *
 */
public class Transaction {
    private boolean isSetter = false;
    private Status status = Status.INCOMPLETE;
    private EconomyAccount account;
    private Object initiator = null;
    private Object target = null;
    private Object reason = null;
    private double delta = 0.0d;
    private double balance = Double.NaN;

    private Transaction(EconomyAccount against) {
        this.account = account;
    }

    public static Transaction of(EconomyAccount account) {
        return new Transaction(account);
    }

    public static Transaction setBalance(EconomyAccount account, double balance) {
        Transaction result = new Transaction(account);
        result.balance = balance;
        result.isSetter = true;
        return result;
    }

    public Transaction withInitiator(Object o) {
        if (status==Status.INCOMPLETE) this.initiator = o;
        return this;
    }

    public Transaction withTarget(Object o) {
        if (status==Status.INCOMPLETE) this.target = o;
        return this;
    }

    public Transaction withReason(Object o) {
        if (status==Status.INCOMPLETE) this.reason = o;
        return this;
    }

    public Object getReason() {
        return reason;
    }

    public Object getInitiator() {
        return initiator;
    }

    public Object getTarget() {
        return target;
    }

    public double getDelta() {
        return delta;
    }

    public Transaction add(double amount) {
        delta += amount;
        return this;
    }

    public Transaction subtract(double amount) {
        delta -= amount;
        return this;
    }

    public Transaction multiplyBy(double amount) {
        delta *= amount;
        return this;
    }

    public Transaction divideBy(double amount) {
        delta /= amount;
        return this;
    }

    public Transaction fireEvent() {
        status = Status.EVENT_FIRED;
        //TODO: Fire
        //TODO: Update Status to CANCELLED if needed
        return this;
    }

    public Transaction commit() {
        if (status!=Status.EVENT_FIRED) {
            throw new IllegalStateException("Expected transaction state: EVENT_FIRED. Instead found "+status.name());
        }

        //TODO: Commit to the EconomyAccount
        return this;
    }

    public static enum Status {
        INCOMPLETE,
        EVENT_FIRED,
        CANCELLED,
        FAILED,
        COMPLETE;
    }
}
