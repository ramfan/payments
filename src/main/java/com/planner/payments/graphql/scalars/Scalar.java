package com.planner.payments.graphql.scalars;

import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class Scalar {

public static final GraphQLScalarType dateScalar = GraphQLScalarType
        .newScalar()
        .name("Date")
        .coercing(new DateScalar())
        .build();

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(dateScalar);
    }
}
