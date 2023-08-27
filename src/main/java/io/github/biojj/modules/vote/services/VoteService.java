package io.github.biojj.modules.vote.services;

import io.github.biojj.modules.member.model.Member;
import io.github.biojj.modules.member.repository.MemberRepository;
import io.github.biojj.modules.vote.model.Vote;
import io.github.biojj.modules.vote.repository.VoteRepository;
import io.github.biojj.modules.votingSession.model.VotingSession;
import io.github.biojj.modules.votingSession.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class VoteService {
    private final VoteRepository repository;
    private final MemberRepository memberRepository;
    private final VotingSessionRepository votingSessionRepository;

    @Value("${votingSession.defaultOpeningDurationMinutes}")
    private int defaultOpeningDurationMinutes;

    public VoteService(VoteRepository repository, MemberRepository memberRepository, VotingSessionRepository votingSessionRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
        this.votingSessionRepository = votingSessionRepository;
    }

    public Vote save(Vote vote) {
        Member member = memberRepository.findById(vote.getMember().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Membro não encontrado"));

        VotingSession session = votingSessionRepository.findById(vote.getSchedule().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sessão de votação não encontrada"));

        validateVotingSession(session.getClosingDate());

        if (repository.existsByMemberIdAndScheduleId(member.getId(), session.getSchedule().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este membro já votou nesta sessão.");
        }

        Vote validVote = Vote.builder()
                .schedule(session.getSchedule())
                .member(member)
                .voteChoice(vote.getVoteChoice())
                .build();

        return repository.save(validVote);
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

    public LocalDateTime calculateClosingDate() {
        return LocalDateTime.now().plusMinutes(defaultOpeningDurationMinutes);
    }

    public void validateVotingSession(LocalDateTime closingDate) {
        if (LocalDateTime.now().isAfter(closingDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A sessão de votação está fechada.");
        }
    }

}
