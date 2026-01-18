/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleInstantInteraction
/*    */   extends SimpleInteraction
/*    */ {
/* 20 */   public static final BuilderCodec<SimpleInstantInteraction> CODEC = BuilderCodec.abstractBuilder(SimpleInstantInteraction.class, SimpleInteraction.CODEC)
/* 21 */     .build();
/*    */   
/*    */   public SimpleInstantInteraction(String id) {
/* 24 */     super(id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected SimpleInstantInteraction() {}
/*    */ 
/*    */   
/*    */   protected final void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 33 */     if (!firstRun)
/*    */       return; 
/* 35 */     firstRun(type, context, cooldownHandler);
/* 36 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void simulateTick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 41 */     if (!firstRun)
/*    */       return; 
/* 43 */     simulateFirstRun(type, context, cooldownHandler);
/*    */ 
/*    */     
/* 46 */     if (getWaitForDataFrom() == WaitForDataFrom.Server && context.getServerState() != null && (context.getServerState()).state == InteractionState.Failed) {
/* 47 */       (context.getState()).state = InteractionState.Failed;
/*    */     }
/* 49 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void firstRun(@Nonnull InteractionType paramInteractionType, @Nonnull InteractionContext paramInteractionContext, @Nonnull CooldownHandler paramCooldownHandler);
/*    */ 
/*    */ 
/*    */   
/*    */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 60 */     firstRun(type, context, cooldownHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "SimpleInstantInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\SimpleInstantInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */