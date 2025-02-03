package com.hcd.figureservice.repository;

import com.hcd.figureservice.domain.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long> {

    Optional<Figure> findByName(String name);
}
