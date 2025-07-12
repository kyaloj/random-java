package com.example.demo.walletconnect;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public record TransferConfig(
        @SerializedName("source_account_id") String sourceAccountId,
        @SerializedName("destination_account_id") String destinationAccountId,
        BigDecimal amount
) {}
