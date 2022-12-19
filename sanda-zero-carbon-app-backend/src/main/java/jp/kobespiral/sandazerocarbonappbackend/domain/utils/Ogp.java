package jp.kobespiral.sandazerocarbonappbackend.domain.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Ogp {
    public static Elements getOgp(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document.select("meta[property~=og:*]");
    }
}