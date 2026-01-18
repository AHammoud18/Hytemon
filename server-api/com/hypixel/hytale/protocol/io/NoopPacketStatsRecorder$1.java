/*    */ package com.hypixel.hytale.protocol.io;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements PacketStatsRecorder.PacketStatsEntry
/*    */ {
/*    */   public int getPacketId() {
/* 12 */     return 0; } @Nullable
/* 13 */   public String getName() { return null; } public boolean hasData() {
/* 14 */     return false;
/*    */   }
/* 16 */   public int getSentCount() { return 0; }
/* 17 */   public long getSentUncompressedTotal() { return 0L; }
/* 18 */   public long getSentCompressedTotal() { return 0L; }
/* 19 */   public long getSentUncompressedMin() { return 0L; }
/* 20 */   public long getSentUncompressedMax() { return 0L; }
/* 21 */   public long getSentCompressedMin() { return 0L; }
/* 22 */   public long getSentCompressedMax() { return 0L; }
/* 23 */   public double getSentUncompressedAvg() { return 0.0D; }
/* 24 */   public double getSentCompressedAvg() { return 0.0D; } @Nonnull
/* 25 */   public PacketStatsRecorder.RecentStats getSentRecently() { return PacketStatsRecorder.RecentStats.EMPTY; }
/*    */   
/* 27 */   public int getReceivedCount() { return 0; }
/* 28 */   public long getReceivedUncompressedTotal() { return 0L; }
/* 29 */   public long getReceivedCompressedTotal() { return 0L; }
/* 30 */   public long getReceivedUncompressedMin() { return 0L; }
/* 31 */   public long getReceivedUncompressedMax() { return 0L; }
/* 32 */   public long getReceivedCompressedMin() { return 0L; }
/* 33 */   public long getReceivedCompressedMax() { return 0L; }
/* 34 */   public double getReceivedUncompressedAvg() { return 0.0D; }
/* 35 */   public double getReceivedCompressedAvg() { return 0.0D; } @Nonnull
/* 36 */   public PacketStatsRecorder.RecentStats getReceivedRecently() { return PacketStatsRecorder.RecentStats.EMPTY; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\io\NoopPacketStatsRecorder$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */