/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.operation;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.InteractionRules;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public interface Operation
/*    */ {
/*    */   void tick(@Nonnull Ref<EntityStore> paramRef, @Nonnull LivingEntity paramLivingEntity, boolean paramBoolean, float paramFloat, @Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nonnull CooldownHandler paramCooldownHandler);
/*    */   
/*    */   void simulateTick(@Nonnull Ref<EntityStore> paramRef, @Nonnull LivingEntity paramLivingEntity, boolean paramBoolean, float paramFloat, @Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nonnull CooldownHandler paramCooldownHandler);
/*    */   
/*    */   default void handle(@Nonnull Ref<EntityStore> ref, boolean firstRun, float time, @Nonnull InteractionType type, @Nonnull InteractionContext context) {}
/*    */   
/*    */   WaitForDataFrom getWaitForDataFrom();
/*    */   
/*    */   @Nullable
/*    */   default InteractionRules getRules() {
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   default Int2ObjectMap<IntSet> getTags() {
/* 80 */     return Int2ObjectMaps.emptyMap();
/*    */   }
/*    */   
/*    */   default Operation getInnerOperation() {
/* 84 */     Operation op = this;
/* 85 */     while (op instanceof NestedOperation) {
/* 86 */       op = ((NestedOperation)op).inner();
/*    */     }
/* 88 */     return op;
/*    */   }
/*    */   
/*    */   public static interface NestedOperation {
/*    */     Operation inner();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\operation\Operation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */