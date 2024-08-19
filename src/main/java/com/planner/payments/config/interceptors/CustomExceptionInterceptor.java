package com.planner.payments.config.interceptors;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.graphql.execution.ErrorType.UNAUTHORIZED;

@Component
public class CustomExceptionInterceptor  extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if ((ex instanceof AccessDeniedException || ex instanceof NullPointerException) && env.getFieldDefinition().getName().equals("refreshSession")) {
            return GraphqlErrorBuilder.newError()
                    .message("Unauthorized").errorType(UNAUTHORIZED)
                    .build();
        } else if (ex instanceof AccessDeniedException) {
            var context = SecurityContextHolder.getContext();
            var authentication = context.getAuthentication();
            var authorities = authentication.getAuthorities();

            if (!authentication.isAuthenticated() ||
                    (authorities.size() == 1 &&
                            ((SimpleGrantedAuthority)authorities.toArray()[0])
                                    .getAuthority()
                                    .equals("ROLE_ANONYMOUS"))) {
                return GraphqlErrorBuilder.newError()
                        .message("Unauthorized").errorType(UNAUTHORIZED)
                        .build();
            }
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .build();
        } else if (ex instanceof Exception) {
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .build();
        } else {
            return null;
        }
    }



}