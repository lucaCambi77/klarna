package com.klarna.risk.decision.domain.repository;

import java.util.Collection;

import com.klarna.risk.decision.domain.model.CustomerTransaction;

/**
 * An interface to log the credit decisions.
 */
public interface CreditHistoryRepository
{

    /**
     * Lookup all of the transactions based on the customer's email.
     *
     * @param email
     *            the customer's email
     * @return the customer's history
     */
    Collection<CustomerTransaction> lookupTransactions(String email);

    /**
     * Lookup all of the transactions based on the customer's email and the reason.
     *
     * @param email
     *            the customer's email
     * @param reason
     *            the credit decision reason
     * @return the customer's history
     */
    Collection<CustomerTransaction> lookupTransactions(String email, String reason);

    /**
     * Persist customer transaction
     * 
     * @param transaction
     */
    void persistTransaction(CustomerTransaction transaction);

}
