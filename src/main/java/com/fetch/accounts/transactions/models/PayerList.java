package com.fetch.accounts.transactions.models;

import lombok.Data;

import java.util.List;

@Data
public class PayerList {

    private List<PayerRecordTimestamped> payerRecordList;
}
