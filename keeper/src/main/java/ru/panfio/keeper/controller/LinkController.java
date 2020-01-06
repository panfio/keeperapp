package ru.panfio.keeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.panfio.keeper.domain.payload.ApiResponse;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.repository.LinkRepo;
import ru.panfio.keeper.service.LinkService;

@RestController
@RequestMapping("/api/links")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Autowired
    LinkRepo linkRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createLink(
            @RequestBody LinkCreationRequest request) {
        if (linkService.createLink(request) != null) {
            return new ResponseEntity(
                    new ApiResponse(true, "Link created"),
                    HttpStatus.OK);
        }
        return new ResponseEntity(
                new ApiResponse(true, "Something went Wrong"),
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortLink() {

        return new ResponseEntity(
                linkRepo.findAll(Sort.by(Sort.Direction.DESC, "date")),
                HttpStatus.OK);
    }

}
