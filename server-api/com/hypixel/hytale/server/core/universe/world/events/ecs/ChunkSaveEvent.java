/*    */ package com.hypixel.hytale.server.core.universe.world.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSaveEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final WorldChunk chunk;
/*    */   
/*    */   public ChunkSaveEvent(@Nonnull WorldChunk chunk) {
/* 27 */     this.chunk = chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldChunk getChunk() {
/* 34 */     return this.chunk;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\ecs\ChunkSaveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */