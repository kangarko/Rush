package net.rush.netty;

import java.util.Objects;
import java.util.logging.Level;

import org.apache.commons.lang3.Validate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import net.rush.Server;
import net.rush.model.Session;
import net.rush.protocol.Packet;

/**
 * This is new instance for every channel - client that connects to
 * the server. It manages incoming messages (packets).
 */
public class RushChannelHandler extends SimpleChannelInboundHandler<Packet> {

	private final Server server;
	@Getter
	private Session session;

	public RushChannelHandler(Server server) {
		this.server = server;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		server.getLogger().info("[+] Channel connected: " + ctx.channel().remoteAddress());

		Validate.isTrue(session == null, "Session already set!");
		session = new Session(server, ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		server.getLogger().info("Channel disconnected: " + ctx.channel().remoteAddress() + " [-]");

		dispatchSession();
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Packet msg) throws Exception {
		session.messageReceived(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		server.getLogger().log(Level.SEVERE, "Exception caught, closing channel.", cause);

		ctx.close();
		dispatchSession();
	}

	private void dispatchSession() {
		Objects.requireNonNull(session);

		if (!session.isPendingRemoval())
			session.destroy();
		
		session = null;
	}
}
