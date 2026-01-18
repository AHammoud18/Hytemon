/*     */ package com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
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
/*     */ public class ActionSet
/*     */ {
/*     */   public static final BuilderCodec<ActionSet> CODEC;
/*     */   protected CombatActionEvaluatorConfig.BasicAttacks basicAttacks;
/*     */   protected String[] combatActions;
/*     */   
/*     */   static {
/* 152 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ActionSet.class, ActionSet::new).append(new KeyedCodec("BasicAttacks", (Codec)CombatActionEvaluatorConfig.BasicAttacks.CODEC), (actionSet, o) -> actionSet.basicAttacks = o, actionSet -> actionSet.basicAttacks).documentation("The basic attacks to be used in this combat substate.").add()).append(new KeyedCodec("Actions", (Codec)Codec.STRING_ARRAY), (actionSet, o) -> actionSet.combatActions = o, actionsSet -> actionsSet.combatActions).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).documentation("A list of available actions that should be used in this combat substate, mapped from AvailableActions.").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CombatActionEvaluatorConfig.BasicAttacks getBasicAttacks() {
/* 161 */     return this.basicAttacks;
/*     */   }
/*     */   
/*     */   public String[] getCombatActions() {
/* 165 */     return this.combatActions;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 171 */     return "ActionSet{basicAttacks=" + String.valueOf(this.basicAttacks) + ", combatActions=" + 
/*     */       
/* 173 */       Arrays.toString((Object[])this.combatActions) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\evaluator\CombatActionEvaluatorConfig$ActionSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */