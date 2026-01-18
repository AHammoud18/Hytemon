/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
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
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkUnloadCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_CHUNK_UNLOAD_ALREADY_UNLOADED = Message.translation("server.commands.chunk.unload.alreadyUnloaded");
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_CHUNK_UNLOAD_SUCCESS = Message.translation("server.commands.chunk.unload.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 32 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.unload.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkUnloadCommand() {
/* 38 */     super("unload", "server.commands.chunk.unload.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 43 */     RelativeChunkPosition chunkPosition = (RelativeChunkPosition)this.chunkPosArg.get(context);
/* 44 */     Vector2i position = chunkPosition.getChunkPosition(context, (ComponentAccessor)store);
/*    */     
/* 46 */     ChunkStore chunkComponentStore = world.getChunkStore();
/* 47 */     long indexChunk = ChunkUtil.indexChunk(position.x, position.y);
/* 48 */     Ref<ChunkStore> chunkRef = chunkComponentStore.getChunkReference(indexChunk);
/* 49 */     if (chunkRef == null) {
/* 50 */       context.sendMessage(MESSAGE_COMMANDS_CHUNK_UNLOAD_ALREADY_UNLOADED
/* 51 */           .param("chunkX", position.x)
/* 52 */           .param("chunkZ", position.y)
/* 53 */           .param("worldName", world.getName()));
/*    */       
/*    */       return;
/*    */     } 
/* 57 */     chunkComponentStore.remove(chunkRef, RemoveReason.UNLOAD);
/* 58 */     world.getNotificationHandler().updateChunk(indexChunk);
/* 59 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_UNLOAD_SUCCESS
/* 60 */         .param("chunkX", position.x)
/* 61 */         .param("chunkZ", position.y)
/* 62 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkUnloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */