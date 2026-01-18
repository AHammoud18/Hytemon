/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.channel.socket.DefaultServerSocketChannelConfig;
/*     */ import io.netty.channel.socket.InternetProtocolFamily;
/*     */ import io.netty.channel.socket.ServerSocketChannel;
/*     */ import io.netty.channel.socket.ServerSocketChannelConfig;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NioServerSocketChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*  50 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*  51 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */   
/*  53 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
/*     */ 
/*     */   
/*  56 */   private static final Method OPEN_SERVER_SOCKET_CHANNEL_WITH_FAMILY = SelectorProviderUtil.findOpenMethod("openServerSocketChannel");
/*     */   private final ServerSocketChannelConfig config;
/*     */   
/*     */   private static ServerSocketChannel newChannel(SelectorProvider provider, SocketProtocolFamily family) {
/*     */     try {
/*  61 */       ServerSocketChannel channel = SelectorProviderUtil.<ServerSocketChannel>newChannel(OPEN_SERVER_SOCKET_CHANNEL_WITH_FAMILY, provider, family);
/*  62 */       return (channel == null) ? provider.openServerSocketChannel() : channel;
/*  63 */     } catch (IOException e) {
/*  64 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerSocketChannel() {
/*  74 */     this(DEFAULT_SELECTOR_PROVIDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerSocketChannel(SelectorProvider provider) {
/*  81 */     this(provider, (SocketProtocolFamily)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public NioServerSocketChannel(SelectorProvider provider, InternetProtocolFamily family) {
/*  91 */     this(provider, (family == null) ? null : family.toSocketProtocolFamily());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerSocketChannel(SelectorProvider provider, SocketProtocolFamily family) {
/*  98 */     this(newChannel(provider, family));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerSocketChannel(ServerSocketChannel channel) {
/* 105 */     super(null, channel, 16);
/* 106 */     this.config = (ServerSocketChannelConfig)new NioServerSocketChannelConfig(this, javaChannel().socket());
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 111 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 116 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig config() {
/* 121 */     return this.config;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 128 */     return (isOpen() && javaChannel().socket().isBound());
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerSocketChannel javaChannel() {
/* 138 */     return (ServerSocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 143 */     return SocketUtils.localSocketAddress(javaChannel().socket());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 148 */     javaChannel().bind(localAddress, this.config.getBacklog());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 153 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 158 */     SocketChannel ch = SocketUtils.accept(javaChannel());
/*     */     
/*     */     try {
/* 161 */       if (ch != null) {
/* 162 */         buf.add(new NioSocketChannel((Channel)this, ch));
/* 163 */         return 1;
/*     */       } 
/* 165 */     } catch (Throwable t) {
/* 166 */       logger.warn("Failed to create a new channel from an accepted socket.", t);
/*     */       
/*     */       try {
/* 169 */         ch.close();
/* 170 */       } catch (Throwable t2) {
/* 171 */         logger.warn("Failed to close a socket.", t2);
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 182 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 197 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/* 202 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Object filterOutboundMessage(Object msg) throws Exception {
/* 207 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   private final class NioServerSocketChannelConfig extends DefaultServerSocketChannelConfig {
/*     */     private NioServerSocketChannelConfig(NioServerSocketChannel channel, ServerSocket javaSocket) {
/* 212 */       super(channel, javaSocket);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 217 */       NioServerSocketChannel.this.clearReadPending();
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 222 */       if (option instanceof NioChannelOption) {
/* 223 */         return NioChannelOption.setOption(jdkChannel(), (NioChannelOption<T>)option, value);
/*     */       }
/* 225 */       return super.setOption(option, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> T getOption(ChannelOption<T> option) {
/* 230 */       if (option instanceof NioChannelOption) {
/* 231 */         return NioChannelOption.getOption(jdkChannel(), (NioChannelOption<T>)option);
/*     */       }
/* 233 */       return (T)super.getOption(option);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<ChannelOption<?>, Object> getOptions() {
/* 238 */       return getOptions(super.getOptions(), (ChannelOption[])NioChannelOption.getOptions(jdkChannel()));
/*     */     }
/*     */     
/*     */     private ServerSocketChannel jdkChannel() {
/* 242 */       return ((NioServerSocketChannel)this.channel).javaChannel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeOnReadError(Throwable cause) {
/* 249 */     return super.closeOnReadError(cause);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioServerSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */