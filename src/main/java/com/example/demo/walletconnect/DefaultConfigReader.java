package com.example.demo.walletconnect;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DefaultConfigReader implements ConfigReader {
    Gson gson = new Gson();
    @Override
    public List<TransferConfig> read(String path) {
        List<TransferConfig> results = new ArrayList<>();
        try (InputStream inputStream = WalletConnect.class.getClassLoader().getResourceAsStream(path);
             Reader reader = new InputStreamReader(inputStream)) {

            // Check if the file was found
            if (inputStream == null) {
                throw new Exception("File not found: " + path);
            }

            // Parse the JSON directly from the reader
            TransfersPayload payload = gson.fromJson(reader, TransfersPayload.class);

            // Use the loaded configuration
            results = payload.getTransfers();

        } catch (Exception e) {
            System.err.println("Error reading or parsing config file.");
            e.printStackTrace();
        }
        return results;
    }
}
