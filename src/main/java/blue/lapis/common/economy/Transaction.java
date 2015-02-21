/*
 * LapisCommons
 * Copyright (c) 2014, Lapis <https://github.com/LapisBlue>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package blue.lapis.common.economy;

import blue.lapis.common.LapisCommonsPlugin;
import blue.lapis.common.economy.event.TransactionEvent;
import blue.lapis.common.economy.event.TransactionException;
import blue.lapis.common.economy.event.impl.TransactionEventImpl;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;

import javax.annotation.Nullable;

/**
 * <p>A Transaction represents a single atomic state-change request to an {@link EconomyAccount}.</p>
 *
 * <p>The primary data elements exposed are a {@code delta} and an {@code absolute}. Delta represents
 * addition or subtraction to the account, and absolute represents a directly-set balance. If both delta and
 * absolute are present, absolute is applied first, then delta. Any conforming EconomyAccount is required to
 * honor these two elements as specified.</p>
 *
 * <p>Secondary information of arbitrary kinds can be attached to initiator, target, and reason.
 * Initiator and target are most often players, but can be automated systems, villager NPCs,
 * or anything that clearly identifies that endpoint of the transaction. Reason is <b>typically</b> a String,
 * but can wind up holding many auxiliary details of a transaction. It is recommended but not required for a
 * conforming EconomyAccount to ignore these three elements.</p>
 *
 * <p>The typical lifecycle of a Transaction starts with a fluent builder syntax. When all the information
 * desired is specified, {@link #commit(FutureCallback)} is called, and an event is fired. If that event is
 * cancelled, the FutureCallback's onFailure is called immediately. If not, the Transaction is handed off to
 * the EconomyAccount specified. At this point the Transaction may complete asynchronously. In the end, either
 * onSuccess or onFailure is called on the callback, and the Transaction is either complete or failed,
 * respectively.
 *
 * <p>The typical calling pattern for a transaction is:</p>
 *
 * <pre>
 *     Transaction t = Transaction.on(myFriendsAccount)
 *         .withInitiator(me)
 *         .withTarget(myFriend)
 *         .withReason("My friend is awesome!!!!11111!111")
 *         .add(9001)
 *         .commit(new FutureCallback&lt;Transaction&gt;(){
 *             &#64;Override
 *             public void onFailure(Throwable t) {
 *                 System.out.println("Boo!");
 *             }
 *             &#64;Override
 *             public void onSuccess(Transaction t) {
 *                 System.out.println("Yay!");
 *             }
 *         });
 * </pre>
 */
public final class Transaction {

    private final EconomyAccount account;

    private Status status = Status.INCOMPLETE;
    private Object initiator = null;
    private Object target = null;
    private Object reason = null;
    private double delta = 0.0d;
    private Double balance = null;

    private FutureCallback<Transaction> callback = null;

    private Transaction(EconomyAccount account) {
        this.account = Preconditions.checkNotNull(account, "account");
    }

    /**
     * <p>Begin building a new Transaction</p>
     *
     * @param account The EconomyAccount which this Transaction applies to
     * @return A new Transaction which will operate on the specified account
     */
    public static Transaction on(EconomyAccount account) {
        return new Transaction(account);
    }

    /**
     * <p>Add an initiator to this Transaction</p>
     *
     * <p>This method may not be called after the Transaction has left INCOMPLETE Status.</p>
     *
     * @param initiator Any object identifying the origin of this Transaction.
     * @return This object for further modification
     */
    public Transaction withInitiator(Object initiator) {
        validateState(Status.INCOMPLETE);
        this.initiator = initiator;
        return this;
    }

    /**
     * <p>Add a target to this Transaction</p>
     *
     * <p>This method may not be called after the Transaction has left INCOMPLETE Status.</p>
     *
     * @param target Any object identifying this Transaction's target; usually the account holder.
     * @return This object for further modification
     */
    public Transaction withTarget(Object target) {
        validateState(Status.INCOMPLETE);
        this.target = target;
        return this;
    }

    /**
     * <p>Add a reason to this Transaction.</p>
     *
     * <p>This method may not be called after the Transaction has left INCOMPLETE Status.</p>
     *
     * @param reason An identifying characteristic of this Transaction. Often this is a String representing
     * what
     * was purchased, but can be arbitrary data for event listeners to use.
     * @return This object for further modification
     */
    public Transaction withReason(Object reason) {
        validateState(Status.INCOMPLETE);
        this.reason = reason;
        return this;
    }

