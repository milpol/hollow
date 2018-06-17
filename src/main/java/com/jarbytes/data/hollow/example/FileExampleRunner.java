package com.jarbytes.data.hollow.example;

import com.jarbytes.data.hollow.Aggregator;
import com.jarbytes.data.hollow.InputHandler;
import com.jarbytes.data.hollow.OutputHandler;
import com.jarbytes.data.hollow.Translator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

public class FileExampleRunner
{
    private static final File INPUT_FILE = new File(System.getProperty("java.io.tmpdir"), "input");
    private static final File OUTPUT_FILE = new File(System.getProperty("java.io.tmpdir"), "output");

    public static void main(final String[] args) throws Exception
    {
        final Translator<DataPoint> dataPointTranslator = new DataPointTranslator();
        try (final BufferedReader bufferedReader = new BufferedReader(
                new FileReader(InputDataGenerator.TEST_DATA_FILE));
             final FileOutputStream fileOutputStream = new FileOutputStream(INPUT_FILE)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] dataPointLine = line.split(":");
                fileOutputStream.write(dataPointTranslator.toBytes(new DataPoint(
                        Long.valueOf(dataPointLine[0]),
                        Double.valueOf(dataPointLine[1])
                )));
            }
        }

        System.out.println("Press \"ENTER\" to continue...");
        System.in.read();


        final long start = System.currentTimeMillis();
        final Aggregator<DataPoint> hourAvgDataPointAggregator = new HourAvgDataPointAggregator(
                new InputHandler<>(dataPointTranslator,
                        new BufferedInputStream(new FileInputStream(INPUT_FILE))),
                new OutputHandler<>(dataPointTranslator,
                        new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE))));
        hourAvgDataPointAggregator.aggregate();
        final long end = System.currentTimeMillis();
        System.out.println("End in: " + (end - start));
    }
}
