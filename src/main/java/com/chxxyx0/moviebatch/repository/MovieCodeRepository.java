package com.chxxyx0.moviebatch.repository;

import com.chxxyx0.moviebatch.entity.MovieCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCodeRepository extends JpaRepository<MovieCode, Long> {

}
