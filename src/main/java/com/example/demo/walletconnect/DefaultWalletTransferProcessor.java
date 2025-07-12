package com.example.demo.walletconnect;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class DefaultWalletTransferProcessor implements WalletTransferProcessor {

    @Override
    public void process(List<WalletTransfer> transfers) {
        System.out.println("--- Processing Transfers ---");

        int success = 0;
        int failed = 0;
        BigDecimal transferVolume = new BigDecimal("0.00");

        List<WalletTransfer> sortedTransfers = transfers.stream()
                .sorted(Comparator.comparing(WalletTransfer::sourceAccount))
                .toList();

        for (WalletTransfer walletTransfer : sortedTransfers) {
            TransferState state = walletTransfer.transferState();
            String source = walletTransfer.sourceAccount();
            String destination = walletTransfer.DestinationAccount();
            BigDecimal amount = walletTransfer.amount();
            BigDecimal debit = walletTransfer.debit();
            String reason = walletTransfer.reasons();

            if (state.equals(TransferState.PENDING)) {
                System.out.println(state + " From: " + source + " -> To: " + destination + " | Amount: $" + amount + " | Debit: $" + debit);
                transferVolume = transferVolume.add(amount);
                success ++;

            } else {
                System.out.println(state + " From: " + source + " -> To: " + destination + " | Amount: $" + amount + " | Reason: " + reason);
                failed ++;
            }
        }

        System.out.println("--- Summary ---");
        System.out.println("Total Transfers to Process: " + success + "\n" +
                "Total Failed Transfers: " + failed + "\n" +
                "Total Transfer Volume (Successful): $" + transferVolume);
    }
}
