/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Position;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ApplyKnockback implements Packet {
/*     */   public static final int PACKET_ID = 164;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 38;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 38;
/*     */   public static final int MAX_SIZE = 38;
/*     */   @Nullable
/*     */   public Position hitPosition;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public int getId() {
/*  26 */     return 164;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  33 */   public ChangeVelocityType changeType = ChangeVelocityType.Add;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplyKnockback(@Nullable Position hitPosition, float x, float y, float z, @Nonnull ChangeVelocityType changeType) {
/*  39 */     this.hitPosition = hitPosition;
/*  40 */     this.x = x;
/*  41 */     this.y = y;
/*  42 */     this.z = z;
/*  43 */     this.changeType = changeType;
/*     */   }
/*     */   
/*     */   public ApplyKnockback(@Nonnull ApplyKnockback other) {
/*  47 */     this.hitPosition = other.hitPosition;
/*  48 */     this.x = other.x;
/*  49 */     this.y = other.y;
/*  50 */     this.z = other.z;
/*  51 */     this.changeType = other.changeType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ApplyKnockback deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     ApplyKnockback obj = new ApplyKnockback();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     if ((nullBits & 0x1) != 0) obj.hitPosition = Position.deserialize(buf, offset + 1); 
/*  59 */     obj.x = buf.getFloatLE(offset + 25);
/*  60 */     obj.y = buf.getFloatLE(offset + 29);
/*  61 */     obj.z = buf.getFloatLE(offset + 33);
/*  62 */     obj.changeType = ChangeVelocityType.fromValue(buf.getByte(offset + 37));
/*     */ 
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     return 38;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.hitPosition != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */     
/*  78 */     if (this.hitPosition != null) { this.hitPosition.serialize(buf); } else { buf.writeZero(24); }
/*  79 */      buf.writeFloatLE(this.x);
/*  80 */     buf.writeFloatLE(this.y);
/*  81 */     buf.writeFloatLE(this.z);
/*  82 */     buf.writeByte(this.changeType.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  88 */     return 38;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  92 */     if (buffer.readableBytes() - offset < 38) {
/*  93 */       return ValidationResult.error("Buffer too small: expected at least 38 bytes");
/*     */     }
/*     */ 
/*     */     
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ApplyKnockback clone() {
/* 101 */     ApplyKnockback copy = new ApplyKnockback();
/* 102 */     copy.hitPosition = (this.hitPosition != null) ? this.hitPosition.clone() : null;
/* 103 */     copy.x = this.x;
/* 104 */     copy.y = this.y;
/* 105 */     copy.z = this.z;
/* 106 */     copy.changeType = this.changeType;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ApplyKnockback other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof ApplyKnockback) { other = (ApplyKnockback)obj; } else { return false; }
/* 115 */      return (Objects.equals(this.hitPosition, other.hitPosition) && this.x == other.x && this.y == other.y && this.z == other.z && Objects.equals(this.changeType, other.changeType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { this.hitPosition, Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z), this.changeType });
/*     */   }
/*     */   
/*     */   public ApplyKnockback() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\ApplyKnockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */