package com.klarna.risk.decision;

import com.klarna.risk.decision.api.CreditRequestDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreditDecisionApiTest {

    private static String SERVICE_URL = "http://localhost:8080/v1/decision";

    @Rule
    public final JettyServerResource server = new JettyServerResource();

    @Test
    public void requestUpTo10ShouldBeAccepted() {
        CreditRequestV1 requestPayload = defaultCreditRequestOfPurchaseAmount(10);

        Response response = ClientBuilder.newClient()
                                         .target(SERVICE_URL).request()
                                         .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(true));
        assertThat(creditDecision.getReason(), is("ok"));
    }

    @Test
    public void requestAbove10ShouldNotBeAccepted() {
        CreditRequestV1 requestPayload = defaultCreditRequestOfPurchaseAmount(11);

        Response response = ClientBuilder.newClient()
                                         .target(SERVICE_URL).request()
                                         .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(false));
        assertThat(creditDecision.getReason(), is("amount"));
    }

    @Test
    public void customerDebtLimitShouldBeExceeded() {
        for (int i = 0; i < 10; i++) {
            ClientBuilder.newClient()
                         .target(SERVICE_URL).request()
                         .post(Entity.entity(defaultCreditRequestOfPurchaseAmount(10), MediaType.APPLICATION_JSON));
        }

        CreditRequestV1 requestPayload = defaultCreditRequestOfPurchaseAmount(1);

        Response response = ClientBuilder.newClient()
                                         .target(SERVICE_URL).request()
                                         .post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));

        assertThat(response.getStatus(), is(200));
        CreditRequestDecisionV1 creditDecision = response.readEntity(CreditRequestDecisionV1.class);
        assertThat(creditDecision.isAccepted(), is(false));
        assertThat(creditDecision.getReason(), is("debt"));
    }


    private CreditRequestV1 defaultCreditRequestOfPurchaseAmount(int purchaseAmount) {
        return new CreditRequestV1("john@doe.com", "john", "doe", purchaseAmount);
    }

}
