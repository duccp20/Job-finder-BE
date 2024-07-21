package com.example.jobfinder.data.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private Date birthDay;

    //    @ManyToOne
    //    @JoinColumn(name = "location_id")
    //    private Location location;

    @Column(name = "address")
    private String address;

    @Column(name = "mail_receive")
    private boolean mailReceive;

    @JsonIgnore
    @Column(name = "password_forgot_token")
    private String passwordForgotToken;

    @JsonIgnore
    @Column(name = "token_active")
    private String tokenActive;

    @JsonIgnore
    @Column(columnDefinition = "mediumtext")
    private String refreshToken;

    //    @ManyToOne(fetch = FetchType.EAGER)
    //    @JoinColumn(name = "social_account_id",)
    //    private SocialAccount socialAccount;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @OneToMany
    @JsonIgnoreProperties(value = "user")
    private List<Token> tokens = new ArrayList<>();
}
