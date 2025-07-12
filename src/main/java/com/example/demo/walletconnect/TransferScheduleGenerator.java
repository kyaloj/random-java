package com.example.demo.walletconnect;

import java.util.List;

public interface TransferScheduleGenerator {
    List<WalletTransfer> generate(List<TransferConfig> transferConfigs);
}
