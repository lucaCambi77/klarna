package com.klarna.risk.decision.application;

import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.domain.CreditDecisionMakerImpl;
import com.klarna.risk.decision.domain.CustomerDebtRepository;
import com.klarna.risk.decision.domain.CustomerDebtRepositoryImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * A class containing interface-to-implementation bindings for dependency injection
 */
public class RiskDecisionBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(CustomerDebtRepositoryImpl.class).to(CustomerDebtRepository.class);
        bind(CreditDecisionMakerImpl.class).to(CreditDecisionMaker.class);
    }

}
