package com.planner.payments.config.interceptors;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import static org.springframework.graphql.execution.ErrorType.UNAUTHORIZED;

@Component
public class CustomExceptionInterceptor  extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AccessDeniedException && env.getFieldDefinition().getName().equals("refreshSession")) {
            return GraphqlErrorBuilder.newError()
                    .message("Unauthorized").errorType(UNAUTHORIZED)
                    .build();
        } else if(ex instanceof Exception){
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .build();
        }else {
            return null;
        }
    }
}