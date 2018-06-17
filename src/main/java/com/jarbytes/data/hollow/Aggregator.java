package com.jarbytes.data.hollow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public abstract class Aggregator<T>
{
    private final InputHandler<T> inputHandler;
    private final OutputHandler<T> aggregatedOutputHandler;

    protected Aggregator(final InputHandler<T> inputHandler,
                         final OutputHandler<T> aggregatedOutputHandler)
    {
        this.inputHandler = requireNonNull(inputHandler);
        this.aggregatedOutputHandler = requireNonNull(aggregatedOutputHandler);
    }

    public void aggregate()
    {
        try {
            ArrayList<T> currentWindow = new ArrayList<>();
            while (inputHandler.hasNext()) {
                final T nextEntity = inputHandler.next();
                if (isNextWindow(nextEntity)) {
                    aggregatedOutputHandler.consumeUnchecked(aggregate(currentWindow));
                    currentWindow = new ArrayList<>();
                } else {
                    currentWindow.add(nextEntity);
                }
            }
            if (!currentWindow.isEmpty()) {
                aggregatedOutputHandler.consumeUnchecked(aggregate(currentWindow));
            }
        } finally {
            try {
                inputHandler.close();
                aggregatedOutputHandler.close();
            } catch (IOException e) {
                throw new IllegalStateException("Failed on I/O close.", e);
            }
        }
    }

    protected abstract boolean isNextWindow(T entity);

    protected abstract T aggregate(Collection<T> entities);
}
