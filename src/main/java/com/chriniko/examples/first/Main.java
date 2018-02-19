package com.chriniko.examples.first;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.chriniko.examples.first.actor.SleepyActor;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {

        // initialize actor system...
        ActorSystem actorSystem = ActorSystem.create("sleepy-system");
        ActorRef sleepyActor = actorSystem.actorOf(SleepyActor.props());


        // do your examples...

        // START: comment/uncomment the following section to see what happens.
        sleepyActor.tell(new SleepyActor.WakeUp(), ActorRef.noSender());
        sleepyActor.tell(new SleepyActor.WakeUp(), ActorRef.noSender());
        sleepyActor.tell(new SleepyActor.WakeUp(), ActorRef.noSender());
        // END: comment/uncomment the following section to see what happens.


        Future<Object> akkaResult = Patterns.ask(
                sleepyActor,
                new SleepyActor.WakeUp(),
                Timeout.apply(2, TimeUnit.SECONDS));

        CompletionStage<Object> javaResult = FutureConverters.toJava(akkaResult);

        javaResult.whenComplete((res, error) -> {

            if (res != null
                    && res instanceof SleepyActor.GettingReady) {

                SleepyActor.GettingReady gettingReady = (SleepyActor.GettingReady) res;
                System.out.println("result = " + gettingReady.getMessage());

            } else {
                System.out.println("error = " + error);
            }

        });


        //terminate system...
        try {
            TimeUnit.SECONDS.sleep(5);
            actorSystem.terminate();
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }
}
