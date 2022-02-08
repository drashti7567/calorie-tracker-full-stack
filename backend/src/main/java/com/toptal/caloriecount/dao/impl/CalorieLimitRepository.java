package com.toptal.caloriecount.dao.impl;

import com.toptal.caloriecount.dao.models.CalorieLimit;
import com.toptal.caloriecount.dao.models.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalorieLimitRepository extends JpaRepository<CalorieLimit, String> {
}
