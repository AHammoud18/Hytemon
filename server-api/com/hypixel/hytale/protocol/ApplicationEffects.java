/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ApplicationEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 35;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 59;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Color entityBottomTint;
/*     */   @Nullable
/*     */   public Color entityTopTint;
/*     */   @Nullable
/*     */   public String entityAnimationId;
/*     */   @Nullable
/*     */   public ModelParticle[] particles;
/*     */   @Nullable
/*     */   public ModelParticle[] firstPersonParticles;
/*     */   
/*     */   public ApplicationEffects(@Nullable Color entityBottomTint, @Nullable Color entityTopTint, @Nullable String entityAnimationId, @Nullable ModelParticle[] particles, @Nullable ModelParticle[] firstPersonParticles, @Nullable String screenEffect, float horizontalSpeedMultiplier, int soundEventIndexLocal, int soundEventIndexWorld, @Nullable String modelVFXId, @Nullable MovementEffects movementEffects, float mouseSensitivityAdjustmentTarget, float mouseSensitivityAdjustmentDuration, @Nullable AbilityEffects abilityEffects) {
/*  39 */     this.entityBottomTint = entityBottomTint;
/*  40 */     this.entityTopTint = entityTopTint;
/*  41 */     this.entityAnimationId = entityAnimationId;
/*  42 */     this.particles = particles;
/*  43 */     this.firstPersonParticles = firstPersonParticles;
/*  44 */     this.screenEffect = screenEffect;
/*  45 */     this.horizontalSpeedMultiplier = horizontalSpeedMultiplier;
/*  46 */     this.soundEventIndexLocal = soundEventIndexLocal;
/*  47 */     this.soundEventIndexWorld = soundEventIndexWorld;
/*  48 */     this.modelVFXId = modelVFXId;
/*  49 */     this.movementEffects = movementEffects;
/*  50 */     this.mouseSensitivityAdjustmentTarget = mouseSensitivityAdjustmentTarget;
/*  51 */     this.mouseSensitivityAdjustmentDuration = mouseSensitivityAdjustmentDuration;
/*  52 */     this.abilityEffects = abilityEffects; } @Nullable
/*     */   public String screenEffect; public float horizontalSpeedMultiplier; public int soundEventIndexLocal; public int soundEventIndexWorld; @Nullable
/*     */   public String modelVFXId; @Nullable
/*     */   public MovementEffects movementEffects; public float mouseSensitivityAdjustmentTarget; public float mouseSensitivityAdjustmentDuration; @Nullable
/*  56 */   public AbilityEffects abilityEffects; public ApplicationEffects() {} public ApplicationEffects(@Nonnull ApplicationEffects other) { this.entityBottomTint = other.entityBottomTint;
/*  57 */     this.entityTopTint = other.entityTopTint;
/*  58 */     this.entityAnimationId = other.entityAnimationId;
/*  59 */     this.particles = other.particles;
/*  60 */     this.firstPersonParticles = other.firstPersonParticles;
/*  61 */     this.screenEffect = other.screenEffect;
/*  62 */     this.horizontalSpeedMultiplier = other.horizontalSpeedMultiplier;
/*  63 */     this.soundEventIndexLocal = other.soundEventIndexLocal;
/*  64 */     this.soundEventIndexWorld = other.soundEventIndexWorld;
/*  65 */     this.modelVFXId = other.modelVFXId;
/*  66 */     this.movementEffects = other.movementEffects;
/*  67 */     this.mouseSensitivityAdjustmentTarget = other.mouseSensitivityAdjustmentTarget;
/*  68 */     this.mouseSensitivityAdjustmentDuration = other.mouseSensitivityAdjustmentDuration;
/*  69 */     this.abilityEffects = other.abilityEffects; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ApplicationEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  74 */     ApplicationEffects obj = new ApplicationEffects();
/*  75 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  76 */     if ((nullBits[0] & 0x1) != 0) obj.entityBottomTint = Color.deserialize(buf, offset + 2); 
/*  77 */     if ((nullBits[0] & 0x2) != 0) obj.entityTopTint = Color.deserialize(buf, offset + 5); 
/*  78 */     obj.horizontalSpeedMultiplier = buf.getFloatLE(offset + 8);
/*  79 */     obj.soundEventIndexLocal = buf.getIntLE(offset + 12);
/*  80 */     obj.soundEventIndexWorld = buf.getIntLE(offset + 16);
/*  81 */     if ((nullBits[0] & 0x80) != 0) obj.movementEffects = MovementEffects.deserialize(buf, offset + 20); 
/*  82 */     obj.mouseSensitivityAdjustmentTarget = buf.getFloatLE(offset + 27);
/*  83 */     obj.mouseSensitivityAdjustmentDuration = buf.getFloatLE(offset + 31);
/*     */     
/*  85 */     if ((nullBits[0] & 0x4) != 0) {
/*  86 */       int varPos0 = offset + 59 + buf.getIntLE(offset + 35);
/*  87 */       int entityAnimationIdLen = VarInt.peek(buf, varPos0);
/*  88 */       if (entityAnimationIdLen < 0) throw ProtocolException.negativeLength("EntityAnimationId", entityAnimationIdLen); 
/*  89 */       if (entityAnimationIdLen > 4096000) throw ProtocolException.stringTooLong("EntityAnimationId", entityAnimationIdLen, 4096000); 
/*  90 */       obj.entityAnimationId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  92 */     if ((nullBits[0] & 0x8) != 0) {
/*  93 */       int varPos1 = offset + 59 + buf.getIntLE(offset + 39);
/*  94 */       int particlesCount = VarInt.peek(buf, varPos1);
/*  95 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  96 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  97 */       int varIntLen = VarInt.length(buf, varPos1);
/*  98 */       if ((varPos1 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/*  99 */         throw ProtocolException.bufferTooSmall("Particles", varPos1 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/* 100 */       obj.particles = new ModelParticle[particlesCount];
/* 101 */       int elemPos = varPos1 + varIntLen;
/* 102 */       for (int i = 0; i < particlesCount; i++) {
/* 103 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/* 104 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 107 */     if ((nullBits[0] & 0x10) != 0) {
/* 108 */       int varPos2 = offset + 59 + buf.getIntLE(offset + 43);
/* 109 */       int firstPersonParticlesCount = VarInt.peek(buf, varPos2);
/* 110 */       if (firstPersonParticlesCount < 0) throw ProtocolException.negativeLength("FirstPersonParticles", firstPersonParticlesCount); 
/* 111 */       if (firstPersonParticlesCount > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", firstPersonParticlesCount, 4096000); 
/* 112 */       int varIntLen = VarInt.length(buf, varPos2);
/* 113 */       if ((varPos2 + varIntLen) + firstPersonParticlesCount * 34L > buf.readableBytes())
/* 114 */         throw ProtocolException.bufferTooSmall("FirstPersonParticles", varPos2 + varIntLen + firstPersonParticlesCount * 34, buf.readableBytes()); 
/* 115 */       obj.firstPersonParticles = new ModelParticle[firstPersonParticlesCount];
/* 116 */       int elemPos = varPos2 + varIntLen;
/* 117 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 118 */         obj.firstPersonParticles[i] = ModelParticle.deserialize(buf, elemPos);
/* 119 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 122 */     if ((nullBits[0] & 0x20) != 0) {
/* 123 */       int varPos3 = offset + 59 + buf.getIntLE(offset + 47);
/* 124 */       int screenEffectLen = VarInt.peek(buf, varPos3);
/* 125 */       if (screenEffectLen < 0) throw ProtocolException.negativeLength("ScreenEffect", screenEffectLen); 
/* 126 */       if (screenEffectLen > 4096000) throw ProtocolException.stringTooLong("ScreenEffect", screenEffectLen, 4096000); 
/* 127 */       obj.screenEffect = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 129 */     if ((nullBits[0] & 0x40) != 0) {
/* 130 */       int varPos4 = offset + 59 + buf.getIntLE(offset + 51);
/* 131 */       int modelVFXIdLen = VarInt.peek(buf, varPos4);
/* 132 */       if (modelVFXIdLen < 0) throw ProtocolException.negativeLength("ModelVFXId", modelVFXIdLen); 
/* 133 */       if (modelVFXIdLen > 4096000) throw ProtocolException.stringTooLong("ModelVFXId", modelVFXIdLen, 4096000); 
/* 134 */       obj.modelVFXId = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/* 136 */     if ((nullBits[1] & 0x1) != 0) {
/* 137 */       int varPos5 = offset + 59 + buf.getIntLE(offset + 55);
/* 138 */       obj.abilityEffects = AbilityEffects.deserialize(buf, varPos5);
/*     */     } 
/*     */     
/* 141 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 145 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 146 */     int maxEnd = 59;
/* 147 */     if ((nullBits[0] & 0x4) != 0) {
/* 148 */       int fieldOffset0 = buf.getIntLE(offset + 35);
/* 149 */       int pos0 = offset + 59 + fieldOffset0;
/* 150 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 151 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 153 */     if ((nullBits[0] & 0x8) != 0) {
/* 154 */       int fieldOffset1 = buf.getIntLE(offset + 39);
/* 155 */       int pos1 = offset + 59 + fieldOffset1;
/* 156 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 157 */       for (int i = 0; i < arrLen; ) { pos1 += ModelParticle.computeBytesConsumed(buf, pos1); i++; }
/* 158 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 160 */     if ((nullBits[0] & 0x10) != 0) {
/* 161 */       int fieldOffset2 = buf.getIntLE(offset + 43);
/* 162 */       int pos2 = offset + 59 + fieldOffset2;
/* 163 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 164 */       for (int i = 0; i < arrLen; ) { pos2 += ModelParticle.computeBytesConsumed(buf, pos2); i++; }
/* 165 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 167 */     if ((nullBits[0] & 0x20) != 0) {
/* 168 */       int fieldOffset3 = buf.getIntLE(offset + 47);
/* 169 */       int pos3 = offset + 59 + fieldOffset3;
/* 170 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 171 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 173 */     if ((nullBits[0] & 0x40) != 0) {
/* 174 */       int fieldOffset4 = buf.getIntLE(offset + 51);
/* 175 */       int pos4 = offset + 59 + fieldOffset4;
/* 176 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 177 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 179 */     if ((nullBits[1] & 0x1) != 0) {
/* 180 */       int fieldOffset5 = buf.getIntLE(offset + 55);
/* 181 */       int pos5 = offset + 59 + fieldOffset5;
/* 182 */       pos5 += AbilityEffects.computeBytesConsumed(buf, pos5);
/* 183 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 185 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 190 */     int startPos = buf.writerIndex();
/* 191 */     byte[] nullBits = new byte[2];
/* 192 */     if (this.entityBottomTint != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 193 */     if (this.entityTopTint != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 194 */     if (this.entityAnimationId != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 195 */     if (this.particles != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 196 */     if (this.firstPersonParticles != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 197 */     if (this.screenEffect != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 198 */     if (this.modelVFXId != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 199 */     if (this.movementEffects != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 200 */     if (this.abilityEffects != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 201 */     buf.writeBytes(nullBits);
/*     */     
/* 203 */     if (this.entityBottomTint != null) { this.entityBottomTint.serialize(buf); } else { buf.writeZero(3); }
/* 204 */      if (this.entityTopTint != null) { this.entityTopTint.serialize(buf); } else { buf.writeZero(3); }
/* 205 */      buf.writeFloatLE(this.horizontalSpeedMultiplier);
/* 206 */     buf.writeIntLE(this.soundEventIndexLocal);
/* 207 */     buf.writeIntLE(this.soundEventIndexWorld);
/* 208 */     if (this.movementEffects != null) { this.movementEffects.serialize(buf); } else { buf.writeZero(7); }
/* 209 */      buf.writeFloatLE(this.mouseSensitivityAdjustmentTarget);
/* 210 */     buf.writeFloatLE(this.mouseSensitivityAdjustmentDuration);
/*     */     
/* 212 */     int entityAnimationIdOffsetSlot = buf.writerIndex();
/* 213 */     buf.writeIntLE(0);
/* 214 */     int particlesOffsetSlot = buf.writerIndex();
/* 215 */     buf.writeIntLE(0);
/* 216 */     int firstPersonParticlesOffsetSlot = buf.writerIndex();
/* 217 */     buf.writeIntLE(0);
/* 218 */     int screenEffectOffsetSlot = buf.writerIndex();
/* 219 */     buf.writeIntLE(0);
/* 220 */     int modelVFXIdOffsetSlot = buf.writerIndex();
/* 221 */     buf.writeIntLE(0);
/* 222 */     int abilityEffectsOffsetSlot = buf.writerIndex();
/* 223 */     buf.writeIntLE(0);
/*     */     
/* 225 */     int varBlockStart = buf.writerIndex();
/* 226 */     if (this.entityAnimationId != null) {
/* 227 */       buf.setIntLE(entityAnimationIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 228 */       PacketIO.writeVarString(buf, this.entityAnimationId, 4096000);
/*     */     } else {
/* 230 */       buf.setIntLE(entityAnimationIdOffsetSlot, -1);
/*     */     } 
/* 232 */     if (this.particles != null) {
/* 233 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 234 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*     */     } else {
/* 236 */       buf.setIntLE(particlesOffsetSlot, -1);
/*     */     } 
/* 238 */     if (this.firstPersonParticles != null) {
/* 239 */       buf.setIntLE(firstPersonParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 240 */       if (this.firstPersonParticles.length > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", this.firstPersonParticles.length, 4096000);  VarInt.write(buf, this.firstPersonParticles.length); for (ModelParticle item : this.firstPersonParticles) item.serialize(buf); 
/*     */     } else {
/* 242 */       buf.setIntLE(firstPersonParticlesOffsetSlot, -1);
/*     */     } 
/* 244 */     if (this.screenEffect != null) {
/* 245 */       buf.setIntLE(screenEffectOffsetSlot, buf.writerIndex() - varBlockStart);
/* 246 */       PacketIO.writeVarString(buf, this.screenEffect, 4096000);
/*     */     } else {
/* 248 */       buf.setIntLE(screenEffectOffsetSlot, -1);
/*     */     } 
/* 250 */     if (this.modelVFXId != null) {
/* 251 */       buf.setIntLE(modelVFXIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 252 */       PacketIO.writeVarString(buf, this.modelVFXId, 4096000);
/*     */     } else {
/* 254 */       buf.setIntLE(modelVFXIdOffsetSlot, -1);
/*     */     } 
/* 256 */     if (this.abilityEffects != null) {
/* 257 */       buf.setIntLE(abilityEffectsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 258 */       this.abilityEffects.serialize(buf);
/*     */     } else {
/* 260 */       buf.setIntLE(abilityEffectsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 266 */     int size = 59;
/* 267 */     if (this.entityAnimationId != null) size += PacketIO.stringSize(this.entityAnimationId); 
/* 268 */     if (this.particles != null) {
/* 269 */       int particlesSize = 0;
/* 270 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/* 271 */       size += VarInt.size(this.particles.length) + particlesSize;
/*     */     } 
/* 273 */     if (this.firstPersonParticles != null) {
/* 274 */       int firstPersonParticlesSize = 0;
/* 275 */       for (ModelParticle elem : this.firstPersonParticles) firstPersonParticlesSize += elem.computeSize(); 
/* 276 */       size += VarInt.size(this.firstPersonParticles.length) + firstPersonParticlesSize;
/*     */     } 
/* 278 */     if (this.screenEffect != null) size += PacketIO.stringSize(this.screenEffect); 
/* 279 */     if (this.modelVFXId != null) size += PacketIO.stringSize(this.modelVFXId); 
/* 280 */     if (this.abilityEffects != null) size += this.abilityEffects.computeSize();
/*     */     
/* 282 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 286 */     if (buffer.readableBytes() - offset < 59) {
/* 287 */       return ValidationResult.error("Buffer too small: expected at least 59 bytes");
/*     */     }
/*     */     
/* 290 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 292 */     if ((nullBits[0] & 0x4) != 0) {
/* 293 */       int entityAnimationIdOffset = buffer.getIntLE(offset + 35);
/* 294 */       if (entityAnimationIdOffset < 0) {
/* 295 */         return ValidationResult.error("Invalid offset for EntityAnimationId");
/*     */       }
/* 297 */       int pos = offset + 59 + entityAnimationIdOffset;
/* 298 */       if (pos >= buffer.writerIndex()) {
/* 299 */         return ValidationResult.error("Offset out of bounds for EntityAnimationId");
/*     */       }
/* 301 */       int entityAnimationIdLen = VarInt.peek(buffer, pos);
/* 302 */       if (entityAnimationIdLen < 0) {
/* 303 */         return ValidationResult.error("Invalid string length for EntityAnimationId");
/*     */       }
/* 305 */       if (entityAnimationIdLen > 4096000) {
/* 306 */         return ValidationResult.error("EntityAnimationId exceeds max length 4096000");
/*     */       }
/* 308 */       pos += VarInt.length(buffer, pos);
/* 309 */       pos += entityAnimationIdLen;
/* 310 */       if (pos > buffer.writerIndex()) {
/* 311 */         return ValidationResult.error("Buffer overflow reading EntityAnimationId");
/*     */       }
/*     */     } 
/*     */     
/* 315 */     if ((nullBits[0] & 0x8) != 0) {
/* 316 */       int particlesOffset = buffer.getIntLE(offset + 39);
/* 317 */       if (particlesOffset < 0) {
/* 318 */         return ValidationResult.error("Invalid offset for Particles");
/*     */       }
/* 320 */       int pos = offset + 59 + particlesOffset;
/* 321 */       if (pos >= buffer.writerIndex()) {
/* 322 */         return ValidationResult.error("Offset out of bounds for Particles");
/*     */       }
/* 324 */       int particlesCount = VarInt.peek(buffer, pos);
/* 325 */       if (particlesCount < 0) {
/* 326 */         return ValidationResult.error("Invalid array count for Particles");
/*     */       }
/* 328 */       if (particlesCount > 4096000) {
/* 329 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*     */       }
/* 331 */       pos += VarInt.length(buffer, pos);
/* 332 */       for (int i = 0; i < particlesCount; i++) {
/* 333 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 334 */         if (!structResult.isValid()) {
/* 335 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*     */         }
/* 337 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     if ((nullBits[0] & 0x10) != 0) {
/* 342 */       int firstPersonParticlesOffset = buffer.getIntLE(offset + 43);
/* 343 */       if (firstPersonParticlesOffset < 0) {
/* 344 */         return ValidationResult.error("Invalid offset for FirstPersonParticles");
/*     */       }
/* 346 */       int pos = offset + 59 + firstPersonParticlesOffset;
/* 347 */       if (pos >= buffer.writerIndex()) {
/* 348 */         return ValidationResult.error("Offset out of bounds for FirstPersonParticles");
/*     */       }
/* 350 */       int firstPersonParticlesCount = VarInt.peek(buffer, pos);
/* 351 */       if (firstPersonParticlesCount < 0) {
/* 352 */         return ValidationResult.error("Invalid array count for FirstPersonParticles");
/*     */       }
/* 354 */       if (firstPersonParticlesCount > 4096000) {
/* 355 */         return ValidationResult.error("FirstPersonParticles exceeds max length 4096000");
/*     */       }
/* 357 */       pos += VarInt.length(buffer, pos);
/* 358 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 359 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 360 */         if (!structResult.isValid()) {
/* 361 */           return ValidationResult.error("Invalid ModelParticle in FirstPersonParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 363 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     if ((nullBits[0] & 0x20) != 0) {
/* 368 */       int screenEffectOffset = buffer.getIntLE(offset + 47);
/* 369 */       if (screenEffectOffset < 0) {
/* 370 */         return ValidationResult.error("Invalid offset for ScreenEffect");
/*     */       }
/* 372 */       int pos = offset + 59 + screenEffectOffset;
/* 373 */       if (pos >= buffer.writerIndex()) {
/* 374 */         return ValidationResult.error("Offset out of bounds for ScreenEffect");
/*     */       }
/* 376 */       int screenEffectLen = VarInt.peek(buffer, pos);
/* 377 */       if (screenEffectLen < 0) {
/* 378 */         return ValidationResult.error("Invalid string length for ScreenEffect");
/*     */       }
/* 380 */       if (screenEffectLen > 4096000) {
/* 381 */         return ValidationResult.error("ScreenEffect exceeds max length 4096000");
/*     */       }
/* 383 */       pos += VarInt.length(buffer, pos);
/* 384 */       pos += screenEffectLen;
/* 385 */       if (pos > buffer.writerIndex()) {
/* 386 */         return ValidationResult.error("Buffer overflow reading ScreenEffect");
/*     */       }
/*     */     } 
/*     */     
/* 390 */     if ((nullBits[0] & 0x40) != 0) {
/* 391 */       int modelVFXIdOffset = buffer.getIntLE(offset + 51);
/* 392 */       if (modelVFXIdOffset < 0) {
/* 393 */         return ValidationResult.error("Invalid offset for ModelVFXId");
/*     */       }
/* 395 */       int pos = offset + 59 + modelVFXIdOffset;
/* 396 */       if (pos >= buffer.writerIndex()) {
/* 397 */         return ValidationResult.error("Offset out of bounds for ModelVFXId");
/*     */       }
/* 399 */       int modelVFXIdLen = VarInt.peek(buffer, pos);
/* 400 */       if (modelVFXIdLen < 0) {
/* 401 */         return ValidationResult.error("Invalid string length for ModelVFXId");
/*     */       }
/* 403 */       if (modelVFXIdLen > 4096000) {
/* 404 */         return ValidationResult.error("ModelVFXId exceeds max length 4096000");
/*     */       }
/* 406 */       pos += VarInt.length(buffer, pos);
/* 407 */       pos += modelVFXIdLen;
/* 408 */       if (pos > buffer.writerIndex()) {
/* 409 */         return ValidationResult.error("Buffer overflow reading ModelVFXId");
/*     */       }
/*     */     } 
/*     */     
/* 413 */     if ((nullBits[1] & 0x1) != 0) {
/* 414 */       int abilityEffectsOffset = buffer.getIntLE(offset + 55);
/* 415 */       if (abilityEffectsOffset < 0) {
/* 416 */         return ValidationResult.error("Invalid offset for AbilityEffects");
/*     */       }
/* 418 */       int pos = offset + 59 + abilityEffectsOffset;
/* 419 */       if (pos >= buffer.writerIndex()) {
/* 420 */         return ValidationResult.error("Offset out of bounds for AbilityEffects");
/*     */       }
/* 422 */       ValidationResult abilityEffectsResult = AbilityEffects.validateStructure(buffer, pos);
/* 423 */       if (!abilityEffectsResult.isValid()) {
/* 424 */         return ValidationResult.error("Invalid AbilityEffects: " + abilityEffectsResult.error());
/*     */       }
/* 426 */       pos += AbilityEffects.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 428 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ApplicationEffects clone() {
/* 432 */     ApplicationEffects copy = new ApplicationEffects();
/* 433 */     copy.entityBottomTint = (this.entityBottomTint != null) ? this.entityBottomTint.clone() : null;
/* 434 */     copy.entityTopTint = (this.entityTopTint != null) ? this.entityTopTint.clone() : null;
/* 435 */     copy.entityAnimationId = this.entityAnimationId;
/* 436 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 437 */     copy.firstPersonParticles = (this.firstPersonParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.firstPersonParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 438 */     copy.screenEffect = this.screenEffect;
/* 439 */     copy.horizontalSpeedMultiplier = this.horizontalSpeedMultiplier;
/* 440 */     copy.soundEventIndexLocal = this.soundEventIndexLocal;
/* 441 */     copy.soundEventIndexWorld = this.soundEventIndexWorld;
/* 442 */     copy.modelVFXId = this.modelVFXId;
/* 443 */     copy.movementEffects = (this.movementEffects != null) ? this.movementEffects.clone() : null;
/* 444 */     copy.mouseSensitivityAdjustmentTarget = this.mouseSensitivityAdjustmentTarget;
/* 445 */     copy.mouseSensitivityAdjustmentDuration = this.mouseSensitivityAdjustmentDuration;
/* 446 */     copy.abilityEffects = (this.abilityEffects != null) ? this.abilityEffects.clone() : null;
/* 447 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ApplicationEffects other;
/* 453 */     if (this == obj) return true; 
/* 454 */     if (obj instanceof ApplicationEffects) { other = (ApplicationEffects)obj; } else { return false; }
/* 455 */      return (Objects.equals(this.entityBottomTint, other.entityBottomTint) && Objects.equals(this.entityTopTint, other.entityTopTint) && Objects.equals(this.entityAnimationId, other.entityAnimationId) && Arrays.equals((Object[])this.particles, (Object[])other.particles) && Arrays.equals((Object[])this.firstPersonParticles, (Object[])other.firstPersonParticles) && Objects.equals(this.screenEffect, other.screenEffect) && this.horizontalSpeedMultiplier == other.horizontalSpeedMultiplier && this.soundEventIndexLocal == other.soundEventIndexLocal && this.soundEventIndexWorld == other.soundEventIndexWorld && Objects.equals(this.modelVFXId, other.modelVFXId) && Objects.equals(this.movementEffects, other.movementEffects) && this.mouseSensitivityAdjustmentTarget == other.mouseSensitivityAdjustmentTarget && this.mouseSensitivityAdjustmentDuration == other.mouseSensitivityAdjustmentDuration && Objects.equals(this.abilityEffects, other.abilityEffects));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 460 */     int result = 1;
/* 461 */     result = 31 * result + Objects.hashCode(this.entityBottomTint);
/* 462 */     result = 31 * result + Objects.hashCode(this.entityTopTint);
/* 463 */     result = 31 * result + Objects.hashCode(this.entityAnimationId);
/* 464 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 465 */     result = 31 * result + Arrays.hashCode((Object[])this.firstPersonParticles);
/* 466 */     result = 31 * result + Objects.hashCode(this.screenEffect);
/* 467 */     result = 31 * result + Float.hashCode(this.horizontalSpeedMultiplier);
/* 468 */     result = 31 * result + Integer.hashCode(this.soundEventIndexLocal);
/* 469 */     result = 31 * result + Integer.hashCode(this.soundEventIndexWorld);
/* 470 */     result = 31 * result + Objects.hashCode(this.modelVFXId);
/* 471 */     result = 31 * result + Objects.hashCode(this.movementEffects);
/* 472 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentTarget);
/* 473 */     result = 31 * result + Float.hashCode(this.mouseSensitivityAdjustmentDuration);
/* 474 */     result = 31 * result + Objects.hashCode(this.abilityEffects);
/* 475 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ApplicationEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */