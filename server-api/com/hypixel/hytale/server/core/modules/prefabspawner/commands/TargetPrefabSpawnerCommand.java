/*    */ package com.hypixel.hytale.server.core.modules.prefabspawner.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*    */ import com.hypixel.hytale.server.core.modules.prefabspawner.PrefabSpawnerState;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class TargetPrefabSpawnerCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE = Message.translation("server.general.blockTargetNotInRange");
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION = Message.translation("server.commands.errors.providePosition");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 35 */   protected final OptionalArg<RelativeIntPosition> positionArg = withOptionalArg("position", "server.commands.prefabspawner.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TargetPrefabSpawnerCommand(@Nonnull String name, @Nonnull String description) {
/* 44 */     super(name, description);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     Vector3i target;
/* 51 */     if (this.positionArg.provided(context)) {
/* 52 */       RelativeIntPosition relativePosition = (RelativeIntPosition)this.positionArg.get(context);
/* 53 */       target = relativePosition.getBlockPosition(context, (ComponentAccessor)store);
/*    */     }
/* 55 */     else if (context.isPlayer()) {
/* 56 */       Ref<EntityStore> playerRef = context.senderAsPlayerRef();
/* 57 */       Vector3i targetBlock = TargetUtil.getTargetBlock(playerRef, 10.0D, (ComponentAccessor)store);
/* 58 */       if (targetBlock == null) {
/* 59 */         throw new GeneralCommandException(MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE);
/*    */       }
/* 61 */       target = targetBlock;
/*    */     } else {
/* 63 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION);
/*    */     } 
/*    */ 
/*    */     
/* 67 */     WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(target.x, target.z));
/* 68 */     BlockState state = chunk.getState(target.x, target.y, target.z);
/*    */     
/* 70 */     if (!(state instanceof PrefabSpawnerState)) {
/* 71 */       context.sendMessage(Message.translation("server.commands.prefabspawner.spawnerNotFoundAtTarget")
/* 72 */           .param("pos", target.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 76 */     execute(context, chunk, (PrefabSpawnerState)state);
/*    */   }
/*    */   
/*    */   protected abstract void execute(@Nonnull CommandContext paramCommandContext, @Nonnull WorldChunk paramWorldChunk, @Nonnull PrefabSpawnerState paramPrefabSpawnerState);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\prefabspawner\commands\TargetPrefabSpawnerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */