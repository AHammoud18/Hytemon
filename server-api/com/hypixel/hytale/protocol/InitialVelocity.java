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
/*     */ public class InitialVelocity
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 25;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 25;
/*     */   public static final int MAX_SIZE = 25;
/*     */   @Nullable
/*     */   public Rangef yaw;
/*     */   @Nullable
/*     */   public Rangef pitch;
/*     */   @Nullable
/*     */   public Rangef speed;
/*     */   
/*     */   public InitialVelocity() {}
/*     */   
/*     */   public InitialVelocity(@Nullable Rangef yaw, @Nullable Rangef pitch, @Nullable Rangef speed) {
/*  28 */     this.yaw = yaw;
/*  29 */     this.pitch = pitch;
/*  30 */     this.speed = speed;
/*     */   }
/*     */   
/*     */   public InitialVelocity(@Nonnull InitialVelocity other) {
/*  34 */     this.yaw = other.yaw;
/*  35 */     this.pitch = other.pitch;
/*  36 */     this.speed = other.speed;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InitialVelocity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     InitialVelocity obj = new InitialVelocity();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     if ((nullBits & 0x1) != 0) obj.yaw = Rangef.deserialize(buf, offset + 1); 
/*  44 */     if ((nullBits & 0x2) != 0) obj.pitch = Rangef.deserialize(buf, offset + 9); 
/*  45 */     if ((nullBits & 0x4) != 0) obj.speed = Rangef.deserialize(buf, offset + 17);
/*     */ 
/*     */     
/*  48 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  52 */     return 25;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  56 */     byte nullBits = 0;
/*  57 */     if (this.yaw != null) nullBits = (byte)(nullBits | 0x1); 
/*  58 */     if (this.pitch != null) nullBits = (byte)(nullBits | 0x2); 
/*  59 */     if (this.speed != null) nullBits = (byte)(nullBits | 0x4); 
/*  60 */     buf.writeByte(nullBits);
/*     */     
/*  62 */     if (this.yaw != null) { this.yaw.serialize(buf); } else { buf.writeZero(8); }
/*  63 */      if (this.pitch != null) { this.pitch.serialize(buf); } else { buf.writeZero(8); }
/*  64 */      if (this.speed != null) { this.speed.serialize(buf); } else { buf.writeZero(8); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  70 */     return 25;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  74 */     if (buffer.readableBytes() - offset < 25) {
/*  75 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*     */     }
/*     */ 
/*     */     
/*  79 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InitialVelocity clone() {
/*  83 */     InitialVelocity copy = new InitialVelocity();
/*  84 */     copy.yaw = (this.yaw != null) ? this.yaw.clone() : null;
/*  85 */     copy.pitch = (this.pitch != null) ? this.pitch.clone() : null;
/*  86 */     copy.speed = (this.speed != null) ? this.speed.clone() : null;
/*  87 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InitialVelocity other;
/*  93 */     if (this == obj) return true; 
/*  94 */     if (obj instanceof InitialVelocity) { other = (InitialVelocity)obj; } else { return false; }
/*  95 */      return (Objects.equals(this.yaw, other.yaw) && Objects.equals(this.pitch, other.pitch) && Objects.equals(this.speed, other.speed));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 100 */     return Objects.hash(new Object[] { this.yaw, this.pitch, this.speed });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InitialVelocity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */