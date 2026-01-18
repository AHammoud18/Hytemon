/*    */ package com.hypixel.hytale.server.npc.decisionmaker.stateevaluator;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.core.Option;
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
/*    */ public class StateOption
/*    */   extends Option
/*    */ {
/*    */   public static final BuilderCodec<StateOption> CODEC;
/*    */   protected String state;
/*    */   protected String subState;
/*    */   protected int stateIndex;
/*    */   protected int subStateIndex;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(StateOption.class, StateOption::new, Option.ABSTRACT_CODEC).append(new KeyedCodec("State", (Codec)Codec.STRING), (option, s) -> option.state = s, option -> option.state).documentation("The main state name.").addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).append(new KeyedCodec("SubState", (Codec)Codec.STRING), (option, s) -> option.state = s, option -> option.state).documentation("The (optional) substate name.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getState() {
/* 44 */     return this.state;
/*    */   }
/*    */   
/*    */   public String getSubState() {
/* 48 */     return this.subState;
/*    */   }
/*    */   
/*    */   public int getStateIndex() {
/* 52 */     return this.stateIndex;
/*    */   }
/*    */   
/*    */   public int getSubStateIndex() {
/* 56 */     return this.subStateIndex;
/*    */   }
/*    */   
/*    */   public void setStateIndex(int stateIndex, int subStateIndex) {
/* 60 */     this.stateIndex = stateIndex;
/* 61 */     this.subStateIndex = subStateIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "StateOption{state=" + this.state + ", stateIndex=" + this.stateIndex + "} " + super
/*    */ 
/*    */       
/* 70 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\decisionmaker\stateevaluator\StateOption.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */