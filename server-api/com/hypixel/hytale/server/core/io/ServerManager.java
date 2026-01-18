/*     */ package com.hypixel.hytale.server.core.io;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.common.util.CompletableFutureUtil;
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.common.util.NetworkUtil;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.event.events.ShutdownEvent;
/*     */ import com.hypixel.hytale.server.core.io.commands.BindingsCommand;
/*     */ import com.hypixel.hytale.server.core.io.handlers.IPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.SubPacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.handlers.game.GamePacketHandler;
/*     */ import com.hypixel.hytale.server.core.io.transport.QUICTransport;
/*     */ import com.hypixel.hytale.server.core.io.transport.Transport;
/*     */ import com.hypixel.hytale.server.core.io.transport.TransportType;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ServerManager extends JavaPlugin {
/*  44 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(ServerManager.class)
/*  45 */     .build();
/*     */   
/*     */   @Nonnull
/*  48 */   private static final NetworkUtil.AddressType[] NON_PUBLIC_ADDRESS_TYPES = new NetworkUtil.AddressType[] { NetworkUtil.AddressType.ANY_LOCAL, NetworkUtil.AddressType.LOOPBACK, NetworkUtil.AddressType.SITE_LOCAL, NetworkUtil.AddressType.LINK_LOCAL, NetworkUtil.AddressType.MULTICAST };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ServerManager instance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerManager get() {
/*  59 */     return instance;
/*     */   }
/*     */   @Nonnull
/*  62 */   private final List<Channel> listeners = new CopyOnWriteArrayList<>();
/*     */   
/*     */   @Nonnull
/*  65 */   private final List<Function<IPacketHandler, SubPacketHandler>> subPacketHandlers = (List<Function<IPacketHandler, SubPacketHandler>>)new ObjectArrayList();
/*     */   
/*     */   @Nullable
/*     */   private Transport transport;
/*     */   
/*     */   @Nullable
/*     */   private CompletableFuture<Void> registerFuture;
/*     */   
/*     */   @Nullable
/*     */   private CompletableFuture<Void> bootFuture;
/*     */   
/*     */   public ServerManager(@Nonnull JavaPluginInit init) {
/*  77 */     super(init);
/*  78 */     instance = this;
/*     */ 
/*     */     
/*  81 */     if (Options.getOptionSet().has(Options.BARE))
/*     */       return; 
/*  83 */     init();
/*     */   }
/*     */   
/*     */   public void init() {
/*  87 */     this.registerFuture = CompletableFutureUtil._catch(CompletableFuture.runAsync(SneakyThrow.sneakyRunnable(() -> {
/*     */               long start = System.nanoTime();
/*     */               switch ((TransportType)Options.getOptionSet().valuesOf(Options.TRANSPORT).getFirst()) {
/*     */                 default:
/*     */                   throw new MatchException(null, null);
/*     */                 case TCP:
/*     */                 
/*     */                 case QUIC:
/*     */                   break;
/*     */               } 
/*     */               this.transport = (Transport)new QUICTransport();
/*     */               getLogger().at(Level.INFO).log("Took %s to setup transport!", FormatUtil.nanosToString(System.nanoTime() - start));
/*     */               this.registerFuture = null;
/*     */             })));
/*     */   }
/*     */   protected void setup() {
/* 103 */     getEventRegistry().register((short)-40, ShutdownEvent.class, event -> unbindAllListeners());
/*     */     
/* 105 */     get().registerSubPacketHandlers(com.hypixel.hytale.server.core.io.handlers.game.InventoryPacketHandler::new);
/*     */     
/* 107 */     getCommandRegistry().registerCommand((AbstractCommand)new BindingsCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/* 112 */     this.bootFuture = CompletableFuture.runAsync(() -> {
/*     */           CompletableFuture<Void> registerFuture = this.registerFuture;
/*     */           if (registerFuture != null) {
/*     */             registerFuture.getNow(null);
/*     */           }
/*     */           if (HytaleServer.get().isShuttingDown()) {
/*     */             return;
/*     */           }
/*     */           if (!Options.getOptionSet().has(Options.MIGRATIONS) && !Options.getOptionSet().has(Options.BARE)) {
/*     */             if (Constants.SINGLEPLAYER) {
/*     */               try {
/*     */                 InetAddress[] localhosts = InetAddress.getAllByName("localhost");
/*     */                 for (InetAddress localhost : localhosts)
/*     */                   bind(new InetSocketAddress(localhost, ((InetSocketAddress)Options.getOptionSet().valueOf(Options.BIND)).getPort())); 
/* 126 */               } catch (UnknownHostException e) {
/*     */                 throw SneakyThrow.sneakyThrow(e);
/*     */               } 
/*     */             } else {
/*     */               for (InetSocketAddress address : Options.getOptionSet().valuesOf(Options.BIND)) {
/*     */                 bind(address);
/*     */               }
/*     */               if (this.listeners.isEmpty()) {
/*     */                 throw new IllegalArgumentException("Listeners is empty after starting ServerManager!!");
/*     */               }
/*     */             } 
/*     */           }
/*     */           this.bootFuture = null;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void shutdown() {
/* 144 */     Universe.get().disconnectAllPLayers();
/*     */     
/* 146 */     unbindAllListeners();
/*     */     
/* 148 */     this.transport.shutdown();
/* 149 */     this.transport = null;
/*     */     
/* 151 */     getLogger().at(Level.INFO).log("Finished shutting down ServerManager...");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unbindAllListeners() {
/* 159 */     for (Channel channel : this.listeners) {
/* 160 */       unbind0(channel);
/*     */     }
/* 162 */     this.listeners.clear();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<Channel> getListeners() {
/* 167 */     return Collections.unmodifiableList(this.listeners);
/*     */   }
/*     */   
/*     */   public boolean bind(@Nonnull InetSocketAddress address) {
/* 171 */     if (address.getAddress().isAnyLocalAddress() && this.transport.getType() == TransportType.QUIC) {
/*     */       
/* 173 */       Channel channelIpv6 = bind0(new InetSocketAddress(NetworkUtil.ANY_IPV6_ADDRESS, address.getPort()));
/* 174 */       if (channelIpv6 != null) this.listeners.add(channelIpv6);
/*     */       
/* 176 */       Channel channelIpv4 = bind0(new InetSocketAddress(NetworkUtil.ANY_IPV4_ADDRESS, address.getPort()));
/* 177 */       if (channelIpv4 != null) this.listeners.add(channelIpv4);
/*     */ 
/*     */       
/* 180 */       Channel channelIpv6Localhost = bind0(new InetSocketAddress(NetworkUtil.LOOPBACK_IPV6_ADDRESS, address.getPort()));
/* 181 */       if (channelIpv6Localhost != null) this.listeners.add(channelIpv6Localhost);
/*     */       
/* 183 */       return (channelIpv4 != null || channelIpv6 != null);
/*     */     } 
/*     */     
/* 186 */     Channel channel = bind0(address);
/* 187 */     if (channel != null) this.listeners.add(channel); 
/* 188 */     return (channel != null);
/*     */   }
/*     */   
/*     */   public boolean unbind(@Nonnull Channel channel) {
/* 192 */     boolean success = unbind0(channel);
/* 193 */     if (success) this.listeners.remove(channel); 
/* 194 */     return success;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public InetSocketAddress getLocalOrPublicAddress() throws SocketException {
/* 199 */     for (Channel channel : this.listeners) {
/* 200 */       SocketAddress socketAddress = channel.localAddress();
/*     */       
/* 202 */       if (socketAddress instanceof InetSocketAddress) { InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*     */         
/* 204 */         InetAddress address = inetSocketAddress.getAddress();
/* 205 */         if (address.isLoopbackAddress()) return inetSocketAddress;
/*     */ 
/*     */         
/* 208 */         if (address.isAnyLocalAddress()) {
/* 209 */           InetAddress anyNonLoopbackAddress = NetworkUtil.getFirstNonLoopbackAddress();
/* 210 */           if (anyNonLoopbackAddress == null) return null; 
/* 211 */           return new InetSocketAddress(anyNonLoopbackAddress, inetSocketAddress.getPort());
/*     */         } 
/*     */         
/* 214 */         return inetSocketAddress; }
/*     */     
/* 216 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InetSocketAddress getNonLoopbackAddress() throws SocketException {
/* 222 */     for (Channel channel : this.listeners) {
/* 223 */       SocketAddress socketAddress = channel.localAddress();
/*     */       
/* 225 */       if (socketAddress instanceof InetSocketAddress) { InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*     */         
/* 227 */         InetAddress address = inetSocketAddress.getAddress();
/* 228 */         if (address.isLoopbackAddress()) {
/*     */           continue;
/*     */         }
/* 231 */         if (address.isAnyLocalAddress()) {
/* 232 */           InetAddress anyNonLoopbackAddress = NetworkUtil.getFirstNonLoopbackAddress();
/* 233 */           if (anyNonLoopbackAddress == null) return null; 
/* 234 */           return new InetSocketAddress(anyNonLoopbackAddress, inetSocketAddress.getPort());
/*     */         } 
/*     */         
/* 237 */         return inetSocketAddress; }
/*     */     
/* 239 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public InetSocketAddress getPublicAddress() throws SocketException {
/* 245 */     for (Channel channel : this.listeners) {
/* 246 */       SocketAddress socketAddress = channel.localAddress();
/*     */       
/* 248 */       if (socketAddress instanceof InetSocketAddress) { InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
/*     */         
/* 250 */         InetAddress address = inetSocketAddress.getAddress();
/* 251 */         if (address.isLoopbackAddress() || 
/* 252 */           address.isSiteLocalAddress()) {
/*     */           continue;
/*     */         }
/* 255 */         if (address.isAnyLocalAddress()) {
/* 256 */           InetAddress anyPublicAddress = NetworkUtil.getFirstAddressWithout(NON_PUBLIC_ADDRESS_TYPES);
/* 257 */           if (anyPublicAddress == null) return null; 
/* 258 */           return new InetSocketAddress(anyPublicAddress, inetSocketAddress.getPort());
/*     */         } 
/*     */         
/* 261 */         return inetSocketAddress; }
/*     */     
/* 263 */     }  return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitForBindComplete() {
/* 268 */     CompletableFuture<Void> future = this.bootFuture;
/* 269 */     if (future != null) future.getNow(null);
/*     */   
/*     */   }
/*     */   
/*     */   public void registerSubPacketHandlers(@Nonnull Function<IPacketHandler, SubPacketHandler> supplier) {
/* 274 */     this.subPacketHandlers.add(supplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void populateSubPacketHandlers(@Nonnull GamePacketHandler packetHandler) {
/* 279 */     for (Function<IPacketHandler, SubPacketHandler> subPacketHandler : this.subPacketHandlers) {
/* 280 */       packetHandler.registerSubPacketHandler(subPacketHandler.apply(packetHandler));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Channel bind0(@Nonnull InetSocketAddress address) {
/* 286 */     long start = System.nanoTime();
/* 287 */     getLogger().at(Level.FINE).log("Binding to %s (%s)", address, this.transport.getType());
/*     */     try {
/* 289 */       ChannelFuture f = this.transport.bind(address).sync();
/* 290 */       if (f.isSuccess()) {
/* 291 */         Channel channel = f.channel();
/* 292 */         getLogger().at(Level.INFO).log("Listening on %s and took %s", channel.localAddress(), FormatUtil.nanosToString(System.nanoTime() - start));
/* 293 */         return channel;
/*     */       } 
/*     */       
/* 296 */       ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause((Throwable)new SkipSentryException(f.cause()))).log("Could not bind to host %s", address);
/*     */     }
/* 298 */     catch (InterruptedException e) {
/* 299 */       Thread.currentThread().interrupt();
/* 300 */       throw new RuntimeException("Interrupted when attempting to bind to host " + String.valueOf(address), e);
/* 301 */     } catch (Throwable t) {
/*     */       
/* 303 */       ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause((Throwable)new SkipSentryException(t))).log("Failed to bind to %s", address);
/*     */     } 
/* 305 */     return null;
/*     */   }
/*     */   
/*     */   private boolean unbind0(@Nonnull Channel channel) {
/* 309 */     long start = System.nanoTime();
/* 310 */     getLogger().at(Level.FINE).log("Closing listener %s", channel);
/*     */     try {
/* 312 */       channel.close().await(1L, TimeUnit.SECONDS);
/* 313 */       getLogger().at(Level.INFO).log("Closed listener %s and took %s", channel, FormatUtil.nanosToString(System.nanoTime() - start));
/* 314 */       return true;
/* 315 */     } catch (InterruptedException e) {
/* 316 */       ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to await for listener to close!");
/* 317 */       Thread.currentThread().interrupt();
/* 318 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\ServerManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */