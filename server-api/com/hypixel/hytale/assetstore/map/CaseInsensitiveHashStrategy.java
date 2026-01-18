/*    */ package com.hypixel.hytale.assetstore.map;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Hash;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CaseInsensitiveHashStrategy<K>
/*    */   implements Hash.Strategy<K>
/*    */ {
/* 14 */   private static final CaseInsensitiveHashStrategy INSTANCE = new CaseInsensitiveHashStrategy();
/*    */ 
/*    */   
/*    */   public static <K> CaseInsensitiveHashStrategy<K> getInstance() {
/* 18 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode(K key) {
/* 23 */     if (key == null) return 0; 
/* 24 */     if (key instanceof String) { String s = (String)key;
/*    */       
/* 26 */       int hash = 0;
/* 27 */       for (int i = 0; i < s.length(); i++) {
/* 28 */         hash = 31 * hash + Character.toLowerCase(s.charAt(i));
/*    */       }
/* 30 */       return hash; }
/*    */     
/* 32 */     return key.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(K a, K b) {
/* 37 */     if (a == b) return true; 
/* 38 */     if (a == null || b == null) return false; 
/* 39 */     if (a instanceof String) { String sa = (String)a; if (b instanceof String) { String sb = (String)b;
/* 40 */         return sa.equalsIgnoreCase(sb); }
/*    */        }
/* 42 */      return a.equals(b);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\assetstore\map\CaseInsensitiveHashStrategy.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */