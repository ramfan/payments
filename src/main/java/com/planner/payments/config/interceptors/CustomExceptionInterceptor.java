package com.planner.payments.config.interceptors;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import org.springframework.stereotype.Component;

import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

//@Component
public class CustomExceptionInterceptor  extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof Exception) {
            return GraphqlErrorBuilder.newError()
                    .errorType(BAD_REQUEST)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        } else {
            return null;
        }
    }
}