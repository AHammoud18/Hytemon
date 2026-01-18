/*    */ package com.hypixel.hytale.builtin.teleport.commands.warp;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.Warp;
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WarpCommand extends AbstractCommandCollection {
/* 24 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED = Message.translation("server.commands.teleport.warp.notLoaded");
/* 25 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_UNKNOWN_WARP = Message.translation("server.commands.teleport.warp.unknownWarp");
/* 26 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/* 27 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_WORLD_NAME_FOR_WARP_NOT_FOUND = Message.translation("server.commands.teleport.warp.worldNameForWarpNotFound");
/* 28 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_WARPED_TO = Message.translation("server.commands.teleport.warp.warpedTo");
/*    */   
/*    */   public WarpCommand() {
/* 31 */     super("warp", "server.commands.warp.desc");
/*    */ 
/*    */     
/* 34 */     addUsageVariant((AbstractCommand)new WarpGoVariantCommand());
/*    */ 
/*    */     
/* 37 */     addSubCommand((AbstractCommand)new WarpGoCommand());
/* 38 */     addSubCommand((AbstractCommand)new WarpSetCommand());
/* 39 */     addSubCommand((AbstractCommand)new WarpListCommand());
/* 40 */     addSubCommand((AbstractCommand)new WarpRemoveCommand());
/* 41 */     addSubCommand((AbstractCommand)new WarpReloadCommand());
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static void tryGo(@Nonnull CommandContext context, @Nonnull String warp, @Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
/* 60 */     if (!TeleportPlugin.get().isWarpsLoaded()) {
/* 61 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 66 */     Warp targetWarp = (Warp)TeleportPlugin.get().getWarps().get(warp.toLowerCase());
/* 67 */     if (targetWarp == null) {
/* 68 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_UNKNOWN_WARP.param("name", warp));
/*    */       
/*    */       return;
/*    */     } 
/* 72 */     String worldName = targetWarp.getWorld();
/* 73 */     World world = Universe.get().getWorld(worldName);
/* 74 */     Teleport teleport = targetWarp.toTeleport();
/* 75 */     if (world == null || teleport == null) {
/* 76 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_WORLD_NAME_FOR_WARP_NOT_FOUND.param("worldName", worldName).param("warp", warp));
/*    */       
/*    */       return;
/*    */     } 
/* 80 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 81 */     assert transformComponent != null;
/*    */     
/* 83 */     HeadRotation headRotationComponent = (HeadRotation)store.getComponent(ref, HeadRotation.getComponentType());
/* 84 */     assert headRotationComponent != null;
/*    */     
/* 86 */     Vector3d playerPosition = transformComponent.getPosition();
/* 87 */     Vector3f playerHeadRotation = headRotationComponent.getRotation();
/*    */     
/* 89 */     ((TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType())).append(world, playerPosition.clone(), playerHeadRotation.clone(), "Warp '" + warp + "'");
/*    */     
/* 91 */     store.addComponent(ref, Teleport.getComponentType(), (Component)teleport);
/* 92 */     context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_WARPED_TO.param("name", warp));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\warp\WarpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */