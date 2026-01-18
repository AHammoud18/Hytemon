/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.environment;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentRange
/*    */ {
/*    */   private int min;
/*    */   private int max;
/*    */   private int id;
/*    */   
/*    */   public EnvironmentRange(int id) {
/* 14 */     this(0, 2147483646, id);
/*    */   }
/*    */   
/*    */   public EnvironmentRange(int min, int max, int id) {
/* 18 */     this.min = min;
/* 19 */     this.max = max;
/* 20 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getMin() {
/* 24 */     return this.min;
/*    */   }
/*    */   
/*    */   void setMin(int min) {
/* 28 */     this.min = min;
/*    */   }
/*    */   
/*    */   public int getMax() {
/* 32 */     return this.max;
/*    */   }
/*    */   
/*    */   void setMax(int max) {
/* 36 */     this.max = max;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   void setId(int id) {
/* 44 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int height() {
/* 48 */     return this.max - this.min + 1;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public EnvironmentRange copy() {
/* 53 */     return new EnvironmentRange(this.min, this.max, this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 59 */     return "EnvironmentRange{min=" + this.min + ", max=" + this.max + ", id='" + this.id + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\environment\EnvironmentRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */