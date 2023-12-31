package io.github.biojj.modules.votingSession.repository;

import io.github.biojj.modules.votingSession.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {
}
