/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class JavaMemoryCollector implements IPerformanceSnapshotCollector {
/*    */   @NotNull
/*  9 */   private final Runtime runtime = Runtime.getRuntime();
/*    */ 
/*    */   
/*    */   public void setup() {}
/*    */ 
/*    */   
/*    */   public void collect(@NotNull PerformanceCollectionData performanceCollectionData) {
/* 16 */     long usedMemory = this.runtime.totalMemory() - this.runtime.freeMemory();
/* 17 */     performanceCollectionData.setUsedHeapMemory(Long.valueOf(usedMemory));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\JavaMemoryCollector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */