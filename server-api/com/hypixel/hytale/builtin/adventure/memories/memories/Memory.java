/*    */ package com.hypixel.hytale.builtin.adventure.memories.memories;
/*    */ 
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Memory
/*    */ {
/* 13 */   public static final CodecMapCodec<Memory> CODEC = new CodecMapCodec();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract String getId();
/*    */ 
/*    */   
/*    */   public abstract String getTitle();
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object o) {
/* 24 */     return (this == o || (o != null && getClass() == o.getClass()));
/*    */   } public abstract Message getTooltipText(); @Nullable
/*    */   public abstract String getIconPath();
/*    */   public abstract Message getUndiscoveredTooltipText();
/*    */   public int hashCode() {
/* 29 */     return getClass().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 35 */     return "Memory{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\memories\Memory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */