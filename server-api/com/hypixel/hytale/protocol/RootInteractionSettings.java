/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RootInteractionSettings
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 32768028;
/*     */   public boolean allowSkipChainOnClick;
/*     */   @Nullable
/*     */   public InteractionCooldown cooldown;
/*     */   
/*     */   public RootInteractionSettings() {}
/*     */   
/*     */   public RootInteractionSettings(boolean allowSkipChainOnClick, @Nullable InteractionCooldown cooldown) {
/*  27 */     this.allowSkipChainOnClick = allowSkipChainOnClick;
/*  28 */     this.cooldown = cooldown;
/*     */   }
/*     */   
/*     */   public RootInteractionSettings(@Nonnull RootInteractionSettings other) {
/*  32 */     this.allowSkipChainOnClick = other.allowSkipChainOnClick;
/*  33 */     this.cooldown = other.cooldown;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RootInteractionSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     RootInteractionSettings obj = new RootInteractionSettings();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.allowSkipChainOnClick = (buf.getByte(offset + 1) != 0);
/*     */     
/*  42 */     int pos = offset + 2;
/*  43 */     if ((nullBits & 0x1) != 0) { obj.cooldown = InteractionCooldown.deserialize(buf, pos);
/*  44 */       pos += InteractionCooldown.computeBytesConsumed(buf, pos); }
/*     */     
/*  46 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     int pos = offset + 2;
/*  52 */     if ((nullBits & 0x1) != 0) pos += InteractionCooldown.computeBytesConsumed(buf, pos); 
/*  53 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  58 */     byte nullBits = 0;
/*  59 */     if (this.cooldown != null) nullBits = (byte)(nullBits | 0x1); 
/*  60 */     buf.writeByte(nullBits);
/*     */     
/*  62 */     buf.writeByte(this.allowSkipChainOnClick ? 1 : 0);
/*     */     
/*  64 */     if (this.cooldown != null) this.cooldown.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  69 */     int size = 2;
/*  70 */     if (this.cooldown != null) size += this.cooldown.computeSize();
/*     */     
/*  72 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 2) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  80 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  82 */     int pos = offset + 2;
/*     */     
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       ValidationResult cooldownResult = InteractionCooldown.validateStructure(buffer, pos);
/*  86 */       if (!cooldownResult.isValid()) {
/*  87 */         return ValidationResult.error("Invalid Cooldown: " + cooldownResult.error());
/*     */       }
/*  89 */       pos += InteractionCooldown.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  91 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RootInteractionSettings clone() {
/*  95 */     RootInteractionSettings copy = new RootInteractionSettings();
/*  96 */     copy.allowSkipChainOnClick = this.allowSkipChainOnClick;
/*  97 */     copy.cooldown = (this.cooldown != null) ? this.cooldown.clone() : null;
/*  98 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RootInteractionSettings other;
/* 104 */     if (this == obj) return true; 
/* 105 */     if (obj instanceof RootInteractionSettings) { other = (RootInteractionSettings)obj; } else { return false; }
/* 106 */      return (this.allowSkipChainOnClick == other.allowSkipChainOnClick && Objects.equals(this.cooldown, other.cooldown));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hash(new Object[] { Boolean.valueOf(this.allowSkipChainOnClick), this.cooldown });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RootInteractionSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */