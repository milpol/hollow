package com.jarbytes.data.hollow;

public interface Translator<T>
{
    byte[] toBytes(T entity);

    T fromBytes(byte[] bytes);

    int getSize();
}
