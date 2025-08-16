package com.keyin.binarySearchTree.bst;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TreeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numbers;
    private String treeJson;

    private LocalDateTime timestamp;

    public TreeRecord(String numbers, String treeJson) {
        this.numbers = numbers;
        this.treeJson = treeJson;
        this.timestamp = LocalDateTime.now();
    }
    public TreeRecord() {
    }

    @PrePersist
    void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

}
