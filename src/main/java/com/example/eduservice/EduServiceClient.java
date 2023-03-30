package com.example.eduservice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class EduServiceClient {
    public static void main(String[] args) {
        // Create a channel to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Create a blocking stub for the service
        EduServiceGrpc.EduServiceBlockingStub stub = EduServiceGrpc.newBlockingStub(channel);

        // Create a request object
        GetCostByParamsRequest request = GetCostByParamsRequest.newBuilder()
                .setExpense("value1") // Replace these with actual request parameters
                .setLength("value2")
                .build();

        // Call the service method and get the response
        GetCostByParamsResponse response = stub.getCostByParams(request);

        // Print the response
        System.out.println("Response received: " + response.getCost());

        // Shutdown the channel
        channel.shutdown();
    }
}
