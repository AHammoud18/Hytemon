/*    */ package com.hypixel.hytale.builtin.instances.removal;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*    */ public interface RemovalCondition
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final CodecMapCodec<RemovalCondition> CODEC = new CodecMapCodec("Type");
/*    */   
/* 23 */   public static final RemovalCondition[] EMPTY = new RemovalCondition[0];
/*    */   
/*    */   boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> paramStore);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\RemovalCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */