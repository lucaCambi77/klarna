/**
 * 
 */
package com.klarna.risk.decision.domain.model;

import java.util.Date;

/**
 * @author luca
 *
 */
public class CustomerTransaction
{

    private CreditDecision creditDecision;
    private int amount;
    private String email;
    private Date transactionDate = new Date();

    public CreditDecision getCreditDecision()
    {
        return creditDecision;
    }

    public void setCreditDecision(CreditDecision creditDecision)
    {
        this.creditDecision = creditDecision;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getTransactionDate()
    {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate)
    {
        this.transactionDate = transactionDate;
    }

    public static class Builder
    {

        private CreditDecision creditDecision;
        private int amount;
        private String email;
        private Date transactionDate = new Date();

        public Builder withCreditDecistion(CreditDecision creditDecision)
        {
            this.creditDecision = creditDecision;
            return this;
        }

        public Builder withAmount(int amount)
        {
            this.amount = amount;
            return this;
        }

        public Builder withEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Builder withTransactionDate(Date transactionDate)
        {
            this.transactionDate = transactionDate;
            return this;
        }

        public CustomerTransaction build()
        {
            CustomerTransaction transaction = new CustomerTransaction();
            transaction.amount = this.amount;
            transaction.creditDecision = this.creditDecision;
            transaction.email = this.email;
            transaction.transactionDate = this.transactionDate;

            return transaction;
        }
    }
}
