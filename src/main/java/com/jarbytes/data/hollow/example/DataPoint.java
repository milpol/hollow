package com.jarbytes.data.hollow.example;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class DataPoint
{
    private final long timestamp;
    private final double value;

    public DataPoint(final long timestamp,
                     final double value)
    {
        this.timestamp = requireNonNull(timestamp);
        this.value = requireNonNull(value);
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public double getValue()
    {
        return value;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DataPoint dataPoint = (DataPoint) o;
        return timestamp == dataPoint.timestamp &&
                Double.compare(dataPoint.value, value) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(timestamp, value);
    }

    @Override
    public String toString()
    {
        return "DataPoint{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}
