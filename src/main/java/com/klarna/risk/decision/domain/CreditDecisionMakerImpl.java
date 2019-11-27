package com.klarna.risk.decision.domain;

import com.klarna.risk.decision.domain.model.CreditDecision;
import com.klarna.risk.decision.domain.strategy.CreditDecisionFactory;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategy;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategyType;

/**
 * The implementation of the {@link CreditDecisionMaker} interface.
 */
public class CreditDecisionMakerImpl implements CreditDecisionMaker
{

    @Override
    public CreditDecision makeCreditDecision(int purchaseAmount, int currentCustomerDebt, CreditDecisionStrategyType type)
    {

        if (purchaseAmount < 0)
            throw new IllegalArgumentException("Purchase amount must be greater than zero!");

        CreditDecisionStrategy creditDecisionStrategy = new CreditDecisionFactory().createCreditDecisionStrategy(type);

        if (purchaseAmount > creditDecisionStrategy.getMaxAmountBreach())
            return CreditDecision.MAX_AMOUNT_BREACH;

        if (purchaseAmount + currentCustomerDebt > creditDecisionStrategy.getMaxDebt())
            return CreditDecision.DEBT;

        return CreditDecision.ACCEPTED;
    }

}
