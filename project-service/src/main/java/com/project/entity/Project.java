package com.project.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "project")
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class Project {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = true)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    private Type type;

    @Column(length = 20, columnDefinition = "varchar(20) default 'DRAFT' NOT NULL")
    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", updatable = false)//, updatable = false
    private List<Section> sections;

    public List<Section> getSections() {
        return sections == null ? new ArrayList<>() : sections;
    }
}