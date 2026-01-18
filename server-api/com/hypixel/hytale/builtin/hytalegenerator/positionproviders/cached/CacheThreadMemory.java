/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders.cached;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CacheThreadMemory
/*    */ {
/*    */   Map<Long, Vector3d[]> sections;
/*    */   LinkedList<Long> expirationList;
/*    */   int size;
/*    */   
/*    */   public CacheThreadMemory(int size) {
/* 15 */     if (size < 0)
/* 16 */       throw new IllegalArgumentException(); 
/* 17 */     this.sections = (Map)new HashMap<>(size);
/* 18 */     this.expirationList = new LinkedList<>();
/* 19 */     this.size = size;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\cached\CacheThreadMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */