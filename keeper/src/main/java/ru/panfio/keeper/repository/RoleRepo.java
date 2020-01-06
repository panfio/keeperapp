package ru.panfio.keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panfio.keeper.domain.Role;
import ru.panfio.keeper.domain.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
