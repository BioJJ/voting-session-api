package io.github.biojj.modules.schedule.services;

import io.github.biojj.modules.schedule.model.Schedule;
import io.github.biojj.modules.schedule.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule save(Schedule schedule) {
        return repository.save(schedule);
    }

    public Page<Schedule> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Schedule findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule não encontrado"));
    }

    public void delete(Long id) {
        repository
                .findById(id)
                .map(schedule -> {
                    repository.delete(schedule);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule não encontrado"));
    }

    public void update(Long id,
                       Schedule scheduleDTO) {

        repository
                .findById(id)
                .map(schedule -> {
                    schedule.setTitle(scheduleDTO.getTitle());
                    schedule.setDescription(scheduleDTO.getDescription());
                    schedule.setStatus(scheduleDTO.getStatus());

                    return repository.save(schedule);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "schedule não encontrado"));
    }

}
