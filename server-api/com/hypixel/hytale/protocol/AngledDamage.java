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
/*     */ public class AngledDamage
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public double angle;
/*     */   public double angleDistance;
/*     */   @Nullable
/*     */   public DamageEffects damageEffects;
/*     */   public int next;
/*     */   
/*     */   public AngledDamage() {}
/*     */   
/*     */   public AngledDamage(double angle, double angleDistance, @Nullable DamageEffects damageEffects, int next) {
/*  29 */     this.angle = angle;
/*  30 */     this.angleDistance = angleDistance;
/*  31 */     this.damageEffects = damageEffects;
/*  32 */     this.next = next;
/*     */   }
/*     */   
/*     */   public AngledDamage(@Nonnull AngledDamage other) {
/*  36 */     this.angle = other.angle;
/*  37 */     this.angleDistance = other.angleDistance;
/*  38 */     this.damageEffects = other.damageEffects;
/*  39 */     this.next = other.next;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AngledDamage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     AngledDamage obj = new AngledDamage();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.angle = buf.getDoubleLE(offset + 1);
/*  47 */     obj.angleDistance = buf.getDoubleLE(offset + 9);
/*  48 */     obj.next = buf.getIntLE(offset + 17);
/*     */     
/*  50 */     int pos = offset + 21;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.damageEffects = DamageEffects.deserialize(buf, pos);
/*  52 */       pos += DamageEffects.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 21;
/*  60 */     if ((nullBits & 0x1) != 0) pos += DamageEffects.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.damageEffects != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeDoubleLE(this.angle);
/*  71 */     buf.writeDoubleLE(this.angleDistance);
/*  72 */     buf.writeIntLE(this.next);
/*     */     
/*  74 */     if (this.damageEffects != null) this.damageEffects.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 21;
/*  80 */     if (this.damageEffects != null) size += this.damageEffects.computeSize();
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 21) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 21;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       ValidationResult damageEffectsResult = DamageEffects.validateStructure(buffer, pos);
/*  96 */       if (!damageEffectsResult.isValid()) {
/*  97 */         return ValidationResult.error("Invalid DamageEffects: " + damageEffectsResult.error());
/*     */       }
/*  99 */       pos += DamageEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 101 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AngledDamage clone() {
/* 105 */     AngledDamage copy = new AngledDamage();
/* 106 */     copy.angle = this.angle;
/* 107 */     copy.angleDistance = this.angleDistance;
/* 108 */     copy.damageEffects = (this.damageEffects != null) ? this.damageEffects.clone() : null;
/* 109 */     copy.next = this.next;
/* 110 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AngledDamage other;
/* 116 */     if (this == obj) return true; 
/* 117 */     if (obj instanceof AngledDamage) { other = (AngledDamage)obj; } else { return false; }
/* 118 */      return (this.angle == other.angle && this.angleDistance == other.angleDistance && Objects.equals(this.damageEffects, other.damageEffects) && this.next == other.next);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     return Objects.hash(new Object[] { Double.valueOf(this.angle), Double.valueOf(this.angleDistance), this.damageEffects, Integer.valueOf(this.next) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AngledDamage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */