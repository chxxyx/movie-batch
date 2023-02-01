package com.chxxyx0.moviebatch.repository;

import com.chxxyx0.moviebatch.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
