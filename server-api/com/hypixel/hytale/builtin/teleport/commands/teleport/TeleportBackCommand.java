/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportBackCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private final OptionalArg<Integer> countArg = withOptionalArg("count", "server.commands.teleport.back.count.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportBackCommand() {
/* 33 */     super("back", "server.commands.teleport.recent.desc");
/* 34 */     requirePermission(HytalePermissions.fromCommand("teleport.back"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 39 */     int counter = this.countArg.provided(context) ? ((Integer)this.countArg.get(context)).intValue() : 1;
/* 40 */     TeleportHistory history = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/* 41 */     history.back(ref, counter);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportBackCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */