package com.example.app_weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private String name, description;
    private Long userId;
    private Timestamp dueDate;
}
