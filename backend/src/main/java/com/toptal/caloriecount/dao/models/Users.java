package com.toptal.caloriecount.dao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class Users {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String name;
    private String type;
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;
    private String password;

    @Column(name="reset_token")
    private String resetToken;

    @Column(name="reset_token_timestamp")
    private Date resetTokenTimestamp;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_timestamp")
    private Timestamp createdTimestamp;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
