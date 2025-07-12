package com.example.demo.walletconnect;

import java.util.List;

// For GSON parser
public class TransfersPayload {
    private List<TransferConfig> transfers;

    // Getter
    public List<TransferConfig> getTransfers() {
        return transfers;
    }
}
