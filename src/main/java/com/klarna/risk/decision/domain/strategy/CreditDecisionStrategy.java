/**
 * 
 */
package com.klarna.risk.decision.domain.strategy;

/**
 * @author luca
 *
 */
public interface CreditDecisionStrategy
{

    public int getMaxAmountBreach();

    public int getMaxDebt();
}
