/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ParticleAttractor
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 85;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 85;
/*     */   public static final int MAX_SIZE = 85;
/*     */   @Nullable
/*     */   public Vector3f position;
/*     */   @Nullable
/*     */   public Vector3f radialAxis;
/*     */   public float trailPositionMultiplier;
/*     */   public float radius;
/*     */   public float radialAcceleration;
/*     */   public float radialTangentAcceleration;
/*     */   @Nullable
/*     */   public Vector3f linearAcceleration;
/*     */   public float radialImpulse;
/*     */   public float radialTangentImpulse;
/*     */   @Nullable
/*     */   public Vector3f linearImpulse;
/*     */   @Nullable
/*     */   public Vector3f dampingMultiplier;
/*     */   
/*     */   public ParticleAttractor() {}
/*     */   
/*     */   public ParticleAttractor(@Nullable Vector3f position, @Nullable Vector3f radialAxis, float trailPositionMultiplier, float radius, float radialAcceleration, float radialTangentAcceleration, @Nullable Vector3f linearAcceleration, float radialImpulse, float radialTangentImpulse, @Nullable Vector3f linearImpulse, @Nullable Vector3f dampingMultiplier) {
/*  36 */     this.position = position;
/*  37 */     this.radialAxis = radialAxis;
/*  38 */     this.trailPositionMultiplier = trailPositionMultiplier;
/*  39 */     this.radius = radius;
/*  40 */     this.radialAcceleration = radialAcceleration;
/*  41 */     this.radialTangentAcceleration = radialTangentAcceleration;
/*  42 */     this.linearAcceleration = linearAcceleration;
/*  43 */     this.radialImpulse = radialImpulse;
/*  44 */     this.radialTangentImpulse = radialTangentImpulse;
/*  45 */     this.linearImpulse = linearImpulse;
/*  46 */     this.dampingMultiplier = dampingMultiplier;
/*     */   }
/*     */   
/*     */   public ParticleAttractor(@Nonnull ParticleAttractor other) {
/*  50 */     this.position = other.position;
/*  51 */     this.radialAxis = other.radialAxis;
/*  52 */     this.trailPositionMultiplier = other.trailPositionMultiplier;
/*  53 */     this.radius = other.radius;
/*  54 */     this.radialAcceleration = other.radialAcceleration;
/*  55 */     this.radialTangentAcceleration = other.radialTangentAcceleration;
/*  56 */     this.linearAcceleration = other.linearAcceleration;
/*  57 */     this.radialImpulse = other.radialImpulse;
/*  58 */     this.radialTangentImpulse = other.radialTangentImpulse;
/*  59 */     this.linearImpulse = other.linearImpulse;
/*  60 */     this.dampingMultiplier = other.dampingMultiplier;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ParticleAttractor deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     ParticleAttractor obj = new ParticleAttractor();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     if ((nullBits & 0x1) != 0) obj.position = Vector3f.deserialize(buf, offset + 1); 
/*  68 */     if ((nullBits & 0x2) != 0) obj.radialAxis = Vector3f.deserialize(buf, offset + 13); 
/*  69 */     obj.trailPositionMultiplier = buf.getFloatLE(offset + 25);
/*  70 */     obj.radius = buf.getFloatLE(offset + 29);
/*  71 */     obj.radialAcceleration = buf.getFloatLE(offset + 33);
/*  72 */     obj.radialTangentAcceleration = buf.getFloatLE(offset + 37);
/*  73 */     if ((nullBits & 0x4) != 0) obj.linearAcceleration = Vector3f.deserialize(buf, offset + 41); 
/*  74 */     obj.radialImpulse = buf.getFloatLE(offset + 53);
/*  75 */     obj.radialTangentImpulse = buf.getFloatLE(offset + 57);
/*  76 */     if ((nullBits & 0x8) != 0) obj.linearImpulse = Vector3f.deserialize(buf, offset + 61); 
/*  77 */     if ((nullBits & 0x10) != 0) obj.dampingMultiplier = Vector3f.deserialize(buf, offset + 73);
/*     */ 
/*     */     
/*  80 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  84 */     return 85;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     if (this.radialAxis != null) nullBits = (byte)(nullBits | 0x2); 
/*  91 */     if (this.linearAcceleration != null) nullBits = (byte)(nullBits | 0x4); 
/*  92 */     if (this.linearImpulse != null) nullBits = (byte)(nullBits | 0x8); 
/*  93 */     if (this.dampingMultiplier != null) nullBits = (byte)(nullBits | 0x10); 
/*  94 */     buf.writeByte(nullBits);
/*     */     
/*  96 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(12); }
/*  97 */      if (this.radialAxis != null) { this.radialAxis.serialize(buf); } else { buf.writeZero(12); }
/*  98 */      buf.writeFloatLE(this.trailPositionMultiplier);
/*  99 */     buf.writeFloatLE(this.radius);
/* 100 */     buf.writeFloatLE(this.radialAcceleration);
/* 101 */     buf.writeFloatLE(this.radialTangentAcceleration);
/* 102 */     if (this.linearAcceleration != null) { this.linearAcceleration.serialize(buf); } else { buf.writeZero(12); }
/* 103 */      buf.writeFloatLE(this.radialImpulse);
/* 104 */     buf.writeFloatLE(this.radialTangentImpulse);
/* 105 */     if (this.linearImpulse != null) { this.linearImpulse.serialize(buf); } else { buf.writeZero(12); }
/* 106 */      if (this.dampingMultiplier != null) { this.dampingMultiplier.serialize(buf); } else { buf.writeZero(12); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 112 */     return 85;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 116 */     if (buffer.readableBytes() - offset < 85) {
/* 117 */       return ValidationResult.error("Buffer too small: expected at least 85 bytes");
/*     */     }
/*     */ 
/*     */     
/* 121 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ParticleAttractor clone() {
/* 125 */     ParticleAttractor copy = new ParticleAttractor();
/* 126 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 127 */     copy.radialAxis = (this.radialAxis != null) ? this.radialAxis.clone() : null;
/* 128 */     copy.trailPositionMultiplier = this.trailPositionMultiplier;
/* 129 */     copy.radius = this.radius;
/* 130 */     copy.radialAcceleration = this.radialAcceleration;
/* 131 */     copy.radialTangentAcceleration = this.radialTangentAcceleration;
/* 132 */     copy.linearAcceleration = (this.linearAcceleration != null) ? this.linearAcceleration.clone() : null;
/* 133 */     copy.radialImpulse = this.radialImpulse;
/* 134 */     copy.radialTangentImpulse = this.radialTangentImpulse;
/* 135 */     copy.linearImpulse = (this.linearImpulse != null) ? this.linearImpulse.clone() : null;
/* 136 */     copy.dampingMultiplier = (this.dampingMultiplier != null) ? this.dampingMultiplier.clone() : null;
/* 137 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ParticleAttractor other;
/* 143 */     if (this == obj) return true; 
/* 144 */     if (obj instanceof ParticleAttractor) { other = (ParticleAttractor)obj; } else { return false; }
/* 145 */      return (Objects.equals(this.position, other.position) && Objects.equals(this.radialAxis, other.radialAxis) && this.trailPositionMultiplier == other.trailPositionMultiplier && this.radius == other.radius && this.radialAcceleration == other.radialAcceleration && this.radialTangentAcceleration == other.radialTangentAcceleration && Objects.equals(this.linearAcceleration, other.linearAcceleration) && this.radialImpulse == other.radialImpulse && this.radialTangentImpulse == other.radialTangentImpulse && Objects.equals(this.linearImpulse, other.linearImpulse) && Objects.equals(this.dampingMultiplier, other.dampingMultiplier));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 150 */     return Objects.hash(new Object[] { this.position, this.radialAxis, Float.valueOf(this.trailPositionMultiplier), Float.valueOf(this.radius), Float.valueOf(this.radialAcceleration), Float.valueOf(this.radialTangentAcceleration), this.linearAcceleration, Float.valueOf(this.radialImpulse), Float.valueOf(this.radialTangentImpulse), this.linearImpulse, this.dampingMultiplier });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleAttractor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */