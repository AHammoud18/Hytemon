/*    */ package com.hypixel.hytale.builtin.blockspawner.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.blockspawner.state.BlockSpawner;
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
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BlockSpawnerGetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE = Message.translation("server.general.blockTargetNotInRange");
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION = Message.translation("server.commands.errors.providePosition");
/*    */   @Nonnull
/* 31 */   private static final Message MESSAGE_COMMANDS_BLOCK_SPAWNER_NO_BLOCK_SPAWNER_SET = Message.translation("server.commands.blockspawner.noBlockSpawnerSet");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 37 */   private final OptionalArg<RelativeIntPosition> positionArg = withOptionalArg("position", "server.commands.blockspawner.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockSpawnerGetCommand() {
/* 43 */     super("get", "server.commands.blockspawner.get.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*    */     Vector3i position;
/* 50 */     if (this.positionArg.provided(context)) {
/* 51 */       RelativeIntPosition relativePosition = (RelativeIntPosition)this.positionArg.get(context);
/* 52 */       position = relativePosition.getBlockPosition(context, (ComponentAccessor)store);
/*    */     }
/* 54 */     else if (context.isPlayer()) {
/* 55 */       Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 56 */       Vector3i targetBlock = TargetUtil.getTargetBlock(ref, 10.0D, (ComponentAccessor)store);
/* 57 */       if (targetBlock == null) {
/* 58 */         throw new GeneralCommandException(MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE);
/*    */       }
/* 60 */       position = targetBlock;
/*    */     } else {
/* 62 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION);
/*    */     } 
/*    */ 
/*    */     
/* 66 */     ChunkStore chunkStore = world.getChunkStore();
/* 67 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(position.x, position.z);
/*    */     
/* 69 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 70 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 71 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/* 72 */           .param("block", position.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 76 */     WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getStore().getComponent(chunkRef, WorldChunk.getComponentType());
/* 77 */     assert worldChunkComponent != null;
/*    */     
/* 79 */     Ref<ChunkStore> blockRef = worldChunkComponent.getBlockComponentEntity(position.x, position.y, position.z);
/* 80 */     if (blockRef == null) {
/* 81 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/* 82 */           .param("block", position.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 86 */     BlockSpawner spawnerState = (BlockSpawner)chunkStore.getStore().getComponent(blockRef, BlockSpawner.getComponentType());
/* 87 */     if (spawnerState == null) {
/* 88 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/* 89 */           .param("block", position.toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 93 */     if (spawnerState.getBlockSpawnerId() == null) {
/* 94 */       context.sendMessage(MESSAGE_COMMANDS_BLOCK_SPAWNER_NO_BLOCK_SPAWNER_SET);
/*    */     } else {
/* 96 */       context.sendMessage(Message.translation("server.commands.blockspawner.currentBlockSpawner")
/* 97 */           .param("id", spawnerState.getBlockSpawnerId()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\command\BlockSpawnerGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */