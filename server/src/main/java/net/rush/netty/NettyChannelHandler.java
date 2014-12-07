package net.rush.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.RequiredArgsConstructor;
import net.rush.RushServer;
import net.rush.model.Session;
import net.rush.protocol.Packet;

/**
 * This is new instance for every channel - client that connects to
 * the server. It manages incoming messages (packets).
 */
@RequiredArgsConstructor
public class NettyChannelHandler extends SimpleChannelInboundHandler<Packet> {
	
	private final RushServer server;
	private Session connection;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("*********** Channel connected: " + ctx.channel().remoteAddress());
		
		connection = new Session(ctx.channel());
		server.sessionRegistry.add(connection);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel disconnected: " + ctx.channel().remoteAddress() + " ***********");
		
		server.sessionRegistry.remove(connection);
		connection = null;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Packet msg) throws Exception {		
		connection.messageReceived(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Logger.getLogger("Minecraft").log(Level.SEVERE, "Exception caught, closing channel.", cause);
		
		ctx.close();
		connection = null;
	}
}
