package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.Cleaner;

public class FileResource {

    private static final Cleaner cleaner = Cleaner.create();

    private final FileWriter writer;
    private final Cleaner.Cleanable cleanable;

    // This nested class defines the cleanup logic
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

    public FileResource(String filePath) throws IOException {
        this.writer = new FileWriter(filePath);
        State state = new State(writer);
        this.cleanable = cleaner.register(this, state);
    }

    public void write(String data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    public void close() throws IOException {
        cleanable.clean(); // Optionally trigger early
    }

    public static void main(String[] args) throws Exception {
        FileResource fr = new FileResource("example.txt");
        fr.write("Hello, Cleaner!\n");

        // Let the cleaner handle it later, or call fr.close() to force it.
        fr = null;

        // Suggest GC to trigger cleaner
        System.gc();
        Thread.sleep(1000); // Give time for cleaner to run
    }
}
