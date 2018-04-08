package com.sample.remoting.akka;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.sample.remoting.akka.model.PlusReq;
import com.sample.remoting.akka.model.PlusResp;

public class FirerActor extends AbstractActor {
    private final LoggingAdapter logger = Logging.getLogger(getContext().getSystem(), this);

    private String remotePathToAccess;

    // Recommended practice reference http://doc.akka.io/docs/akka/current/java/actors.html.
    static Props props(final String aRemotePathToAccess) {
        return Props.create(FirerActor.class, aRemotePathToAccess);
    }

    FirerActor(final String aRemotePathToAccess) {
        remotePathToAccess = aRemotePathToAccess;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(FirerCommand.class, msg -> {
                    try {
                        switch (msg) {
                            case START:
                                final ActorSelection targetRemoteActorSelection = getContext().actorSelection(remotePathToAccess);
                                targetRemoteActorSelection.tell(new PlusReq(5, 2), self());
                                break;
                            case SUICIDE:
                                self().tell(PoisonPill.getInstance(), ActorRef.noSender());
                                break;
                            case STOP:
                                getContext().stop(self());
                                break;
                            default:
                                break;
                        }
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                })
                .match(PlusResp.class, msg -> {
                    logger.info("Got PlusResp {}.", msg.toString());
                    logger.warning("Suiciding this actor.");
                    self().tell(FirerCommand.SUICIDE, ActorRef.noSender());
                })
                .build();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        logger.info("{} has been stopped.", self().path());

        getContext().getSystem().terminate();
    }
}
