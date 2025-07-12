package com.example.demo.walletconnect;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultTransferScheduleGenerator implements TransferScheduleGenerator {
    private Map<String, BigDecimal> accountBalances = new HashMap<>();

    // Instance initializer block
    {
        accountBalances.put("ACC001", new BigDecimal("700.00"));
        accountBalances.put("ACC002", new BigDecimal("1500.00"));
        accountBalances.put("ACC003", new BigDecimal("300.00"));
        accountBalances.put("ACC004", new BigDecimal("5000.00"));
        accountBalances.put("ACC005", new BigDecimal("1000.00"));
    }

    private final BigDecimal transferFee = new BigDecimal("5.00");
    private final DefaultValidator validator = new DefaultValidator(accountBalances, transferFee);


    @Override
    public List<WalletTransfer> generate(List<TransferConfig> transferConfigs) {
        // For each config, Validate and convert to WalletTransfer
        // If successfully, Update the account.
        // This is not thread safe
        return transferConfigs.stream()
                .map((config) -> validator.validate(config))
                .peek(transfer -> {
                    if(transfer.transferState().equals(TransferState.PENDING)) {
                        BigDecimal currentBalance = accountBalances.get(transfer.sourceAccount());
                        BigDecimal newBalance = currentBalance.subtract(transfer.amount()).subtract(transferFee);
                        accountBalances.put(transfer.sourceAccount(), newBalance);
                    }
                })
                .collect(Collectors.toList());
    }
}
