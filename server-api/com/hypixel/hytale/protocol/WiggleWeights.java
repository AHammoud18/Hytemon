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
/*     */ public class WiggleWeights
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 40;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 40;
/*     */   public static final int MAX_SIZE = 40;
/*     */   public float x;
/*     */   public float xDeceleration;
/*     */   public float y;
/*     */   public float yDeceleration;
/*     */   public float z;
/*     */   public float zDeceleration;
/*     */   public float roll;
/*     */   public float rollDeceleration;
/*     */   public float pitch;
/*     */   public float pitchDeceleration;
/*     */   
/*     */   public WiggleWeights() {}
/*     */   
/*     */   public WiggleWeights(float x, float xDeceleration, float y, float yDeceleration, float z, float zDeceleration, float roll, float rollDeceleration, float pitch, float pitchDeceleration) {
/*  35 */     this.x = x;
/*  36 */     this.xDeceleration = xDeceleration;
/*  37 */     this.y = y;
/*  38 */     this.yDeceleration = yDeceleration;
/*  39 */     this.z = z;
/*  40 */     this.zDeceleration = zDeceleration;
/*  41 */     this.roll = roll;
/*  42 */     this.rollDeceleration = rollDeceleration;
/*  43 */     this.pitch = pitch;
/*  44 */     this.pitchDeceleration = pitchDeceleration;
/*     */   }
/*     */   
/*     */   public WiggleWeights(@Nonnull WiggleWeights other) {
/*  48 */     this.x = other.x;
/*  49 */     this.xDeceleration = other.xDeceleration;
/*  50 */     this.y = other.y;
/*  51 */     this.yDeceleration = other.yDeceleration;
/*  52 */     this.z = other.z;
/*  53 */     this.zDeceleration = other.zDeceleration;
/*  54 */     this.roll = other.roll;
/*  55 */     this.rollDeceleration = other.rollDeceleration;
/*  56 */     this.pitch = other.pitch;
/*  57 */     this.pitchDeceleration = other.pitchDeceleration;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WiggleWeights deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     WiggleWeights obj = new WiggleWeights();
/*     */     
/*  64 */     obj.x = buf.getFloatLE(offset + 0);
/*  65 */     obj.xDeceleration = buf.getFloatLE(offset + 4);
/*  66 */     obj.y = buf.getFloatLE(offset + 8);
/*  67 */     obj.yDeceleration = buf.getFloatLE(offset + 12);
/*  68 */     obj.z = buf.getFloatLE(offset + 16);
/*  69 */     obj.zDeceleration = buf.getFloatLE(offset + 20);
/*  70 */     obj.roll = buf.getFloatLE(offset + 24);
/*  71 */     obj.rollDeceleration = buf.getFloatLE(offset + 28);
/*  72 */     obj.pitch = buf.getFloatLE(offset + 32);
/*  73 */     obj.pitchDeceleration = buf.getFloatLE(offset + 36);
/*     */ 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     return 40;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     buf.writeFloatLE(this.x);
/*  86 */     buf.writeFloatLE(this.xDeceleration);
/*  87 */     buf.writeFloatLE(this.y);
/*  88 */     buf.writeFloatLE(this.yDeceleration);
/*  89 */     buf.writeFloatLE(this.z);
/*  90 */     buf.writeFloatLE(this.zDeceleration);
/*  91 */     buf.writeFloatLE(this.roll);
/*  92 */     buf.writeFloatLE(this.rollDeceleration);
/*  93 */     buf.writeFloatLE(this.pitch);
/*  94 */     buf.writeFloatLE(this.pitchDeceleration);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 100 */     return 40;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 104 */     if (buffer.readableBytes() - offset < 40) {
/* 105 */       return ValidationResult.error("Buffer too small: expected at least 40 bytes");
/*     */     }
/*     */ 
/*     */     
/* 109 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WiggleWeights clone() {
/* 113 */     WiggleWeights copy = new WiggleWeights();
/* 114 */     copy.x = this.x;
/* 115 */     copy.xDeceleration = this.xDeceleration;
/* 116 */     copy.y = this.y;
/* 117 */     copy.yDeceleration = this.yDeceleration;
/* 118 */     copy.z = this.z;
/* 119 */     copy.zDeceleration = this.zDeceleration;
/* 120 */     copy.roll = this.roll;
/* 121 */     copy.rollDeceleration = this.rollDeceleration;
/* 122 */     copy.pitch = this.pitch;
/* 123 */     copy.pitchDeceleration = this.pitchDeceleration;
/* 124 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WiggleWeights other;
/* 130 */     if (this == obj) return true; 
/* 131 */     if (obj instanceof WiggleWeights) { other = (WiggleWeights)obj; } else { return false; }
/* 132 */      return (this.x == other.x && this.xDeceleration == other.xDeceleration && this.y == other.y && this.yDeceleration == other.yDeceleration && this.z == other.z && this.zDeceleration == other.zDeceleration && this.roll == other.roll && this.rollDeceleration == other.rollDeceleration && this.pitch == other.pitch && this.pitchDeceleration == other.pitchDeceleration);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 137 */     return Objects.hash(new Object[] { Float.valueOf(this.x), Float.valueOf(this.xDeceleration), Float.valueOf(this.y), Float.valueOf(this.yDeceleration), Float.valueOf(this.z), Float.valueOf(this.zDeceleration), Float.valueOf(this.roll), Float.valueOf(this.rollDeceleration), Float.valueOf(this.pitch), Float.valueOf(this.pitchDeceleration) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\WiggleWeights.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */