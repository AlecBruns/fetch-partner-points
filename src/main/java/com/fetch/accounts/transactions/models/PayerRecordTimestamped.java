package com.fetch.accounts.transactions.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class PayerRecordTimestamped extends PayerRecord {

    private Date timestamp;
}
