/*    */ package com.hypixel.hytale.server.npc.corecomponents.audiovisual;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.ActiveAnimationComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.animations.NPCAnimationSlot;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.audiovisual.builders.BuilderSensorAnimation;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorAnimation
/*    */   extends SensorBase {
/*    */   protected final NPCAnimationSlot slot;
/*    */   protected final String animationId;
/*    */   
/*    */   public SensorAnimation(@Nonnull BuilderSensorAnimation builder, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderSensorBase)builder);
/* 24 */     this.slot = builder.getAnimationSlot(support);
/* 25 */     this.animationId = builder.getAnimationId(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     ActiveAnimationComponent activeAnimationComponent = (ActiveAnimationComponent)store.getComponent(ref, ActiveAnimationComponent.getComponentType());
/* 31 */     assert activeAnimationComponent != null;
/*    */     
/* 33 */     return (super.matches(ref, role, dt, store) && Objects.equals(activeAnimationComponent.getActiveAnimations()[this.slot.getMappedSlot().ordinal()], this.animationId));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 38 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\audiovisual\SensorAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */