package com.example.persist;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ReportDao {
    private int id;
    private String userName;
    private String task;
    private Timestamp timeOfTrack;
}
