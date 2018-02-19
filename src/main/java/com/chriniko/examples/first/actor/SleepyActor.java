package com.chriniko.examples.first.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.List;

public class SleepyActor extends AbstractLoggingActor {

    private static final int THRESHOLD = 3;

    private List<WakeUp> wakeUpsReceived;

    @Override
    public void preStart() throws Exception {
        wakeUpsReceived = new ArrayList<>();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(WakeUp.class, msg -> {
                    wakeUpsReceived.add(msg);

                    if (wakeUpsReceived.size() > THRESHOLD) {
                        sender().tell(
                                new GettingReady("getting dressed, eat breakfast, brush teeth, ride motorcycle"),
                                self());
                    }
                })
                .matchAny(this::unhandled)
                .build();
    }

    public static Props props() {
        return Props.create(SleepyActor.class);
    }


    // --- protocol definition ---

    public static class WakeUp {
    }

    public static class GettingReady {
        private final String message;

        public GettingReady(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
