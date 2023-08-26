package io.github.biojj.modules.vote.services;

import io.github.biojj.modules.vote.model.Vote;
import io.github.biojj.modules.vote.repository.VoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoteService {
    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote save(Vote vote) {
        return repository.save(vote);
    }

    public Page<Vote> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Vote findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vote não encontrado"));
    }

    public void delete(Long id) {
        repository
                .findById(id)
                .map(vote -> {
                    repository.delete(vote);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "vote não encontrado"));
    }

}
