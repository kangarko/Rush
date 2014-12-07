package net.rush.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.rush.RushServer;
import net.rush.netty.pipeline.KickStringEncoder;
import net.rush.netty.pipeline.PacketDecoder;
import net.rush.netty.pipeline.PacketEncoder;
import net.rush.netty.pipeline.Varint21Decoder;
import net.rush.netty.pipeline.Varint21Encoder;
import net.rush.protocol.Protocol;

public class NettyInitializer extends Thread {

	private final RushServer server;

	public NettyInitializer(RushServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		
		EventLoopGroup bossgroup = new NioEventLoopGroup();
		EventLoopGroup workergroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap()
			.group(bossgroup, workergroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline()
					
					.addLast("timer", new ReadTimeoutHandler(30))
					.addLast("kickwriter", new KickStringEncoder())
					
					.addLast("varintdecoder", new Varint21Decoder())
					.addLast("decoder", new PacketDecoder(Protocol.HANDSHAKE))

					.addLast("varintprepender", new Varint21Encoder())
					.addLast("encoder", new PacketEncoder(Protocol.HANDSHAKE))

					.addLast("handler", new NettyChannelHandler(server));
				}
			});
			
			bootstrap.bind(server.port).sync().channel().closeFuture().sync();
			
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			
		} finally {
			bossgroup.shutdownGracefully();
			workergroup.shutdownGracefully();
		}	}

}