package com.klarna.risk.decision.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.klarna.risk.decision.api.CreditRequestDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.domain.model.CreditDecision;
import com.klarna.risk.decision.domain.model.CustomerDebt;
import com.klarna.risk.decision.domain.repository.CreditHistoryRepository;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepository;
import com.klarna.risk.decision.domain.strategy.CreditDecisionFactory;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategyType;

@RunWith(MockitoJUnitRunner.class)
public class CreditDecisionServiceTest
{

    @Mock
    private CustomerDebtRepository customerDebtRepository;

    @Mock
    private CreditDecisionMaker creditDecisionMaker;

    @Mock
    private CreditDecisionFactory creditDecisionStrategyFactory;

    @Mock
    private CreditHistoryRepository creditHistoryRepository;

    @InjectMocks
    private CreditDecisionServiceV1 creditDecisionService;

    private static CreditRequestV1 creditRequest = new CreditRequestV1.Builder()
            .withEmail("john@doe.com").withFirstName("John").withLastName("Doe").withCreditDecisionType(CreditDecisionStrategyType.DEFAULT)
            .build();

    @Test
    public void shouldAcceptCreditRequest()
    {
        creditRequest.setPurchaseAmount(10);

        when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.getEmail()))
                .thenReturn(new CustomerDebt(creditRequest.getEmail(), 7));
        when(creditDecisionMaker.makeCreditDecision(10, 7, CreditDecisionStrategyType.DEFAULT))
                .thenReturn(CreditDecision.ACCEPTED);

        CreditRequestDecisionV1 decision = creditDecisionService.handleCreditRequestV1(creditRequest);
        assertThat(decision.isAccepted(), is(true));
        assertThat(decision.getReason(), is("ok"));
    }

    @Test
    public void shouldRejectCreditRequest()
    {
        creditRequest.setPurchaseAmount(11);

        when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.getEmail()))
                .thenReturn(new CustomerDebt(creditRequest.getEmail(), 7));
        when(creditDecisionMaker.makeCreditDecision(11, 7, CreditDecisionStrategyType.DEFAULT))
                .thenReturn(CreditDecision.MAX_AMOUNT_BREACH);

        CreditRequestDecisionV1 decision = creditDecisionService.handleCreditRequestV1(creditRequest);
        assertThat(decision.isAccepted(), is(false));
        assertThat(decision.getReason(), is("amount"));
    }

    @Test
    public void shouldUpdateCustomerDebtWhenCreditAccepted()
    {
        creditRequest.setPurchaseAmount(10);

        when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.getEmail()))
                .thenReturn(new CustomerDebt(creditRequest.getEmail(), 7));
        when(creditDecisionMaker.makeCreditDecision(10, 7, CreditDecisionStrategyType.DEFAULT))
                .thenReturn(CreditDecision.ACCEPTED);

        creditDecisionService.handleCreditRequestV1(creditRequest);

        ArgumentCaptor<CustomerDebt> captor = ArgumentCaptor.forClass(CustomerDebt.class);
        verify(customerDebtRepository).persistCustomerDebt(captor.capture());
        assertThat(captor.getValue().getDebtAmount(), is(17));
    }

}
