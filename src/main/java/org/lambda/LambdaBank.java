package org.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaBank implements RequestHandler<BankRequest, BankResponse> {
    @Override
    public BankResponse handleRequest(BankRequest bankRequest, Context context) {
        return null;
    }
}