package com.example.jobfinder.config;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.springframework.boot.ApplicationRunner;
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// import com.example.jobfinder.data.entity.*;
// import com.example.jobfinder.data.repository.*;
// import com.example.jobfinder.utils.enumeration.ERole;
//
// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import lombok.experimental.NonFinal;
// import lombok.extern.slf4j.Slf4j;
//
// @Configuration
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// @Slf4j
// public class DatabaseInitializer {
//
//    PasswordEncoder passwordEncoder;
//
//    @NonFinal
//    String ADMIN_PASSWORD = "admin";
//
//    @Bean
//    @ConditionalOnProperty(
//            prefix = "spring",
//            value = "datasource.driverClassName",
//            havingValue = "com.mysql.cj.jdbc.Driver")
//    ApplicationRunner applicationRunner(
//            UserRepository userRepository,
//            RoleRepository roleRepository,
//            MajorRepository majorRepository,
//            PositionRepository positionRepository,
//            ScheduleRepository scheduleRepository) {
//        log.info("Initializing application.....");
//        return args -> {
//            if (!userRepository.findByFirstName(ERole.Admin.toString()).isPresent()) {
//
//                Role adminRole =
//                        Role.builder().roleId(1).name(ERole.Admin.toString()).build();
//
//                roleRepository.save(adminRole);
//
//                User user = User.builder()
//                        .firstName("admin")
//                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
//                        .role(adminRole)
//                        .build();
//
//                userRepository.save(user);
//
//                log.warn("admin user has been created with default password: admin, please change it");
//            }
//
//            if (majorRepository.count() == 0) {
//
//                log.info("Creating default majors ...");
//
//                List<Major> majors = new ArrayList<>();
//
//                majors.add(Major.builder().id(1).name("Computer Science").build());
//
//                majors.add(Major.builder().id(2).name("Software Engineering").build());
//
//                majorRepository.saveAll(majors);
//
//                log.info("Default majors has been created ...");
//            }
//
//            if (positionRepository.count() == 0) {
//
//                log.info("Creating default positions ...");
//
//                List<Position> positions = new ArrayList<>();
//
//                positions.add(
//                        Position.builder().id(1).name("Software Developer").build());
//
//                positions.add(Position.builder().id(2).name("Software Engineer").build());
//
//                positions.add(
//                        Position.builder().id(3).name("Software Architect").build());
//
//                positionRepository.saveAll(positions);
//
//                log.info("Default positions has been created ...");
//            }
//
//            log.info("Application initialization completed .....");
//        };
//    }
// }

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.utils.enumeration.ERole;
import com.example.jobfinder.utils.enumeration.Estatus;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Builder
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;

    @Override
    public void run(String... args) {

        log.info("Initializing application.....");

        long countRoles = this.roleRepository.count();
        long countStatus = this.statusRepository.count();
        if (countRoles > 0 || countStatus > 0) {
            log.info("Application initialization completed .....");
            return;
        }

        log.info("Creating default roles ...");

        if (countRoles == 0) {

            ArrayList<Role> roles = new ArrayList<>();

            roles.add(Role.builder()
                    .name(ERole.Admin.toString())
                    .roleId(ERole.adminRole)
                    .build());
            roles.add(Role.builder()
                    .name(ERole.Candidate.toString())
                    .roleId(ERole.candidateRole)
                    .build());
            roles.add(Role.builder()
                    .name(ERole.HR.toString())
                    .roleId(ERole.hrRole)
                    .build());

            roleRepository.saveAll(roles);
        }

        log.info("Default roles has been created ...");

        log.info("Creating default status ...");

        if (countStatus == 0) {

            ArrayList<Status> statuses = new ArrayList<>();

            statuses.add(Status.builder()
                    .name(Estatus.Active.toString())
                    .statusId(Estatus.activeStatus)
                    .build());
            statuses.add(Status.builder()
                    .name(Estatus.Not_Active.toString())
                    .statusId(Estatus.notActiveStatus)
                    .build());
            statuses.add(Status.builder()
                    .name(Estatus.Lock.toString())
                    .statusId(Estatus.lockStatus)
                    .build());
            statuses.add(Status.builder()
                    .name(Estatus.Disable.toString())
                    .statusId(Estatus.disableStatus)
                    .build());
            statuses.add(Status.builder()
                    .name(Estatus.Delete.toString())
                    .statusId(Estatus.deleteStatus)
                    .build());

            statusRepository.saveAll(statuses);
        }
        log.info("Default status has been created ...");

        log.info("Application initialization completed .....");
    }
}
