package com.search.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "publish_data_payload")
@Entity
@Data
public class PublishDataPayload {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Lob
    @Column(name = "content")
    private String content;

}