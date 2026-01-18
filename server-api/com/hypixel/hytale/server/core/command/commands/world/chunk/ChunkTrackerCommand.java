/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class ChunkTrackerCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_CHUNK_TRACKER_SUMMARY = Message.translation("server.commands.chunkTracker.summary");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkTrackerCommand() {
/* 28 */     super("tracker", "server.commands.chunk.tracker.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 33 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 34 */     assert playerComponent != null;
/*    */     
/* 36 */     ChunkTracker chunkTrackerComponent = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/* 37 */     assert chunkTrackerComponent != null;
/*    */     
/* 39 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 41 */     String loadedWorldChunks = Integer.toString(chunkStore.getLoadedChunksCount());
/* 42 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_TRACKER_SUMMARY
/* 43 */         .param("maxChunksPerSecond", chunkTrackerComponent.getMaxChunksPerSecond())
/* 44 */         .param("maxChunksPerTick", chunkTrackerComponent.getMaxChunksPerTick())
/* 45 */         .param("minChunkLoadedRadius", chunkTrackerComponent.getMinLoadedChunksRadius())
/* 46 */         .param("maxHotChunkLoadedRadius", chunkTrackerComponent.getMaxHotLoadedChunksRadius())
/* 47 */         .param("loadedPlayerChunks", chunkTrackerComponent.getLoadedChunksCount())
/* 48 */         .param("loadingPlayerChunks", chunkTrackerComponent.getLoadingChunksCount())
/* 49 */         .param("loadedWorldChunks", loadedWorldChunks));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkTrackerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */