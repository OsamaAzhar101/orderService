package service;


import com.oasys.news_summary_automation.model.NewsArticle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScriptGeneratorService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateScript(List<NewsArticle> articles) {
        StringBuilder summaries = new StringBuilder();
        for (NewsArticle article : articles) {
            summaries.append("- ").append(article.getSummary()).append("\n");
        }

        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(
                Map.of("role", "system", "content", "Generate a script based on the following summaries."),
                Map.of("role", "user", "content", summaries.toString())
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                httpEntity,
                Map.class
        );

        Map<String, Object> response = responseEntity.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }
}