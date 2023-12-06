package com.raf.nwpdomaci3.repository;

import com.raf.nwpdomaci3.domain.entities.Role;
import com.raf.nwpdomaci3.domain.entities.RoleType;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepositoryImplementation<Role, Long> {

    List<Role> findAllByRoleIn(List<RoleType> roles);
}
