package com.fetch.accounts.transactions.services.impl;

import com.fetch.accounts.transactions.models.PayerRecord;
import com.fetch.accounts.transactions.models.PayerRecordTimestamped;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PayersTransactionsServiceImplTest {

    private PayersTransactionsServiceImpl service;

    private List<PayerRecordTimestamped> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
       PayerRecordTimestamped payer1 = new PayerRecordTimestamped();
       payer1.setPayer("Jill");
       payer1.setPoints(500);
       payer1.setTimestamp(new Date());

        PayerRecordTimestamped payer2 = new PayerRecordTimestamped();
        payer2.setPayer("Joe");
        payer2.setPoints(50);
        Date date = new Date();
        date.setTime(50);
        payer2.setTimestamp(date);

        PayerRecordTimestamped payer3 = new PayerRecordTimestamped();
        payer3.setPayer("Jill");
        payer3.setPoints(50);
        Date date2 = new Date();
        date2.setTime(5220);
        payer3.setTimestamp(date);

        list.add(payer1);
        list.add(payer2);
        list.add(payer3);
        service = new PayersTransactionsServiceImpl(list);
    }

    @Test
    public void testAddTransaction() {
        PayerRecordTimestamped expected = new PayerRecordTimestamped();
        expected.setPayer("Henry");
        expected.setPoints(20);
        expected.setTimestamp(new Date());
        List<PayerRecordTimestamped> newList = new ArrayList<>();
        newList.add(expected);

        service.addTransaction(newList);

       assertTrue(list.contains(expected));
    }

    @Test
    public void testSpendPoints() {
        List<PayerRecord> expected = new ArrayList<>();
        expected.add(new PayerRecord("Joe", -50));
        expected.add(new PayerRecord("Jill", -550));

        assertEquals(expected, service.spendPoints(600));
    }

    @Test
    public void testSpendPointsOverSpend() {
        List<PayerRecord> expected = new ArrayList<>();
        expected.add(new PayerRecord("Joe", -50));
        expected.add(new PayerRecord("Jill", -550));
        expected.add(new PayerRecord("Notice: Total Balance is not high enough. Remaining unspent points are listed.", 200));

        assertEquals(expected, service.spendPoints(800));
    }

    @Test
    public void getBalance() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Jill", 550);
        expected.put("Joe", 50);

        assertEquals(expected, service.getBalance()
        );
    }

}
