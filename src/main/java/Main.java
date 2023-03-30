import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Replace with your MongoDB connection string
        ConnectionString connectionString = new ConnectionString("mongodb+srv://new-user_1:S6NxIVSXQ01v3Scl@cluster0.kw9bxn7.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {

            MongoDatabase database = mongoClient.getDatabase("education");
            // Create an instance of the EduCostStatQueries class with a reference to your database
            EduCostStatQueries eduCostStatQueries = new EduCostStatQueries(database);

            // Execute the getTop5MostExpensiveStates method and store the results in a list
            List<Document> top5ExpensiveStates = eduCostStatQueries.getTop5MostExpensiveStates(2013, "Private", "4-year");

            // Iterate through the results and print them
            for (Document state : top5ExpensiveStates) {
                System.out.println(state.toJson());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
