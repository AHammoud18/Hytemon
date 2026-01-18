/*    */ package io.sentry;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class PerformanceCollectionData {
/*    */   @Nullable
/*  8 */   private Double cpuUsagePercentage = null; @Nullable
/*  9 */   private Long usedHeapMemory = null; @Nullable
/* 10 */   private Long usedNativeMemory = null;
/*    */   private final long nanoTimestamp;
/*    */   
/*    */   public PerformanceCollectionData(long nanoTimestamp) {
/* 14 */     this.nanoTimestamp = nanoTimestamp;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCpuUsagePercentage(@Nullable Double cpuUsagePercentage) {
/* 19 */     this.cpuUsagePercentage = cpuUsagePercentage;
/*    */   }
/*    */   @Nullable
/*    */   public Double getCpuUsagePercentage() {
/* 23 */     return this.cpuUsagePercentage;
/*    */   }
/*    */   
/*    */   public void setUsedHeapMemory(@Nullable Long usedHeapMemory) {
/* 27 */     this.usedHeapMemory = usedHeapMemory;
/*    */   }
/*    */   @Nullable
/*    */   public Long getUsedHeapMemory() {
/* 31 */     return this.usedHeapMemory;
/*    */   }
/*    */   
/*    */   public void setUsedNativeMemory(@Nullable Long usedNativeMemory) {
/* 35 */     this.usedNativeMemory = usedNativeMemory;
/*    */   }
/*    */   @Nullable
/*    */   public Long getUsedNativeMemory() {
/* 39 */     return this.usedNativeMemory;
/*    */   }
/*    */   
/*    */   public long getNanoTimestamp() {
/* 43 */     return this.nanoTimestamp;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\PerformanceCollectionData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */