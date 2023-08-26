package io.github.biojj.modules.votingSession.controller;

import io.github.biojj.modules.votingSession.model.VotingSession;
import io.github.biojj.modules.votingSession.services.VotingSessionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/voting-session")
public class VotingSessionController {

    private final VotingSessionService service;

    public VotingSessionController(VotingSessionService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VotingSession save(@RequestBody @Valid VotingSession votingSession) {
        return service.save(votingSession);
    }

    @GetMapping
    public Page<VotingSession> findAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return service.findAll(pageable);
    }

    @GetMapping("{id}")
    public VotingSession findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody @Valid VotingSession votingSessionActualization) {
        service.update(id, votingSessionActualization);
    }
}
