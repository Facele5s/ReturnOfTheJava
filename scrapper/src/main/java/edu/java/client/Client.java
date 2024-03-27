package edu.java.client;

import java.net.URI;

public interface Client {
    boolean isLinkSupported(URI url);

    Response getResponse(URI uri);
}
