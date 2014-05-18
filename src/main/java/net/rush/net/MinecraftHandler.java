package net.rush.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.Server;
import net.rush.packets.Packet;

/**
 * A {@link SimpleChannelUpstreamHandler} which processes incoming network
 * events.
.
 */
public class MinecraftHandler extends SimpleChannelInboundHandler<Packet> {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger("Minecraft");

	/**
	 * The server.
	 */
	private final Server server;
	private final boolean compact;
	
	Session session;
	
	/**
	 * Creates a new network event handler.
	 * @param server The server.
	 */
	public MinecraftHandler(Server server, boolean compact) {
		this.server = server;
		this.compact = compact;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel c = ctx.channel();
		server.getChannelGroup().add(c);

		Session session = new Session(server, c, compact);
		server.getSessionRegistry().add(session);
		//ctx.setAttachment(session);
		this.session = session;

		logger.info("Channel connected: " + c + ".");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		Channel c = ctx.channel();
		server.getChannelGroup().remove(c);

		//Session session = (Session) ctx.getAttachment();
		server.getSessionRegistry().remove(session);

		logger.info("Channel disconnected: " + c + ".");
	}
		
	@Override
	public void messageReceived(ChannelHandlerContext ctx, Packet packet) throws Exception {
		//Session session = (Session) ctx.getAttachment();
		session.messageReceived(packet);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (cause instanceof IOException)
			logger.info("End of stream ");
		else
			logger.log(Level.WARNING, "Exception caught, closing channel: " + ctx.channel() + "...", cause);
		ctx.close();
	}

}

