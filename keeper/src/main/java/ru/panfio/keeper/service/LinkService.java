package ru.panfio.keeper.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.panfio.keeper.domain.Link;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.repository.LinkRepo;
import ru.panfio.keeper.repository.UserRepo;

import javax.transaction.Transactional;
import java.net.URL;
import java.time.Instant;
import java.util.Random;

@Slf4j
@Service
public class LinkService {

    private final LinkRepo linkRepo;
    private final UserRepo userRepo;

    public LinkService(LinkRepo linkRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    //CHECKSTYLE:OFF
    @SneakyThrows
    @Transactional
    public Link createLink(LinkCreationRequest request) {
        Link link = new Link();

        link.setLink(request.getLink());
        //todo check existing urls
        link.setCut(genRandomString(4));
        link.setDate(Instant.now());
        userRepo.findById(request.getUserId())
                .ifPresent(user -> link.setUser(user));

        //fail when no  http://
        //todo error on https://www.baeldung.com/java-collections-complexity
        //todo url validation
        Document doc = Jsoup.parse(new URL(request.getLink()), 50000);
        String htmlTitle = doc.head().tagName("title").text();
        //todo when title is too big
        link.setName(htmlTitle);
        log.info("Created link {}", link);
        return linkRepo.save(link);
    }


    //todo create util package
    private String genRandomString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
    //CHECKSTYLE:ON

}
