package com.fetch.accounts.transactions.configs;

import com.fetch.accounts.transactions.models.PayerRecordTimestamped;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringConfiguration {

    @Bean
    public List<PayerRecordTimestamped> getListOfRecords() {
        return new ArrayList<>();
    }
}
