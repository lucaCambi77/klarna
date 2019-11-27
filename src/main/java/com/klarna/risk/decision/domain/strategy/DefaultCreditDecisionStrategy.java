/**
 * 
 */
package com.klarna.risk.decision.domain.strategy;

/**
 * @author luca
 *
 */
public class DefaultCreditDecisionStrategy implements CreditDecisionStrategy
{

    @Override
    public int getMaxAmountBreach()
    {
        return 10;
    }

    @Override
    public int getMaxDebt()
    {
        return 100;
    }

}
