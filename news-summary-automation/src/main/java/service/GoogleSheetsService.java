package service;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.oasys.news_summary_automation.model.NewsArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSheetsService {

    @Value("${google.sheets.spreadsheetId}")
    private String spreadsheetId;

    private final Sheets sheetsService;

    public GoogleSheetsService(Sheets sheetsService) {
        this.sheetsService = sheetsService;
    }


        public void writeArticles(List<NewsArticle> articles) throws IOException {
            List<List<Object>> values = articles.stream()
                    .map(article -> List.of(
                            (Object) article.getDate(),
                            (Object) article.getCategory(),
                            (Object) article.getHeadline(),
                            (Object) article.getSource(),
                            (Object) article.getUrl(),
                            (Object) article.getSummary()
                    ))
                    .collect(Collectors.toList());


        ValueRange body = new ValueRange().setValues(values);
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, "Sheet1!A:F", body)
                .setValueInputOption("RAW")
                .execute();
    }
}
