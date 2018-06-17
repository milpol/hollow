package com.jarbytes.data.hollow.example;

import com.jarbytes.data.hollow.Aggregator;
import com.jarbytes.data.hollow.InputHandler;
import com.jarbytes.data.hollow.OutputHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

public class HourAvgDataPointAggregator extends Aggregator<DataPoint>
{
    private int currentHour;

    protected HourAvgDataPointAggregator(final InputHandler<DataPoint> inputHandler,
                                         final OutputHandler<DataPoint> aggregatedOutputHandler)
    {
        super(inputHandler, aggregatedOutputHandler);
    }

    @Override
    protected boolean isNextWindow(final DataPoint dataPoint)
    {
        final int dataPointHour = LocalDateTime.ofEpochSecond(
                dataPoint.getTimestamp(), 0, ZoneOffset.UTC).getHour();
        if (this.currentHour != dataPointHour) {
            this.currentHour = dataPointHour;
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected DataPoint aggregate(final Collection<DataPoint> dataPoints)
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
