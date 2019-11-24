package com.klarna.risk.decision.domain;

import java.util.Collection;

/**
 * The implementation of the {@link CreditHistoryRepository} interface.
 */
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    @Override
    public Collection lookupTransactions(String email) {
        throw new UnsupportedOperationException("The method is not implemented!");
    }

    @Override
    public Collection lookupTransactions(String email, String reason) {
        throw new UnsupportedOperationException("The method is not implemented!");
    }
}
