package ru.panfio.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.panfio.keeper.config.security.UserPrincipal;
import ru.panfio.keeper.domain.Bookmark;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.dto.UrlMetaDto;
import ru.panfio.keeper.repository.BookmarkRepo;
import ru.panfio.keeper.repository.UserRepo;

import java.time.Instant;

@Slf4j
@Service
public class BookmarkService {
    private final BookmarkRepo bookmarkRepo;
    private final UserRepo userRepo;

    public BookmarkService(BookmarkRepo bookmarkRepo, UserRepo userRepo) {
        this.bookmarkRepo = bookmarkRepo;
        this.userRepo = userRepo;
    }

    /**
     * Creates bookmark.
     * @param currentUser authenticated user
     * @param request request
     * @return bookmark
     */
    public Bookmark createBookmark(UserPrincipal currentUser,
                                   LinkCreationRequest request) {
        Bookmark bookmark = new Bookmark();
        String url = request.getLink();
        if (!UrlUtils.isValidUrl(url)) {
            return null;
        }
        userRepo.findById(currentUser.getId()).ifPresent(bookmark::setUser);

        UrlMetaDto meta = UrlUtils.getUrlMeta(url);
        if (meta != null) {
            bookmark.setCover(meta.getCover());
            bookmark.setTitle(meta.getTitle());
            bookmark.setDescription(meta.getDescription());
        }
        bookmark.setDate(Instant.now());
        bookmark.setLink(url);

        log.info("Created bookmark {}", bookmark);
        return bookmarkRepo.save(bookmark);
    }
}
