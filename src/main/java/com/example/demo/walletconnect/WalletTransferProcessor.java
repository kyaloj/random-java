package com.example.demo.walletconnect;

import java.util.List;

public interface WalletTransferProcessor {
    void process(List<WalletTransfer> transfers);
}
