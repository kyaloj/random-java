package com.example.demo.walletconnect;

import java.math.BigDecimal;
import java.util.Map;

public class DefaultValidator implements Validator{
    private final Map<String, BigDecimal> accountBalances;
    private final BigDecimal transferFee;

    DefaultValidator(Map<String, BigDecimal> initialBalances, BigDecimal transferFee) {
        this.accountBalances = initialBalances;
        this.transferFee = transferFee;
    }

    @Override
    public WalletTransfer validate(TransferConfig config) {
        String reason = null;
        TransferState state = TransferState.PENDING;

        String source = config.sourceAccountId();
        String destination = config.destinationAccountId();
        BigDecimal amount = config.amount();
        BigDecimal debit = amount.add(transferFee);

        if (debit.compareTo(accountBalances.get(source)) > 0) {
            reason = "Insufficient funds";
            state = TransferState.FAILED;
        }

        return new WalletTransfer(source, destination, amount, debit, state, reason);
    }
}
