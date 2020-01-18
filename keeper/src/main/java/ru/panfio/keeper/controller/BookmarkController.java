package ru.panfio.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.panfio.keeper.config.security.CurrentUser;
import ru.panfio.keeper.config.security.UserPrincipal;
import ru.panfio.keeper.domain.Bookmark;
import ru.panfio.keeper.domain.payload.ApiResponse;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.repository.BookmarkRepo;
import ru.panfio.keeper.service.BookmarkService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final BookmarkRepo bookmarkRepo;

    public BookmarkController(BookmarkService bookmarkService,
                              BookmarkRepo bookmarkRepo) {
        this.bookmarkService = bookmarkService;
        this.bookmarkRepo = bookmarkRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLink(
            @RequestBody LinkCreationRequest request,
            @CurrentUser UserPrincipal currentUser) {
        if (bookmarkService.createBookmark(currentUser, request) != null) {
            return new ResponseEntity(
                    new ApiResponse(true, "Bookmark created"),
                    HttpStatus.OK);
        }
        return new ResponseEntity(
                new ApiResponse(false,
                        "I know this is annoying, but please add a "
                                + "protocol scheme or enter a valid URL"),
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<?> getUserLinks(@CurrentUser UserPrincipal currentUser,
                                          @RequestParam String userId) {
        if (currentUser.getId().toString().equals(userId)) {
            return new ResponseEntity(
                    bookmarkRepo.findAll(Sort.by(Sort.Direction.DESC, "date")),
                    HttpStatus.OK);
        }
        return new ResponseEntity(
                new ApiResponse(false,
                        "Please login"),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookmark(@CurrentUser UserPrincipal currentUser,
                                            @PathVariable String id) {
        long bookmarkId = Long.parseLong(id);
        Optional<Bookmark> dbBookmark = bookmarkRepo.findById(bookmarkId);
        if (!dbBookmark.isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Already deleted"),
                    HttpStatus.BAD_REQUEST);
        }
        boolean isUserBookmark =
                dbBookmark.get().getUser().getId().equals(currentUser.getId());
        if (isUserBookmark) {
            bookmarkRepo.deleteById(bookmarkId);
            log.info("Deleted bookmark {}", bookmarkId);
            return new ResponseEntity(
                    new ApiResponse(true, "Bookmark deleted"),
                    HttpStatus.OK);
        }
        return new ResponseEntity(new ApiResponse(false, "Delete error"),
                HttpStatus.BAD_REQUEST);
    }
}
