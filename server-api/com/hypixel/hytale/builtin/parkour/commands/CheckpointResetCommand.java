/*    */ package com.hypixel.hytale.builtin.parkour.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.parkour.ParkourPlugin;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckpointResetCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 21 */   private static final Message MESSAGE_COMMANDS_CHECKPOINT_RESET_SUCCESS = Message.translation("server.commands.checkpoint.reset.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CheckpointResetCommand() {
/* 27 */     super("reset", "server.commands.checkpoint.reset.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 32 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 33 */     assert uuidComponent != null;
/*    */     
/* 35 */     ParkourPlugin.get().resetPlayer(uuidComponent.getUuid());
/* 36 */     context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_RESET_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\commands\CheckpointResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */