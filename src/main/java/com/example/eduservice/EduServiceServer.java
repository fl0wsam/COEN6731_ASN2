package com.example.eduservice;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class EduServiceServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051; // You can choose any available port
        Server server = ServerBuilder.forPort(port)
                .addService(new EduServiceImpl())
                .build()
                .start();
        System.out.println("Server started, listening on " + port);
        server.awaitTermination();
    }
}
