/*    */ package com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator;
/*    */ 
/*    */ import java.util.function.LongPredicate;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Arguments
/*    */   extends Record
/*    */ {
/*    */   private final int seed;
/*    */   private final long index;
/*    */   private final int x;
/*    */   private final int z;
/*    */   @Nullable
/*    */   private final LongPredicate stillNeeded;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/chunkgenerator/ChunkRequest$Arguments;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Arguments(int seed, long index, int x, int z, @Nullable LongPredicate stillNeeded) {
/* 63 */     this.seed = seed; this.index = index; this.x = x; this.z = z; this.stillNeeded = stillNeeded; } public int seed() { return this.seed; } public long index() { return this.index; } public int x() { return this.x; } public int z() { return this.z; } @Nullable public LongPredicate stillNeeded() { return this.stillNeeded; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\chunkgenerator\ChunkRequest$Arguments.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */