package net.rush.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import net.rush.RushServer;
import net.rush.model.Session;
import net.rush.protocol.Packet;

/**
 * This is new instance for every channel - client that connects to
 * the server. It manages incoming messages (packets).
 */
public class NettyChannelHandler extends SimpleChannelInboundHandler<Packet> {
	
	private final RushServer server;
	public Session session;
	
	public NettyChannelHandler(RushServer server) {
		this.server = server;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("*********** Channel connected: " + ctx.channel().remoteAddress());
		
		Validate.isTrue(session == null, "Session already set!");
		session = new Session(ctx.channel(), server);
		server.sessionRegistry.add(session);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel disconnected: " + ctx.channel().remoteAddress() + " ***********");

		Objects.requireNonNull(session, "Session cannot be null!");
		server.sessionRegistry.remove(session);
		session = null;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Packet msg) throws Exception {
		session.messageReceived(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Logger.getLogger("Minecraft").log(Level.SEVERE, "Exception caught, closing channel.", cause);
		
		ctx.close();
		session = null;
	}
}
