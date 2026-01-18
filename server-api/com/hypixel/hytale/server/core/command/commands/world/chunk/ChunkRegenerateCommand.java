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
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkRegenerateCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_CHUNK_REGENERATE_SUCCESS = Message.translation("server.commands.chunk.regenerate.success");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 29 */   private final RequiredArg<RelativeChunkPosition> chunkPosArg = withRequiredArg("x z", "server.commands.chunk.regenerate.position.desc", ArgTypes.RELATIVE_CHUNK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkRegenerateCommand() {
/* 35 */     super("regenerate", "server.commands.chunk.regenerate.desc", true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 40 */     Vector2i chunkPosition = ((RelativeChunkPosition)this.chunkPosArg.get(context)).getChunkPosition(context, (ComponentAccessor)store);
/* 41 */     long chunkIndex = ChunkUtil.indexChunk(chunkPosition.x, chunkPosition.y);
/*    */     
/* 43 */     ChunkStore chunkStore = world.getChunkStore();
/* 44 */     chunkStore.getChunkReferenceAsync(chunkIndex, 9)
/* 45 */       .thenAccept(chunkRef -> world.execute(()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkRegenerateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */