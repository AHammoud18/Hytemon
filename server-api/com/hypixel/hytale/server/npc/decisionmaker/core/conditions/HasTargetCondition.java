/*    */ package com.hypixel.hytale.server.npc.decisionmaker.core.conditions;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.EvaluationContext;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.conditions.base.SimpleCondition;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class HasTargetCondition
/*    */   extends SimpleCondition
/*    */ {
/*    */   public static final BuilderCodec<HasTargetCondition> CODEC;
/*    */   protected String targetSlot;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HasTargetCondition.class, HasTargetCondition::new, ABSTRACT_CODEC).documentation("A simple boolean condition that returns whether the NPC has a target locked in the given slot.")).appendInherited(new KeyedCodec("TargetSlot", (Codec)Codec.STRING), (condition, s) -> condition.targetSlot = s, condition -> condition.targetSlot, (condition, parent) -> condition.targetSlot = parent.targetSlot).documentation("The target slot to check.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getTargetSlot() {
/* 42 */     return this.targetSlot;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean evaluate(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 47 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPCEntity.getComponentType());
/* 48 */     assert npcComponent != null;
/*    */     
/* 50 */     return npcComponent.getRole().getMarkedEntitySupport().hasMarkedEntityInSlot(this.targetSlot);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 56 */     return "HasTargetCondition{targetSlot=" + this.targetSlot + "} " + super
/*    */       
/* 58 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\HasTargetCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */