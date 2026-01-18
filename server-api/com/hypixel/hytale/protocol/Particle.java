/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Particle
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 133;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   @Nonnull
/*  22 */   public ParticleUVOption uvOption = ParticleUVOption.None; public static final int VARIABLE_BLOCK_START = 141; public static final int MAX_SIZE = 270336151; @Nullable public String texturePath; @Nullable public Size frameSize; @Nonnull
/*  23 */   public ParticleScaleRatioConstraint scaleRatioConstraint = ParticleScaleRatioConstraint.OneToOne; @Nonnull
/*  24 */   public SoftParticle softParticles = SoftParticle.Enable;
/*     */   public float softParticlesFadeFactor;
/*     */   public boolean useSpriteBlending;
/*     */   @Nullable
/*     */   public ParticleAnimationFrame initialAnimationFrame;
/*     */   @Nullable
/*     */   public ParticleAnimationFrame collisionAnimationFrame;
/*     */   @Nullable
/*     */   public Map<Integer, ParticleAnimationFrame> animationFrames;
/*     */   
/*     */   public Particle(@Nullable String texturePath, @Nullable Size frameSize, @Nonnull ParticleUVOption uvOption, @Nonnull ParticleScaleRatioConstraint scaleRatioConstraint, @Nonnull SoftParticle softParticles, float softParticlesFadeFactor, boolean useSpriteBlending, @Nullable ParticleAnimationFrame initialAnimationFrame, @Nullable ParticleAnimationFrame collisionAnimationFrame, @Nullable Map<Integer, ParticleAnimationFrame> animationFrames) {
/*  35 */     this.texturePath = texturePath;
/*  36 */     this.frameSize = frameSize;
/*  37 */     this.uvOption = uvOption;
/*  38 */     this.scaleRatioConstraint = scaleRatioConstraint;
/*  39 */     this.softParticles = softParticles;
/*  40 */     this.softParticlesFadeFactor = softParticlesFadeFactor;
/*  41 */     this.useSpriteBlending = useSpriteBlending;
/*  42 */     this.initialAnimationFrame = initialAnimationFrame;
/*  43 */     this.collisionAnimationFrame = collisionAnimationFrame;
/*  44 */     this.animationFrames = animationFrames;
/*     */   }
/*     */   
/*     */   public Particle(@Nonnull Particle other) {
/*  48 */     this.texturePath = other.texturePath;
/*  49 */     this.frameSize = other.frameSize;
/*  50 */     this.uvOption = other.uvOption;
/*  51 */     this.scaleRatioConstraint = other.scaleRatioConstraint;
/*  52 */     this.softParticles = other.softParticles;
/*  53 */     this.softParticlesFadeFactor = other.softParticlesFadeFactor;
/*  54 */     this.useSpriteBlending = other.useSpriteBlending;
/*  55 */     this.initialAnimationFrame = other.initialAnimationFrame;
/*  56 */     this.collisionAnimationFrame = other.collisionAnimationFrame;
/*  57 */     this.animationFrames = other.animationFrames;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Particle deserialize(@Nonnull ByteBuf buf, int offset) {
/*  62 */     Particle obj = new Particle();
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     if ((nullBits & 0x2) != 0) obj.frameSize = Size.deserialize(buf, offset + 1); 
/*  65 */     obj.uvOption = ParticleUVOption.fromValue(buf.getByte(offset + 9));
/*  66 */     obj.scaleRatioConstraint = ParticleScaleRatioConstraint.fromValue(buf.getByte(offset + 10));
/*  67 */     obj.softParticles = SoftParticle.fromValue(buf.getByte(offset + 11));
/*  68 */     obj.softParticlesFadeFactor = buf.getFloatLE(offset + 12);
/*  69 */     obj.useSpriteBlending = (buf.getByte(offset + 16) != 0);
/*  70 */     if ((nullBits & 0x4) != 0) obj.initialAnimationFrame = ParticleAnimationFrame.deserialize(buf, offset + 17); 
/*  71 */     if ((nullBits & 0x8) != 0) obj.collisionAnimationFrame = ParticleAnimationFrame.deserialize(buf, offset + 75);
/*     */     
/*  73 */     if ((nullBits & 0x1) != 0) {
/*  74 */       int varPos0 = offset + 141 + buf.getIntLE(offset + 133);
/*  75 */       int texturePathLen = VarInt.peek(buf, varPos0);
/*  76 */       if (texturePathLen < 0) throw ProtocolException.negativeLength("TexturePath", texturePathLen); 
/*  77 */       if (texturePathLen > 4096000) throw ProtocolException.stringTooLong("TexturePath", texturePathLen, 4096000); 
/*  78 */       obj.texturePath = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  80 */     if ((nullBits & 0x10) != 0) {
/*  81 */       int varPos1 = offset + 141 + buf.getIntLE(offset + 137);
/*  82 */       int animationFramesCount = VarInt.peek(buf, varPos1);
/*  83 */       if (animationFramesCount < 0) throw ProtocolException.negativeLength("AnimationFrames", animationFramesCount); 
/*  84 */       if (animationFramesCount > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationFrames", animationFramesCount, 4096000); 
/*  85 */       int varIntLen = VarInt.length(buf, varPos1);
/*  86 */       obj.animationFrames = new HashMap<>(animationFramesCount);
/*  87 */       int dictPos = varPos1 + varIntLen;
/*  88 */       for (int i = 0; i < animationFramesCount; i++) {
/*  89 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  90 */         ParticleAnimationFrame val = ParticleAnimationFrame.deserialize(buf, dictPos);
/*  91 */         dictPos += ParticleAnimationFrame.computeBytesConsumed(buf, dictPos);
/*  92 */         if (obj.animationFrames.put(Integer.valueOf(key), val) != null) {
/*  93 */           throw ProtocolException.duplicateKey("animationFrames", Integer.valueOf(key));
/*     */         }
/*     */       } 
/*     */     } 
/*  97 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 101 */     byte nullBits = buf.getByte(offset);
/* 102 */     int maxEnd = 141;
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int fieldOffset0 = buf.getIntLE(offset + 133);
/* 105 */       int pos0 = offset + 141 + fieldOffset0;
/* 106 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 107 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 109 */     if ((nullBits & 0x10) != 0) {
/* 110 */       int fieldOffset1 = buf.getIntLE(offset + 137);
/* 111 */       int pos1 = offset + 141 + fieldOffset1;
/* 112 */       int dictLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 113 */       for (int i = 0; i < dictLen; ) { pos1 += 4; pos1 += ParticleAnimationFrame.computeBytesConsumed(buf, pos1); i++; }
/* 114 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 116 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 121 */     int startPos = buf.writerIndex();
/* 122 */     byte nullBits = 0;
/* 123 */     if (this.texturePath != null) nullBits = (byte)(nullBits | 0x1); 
/* 124 */     if (this.frameSize != null) nullBits = (byte)(nullBits | 0x2); 
/* 125 */     if (this.initialAnimationFrame != null) nullBits = (byte)(nullBits | 0x4); 
/* 126 */     if (this.collisionAnimationFrame != null) nullBits = (byte)(nullBits | 0x8); 
/* 127 */     if (this.animationFrames != null) nullBits = (byte)(nullBits | 0x10); 
/* 128 */     buf.writeByte(nullBits);
/*     */     
/* 130 */     if (this.frameSize != null) { this.frameSize.serialize(buf); } else { buf.writeZero(8); }
/* 131 */      buf.writeByte(this.uvOption.getValue());
/* 132 */     buf.writeByte(this.scaleRatioConstraint.getValue());
/* 133 */     buf.writeByte(this.softParticles.getValue());
/* 134 */     buf.writeFloatLE(this.softParticlesFadeFactor);
/* 135 */     buf.writeByte(this.useSpriteBlending ? 1 : 0);
/* 136 */     if (this.initialAnimationFrame != null) { this.initialAnimationFrame.serialize(buf); } else { buf.writeZero(58); }
/* 137 */      if (this.collisionAnimationFrame != null) { this.collisionAnimationFrame.serialize(buf); } else { buf.writeZero(58); }
/*     */     
/* 139 */     int texturePathOffsetSlot = buf.writerIndex();
/* 140 */     buf.writeIntLE(0);
/* 141 */     int animationFramesOffsetSlot = buf.writerIndex();
/* 142 */     buf.writeIntLE(0);
/*     */     
/* 144 */     int varBlockStart = buf.writerIndex();
/* 145 */     if (this.texturePath != null) {
/* 146 */       buf.setIntLE(texturePathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 147 */       PacketIO.writeVarString(buf, this.texturePath, 4096000);
/*     */     } else {
/* 149 */       buf.setIntLE(texturePathOffsetSlot, -1);
/*     */     } 
/* 151 */     if (this.animationFrames != null)
/* 152 */     { buf.setIntLE(animationFramesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 153 */       if (this.animationFrames.size() > 4096000) throw ProtocolException.dictionaryTooLarge("AnimationFrames", this.animationFrames.size(), 4096000);  VarInt.write(buf, this.animationFrames.size()); for (Map.Entry<Integer, ParticleAnimationFrame> e : this.animationFrames.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((ParticleAnimationFrame)e.getValue()).serialize(buf); }
/*     */        }
/* 155 */     else { buf.setIntLE(animationFramesOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 161 */     int size = 141;
/* 162 */     if (this.texturePath != null) size += PacketIO.stringSize(this.texturePath); 
/* 163 */     if (this.animationFrames != null) size += VarInt.size(this.animationFrames.size()) + this.animationFrames.size() * 62;
/*     */     
/* 165 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 169 */     if (buffer.readableBytes() - offset < 141) {
/* 170 */       return ValidationResult.error("Buffer too small: expected at least 141 bytes");
/*     */     }
/*     */     
/* 173 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 176 */     if ((nullBits & 0x1) != 0) {
/* 177 */       int texturePathOffset = buffer.getIntLE(offset + 133);
/* 178 */       if (texturePathOffset < 0) {
/* 179 */         return ValidationResult.error("Invalid offset for TexturePath");
/*     */       }
/* 181 */       int pos = offset + 141 + texturePathOffset;
/* 182 */       if (pos >= buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Offset out of bounds for TexturePath");
/*     */       }
/* 185 */       int texturePathLen = VarInt.peek(buffer, pos);
/* 186 */       if (texturePathLen < 0) {
/* 187 */         return ValidationResult.error("Invalid string length for TexturePath");
/*     */       }
/* 189 */       if (texturePathLen > 4096000) {
/* 190 */         return ValidationResult.error("TexturePath exceeds max length 4096000");
/*     */       }
/* 192 */       pos += VarInt.length(buffer, pos);
/* 193 */       pos += texturePathLen;
/* 194 */       if (pos > buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Buffer overflow reading TexturePath");
/*     */       }
/*     */     } 
/*     */     
/* 199 */     if ((nullBits & 0x10) != 0) {
/* 200 */       int animationFramesOffset = buffer.getIntLE(offset + 137);
/* 201 */       if (animationFramesOffset < 0) {
/* 202 */         return ValidationResult.error("Invalid offset for AnimationFrames");
/*     */       }
/* 204 */       int pos = offset + 141 + animationFramesOffset;
/* 205 */       if (pos >= buffer.writerIndex()) {
/* 206 */         return ValidationResult.error("Offset out of bounds for AnimationFrames");
/*     */       }
/* 208 */       int animationFramesCount = VarInt.peek(buffer, pos);
/* 209 */       if (animationFramesCount < 0) {
/* 210 */         return ValidationResult.error("Invalid dictionary count for AnimationFrames");
/*     */       }
/* 212 */       if (animationFramesCount > 4096000) {
/* 213 */         return ValidationResult.error("AnimationFrames exceeds max length 4096000");
/*     */       }
/* 215 */       pos += VarInt.length(buffer, pos);
/* 216 */       for (int i = 0; i < animationFramesCount; i++) {
/* 217 */         pos += 4;
/* 218 */         if (pos > buffer.writerIndex()) {
/* 219 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 221 */         pos += 58;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Particle clone() {
/* 229 */     Particle copy = new Particle();
/* 230 */     copy.texturePath = this.texturePath;
/* 231 */     copy.frameSize = (this.frameSize != null) ? this.frameSize.clone() : null;
/* 232 */     copy.uvOption = this.uvOption;
/* 233 */     copy.scaleRatioConstraint = this.scaleRatioConstraint;
/* 234 */     copy.softParticles = this.softParticles;
/* 235 */     copy.softParticlesFadeFactor = this.softParticlesFadeFactor;
/* 236 */     copy.useSpriteBlending = this.useSpriteBlending;
/* 237 */     copy.initialAnimationFrame = (this.initialAnimationFrame != null) ? this.initialAnimationFrame.clone() : null;
/* 238 */     copy.collisionAnimationFrame = (this.collisionAnimationFrame != null) ? this.collisionAnimationFrame.clone() : null;
/* 239 */     if (this.animationFrames != null) {
/* 240 */       Map<Integer, ParticleAnimationFrame> m = new HashMap<>();
/* 241 */       for (Map.Entry<Integer, ParticleAnimationFrame> e : this.animationFrames.entrySet()) m.put(e.getKey(), ((ParticleAnimationFrame)e.getValue()).clone()); 
/* 242 */       copy.animationFrames = m;
/*     */     } 
/* 244 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Particle other;
/* 250 */     if (this == obj) return true; 
/* 251 */     if (obj instanceof Particle) { other = (Particle)obj; } else { return false; }
/* 252 */      return (Objects.equals(this.texturePath, other.texturePath) && Objects.equals(this.frameSize, other.frameSize) && Objects.equals(this.uvOption, other.uvOption) && Objects.equals(this.scaleRatioConstraint, other.scaleRatioConstraint) && Objects.equals(this.softParticles, other.softParticles) && this.softParticlesFadeFactor == other.softParticlesFadeFactor && this.useSpriteBlending == other.useSpriteBlending && Objects.equals(this.initialAnimationFrame, other.initialAnimationFrame) && Objects.equals(this.collisionAnimationFrame, other.collisionAnimationFrame) && Objects.equals(this.animationFrames, other.animationFrames));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 257 */     return Objects.hash(new Object[] { this.texturePath, this.frameSize, this.uvOption, this.scaleRatioConstraint, this.softParticles, Float.valueOf(this.softParticlesFadeFactor), Boolean.valueOf(this.useSpriteBlending), this.initialAnimationFrame, this.collisionAnimationFrame, this.animationFrames });
/*     */   }
/*     */   
/*     */   public Particle() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Particle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */