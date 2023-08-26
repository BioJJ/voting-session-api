package io.github.biojj.modules.votingSession.services;

import io.github.biojj.modules.votingSession.model.VotingSession;
import io.github.biojj.modules.votingSession.repository.VotingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VotingSessionService {

    private final VotingSessionRepository repository;

    public VotingSessionService(VotingSessionRepository repository) {
        this.repository = repository;
    }

    public VotingSession save(VotingSession votingSession) {
        return repository.save(votingSession);
    }

    public Page<VotingSession> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public VotingSession findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "votingSession não encontrado"));
    }

    public void delete(Long id) {
        repository
                .findById(id)
                .map(votingSession -> {
                    repository.delete(votingSession);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "votingSession não encontrado"));
    }

    public void update(Long id,
                       VotingSession votingSessionDTO) {

        repository
                .findById(id)
                .map(votingSession -> {
                    votingSession.setOpeningDate(votingSessionDTO.getOpeningDate());
                    votingSession.setClosingDate(votingSessionDTO.getClosingDate());

                    return repository.save(votingSession);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "votingSession não encontrado"));
    }
}
