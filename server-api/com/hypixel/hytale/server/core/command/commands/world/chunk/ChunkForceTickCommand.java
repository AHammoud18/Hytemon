/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeChunkPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkForceTickCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED = Message.translation("server.commands.errors.chunkNotLoaded");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_FORCECHUNKTICK_CHUNK_LOAD_USAGE = Message.translation("server.commands.forcechunktick.chunkLoadUsage");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_FORCECHUNKTICK_BLOCKS_IN_CHUNK_TICK = Message.translation("server.commands.forcechunktick.blocksInChunkTick");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.forcetick.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkForceTickCommand() {
/* 40 */     super("forcetick", "server.commands.chunk.forcetick.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 45 */     RelativeChunkPosition chunkPosition = (RelativeChunkPosition)this.chunkPosArg.get(context);
/* 46 */     Vector2i position = chunkPosition.getChunkPosition(context, (ComponentAccessor)store);
/*    */     
/* 48 */     ChunkStore chunkStore = world.getChunkStore();
/* 49 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 50 */     long chunkIndex = ChunkUtil.indexChunk(position.x, position.y);
/* 51 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*    */ 
/*    */     
/* 54 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 55 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED
/* 56 */           .param("chunkX", position.x)
/* 57 */           .param("chunkZ", position.y)
/* 58 */           .param("world", world.getName()));
/*    */ 
/*    */       
/* 61 */       context.sendMessage(MESSAGE_COMMANDS_FORCECHUNKTICK_CHUNK_LOAD_USAGE
/* 62 */           .param("chunkX", position.x)
/* 63 */           .param("chunkZ", position.y));
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 68 */     assert blockChunkComponent != null;
/*    */     
/* 70 */     for (int x = 0; x < 32; x++) {
/* 71 */       for (int y = 0; y < 320; y++) {
/* 72 */         for (int z = 0; z < 32; z++) {
/* 73 */           blockChunkComponent.setTicking(x, y, z, true);
/*    */         }
/*    */       } 
/*    */     } 
/* 77 */     context.sendMessage(MESSAGE_COMMANDS_FORCECHUNKTICK_BLOCKS_IN_CHUNK_TICK
/* 78 */         .param("chunkX", position.x)
/* 79 */         .param("chunkZ", position.y));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkForceTickCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */