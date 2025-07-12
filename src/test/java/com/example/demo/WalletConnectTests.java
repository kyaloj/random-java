package com.example.demo;

import com.example.demo.walletconnect.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class WalletConnectTests {
    Gson gson = new Gson();
    @Test
    void testProcess() {
        BigDecimal transferFee = new BigDecimal("5.00");

        List<WalletTransfer> expected = List.of(
                new WalletTransfer("ACC001", "ACC002", new BigDecimal("500.00"), new BigDecimal("500.00").add(transferFee), TransferState.PENDING, null),
                new WalletTransfer("ACC001", "ACC003", new BigDecimal("150.00"), new BigDecimal("150.00").add(transferFee), TransferState.PENDING, null),
                new WalletTransfer("ACC003", "ACC004", new BigDecimal("250.00"), new BigDecimal("250.00").add(transferFee), TransferState.PENDING, null),
                new WalletTransfer("ACC005", "ACC001", new BigDecimal("1200.00"), new BigDecimal("1200.00").add(transferFee), TransferState.FAILED, "Insufficient funds")
        );

        TransferScheduleGenerator transferScheduleGenerator = new DefaultTransferScheduleGenerator();
        List<WalletTransfer> actual = transferScheduleGenerator.generate(gson.fromJson(jsonString(), TransfersPayload.class).getTransfers()).stream().sorted(Comparator.comparing(WalletTransfer::sourceAccount)).toList();

        assertEquals(expected, actual);

    }

    private String jsonString() {
        String jsonString = """
        {
          "transfers": [
            {
              "source_account_id": "ACC003",
              "destination_account_id": "ACC004",
              "amount": 250.00
            },
            {
              "source_account_id": "ACC001",
              "destination_account_id": "ACC002",
              "amount": 500.00
            },
            {
              "source_account_id": "ACC005",
              "destination_account_id": "ACC001",
              "amount": 1200.00
            },
            {
              "source_account_id": "ACC001",
              "destination_account_id": "ACC003",
              "amount": 150.00
            }
          ]
        }
        """;

        return jsonString;
    }
}
