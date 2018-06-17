package com.jarbytes.data.hollow.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.Instant;

public class InputDataGenerator
{
    static final File TEST_DATA_FILE = new File(System.getProperty("java.io.tmpdir"), "test");
    private static final int INPUTS = 5_000_000;

    public static void main(final String[] args) throws Exception
    {
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TEST_DATA_FILE))) {
            for (int i = 0; i < INPUTS; ++i) {
                final String line = Instant.ofEpochSecond(i).getEpochSecond() + ":" + Math.random();
                bufferedWriter.append(line);
                bufferedWriter.newLine();
            }
        }
    }
}
