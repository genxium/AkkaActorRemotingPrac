package com.sample.remoting.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    // Reference https://doc.akka.io/docs/akka/current/general/addressing.html.
    private static final String MAGIC_GUARDIAN_PATH_PREFIX = "user";

    private static final String PLUS_DAEMON_ACTOR_SYSTEM_NAME ="plusdaemon";
    private static final String PLUS_DAEMON_TARGET_ACTOR_NAME = "plus_actor_1";

    private static final String FIRER_DAEMON_ACTOR_SYSTEM_NAME ="firerdaemon";

    public static void main(String[] args) {
        if (0 >= args.length) {
            logger.error("Please launch with either `plusdaemon` or `fire`.");
            return;
        }
        final String act = args[0];
        logger.debug("Launched with first arg {}.", act);
        try {
            if (act.equals("plusdaemon")) {
                final ActorSystem plusSys = startPlusDaemon();

                logger.info("Started actor system `{}`", plusSys.name());

                final String explicitActorName = PLUS_DAEMON_TARGET_ACTOR_NAME;
                /**
                 * If an "explicitActorName" is not specified here, then a "implicitRandomActorName" will
                 * be given whilst making it non-trival to access from a remote process by "/path/to/explicitActorName".
                 */
                final ActorRef plusActorRef = plusSys.actorOf(PlusActor.props(), explicitActorName);

                logger.info("Started actor with path `{}`", plusActorRef.path());

            }

            if (act.equals("fire")) {
                final ActorSystem firerSys = startFirerDaemon();

                logger.info("Started actor system `{}`", firerSys.name());

                final String remoteTargetActorPathToAccess = String.format("akka://%s@%s:%d/%s/%s", PLUS_DAEMON_ACTOR_SYSTEM_NAME, "127.0.0.1", 2552, MAGIC_GUARDIAN_PATH_PREFIX, PLUS_DAEMON_TARGET_ACTOR_NAME);
                final ActorRef firerActorRef = firerSys.actorOf(FirerActor.props(remoteTargetActorPathToAccess));

                firerActorRef.tell(FirerCommand.START, ActorRef.noSender());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static ActorSystem startPlusDaemon() {
        final String configNameInResources = PLUS_DAEMON_ACTOR_SYSTEM_NAME;
        final ActorSystem plusSys = ActorSystem.create(PLUS_DAEMON_ACTOR_SYSTEM_NAME,
                ConfigFactory.load((configNameInResources)));

        return plusSys;
    }

    private static ActorSystem startFirerDaemon() {
        final String configNameInResources = FIRER_DAEMON_ACTOR_SYSTEM_NAME;
        final ActorSystem firerSys = ActorSystem.create(FIRER_DAEMON_ACTOR_SYSTEM_NAME,
                ConfigFactory.load((configNameInResources)));

        return firerSys;
    }

    String getGreeting() {
        return "Greetings MyApp!";
    }
}
