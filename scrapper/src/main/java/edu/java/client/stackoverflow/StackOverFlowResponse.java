package edu.java.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.java.client.Response;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverFlowResponse(List<StackOverFlowItem> items) implements Response {
    @Override
    public Long getId() {
        return items().getFirst().questionId();
    }

    @Override
    public List<String> getParams() {
        return List.of();
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
