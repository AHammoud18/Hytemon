/*    */ package com.hypixel.hytale.protocol.io;
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
/*    */ public final class RecentStats
/*    */   extends Record
/*    */ {
/*    */   private final int count;
/*    */   private final long uncompressedTotal;
/*    */   private final long compressedTotal;
/*    */   private final int uncompressedMin;
/*    */   private final int uncompressedMax;
/*    */   private final int compressedMin;
/*    */   private final int compressedMax;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #83	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #83	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #83	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/protocol/io/PacketStatsRecorder$RecentStats;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public RecentStats(int count, long uncompressedTotal, long compressedTotal, int uncompressedMin, int uncompressedMax, int compressedMin, int compressedMax) {
/* 83 */     this.count = count; this.uncompressedTotal = uncompressedTotal; this.compressedTotal = compressedTotal; this.uncompressedMin = uncompressedMin; this.uncompressedMax = uncompressedMax; this.compressedMin = compressedMin; this.compressedMax = compressedMax; } public int count() { return this.count; } public long uncompressedTotal() { return this.uncompressedTotal; } public long compressedTotal() { return this.compressedTotal; } public int uncompressedMin() { return this.uncompressedMin; } public int uncompressedMax() { return this.uncompressedMax; } public int compressedMin() { return this.compressedMin; } public int compressedMax() { return this.compressedMax; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   public static final RecentStats EMPTY = new RecentStats(0, 0L, 0L, 0, 0, 0, 0);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\PacketStatsRecorder$RecentStats.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */