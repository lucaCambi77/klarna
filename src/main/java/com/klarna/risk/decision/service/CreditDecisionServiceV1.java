package com.klarna.risk.decision.service;

import static com.google.common.base.Preconditions.checkArgument;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Strings;
import com.klarna.risk.decision.api.CreditRequestDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.domain.model.CreditDecision;
import com.klarna.risk.decision.domain.model.CustomerDebt;
import com.klarna.risk.decision.domain.model.CustomerTransaction;
import com.klarna.risk.decision.domain.repository.CreditHistoryRepository;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepository;

/**
 * The public API of the credit decision solution.
 */
@Path("/")
@Singleton
public class CreditDecisionServiceV1
{

    @Inject
    private CustomerDebtRepository customerDebtRepository;

    @Inject
    private CreditDecisionMaker creditDecisionMaker;

    @Inject
    private CreditHistoryRepository creditHistoryRepository;

    /**
     * Handling the credit decision process.
     *
     * @param creditRequestV1
     *            credit request with the amount and the customer's details
     * @return the decision
     */
    @POST
    @Path("/v1/decision")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreditRequestDecisionV1 handleCreditRequestV1(CreditRequestV1 creditRequestV1)
    {

        performArgumentChecks(creditRequestV1);

        CustomerDebt customerDebt = customerDebtRepository.fetchCustomerDebtForEmail(creditRequestV1.getEmail());

        CreditDecision creditDecision = creditDecisionMaker.makeCreditDecision(creditRequestV1.getPurchaseAmount(), customerDebt.getDebtAmount(),
                creditRequestV1.getCreditDecisionStrategyType());

        if (creditDecision.isAccepted())
        {

            creditHistoryRepository.persistTransaction(
                    new CustomerTransaction.Builder().withAmount(creditRequestV1.getPurchaseAmount()).withCreditDecistion(creditDecision)
                            .withEmail(creditRequestV1.getEmail()).build());

            customerDebt.increaseDebtAmount(creditRequestV1.getPurchaseAmount());
            customerDebtRepository.persistCustomerDebt(customerDebt);
        }

        return CreditRequestDecisionV1.from(creditDecision);
    }

    private void performArgumentChecks(CreditRequestV1 creditRequest)
    {
        checkArgument(creditRequest != null);
        checkArgument(!Strings.isNullOrEmpty(creditRequest.getEmail()));
        checkArgument(!Strings.isNullOrEmpty(creditRequest.getFirstName()));
        checkArgument(!Strings.isNullOrEmpty(creditRequest.getLastName()));
        checkArgument(creditRequest.getPurchaseAmount() > 0);
    }

}
