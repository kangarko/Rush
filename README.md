### Latest information (03.2018): The development has been paused for a few years due to other priorities. It will remain here for historical purposes and for possible future comeback(s).

Rush
====

An alternative Minecraft server in Java.

Introduction
------------

Rush is a lightweight, from scratch, open source
[Minecraft](http://minecraft.net) server written with latest standards in Java 8 and Netty 5.

The main goals of the project are to provide a lightweight and modern Minecraft server 
where exact vanilla functionality is not needed or higher performance is 
desired than the official software can deliver.

Jenkins
-------

Official build server with compiled builds is found [here](https://ci.ekranos.me/job/Rush/)

Credits to Ekranos for setup and host.

Compatibility
-------------

Rush is currently compatible with following Minecraft versions.
* 1.7.6 - 1.7.9 (protocol #5)
* 1.7.2 - 1.7.5 (protocol #4)

Planned compatibility:
* 1.8.1 (high priority)
* 1.2.5 (fork maybe?)
* beta1.7.3 (fork maybe?)

Building
--------

Rush can be easily built with Maven.
In Eclipse, import as Existing Maven Project and locate the pom.xml.
Recommended compile goal is "clean install".
Compiled files are located inside the Rush/server/target and Rush/api/target folders.


Running
-------

Running Rush is simple because its dependencies are shaded into the output
jar at compile time. Simply execute `java -jar craftrush.jar` along with any
extra JVM options desired.

Rush uses [JLine](http://jline.sf.net) for console input and colored
console output. 

API
---

Please note that the API is NOT compatible with the Bukkit API and is in early development.


Credits
-------

 * [The Minecraft Coalition](http://wiki.vg/wiki) - protocol and file formats
   research.
 * [Trustin Lee](http://gleamynode.net) - author of the
   [Netty](http://jboss.org/netty) library.
 * Graham Edgecombe - author of the original
   [Lightstone](https://github.com/grahamedgecombe/lightstone) - I learned a lot from that server.
 * [Glowstone](https://github.com/SpaceManiac/Glowstone) - for making an awesome open source Minecraft server in java.
 * [Notch](http://mojang.com/notch) and all the other people at
   [Mojang](http://mojang.com) - for making such an awesome game in the first
   place!
