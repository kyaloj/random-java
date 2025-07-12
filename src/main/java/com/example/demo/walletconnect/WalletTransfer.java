package com.example.demo.walletconnect;

import java.math.BigDecimal;

public record WalletTransfer(
        String sourceAccount,
        String DestinationAccount,
        BigDecimal amount,
        BigDecimal debit,
        TransferState transferState,
        String reasons

) {}
