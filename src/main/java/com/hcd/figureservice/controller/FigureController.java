package com.hcd.figureservice.controller;

import com.hcd.figureservice.controller.dto.FigureRequest;
import com.hcd.figureservice.controller.dto.FigureResponse;
import com.hcd.figureservice.domain.Figure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/figures")
public class FigureController {

    private final FigureService figureService;

    public FigureController(FigureService figureService) {
        this.figureService = figureService;
    }

    @GetMapping
    public ResponseEntity<List<FigureResponse>> all() {
        List<FigureResponse> response = figureService.findAll().stream()
                .map(figure -> new FigureResponse(figure.getId(), figure.getName()))
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FigureResponse> one(@PathVariable Long id) {
        Figure figure = figureService.findById(id);
        FigureResponse response = new FigureResponse(figure.getId(), figure.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FigureResponse> create(@RequestBody FigureRequest request) {
        Figure figure = figureService.create(new Figure(request.name()));
        return ResponseEntity.created(URI.create("/api/v1/figures/" + figure.getId()))
                .body(new FigureResponse(figure.getId(), figure.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FigureResponse> update(@PathVariable Long id,
                                                 @RequestBody FigureRequest request) {
        Figure figure = new Figure(id, request.name());
        figure = figureService.update(figure);
        FigureResponse response = new FigureResponse(figure.getId(), figure.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        figureService.delete(id);
        return ResponseEntity.ok().build();
    }
}
