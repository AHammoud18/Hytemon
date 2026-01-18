/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.combatactions;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluator;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.valuestore.ValueStore;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.logging.Level;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateCombatAction
/*    */   extends CombatActionOption
/*    */ {
/*    */   public static final BuilderCodec<StateCombatAction> CODEC;
/*    */   protected String state;
/*    */   protected String subState;
/*    */   
/*    */   static {
/* 47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StateCombatAction.class, StateCombatAction::new, CombatActionOption.BASE_CODEC).documentation("A combat action which switches the NPCs state. Using substate only will switch between combat substates, whereas including the main state can be used to transition out of combat.")).append(new KeyedCodec("State", (Codec)Codec.STRING), (option, s) -> option.state = s, option -> option.state).documentation("The main state name.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).append(new KeyedCodec("SubState", (Codec)Codec.STRING), (option, s) -> option.subState = s, option -> option.subState).documentation("The substate name.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getState() {
/* 54 */     return this.state;
/*    */   }
/*    */   
/*    */   public String getSubState() {
/* 58 */     return this.subState;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, CommandBuffer<EntityStore> commandBuffer, @Nonnull Role role, @Nonnull CombatActionEvaluator evaluator, ValueStore valueStore) {
/* 63 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */     
/* 65 */     role.getStateSupport().setState(ref, this.state, this.subState, (ComponentAccessor)commandBuffer);
/* 66 */     evaluator.completeCurrentAction(true, true);
/* 67 */     evaluator.clearTimeout();
/*    */     
/* 69 */     HytaleLogger.Api ctx = CombatActionEvaluator.LOGGER.at(Level.FINEST);
/* 70 */     if (ctx.isEnabled()) {
/* 71 */       ctx.log("%s: Set state to %s.%s", archetypeChunk.getReferenceTo(index), this.state, (this.subState == null) ? "Default" : this.subState);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBasicAttackAllowed(int selfIndex, ArchetypeChunk<EntityStore> archetypeChunk, CommandBuffer<EntityStore> commandBuffer, CombatActionEvaluator evaluator) {
/* 77 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 83 */     return "StateCombatAction{state='" + this.state + "', subState='" + this.subState + "'}" + super
/*    */ 
/*    */       
/* 86 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\evaluator\combatactions\StateCombatAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */