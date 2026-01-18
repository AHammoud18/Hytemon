/*    */ package com.hypixel.hytale.server.core.modules.entity.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.AnimationSlot;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActiveAnimationComponent
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static ComponentType<EntityStore, ActiveAnimationComponent> getComponentType() {
/* 18 */     return EntityModule.get().getActiveAnimationComponentType();
/*    */   }
/*    */   
/* 21 */   private final String[] activeAnimations = new String[AnimationSlot.VALUES.length];
/*    */ 
/*    */   
/*    */   private boolean isNetworkOutdated = false;
/*    */ 
/*    */   
/*    */   public ActiveAnimationComponent(String[] activeAnimations) {
/* 28 */     System.arraycopy(activeAnimations, 0, this.activeAnimations, 0, activeAnimations.length);
/*    */   }
/*    */   
/*    */   public String[] getActiveAnimations() {
/* 32 */     return this.activeAnimations;
/*    */   }
/*    */   
/*    */   public void setPlayingAnimation(AnimationSlot slot, @Nullable String animation) {
/* 36 */     if (this.activeAnimations[slot.ordinal()] != null && this.activeAnimations[slot.ordinal()].equals(animation))
/*    */       return; 
/* 38 */     this.activeAnimations[slot.ordinal()] = animation;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean consumeNetworkOutdated() {
/* 43 */     boolean temp = this.isNetworkOutdated;
/* 44 */     this.isNetworkOutdated = false;
/* 45 */     return temp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 52 */     return new ActiveAnimationComponent(this.activeAnimations);
/*    */   }
/*    */   
/*    */   public ActiveAnimationComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\component\ActiveAnimationComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */