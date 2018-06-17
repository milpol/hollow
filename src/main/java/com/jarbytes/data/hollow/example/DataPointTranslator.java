package com.jarbytes.data.hollow.example;

import com.jarbytes.data.hollow.Translator;

import java.nio.ByteBuffer;

public class DataPointTranslator implements Translator<DataPoint>
{
    @Override
    public byte[] toBytes(final DataPoint entity)
    {
        return ByteBuffer.allocate(getSize())
                .putLong(entity.getTimestamp())
                .putDouble(entity.getValue())
                .array();
    }

    @Override
    public DataPoint fromBytes(final byte[] bytes)
    {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new DataPoint(byteBuffer.getLong(), byteBuffer.getDouble());
    }

    @Override
    public int getSize()
    {
        return 128;
    }
}
