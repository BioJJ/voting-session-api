package io.github.biojj.modules.member.services;

import io.github.biojj.modules.member.model.Member;
import io.github.biojj.modules.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member save(Member member) {
        return repository.save(member);
    }

    public Page<Member> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Member findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member não encontrado"));
    }

    public void delete(Long id) {
        repository
                .findById(id)
                .map(member -> {
                    repository.delete(member);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member não encontrado"));
    }

    public void update(Long id,
                       Member memberDTO) {

        repository
                .findById(id)
                .map(member -> {
                    member.setName(memberDTO.getName());
                    member.setCpf(memberDTO.getCpf());
                    member.setHabilitationStatus(memberDTO.getHabilitationStatus());

                    return repository.save(member);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "member não encontrado"));
    }
}
