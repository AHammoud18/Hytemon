/*    */ package com.hypixel.hytale.builtin.teleport.commands.warp;
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.Warp;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WarpRemoveCommand extends CommandBase {
/* 24 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED = Message.translation("server.commands.teleport.warp.notLoaded");
/* 25 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_UNKNOWN_WARP = Message.translation("server.commands.teleport.warp.unknownWarp");
/* 26 */   private static final Message MESSAGE_COMMANDS_TELEPORT_WARP_REMOVED_WARP = Message.translation("server.commands.teleport.warp.removedWarp");
/*    */   
/*    */   @Nonnull
/* 29 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.warp.remove.name.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WarpRemoveCommand() {
/* 35 */     super("remove", "server.commands.warp.remove.desc");
/* 36 */     requirePermission(HytalePermissions.fromCommand("warp.remove"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 46 */     if (!TeleportPlugin.get().isWarpsLoaded()) {
/* 47 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_NOT_LOADED);
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     Map<String, Warp> warps = TeleportPlugin.get().getWarps();
/* 52 */     String warpName = ((String)this.nameArg.get(context)).toLowerCase();
/* 53 */     Warp old = warps.remove(warpName);
/*    */     
/* 55 */     if (old == null) {
/* 56 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_UNKNOWN_WARP.param("name", warpName));
/*    */     } else {
/* 58 */       TeleportPlugin.get().saveWarps();
/* 59 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_WARP_REMOVED_WARP.param("name", warpName));
/*    */ 
/*    */       
/* 62 */       World targetWorld = Universe.get().getWorld(old.getWorld());
/* 63 */       if (targetWorld != null) {
/* 64 */         ComponentType<EntityStore, TeleportPlugin.WarpComponent> warpComponentType = TeleportPlugin.WarpComponent.getComponentType();
/* 65 */         Store<EntityStore> store = targetWorld.getEntityStore().getStore();
/* 66 */         targetWorld.execute(() -> store.forEachEntityParallel((Query)warpComponentType, ()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\warp\WarpRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */