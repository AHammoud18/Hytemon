/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ParticleAnimationFrame
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 58;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 58;
/*     */   public static final int MAX_SIZE = 58;
/*     */   @Nullable
/*     */   public Range frameIndex;
/*     */   @Nullable
/*     */   public RangeVector2f scale;
/*     */   @Nullable
/*     */   public RangeVector3f rotation;
/*     */   @Nullable
/*     */   public Color color;
/*     */   public float opacity;
/*     */   
/*     */   public ParticleAnimationFrame() {}
/*     */   
/*     */   public ParticleAnimationFrame(@Nullable Range frameIndex, @Nullable RangeVector2f scale, @Nullable RangeVector3f rotation, @Nullable Color color, float opacity) {
/*  30 */     this.frameIndex = frameIndex;
/*  31 */     this.scale = scale;
/*  32 */     this.rotation = rotation;
/*  33 */     this.color = color;
/*  34 */     this.opacity = opacity;
/*     */   }
/*     */   
/*     */   public ParticleAnimationFrame(@Nonnull ParticleAnimationFrame other) {
/*  38 */     this.frameIndex = other.frameIndex;
/*  39 */     this.scale = other.scale;
/*  40 */     this.rotation = other.rotation;
/*  41 */     this.color = other.color;
/*  42 */     this.opacity = other.opacity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ParticleAnimationFrame deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     ParticleAnimationFrame obj = new ParticleAnimationFrame();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     if ((nullBits & 0x1) != 0) obj.frameIndex = Range.deserialize(buf, offset + 1); 
/*  50 */     if ((nullBits & 0x2) != 0) obj.scale = RangeVector2f.deserialize(buf, offset + 9); 
/*  51 */     if ((nullBits & 0x4) != 0) obj.rotation = RangeVector3f.deserialize(buf, offset + 26); 
/*  52 */     if ((nullBits & 0x8) != 0) obj.color = Color.deserialize(buf, offset + 51); 
/*  53 */     obj.opacity = buf.getFloatLE(offset + 54);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 58;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  64 */     byte nullBits = 0;
/*  65 */     if (this.frameIndex != null) nullBits = (byte)(nullBits | 0x1); 
/*  66 */     if (this.scale != null) nullBits = (byte)(nullBits | 0x2); 
/*  67 */     if (this.rotation != null) nullBits = (byte)(nullBits | 0x4); 
/*  68 */     if (this.color != null) nullBits = (byte)(nullBits | 0x8); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     if (this.frameIndex != null) { this.frameIndex.serialize(buf); } else { buf.writeZero(8); }
/*  72 */      if (this.scale != null) { this.scale.serialize(buf); } else { buf.writeZero(17); }
/*  73 */      if (this.rotation != null) { this.rotation.serialize(buf); } else { buf.writeZero(25); }
/*  74 */      if (this.color != null) { this.color.serialize(buf); } else { buf.writeZero(3); }
/*  75 */      buf.writeFloatLE(this.opacity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  81 */     return 58;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 58) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 58 bytes");
/*     */     }
/*     */ 
/*     */     
/*  90 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ParticleAnimationFrame clone() {
/*  94 */     ParticleAnimationFrame copy = new ParticleAnimationFrame();
/*  95 */     copy.frameIndex = (this.frameIndex != null) ? this.frameIndex.clone() : null;
/*  96 */     copy.scale = (this.scale != null) ? this.scale.clone() : null;
/*  97 */     copy.rotation = (this.rotation != null) ? this.rotation.clone() : null;
/*  98 */     copy.color = (this.color != null) ? this.color.clone() : null;
/*  99 */     copy.opacity = this.opacity;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ParticleAnimationFrame other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof ParticleAnimationFrame) { other = (ParticleAnimationFrame)obj; } else { return false; }
/* 108 */      return (Objects.equals(this.frameIndex, other.frameIndex) && Objects.equals(this.scale, other.scale) && Objects.equals(this.rotation, other.rotation) && Objects.equals(this.color, other.color) && this.opacity == other.opacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { this.frameIndex, this.scale, this.rotation, this.color, Float.valueOf(this.opacity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleAnimationFrame.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */