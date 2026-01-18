/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.EntityStatsModule;
/*    */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.ScaledCurveCondition;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelfStatAbsoluteCondition
/*    */   extends ScaledCurveCondition
/*    */ {
/*    */   public static final BuilderCodec<SelfStatAbsoluteCondition> CODEC;
/*    */   protected String stat;
/*    */   protected int statIndex;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SelfStatAbsoluteCondition.class, SelfStatAbsoluteCondition::new, ScaledCurveCondition.ABSTRACT_CODEC).documentation("A scaled curve condition that returns a utility value based on the absolute value of one of this NPC's stats.")).appendInherited(new KeyedCodec("Stat", (Codec)Codec.STRING), (condition, s) -> condition.stat = s, condition -> condition.stat, (condition, parent) -> condition.stat = parent.stat).addValidator(Validators.nonNull()).addValidator(EntityStatType.VALIDATOR_CACHE.getValidator()).documentation("The stat to check.").add()).afterDecode(condition -> condition.statIndex = EntityStatType.getAssetMap().getIndex(condition.stat))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected double getInput(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 44 */     EntityStatMap entityStatMapComponent = (EntityStatMap)archetypeChunk.getComponent(selfIndex, EntityStatsModule.get().getEntityStatMapComponentType());
/* 45 */     assert entityStatMapComponent != null;
/*    */     
/* 47 */     return ((EntityStatValue)Objects.<EntityStatValue>requireNonNull(entityStatMapComponent.get(this.statIndex))).get();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "SelfStatAbsoluteCondition{stat='" + this.stat + "', statIndex=" + this.statIndex + "}" + super
/*    */ 
/*    */       
/* 56 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\SelfStatAbsoluteCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */