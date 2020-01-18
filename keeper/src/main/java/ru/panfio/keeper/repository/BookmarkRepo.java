package ru.panfio.keeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panfio.keeper.domain.Bookmark;

@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

}
