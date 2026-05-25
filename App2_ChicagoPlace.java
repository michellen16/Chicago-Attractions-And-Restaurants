package edu.uic.cs478.s2026.project3app2;

import java.io.Serializable;

/**
 * Simple data model representing a Chicago point of interest
 * or restaurant. Serializable so it can be passed in a Bundle
 * across configuration changes.
 */
public class ChicagoPlace implements Serializable {

    private final String name;
    private final String description;
    private final String url;

    public ChicagoPlace(String name, String description, String url) {
        this.name        = name;
        this.description = description;
        this.url         = url;
    }

    public String getName()        { return name;        }
    public String getDescription() { return description; }
    public String getUrl()         { return url;         }
}
