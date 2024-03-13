package edu.java.client.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverFlowResponse(List<StackOverFlowItem> items) {
    public record StackOverFlowItem(
        @JsonProperty("question_id")
        Long questionId,

        @JsonProperty("last_activity_date")
        OffsetDateTime lastActivityDate
    ) {

    }
}
