package com.toptal.caloriecount.dao.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="entry")
public class FoodEntry {

    @Column(name="entry_id")
    @Id
    private String entryId;

    @Column(name="eat_time")
    private String eatTime;

    @Column(name="food_name")
    private String foodName;

    private Float calories;

    @Column(name="creation_timestamp")
    private Timestamp creationTimestamp;

    @Column(name="last_updated_timestamp")
    private Timestamp lastUpdatedTimestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

}
