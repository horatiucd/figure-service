package com.hcd.figureservice.controller;

import com.hcd.figureservice.domain.Figure;
import com.hcd.figureservice.exception.CustomException;
import com.hcd.figureservice.repository.FigureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FigureService {

    private final FigureRepository figureRepository;

    public FigureService(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Transactional(readOnly = true)
    public List<Figure> findAll() {
        return figureRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Figure> findById(long id) {
        return figureRepository.findById(id);
    }

    @Transactional
    public Figure create(Figure figure) {
        Optional<Figure> existingFigure = figureRepository.findByName(figure.getName());
        if (existingFigure.isPresent()) {
            throw new CustomException("A Figure with the same 'name' already exists.");
        }
        figure.setCode(UUID.randomUUID().toString());
        return figureRepository.save(figure);
    }

    @Transactional
    public Figure update(Figure figure) {
        Figure entity = identifyFigure(figure.getId());
        entity.setName(figure.getName());
        return figureRepository.save(entity);
    }

    @Transactional
    public void delete(long id) {
        identifyFigure(id);
        figureRepository.deleteById(id);
    }

    private Figure identifyFigure(long id) {
        return figureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Figure not found."));
    }
}