    /**
     * <p>Add a new balance amount to this Transaction. This is generally not recommended except in
     * circumstances where an administrator has explicitly set someone's balance.</p>
     *
     * <p>This method may not be called after the Transaction has left INCOMPLETE Status.</p>
     *
     * @param balance The new "absolute" balance for this account
     * @return This object for further modification
     *
     * @see #add(double)
     * @see #subtract(double)
     */
    public Transaction withAbsolute(Double balance) {
        validateState(Status.INCOMPLETE);
        this.balance = balance;
        return this;
    }

    /**
     * @return The EconomyAccount which this Transaction applies to
     */
    public EconomyAccount getAccount() {
        return account;
    }

    /**
     * @return The Reason object which identifies an item being bought/sold, land being taxed, or similar data
     */
    @Nullable
    public Object getReason() {
        return reason;
    }

    /**
     * @return The initiator, usually the Player who invoked this transaction
     */
    @Nullable
    public Object getInitiator() {
        return initiator;
    }

    /**
     * @return The target of the "action" being performed, usually the holder of the EconomyAccount involved
     */
    @Nullable
    public Object getTarget() {
        return target;
    }

    /**
     * @return The amount the balance should change
     */
    public double getDelta() {
        return delta;
    }

    /**
     * @return The new balance which this Transaction specifies for the account, or null if this transaction is
     * a relative gain/loss
     */
    @Nullable
    public Double getAbsolute() {
        return balance;
    }

    /**
     * Express a payment or increase in the account's balance.
     *
     * @param amount The amount to increase the account balance by
     * @return This object for further modification
     */
    public Transaction add(double amount) {
        delta += amount;
        return this;
    }

    /**
     * Express a withdrawal or loss to the account's balance.
     *
     * @param amount The amount to reduce the account balance by
     * @return This object for further modification
     */
    public Transaction subtract(double amount) {
        delta -= amount;
        return this;
    }

    /**
     * Express a constant balance that the account should be changed to
     *
     * @param amount The amount that the new account balance should be
     * @return This object for further modification
     */
    public Transaction setAbsolute(double amount) {
        balance = amount;
        return this;
    }

    /**
     * Causes a TransactionEvent to be fired for this Transaction, and if the event is not cancelled, hands
     * this Transaction to the EconomyAccount for final processing. This final processing may complete
     * asynchronously, and may fail, so it's important to register a FutureCallback to handle the result of the
     * transaction.
     *
     * @param onResult A callback for when the transaction is complete
     */
    public void commit(FutureCallback<Transaction> onResult) {
        validateState(Status.INCOMPLETE);
        status = Status.IN_TRANSIT;
        TransactionEvent event = new TransactionEventImpl(this);
        LapisCommonsPlugin.getGame().getEventManager().post(event);

        if (event.isCancelled()) {
            status = Status.CANCELLED;
            onResult.onFailure(new TransactionException("Transaction cancelled.", this));
            return;
        }
        status = Status.PROCESSING;

        this.callback = onResult;
        account.apply(this);
    }

    /**
     * Called by an EconomyAccount to indicate to the initiating program that the transaction has completed
     * successfully.
     */
    public void fireSuccess() {
        validateState(Status.PROCESSING);
        status = Status.COMPLETE;
        if (callback != null) {
            callback.onSuccess(this);
        }
    }

    /**
     * Called by an EconomyAccount to indicate to the initiating program that the transaction was unable to be
     * completed.
     *
     * @param t The reason for the failed transaction or null if unknown.
     */
    public void fireFailure(Throwable t) {
        validateState(Status.PROCESSING);
        status = Status.FAILED;
        if (callback != null) {
            callback.onFailure((t != null) ? t : new TransactionException("Transaction failed.", this));
        }
    }

    private void validateState(Status expected) {
        Preconditions.checkState(status == expected, "Expected transaction state %s, " +
                "found %s instead.", expected, status);
    }

    private static enum Status {
        /**
         * The Transaction is still being built or has not been submitted to the event yet.
         */
        INCOMPLETE,
        /**
         * The Transaction is currently in a TransactionEvent
         */
        IN_TRANSIT,
        /**
         * The Transaction is currently waiting for the EconomyAccount to finish final processing.
         */
        PROCESSING,
        /**
         * The Transaction was submitted to the event, but the event was cancelled by a listener.
         */
        CANCELLED,
        /**
         * The Transaction was submitted to the EconomyAccount for final processing, but was rejected.
         */
        FAILED,
        /**
         * The Transaction was submitted to the EconomyAccount and its final processing was successful.
         */
        COMPLETE
    }

}
