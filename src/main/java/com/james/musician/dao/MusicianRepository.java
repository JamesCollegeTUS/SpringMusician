package com.james.musician.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.james.musician.model.Musician;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {

	Musician findByEmailAddress(String emailAddress);
}
