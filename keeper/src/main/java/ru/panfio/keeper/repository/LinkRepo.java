package ru.panfio.keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panfio.keeper.domain.Link;

import java.util.Optional;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {
    Optional<Link> findByCut(String cut);
}
