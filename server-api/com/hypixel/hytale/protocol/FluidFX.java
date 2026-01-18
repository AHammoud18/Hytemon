/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class FluidFX {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 61;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 69;
/*     */   public static final int MAX_SIZE = 32768087;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nonnull
/*  21 */   public ShaderType shader = ShaderType.None; @Nonnull
/*  22 */   public FluidFog fogMode = FluidFog.Color;
/*     */   
/*     */   @Nullable
/*     */   public Color fogColor;
/*     */   
/*     */   @Nullable
/*     */   public NearFar fogDistance;
/*     */   
/*     */   public float fogDepthStart;
/*     */   
/*     */   public float fogDepthFalloff;
/*     */   
/*     */   @Nullable
/*     */   public Color colorFilter;
/*     */   
/*     */   public FluidFX(@Nullable String id, @Nonnull ShaderType shader, @Nonnull FluidFog fogMode, @Nullable Color fogColor, @Nullable NearFar fogDistance, float fogDepthStart, float fogDepthFalloff, @Nullable Color colorFilter, float colorSaturation, float distortionAmplitude, float distortionFrequency, @Nullable FluidParticle particle, @Nullable FluidFXMovementSettings movementSettings) {
/*  38 */     this.id = id;
/*  39 */     this.shader = shader;
/*  40 */     this.fogMode = fogMode;
/*  41 */     this.fogColor = fogColor;
/*  42 */     this.fogDistance = fogDistance;
/*  43 */     this.fogDepthStart = fogDepthStart;
/*  44 */     this.fogDepthFalloff = fogDepthFalloff;
/*  45 */     this.colorFilter = colorFilter;
/*  46 */     this.colorSaturation = colorSaturation;
/*  47 */     this.distortionAmplitude = distortionAmplitude;
/*  48 */     this.distortionFrequency = distortionFrequency;
/*  49 */     this.particle = particle;
/*  50 */     this.movementSettings = movementSettings;
/*     */   } public float colorSaturation; public float distortionAmplitude; public float distortionFrequency; @Nullable
/*     */   public FluidParticle particle; @Nullable
/*     */   public FluidFXMovementSettings movementSettings; public FluidFX(@Nonnull FluidFX other) {
/*  54 */     this.id = other.id;
/*  55 */     this.shader = other.shader;
/*  56 */     this.fogMode = other.fogMode;
/*  57 */     this.fogColor = other.fogColor;
/*  58 */     this.fogDistance = other.fogDistance;
/*  59 */     this.fogDepthStart = other.fogDepthStart;
/*  60 */     this.fogDepthFalloff = other.fogDepthFalloff;
/*  61 */     this.colorFilter = other.colorFilter;
/*  62 */     this.colorSaturation = other.colorSaturation;
/*  63 */     this.distortionAmplitude = other.distortionAmplitude;
/*  64 */     this.distortionFrequency = other.distortionFrequency;
/*  65 */     this.particle = other.particle;
/*  66 */     this.movementSettings = other.movementSettings;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FluidFX deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     FluidFX obj = new FluidFX();
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     obj.shader = ShaderType.fromValue(buf.getByte(offset + 1));
/*  74 */     obj.fogMode = FluidFog.fromValue(buf.getByte(offset + 2));
/*  75 */     if ((nullBits & 0x2) != 0) obj.fogColor = Color.deserialize(buf, offset + 3); 
/*  76 */     if ((nullBits & 0x4) != 0) obj.fogDistance = NearFar.deserialize(buf, offset + 6); 
/*  77 */     obj.fogDepthStart = buf.getFloatLE(offset + 14);
/*  78 */     obj.fogDepthFalloff = buf.getFloatLE(offset + 18);
/*  79 */     if ((nullBits & 0x8) != 0) obj.colorFilter = Color.deserialize(buf, offset + 22); 
/*  80 */     obj.colorSaturation = buf.getFloatLE(offset + 25);
/*  81 */     obj.distortionAmplitude = buf.getFloatLE(offset + 29);
/*  82 */     obj.distortionFrequency = buf.getFloatLE(offset + 33);
/*  83 */     if ((nullBits & 0x20) != 0) obj.movementSettings = FluidFXMovementSettings.deserialize(buf, offset + 37);
/*     */     
/*  85 */     if ((nullBits & 0x1) != 0) {
/*  86 */       int varPos0 = offset + 69 + buf.getIntLE(offset + 61);
/*  87 */       int idLen = VarInt.peek(buf, varPos0);
/*  88 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  89 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  90 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits & 0x10) != 0) {
/*  93 */       int varPos1 = offset + 69 + buf.getIntLE(offset + 65);
/*  94 */       obj.particle = FluidParticle.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  97 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 101 */     byte nullBits = buf.getByte(offset);
/* 102 */     int maxEnd = 69;
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int fieldOffset0 = buf.getIntLE(offset + 61);
/* 105 */       int pos0 = offset + 69 + fieldOffset0;
/* 106 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 107 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 109 */     if ((nullBits & 0x10) != 0) {
/* 110 */       int fieldOffset1 = buf.getIntLE(offset + 65);
/* 111 */       int pos1 = offset + 69 + fieldOffset1;
/* 112 */       pos1 += FluidParticle.computeBytesConsumed(buf, pos1);
/* 113 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 115 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 120 */     int startPos = buf.writerIndex();
/* 121 */     byte nullBits = 0;
/* 122 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 123 */     if (this.fogColor != null) nullBits = (byte)(nullBits | 0x2); 
/* 124 */     if (this.fogDistance != null) nullBits = (byte)(nullBits | 0x4); 
/* 125 */     if (this.colorFilter != null) nullBits = (byte)(nullBits | 0x8); 
/* 126 */     if (this.particle != null) nullBits = (byte)(nullBits | 0x10); 
/* 127 */     if (this.movementSettings != null) nullBits = (byte)(nullBits | 0x20); 
/* 128 */     buf.writeByte(nullBits);
/*     */     
/* 130 */     buf.writeByte(this.shader.getValue());
/* 131 */     buf.writeByte(this.fogMode.getValue());
/* 132 */     if (this.fogColor != null) { this.fogColor.serialize(buf); } else { buf.writeZero(3); }
/* 133 */      if (this.fogDistance != null) { this.fogDistance.serialize(buf); } else { buf.writeZero(8); }
/* 134 */      buf.writeFloatLE(this.fogDepthStart);
/* 135 */     buf.writeFloatLE(this.fogDepthFalloff);
/* 136 */     if (this.colorFilter != null) { this.colorFilter.serialize(buf); } else { buf.writeZero(3); }
/* 137 */      buf.writeFloatLE(this.colorSaturation);
/* 138 */     buf.writeFloatLE(this.distortionAmplitude);
/* 139 */     buf.writeFloatLE(this.distortionFrequency);
/* 140 */     if (this.movementSettings != null) { this.movementSettings.serialize(buf); } else { buf.writeZero(24); }
/*     */     
/* 142 */     int idOffsetSlot = buf.writerIndex();
/* 143 */     buf.writeIntLE(0);
/* 144 */     int particleOffsetSlot = buf.writerIndex();
/* 145 */     buf.writeIntLE(0);
/*     */     
/* 147 */     int varBlockStart = buf.writerIndex();
/* 148 */     if (this.id != null) {
/* 149 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 150 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 152 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 154 */     if (this.particle != null) {
/* 155 */       buf.setIntLE(particleOffsetSlot, buf.writerIndex() - varBlockStart);
/* 156 */       this.particle.serialize(buf);
/*     */     } else {
/* 158 */       buf.setIntLE(particleOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 164 */     int size = 69;
/* 165 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 166 */     if (this.particle != null) size += this.particle.computeSize();
/*     */     
/* 168 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 172 */     if (buffer.readableBytes() - offset < 69) {
/* 173 */       return ValidationResult.error("Buffer too small: expected at least 69 bytes");
/*     */     }
/*     */     
/* 176 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 179 */     if ((nullBits & 0x1) != 0) {
/* 180 */       int idOffset = buffer.getIntLE(offset + 61);
/* 181 */       if (idOffset < 0) {
/* 182 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 184 */       int pos = offset + 69 + idOffset;
/* 185 */       if (pos >= buffer.writerIndex()) {
/* 186 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 188 */       int idLen = VarInt.peek(buffer, pos);
/* 189 */       if (idLen < 0) {
/* 190 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 192 */       if (idLen > 4096000) {
/* 193 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 195 */       pos += VarInt.length(buffer, pos);
/* 196 */       pos += idLen;
/* 197 */       if (pos > buffer.writerIndex()) {
/* 198 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 202 */     if ((nullBits & 0x10) != 0) {
/* 203 */       int particleOffset = buffer.getIntLE(offset + 65);
/* 204 */       if (particleOffset < 0) {
/* 205 */         return ValidationResult.error("Invalid offset for Particle");
/*     */       }
/* 207 */       int pos = offset + 69 + particleOffset;
/* 208 */       if (pos >= buffer.writerIndex()) {
/* 209 */         return ValidationResult.error("Offset out of bounds for Particle");
/*     */       }
/* 211 */       ValidationResult particleResult = FluidParticle.validateStructure(buffer, pos);
/* 212 */       if (!particleResult.isValid()) {
/* 213 */         return ValidationResult.error("Invalid Particle: " + particleResult.error());
/*     */       }
/* 215 */       pos += FluidParticle.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 217 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FluidFX clone() {
/* 221 */     FluidFX copy = new FluidFX();
/* 222 */     copy.id = this.id;
/* 223 */     copy.shader = this.shader;
/* 224 */     copy.fogMode = this.fogMode;
/* 225 */     copy.fogColor = (this.fogColor != null) ? this.fogColor.clone() : null;
/* 226 */     copy.fogDistance = (this.fogDistance != null) ? this.fogDistance.clone() : null;
/* 227 */     copy.fogDepthStart = this.fogDepthStart;
/* 228 */     copy.fogDepthFalloff = this.fogDepthFalloff;
/* 229 */     copy.colorFilter = (this.colorFilter != null) ? this.colorFilter.clone() : null;
/* 230 */     copy.colorSaturation = this.colorSaturation;
/* 231 */     copy.distortionAmplitude = this.distortionAmplitude;
/* 232 */     copy.distortionFrequency = this.distortionFrequency;
/* 233 */     copy.particle = (this.particle != null) ? this.particle.clone() : null;
/* 234 */     copy.movementSettings = (this.movementSettings != null) ? this.movementSettings.clone() : null;
/* 235 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FluidFX other;
/* 241 */     if (this == obj) return true; 
/* 242 */     if (obj instanceof FluidFX) { other = (FluidFX)obj; } else { return false; }
/* 243 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.shader, other.shader) && Objects.equals(this.fogMode, other.fogMode) && Objects.equals(this.fogColor, other.fogColor) && Objects.equals(this.fogDistance, other.fogDistance) && this.fogDepthStart == other.fogDepthStart && this.fogDepthFalloff == other.fogDepthFalloff && Objects.equals(this.colorFilter, other.colorFilter) && this.colorSaturation == other.colorSaturation && this.distortionAmplitude == other.distortionAmplitude && this.distortionFrequency == other.distortionFrequency && Objects.equals(this.particle, other.particle) && Objects.equals(this.movementSettings, other.movementSettings));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 248 */     return Objects.hash(new Object[] { this.id, this.shader, this.fogMode, this.fogColor, this.fogDistance, Float.valueOf(this.fogDepthStart), Float.valueOf(this.fogDepthFalloff), this.colorFilter, Float.valueOf(this.colorSaturation), Float.valueOf(this.distortionAmplitude), Float.valueOf(this.distortionFrequency), this.particle, this.movementSettings });
/*     */   }
/*     */   
/*     */   public FluidFX() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FluidFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */