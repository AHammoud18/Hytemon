/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.ValueType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.ints.Int2FloatMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StatsConditionInteraction extends StatsConditionBaseInteraction {
/*    */   @Nonnull
/* 23 */   public static final BuilderCodec<StatsConditionInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(StatsConditionInteraction.class, StatsConditionInteraction::new, StatsConditionBaseInteraction.CODEC)
/* 24 */     .documentation("Interaction that is successful if the given stat conditions match."))
/* 25 */     .build();
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 29 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 30 */     assert commandBuffer != null;
/*    */     
/* 32 */     Ref<EntityStore> ref = context.getEntity();
/* 33 */     if (!canAfford(ref, (ComponentAccessor<EntityStore>)commandBuffer)) {
/* 34 */       (context.getState()).state = InteractionState.Failed;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canAfford(@Nonnull Ref<EntityStore> ref, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 40 */     EntityStatMap entityStatMapComponent = (EntityStatMap)componentAccessor.getComponent(ref, EntityStatMap.getComponentType());
/* 41 */     if (entityStatMapComponent == null) return false;
/*    */     
/* 43 */     if (this.costs == null) return false; 
/* 44 */     for (ObjectIterator<Int2FloatMap.Entry> objectIterator = this.costs.int2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Int2FloatMap.Entry cost = objectIterator.next();
/* 45 */       EntityStatValue stat = entityStatMapComponent.get(cost.getIntKey());
/* 46 */       if (stat == null) return false;
/*    */       
/* 48 */       float statValue = (this.valueType == ValueType.Absolute) ? stat.get() : (stat.asPercentage() * 100.0F);
/*    */       
/* 50 */       if (this.lessThan) {
/* 51 */         if (statValue >= cost.getFloatValue())
/* 52 */           return false; 
/*    */         continue;
/*    */       } 
/* 55 */       if (statValue < cost.getFloatValue() && !canOverdraw(statValue, stat.getMin())) {
/* 56 */         return false;
/*    */       } }
/*    */ 
/*    */ 
/*    */     
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 67 */     return (Interaction)new com.hypixel.hytale.protocol.StatsConditionInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 72 */     super.configurePacket(packet);
/* 73 */     com.hypixel.hytale.protocol.StatsConditionInteraction p = (com.hypixel.hytale.protocol.StatsConditionInteraction)packet;
/* 74 */     p.costs = (Map)this.costs;
/* 75 */     p.lessThan = this.lessThan;
/* 76 */     p.lenient = this.lenient;
/* 77 */     p.valueType = this.valueType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 83 */     return "StatsConditionInteraction{}" + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\StatsConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */