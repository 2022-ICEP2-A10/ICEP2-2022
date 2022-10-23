package util;

import lombok.AllArgsConstructor;
import repository.FileBaseDatabase;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
public class Sequence implements FileBaseDatabase {
    private Long sequence;

    public Long nextSequence() {
        return sequence++;
    }

    @Override
    public void destroy() {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./data/next_sequence"), StandardCharsets.UTF_8))) {
            writer.write(sequence.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
