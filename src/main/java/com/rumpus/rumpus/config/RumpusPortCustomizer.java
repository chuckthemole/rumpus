package com.rumpus.rumpus.config;

@org.springframework.stereotype.Component
public class RumpusPortCustomizer extends com.rumpus.common.Server.AbstractServerPortCustomizer {
 
    private static final String NAME = "RumpusPortCustomizer";
    private static final String PORT = "8081"; // TODO: look in AbstractServerPortCustomizer for its todo. I should make a port manager, or look for one if I started one earlier.

    public RumpusPortCustomizer() {
        super(NAME, PORT);
    }
}
