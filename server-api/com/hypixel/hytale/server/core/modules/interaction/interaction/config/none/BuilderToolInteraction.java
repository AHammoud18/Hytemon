/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.protocol.WaitForDataFrom;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInteraction;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuilderToolInteraction
/*    */   extends SimpleInteraction
/*    */ {
/*    */   @Nonnull
/* 23 */   public static final BuilderCodec<BuilderToolInteraction> CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(BuilderToolInteraction.class, BuilderToolInteraction::new, SimpleInteraction.CODEC)
/* 24 */     .documentation("Runs a builder tool"))
/* 25 */     .build();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 30 */     return (Interaction)new com.hypixel.hytale.protocol.BuilderToolInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean needsRemoteSync() {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WaitForDataFrom getWaitForDataFrom() {
/* 41 */     return WaitForDataFrom.Client;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick0(boolean firstRun, float time, @NonNullDecl InteractionType type, @Nonnull InteractionContext context, @NonNullDecl CooldownHandler cooldownHandler) {
/* 46 */     (context.getState()).state = (context.getClientState()).state;
/* 47 */     super.tick0(firstRun, time, type, context, cooldownHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "BuilderToolInteraction{} " + super.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\BuilderToolInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */