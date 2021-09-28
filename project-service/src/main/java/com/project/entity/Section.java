package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "section")
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class Section {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "project_id")
    private Long projectId;
}