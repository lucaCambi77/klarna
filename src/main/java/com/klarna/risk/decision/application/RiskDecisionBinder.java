package com.klarna.risk.decision.application;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.domain.CreditDecisionMakerImpl;
import com.klarna.risk.decision.domain.repository.CreditHistoryRepository;
import com.klarna.risk.decision.domain.repository.CreditHistoryRepositoryImpl;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepository;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepositoryImpl;

/**
 * A class containing interface-to-implementation bindings for dependency injection
 */
public class RiskDecisionBinder extends AbstractBinder
{

    @Override
    protected void configure()
    {
        bind(CustomerDebtRepositoryImpl.class).to(CustomerDebtRepository.class);
        bind(CreditDecisionMakerImpl.class).to(CreditDecisionMaker.class);
        bind(CreditHistoryRepositoryImpl.class).to(CreditHistoryRepository.class);

    }

}
