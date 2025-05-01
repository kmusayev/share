package managementServices;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DataService {
	private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

	//Data center.. Handles read and write to file
	public static <T> List<T> readList(String path, TypeReference<List<T>> typeRef) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                return Collections.emptyList();
            }
            return mapper.readValue(file, typeRef);
        } catch (Exception e) {
            System.err.println("Failed to read file: " + path);
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static <T> void writeList(String path, List<T> data) {
        try {
            File file = new File(path);
            File parentDir = file.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created) {
                    System.err.println("Failed to create directory: " + parentDir.getAbsolutePath());
                }
            }

            mapper.writeValue(file, data);
        } catch (Exception e) {
            System.err.println("Failed to write file: " + path);
            e.printStackTrace();
        }
    }
}