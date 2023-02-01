package com.chxxyx0.moviebatch.model.kobis.movieInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actors {

    private String peopleNm;
    private String peopleNmEn;
    private String cast;
    private String castEn;
}
