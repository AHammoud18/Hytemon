/*    */ package com.hypixel.hytale.server.worldgen.zone;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZoneGeneratorResult
/*    */ {
/*    */   protected Zone zone;
/*    */   protected double borderDistance;
/*    */   
/*    */   public ZoneGeneratorResult() {}
/*    */   
/*    */   public ZoneGeneratorResult(Zone zone, double borderDistance) {
/* 15 */     this.zone = zone;
/* 16 */     this.borderDistance = borderDistance;
/*    */   }
/*    */   
/*    */   public void setZone(Zone zone) {
/* 20 */     this.zone = zone;
/*    */   }
/*    */   
/*    */   public void setBorderDistance(double borderDistance) {
/* 24 */     this.borderDistance = borderDistance;
/*    */   }
/*    */   
/*    */   public Zone getZone() {
/* 28 */     return this.zone;
/*    */   }
/*    */   
/*    */   public double getBorderDistance() {
/* 32 */     return this.borderDistance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\zone\ZoneGeneratorResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */