package com.fetch.accounts.transactions.controllers;

import com.fetch.accounts.transactions.models.PayerList;
import com.fetch.accounts.transactions.models.PayerRecord;
import com.fetch.accounts.transactions.models.PayerRecordTimestamped;
import com.fetch.accounts.transactions.models.PointsToSpend;
import com.fetch.accounts.transactions.services.PayersTransactionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PayersTransactionsControllerTest {

    @Mock
    PayersTransactionsService payersTransactionsService;

    @InjectMocks
    PayersTransactionsController payersTransactionsController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddTransaction() {
        PayerList list = new PayerList();
        List<PayerRecordTimestamped> payerRecordTimestampedList = new ArrayList<>();
        PayerRecordTimestamped payerRecordTimestamped = new PayerRecordTimestamped();
        payerRecordTimestamped.setPayer("Bob");
        payerRecordTimestamped.setPoints(504);
        Date date = new Date();
        payerRecordTimestamped.setTimestamp(date);
        payerRecordTimestampedList.add(payerRecordTimestamped);
        list.setPayerRecordList(payerRecordTimestampedList);

        doNothing().when(payersTransactionsService).addTransaction(payerRecordTimestampedList);

        payersTransactionsController.addTransaction(list);

       verify(payersTransactionsService, times(1)).addTransaction(payerRecordTimestampedList);
    }

    @Test()
    public void testAddTransactionError() {
        PayerList list = new PayerList();

        assertThrows(NullPointerException.class, () -> payersTransactionsController.addTransaction(list));
    }

    @Test
    public void testSpendPoints() {
        List<PayerRecord> payerRecordList = new ArrayList<>();
        payerRecordList.add(new PayerRecord("Bob", 5));
        when(payersTransactionsService.spendPoints(anyInt())).thenReturn(payerRecordList);

        List<PayerRecord> result = payersTransactionsController.spendPoints(new PointsToSpend(50));
        assertEquals(payerRecordList, result);
    }

    @Test
    public void testGetBalance() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Bob", 500);
        map.put("Sarah", 200);

        when(payersTransactionsService.getBalance()).thenReturn(map);

        assertEquals(map, payersTransactionsController.getBalance());
    }
}
