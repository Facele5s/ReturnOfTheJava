package edu.java.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Response;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public record StackOverFlowResponse(List<StackOverFlowItem> items) implements Response {
    @Override
    public Long getId() {
        return items().getFirst().questionId();
    }

    @Override
    public Map<String, String> getParams() {
        return Map.of();
    }

    @Override
    public OffsetDateTime getUpdateDate() {
        return items().getFirst().lastActivityDate();
    }

    public record StackOverFlowItem(
        @JsonProperty("question_id")
        Long questionId,

        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {
    }
}
