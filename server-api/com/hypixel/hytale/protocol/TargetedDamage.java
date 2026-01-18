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
/*     */ public class TargetedDamage
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int index;
/*     */   @Nullable
/*     */   public DamageEffects damageEffects;
/*     */   public int next;
/*     */   
/*     */   public TargetedDamage() {}
/*     */   
/*     */   public TargetedDamage(int index, @Nullable DamageEffects damageEffects, int next) {
/*  28 */     this.index = index;
/*  29 */     this.damageEffects = damageEffects;
/*  30 */     this.next = next;
/*     */   }
/*     */   
/*     */   public TargetedDamage(@Nonnull TargetedDamage other) {
/*  34 */     this.index = other.index;
/*  35 */     this.damageEffects = other.damageEffects;
/*  36 */     this.next = other.next;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static TargetedDamage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     TargetedDamage obj = new TargetedDamage();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.index = buf.getIntLE(offset + 1);
/*  44 */     obj.next = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.damageEffects = DamageEffects.deserialize(buf, pos);
/*  48 */       pos += DamageEffects.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 9;
/*  56 */     if ((nullBits & 0x1) != 0) pos += DamageEffects.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.damageEffects != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.index);
/*  67 */     buf.writeIntLE(this.next);
/*     */     
/*  69 */     if (this.damageEffects != null) this.damageEffects.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 9;
/*  75 */     if (this.damageEffects != null) size += this.damageEffects.computeSize();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 9) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  85 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  87 */     int pos = offset + 9;
/*     */     
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       ValidationResult damageEffectsResult = DamageEffects.validateStructure(buffer, pos);
/*  91 */       if (!damageEffectsResult.isValid()) {
/*  92 */         return ValidationResult.error("Invalid DamageEffects: " + damageEffectsResult.error());
/*     */       }
/*  94 */       pos += DamageEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  96 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public TargetedDamage clone() {
/* 100 */     TargetedDamage copy = new TargetedDamage();
/* 101 */     copy.index = this.index;
/* 102 */     copy.damageEffects = (this.damageEffects != null) ? this.damageEffects.clone() : null;
/* 103 */     copy.next = this.next;
/* 104 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     TargetedDamage other;
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj instanceof TargetedDamage) { other = (TargetedDamage)obj; } else { return false; }
/* 112 */      return (this.index == other.index && Objects.equals(this.damageEffects, other.damageEffects) && this.next == other.next);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { Integer.valueOf(this.index), this.damageEffects, Integer.valueOf(this.next) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\TargetedDamage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */