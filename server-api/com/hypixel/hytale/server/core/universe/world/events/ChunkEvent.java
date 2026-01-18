/*    */ package com.hypixel.hytale.server.core.universe.world.events;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
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
/*    */ public abstract class ChunkEvent
/*    */   implements IEvent<String>
/*    */ {
/*    */   @Nonnull
/*    */   private final WorldChunk chunk;
/*    */   
/*    */   public ChunkEvent(@Nonnull WorldChunk chunk) {
/* 25 */     this.chunk = chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldChunk getChunk() {
/* 32 */     return this.chunk;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 38 */     return "ChunkEvent{chunk=" + String.valueOf(this.chunk) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\ChunkEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */