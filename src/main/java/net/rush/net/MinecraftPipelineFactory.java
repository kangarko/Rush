package net.rush.net;

import net.rush.Server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * A {@link ChannelPipelineFactory} for the Minecraft protocol.

 */
public final class MinecraftPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The server.
	 */
	private final Server server;

	/**
	 * Creates a new Minecraft pipeline factory.
	 * @param server The server.
	 */
	public MinecraftPipelineFactory(Server server) {
		this.server = server;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		return Channels.pipeline(new MinecraftDecoder(), new MinecraftEncoder(), new MinecraftHandler(server));
	}

}

