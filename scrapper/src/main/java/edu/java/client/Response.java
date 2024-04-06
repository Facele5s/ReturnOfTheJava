package edu.java.client;

import java.time.OffsetDateTime;
import java.util.List;

public interface Response {
    Long getId();

    List<String> getParams();

    OffsetDateTime getUpdateDate();
}
