package com.chxxyx0.moviebatch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieCode {

    @Id
    private long code;

    @Column
    private String title;

    @Column(length = 1)
    private String batchStatus;

}
