package ru.panfio.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.panfio.keeper.dto.UrlMetaDto;

import java.io.IOException;
import java.net.URL;

@Slf4j
public final class UrlUtils {

    public static final int MAX_TITLE_LENGTH = 200;
    /**
     * Checks if URL is valid.
     *
     * @param url URL
     * @return true if valid
     */
    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static UrlMetaDto getUrlMeta(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements cover = doc.select("meta[name$=image], meta[property$=image]");
            Elements title = doc.select("meta[name$=title], meta[property$=title]");
            Elements description =
                    doc.select("meta[name$=description], meta[property$=description]");

            return new UrlMetaDto(
                    getAttr(title.first()),
                    getAttr(description.first()),
                    getAttr(cover.first())
            );
        } catch (IOException e) {
            log.error("Invalid HTML or network connection problem");
        }
        return null;
    }

    public static String getAttr(Element element) {
        return  element == null ? "" : element.attr("content");
    }

    /**
     * Gets title from html page.
     *
     * @param url url
     * @return title or url if title not found
     */
    public static  String getLinkTitle(final String url) {
        String htmlTitle = null;
        try {
            Document doc = Jsoup.connect(url).get();
            htmlTitle = doc.head().tagName("title").text();
            if (htmlTitle.length() > MAX_TITLE_LENGTH) {
                htmlTitle = htmlTitle.substring(0, MAX_TITLE_LENGTH).trim() + " ...";
            }
            return htmlTitle;
        } catch (IOException e) {
            log.error("Invalid HTML or network connection problem");
        }
        return htmlTitle == null ? url : htmlTitle;
    }

    private UrlUtils() {

    }
}
