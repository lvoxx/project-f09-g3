package com.cp2396f09_sem4_grp3.onmart_common.entities;

import java.io.Serializable;

import com.cp2396f09_sem4_grp3.onmart_common.entities.enumeration.EScheduleShift;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonBackReference
    private Schedule schedule;

    private Long employeeId;

    private String employeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", nullable = false)
    private EScheduleShift scheduleShift;

}