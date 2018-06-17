package com.jarbytes.data.hollow;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

public class InputHandler<T> implements Iterator<T>, AutoCloseable
{
    private final Translator<T> translator;
    private final InputStream inputStream;

    public InputHandler(final Translator<T> translator,
                        final InputStream inputStream)
    {
        this.translator = requireNonNull(translator);
        this.inputStream = requireNonNull(inputStream);
    }

    @Override
    public boolean hasNext()
    {
        try {
            return inputStream.available() > 0;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public T next()
    {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            final int entitySize = translator.getSize();
            final byte[] bytes = new byte[entitySize];
            if (entitySize != inputStream.read(bytes)) {
                throw new IllegalStateException("Corrupted input stream.");
            }
            return translator.fromBytes(bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException
    {
        inputStream.close();
    }
}
