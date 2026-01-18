/*    */ package com.hypixel.hytale.builtin.ambience.components;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.AmbiencePlugin;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.packets.world.UpdateEnvironmentMusic;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AmbienceTracker
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, AmbienceTracker> getComponentType() {
/* 14 */     return AmbiencePlugin.get().getAmbienceTrackerComponentType();
/*    */   }
/*    */   
/* 17 */   private final UpdateEnvironmentMusic musicPacket = new UpdateEnvironmentMusic(0);
/*    */   
/*    */   private int forcedMusicIndex;
/*    */   
/*    */   public void setForcedMusicIndex(int forcedMusicIndex) {
/* 22 */     this.forcedMusicIndex = forcedMusicIndex;
/*    */   }
/*    */   
/*    */   public int getForcedMusicIndex() {
/* 26 */     return this.forcedMusicIndex;
/*    */   }
/*    */   
/*    */   public UpdateEnvironmentMusic getMusicPacket() {
/* 30 */     return this.musicPacket;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Component<EntityStore> clone() {
/* 36 */     AmbienceTracker clone = new AmbienceTracker();
/* 37 */     clone.forcedMusicIndex = this.forcedMusicIndex;
/* 38 */     return clone;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\components\AmbienceTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */