/*    */ package com.hypixel.hytale.server.core.prefab.event;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrefabPasteEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   private final int prefabId;
/*    */   private final boolean pasteStart;
/*    */   
/*    */   public PrefabPasteEvent(int prefabId, boolean pasteStart) {
/* 29 */     this.prefabId = prefabId;
/* 30 */     this.pasteStart = pasteStart;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPrefabId() {
/* 37 */     return this.prefabId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPasteStart() {
/* 44 */     return this.pasteStart;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\event\PrefabPasteEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */