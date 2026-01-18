/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
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
/*    */ public class ChunkLoadCommand
/*    */   extends AbstractWorldCommand {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_CHUNK_LOAD_ALREADY_LOADED = Message.translation("server.commands.chunk.load.alreadyLoaded");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_CHUNK_LOAD_LOADING = Message.translation("server.commands.chunk.load.loading");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHUNK_LOAD_LOADED = Message.translation("server.commands.chunk.load.loaded");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.load.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final FlagArg markDirtyArg = withFlagArg("markdirty", "server.commands.chunk.load.markdirty.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkLoadCommand() {
/* 46 */     super("load", "server.commands.chunk.load.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 51 */     RelativeChunkPosition chunkPosition = (RelativeChunkPosition)this.chunkPosArg.get(context);
/* 52 */     Vector2i position = chunkPosition.getChunkPosition(context, (ComponentAccessor)store);
/*    */     
/* 54 */     ChunkStore chunkStore = world.getChunkStore();
/* 55 */     long chunkIndex = ChunkUtil.indexChunk(position.x, position.y);
/* 56 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*    */ 
/*    */     
/* 59 */     if (chunkRef != null && chunkRef.isValid()) {
/* 60 */       context.sendMessage(MESSAGE_COMMANDS_CHUNK_LOAD_ALREADY_LOADED
/* 61 */           .param("chunkX", position.x)
/* 62 */           .param("chunkZ", position.y)
/* 63 */           .param("worldName", world.getName()));
/*    */       
/*    */       return;
/*    */     } 
/* 67 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_LOAD_LOADING
/* 68 */         .param("chunkX", position.x)
/* 69 */         .param("chunkZ", position.y)
/* 70 */         .param("worldName", world.getName()));
/*    */     
/* 72 */     world.getChunkAsync(position.x, position.y).thenAccept(worldChunk -> world.execute(()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkLoadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */