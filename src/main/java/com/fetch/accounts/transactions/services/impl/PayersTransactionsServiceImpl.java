package com.fetch.accounts.transactions.services.impl;

import com.fetch.accounts.transactions.models.PayerRecord;
import com.fetch.accounts.transactions.models.PayerRecordTimestamped;
import com.fetch.accounts.transactions.services.PayersTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Component
public class PayersTransactionsServiceImpl implements PayersTransactionsService {

    private List<PayerRecordTimestamped> masterPayerRecordList;

    public PayersTransactionsServiceImpl(List<PayerRecordTimestamped> masterPayerRecordList) {
        this.masterPayerRecordList = masterPayerRecordList;
    }

    /**
     * Adds new transaction record to master list
     *
     * @param recordList
     */
    public void addTransaction(List<PayerRecordTimestamped> recordList) {
        masterPayerRecordList.addAll(recordList);
    }

    /**
     * Spend Points based on Accounting Teams Standards:
     *
     * ● We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)
     * ● We want no payer's points to go negative.
     *
     * @param pointsToSpend
     * @return
     */
    public List<PayerRecord> spendPoints(int pointsToSpend) {
        List<PayerRecord> result = new ArrayList<>();
        masterPayerRecordList.sort(Comparator.comparing(PayerRecordTimestamped::getTimestamp));
        for (PayerRecordTimestamped payerRecordTimestamped : masterPayerRecordList) {
            int points = payerRecordTimestamped.getPoints();
            if (points > 0) {
                PayerRecord payerRecord;
                if (points > pointsToSpend) {
                    int pointDiff = points - pointsToSpend;
                    payerRecord = new PayerRecord(payerRecordTimestamped.getPayer(), -pointsToSpend);
                    payerRecordTimestamped.setPoints(points - pointDiff);
                    pointsToSpend = 0;

                } else {
                    pointsToSpend -= points;
                    payerRecord = new PayerRecord(payerRecordTimestamped.getPayer(), -points);
                    payerRecordTimestamped.setPoints(0);
                }
                OptionalInt index = IntStream.range(0, result.size())
                        .filter(i -> payerRecord.getPayer().equals(result.get(i).getPayer()))
                        .findFirst();

                if (index.isPresent()) {
                    result.set(index.getAsInt(), new PayerRecord(payerRecord.getPayer(), payerRecord.getPoints() + result.get(index.getAsInt()).getPoints()));
                } else {
                    result.add(payerRecord);
                }
            }
            if (pointsToSpend <= 0) {
                break;
            }
        }
        if (pointsToSpend > 0) {
            result.add(new PayerRecord("Notice: Total Balance is not high enough. Remaining unspent points are listed.", pointsToSpend));
        }
        return result;
    }

    public Map<String, Integer> getBalance() {
        return masterPayerRecordList.stream().collect(groupingBy(PayerRecordTimestamped::getPayer, summingInt(PayerRecordTimestamped::getPoints)));
    }
}
