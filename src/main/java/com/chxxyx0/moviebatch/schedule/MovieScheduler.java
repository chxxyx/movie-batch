package com.chxxyx0.moviebatch.schedule;

import com.chxxyx0.moviebatch.entity.Movie;
import com.chxxyx0.moviebatch.entity.MovieCode;
import com.chxxyx0.moviebatch.job.BoxOfficeJobConfig;
import com.chxxyx0.moviebatch.service.MovieService;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MovieScheduler {

    private final MovieService movieService;
    private final JobLauncher jobLauncher;
    private final BoxOfficeJobConfig boxOfficeJobConfig;

    @Scheduled(cron ="0 0 0 * * *") //매일 12시에 실행
    public void executeDailyJob () throws ParseException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addString("datetime", LocalDateTime.now().toString())
                .toJobParameters();

            jobLauncher.run(
                boxOfficeJobConfig.dailyBoxOfficeJob(),
                jobParameters  // job parameter 설정
            );
        } catch (JobExecutionException ex) {
            log.info(ex.getMessage());
            ex.printStackTrace();
        }

    }

//    @Scheduled(cron = "0 0 0 * * *")
//    public void saveDayMovieInfo() throws ParseException {
//        LocalDate day = LocalDate.now().minusDays(1);
//        String dayString = day.toString().replace("-", "");
//
//        List<Movie> movieList = new ArrayList<>();
//        List<MovieCode> movieCodeList = movieService.saveMovieCode(
//            dayString);
//
//        for (MovieCode movieCode : movieCodeList) {
//            movieList.add(
//                movieService.saveMovieInfoByMovieCode(movieCode.getCode()));
//        }
//
//    }
}
