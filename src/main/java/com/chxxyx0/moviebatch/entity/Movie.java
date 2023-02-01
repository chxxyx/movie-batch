package com.chxxyx0.moviebatch.entity;

import com.chxxyx0.moviebatch.type.MovieStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Movie {

    @Id
    private long code;

    @Column
    private String title;

    @Column
    private String actors;

    @Column
    private String directors;

    @Column
    private String genre;

    @Column
    private String nation;

    @Column
    private long runTime;

    @Column
    private LocalDateTime openDt;

    @Column
    @Enumerated(EnumType.STRING)
    private MovieStatus status;

//    /*영화상세정보 입력 유무*/
//    @Column(length = 1)
//    private String batchStatus;

}
