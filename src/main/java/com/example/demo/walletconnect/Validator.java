package com.example.demo.walletconnect;

public interface Validator {
    WalletTransfer validate(TransferConfig config);
}
