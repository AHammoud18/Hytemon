/*     */ package com.hypixel.hytale.server.core.modules.camera;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginManifest;
/*     */ import com.hypixel.hytale.event.EventBus;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.camera.SetFlyCameraMode;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.event.events.permissions.GroupPermissionChangeEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.permissions.PlayerGroupEvent;
/*     */ import com.hypixel.hytale.server.core.event.events.permissions.PlayerPermissionChangeEvent;
/*     */ import com.hypixel.hytale.server.core.permissions.PermissionsModule;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*     */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlyCameraModule
/*     */   extends JavaPlugin
/*     */ {
/*     */   @Nonnull
/*  29 */   public static final PluginManifest MANIFEST = PluginManifest.corePlugin(FlyCameraModule.class)
/*  30 */     .depends(new Class[] { PermissionsModule.class
/*  31 */       }).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FlyCameraModule(@Nonnull JavaPluginInit init) {
/*  39 */     super(init);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setup() {
/*  44 */     EventBus eventBus = HytaleServer.get().getEventBus();
/*  45 */     eventBus.register(PlayerPermissionChangeEvent.PermissionsRemoved.class, this::handlePlayerPermissionsRemoved);
/*  46 */     eventBus.register(PlayerGroupEvent.Removed.class, this::handlePlayerGroupRemoved);
/*  47 */     eventBus.register(GroupPermissionChangeEvent.Removed.class, this::handleGroupPermissionsRemoved);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePlayerPermissionsRemoved(@Nonnull PlayerPermissionChangeEvent.PermissionsRemoved event) {
/*  57 */     if (PermissionsModule.hasPermission(event.getRemovedPermissions(), "hytale.camera.flycam") == Boolean.TRUE) {
/*  58 */       checkAndEnforceFlyCameraPermission(event.getPlayerUuid());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handlePlayerGroupRemoved(@Nonnull PlayerGroupEvent.Removed event) {
/*  69 */     checkAndEnforceFlyCameraPermission(event.getPlayerUuid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleGroupPermissionsRemoved(@Nonnull GroupPermissionChangeEvent.Removed event) {
/*  79 */     if (PermissionsModule.hasPermission(event.getRemovedPermissions(), "hytale.camera.flycam") != Boolean.TRUE) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     String groupName = event.getGroupName();
/*  84 */     PermissionsModule permissionsModule = PermissionsModule.get();
/*     */     
/*  86 */     for (PlayerRef playerRef : Universe.get().getPlayers()) {
/*  87 */       UUID uuid = playerRef.getUuid();
/*  88 */       Set<String> groups = permissionsModule.getGroupsForUser(uuid);
/*     */       
/*  90 */       if (groups.contains(groupName)) {
/*  91 */         checkAndEnforceFlyCameraPermission(uuid);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkAndEnforceFlyCameraPermission(@Nonnull UUID uuid) {
/* 104 */     PlayerRef playerRef = Universe.get().getPlayer(uuid);
/* 105 */     if (playerRef == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     boolean hasPermission = PermissionsModule.get().hasPermission(uuid, "hytale.camera.flycam");
/*     */ 
/*     */     
/* 112 */     if (!hasPermission)
/* 113 */       playerRef.getPacketHandler().writeNoCache((Packet)new SetFlyCameraMode(false)); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\camera\FlyCameraModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */