/*    */ package com.hypixel.hytale.server.spawning.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloodFillEntryPoolSimple
/*    */ {
/*    */   private static final int ENTRY_SIZE = 5;
/* 12 */   private final List<int[]> entryPool = (List<int[]>)new ObjectArrayList();
/*    */   
/*    */   public int[] allocate() {
/* 15 */     return this.entryPool.isEmpty() ? new int[5] : this.entryPool.removeLast();
/*    */   }
/*    */   
/*    */   public void deallocate(int[] entry) {
/* 19 */     this.entryPool.add(entry);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawnin\\util\FloodFillEntryPoolSimple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */