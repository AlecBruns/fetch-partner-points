package com.fetch.accounts.transactions.services;

import com.fetch.accounts.transactions.models.PayerRecord;
import com.fetch.accounts.transactions.models.PayerRecordTimestamped;

import java.util.List;
import java.util.Map;

public interface PayersTransactionsService {

    void addTransaction(List<PayerRecordTimestamped> recordList);

    List<PayerRecord> spendPoints(int pointsToSpend);

    Map<String, Integer> getBalance();
}
