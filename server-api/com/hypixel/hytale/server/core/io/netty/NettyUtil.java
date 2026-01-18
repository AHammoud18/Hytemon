/*     */ package com.hypixel.hytale.server.core.io.netty;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.backend.HytaleLoggerBackend;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.util.concurrent.ThreadUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFactory;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollDatagramChannel;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.kqueue.KQueue;
/*     */ import io.netty.channel.kqueue.KQueueDatagramChannel;
/*     */ import io.netty.channel.kqueue.KQueueServerSocketChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.DatagramChannel;
/*     */ import io.netty.channel.socket.SocketProtocolFamily;
/*     */ import io.netty.channel.socket.nio.NioDatagramChannel;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.codec.quic.QuicChannel;
/*     */ import io.netty.handler.codec.quic.QuicStreamChannel;
/*     */ import io.netty.handler.logging.LogLevel;
/*     */ import io.netty.handler.logging.LoggingHandler;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NettyUtil {
/*  41 */   public static final HytaleLogger CONNECTION_EXCEPTION_LOGGER = HytaleLogger.get("ConnectionExceptionLogging");
/*  42 */   public static final HytaleLogger PACKET_LOGGER = HytaleLogger.get("PacketLogging"); public static final String PACKET_DECODER = "packetDecoder";
/*     */   public static final String PACKET_ARRAY_ENCODER = "packetArrayEncoder";
/*     */   
/*     */   static {
/*  46 */     HytaleLoggerBackend loggerBackend = HytaleLoggerBackend.getLogger(PACKET_LOGGER.getName());
/*  47 */     loggerBackend.setOnLevelChange((oldLevel, newLevel) -> {
/*     */           Universe universe = Universe.get();
/*     */           if (universe != null) {
/*     */             if (newLevel == Level.OFF) {
/*     */               for (PlayerRef p : universe.getPlayers()) {
/*     */                 uninjectLogger(p.getPacketHandler().getChannel());
/*     */               }
/*     */             } else {
/*     */               for (PlayerRef p : universe.getPlayers()) {
/*     */                 injectLogger(p.getPacketHandler().getChannel());
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*  61 */     PACKET_LOGGER.setLevel(Level.OFF);
/*  62 */     loggerBackend.loadLogLevel();
/*     */     
/*  64 */     CONNECTION_EXCEPTION_LOGGER.setLevel(Level.ALL);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final PacketArrayEncoder PACKET_ARRAY_ENCODER_INSTANCE = new PacketArrayEncoder();
/*     */   public static final String PACKET_ENCODER = "packetEncoder";
/*     */   public static final String LOGGER_KEY = "logger";
/*  72 */   public static final LoggingHandler LOGGER = new LoggingHandler("PacketLogging", LogLevel.INFO);
/*     */   
/*     */   public static final String HANDLER = "handler";
/*     */   public static final String TIMEOUT_HANDLER = "timeOut";
/*     */   public static final String RATE_LIMIT = "rateLimit";
/*     */   
/*     */   public static void init() {}
/*     */   
/*     */   private static void injectLogger(@Nonnull Channel channel) {
/*  81 */     if (channel.pipeline().get("logger") == null) {
/*  82 */       channel.pipeline().addAfter("packetArrayEncoder", "logger", (ChannelHandler)LOGGER);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void uninjectLogger(@Nonnull Channel channel) {
/*  87 */     channel.pipeline().remove("logger");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setChannelHandler(@Nonnull Channel channel, @Nonnull PacketHandler packetHandler) {
/*  92 */     ChannelHandler oldHandler = channel.pipeline().replace("handler", "handler", (ChannelHandler)new PlayerChannelHandler(packetHandler));
/*     */     
/*  94 */     PacketHandler oldPlayerConnection = null;
/*  95 */     if (oldHandler instanceof PlayerChannelHandler) {
/*  96 */       oldPlayerConnection = ((PlayerChannelHandler)oldHandler).getHandler();
/*  97 */       oldPlayerConnection.unregistered(packetHandler);
/*     */     } 
/*     */     
/* 100 */     packetHandler.registered(oldPlayerConnection);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EventLoopGroup getEventLoopGroup(String name) {
/* 105 */     return getEventLoopGroup(0, name);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EventLoopGroup getEventLoopGroup(int nThreads, String name) {
/* 110 */     if (nThreads == 0) {
/* 111 */       nThreads = Math.max(1, SystemPropertyUtil.getInt("server.io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
/*     */     }
/* 113 */     ThreadFactory factory = ThreadUtil.daemonCounted(name + " - %d");
/* 114 */     if (Epoll.isAvailable())
/* 115 */       return (EventLoopGroup)new EpollEventLoopGroup(nThreads, factory); 
/* 116 */     if (KQueue.isAvailable()) {
/* 117 */       return (EventLoopGroup)new KQueueEventLoopGroup(nThreads, factory);
/*     */     }
/* 119 */     return (EventLoopGroup)new NioEventLoopGroup(nThreads, factory);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Class<? extends ServerChannel> getServerChannel() {
/* 125 */     if (Epoll.isAvailable())
/* 126 */       return (Class)EpollServerSocketChannel.class; 
/* 127 */     if (KQueue.isAvailable()) {
/* 128 */       return (Class)KQueueServerSocketChannel.class;
/*     */     }
/* 130 */     return (Class)NioServerSocketChannel.class;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ReflectiveChannelFactory<? extends DatagramChannel> getDatagramChannelFactory(SocketProtocolFamily family) {
/* 136 */     if (Epoll.isAvailable())
/* 137 */       return new ReflectiveChannelFactory(EpollDatagramChannel.class, family); 
/* 138 */     if (KQueue.isAvailable()) {
/* 139 */       return new ReflectiveChannelFactory(KQueueDatagramChannel.class, family);
/*     */     }
/* 141 */     return new ReflectiveChannelFactory(NioDatagramChannel.class, family);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatRemoteAddress(Channel channel) {
/* 146 */     if (channel instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)channel;
/* 147 */       return String.valueOf(quicChannel.remoteAddress()) + " (" + String.valueOf(quicChannel.remoteAddress()) + ")"; }
/* 148 */      if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/*     */       
/* 150 */       return String.valueOf(quicStreamChannel.parent().localAddress()) + " (" + String.valueOf(quicStreamChannel.parent().localAddress()) + ", streamId=" + String.valueOf(quicStreamChannel.parent().remoteSocketAddress()) + ")"; }
/*     */     
/* 152 */     return channel.remoteAddress().toString();
/*     */   }
/*     */   
/*     */   public static String formatLocalAddress(Channel channel) {
/* 156 */     if (channel instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)channel;
/* 157 */       return String.valueOf(quicChannel.localAddress()) + " (" + String.valueOf(quicChannel.localAddress()) + ")"; }
/* 158 */      if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/* 159 */       return String.valueOf(quicStreamChannel.parent().localAddress()) + " (" + String.valueOf(quicStreamChannel.parent().localAddress()) + ", streamId=" + String.valueOf(quicStreamChannel.parent().localSocketAddress()) + ")"; }
/*     */     
/* 161 */     return channel.localAddress().toString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static SocketAddress getRemoteSocketAddress(Channel channel) {
/* 166 */     if (channel instanceof QuicChannel) { QuicChannel quicChannel = (QuicChannel)channel;
/* 167 */       return quicChannel.remoteSocketAddress(); }
/* 168 */      if (channel instanceof QuicStreamChannel) { QuicStreamChannel quicStreamChannel = (QuicStreamChannel)channel;
/* 169 */       return quicStreamChannel.parent().remoteSocketAddress(); }
/*     */     
/* 171 */     return channel.remoteAddress();
/*     */   }
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
/*     */   public static boolean isFromSameOrigin(Channel channel1, Channel channel2) {
/* 184 */     SocketAddress remoteSocketAddress1 = getRemoteSocketAddress(channel1);
/* 185 */     SocketAddress remoteSocketAddress2 = getRemoteSocketAddress(channel2);
/*     */ 
/*     */     
/* 188 */     if (remoteSocketAddress1 == null || remoteSocketAddress2 == null) return false;
/*     */ 
/*     */     
/* 191 */     if (Objects.equals(remoteSocketAddress1, remoteSocketAddress2)) return true;
/*     */ 
/*     */     
/* 194 */     if (!remoteSocketAddress1.getClass().equals(remoteSocketAddress2.getClass())) return false;
/*     */ 
/*     */     
/* 197 */     if (remoteSocketAddress1 instanceof InetSocketAddress) { InetSocketAddress remoteInetSocketAddress1 = (InetSocketAddress)remoteSocketAddress1; if (remoteSocketAddress2 instanceof InetSocketAddress) {
/* 198 */         InetSocketAddress remoteInetSocketAddress2 = (InetSocketAddress)remoteSocketAddress2;
/* 199 */         if (remoteInetSocketAddress1.getAddress().isLoopbackAddress() && remoteInetSocketAddress2.getAddress().isLoopbackAddress())
/*     */         {
/* 201 */           return true;
/*     */         }
/* 203 */         return remoteInetSocketAddress1.getAddress().equals(remoteInetSocketAddress2.getAddress());
/*     */       }  }
/*     */     
/* 206 */     return false;
/*     */   }
/*     */   
/*     */   public static class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory<T> {
/*     */     @Nonnull
/*     */     private final Constructor<? extends T> constructor;
/*     */     private final SocketProtocolFamily family;
/*     */     
/*     */     public ReflectiveChannelFactory(@Nonnull Class<? extends T> clazz, SocketProtocolFamily family) {
/* 215 */       ObjectUtil.checkNotNull(clazz, "clazz");
/*     */       try {
/* 217 */         this.constructor = clazz.getConstructor(new Class[] { SocketProtocolFamily.class });
/* 218 */         this.family = family;
/* 219 */       } catch (NoSuchMethodException e) {
/* 220 */         throw new IllegalArgumentException("Class " + StringUtil.simpleClassName(clazz) + " does not have a public non-arg constructor", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public T newChannel() {
/*     */       try {
/* 229 */         return this.constructor.newInstance(new Object[] { this.family });
/* 230 */       } catch (Throwable t) {
/* 231 */         throw new ChannelException("Unable to create Channel from class " + String.valueOf(this.constructor.getDeclaringClass()), t);
/*     */       } 
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public String getSimpleName() {
/* 237 */       return StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + "(" + StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + ")";
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 243 */       return StringUtil.simpleClassName(io.netty.channel.ReflectiveChannelFactory.class) + "(" + StringUtil.simpleClassName(io.netty.channel.ReflectiveChannelFactory.class) + ".class, " + 
/* 244 */         StringUtil.simpleClassName(this.constructor.getDeclaringClass()) + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\netty\NettyUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */