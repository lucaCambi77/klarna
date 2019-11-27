/**
 * 
 */
package com.klarna.risk.decision.domain.strategy;

/**
 * @author luca
 *
 */
public class CreditDecisionFactory
{

    public CreditDecisionStrategy createCreditDecisionStrategy(CreditDecisionStrategyType type)
    {

        switch (type == null ? CreditDecisionStrategyType.DEFAULT : type)
        {

            default:
                return new DefaultCreditDecisionStrategy();

        }
    }
}
