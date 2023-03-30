package com.example.eduservice;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EduCostStatQueries {

    private final MongoDatabase database;

    public EduCostStatQueries(MongoDatabase database) {
        this.database = database;
    }

    // Query 1
    public Document getCostByParams(int year, String state, String type, String length, String expense) {
        MongoCollection<Document> collection = database.getCollection("EduCostStat");

        Document result = collection.find(Filters.and(
                Filters.eq("year", year),
                Filters.eq("state", state),
                Filters.eq("type", type),
                Filters.eq("length", length)
        )).first();

        if (result != null) {
            saveQueryOne(year, state, type, length, expense);
            return new Document("cost", result.get(expense));
        } else {
            return new Document("error", "No matching data found.");
        }
    }

    // Query 2
    public List<Document> getTop5MostExpensiveStates(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStat");

        List<Document> results = collection.find(Filters.and(
                Filters.eq("year", year),
                Filters.eq("type", type),
                Filters.eq("length", length)
        )).sort(Sorts.descending("total")).limit(5).into(new ArrayList<>());

        if (!results.isEmpty()) {
            saveQueryTwo(year, type, length);
        }

        return results;
    }

    // Query 3
    public List<Document> getTop5MostEconomicalStates(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStat");

        List<Document> results = collection.find(Filters.and(
                Filters.eq("year", year),
                Filters.eq("type", type),
                Filters.eq("length", length)
        )).sort(Sorts.ascending("total")).limit(5).into(new ArrayList<>());

        if (!results.isEmpty()) {
            saveQueryThree(year, type, length);
        }

        return results;
    }

    // Query 4
    public List<Document> getTop5GrowthRateStates(int baseYear, String type, String length, int range) {
        MongoCollection<Document> collection = database.getCollection("EduCostStat");

        List<Document> results = collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(Filters.eq("type", type), Filters.eq("length", length))),
                Aggregates.match(Filters.in("year", baseYear - range, baseYear)),
                Aggregates.group("$state",
                        Accumulators.avg("avgOverallExpense", "$total")),
                Aggregates.sort(Sorts.descending("avgOverallExpense")),
                Aggregates.limit(5)
        )).into(new ArrayList<>());

        if (!results.isEmpty()) {
            saveQueryFour(baseYear, type, length, range);
        }

        return results;
    }

    // Query 5
    public List<Document> getRegionAvgExpense(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStat");

        List<Document> results = collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.and(Filters.eq("year", year), Filters.eq("type", type), Filters.eq("length", length))),
                Aggregates.group("$region",
                        Accumulators.avg("avgOverallExpense", "$total")),
                Aggregates.sort(Sorts.ascending("_id"))
        )).into(new ArrayList<>());

        if (!results.isEmpty()) {
            saveQueryFive(year, type, length);
        }

        return results;
    }

    // Other queries (4 and 5) should be implemented similarly, with their corresponding save methods.

    // Save methods for each query type
    private void saveQueryOne(int year, String state, String type, String length, String expense) {
        MongoCollection<Document> collection = database.getCollection("EduCostStatQueryOne");
        Document document = new Document()
                .append("year", year)
                .append("state", state)
                .append("type", type)
                .append("length", length)
                .append("expense", expense);
        collection.insertOne(document);
    }

    private void saveQueryTwo(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStatQueryTwo");
        Document document = new Document()
                .append("year", year)
                .append("type", type)
                .append("length", length);
        collection.insertOne(document);
    }

    private void saveQueryThree(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStatQueryThree");
        Document document = new Document()
                .append("year", year)
                .append("type", type)
                .append("length", length);
        collection.insertOne(document);
    }

    private void saveQueryFour(int baseYear, String type, String length, int range) {
        MongoCollection<Document> collection = database.getCollection("EduCostStatQueryFour");
        Document document = new Document()
                .append("baseYear", baseYear)
                .append("type", type)
                .append("length", length)
                .append("range", range);
        collection.insertOne(document);
    }

    private void saveQueryFive(int year, String type, String length) {
        MongoCollection<Document> collection = database.getCollection("EduCostStatQueryFive");
        Document document = new Document()
                .append("year", year)
                .append("type", type)
                .append("length", length);
        collection.insertOne(document);
    }

}
