/*    */ package com.hypixel.hytale.server.npc.sensorinfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CachedPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   private boolean fromCache;
/*    */   
/*    */   public void setIsFromCache(boolean status) {
/* 16 */     this.fromCache = status;
/*    */   }
/*    */   
/*    */   public boolean isFromCache() {
/* 20 */     return this.fromCache;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\sensorinfo\CachedPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */