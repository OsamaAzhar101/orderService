package service;

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
public class OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String summarizeArticle(String content) {
        // Prepare the request payload
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(
                Map.of("role", "system", "content", "Summarize the following article in two sentences, emphasizing its impact on relevant subject matter."),
                Map.of("role", "user", "content", content)
        ));

        // Set headers
        Map<String, String> headers = Map.of(
                "Authorization", "Bearer " + openaiApiKey,
                "Content-Type", "application/json"
        );

        // Make the API call
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                new HttpEntity<>(request, new HttpHeaders(createHttpHeaders())),
                Map.class
        );

        // Extract the response body
        Map<String, Object> response = responseEntity.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }


    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openaiApiKey);
        headers.set("Content-Type", "application/json");
        return headers;
    }

}