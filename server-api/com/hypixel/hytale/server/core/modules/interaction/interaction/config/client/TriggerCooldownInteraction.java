/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.client;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionCooldown;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
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
/*     */ public class TriggerCooldownInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<TriggerCooldownInteraction> CODEC;
/*     */   @Nullable
/*     */   private InteractionCooldown cooldown;
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TriggerCooldownInteraction.class, TriggerCooldownInteraction::new, SimpleInstantInteraction.CODEC).documentation("Triggers the cooldown as if it was triggered normally.")).appendInherited(new KeyedCodec("Cooldown", (Codec)RootInteraction.COOLDOWN_CODEC), (interaction, s) -> interaction.cooldown = s, interaction -> interaction.cooldown, (interaction, parent) -> interaction.next = parent.next).documentation("The cooldown concerning this interaction, defaulting to the root cooldown if none presented").add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  48 */     String cooldownId = null;
/*  49 */     float cooldownTime = 0.0F;
/*  50 */     float[] charges = null;
/*  51 */     boolean interruptRecharge = false;
/*     */     
/*  53 */     if (this.cooldown != null) {
/*  54 */       cooldownId = this.cooldown.cooldownId;
/*  55 */       cooldownTime = this.cooldown.cooldown;
/*  56 */       charges = this.cooldown.chargeTimes;
/*  57 */       interruptRecharge = this.cooldown.interruptRecharge;
/*     */     } 
/*     */     
/*  60 */     resetCooldown(context, cooldownHandler, cooldownId, cooldownTime, charges, interruptRecharge);
/*     */   }
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
/*     */   protected static void resetCooldown(@Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler, @Nullable String cooldownId, float cooldownTime, @Nullable float[] chargeTimes, boolean interruptRecharge0) {
/*  76 */     float time = 0.35F;
/*  77 */     float[] charges = InteractionManager.DEFAULT_CHARGE_TIMES;
/*  78 */     boolean interruptRecharge = false;
/*     */ 
/*     */     
/*  81 */     if (cooldownId == null) {
/*  82 */       InteractionChain chain = context.getChain();
/*  83 */       assert chain != null;
/*     */       
/*  85 */       RootInteraction rootInteraction = chain.getInitialRootInteraction();
/*  86 */       InteractionCooldown rootCooldown = rootInteraction.getCooldown();
/*     */ 
/*     */       
/*  89 */       if (rootCooldown != null) {
/*  90 */         cooldownId = rootCooldown.cooldownId;
/*     */         
/*  92 */         if (rootCooldown.cooldown > 0.0F) {
/*  93 */           time = rootCooldown.cooldown;
/*     */         }
/*     */         
/*  96 */         if (rootCooldown.interruptRecharge) {
/*  97 */           interruptRecharge = true;
/*     */         }
/*     */         
/* 100 */         if (rootCooldown.chargeTimes != null && rootCooldown.chargeTimes.length > 0) {
/* 101 */           charges = rootCooldown.chargeTimes;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 106 */       if (cooldownId == null) {
/* 107 */         cooldownId = rootInteraction.getId();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 112 */     CooldownHandler.Cooldown possibleCooldown = cooldownHandler.getCooldown(cooldownId);
/*     */ 
/*     */     
/* 115 */     if (possibleCooldown != null) {
/* 116 */       time = possibleCooldown.getCooldown();
/* 117 */       charges = possibleCooldown.getCharges();
/* 118 */       interruptRecharge = possibleCooldown.interruptRecharge();
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (cooldownTime > 0.0F) {
/* 123 */       time = cooldownTime;
/*     */     }
/*     */ 
/*     */     
/* 127 */     if (chargeTimes != null && chargeTimes.length > 0) {
/* 128 */       charges = chargeTimes;
/*     */     }
/*     */     
/* 131 */     if (interruptRecharge0) {
/* 132 */       interruptRecharge = true;
/*     */     }
/*     */ 
/*     */     
/* 136 */     CooldownHandler.Cooldown cooldown = cooldownHandler.getCooldown(cooldownId, time, charges, true, interruptRecharge);
/*     */ 
/*     */     
/* 139 */     cooldown.setCooldownMax(time);
/* 140 */     cooldown.setCharges(charges);
/*     */ 
/*     */     
/* 143 */     cooldown.deductCharge();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 149 */     return (Interaction)new com.hypixel.hytale.protocol.TriggerCooldownInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 154 */     super.configurePacket(packet);
/* 155 */     com.hypixel.hytale.protocol.TriggerCooldownInteraction p = (com.hypixel.hytale.protocol.TriggerCooldownInteraction)packet;
/* 156 */     p.cooldown = this.cooldown;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 162 */     return "TriggerCooldownInteraction{} " + super
/* 163 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\client\TriggerCooldownInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */