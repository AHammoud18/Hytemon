/*    */ package com.hypixel.hytale.server.core.modules.entitystats.asset.condition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AliveCondition
/*    */   extends Condition
/*    */ {
/*    */   @Nonnull
/* 21 */   public static final BuilderCodec<AliveCondition> CODEC = BuilderCodec.builder(AliveCondition.class, AliveCondition::new, Condition.BASE_CODEC)
/* 22 */     .build();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AliveCondition() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AliveCondition(boolean inverse) {
/* 38 */     super(inverse);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval0(@Nonnull ComponentAccessor<EntityStore> componentAccessor, @Nonnull Ref<EntityStore> ref, @Nonnull Instant currentTime) {
/* 43 */     return !componentAccessor.getArchetype(ref).contains(DeathComponent.getComponentType());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 49 */     return "AliveCondition{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\condition\AliveCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */