package com.chxxyx0.moviebatch.model.kobis.boxOffice;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoxOfficeResult {

    private String boxofficeType;
    private String showRange;
    private List<BoxOfficeResultList> dailyBoxOfficeList;
}
