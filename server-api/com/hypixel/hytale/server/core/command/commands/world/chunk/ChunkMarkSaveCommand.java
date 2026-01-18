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
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkMarkSaveCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_CHUNK_MARKSAVE_MARKING_ALREADY_LOADED = Message.translation("server.commands.chunk.marksave.markingAlreadyLoaded");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_CHUNK_LOAD_LOADING = Message.translation("server.commands.chunk.load.loading");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHUNK_MARKSAVE_LOADED = Message.translation("server.commands.chunk.marksave.loaded");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.marksave.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkMarkSaveCommand() {
/* 40 */     super("marksave", "server.commands.chunk.marksave.desc");
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
/* 54 */     if (chunkRef != null && chunkRef.isValid()) {
/* 55 */       WorldChunk worldChunkComponent = (WorldChunk)chunkStoreStore.getComponent(chunkRef, WorldChunk.getComponentType());
/* 56 */       assert worldChunkComponent != null;
/* 57 */       worldChunkComponent.markNeedsSaving();
/* 58 */       context.sendMessage(MESSAGE_COMMANDS_CHUNK_MARKSAVE_MARKING_ALREADY_LOADED
/* 59 */           .param("x", position.x)
/* 60 */           .param("z", position.y)
/* 61 */           .param("worldName", world.getName()));
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_LOAD_LOADING
/* 66 */         .param("chunkX", position.x)
/* 67 */         .param("chunkZ", position.y)
/* 68 */         .param("worldName", world.getName()));
/*    */     
/* 70 */     world.getChunkAsync(position.x, position.y).thenAccept(worldChunk -> world.execute(()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkMarkSaveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */