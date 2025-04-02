package org.example;
import java.io.FileWriter;
import java.io.IOException;

public class NoCleanerFileResource {

    private final FileWriter writer;

    public NoCleanerFileResource(String filePath) throws IOException {
        this.writer = new FileWriter(filePath);
    }

    public void write(String data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    public void close() throws IOException {
        System.out.println("Closing file...");
        writer.close();
    }

    public static void main(String[] args) {
        try {
            NoCleanerFileResource fr = new NoCleanerFileResource("example.txt");
            fr.write("Hello, manual close!\n");
            fr.close(); // Manual cleanup
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
        }
    }
}
