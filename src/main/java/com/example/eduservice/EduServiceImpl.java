package com.example.eduservice;

import com.example.eduservice.EduServiceGrpc.EduServiceImplBase;
import io.grpc.stub.StreamObserver;

public class EduServiceImpl extends EduServiceImplBase {

    @Override
    public void getCostByParams(GetCostByParamsRequest request, StreamObserver<GetCostByParamsResponse> responseObserver) {
        // Process the request and compute the result
        String paramName1 = request.getState();
        String paramName2 = request.getType();
        double computedResult = computeResult(paramName1, paramName2);

        // Create the response object
        GetCostByParamsResponse response = GetCostByParamsResponse.newBuilder()
                .setCost(computedResult)
                .build();

        // Send the response to the client
        responseObserver.onNext(response);

        // Mark the call as completed
        responseObserver.onCompleted();
    }

    private double computeResult(String paramName1, String paramName2) {
        // Replace this with your actual implementation logic
        // This is just a dummy implementation
        return paramName1.length() + paramName2.length();
    }

    // Implement other service methods as needed
}
