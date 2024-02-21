import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public class Utils<T> {
    public static final String USER_API = "https://jsonplaceholder.typicode.com/users";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public List<T> toList(InputStream inputStream) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, new TypeReference<>() {});
        }
        catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

    public T toObject(InputStream inputStream) {
        try {
            return (T) OBJECT_MAPPER.readValue(inputStream, Object.class);
        }
        catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

    public String toJson(T user) {
        try {
            return OBJECT_MAPPER.writeValueAsString(user);
        }
        catch (JsonProcessingException exc) {
            throw new UncheckedIOException(exc);
        }
    }
}
