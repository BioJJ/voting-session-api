package io.github.biojj.modules.vote.repository;

import io.github.biojj.modules.vote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByMemberIdAndScheduleId(Long memberId, Long scheduleId);
}
