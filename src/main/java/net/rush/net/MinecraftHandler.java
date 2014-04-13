package net.rush.net;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rush.Server;
import net.rush.packets.Packet;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * A {@link SimpleChannelUpstreamHandler} which processes incoming network
 * events.
.
 */
public class MinecraftHandler extends SimpleChannelUpstreamHandler {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(MinecraftHandler.class.getName());

	/**
	 * The server.
	 */
	private final Server server;

	/**
	 * Creates a new network event handler.
	 * @param server The server.
	 */
	public MinecraftHandler(Server server) {
		this.server = server;
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel c = e.getChannel();
		server.getChannelGroup().add(c);

		Session session = new Session(server, c);
		server.getSessionRegistry().add(session);
		ctx.setAttachment(session);

		logger.info("Channel connected: " + c + ".");
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		Channel c = e.getChannel();
		server.getChannelGroup().remove(c);

		Session session = (Session) ctx.getAttachment();
		server.getSessionRegistry().remove(session);

		logger.info("Channel disconnected: " + c + ".");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Session session = (Session) ctx.getAttachment();
		session.messageReceived((Packet) e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		Channel c = e.getChannel();
		if (c.isOpen()) {
			if (e.getCause() instanceof IOException)
				logger.info("End of stream");
			else
				logger.log(Level.WARNING, "Exception caught, closing channel: " + c + "...", e.getCause());
			c.close();
		}
	}

}

