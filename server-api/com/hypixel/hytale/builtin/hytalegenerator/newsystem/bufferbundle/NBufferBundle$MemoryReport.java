/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class MemoryReport
/*     */ {
/*     */   public final List<GridEntry> gridEntries;
/*     */   
/*     */   public static final class GridEntry
/*     */     extends Record
/*     */   {
/*     */     private final MemInstrument.Report report;
/*     */     private final int bufferCount;
/*     */     @Nonnull
/*     */     private final NBufferType bufferType;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #363	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/hytalegenerator/newsystem/bufferbundle/NBufferBundle$MemoryReport$GridEntry;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public GridEntry(MemInstrument.Report report, int bufferCount, @Nonnull NBufferType bufferType) {
/* 363 */       this.report = report; this.bufferCount = bufferCount; this.bufferType = bufferType; } public MemInstrument.Report report() { return this.report; } public int bufferCount() { return this.bufferCount; } @Nonnull public NBufferType bufferType() { return this.bufferType; }
/*     */      @Nonnull
/*     */     public String toString(int indentation) {
/* 366 */       long size_mb = this.report.size_bytes() / 1000000L;
/*     */       
/* 368 */       StringBuilder builder = new StringBuilder();
/* 369 */       builder.append("\t".repeat(indentation)).append(this.bufferType.name + " Grid (Index ").append((bufferType()).index).append("):\n");
/* 370 */       builder.append("\t".repeat(indentation + 1)).append("Memory Footprint: ").append(size_mb).append(" mb\n");
/* 371 */       builder.append("\t".repeat(indentation + 1)).append("Buffer Count: ").append(this.bufferCount).append("\n");
/*     */       
/* 373 */       return builder.toString();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryReport() {
/* 380 */     this.gridEntries = new ArrayList<>();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 385 */     this.gridEntries.sort((o1, o2) -> ((o1.bufferType()).index > (o2.bufferType()).index) ? 1 : (((o1.bufferType()).index < (o2.bufferType()).index) ? -1 : 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 397 */     long total_mb = 0L;
/* 398 */     for (GridEntry entry : this.gridEntries) {
/* 399 */       total_mb += entry.report.size_bytes();
/*     */     }
/* 401 */     total_mb /= 1000000L;
/*     */     
/* 403 */     builder.append("Memory Usage Report\n");
/* 404 */     builder.append("Buffers Memory Usage: ").append(total_mb).append(" mb\n");
/*     */     
/* 406 */     for (GridEntry entry : this.gridEntries) {
/* 407 */       builder.append(entry.toString(1));
/*     */     }
/*     */     
/* 410 */     return builder.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\NBufferBundle$MemoryReport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */