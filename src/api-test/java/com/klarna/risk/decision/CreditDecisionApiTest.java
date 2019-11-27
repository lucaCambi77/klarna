package com.klarna.risk.decision;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;

import com.klarna.risk.decision.api.CreditRequestDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.domain.strategy.CreditDecisionStrategyType;

public class CreditDecisionApiTest
{

    private static String SERVICE_URL = "http://localhost:8080/v1/decision";

    @Rule
    public final JettyServerResource server = new JettyServerResource();

    private static CreditRequestV1 requestPayload = new CreditRequestV1.Builder()
            .withEmail("john@doe.com").withFirstName("John").withLastName("Doe").withCreditDecisionType(CreditDecisionStrategyType.DEFAULT)
            .build();

    @Test
    public void requestUpTo10ShouldBeAccepted()
    {
        requestPayload.setPurchaseAmount(10);

        Response response = ClientBuilder.newClient()
                .target(SERVICE_URL).request()
                .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(true));
        assertThat(creditDecision.getReason(), is("ok"));
    }

    @Test
    public void requestAbove10ShouldNotBeAccepted()
    {
        requestPayload.setPurchaseAmount(11);

        Response response = ClientBuilder.newClient()
                .target(SERVICE_URL).request()
                .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(false));
        assertThat(creditDecision.getReason(), is("amount"));
    }

    @Test
    public void customerDebtLimitShouldBeExceeded()
    {
        requestPayload.setPurchaseAmount(10);

        for (int i = 0; i < 10; i++)
        {
            ClientBuilder.newClient()
                    .target(SERVICE_URL).request()
                    .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));
        }

        requestPayload.setPurchaseAmount(1);

        Response response = ClientBuilder.newClient()
                .target(SERVICE_URL).request()
                .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(false));
        assertThat(creditDecision.getReason(), is("debt"));
    }

}
