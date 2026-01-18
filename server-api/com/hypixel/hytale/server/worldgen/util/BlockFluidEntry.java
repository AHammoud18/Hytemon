/*    */ package com.hypixel.hytale.server.worldgen.util;
/*    */ 
/*    */ @Deprecated
/*    */ public final class BlockFluidEntry
/*    */   extends Record
/*    */ {
/*    */   private final int blockId;
/*    */   private final int rotation;
/*    */   private final int fluidId;
/*    */   
/*    */   public int fluidId() {
/* 12 */     return this.fluidId; } public int rotation() { return this.rotation; } public int blockId() { return this.blockId; }
/*    */   public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry;
/* 13 */     //   0	8	1	o	Ljava/lang/Object; } public BlockFluidEntry(int blockId, int rotation, int fluidId) { this.blockId = blockId; this.rotation = rotation; this.fluidId = fluidId; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lcom/hypixel/hytale/server/worldgen/util/BlockFluidEntry; } public static final BlockFluidEntry[] EMPTY_ARRAY = new BlockFluidEntry[0];
/* 15 */   public static final BlockFluidEntry EMPTY = new BlockFluidEntry(0, 0, 0);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldge\\util\BlockFluidEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */