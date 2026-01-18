/*    */ package com.hypixel.hytale.server.core.command.commands.world.chunk;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.iterator.SpiralIterator;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.player.ChunkTracker;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class ChunkResendCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private static final Message MESSAGE_COMMANDS_CHUNK_RESEND_UNLOADED_ALL = Message.translation("server.commands.chunk.resend.unloadedAll");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final FlagArg clearCacheArg = withFlagArg("clearcache", "server.commands.chunk.resend.clearcache.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkResendCommand() {
/* 40 */     super("resend", "server.commands.chunk.resend.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 45 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 46 */     assert playerComponent != null;
/*    */     
/* 48 */     ChunkTracker chunkTrackerComponent = (ChunkTracker)store.getComponent(ref, ChunkTracker.getComponentType());
/* 49 */     assert chunkTrackerComponent != null;
/*    */     
/* 51 */     TransformComponent transformComponent = (TransformComponent)store.getComponent(ref, TransformComponent.getComponentType());
/* 52 */     assert transformComponent != null;
/*    */     
/* 54 */     Vector3d position = transformComponent.getPosition();
/* 55 */     int chunkX = MathUtil.floor(position.getX()) >> 5;
/* 56 */     int chunkZ = MathUtil.floor(position.getZ()) >> 5;
/*    */     
/* 58 */     if (this.clearCacheArg.provided(context)) {
/* 59 */       ChunkStore chunkStore = world.getChunkStore();
/* 60 */       Store<ChunkStore> chunkStoreStore = chunkStore.getStore();
/*    */       
/* 62 */       SpiralIterator iterator = new SpiralIterator(chunkX, chunkZ, playerComponent.getViewRadius());
/* 63 */       while (iterator.hasNext()) {
/*    */         
/* 65 */         long chunkIndex = iterator.next();
/* 66 */         Ref<ChunkStore> chunkRef = chunkStore.getChunkReference(chunkIndex);
/* 67 */         if (chunkRef != null && chunkRef.isValid()) {
/*    */           
/* 69 */           BlockChunk blockChunkComponent = (BlockChunk)chunkStoreStore.getComponent(chunkRef, BlockChunk.getComponentType());
/* 70 */           if (blockChunkComponent != null) {
/* 71 */             for (int y = 0; y < 10; y++) {
/* 72 */               blockChunkComponent.invalidateChunkSection(y);
/*    */             }
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     chunkTrackerComponent.unloadAll(playerRef);
/* 80 */     context.sendMessage(MESSAGE_COMMANDS_CHUNK_RESEND_UNLOADED_ALL);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\chunk\ChunkResendCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */