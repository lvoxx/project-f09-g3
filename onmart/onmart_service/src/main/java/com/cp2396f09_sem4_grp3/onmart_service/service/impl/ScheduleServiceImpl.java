package com.cp2396f09_sem4_grp3.onmart_service.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cp2396f09_sem4_grp3.onmart_common.dto.request.crud.ScheduleRequest;
import com.cp2396f09_sem4_grp3.onmart_common.dto.response.crud.ScheduleResponse;
import com.cp2396f09_sem4_grp3.onmart_common.entities.Schedule;
import com.cp2396f09_sem4_grp3.onmart_common.entities.ScheduleDetails;
import com.cp2396f09_sem4_grp3.onmart_common.entities.User;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.DataNotFoundException;
import com.cp2396f09_sem4_grp3.onmart_service.helper.exception.crud.ExistDataException;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ScheduleDetailsRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.ScheduleRepository;
import com.cp2396f09_sem4_grp3.onmart_service.repository.UserRepository;
import com.cp2396f09_sem4_grp3.onmart_service.service.ScheduleService;
import com.cp2396f09_sem4_grp3.onmart_service.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    @Value("${app.jpa.max-items-in-page}")
    private int maxItems;

    private final UserService userService;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDetailsRepository detailsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<ScheduleResponse> getScheduleFromDateToDate(Integer page, LocalDate fromDate, LocalDate toDate) {
        Pageable paging = PageRequest.of(page, maxItems, Sort.by("id"));
        return scheduleRepository.findSchedulesWithinDateRange(fromDate, toDate, paging)
                .map(s -> modelMapper.map(s, ScheduleResponse.class));
    }

    @Override
    public Page<ScheduleResponse> getScheduleFromDateToDateByAccount(Integer page, LocalDate fromDate,
            LocalDate toDate,
            String email) {
        Pageable paging = PageRequest.of(page, maxItems, Sort.by("id"));
        User employee = userService.findByEmail(email);

        return scheduleRepository.findSchedulesWithinDateRangeAndAccountId(fromDate, toDate, employee.getId(), paging)
                .map(s -> modelMapper.map(s, ScheduleResponse.class));
    }

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request, String email) {
        Optional<Schedule> existSchedule = scheduleRepository.findSchedulesByDate(request.getDate());
        if (existSchedule.isPresent()) {
            throw new ExistDataException("Schdule alreasy exist.");
        }
        User admin = userService.findByEmail(email);
        Schedule schedule = Schedule.builder()
                .adminId(admin.getId())
                .adminName(admin.getFirstName() + " " + admin.getLastName())
                .date(request.getDate())
                .build();

        List<Long> userIds = request.getScheduleDetails().stream().map(s -> s.getEmployeeId()).toList();
        List<User> users = userRepository.findAllById(userIds);

        List<ScheduleDetails> details = request.getScheduleDetails().stream()
                .map(s -> {
                    User user = users.stream().filter(u -> u.getId().equals(s.getEmployeeId())).findFirst().get();
                    return ScheduleDetails.builder()
                            .employeeId(user.getId())
                            .employeeName(user.getFirstName() + " " + user.getLastName())
                            .scheduleShift(s.getShift())
                            .schedule(schedule)
                            .build();
                })
                .toList();
        ScheduleResponse response = modelMapper.map(scheduleRepository.saveAndFlush(schedule), ScheduleResponse.class);
        detailsRepository.saveAllAndFlush(details);

        return response;
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("No schedule found."));
        // if (schedule.getScheduleDetails() != null) {
        //     detailsRepository.deleteAllInBatch(schedule.getScheduleDetails());
        // }
        scheduleRepository.deleteById(id);
    }

}
