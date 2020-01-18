package ru.panfio.keeper.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.panfio.keeper.config.security.CurrentUser;
import ru.panfio.keeper.config.security.UserPrincipal;
import ru.panfio.keeper.domain.payload.ApiResponse;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.repository.LinkRepo;
import ru.panfio.keeper.service.LinkService;

@RestController
@RequestMapping("/api/link")
public class LinkController {
    private final LinkService linkService;
    private final LinkRepo linkRepo;

    public LinkController(LinkService linkService,
                          LinkRepo linkRepo) {
        this.linkService = linkService;
        this.linkRepo = linkRepo;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLink(
            @RequestBody LinkCreationRequest request,
            @CurrentUser UserPrincipal currentUser) {
        if (linkService.createLink(currentUser, request) != null) {
            return new ResponseEntity(
                    new ApiResponse(true, "Link created"),
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
                    linkRepo.findAll(Sort.by(Sort.Direction.DESC, "date")),
                    HttpStatus.OK);
        }
        return new ResponseEntity(
                new ApiResponse(false,
                        "Please login"),
                HttpStatus.BAD_REQUEST);
    }

}
