package com.fetch.accounts.transactions.controllers;

import com.fetch.accounts.transactions.models.PayerList;
import com.fetch.accounts.transactions.models.PayerRecord;
import com.fetch.accounts.transactions.models.PointsToSpend;
import com.fetch.accounts.transactions.services.PayersTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class PayersTransactionsController {

    @Autowired
    PayersTransactionsService payersTransactionsService;

    /**
     * @return
     */
    @PostMapping("/add")
    public void addTransaction(@RequestBody PayerList payerList) {
        if (Objects.isNull(payerList.getPayerRecordList())) {
            throw new NullPointerException("Record List cannot be null");
        }
        payersTransactionsService.addTransaction(payerList.getPayerRecordList());
    }

    /**
     * @return
     */
    @PostMapping("/spend")
    public List<PayerRecord> spendPoints(@RequestBody PointsToSpend pointsToSpend) {
        return payersTransactionsService.spendPoints(pointsToSpend.getPoints());
    }

    /**
     * @return
     */
    @GetMapping("get/balance")
    public Map<String, Integer> getBalance() {
        return payersTransactionsService.getBalance();
    }
}
