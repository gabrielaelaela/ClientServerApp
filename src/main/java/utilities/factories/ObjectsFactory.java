package utilities.factories;

import utilities.users.User;

import java.io.IOException;

public interface ObjectsFactory {
    public Object buildWithQuestions() throws IOException;
    public Object buildWithoutQuestions(User user) throws IOException;
}
