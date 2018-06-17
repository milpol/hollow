package com.jarbytes.data.hollow.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemoryExampleRunner
{
    public static void main(final String[] args) throws Exception
    {
        final List<DataPoint> dataPoints = new ArrayList<>();
        try (final BufferedReader bufferedReader = new BufferedReader(
                new FileReader(InputDataGenerator.TEST_DATA_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] dataPointLine = line.split(":");
                dataPoints.add(new DataPoint(
                        Long.valueOf(dataPointLine[0]),
                        Double.valueOf(dataPointLine[1])
                ));
            }
        }
        System.out.println("Press \"ENTER\" to continue...");
        System.in.read();
        final long start = System.currentTimeMillis();
        int currentHour = 0;
        List<DataPoint> currentWindow = new ArrayList<>();
        for (DataPoint dataPoint : dataPoints) {
            final int dataPointHour = LocalDateTime.ofEpochSecond(
                    dataPoint.getTimestamp(), 0, ZoneOffset.UTC).getHour();
            if (dataPointHour != currentHour) {
                currentHour = dataPointHour;
                System.out.println(aggregate(currentWindow));
                currentWindow.clear();
            } else {
                currentWindow.add(dataPoint);
            }
        }
        final long end = System.currentTimeMillis();
        System.out.println("End in: " + (end - start));
    }

    private static DataPoint aggregate(final Collection<DataPoint> dataPoints)
    {
        final long avgTimestamp = (long) dataPoints.stream()
                .mapToLong(DataPoint::getTimestamp)
                .average()
                .orElse(0);
        final double avgValue = dataPoints.stream()
                .mapToDouble(DataPoint::getValue)
                .average()
                .orElse(0);
        return new DataPoint(avgTimestamp, avgValue);
    }
}
