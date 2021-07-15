package com.fetch.accounts.transactions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayerRecord {

    private String payer;

    private int points;
}
