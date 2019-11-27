package com.klarna.risk.decision.domain.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.klarna.risk.decision.domain.model.CustomerTransaction;

/**
 * The implementation of the {@link CreditHistoryRepository} interface.
 */
@Resource
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository
{
    List<CustomerTransaction> transactionList = new ArrayList<CustomerTransaction>();

    @Override
    public Collection<CustomerTransaction> lookupTransactions(String email)
    {
        return transactionList.stream().filter(t -> t.getEmail().equals(email)).collect(Collectors.toList());
    }

    @Override
    public Collection<CustomerTransaction> lookupTransactions(String email, String reason)
    {
        return transactionList.stream().filter(t -> t.getEmail().equals(email) && t.getCreditDecision().getReason().equals(reason))
                .collect(Collectors.toList());
    }

    @Override
    public void persistTransaction(CustomerTransaction transaction)
    {
        transactionList.add(transaction);
    }

}
