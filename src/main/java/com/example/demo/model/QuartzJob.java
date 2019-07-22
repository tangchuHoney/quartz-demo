package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuartzJob  {

    private static final long serialVersionUID = 1L;

    private String jobClassName;

    private String cronExpression;

    private String parameter;

    private String description;

    private int status;

}