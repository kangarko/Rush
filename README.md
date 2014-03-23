Rush
==========

Introduction
------------

Rush is a lightweight, from scratch, open source
[Minecraft](http://minecraft.net) server written in Java that will support plugins
written for the [Bukkit](http://bukkit.org) API.

The main goals of the project are to provide a lightweight implementation
of the Bukkit API and Minecraft server where exact vanilla functionality is
not needed or higher performance is desired than the official software can
deliver.

Building
--------

Rush can be build with Maven.
In Eclipse, import as Existing Maven Project and locate the pom.xml.
Recommended compile goal is "clean install".
Compiled file is located inside the Rush folder and inside the "target" folder.


Running
-------

Running Rush is simple because its dependencies are shaded into the output
jar at compile time. Simply execute `java -jar rush.jar` along with any
extra JVM options desired.

Rush uses [JLine](http://jline.sf.net) for console input and colored
console output. 

Documentation
-------------

Rush has currently partial documentation in code and more will follow.

For documentation on the Bukkit API, see the
[Bukkit Javadocs](http://jd.bukkit.org/).

Credits
-------

 * [The Minecraft Coalition](http://wiki.vg/wiki) - protocol and file formats
   research.
 * [Trustin Lee](http://gleamynode.net) - author of the
   [Netty](http://jboss.org/netty) library.
 * Graham Edgecombe - author of the original
   [Lightstone](https://github.com/grahamedgecombe/lightstone) - and everyone
   else who has contributed to Lightstone.
 * [Glowstone](https://github.com/SpaceManiac/Glowstone) - for making open source Minecraft server in java.
 * [Maincraft] (https://github.com/Maincraft/Maincraft) - Rush uses its packet functionality and serialization.
 * The [Bukkit](http://bukkit.org) team for their outstandingly well-designed
   plugin API.
 * [Notch](http://mojang.com/notch) and all the other people at
   [Mojang](http://mojang.com) - for making such an awesome game in the first
   place!
