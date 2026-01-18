/*    */ package com.hypixel.hytale.server.core.asset;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LoadAssetEvent
/*    */   implements IEvent<Void>
/*    */ {
/*    */   public static final short PRIORITY_LOAD_COMMON = -32;
/*    */   public static final short PRIORITY_LOAD_REGISTRY = -16;
/*    */   public static final short PRIORITY_LOAD_LATE = 64;
/*    */   private final long bootStart;
/*    */   @Nonnull
/* 16 */   private final List<String> reasons = (List<String>)new ObjectArrayList();
/*    */   
/*    */   private boolean shouldShutdown = false;
/*    */ 
/*    */   
/*    */   public LoadAssetEvent(long bootStart) {
/* 22 */     this.bootStart = bootStart;
/*    */   }
/*    */   
/*    */   public long getBootStart() {
/* 26 */     return this.bootStart;
/*    */   }
/*    */   
/*    */   public boolean isShouldShutdown() {
/* 30 */     return this.shouldShutdown;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<String> getReasons() {
/* 35 */     return this.reasons;
/*    */   }
/*    */   
/*    */   public void failed(boolean shouldShutdown, String reason) {
/* 39 */     this.shouldShutdown |= shouldShutdown;
/* 40 */     this.reasons.add(reason);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     return "LoadAssetEvent{bootStart=" + this.bootStart + ", shouldShutdown=" + this.shouldShutdown + ", reasons=" + String.valueOf(this.reasons) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\LoadAssetEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */