package com.klarna.risk.decision.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.klarna.risk.decision.domain.model.CustomerDebt;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepository;
import com.klarna.risk.decision.domain.repository.CustomerDebtRepositoryImpl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CustomerDebtRepositoryTest {

    private CustomerDebtRepository customerDebtRepository;

    @Before
    public void before() {
        customerDebtRepository = new CustomerDebtRepositoryImpl();
    }

    @After
    public void after() {
        customerDebtRepository = null;
    }

    @Test
    public void newCustomerDebtIsZero() {
        CustomerDebt customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
        assertThat(customerDebt, notNullValue());
        assertThat(customerDebt.getCustomerEmail(), is("john@doe.com"));
        assertThat(customerDebt.getDebtAmount(), is(0));
    }

    @Test
    public void returningCustomerDebtIsNotZero() {
        for (int i = 0; i < 5; i++) {
            CustomerDebt customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
            assertThat(customerDebt, notNullValue());
            assertThat(customerDebt.getCustomerEmail(), is("john@doe.com"));
            assertThat(customerDebt.getDebtAmount(), is(10 * i));
            customerDebt.increaseDebtAmount(10);
            customerDebtRepository.persistCustomerDebt(customerDebt);
        }
    }

}
