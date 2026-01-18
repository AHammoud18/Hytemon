/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class UpdatePostFxSettings
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 361;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   public float globalIntensity;
/*     */   public float power;
/*     */   public float sunshaftScale;
/*     */   public float sunIntensity;
/*     */   public float sunshaftIntensity;
/*     */   
/*     */   public int getId() {
/*  25 */     return 361;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePostFxSettings() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePostFxSettings(float globalIntensity, float power, float sunshaftScale, float sunIntensity, float sunshaftIntensity) {
/*  38 */     this.globalIntensity = globalIntensity;
/*  39 */     this.power = power;
/*  40 */     this.sunshaftScale = sunshaftScale;
/*  41 */     this.sunIntensity = sunIntensity;
/*  42 */     this.sunshaftIntensity = sunshaftIntensity;
/*     */   }
/*     */   
/*     */   public UpdatePostFxSettings(@Nonnull UpdatePostFxSettings other) {
/*  46 */     this.globalIntensity = other.globalIntensity;
/*  47 */     this.power = other.power;
/*  48 */     this.sunshaftScale = other.sunshaftScale;
/*  49 */     this.sunIntensity = other.sunIntensity;
/*  50 */     this.sunshaftIntensity = other.sunshaftIntensity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdatePostFxSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     UpdatePostFxSettings obj = new UpdatePostFxSettings();
/*     */     
/*  57 */     obj.globalIntensity = buf.getFloatLE(offset + 0);
/*  58 */     obj.power = buf.getFloatLE(offset + 4);
/*  59 */     obj.sunshaftScale = buf.getFloatLE(offset + 8);
/*  60 */     obj.sunIntensity = buf.getFloatLE(offset + 12);
/*  61 */     obj.sunshaftIntensity = buf.getFloatLE(offset + 16);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     buf.writeFloatLE(this.globalIntensity);
/*  75 */     buf.writeFloatLE(this.power);
/*  76 */     buf.writeFloatLE(this.sunshaftScale);
/*  77 */     buf.writeFloatLE(this.sunIntensity);
/*  78 */     buf.writeFloatLE(this.sunshaftIntensity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  88 */     if (buffer.readableBytes() - offset < 20) {
/*  89 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdatePostFxSettings clone() {
/*  97 */     UpdatePostFxSettings copy = new UpdatePostFxSettings();
/*  98 */     copy.globalIntensity = this.globalIntensity;
/*  99 */     copy.power = this.power;
/* 100 */     copy.sunshaftScale = this.sunshaftScale;
/* 101 */     copy.sunIntensity = this.sunIntensity;
/* 102 */     copy.sunshaftIntensity = this.sunshaftIntensity;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdatePostFxSettings other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof UpdatePostFxSettings) { other = (UpdatePostFxSettings)obj; } else { return false; }
/* 111 */      return (this.globalIntensity == other.globalIntensity && this.power == other.power && this.sunshaftScale == other.sunshaftScale && this.sunIntensity == other.sunIntensity && this.sunshaftIntensity == other.sunshaftIntensity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { Float.valueOf(this.globalIntensity), Float.valueOf(this.power), Float.valueOf(this.sunshaftScale), Float.valueOf(this.sunIntensity), Float.valueOf(this.sunshaftIntensity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdatePostFxSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */