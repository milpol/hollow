package com.jarbytes.data.hollow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import static java.util.Objects.requireNonNull;

public class OutputHandler<T> implements AutoCloseable
{
    private final Translator<T> translator;
    private final OutputStream outputStream;

    public OutputHandler(final Translator<T> translator,
                         final OutputStream outputStream)
    {
        this.translator = requireNonNull(translator);
        this.outputStream = requireNonNull(outputStream);
    }

    public void consumeUnchecked(final T entity)
    {
        try {
            consume(entity);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void consume(final T entity) throws IOException
    {
        requireNonNull(entity);
        outputStream.write(translator.toBytes(entity));
    }

    public void closeUnchecked()
    {
        try {
            close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() throws IOException
    {
        outputStream.close();
    }
}
