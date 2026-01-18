/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionState;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
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
/*    */ public class RunRootInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<RunRootInteraction> CODEC;
/*    */   protected String rootInteraction;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RunRootInteraction.class, RunRootInteraction::new, SimpleInstantInteraction.CODEC).documentation("Runs the given interaction root.")).appendInherited(new KeyedCodec("RootInteraction", (Codec)Codec.STRING), (o, i) -> o.rootInteraction = i, o -> o.rootInteraction, (o, p) -> o.rootInteraction = p.rootInteraction).documentation("A reference to a root interaction to run").addValidator(Validators.nonNull()).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getValidator().late()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 41 */     (context.getState()).state = InteractionState.Finished;
/* 42 */     context.execute(RootInteraction.getRootInteractionOrUnknown(this.rootInteraction));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 48 */     return (Interaction)new com.hypixel.hytale.protocol.RunRootInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 53 */     super.configurePacket(packet);
/* 54 */     com.hypixel.hytale.protocol.RunRootInteraction p = (com.hypixel.hytale.protocol.RunRootInteraction)packet;
/* 55 */     p.rootInteraction = RootInteraction.getRootInteractionIdOrUnknown(this.rootInteraction);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\RunRootInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */