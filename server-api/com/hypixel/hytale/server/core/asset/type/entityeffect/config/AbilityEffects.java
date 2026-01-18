/*    */ package com.hypixel.hytale.server.core.asset.type.entityeffect.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.AbilityEffects;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
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
/*    */ public class AbilityEffects
/*    */   implements NetworkSerializable<AbilityEffects>
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<AbilityEffects> CODEC;
/*    */   protected Set<InteractionType> disabled;
/*    */   
/*    */   static {
/* 35 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(AbilityEffects.class, AbilityEffects::new).appendInherited(new KeyedCodec("Disabled", (Codec)InteractionModule.INTERACTION_TYPE_SET_CODEC), (entityEffect, s) -> entityEffect.disabled = s, entityEffect -> entityEffect.disabled, (entityEffect, parent) -> entityEffect.disabled = parent.disabled).documentation("A collection of interaction types to become disabled while the entity effect affiliated with this ability effect is active").add()).build();
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
/*    */   
/*    */   public AbilityEffects(@Nonnull Set<InteractionType> disabled) {
/* 48 */     this.disabled = EnumSet.copyOf(disabled);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbilityEffects() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AbilityEffects toPacket() {
/* 62 */     AbilityEffects packet = new AbilityEffects();
/* 63 */     packet.disabled = (this.disabled == null) ? null : (InteractionType[])this.disabled.toArray(x$0 -> new InteractionType[x$0]);
/* 64 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "AbilityEffects{, disabled=" + String.valueOf(this.disabled) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\entityeffect\config\AbilityEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */