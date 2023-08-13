package utilities.deserializers;

import com.google.gson.Gson;

public class ClassDeserializer {
    public static Class deserializeString(String input) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(input, Class.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
