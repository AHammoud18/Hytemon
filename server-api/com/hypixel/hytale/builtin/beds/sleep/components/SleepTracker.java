/*    */ package com.hypixel.hytale.builtin.beds.sleep.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.beds.BedsPlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.packets.world.UpdateSleepState;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nullable;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*    */ 
/*    */ public class SleepTracker
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, SleepTracker> getComponentType() {
/* 14 */     return BedsPlugin.getInstance().getSleepTrackerComponentType();
/*    */   }
/*    */   
/* 17 */   private UpdateSleepState lastSentPacket = new UpdateSleepState(false, false, null, null);
/*    */   
/*    */   @Nullable
/*    */   public UpdateSleepState generatePacketToSend(UpdateSleepState state) {
/* 21 */     if (this.lastSentPacket.equals(state)) {
/* 22 */       return null;
/*    */     }
/*    */     
/* 25 */     this.lastSentPacket = state;
/* 26 */     return this.lastSentPacket;
/*    */   }
/*    */ 
/*    */   
/*    */   @NullableDecl
/*    */   public Component<EntityStore> clone() {
/* 32 */     return new SleepTracker();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\beds\sleep\components\SleepTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */