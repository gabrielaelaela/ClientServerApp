package utilities.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utilities.organizations.Organization;
import utilities.upgradedcollections.UpgradedPriorityQueue;

public class Serializer {
    public static String serialize(UpgradedPriorityQueue<Organization> organizationUpgradedPriorityQueue) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(organizationUpgradedPriorityQueue);
    }

    public static String serialize(Class objectClass) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(objectClass);
    }

    public static String serialize(String input) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(input);
    }

    public static String serialize(Object object) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(object);
    }
}
