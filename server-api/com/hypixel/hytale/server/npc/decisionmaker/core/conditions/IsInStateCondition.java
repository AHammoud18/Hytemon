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
/*    */ import com.hypixel.hytale.server.npc.role.support.StateSupport;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IsInStateCondition
/*    */   extends SimpleCondition
/*    */ {
/*    */   public static final BuilderCodec<IsInStateCondition> CODEC;
/*    */   protected String state;
/*    */   protected String subState;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IsInStateCondition.class, IsInStateCondition::new, ABSTRACT_CODEC).documentation("A simple boolean condition that returns whether the NPC is in a given state.")).appendInherited(new KeyedCodec("State", (Codec)Codec.STRING), (condition, s) -> condition.state = s, condition -> condition.state, (condition, parent) -> condition.state = parent.state).documentation("The main state to evaluate.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).appendInherited(new KeyedCodec("SubState", (Codec)Codec.STRING), (condition, s) -> condition.subState = s, condition -> condition.subState, (condition, parent) -> condition.subState = parent.subState).documentation("The optional substate to evaluate.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getState() {
/* 52 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean evaluate(int selfIndex, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, Ref<EntityStore> target, CommandBuffer<EntityStore> commandBuffer, EvaluationContext context) {
/* 57 */     NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(selfIndex, NPCEntity.getComponentType());
/* 58 */     assert npcComponent != null;
/*    */     
/* 60 */     StateSupport stateSupport = npcComponent.getRole().getStateSupport();
/* 61 */     return stateSupport.inState(this.state, this.subState);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "IsInStateCondition{state=" + this.state + ", subState=" + this.subState + "} " + super
/*    */ 
/*    */       
/* 70 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\core\conditions\IsInStateCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */