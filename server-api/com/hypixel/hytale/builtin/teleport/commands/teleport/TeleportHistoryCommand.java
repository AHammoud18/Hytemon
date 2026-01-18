/*    */ package com.hypixel.hytale.builtin.teleport.commands.teleport;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.teleport.TeleportPlugin;
/*    */ import com.hypixel.hytale.builtin.teleport.components.TeleportHistory;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportHistoryCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_TELEPORT_HISTORY_EMPTY = Message.translation("server.commands.teleport.history.empty");
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_TELEPORT_HISTORY_INFO = Message.translation("server.commands.teleport.history.info");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TeleportHistoryCommand() {
/* 31 */     super("history", "server.commands.teleport.dump.desc");
/* 32 */     requirePermission(HytalePermissions.fromCommand("teleport.history"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 38 */     assert playerRefComponent != null;
/*    */     
/* 40 */     TeleportHistory history = (TeleportHistory)store.ensureAndGetComponent(ref, TeleportHistory.getComponentType());
/*    */     
/* 42 */     TeleportPlugin.get().getLogger().at(Level.INFO).log("Got history for player %s: %s", playerRefComponent
/* 43 */         .getUsername(), history);
/*    */ 
/*    */     
/* 46 */     int backSize = history.getBackSize();
/* 47 */     int forwardSize = history.getForwardSize();
/*    */     
/* 49 */     if (backSize == 0 && forwardSize == 0) {
/* 50 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_HISTORY_EMPTY);
/*    */     } else {
/* 52 */       context.sendMessage(MESSAGE_COMMANDS_TELEPORT_HISTORY_INFO
/* 53 */           .param("backCount", backSize)
/* 54 */           .param("forwardCount", forwardSize));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\teleport\commands\teleport\TeleportHistoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */