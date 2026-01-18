/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AudioComponent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, AudioComponent> getComponentType() {
/* 14 */     return EntityModule.get().getAudioComponentType();
/*    */   }
/*    */   
/* 17 */   private IntList soundEventIds = (IntList)new IntArrayList();
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = true;
/*    */ 
/*    */   
/*    */   public AudioComponent(IntList soundEventIds) {
/* 24 */     this.soundEventIds = soundEventIds;
/*    */   }
/*    */   
/*    */   public int[] getSoundEventIds() {
/* 28 */     return this.soundEventIds.toIntArray();
/*    */   }
/*    */   
/*    */   public void addSound(int soundIndex) {
/* 32 */     this.soundEventIds.add(soundIndex);
/* 33 */     this.isNetworkOutdated = true;
/*    */   }
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 37 */     boolean temp = this.isNetworkOutdated;
/* 38 */     this.isNetworkOutdated = false;
/* 39 */     return temp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 46 */     return new AudioComponent((IntList)new IntArrayList(this.soundEventIds));
/*    */   }
/*    */   
/*    */   public AudioComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\AudioComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */