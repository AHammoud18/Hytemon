/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionCooldown;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class IncrementCooldownInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<IncrementCooldownInteraction> CODEC;
/*     */   @Nullable
/*     */   private String cooldown;
/*     */   private float cooldownTime;
/*     */   private float chargeTime;
/*     */   private int charge;
/*     */   private boolean interruptRecharge;
/*     */   
/*     */   static {
/*  69 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IncrementCooldownInteraction.class, IncrementCooldownInteraction::new, SimpleInstantInteraction.CODEC).documentation("Increase the given cooldown.")).appendInherited(new KeyedCodec("Id", (Codec)Codec.STRING), (o, i) -> o.cooldown = i, o -> o.cooldown, (o, p) -> o.cooldown = p.cooldown).documentation("The ID of the cooldown to increment").add()).appendInherited(new KeyedCodec("Time", (Codec)Codec.FLOAT), (o, i) -> o.cooldownTime = i.floatValue(), o -> Float.valueOf(o.cooldownTime), (o, p) -> o.cooldownTime = p.cooldownTime).documentation("The amount of time to increase the current cooldown time by").add()).appendInherited(new KeyedCodec("ChargeTime", (Codec)Codec.FLOAT), (o, i) -> o.chargeTime = i.floatValue(), o -> Float.valueOf(o.chargeTime), (o, p) -> o.chargeTime = p.chargeTime).documentation("The amount of time to increase the current charge time by").add()).appendInherited(new KeyedCodec("Charge", (Codec)Codec.INTEGER), (o, i) -> o.charge = i.intValue(), o -> Integer.valueOf(o.charge), (o, p) -> o.charge = p.charge).documentation("The amount of empty charges to recharge").add()).appendInherited(new KeyedCodec("InterruptRecharge", (Codec)Codec.BOOLEAN), (o, i) -> o.interruptRecharge = i.booleanValue(), o -> Boolean.valueOf(o.interruptRecharge), (o, p) -> o.interruptRecharge = p.interruptRecharge).documentation("Determines whether the recharge of this cooldown should be interrupted").add()).afterDecode(interaction -> interaction.chargeTime = -interaction.chargeTime)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  80 */     String cooldownId = this.cooldown;
/*     */     
/*  82 */     if (StringUtil.isNullOrEmpty(cooldownId)) {
/*  83 */       InteractionCooldown rootCooldown = context.getChain().getRootInteraction().getCooldown();
/*     */       
/*  85 */       if (rootCooldown != null) {
/*  86 */         cooldownId = rootCooldown.cooldownId;
/*     */       }
/*     */     } 
/*     */     
/*  90 */     processCooldown(cooldownHandler, cooldownId);
/*  91 */     (context.getState()).state = InteractionState.Finished;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processCooldown(@Nonnull CooldownHandler cooldownHandler, @Nonnull String cooldownId) {
/* 101 */     CooldownHandler.Cooldown cooldown = cooldownHandler.getCooldown(cooldownId);
/* 102 */     if (cooldown == null) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     if (this.cooldownTime != 0.0F) {
/* 107 */       cooldown.increaseTime(this.cooldownTime);
/*     */     }
/*     */     
/* 110 */     if (this.charge != 0) {
/* 111 */       cooldown.replenishCharge(this.charge, this.interruptRecharge);
/*     */     }
/*     */     
/* 114 */     if (this.chargeTime != 0.0F) {
/* 115 */       cooldown.increaseChargeTime(this.chargeTime);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 122 */     return (Interaction)new com.hypixel.hytale.protocol.IncrementCooldownInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 127 */     super.configurePacket(packet);
/* 128 */     com.hypixel.hytale.protocol.IncrementCooldownInteraction p = (com.hypixel.hytale.protocol.IncrementCooldownInteraction)packet;
/* 129 */     p.cooldownId = this.cooldown;
/* 130 */     p.cooldownIncrementTime = this.cooldownTime;
/* 131 */     p.cooldownIncrementCharge = this.charge;
/* 132 */     p.cooldownIncrementChargeTime = this.chargeTime;
/* 133 */     p.cooldownIncrementInterrupt = this.interruptRecharge;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "IncrementCooldownInteraction{} " + super
/* 140 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\IncrementCooldownInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */