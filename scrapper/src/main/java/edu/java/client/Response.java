package edu.java.client;

import java.time.OffsetDateTime;
import java.util.Map;

public interface Response {
    Long getId();

    Map<String, String> getParams();

    OffsetDateTime getUpdateDate();

    String getDescription();
}
