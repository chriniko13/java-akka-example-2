package com.chriniko.examples.first.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import scala.concurrent.duration.Duration;

public class SleepyActorTest {

    private static ActorSystem system;
    private static ActorRef actorRef;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();

        actorRef = system.actorOf(SleepyActor.props());
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system, Duration.create("10 seconds"), true);
        system = null;
    }

    @Test
    public void dummy() {
    }

    // Note: add your test scenarios below....

}