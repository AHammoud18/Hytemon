/*     */ package com.hypixel.hytale.server.core.asset.modifiers;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.ExtraInfo;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.MovementEffects;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class MovementEffects
/*     */   implements NetworkSerializable<MovementEffects>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<MovementEffects> CODEC;
/*     */   
/*     */   static {
/*  98 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(MovementEffects.class, MovementEffects::new).appendInherited(new KeyedCodec("DisableAll", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableAll = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableAll), (entityEffect, parent) -> entityEffect.disableAll = parent.disableAll).documentation("Determines whether all movement input is disabled").add()).appendInherited(new KeyedCodec("DisableForward", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableForward = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableForward), (entityEffect, parent) -> entityEffect.disableForward = parent.disableForward).documentation("Determines whether forwards movement input is disabled").add()).appendInherited(new KeyedCodec("DisableBackward", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableBackward = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableBackward), (entityEffect, parent) -> entityEffect.disableBackward = parent.disableBackward).documentation("Determines whether backwards movement input is disabled").add()).appendInherited(new KeyedCodec("DisableLeft", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableLeft = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableLeft), (entityEffect, parent) -> entityEffect.disableLeft = parent.disableLeft).documentation("Determines whether left-strafe movement input is disabled").add()).appendInherited(new KeyedCodec("DisableRight", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableRight = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableRight), (entityEffect, parent) -> entityEffect.disableRight = parent.disableRight).documentation("Determines whether right-strafe movement input is disabled").add()).appendInherited(new KeyedCodec("DisableSprint", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableSprint = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableSprint), (entityEffect, parent) -> entityEffect.disableSprint = parent.disableSprint).documentation("Determines whether sprint input is disabled").add()).appendInherited(new KeyedCodec("DisableJump", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableJump = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableJump), (entityEffect, parent) -> entityEffect.disableJump = parent.disableJump).documentation("Determines whether jump input is disabled").add()).appendInherited(new KeyedCodec("DisableCrouch", (Codec)Codec.BOOLEAN), (entityEffect, s) -> entityEffect.disableCrouch = s.booleanValue(), entityEffect -> Boolean.valueOf(entityEffect.disableCrouch), (entityEffect, parent) -> entityEffect.disableCrouch = parent.disableCrouch).documentation("Determines whether crouch input is disabled").add()).afterDecode((movementEffects, extraInfo) -> { if (movementEffects.disableAll) { movementEffects.disableForward = true; movementEffects.disableBackward = true; movementEffects.disableLeft = true; movementEffects.disableRight = true; movementEffects.disableSprint = true; movementEffects.disableJump = true; movementEffects.disableCrouch = true; }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableAll = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableForward = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableBackward = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableLeft = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableRight = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableSprint = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableJump = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean disableCrouch = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisableAll() {
/* 149 */     return this.disableAll;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MovementEffects toPacket() {
/* 155 */     MovementEffects packet = new MovementEffects();
/*     */     
/* 157 */     packet.disableForward = this.disableForward;
/* 158 */     packet.disableBackward = this.disableBackward;
/* 159 */     packet.disableLeft = this.disableLeft;
/* 160 */     packet.disableRight = this.disableRight;
/*     */     
/* 162 */     packet.disableSprint = this.disableSprint;
/* 163 */     packet.disableJump = this.disableJump;
/* 164 */     packet.disableCrouch = this.disableCrouch;
/*     */     
/* 166 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 172 */     return "MovementEffects{, disableAll=" + this.disableAll + ", disableForward=" + this.disableForward + ", disableBackward=" + this.disableBackward + ", disableLeft=" + this.disableLeft + ", disableRight=" + this.disableRight + ", disableSprint=" + this.disableSprint + ", disableJump=" + this.disableJump + ", disableCrouch=" + this.disableCrouch + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\modifiers\MovementEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */