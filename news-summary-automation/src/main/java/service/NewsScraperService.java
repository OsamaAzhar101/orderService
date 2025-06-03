package service;

import com.oasys.news_summary_automation.model.NewsArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsScraperService {

    public List<NewsArticle> scrapeNews(List<String> keywords) throws IOException {
        List<NewsArticle> articles = new ArrayList<>();
        Document doc = Jsoup.connect("https://ground.news/").get();
        Elements newsElements = doc.select("a.card");

        for (Element element : newsElements) {
            String headline = element.select("h3").text();
            String url = element.absUrl("href");
            String source = element.select(".source").text();
            String category = element.select(".category").text();

            for (String keyword : keywords) {
                if (headline.toLowerCase().contains(keyword.toLowerCase())) {
                    NewsArticle article = new NewsArticle();
                    article.setHeadline(headline);
                    article.setUrl(url);
                    article.setSource(source);
                    article.setCategory(category);
                    article.setDate(java.time.LocalDate.now().toString());
                    articles.add(article);
                    break;
                }
            }
        }
        return articles;
    }
}
