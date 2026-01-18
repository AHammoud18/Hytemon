/*    */ package com.hypixel.hytale.builtin.portals.utils.spatial;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Entry<T>
/*    */ {
/*    */   private final Vector3d pos;
/*    */   private Vector3i cell;
/*    */   private final T value;
/*    */   
/*    */   private Entry(Vector3d pos, Vector3i cell, T value) {
/* 26 */     this.pos = pos;
/* 27 */     this.cell = cell;
/* 28 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portal\\utils\spatial\SpatialHashGrid$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */