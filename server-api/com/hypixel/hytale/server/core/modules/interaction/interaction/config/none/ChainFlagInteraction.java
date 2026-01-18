/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChainFlagInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ChainFlagInteraction> CODEC;
/*    */   protected String chainId;
/*    */   protected String flag;
/*    */   
/*    */   static {
/* 40 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ChainFlagInteraction.class, ChainFlagInteraction::new, SimpleInstantInteraction.CODEC).documentation("Sets a flag on the given chain id that a Chaining interaction can optionally use to adjust what it'll execute.")).appendInherited(new KeyedCodec("ChainId", (Codec)Codec.STRING), (o, i) -> o.chainId = i, o -> o.chainId, (o, p) -> o.chainId = p.chainId).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Flag", (Codec)Codec.STRING), (o, i) -> o.flag = i, o -> o.flag, (o, p) -> o.flag = p.flag).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 53 */     return (Interaction)new com.hypixel.hytale.protocol.ChainFlagInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 58 */     super.configurePacket(packet);
/* 59 */     com.hypixel.hytale.protocol.ChainFlagInteraction p = (com.hypixel.hytale.protocol.ChainFlagInteraction)packet;
/* 60 */     p.chainId = this.chainId;
/* 61 */     p.flag = this.flag;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\ChainFlagInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */