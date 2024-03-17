package edu.java.client;

import edu.java.entity.ClientResponse;
import java.net.URI;

public interface UrlClient {
    boolean supportsUrl(URI url);

    ClientResponse fetch(URI uri);
}
