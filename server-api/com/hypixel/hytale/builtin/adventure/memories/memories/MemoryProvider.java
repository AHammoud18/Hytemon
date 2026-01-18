/*    */ package com.hypixel.hytale.builtin.adventure.memories.memories;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.MemoriesPlugin;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class MemoryProvider<T extends Memory>
/*    */ {
/*    */   private final String id;
/*    */   private final BuilderCodec<T> codec;
/*    */   private final double defaultRadius;
/*    */   
/*    */   public MemoryProvider(String id, BuilderCodec<T> codec, double defaultRadius) {
/* 15 */     this.id = id;
/* 16 */     this.codec = codec;
/* 17 */     this.defaultRadius = defaultRadius;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 21 */     return this.id;
/*    */   }
/*    */   
/*    */   public BuilderCodec<T> getCodec() {
/* 25 */     return this.codec;
/*    */   }
/*    */   
/*    */   public double getCollectionRadius() {
/* 29 */     return MemoriesPlugin.get().getConfig().getCollectionRadius().getOrDefault(this.id, this.defaultRadius);
/*    */   }
/*    */   
/*    */   public abstract Map<String, Set<Memory>> getAllMemories();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\memories\MemoryProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */