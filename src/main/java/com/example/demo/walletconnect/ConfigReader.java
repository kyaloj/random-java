package com.example.demo.walletconnect;

import java.util.List;

public interface ConfigReader {
    List<TransferConfig> read(String path);
}
