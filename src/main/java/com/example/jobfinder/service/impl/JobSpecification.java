package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.Job;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public class JobSpecification {

    public static Specification<Job> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                        : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Job> hasProvinceName(String provinceName) {
        return (root, query, criteriaBuilder) ->
                provinceName == null ? criteriaBuilder.isTrue(criteriaBuilder.literal(true))
                        : criteriaBuilder.like(root.get("province"), "%" + provinceName + "%");
    }

    public static Specification<Job> hasPositionIds(List<String> positionIds) {
        return (root, query, criteriaBuilder) -> {
            if (positionIds == null || positionIds.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return root.join("jobPositions").get("position").get("id").in(positionIds);
        };
    }

    public static Specification<Job> hasScheduleIds(List<String> scheduleIds) {
        return (root, query, criteriaBuilder) -> {
            if (scheduleIds == null || scheduleIds.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return root.join("jobSchedules").get("schedule").get("id").in(scheduleIds);
        };
    }

    public static Specification<Job> hasMajorIds(List<String> majorIds) {
        return (root, query, criteriaBuilder) -> {
            if (majorIds == null || majorIds.isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return root.join("jobMajors").get("major").get("id").in(majorIds);
        };
    }

    public static Specification<Job> hasStatusId(int statusId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status").get("statusId"), statusId);
    }

}
