package com.klarna.risk.decision.api;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategyType;

/**
 * A class representing the details of credit request.
 */
public class CreditRequestV1
{

    /**
     * The email of the customer.
     */
    @JsonProperty(value = "email", required = true)
    private String email;

    /**
     * The total amount of this purchase.
     */
    @JsonProperty(value = "purchase_amount", required = true)
    private int purchaseAmount;

    /**
     * The first name of the customer
     */
    @JsonProperty(value = "first_name")
    private String firstName;

    /**
     * The last name of the customer
     */
    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "credit_decision_type")
    private CreditDecisionStrategyType creditDecisionStrategyType;

    private CreditRequestV1()
    {
        // Required by Jackson
    }

    public CreditRequestV1(String email, String firstName, String lastName, int purchaseAmount)
    {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchaseAmount = purchaseAmount;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getPurchaseAmount()
    {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount)
    {
        this.purchaseAmount = purchaseAmount;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public CreditDecisionStrategyType getCreditDecisionStrategyType()
    {
        return creditDecisionStrategyType;
    }

    public void setCreditDecisionStrategyType(CreditDecisionStrategyType creditDecisionType)
    {
        this.creditDecisionStrategyType = creditDecisionType;
    }

    @JsonIgnoreType
    public static class Builder
    {

        private String email;

        private int purchaseAmount;

        private String firstName;

        private String lastName;

        private CreditDecisionStrategyType creditDecisionStrategyType;

        public Builder withEmail(String email)
        {
            this.email = email;
            return this;

        }

        public Builder withPurchaseAmount(String purchaseAmount)
        {
            this.email = purchaseAmount;
            return this;

        }

        public Builder withFirstName(String firstName)
        {
            this.firstName = firstName;
            return this;

        }

        public Builder withLastName(String lastName)
        {
            this.lastName = lastName;
            return this;

        }

        public Builder withCreditDecisionType(CreditDecisionStrategyType type)
        {
            this.creditDecisionStrategyType = type;
            return this;

        }

        public CreditRequestV1 build()
        {

            CreditRequestV1 creditRequest = new CreditRequestV1();
            creditRequest.email = this.email;
            creditRequest.firstName = this.firstName;
            creditRequest.lastName = this.lastName;
            creditRequest.purchaseAmount = this.purchaseAmount;
            creditRequest.creditDecisionStrategyType = this.creditDecisionStrategyType;

            return creditRequest;
        }
    }

    @Override
    public String toString()
    {
        return String.format("CreditRequestV1 [email=%s, firstName=%s, lastName=%s, purchaseAmount=%d]", email, firstName, lastName, purchaseAmount);
    }

}
