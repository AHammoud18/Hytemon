/*     */ package com.hypixel.hytale.builtin.blockspawner.command;
/*     */ import com.hypixel.hytale.builtin.blockspawner.BlockSpawnerTable;
/*     */ import com.hypixel.hytale.builtin.blockspawner.state.BlockSpawner;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.Argument;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.exceptions.GeneralCommandException;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class BlockSpawnerSetCommand extends AbstractWorldCommand {
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE = Message.translation("server.general.blockTargetNotInRange");
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION = Message.translation("server.commands.errors.providePosition");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  39 */   private static final SingleArgumentType<BlockSpawnerTable> BLOCK_SPAWNER_ASSET_TYPE = (SingleArgumentType<BlockSpawnerTable>)new AssetArgumentType("server.commands.parsing.argtype.asset.blockspawnertable.name", BlockSpawnerTable.class, "server.commands.parsing.argtype.asset.blockspawnertable.usage");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  49 */   private final RequiredArg<BlockSpawnerTable> blockSpawnerIdArg = withRequiredArg("blockSpawnerId", "server.commands.blockspawner.set.blockSpawnerId.desc", (ArgumentType)BLOCK_SPAWNER_ASSET_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  55 */   private final OptionalArg<RelativeIntPosition> positionArg = withOptionalArg("position", "server.commands.blockspawner.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  61 */   private final FlagArg ignoreChecksFlag = withFlagArg("ignoreChecks", "server.commands.blockspawner.arg.ignoreChecks");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockSpawnerSetCommand() {
/*  67 */     super("set", "server.commands.blockspawner.set.desc");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/*     */     Vector3i position;
/*     */     String spawnerId;
/*  74 */     if (this.positionArg.provided(context)) {
/*  75 */       RelativeIntPosition relativePosition = (RelativeIntPosition)this.positionArg.get(context);
/*  76 */       position = relativePosition.getBlockPosition(context, (ComponentAccessor)store);
/*     */     }
/*  78 */     else if (context.isPlayer()) {
/*  79 */       Ref<EntityStore> ref = context.senderAsPlayerRef();
/*  80 */       Vector3i targetBlock = TargetUtil.getTargetBlock(ref, 10.0D, (ComponentAccessor)store);
/*  81 */       if (targetBlock == null) {
/*  82 */         throw new GeneralCommandException(MESSAGE_GENERAL_BLOCK_TARGET_NOT_IN_RANGE);
/*     */       }
/*  84 */       position = targetBlock;
/*     */     } else {
/*  86 */       throw new GeneralCommandException(MESSAGE_COMMANDS_ERRORS_PROVIDE_POSITION);
/*     */     } 
/*     */ 
/*     */     
/*  90 */     WorldChunk chunk = world.getChunk(ChunkUtil.indexChunkFromBlock(position.x, position.z));
/*  91 */     Ref<ChunkStore> blockRef = chunk.getBlockComponentEntity(position.x, position.y, position.z);
/*     */     
/*  93 */     if (blockRef == null) {
/*  94 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/*  95 */           .param("block", position.toString()));
/*     */       
/*     */       return;
/*     */     } 
/*  99 */     BlockSpawner spawnerState = (BlockSpawner)world.getChunkStore().getStore().getComponent(blockRef, BlockSpawner.getComponentType());
/*     */     
/* 101 */     if (spawnerState == null) {
/* 102 */       context.sendMessage(Message.translation("server.general.containerNotFound")
/* 103 */           .param("block", position.toString()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 108 */     if (((Boolean)this.ignoreChecksFlag.get(context)).booleanValue()) {
/*     */       
/* 110 */       String[] input = context.getInput((Argument)this.blockSpawnerIdArg);
/* 111 */       spawnerId = (input != null && input.length > 0) ? input[0] : null;
/* 112 */       if (spawnerId == null) {
/* 113 */         context.sendMessage(Message.translation("errors.validation_failure")
/* 114 */             .param("message", "blockSpawnerId is required when --ignoreChecks is set"));
/*     */         return;
/*     */       } 
/*     */     } else {
/* 118 */       spawnerId = ((BlockSpawnerTable)this.blockSpawnerIdArg.get(context)).getId();
/*     */     } 
/*     */     
/* 121 */     spawnerState.setBlockSpawnerId(spawnerId);
/*     */     
/* 123 */     chunk.markNeedsSaving();
/* 124 */     context.sendMessage(Message.translation("server.commands.blockspawner.blockSpawnerSet")
/* 125 */         .param("id", spawnerId));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\command\BlockSpawnerSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */