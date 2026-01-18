/*     */ package com.hypixel.hytale.server.npc.decisionmaker.core;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.Condition;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Option
/*     */ {
/*     */   public static final BuilderCodec<Option> ABSTRACT_CODEC;
/*     */   protected String description;
/*     */   protected String[] conditions;
/*     */   
/*     */   static {
/*  65 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Option.class).append(new KeyedCodec("Description", (Codec)Codec.STRING), (option, s) -> option.description = s, option -> option.description).documentation("A friendly description of this option's outcome.").add()).appendInherited(new KeyedCodec("Conditions", Condition.CHILD_ASSET_CODEC_ARRAY), (option, s) -> option.conditions = s, option -> option.conditions, (option, parent) -> option.conditions = parent.conditions).documentation("The list of conditions for evaluating this option's utility.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)Condition.VALIDATOR_CACHE.getArrayValidator()).add()).appendInherited(new KeyedCodec("WeightCoefficient", (Codec)Codec.DOUBLE), (option, d) -> option.weightCoefficient = d.doubleValue(), option -> Double.valueOf(option.weightCoefficient), (option, parent) -> option.weightCoefficient = parent.weightCoefficient).documentation("An additional weighted ranking that can be used to greatly increase the utility of this option.").addValidator(Validators.greaterThanOrEqual(Double.valueOf(1.0D))).add()).build();
/*     */   }
/*     */ 
/*     */   
/*  69 */   protected double weightCoefficient = 1.0D;
/*     */ 
/*     */   
/*     */   protected ConditionReference[] sortedConditions;
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getConditions() {
/*  77 */     return this.conditions;
/*     */   }
/*     */   
/*     */   public double getWeightCoefficient() {
/*  81 */     return this.weightCoefficient;
/*     */   }
/*     */   
/*     */   public void sortConditions() {
/*  85 */     this.sortedConditions = new ConditionReference[this.conditions.length];
/*  86 */     for (int i = 0; i < this.conditions.length; i++) {
/*  87 */       Condition condition = (Condition)Condition.getAssetMap().getAsset(this.conditions[i]);
/*  88 */       if (condition == null) throw new IllegalStateException("Condition '" + this.conditions[i] + "' does not exist!");
/*     */       
/*  90 */       this.sortedConditions[i] = new ConditionReference(Condition.getAssetMap().getIndex(this.conditions[i]), condition);
/*     */     } 
/*     */     
/*  93 */     Arrays.sort(this.sortedConditions, Comparator.comparingInt(ConditionReference::getSimplicity));
/*     */   }
/*     */   
/*     */   public void setupNPC(Role role) {
/*  97 */     for (ConditionReference condition : this.sortedConditions) {
/*  98 */       condition.get().setupNPC(role);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setupNPC(Holder<EntityStore> holder) {
/* 103 */     for (ConditionReference condition : this.sortedConditions) {
/* 104 */       condition.get().setupNPC(holder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateUtility(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, @Nonnull EvaluationContext context) {
/* 120 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPCEntity.getComponentType());
/* 121 */     assert npcComponent != null;
/*     */     
/* 123 */     UUIDComponent uuidComponent = (UUIDComponent)archetypeChunk.getComponent(selfIndex, UUIDComponent.getComponentType());
/* 124 */     assert uuidComponent != null;
/*     */     
/* 126 */     double compensationFactor = 1.0D - 1.0D / this.sortedConditions.length;
/* 127 */     double result = 1.0D;
/*     */     
/* 129 */     for (ConditionReference reference : this.sortedConditions) {
/* 130 */       Condition condition = reference.get();
/* 131 */       double score = condition.calculateUtility(selfIndex, archetypeChunk, target, commandBuffer, context);
/*     */       
/* 133 */       HytaleLogger.Api logContext = Evaluator.LOGGER.at(Level.FINE);
/* 134 */       if (logContext.isEnabled()) {
/* 135 */         logContext.log("%s with uuid %s: Scored condition %s at %s", npcComponent.getRoleName(), uuidComponent.getUuid(), condition, Double.valueOf(score));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 140 */       result *= score + (1.0D - score) * compensationFactor * score;
/*     */       
/* 142 */       if (result == 0.0D || result < context.getMinimumUtility()) return 0.0D; 
/*     */     } 
/* 144 */     return result * this.weightCoefficient;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 150 */     return "Option{description=" + this.description + ", conditions=" + 
/*     */       
/* 152 */       Arrays.toString((Object[])this.conditions) + ", sortedConditions=" + 
/* 153 */       Arrays.toString((Object[])this.sortedConditions) + ", weightCoefficient=" + this.weightCoefficient + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ConditionReference
/*     */   {
/*     */     private final int index;
/*     */     private WeakReference<Condition> reference;
/*     */     
/*     */     private ConditionReference(int index, @Nonnull Condition condition) {
/* 163 */       this.index = index;
/* 164 */       this.reference = condition.getReference();
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     private Condition get() {
/* 169 */       Condition condition = this.reference.get();
/* 170 */       if (condition == null) {
/* 171 */         condition = (Condition)Condition.getAssetMap().getAsset(this.index);
/* 172 */         this.reference = condition.getReference();
/*     */       } 
/* 174 */       return condition;
/*     */     }
/*     */     
/*     */     private int getSimplicity() {
/* 178 */       return get().getSimplicity();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\Option.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */