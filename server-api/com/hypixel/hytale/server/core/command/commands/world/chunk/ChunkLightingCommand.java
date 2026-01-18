/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector2i;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkLightingCommand extends AbstractWorldCommand {
/*    */   @Nonnull
/* 26 */   private static final Message MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED = Message.translation("server.commands.errors.chunkNotLoaded");
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHUNKINFO_LOAD_USAGE = Message.translation("server.commands.chunkinfo.load.usage");
/*    */   @Nonnull
/* 30 */   private static final Message MESSAGE_COMMANDS_CHUNKINFO_SERIALIZED = Message.translation("server.commands.chunkinfo.serialized");
/*    */   @Nonnull
/* 32 */   private static final Message MESSAGE_COMMANDS_CHUNKINFO_SERIALIZED_FAILED = Message.translation("server.commands.chunkinfo.serialized.failed");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 38 */   private final RequiredArg<RelativeIntPosition> positionArg = withRequiredArg("x y z", "server.commands.chunk.lighting.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkLightingCommand() {
/* 44 */     super("lighting", "server.commands.chunklighting.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 49 */     Vector3i position = ((RelativeIntPosition)this.positionArg.get(context)).getBlockPosition(context, (ComponentAccessor)store);
/*    */     
/* 51 */     ChunkStore chunkStore = world.getChunkStore();
/* 52 */     Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/* 53 */     Vector2i chunkPos = new Vector2i(ChunkUtil.chunkCoordinate(position.getX()), ChunkUtil.chunkCoordinate(position.getZ()));
/* 54 */     long chunkIndex = ChunkUtil.indexChunk(chunkPos.x, chunkPos.y);
/* 55 */     Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/*    */ 
/*    */     
/* 58 */     if (chunkRef == null || !chunkRef.isValid()) {
/* 59 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_CHUNK_NOT_LOADED
/* 60 */           .param("chunkX", chunkPos.x)
/* 61 */           .param("chunkZ", chunkPos.y)
/* 62 */           .param("world", world.getName()));
/*    */ 
/*    */       
/* 65 */       context.sendMessage(MESSAGE_COMMANDS_CHUNKINFO_LOAD_USAGE
/* 66 */           .param("chunkX", chunkPos.x)
/* 67 */           .param("chunkZ", chunkPos.y));
/*    */       
/*    */       return;
/*    */     } 
/* 71 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 72 */     assert blockChunkComponent != null;
/*    */     
/*    */     try {
/* 75 */       BlockSection section = blockChunkComponent.getSectionAtBlockY(position.y);
/* 76 */       String s = section.getLocalLight().octreeToString();
/* 77 */       HytaleLogger.getLogger().at(Level.INFO).log("Chunk light output for (%d, %d, %d): %s", 
/* 78 */           Integer.valueOf(position.x), Integer.valueOf(position.y), Integer.valueOf(position.z), s);
/* 79 */       context.sendMessage(MESSAGE_COMMANDS_CHUNKINFO_SERIALIZED);
/* 80 */     } catch (Throwable t) {
/* 81 */       HytaleLogger.getLogger().at(Level.SEVERE).log("Failed to print chunk:", t);
/* 82 */       context.sendMessage(MESSAGE_COMMANDS_CHUNKINFO_SERIALIZED_FAILED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkLightingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */