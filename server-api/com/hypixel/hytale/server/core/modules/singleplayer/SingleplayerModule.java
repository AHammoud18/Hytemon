/*     */ package com.hypixel.hytale.server.core.modules.singleplayer;
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.event.IEventDispatcher;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.packets.serveraccess.Access;
/*     */ import com.hypixel.hytale.protocol.packets.serveraccess.RequestServerAccess;
/*     */ import com.hypixel.hytale.server.core.Constants;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.Options;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.io.ServerManager;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.AccessControlModule;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.AccessProvider;
/*     */ import com.hypixel.hytale.server.core.modules.accesscontrol.provider.ClientDelegatingProvider;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.commands.PlayCommand;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.util.ProcessUtil;
/*     */ import io.netty.channel.Channel;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SingleplayerModule extends JavaPlugin {
/*  34 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(SingleplayerModule.class)
/*  35 */     .depends(new Class[] { AccessControlModule.class
/*  36 */       }).optDepends(new Class[] { InteractionModule.class
/*  37 */       }).build(); private static SingleplayerModule instance;
/*     */   private Access access;
/*     */   private Access requestedAccess;
/*     */   
/*     */   public static SingleplayerModule get() {
/*  42 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  47 */   private List<InetSocketAddress> publicAddresses = new CopyOnWriteArrayList<>();
/*     */   
/*     */   public SingleplayerModule(@Nonnull JavaPluginInit init) {
/*  50 */     super(init);
/*  51 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  56 */     if (Constants.SINGLEPLAYER) {
/*  57 */       AccessControlModule.get().registerAccessProvider((AccessProvider)new ClientDelegatingProvider());
/*     */     }
/*  59 */     getCommandRegistry().registerCommand((AbstractCommand)new PlayCommand(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start() {
/*  64 */     Integer pid = (Integer)Options.getOptionSet().valueOf(Options.CLIENT_PID);
/*  65 */     if (pid != null) {
/*  66 */       getLogger().at(Level.INFO).log("Client PID: %d", pid);
/*  67 */       HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> {
/*     */             try {
/*     */               checkClientPid();
/*  70 */             } catch (Exception e) {
/*     */               ((HytaleLogger.Api)getLogger().at(Level.SEVERE).withCause(e)).log("Failed to check client PID!");
/*     */             } 
/*     */           }60L, 60L, TimeUnit.SECONDS);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Access getAccess() {
/*  78 */     return this.access;
/*     */   }
/*     */   
/*     */   public Access getRequestedAccess() {
/*  82 */     return this.requestedAccess;
/*     */   }
/*     */   
/*     */   public void requestServerAccess(Access access) {
/*  86 */     if (!Constants.SINGLEPLAYER) throw new IllegalArgumentException("Server access can only be modified in singleplayer!");
/*     */     
/*  88 */     ServerManager serverManager = ServerManager.get();
/*  89 */     short externalPort = 0;
/*     */     
/*  91 */     if (access != Access.Private) {
/*  92 */       if (!serverManager.bind(new InetSocketAddress(0))) {
/*     */         
/*  94 */         requestServerAccess(Access.Private);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 100 */         InetSocketAddress boundAddress = serverManager.getNonLoopbackAddress();
/* 101 */         if (boundAddress != null) {
/* 102 */           externalPort = (short)boundAddress.getPort();
/*     */         }
/* 104 */       } catch (Exception e) {
/* 105 */         ((HytaleLogger.Api)getLogger().at(Level.WARNING).withCause(e)).log("Failed to get bound port");
/*     */       } 
/*     */     } else {
/* 108 */       for (Channel channel : serverManager.getListeners()) {
/* 109 */         SocketAddress socketAddress = channel.localAddress(); if (socketAddress instanceof InetSocketAddress) { InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress; if (inetSocketAddress
/* 110 */             .getAddress().isAnyLocalAddress()) {
/* 111 */             serverManager.unbind(channel);
/*     */           } }
/*     */       
/*     */       } 
/*     */     } 
/* 116 */     IEventDispatcher<SingleplayerRequestAccessEvent, SingleplayerRequestAccessEvent> dispatchFor = HytaleServer.get().getEventBus().dispatchFor(SingleplayerRequestAccessEvent.class);
/* 117 */     if (dispatchFor.hasListener()) dispatchFor.dispatch((IBaseEvent)new SingleplayerRequestAccessEvent(access));
/*     */     
/* 119 */     Universe.get().getPlayer(getUuid()).getPacketHandler().writeNoCache((Packet)new RequestServerAccess(access, externalPort));
/*     */     
/* 121 */     this.requestedAccess = access;
/*     */   }
/*     */   
/*     */   public void setPublicAddresses(List<InetSocketAddress> publicAddresses) {
/* 125 */     this.publicAddresses = publicAddresses;
/*     */   }
/*     */   
/*     */   public void updateAccess(@Nonnull Access access) {
/* 129 */     if (this.requestedAccess != access) {
/* 130 */       Universe.get().sendMessage(Message.translation("server.modules.sp.requestAccessDifferent")
/* 131 */           .param("requestedAccess", this.requestedAccess.toString())
/* 132 */           .param("access", access.toString()));
/*     */     }
/*     */ 
/*     */     
/* 136 */     Universe.get().sendMessage(Message.translation("server.modules.sp.serverAccessUpdated")
/* 137 */         .param("access", access.toString()));
/* 138 */     this.access = access;
/*     */   }
/*     */   
/*     */   public static void checkClientPid() {
/* 142 */     if (!ProcessUtil.isProcessRunning(((Integer)Options.getOptionSet().valueOf(Options.CLIENT_PID)).intValue())) {
/* 143 */       HytaleServer.get().shutdownServer(ShutdownReason.CLIENT_GONE);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static UUID getUuid() {
/* 149 */     return (UUID)Options.getOptionSet().valueOf(Options.OWNER_UUID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUsername() {
/* 155 */     return (String)Options.getOptionSet().valueOf(Options.OWNER_NAME);
/*     */   }
/*     */   
/*     */   public static boolean isOwner(@Nonnull PlayerRef player) {
/* 159 */     return isOwner(player.getPacketHandler().getAuth(), player.getUuid());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isOwner(PlayerAuthentication playerAuth, @Nonnull UUID playerUuid) {
/* 164 */     return playerUuid.equals(getUuid());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\singleplayer\SingleplayerModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */