package com.planner.payments.service.jwt;

public record JwtClaimsRecord(Long id, String[] roleList, String username){}
