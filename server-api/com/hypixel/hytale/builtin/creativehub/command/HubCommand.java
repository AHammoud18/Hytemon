/*    */ package com.hypixel.hytale.builtin.creativehub.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.creativehub.CreativeHubPlugin;
/*    */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubEntityConfig;
/*    */ import com.hypixel.hytale.builtin.creativehub.config.CreativeHubWorldConfig;
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.spawn.ISpawnProvider;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class HubCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_NOT_IN_HUB_WORLD = Message.translation("server.commands.hub.notInHubWorld");
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_ALREADY_IN_HUB = Message.translation("server.commands.hub.alreadyInHub");
/*    */   
/*    */   public HubCommand() {
/* 33 */     super("hub", "server.commands.hub.desc");
/* 34 */     addAliases(new String[] { "converge", "convergence" });
/* 35 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 45 */     World parentWorld = findParentHubWorld(store, ref);
/* 46 */     if (parentWorld == null) {
/* 47 */       playerRef.sendMessage(MESSAGE_NOT_IN_HUB_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     CreativeHubWorldConfig hubConfig = CreativeHubWorldConfig.get(parentWorld.getWorldConfig());
/* 52 */     if (hubConfig == null || hubConfig.getStartupInstance() == null) {
/* 53 */       playerRef.sendMessage(MESSAGE_NOT_IN_HUB_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 58 */     World currentHub = CreativeHubPlugin.get().getActiveHubInstance(parentWorld);
/* 59 */     if (currentHub != null && world.equals(currentHub)) {
/* 60 */       playerRef.sendMessage(MESSAGE_ALREADY_IN_HUB);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 65 */     ISpawnProvider spawnProvider = parentWorld.getWorldConfig().getSpawnProvider();
/*    */ 
/*    */     
/* 68 */     Transform returnPoint = (spawnProvider != null) ? spawnProvider.getSpawnPoint(parentWorld, playerRef.getUuid()) : new Transform();
/*    */ 
/*    */     
/* 71 */     World hubInstance = CreativeHubPlugin.get().getOrSpawnHubInstance(parentWorld, hubConfig, returnPoint);
/* 72 */     InstancesPlugin.teleportPlayerToInstance(ref, (ComponentAccessor)store, hubInstance, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private World findParentHubWorld(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref) {
/* 88 */     CreativeHubEntityConfig hubEntityConfig = (CreativeHubEntityConfig)store.getComponent(ref, CreativeHubEntityConfig.getComponentType());
/* 89 */     if (hubEntityConfig != null && hubEntityConfig.getParentHubWorldUuid() != null) {
/* 90 */       World parentWorld = Universe.get().getWorld(hubEntityConfig.getParentHubWorldUuid());
/* 91 */       if (parentWorld != null) {
/*    */         
/* 93 */         CreativeHubWorldConfig hubConfig = CreativeHubWorldConfig.get(parentWorld.getWorldConfig());
/* 94 */         if (hubConfig != null && hubConfig.getStartupInstance() != null) {
/* 95 */           return parentWorld;
/*    */         }
/*    */       } 
/*    */     } 
/* 99 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\creativehub\command\HubCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */