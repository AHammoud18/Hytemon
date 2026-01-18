/*    */ package com.hypixel.hytale.builtin.parkour.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.parkour.ParkourPlugin;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CheckpointRemoveCommand
/*    */   extends AbstractWorldCommand {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_CHECKPOINT_REMOVE_FAILED = Message.translation("server.commands.checkpoint.remove.failed");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_CHECKPOINT_REMOVE_SUCCESS = Message.translation("server.commands.checkpoint.remove.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 30 */   private final RequiredArg<Integer> indexArg = withRequiredArg("index", "server.commands.checkpoint.remove.index.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CheckpointRemoveCommand() {
/* 36 */     super("remove", "server.commands.checkpoint.remove.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 41 */     int index = ((Integer)this.indexArg.get(context)).intValue();
/* 42 */     Int2ObjectMap<UUID> checkpointUUIDMap = ParkourPlugin.get().getCheckpointUUIDMap();
/*    */     
/* 44 */     UUID uuid = (UUID)checkpointUUIDMap.get(index);
/* 45 */     if (uuid == null) {
/* 46 */       context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_REMOVE_FAILED);
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     Ref<EntityStore> ref = ((EntityStore)store.getExternalData()).getRefFromUUID(uuid);
/* 51 */     if (ref == null || !ref.isValid()) {
/* 52 */       context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_REMOVE_FAILED);
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     store.removeEntity(ref, RemoveReason.REMOVE);
/* 57 */     checkpointUUIDMap.remove(index);
/* 58 */     ParkourPlugin.get().updateLastIndex();
/*    */     
/* 60 */     context.sendMessage(MESSAGE_COMMANDS_CHECKPOINT_REMOVE_SUCCESS);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\commands\CheckpointRemoveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */