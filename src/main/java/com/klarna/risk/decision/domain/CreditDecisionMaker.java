package com.klarna.risk.decision.domain;

import com.klarna.risk.decision.domain.model.CreditDecision;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategyType;

/**
 * An interface of the credit decision algorithm.
 */
public interface CreditDecisionMaker
{

    /**
     * Obtain a credit decision for given credit request amount and current customer debt amount.
     *
     * @param purchaseAmount
     *            the requested credit amount
     * @param customerDebt
     *            the customer's current dept
     * @param creditDecitionStrategy
     *            the credit decision strategy to apply
     * 
     * @return the credit decision
     */
    CreditDecision makeCreditDecision(int purchaseAmount, int customerDebt, CreditDecisionStrategyType creditDecitionStrategyType);

}