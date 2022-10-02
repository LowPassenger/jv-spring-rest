package mate.academy.spring.controller;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.spring.dto.MovieRequestDto;
import mate.academy.spring.dto.MovieResponseDto;
import mate.academy.spring.security.mapper.MovieDtoMapper;
import mate.academy.spring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieDtoMapper movieDtoMapper;
    private MovieService movieService;

    @Autowired
    public MovieController(MovieDtoMapper movieDtoMapper, MovieService movieService) {
        this.movieDtoMapper = movieDtoMapper;
        this.movieService = movieService;
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll().stream()
               .map(movieDtoMapper::toDto)
               .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDto create(@RequestBody MovieRequestDto movieRequestDto) {
        return movieDtoMapper.toDto(movieService.add(movieDtoMapper.toModel(movieRequestDto)));
    }
}
