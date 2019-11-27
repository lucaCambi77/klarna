package com.klarna.risk.decision.domain.repository;

import java.util.Map;

import javax.annotation.Resource;

import com.google.common.collect.Maps;
import com.klarna.risk.decision.domain.model.CustomerDebt;

/**
 * The implementation of the {@link CustomerDebtRepository} interface.
 */
@Resource
public class CustomerDebtRepositoryImpl implements CustomerDebtRepository {

    private Map<String, Integer> customerDebtStorage = Maps.newConcurrentMap();

    @Override
    public CustomerDebt fetchCustomerDebtForEmail(String email) {
        return new CustomerDebt(email, customerDebtStorage.getOrDefault(email, 0));
    }

    @Override
    public void persistCustomerDebt(CustomerDebt customerDebt) {
        customerDebtStorage.put(customerDebt.getCustomerEmail(), customerDebt.getDebtAmount());
    }

}
