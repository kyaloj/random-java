package com.example.demo.walletconnect;

import java.util.List;

public class WalletConnect {
    public static void main(String[] args) {
        // read the config
        ConfigReader reader = new DefaultConfigReader();
        List<TransferConfig> configs = reader.read("walletConnect/config.json");


        // Generate the transfers
        TransferScheduleGenerator generator = new DefaultTransferScheduleGenerator();
        List<WalletTransfer> transfers = generator.generate(configs);


        // Process the transfers
        WalletTransferProcessor processor = new DefaultWalletTransferProcessor();
        processor.process(transfers);

    }
}

