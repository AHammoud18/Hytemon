/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StopNetworkChunkSendingCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 26 */   private final RequiredArg<Boolean> sendNetworkChunksArg = withRequiredArg("sendNetworkChunks", "Whether chunks should be sent over the network to yourself", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StopNetworkChunkSendingCommand() {
/* 33 */     super("networkChunkSending", "Stop sending chunks over the network");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 38 */     ChunkTracker chunkTrackerComponent = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/*    */     
/* 40 */     if (chunkTrackerComponent == null) {
/* 41 */       playerRef.sendMessage(Message.translation("server.commands.networkChunkSending.noComponent"));
/*    */       
/*    */       return;
/*    */     } 
/* 45 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 46 */     assert playerRefComponent != null;
/*    */     
/* 48 */     chunkTrackerComponent.setReadyForChunks(((Boolean)this.sendNetworkChunksArg.get(context)).booleanValue());
/* 49 */     playerRef.sendMessage(Message.translation("server.commands.networkChunkSending.set")
/* 50 */         .param("username", playerRefComponent.getUsername())
/* 51 */         .param("enabled", ((Boolean)this.sendNetworkChunksArg.get(context)).booleanValue()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\StopNetworkChunkSendingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */