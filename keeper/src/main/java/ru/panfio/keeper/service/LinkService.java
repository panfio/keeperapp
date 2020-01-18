package ru.panfio.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.panfio.keeper.config.security.UserPrincipal;
import ru.panfio.keeper.domain.Link;
import ru.panfio.keeper.domain.payload.LinkCreationRequest;
import ru.panfio.keeper.repository.LinkRepo;
import ru.panfio.keeper.repository.UserRepo;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Random;

@Slf4j
@Service
public class LinkService {

    public static final int HASH_LENGTH = 5;
    private final LinkRepo linkRepo;
    private final UserRepo userRepo;

    public LinkService(LinkRepo linkRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.userRepo = userRepo;
    }

    /**
     * Creates a short link.
     *
     * @param currentUser authorised user
     * @param request     Link Creation Request
     * @return created link or null if any errors
     */
    @Transactional
    public Link createLink(UserPrincipal currentUser, LinkCreationRequest request) {
        String url = request.getLink();
        if (!UrlUtils.isValidUrl(url)) {
            return null;
        }

        Link link = new Link();
        link.setLink(url);
        link.setCut(genUrlId(HASH_LENGTH));
        link.setDate(Instant.now());
        userRepo.findById(currentUser.getId()).ifPresent(link::setUser);
        link.setName(UrlUtils.getLinkTitle(url));

        log.info("Created link {}", link);
        return linkRepo.save(link);
    }

    /**
     * Generates unique url id.
     *
     * @param idLength URL
     * @return generated id
     */
    private String genUrlId(final int idLength) {
        for (;;) {
            String urlId = genRandomString(idLength);
            if (linkRepo.findByCut(urlId).isPresent()) {
                continue;
            }
            return urlId;
        }
    }

    /**
     * Generates random string with the given length.
     *
     * @param length length
     * @return generated string
     */
    public String genRandomString(int length) {
        //CHECKSTYLE:OFF
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
        //CHECKSTYLE:ON
    }
}
