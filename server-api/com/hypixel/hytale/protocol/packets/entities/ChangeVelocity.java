/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.VelocityConfig;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ChangeVelocity
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 163;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 35;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 35;
/*     */   public static final int MAX_SIZE = 35;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public int getId() {
/*  26 */     return 163;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  32 */   public ChangeVelocityType changeType = ChangeVelocityType.Add;
/*     */   
/*     */   @Nullable
/*     */   public VelocityConfig config;
/*     */ 
/*     */   
/*     */   public ChangeVelocity(float x, float y, float z, @Nonnull ChangeVelocityType changeType, @Nullable VelocityConfig config) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*  42 */     this.changeType = changeType;
/*  43 */     this.config = config;
/*     */   }
/*     */   
/*     */   public ChangeVelocity(@Nonnull ChangeVelocity other) {
/*  47 */     this.x = other.x;
/*  48 */     this.y = other.y;
/*  49 */     this.z = other.z;
/*  50 */     this.changeType = other.changeType;
/*  51 */     this.config = other.config;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ChangeVelocity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     ChangeVelocity obj = new ChangeVelocity();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.x = buf.getFloatLE(offset + 1);
/*  59 */     obj.y = buf.getFloatLE(offset + 5);
/*  60 */     obj.z = buf.getFloatLE(offset + 9);
/*  61 */     obj.changeType = ChangeVelocityType.fromValue(buf.getByte(offset + 13));
/*  62 */     if ((nullBits & 0x1) != 0) obj.config = VelocityConfig.deserialize(buf, offset + 14);
/*     */ 
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     return 35;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.config != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */     
/*  78 */     buf.writeFloatLE(this.x);
/*  79 */     buf.writeFloatLE(this.y);
/*  80 */     buf.writeFloatLE(this.z);
/*  81 */     buf.writeByte(this.changeType.getValue());
/*  82 */     if (this.config != null) { this.config.serialize(buf); } else { buf.writeZero(21); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  88 */     return 35;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  92 */     if (buffer.readableBytes() - offset < 35) {
/*  93 */       return ValidationResult.error("Buffer too small: expected at least 35 bytes");
/*     */     }
/*     */ 
/*     */     
/*  97 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ChangeVelocity clone() {
/* 101 */     ChangeVelocity copy = new ChangeVelocity();
/* 102 */     copy.x = this.x;
/* 103 */     copy.y = this.y;
/* 104 */     copy.z = this.z;
/* 105 */     copy.changeType = this.changeType;
/* 106 */     copy.config = (this.config != null) ? this.config.clone() : null;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ChangeVelocity other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof ChangeVelocity) { other = (ChangeVelocity)obj; } else { return false; }
/* 115 */      return (this.x == other.x && this.y == other.y && this.z == other.z && Objects.equals(this.changeType, other.changeType) && Objects.equals(this.config, other.config));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z), this.changeType, this.config });
/*     */   }
/*     */   
/*     */   public ChangeVelocity() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\ChangeVelocity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */