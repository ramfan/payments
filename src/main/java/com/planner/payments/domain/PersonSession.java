package com.planner.payments.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

@Entity(name = "person_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person person;

    @Column(name = "refresh_token", columnDefinition = "uuid")
    private UUID refreshToken;

    private Date createdAt;

    @NotNull
    private Long expiresIn;
}
