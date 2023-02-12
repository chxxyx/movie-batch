package com.chxxyx0.moviebatch.service;

import com.chxxyx0.moviebatch.api.MovieApi;
import com.chxxyx0.moviebatch.entity.Movie;
import com.chxxyx0.moviebatch.entity.MovieCode;
import com.chxxyx0.moviebatch.model.InputDates;
import com.chxxyx0.moviebatch.model.kobis.boxOffice.BoxOffice;
import com.chxxyx0.moviebatch.model.kobis.boxOffice.BoxOfficeResultList;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.Actors;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.Directors;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.Genres;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.MovieInfo;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.MovieInfoOutput;
import com.chxxyx0.moviebatch.model.kobis.movieInfo.Nations;
import com.chxxyx0.moviebatch.repository.MovieCodeRepository;
import com.chxxyx0.moviebatch.repository.MovieRepository;
import com.chxxyx0.moviebatch.type.MovieStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

	private final MovieApi movieApi;
	private final MovieCodeRepository movieCodeRepository;
	private final MovieRepository movieRepository;

	public List<MovieCode> saveMovieCode(String date) {
		BoxOffice boxOffice = movieApi.fetchBoxOfficeResult(date);
		List<MovieCode> movieCodeList = new ArrayList<>();
		for (BoxOfficeResultList item : boxOffice.getBoxOfficeResult().getDailyBoxOfficeList()) {
			movieCodeList.add(
				MovieCode.builder().code(Long.parseLong(item.getMovieCd())).title(item.getMovieNm())
					.build());
		}

		movieCodeRepository.saveAll(movieCodeList);

		return movieCodeList;
	}

	public Set<MovieCode> saveManyMovieCodes(InputDates dates) {
		Set<MovieCode> movieCodeList = new HashSet<>();
		List<BoxOfficeResultList> boxOfficeList = new ArrayList<>();
		try {
			boxOfficeList = movieApi.fetchManyBoxOfficeResult(dates.getStartDt(), dates.getEndDt());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (BoxOfficeResultList item : boxOfficeList) {
			movieCodeList.add(
				MovieCode.builder().code(Long.parseLong(item.getMovieCd())).title(item.getMovieNm())
					.build());
		}

		movieCodeRepository.saveAll(movieCodeList);

		return movieCodeList;
	}

	//영화 상세정보 저장
	public Movie saveMovieInfoByMovieCode(Long movieCode) throws ParseException {

		MovieInfoOutput movieInfoOutput = movieApi.fetchMovieInfoResult(movieCode);
		MovieInfo movieInfo = movieInfoOutput.getMovieInfoResult().getMovieInfo();
		// 감독, 배우, 장르, 국가는 list를 합쳐서 하나의 문자열로 저장
		List<Directors> directors = movieInfo.getDirectors();
		String director = directors.stream().map(Directors::getPeopleNm)
			.collect(Collectors.joining(", "));

		List<Actors> actors = movieInfo.getActors();
		String actor = actors.stream().map(Actors::getPeopleNm).limit(10)
			.collect(Collectors.joining(", "));

		List<Genres> genres = movieInfo.getGenres();
		String genre = genres.stream().map(Genres::getGenreNm).collect(Collectors.joining(", "));

		List<Nations> nations = movieInfo.getNations();
		String nation = nations.stream().map(Nations::getNationNm)
			.collect(Collectors.joining(", "));
		//날짜 포맷
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		LocalDateTime openDt = null;
		if (movieInfo.getOpenDt() != "") {
			openDt = format.parse(movieInfo.getOpenDt()).toInstant().atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		}

		Movie movie = Movie.builder().code(Long.parseLong(movieInfo.getMovieCd()))
			.title(movieInfo.getMovieNm()).actors(actor).directors(director).genre(genre)
			.nation(nation).runTime(Long.parseLong(movieInfo.getShowTm())).openDt(openDt)
			.status(MovieStatus.STATUS_WILL).build();
		movieRepository.save(movie);

		MovieCode movieCodeStatus = movieCodeRepository.findByCode(
			Long.parseLong(movieInfo.getMovieCd()));

		movieCodeStatus.setBatchStatus("Y");
		movieCodeRepository.save(movieCodeStatus);

		return movie;
	}
}
