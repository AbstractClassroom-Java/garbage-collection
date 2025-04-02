# FileResource: Auto-cleaned File Writer Using Java Cleaner

This class demonstrates how to safely manage a `FileWriter` resource in Java using the `Cleaner` API. It ensures that the file resource is automatically closed either explicitly by the user or implicitly by the garbage collector.

## Overview

`FileResource` wraps a `FileWriter` and ensures it gets closed when the object is no longer in use, using Java's `Cleaner` mechanism.

## Class Breakdown

### Package Declaration

```java
package org.example;
```

This line places the `FileResource` class inside the `org.example` package.

### Imports

```java
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.Cleaner;
```

These imports are required to:
- Use `FileWriter` for writing to a file.
- Handle `IOException`.
- Use Java's `Cleaner` API for resource cleanup.

### Cleaner Initialization

```java
private static final Cleaner cleaner = Cleaner.create();
```

Creates a single shared `Cleaner` instance. The `Cleaner` will manage the cleanup tasks for any object that registers with it.

### FileResource Fields

```java
private final FileWriter writer;
private final Cleaner.Cleanable cleanable;
```

- `writer`: The actual `FileWriter` used to write to the file.
- `cleanable`: A reference to the cleanup task registered with the `Cleaner`. This can be used to trigger cleanup early if needed.

### Nested `State` Class

```java
private static class State implements Runnable {
    private FileWriter writer;

    State(FileWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            System.out.println("Cleaning up: Closing file...");
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}
```

- `State` defines the logic to run when the object is garbage collected.
- It implements `Runnable`, and its `run()` method is responsible for closing the file.
- If the `Cleaner` triggers this task, it prints a message and attempts to close the writer.

### Constructor

```java
public FileResource(String filePath) throws IOException {
    this.writer = new FileWriter(filePath);
    State state = new State(writer);
    this.cleanable = cleaner.register(this, state);
}
```

- Creates a new `FileWriter` with the given file path.
- Wraps the writer in a `State` object.
- Registers the current `FileResource` instance and its `State` cleanup logic with the `Cleaner`.

### `write` Method

```java
public void write(String data) throws IOException {
    writer.write(data);
    writer.flush();
}
```

Writes a string to the file and flushes the output to ensure it is written immediately.

### `close` Method

```java
public void close() throws IOException {
    cleanable.clean(); // Optionally trigger early
}
```

Explicitly triggers the cleanup logic to close the file. This can be called manually instead of waiting for the garbage collector.

### `main` Method (Usage Example)

```java
public static void main(String[] args) throws Exception {
    FileResource fr = new FileResource("example.txt");
    fr.write("Hello, Cleaner!\n");

    fr = null;

    System.gc();
    Thread.sleep(1000);
}
```

- Creates a `FileResource` and writes a line to `example.txt`.
- Sets the object to `null`, making it eligible for garbage collection.
- Calls `System.gc()` to suggest garbage collection, then sleeps for 1 second to allow the `Cleaner` to run.

## Summary

This class demonstrates a safe and automated way to manage non-memory resources using Java's `Cleaner` API. It avoids resource leaks by ensuring the file is closed, even if the user forgets to do it manually.
