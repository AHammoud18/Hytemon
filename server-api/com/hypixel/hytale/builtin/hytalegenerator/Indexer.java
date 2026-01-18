/*    */ package com.hypixel.hytale.builtin.hytalegenerator;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Indexer
/*    */ {
/* 10 */   private Map<Object, Integer> ids = new HashMap<>();
/*    */ 
/*    */   
/*    */   public int getIdFor(Object o) {
/* 14 */     return ((Integer)this.ids.computeIfAbsent(o, k -> Integer.valueOf(this.ids.size()))).intValue();
/*    */   }
/*    */   
/*    */   public int size() {
/* 18 */     return this.ids.size();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\Indexer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */