package com.sample.remoting.akka;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.sample.remoting.akka.model.PlusReq;
import com.sample.remoting.akka.model.PlusResp;

public class PlusActor extends AbstractActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().getSystem(), this);

    // Recommended practice reference http://doc.akka.io/docs/akka/current/java/actors.html.
    static Props props() {
        return Props.create(PlusActor.class);
    }

    PlusActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PlusReq.class, msg -> {
                    logger.info("Received msg {}.", msg.toString());
                    final PlusResp result = new PlusResp(msg.getOp1() + msg.getOp2());
                    getSender().tell(result, getSelf());
                    logger.info("Result told {}.", result.toString());
                })
                .build();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        logger.info("{} has been stopped.", self().path());

        // TODO.
    }
}
