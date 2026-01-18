/*    */ package com.hypixel.hytale.server.core.inventory.container;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class TestRemoveItemSlotResult
/*    */ {
/* 10 */   Map<Short, Integer> picked = (Map<Short, Integer>)new Object2IntOpenHashMap();
/*    */   int quantityRemaining;
/*    */   
/*    */   public TestRemoveItemSlotResult(int testQuantityRemaining) {
/* 14 */     this.quantityRemaining = testQuantityRemaining;
/*    */   }
/*    */   
/*    */   public boolean hasResult() {
/* 18 */     return !this.picked.isEmpty();
/*    */   }
/*    */   
/*    */   public Set<Short> getPickedSlots() {
/* 22 */     return this.picked.keySet();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\inventory\container\TestRemoveItemSlotResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */