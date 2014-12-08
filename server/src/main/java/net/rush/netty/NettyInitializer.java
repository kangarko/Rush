package net.rush.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.rush.RushServer;
import net.rush.netty.pipeline.KickStringWriter;
import net.rush.netty.pipeline.PacketDecoder;
import net.rush.netty.pipeline.PacketEncoder;
import net.rush.netty.pipeline.Varint21Decoder;
import net.rush.netty.pipeline.Varint21Encoder;
import net.rush.protocol.Protocol;

public class NettyInitializer extends Thread {

	private final RushServer server;

	public NettyInitializer(RushServer server) {
		this.server = server;
		setName("Netty Initializer Thread");
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
					.addLast("kickwriter", new KickStringWriter())

					.addLast("varintdecoder", new Varint21Decoder())
					.addLast("decoder", new PacketDecoder(Protocol.HANDSHAKE))

					.addLast("varintprepender", new Varint21Encoder())
					.addLast("encoder", new PacketEncoder(Protocol.HANDSHAKE))

					.addLast("handler", new NettyChannelHandler(server));
				}
			});

			try {
				bootstrap.bind(server.port).sync().channel().closeFuture().sync();
			} catch (Throwable t) {
				server.getLogger().severe("** FAILED TO BIND TO THE PORT! Make sure that");
				server.getLogger().severe("another server is not running on that port. **");
				server.getLogger().severe("The exception was: " + t.getMessage());
				System.exit(0);
			}

		} finally {
			bossgroup.shutdownGracefully();
			workergroup.shutdownGracefully();
		}	}

}