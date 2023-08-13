package utilities.deserializers;

import com.google.gson.Gson;
import utilities.organizations.Organization;
import utilities.upgradedcollections.UpgradedPriorityQueue;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PriorityQueueDeserializer {
    private static String filename;

    /**
     * @return collection
     * @throws IOException
     */
    public static UpgradedPriorityQueue deserializeFile(String filename) throws IOException {
        UpgradedPriorityQueue<Organization> upgradedPriorityQueue = new UpgradedPriorityQueue<>();

        Reader reader = Files.newBufferedReader(Paths.get(filename));

        try {
            Gson gson = new Gson();
            Organization[] organizations = gson.fromJson(reader, Organization[].class);
            for (Organization organization: organizations) {
                upgradedPriorityQueue.add(organization);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            reader.close();
        }

        return upgradedPriorityQueue;
    }

    public static UpgradedPriorityQueue deserializeString(String input) {
        UpgradedPriorityQueue<Organization> upgradedPriorityQueue = new UpgradedPriorityQueue<>();

        try {
            Gson gson = new Gson();
            Organization[] organizations = gson.fromJson(input, Organization[].class);
            for (Organization organization: organizations) {
                upgradedPriorityQueue.add(organization);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return upgradedPriorityQueue;
    }
}
