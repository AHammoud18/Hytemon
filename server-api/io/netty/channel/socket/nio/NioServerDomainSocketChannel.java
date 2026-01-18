/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.RecvByteBufAllocator;
/*     */ import io.netty.channel.ServerChannel;
/*     */ import io.netty.channel.ServerChannelRecvByteBufAllocator;
/*     */ import io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import io.netty.util.NetUtil;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SocketUtils;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NioServerDomainSocketChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements ServerChannel
/*     */ {
/*  56 */   private static final Method OPEN_SERVER_SOCKET_CHANNEL_WITH_FAMILY = SelectorProviderUtil.findOpenMethod("openServerSocketChannel");
/*  57 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerDomainSocketChannel.class);
/*  58 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
/*  59 */   private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
/*     */   
/*     */   private final NioDomainServerSocketChannelConfig config;
/*     */   private volatile boolean bound;
/*     */   
/*     */   static ServerSocketChannel newChannel(SelectorProvider provider) {
/*  65 */     if (PlatformDependent.javaVersion() < 16) {
/*  66 */       throw new UnsupportedOperationException("Only supported with Java 16+");
/*     */     }
/*     */     
/*     */     try {
/*  70 */       ServerSocketChannel channel = SelectorProviderUtil.<ServerSocketChannel>newDomainSocketChannel(OPEN_SERVER_SOCKET_CHANNEL_WITH_FAMILY, provider);
/*  71 */       if (channel == null) {
/*  72 */         throw new ChannelException("Failed to open a socket.");
/*     */       }
/*  74 */       return channel;
/*  75 */     } catch (IOException e) {
/*  76 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerSocketChannel javaChannel() {
/*  82 */     return (ServerSocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerDomainSocketChannel() {
/*  89 */     this(DEFAULT_SELECTOR_PROVIDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerDomainSocketChannel(SelectorProvider provider) {
/*  96 */     this(newChannel(provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerDomainSocketChannel(ServerSocketChannel channel) {
/* 103 */     super(null, channel, 16);
/* 104 */     if (PlatformDependent.javaVersion() < 16) {
/* 105 */       throw new UnsupportedOperationException("Only supported with Java 16+");
/*     */     }
/* 107 */     this.config = new NioDomainServerSocketChannelConfig(this);
/*     */     
/*     */     try {
/* 110 */       this.bound = (channel.getLocalAddress() != null);
/* 111 */     } catch (IOException e) {
/* 112 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/* 118 */     return (ChannelConfig)this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 123 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 130 */     return (isOpen() && this.bound);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 135 */     javaChannel().bind(localAddress, this.config.getBacklog());
/* 136 */     this.bound = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 141 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 146 */     SocketChannel ch = SocketUtils.accept(javaChannel());
/*     */     try {
/* 148 */       if (ch != null) {
/* 149 */         buf.add(new NioDomainSocketChannel((Channel)this, ch));
/* 150 */         return 1;
/*     */       } 
/* 152 */     } catch (Throwable t) {
/* 153 */       logger.warn("Failed to create a new channel from an accepted socket.", t);
/*     */       
/*     */       try {
/* 156 */         ch.close();
/* 157 */       } catch (Throwable t2) {
/* 158 */         logger.warn("Failed to close a socket.", t2);
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/* 167 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 174 */     SocketAddress local = localAddress();
/*     */     try {
/* 176 */       super.doClose();
/*     */     } finally {
/* 178 */       javaChannel().close();
/* 179 */       if (local != null) {
/* 180 */         NioDomainSocketUtil.deleteSocketFile(local);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 190 */       return javaChannel().getLocalAddress();
/* 191 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 194 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   private final class NioDomainServerSocketChannelConfig
/*     */     extends DefaultChannelConfig {
/* 204 */     private volatile int backlog = NetUtil.SOMAXCONN;
/*     */     
/*     */     private NioDomainServerSocketChannelConfig(NioServerDomainSocketChannel channel) {
/* 207 */       super((Channel)channel, (RecvByteBufAllocator)new ServerChannelRecvByteBufAllocator());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void autoReadCleared() {
/* 212 */       NioServerDomainSocketChannel.this.clearReadPending();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<ChannelOption<?>, Object> getOptions() {
/* 217 */       List<ChannelOption<?>> options = new ArrayList<>();
/* 218 */       options.add(ChannelOption.SO_BACKLOG);
/* 219 */       for (ChannelOption<?> opt : NioChannelOption.getOptions(jdkChannel())) {
/* 220 */         options.add(opt);
/*     */       }
/* 222 */       return getOptions(super.getOptions(), options.<ChannelOption>toArray(new ChannelOption[0]));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T getOption(ChannelOption<T> option) {
/* 228 */       if (option == ChannelOption.SO_BACKLOG) {
/* 229 */         return (T)Integer.valueOf(getBacklog());
/*     */       }
/* 231 */       if (option instanceof NioChannelOption) {
/* 232 */         return NioChannelOption.getOption(jdkChannel(), (NioChannelOption<T>)option);
/*     */       }
/*     */       
/* 235 */       return (T)super.getOption(option);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> boolean setOption(ChannelOption<T> option, T value) {
/* 240 */       if (option == ChannelOption.SO_BACKLOG)
/* 241 */       { validate(option, value);
/* 242 */         setBacklog(((Integer)value).intValue()); }
/* 243 */       else { if (option instanceof NioChannelOption) {
/* 244 */           return NioChannelOption.setOption(jdkChannel(), (NioChannelOption<T>)option, value);
/*     */         }
/* 246 */         return super.setOption(option, value); }
/*     */ 
/*     */       
/* 249 */       return true;
/*     */     }
/*     */     
/*     */     private int getBacklog() {
/* 253 */       return this.backlog;
/*     */     }
/*     */     
/*     */     private NioDomainServerSocketChannelConfig setBacklog(int backlog) {
/* 257 */       ObjectUtil.checkPositiveOrZero(backlog, "backlog");
/* 258 */       this.backlog = backlog;
/* 259 */       return this;
/*     */     }
/*     */     
/*     */     private ServerSocketChannel jdkChannel() {
/* 263 */       return ((NioServerDomainSocketChannel)this.channel).javaChannel();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean closeOnReadError(Throwable cause) {
/* 270 */     return super.closeOnReadError(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 276 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 281 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\socket\nio\NioServerDomainSocketChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */