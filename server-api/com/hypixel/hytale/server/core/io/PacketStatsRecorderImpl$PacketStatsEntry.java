/*     */ package com.hypixel.hytale.server.core.io;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.metrics.MetricsRegistry;
/*     */ import com.hypixel.hytale.metrics.metric.AverageCollector;
/*     */ import com.hypixel.hytale.protocol.PacketRegistry;
/*     */ import com.hypixel.hytale.protocol.io.PacketStatsRecorder;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketStatsEntry
/*     */   implements PacketStatsRecorder.PacketStatsEntry
/*     */ {
/*  68 */   public static final MetricsRegistry<PacketStatsEntry> METRICS_REGISTRY = (new MetricsRegistry())
/*  69 */     .register("PacketId", PacketStatsEntry::getPacketId, (Codec)Codec.INTEGER)
/*  70 */     .register("Name", PacketStatsEntry::getName, (Codec)Codec.STRING)
/*  71 */     .register("SentCount", PacketStatsEntry::getSentCount, (Codec)Codec.INTEGER)
/*  72 */     .register("SentUncompressedTotal", PacketStatsEntry::getSentUncompressedTotal, (Codec)Codec.LONG)
/*  73 */     .register("SentCompressedTotal", PacketStatsEntry::getSentCompressedTotal, (Codec)Codec.LONG)
/*  74 */     .register("SentUncompressedMin", PacketStatsEntry::getSentUncompressedMin, (Codec)Codec.LONG)
/*  75 */     .register("SentUncompressedMax", PacketStatsEntry::getSentUncompressedMax, (Codec)Codec.LONG)
/*  76 */     .register("SentCompressedMin", PacketStatsEntry::getSentCompressedMin, (Codec)Codec.LONG)
/*  77 */     .register("SentCompressedMax", PacketStatsEntry::getSentCompressedMax, (Codec)Codec.LONG)
/*  78 */     .register("ReceivedCount", PacketStatsEntry::getReceivedCount, (Codec)Codec.INTEGER)
/*  79 */     .register("ReceivedUncompressedTotal", PacketStatsEntry::getReceivedUncompressedTotal, (Codec)Codec.LONG)
/*  80 */     .register("ReceivedCompressedTotal", PacketStatsEntry::getReceivedCompressedTotal, (Codec)Codec.LONG)
/*  81 */     .register("ReceivedUncompressedMin", PacketStatsEntry::getReceivedUncompressedMin, (Codec)Codec.LONG)
/*  82 */     .register("ReceivedUncompressedMax", PacketStatsEntry::getReceivedUncompressedMax, (Codec)Codec.LONG)
/*  83 */     .register("ReceivedCompressedMin", PacketStatsEntry::getReceivedCompressedMin, (Codec)Codec.LONG)
/*  84 */     .register("ReceivedCompressedMax", PacketStatsEntry::getReceivedCompressedMax, (Codec)Codec.LONG);
/*     */ 
/*     */   
/*     */   private final int packetId;
/*     */   
/*  89 */   private final AtomicInteger sentCount = new AtomicInteger();
/*  90 */   private final AtomicLong sentUncompressedTotal = new AtomicLong();
/*  91 */   private final AtomicLong sentCompressedTotal = new AtomicLong();
/*  92 */   private final AtomicLong sentUncompressedMin = new AtomicLong(Long.MAX_VALUE);
/*  93 */   private final AtomicLong sentUncompressedMax = new AtomicLong();
/*  94 */   private final AtomicLong sentCompressedMin = new AtomicLong(Long.MAX_VALUE);
/*  95 */   private final AtomicLong sentCompressedMax = new AtomicLong();
/*  96 */   private final AverageCollector sentUncompressedAvg = new AverageCollector();
/*  97 */   private final AverageCollector sentCompressedAvg = new AverageCollector();
/*  98 */   private final Queue<SizeRecord> sentRecently = new ConcurrentLinkedQueue<>();
/*     */ 
/*     */   
/* 101 */   private final AtomicInteger receivedCount = new AtomicInteger();
/* 102 */   private final AtomicLong receivedUncompressedTotal = new AtomicLong();
/* 103 */   private final AtomicLong receivedCompressedTotal = new AtomicLong();
/* 104 */   private final AtomicLong receivedUncompressedMin = new AtomicLong(Long.MAX_VALUE);
/* 105 */   private final AtomicLong receivedUncompressedMax = new AtomicLong();
/* 106 */   private final AtomicLong receivedCompressedMin = new AtomicLong(Long.MAX_VALUE);
/* 107 */   private final AtomicLong receivedCompressedMax = new AtomicLong();
/* 108 */   private final AverageCollector receivedUncompressedAvg = new AverageCollector();
/* 109 */   private final AverageCollector receivedCompressedAvg = new AverageCollector();
/* 110 */   private final Queue<SizeRecord> receivedRecently = new ConcurrentLinkedQueue<>();
/*     */   
/*     */   public PacketStatsEntry(int packetId) {
/* 113 */     this.packetId = packetId;
/*     */   }
/*     */   
/*     */   void recordSend(int uncompressedSize, int compressedSize) {
/* 117 */     this.sentCount.incrementAndGet();
/* 118 */     this.sentUncompressedTotal.addAndGet(uncompressedSize);
/* 119 */     this.sentCompressedTotal.addAndGet(compressedSize);
/* 120 */     this.sentUncompressedMin.accumulateAndGet(uncompressedSize, Math::min);
/* 121 */     this.sentUncompressedMax.accumulateAndGet(uncompressedSize, Math::max);
/* 122 */     this.sentCompressedMin.accumulateAndGet(compressedSize, Math::min);
/* 123 */     this.sentCompressedMax.accumulateAndGet(compressedSize, Math::max);
/* 124 */     this.sentUncompressedAvg.add(uncompressedSize);
/* 125 */     this.sentCompressedAvg.add(compressedSize);
/*     */     
/* 127 */     long now = System.nanoTime();
/* 128 */     this.sentRecently.add(new SizeRecord(now, uncompressedSize, compressedSize));
/* 129 */     pruneOld(this.sentRecently, now);
/*     */   }
/*     */   
/*     */   void recordReceive(int uncompressedSize, int compressedSize) {
/* 133 */     this.receivedCount.incrementAndGet();
/* 134 */     this.receivedUncompressedTotal.addAndGet(uncompressedSize);
/* 135 */     this.receivedCompressedTotal.addAndGet(compressedSize);
/* 136 */     this.receivedUncompressedMin.accumulateAndGet(uncompressedSize, Math::min);
/* 137 */     this.receivedUncompressedMax.accumulateAndGet(uncompressedSize, Math::max);
/* 138 */     this.receivedCompressedMin.accumulateAndGet(compressedSize, Math::min);
/* 139 */     this.receivedCompressedMax.accumulateAndGet(compressedSize, Math::max);
/* 140 */     this.receivedUncompressedAvg.add(uncompressedSize);
/* 141 */     this.receivedCompressedAvg.add(compressedSize);
/*     */     
/* 143 */     long now = System.nanoTime();
/* 144 */     this.receivedRecently.add(new SizeRecord(now, uncompressedSize, compressedSize));
/* 145 */     pruneOld(this.receivedRecently, now);
/*     */   }
/*     */   
/*     */   private void pruneOld(Queue<SizeRecord> queue, long now) {
/* 149 */     long cutoff = now - TimeUnit.SECONDS.toNanos(30L);
/* 150 */     SizeRecord head = queue.peek();
/* 151 */     while (head != null && head.nanos < cutoff) {
/* 152 */       queue.poll();
/* 153 */       head = queue.peek();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasData() {
/* 158 */     return (this.sentCount.get() > 0 || this.receivedCount.get() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPacketId() {
/* 164 */     return this.packetId;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getName() {
/* 169 */     PacketRegistry.PacketInfo info = PacketRegistry.getById(this.packetId);
/* 170 */     return (info != null) ? info.name() : null;
/*     */   }
/*     */   
/*     */   public int getSentCount() {
/* 174 */     return this.sentCount.get();
/* 175 */   } public long getSentUncompressedTotal() { return this.sentUncompressedTotal.get(); }
/* 176 */   public long getSentCompressedTotal() { return this.sentCompressedTotal.get(); }
/* 177 */   public long getSentUncompressedMin() { return (this.sentCount.get() > 0) ? this.sentUncompressedMin.get() : 0L; }
/* 178 */   public long getSentUncompressedMax() { return this.sentUncompressedMax.get(); }
/* 179 */   public long getSentCompressedMin() { return (this.sentCount.get() > 0) ? this.sentCompressedMin.get() : 0L; }
/* 180 */   public long getSentCompressedMax() { return this.sentCompressedMax.get(); }
/* 181 */   public double getSentUncompressedAvg() { return this.sentUncompressedAvg.get(); } public double getSentCompressedAvg() {
/* 182 */     return this.sentCompressedAvg.get();
/*     */   }
/*     */   
/* 185 */   public int getReceivedCount() { return this.receivedCount.get(); }
/* 186 */   public long getReceivedUncompressedTotal() { return this.receivedUncompressedTotal.get(); }
/* 187 */   public long getReceivedCompressedTotal() { return this.receivedCompressedTotal.get(); }
/* 188 */   public long getReceivedUncompressedMin() { return (this.receivedCount.get() > 0) ? this.receivedUncompressedMin.get() : 0L; }
/* 189 */   public long getReceivedUncompressedMax() { return this.receivedUncompressedMax.get(); }
/* 190 */   public long getReceivedCompressedMin() { return (this.receivedCount.get() > 0) ? this.receivedCompressedMin.get() : 0L; }
/* 191 */   public long getReceivedCompressedMax() { return this.receivedCompressedMax.get(); }
/* 192 */   public double getReceivedUncompressedAvg() { return this.receivedUncompressedAvg.get(); } public double getReceivedCompressedAvg() {
/* 193 */     return this.receivedCompressedAvg.get();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PacketStatsRecorder.RecentStats getSentRecently() {
/* 199 */     return computeRecentStats(this.sentRecently);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public PacketStatsRecorder.RecentStats getReceivedRecently() {
/* 205 */     return computeRecentStats(this.receivedRecently);
/*     */   }
/*     */   
/*     */   private PacketStatsRecorder.RecentStats computeRecentStats(Queue<SizeRecord> queue) {
/* 209 */     int count = 0;
/* 210 */     long uncompressedTotal = 0L;
/* 211 */     long compressedTotal = 0L;
/* 212 */     int uncompressedMin = Integer.MAX_VALUE;
/* 213 */     int uncompressedMax = 0;
/* 214 */     int compressedMin = Integer.MAX_VALUE;
/* 215 */     int compressedMax = 0;
/*     */     
/* 217 */     for (SizeRecord record : queue) {
/* 218 */       count++;
/* 219 */       uncompressedTotal += record.uncompressedSize;
/* 220 */       compressedTotal += record.compressedSize;
/* 221 */       uncompressedMin = Math.min(uncompressedMin, record.uncompressedSize);
/* 222 */       uncompressedMax = Math.max(uncompressedMax, record.uncompressedSize);
/* 223 */       compressedMin = Math.min(compressedMin, record.compressedSize);
/* 224 */       compressedMax = Math.max(compressedMax, record.compressedSize);
/*     */     } 
/*     */     
/* 227 */     if (count == 0) {
/* 228 */       return PacketStatsRecorder.RecentStats.EMPTY;
/*     */     }
/* 230 */     return new PacketStatsRecorder.RecentStats(count, uncompressedTotal, compressedTotal, uncompressedMin, uncompressedMax, compressedMin, compressedMax);
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 235 */     this.sentCount.set(0);
/* 236 */     this.sentUncompressedTotal.set(0L);
/* 237 */     this.sentCompressedTotal.set(0L);
/* 238 */     this.sentUncompressedMin.set(Long.MAX_VALUE);
/* 239 */     this.sentUncompressedMax.set(0L);
/* 240 */     this.sentCompressedMin.set(Long.MAX_VALUE);
/* 241 */     this.sentCompressedMax.set(0L);
/* 242 */     this.sentUncompressedAvg.clear();
/* 243 */     this.sentCompressedAvg.clear();
/* 244 */     this.sentRecently.clear();
/*     */     
/* 246 */     this.receivedCount.set(0);
/* 247 */     this.receivedUncompressedTotal.set(0L);
/* 248 */     this.receivedCompressedTotal.set(0L);
/* 249 */     this.receivedUncompressedMin.set(Long.MAX_VALUE);
/* 250 */     this.receivedUncompressedMax.set(0L);
/* 251 */     this.receivedCompressedMin.set(Long.MAX_VALUE);
/* 252 */     this.receivedCompressedMax.set(0L);
/* 253 */     this.receivedUncompressedAvg.clear();
/* 254 */     this.receivedCompressedAvg.clear();
/* 255 */     this.receivedRecently.clear();
/*     */   }
/*     */   public static final class SizeRecord extends Record { private final long nanos; private final int uncompressedSize; private final int compressedSize;
/* 258 */     public SizeRecord(long nanos, int uncompressedSize, int compressedSize) { this.nanos = nanos; this.uncompressedSize = uncompressedSize; this.compressedSize = compressedSize; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #258	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 258 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord; } public long nanos() { return this.nanos; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #258	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #258	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/io/PacketStatsRecorderImpl$PacketStatsEntry$SizeRecord;
/* 258 */       //   0	8	1	o	Ljava/lang/Object; } public int uncompressedSize() { return this.uncompressedSize; } public int compressedSize() { return this.compressedSize; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\io\PacketStatsRecorderImpl$PacketStatsEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */