/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChargingDelay
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   public float minDelay;
/*     */   public float maxDelay;
/*     */   public float maxTotalDelay;
/*     */   public float minHealth;
/*     */   public float maxHealth;
/*     */   
/*     */   public ChargingDelay() {}
/*     */   
/*     */   public ChargingDelay(float minDelay, float maxDelay, float maxTotalDelay, float minHealth, float maxHealth) {
/*  30 */     this.minDelay = minDelay;
/*  31 */     this.maxDelay = maxDelay;
/*  32 */     this.maxTotalDelay = maxTotalDelay;
/*  33 */     this.minHealth = minHealth;
/*  34 */     this.maxHealth = maxHealth;
/*     */   }
/*     */   
/*     */   public ChargingDelay(@Nonnull ChargingDelay other) {
/*  38 */     this.minDelay = other.minDelay;
/*  39 */     this.maxDelay = other.maxDelay;
/*  40 */     this.maxTotalDelay = other.maxTotalDelay;
/*  41 */     this.minHealth = other.minHealth;
/*  42 */     this.maxHealth = other.maxHealth;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChargingDelay deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     ChargingDelay obj = new ChargingDelay();
/*     */     
/*  49 */     obj.minDelay = buf.getFloatLE(offset + 0);
/*  50 */     obj.maxDelay = buf.getFloatLE(offset + 4);
/*  51 */     obj.maxTotalDelay = buf.getFloatLE(offset + 8);
/*  52 */     obj.minHealth = buf.getFloatLE(offset + 12);
/*  53 */     obj.maxHealth = buf.getFloatLE(offset + 16);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     buf.writeFloatLE(this.minDelay);
/*  66 */     buf.writeFloatLE(this.maxDelay);
/*  67 */     buf.writeFloatLE(this.maxTotalDelay);
/*  68 */     buf.writeFloatLE(this.minHealth);
/*  69 */     buf.writeFloatLE(this.maxHealth);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  75 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  79 */     if (buffer.readableBytes() - offset < 20) {
/*  80 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  84 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChargingDelay clone() {
/*  88 */     ChargingDelay copy = new ChargingDelay();
/*  89 */     copy.minDelay = this.minDelay;
/*  90 */     copy.maxDelay = this.maxDelay;
/*  91 */     copy.maxTotalDelay = this.maxTotalDelay;
/*  92 */     copy.minHealth = this.minHealth;
/*  93 */     copy.maxHealth = this.maxHealth;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChargingDelay other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof ChargingDelay) { other = (ChargingDelay)obj; } else { return false; }
/* 102 */      return (this.minDelay == other.minDelay && this.maxDelay == other.maxDelay && this.maxTotalDelay == other.maxTotalDelay && this.minHealth == other.minHealth && this.maxHealth == other.maxHealth);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { Float.valueOf(this.minDelay), Float.valueOf(this.maxDelay), Float.valueOf(this.maxTotalDelay), Float.valueOf(this.minHealth), Float.valueOf(this.maxHealth) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ChargingDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */