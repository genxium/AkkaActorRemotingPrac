This repo is for internal training use only.

Paths
==      

You're reading `<proj-root>/README.md`. 

Runtime differentiation
==

You're supposed to play with the built deliverable from this repository, i.e. `<proj-root>/build/` after `user@proj-root> gradle build` seen by `user@proj-root> gradle run`, at runtime.

When testing on a single machine with a single OS installed, there should be at least 2 processes launched
from the same class "com.sample.remoting.akka.MyApp", differentiated by launch args.

- If launched by `gradle run -Pplusdaemon`, then the process will be _binding to_ `localhost port 2552` as an [ActorSystem](https://doc.akka.io/japi/akka/current/akka/actor/ActorSystem.html) to provide `PlusActor` for remote access.
    - It's deliberate that the term _"listening to"_ is NOT used here, allowing the `ActorSystem` to provide actors for remote access using UDP transport as well. 
- If launched by `gradle run -Pfire`, then the process will try to fire an one-off access to the remote `ActorSystem`, i.e. the `plusdaemon`, which provides `PlusActor`.
    - Although an `ActorSystem` named "firerdaemon" local to the process launched by `gradle run -Pfire` would still be created to provide an [ActorContext](https://doc.akka.io/japi/akka/current/akka/actor/ActorContext.html) for the necessary [ActorRefFactory.actorSelection(...)](https://doc.akka.io/japi/akka/current/akka/actor/ActorRefFactory.html#actorSelection-java.lang.String-) invocation. However it's made one-off-alike, i.e. this "firerdaemon" will be terminated once the only `FirerActor`
      receives a response from the remote `PlusActor`. See `FirerActor.postStop()` for details.


References
==

- https://doc.akka.io/docs/akka/current/general/addressing.html.
- https://doc.akka.io/docs/akka/current/remoting-artery.html.
    - As well as the old one, https://doc.akka.io/docs/akka/current/remoting.html.
- https://doc.akka.io/docs/akka/current/serialization.html.
- https://doc.akka.io/docs/akka/current/general/configuration.html.
- https://github.com/akka/akka-samples/tree/2.5/akka-sample-remote-java.
