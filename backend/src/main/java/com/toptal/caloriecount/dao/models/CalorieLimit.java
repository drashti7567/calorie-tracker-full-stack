package com.toptal.caloriecount.dao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="calorie_limit")
public class CalorieLimit {
    @Id
    @Column(name="user_id")
    private String userId;

    @Column(name="basic_limit")
    private Float basicLimit;

    private Float carbs;
    private Float protein;
    private Float fat;
    private Float height;
    private Float weight;

    @Column(name="last_updated_timestamp")
    private Timestamp lastUpdatedTimestamp;
}
