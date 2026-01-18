/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect;
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
/*     */ 
/*     */ 
/*     */ public class InteractionEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 32;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 52;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ModelParticle[] particles;
/*     */   @Nullable
/*     */   public ModelParticle[] firstPersonParticles;
/*     */   public int worldSoundEventIndex;
/*     */   public int localSoundEventIndex;
/*     */   
/*     */   public InteractionEffects(@Nullable ModelParticle[] particles, @Nullable ModelParticle[] firstPersonParticles, int worldSoundEventIndex, int localSoundEventIndex, @Nullable ModelTrail[] trails, boolean waitForAnimationToFinish, @Nullable String itemPlayerAnimationsId, @Nullable String itemAnimationId, boolean clearAnimationOnFinish, boolean clearSoundEventOnFinish, @Nullable CameraShakeEffect cameraShake, @Nullable MovementEffects movementEffects, float startDelay) {
/*  38 */     this.particles = particles;
/*  39 */     this.firstPersonParticles = firstPersonParticles;
/*  40 */     this.worldSoundEventIndex = worldSoundEventIndex;
/*  41 */     this.localSoundEventIndex = localSoundEventIndex;
/*  42 */     this.trails = trails;
/*  43 */     this.waitForAnimationToFinish = waitForAnimationToFinish;
/*  44 */     this.itemPlayerAnimationsId = itemPlayerAnimationsId;
/*  45 */     this.itemAnimationId = itemAnimationId;
/*  46 */     this.clearAnimationOnFinish = clearAnimationOnFinish;
/*  47 */     this.clearSoundEventOnFinish = clearSoundEventOnFinish;
/*  48 */     this.cameraShake = cameraShake;
/*  49 */     this.movementEffects = movementEffects;
/*  50 */     this.startDelay = startDelay; } @Nullable public ModelTrail[] trails; public boolean waitForAnimationToFinish = true; @Nullable
/*     */   public String itemPlayerAnimationsId; @Nullable
/*     */   public String itemAnimationId; public boolean clearAnimationOnFinish; public boolean clearSoundEventOnFinish; @Nullable
/*     */   public CameraShakeEffect cameraShake; @Nullable
/*  54 */   public MovementEffects movementEffects; public float startDelay; public InteractionEffects(@Nonnull InteractionEffects other) { this.particles = other.particles;
/*  55 */     this.firstPersonParticles = other.firstPersonParticles;
/*  56 */     this.worldSoundEventIndex = other.worldSoundEventIndex;
/*  57 */     this.localSoundEventIndex = other.localSoundEventIndex;
/*  58 */     this.trails = other.trails;
/*  59 */     this.waitForAnimationToFinish = other.waitForAnimationToFinish;
/*  60 */     this.itemPlayerAnimationsId = other.itemPlayerAnimationsId;
/*  61 */     this.itemAnimationId = other.itemAnimationId;
/*  62 */     this.clearAnimationOnFinish = other.clearAnimationOnFinish;
/*  63 */     this.clearSoundEventOnFinish = other.clearSoundEventOnFinish;
/*  64 */     this.cameraShake = other.cameraShake;
/*  65 */     this.movementEffects = other.movementEffects;
/*  66 */     this.startDelay = other.startDelay; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  71 */     InteractionEffects obj = new InteractionEffects();
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     obj.worldSoundEventIndex = buf.getIntLE(offset + 1);
/*  74 */     obj.localSoundEventIndex = buf.getIntLE(offset + 5);
/*  75 */     obj.waitForAnimationToFinish = (buf.getByte(offset + 9) != 0);
/*  76 */     obj.clearAnimationOnFinish = (buf.getByte(offset + 10) != 0);
/*  77 */     obj.clearSoundEventOnFinish = (buf.getByte(offset + 11) != 0);
/*  78 */     if ((nullBits & 0x20) != 0) obj.cameraShake = CameraShakeEffect.deserialize(buf, offset + 12); 
/*  79 */     if ((nullBits & 0x40) != 0) obj.movementEffects = MovementEffects.deserialize(buf, offset + 21); 
/*  80 */     obj.startDelay = buf.getFloatLE(offset + 28);
/*     */     
/*  82 */     if ((nullBits & 0x1) != 0) {
/*  83 */       int varPos0 = offset + 52 + buf.getIntLE(offset + 32);
/*  84 */       int particlesCount = VarInt.peek(buf, varPos0);
/*  85 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  86 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  87 */       int varIntLen = VarInt.length(buf, varPos0);
/*  88 */       if ((varPos0 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/*  89 */         throw ProtocolException.bufferTooSmall("Particles", varPos0 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/*  90 */       obj.particles = new ModelParticle[particlesCount];
/*  91 */       int elemPos = varPos0 + varIntLen;
/*  92 */       for (int i = 0; i < particlesCount; i++) {
/*  93 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/*  94 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  97 */     if ((nullBits & 0x2) != 0) {
/*  98 */       int varPos1 = offset + 52 + buf.getIntLE(offset + 36);
/*  99 */       int firstPersonParticlesCount = VarInt.peek(buf, varPos1);
/* 100 */       if (firstPersonParticlesCount < 0) throw ProtocolException.negativeLength("FirstPersonParticles", firstPersonParticlesCount); 
/* 101 */       if (firstPersonParticlesCount > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", firstPersonParticlesCount, 4096000); 
/* 102 */       int varIntLen = VarInt.length(buf, varPos1);
/* 103 */       if ((varPos1 + varIntLen) + firstPersonParticlesCount * 34L > buf.readableBytes())
/* 104 */         throw ProtocolException.bufferTooSmall("FirstPersonParticles", varPos1 + varIntLen + firstPersonParticlesCount * 34, buf.readableBytes()); 
/* 105 */       obj.firstPersonParticles = new ModelParticle[firstPersonParticlesCount];
/* 106 */       int elemPos = varPos1 + varIntLen;
/* 107 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 108 */         obj.firstPersonParticles[i] = ModelParticle.deserialize(buf, elemPos);
/* 109 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 112 */     if ((nullBits & 0x4) != 0) {
/* 113 */       int varPos2 = offset + 52 + buf.getIntLE(offset + 40);
/* 114 */       int trailsCount = VarInt.peek(buf, varPos2);
/* 115 */       if (trailsCount < 0) throw ProtocolException.negativeLength("Trails", trailsCount); 
/* 116 */       if (trailsCount > 4096000) throw ProtocolException.arrayTooLong("Trails", trailsCount, 4096000); 
/* 117 */       int varIntLen = VarInt.length(buf, varPos2);
/* 118 */       if ((varPos2 + varIntLen) + trailsCount * 27L > buf.readableBytes())
/* 119 */         throw ProtocolException.bufferTooSmall("Trails", varPos2 + varIntLen + trailsCount * 27, buf.readableBytes()); 
/* 120 */       obj.trails = new ModelTrail[trailsCount];
/* 121 */       int elemPos = varPos2 + varIntLen;
/* 122 */       for (int i = 0; i < trailsCount; i++) {
/* 123 */         obj.trails[i] = ModelTrail.deserialize(buf, elemPos);
/* 124 */         elemPos += ModelTrail.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 127 */     if ((nullBits & 0x8) != 0) {
/* 128 */       int varPos3 = offset + 52 + buf.getIntLE(offset + 44);
/* 129 */       int itemPlayerAnimationsIdLen = VarInt.peek(buf, varPos3);
/* 130 */       if (itemPlayerAnimationsIdLen < 0) throw ProtocolException.negativeLength("ItemPlayerAnimationsId", itemPlayerAnimationsIdLen); 
/* 131 */       if (itemPlayerAnimationsIdLen > 4096000) throw ProtocolException.stringTooLong("ItemPlayerAnimationsId", itemPlayerAnimationsIdLen, 4096000); 
/* 132 */       obj.itemPlayerAnimationsId = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 134 */     if ((nullBits & 0x10) != 0) {
/* 135 */       int varPos4 = offset + 52 + buf.getIntLE(offset + 48);
/* 136 */       int itemAnimationIdLen = VarInt.peek(buf, varPos4);
/* 137 */       if (itemAnimationIdLen < 0) throw ProtocolException.negativeLength("ItemAnimationId", itemAnimationIdLen); 
/* 138 */       if (itemAnimationIdLen > 4096000) throw ProtocolException.stringTooLong("ItemAnimationId", itemAnimationIdLen, 4096000); 
/* 139 */       obj.itemAnimationId = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 142 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 146 */     byte nullBits = buf.getByte(offset);
/* 147 */     int maxEnd = 52;
/* 148 */     if ((nullBits & 0x1) != 0) {
/* 149 */       int fieldOffset0 = buf.getIntLE(offset + 32);
/* 150 */       int pos0 = offset + 52 + fieldOffset0;
/* 151 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 152 */       for (int i = 0; i < arrLen; ) { pos0 += ModelParticle.computeBytesConsumed(buf, pos0); i++; }
/* 153 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 155 */     if ((nullBits & 0x2) != 0) {
/* 156 */       int fieldOffset1 = buf.getIntLE(offset + 36);
/* 157 */       int pos1 = offset + 52 + fieldOffset1;
/* 158 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 159 */       for (int i = 0; i < arrLen; ) { pos1 += ModelParticle.computeBytesConsumed(buf, pos1); i++; }
/* 160 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 162 */     if ((nullBits & 0x4) != 0) {
/* 163 */       int fieldOffset2 = buf.getIntLE(offset + 40);
/* 164 */       int pos2 = offset + 52 + fieldOffset2;
/* 165 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 166 */       for (int i = 0; i < arrLen; ) { pos2 += ModelTrail.computeBytesConsumed(buf, pos2); i++; }
/* 167 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 169 */     if ((nullBits & 0x8) != 0) {
/* 170 */       int fieldOffset3 = buf.getIntLE(offset + 44);
/* 171 */       int pos3 = offset + 52 + fieldOffset3;
/* 172 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 173 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 175 */     if ((nullBits & 0x10) != 0) {
/* 176 */       int fieldOffset4 = buf.getIntLE(offset + 48);
/* 177 */       int pos4 = offset + 52 + fieldOffset4;
/* 178 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 179 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 181 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 186 */     int startPos = buf.writerIndex();
/* 187 */     byte nullBits = 0;
/* 188 */     if (this.particles != null) nullBits = (byte)(nullBits | 0x1); 
/* 189 */     if (this.firstPersonParticles != null) nullBits = (byte)(nullBits | 0x2); 
/* 190 */     if (this.trails != null) nullBits = (byte)(nullBits | 0x4); 
/* 191 */     if (this.itemPlayerAnimationsId != null) nullBits = (byte)(nullBits | 0x8); 
/* 192 */     if (this.itemAnimationId != null) nullBits = (byte)(nullBits | 0x10); 
/* 193 */     if (this.cameraShake != null) nullBits = (byte)(nullBits | 0x20); 
/* 194 */     if (this.movementEffects != null) nullBits = (byte)(nullBits | 0x40); 
/* 195 */     buf.writeByte(nullBits);
/*     */     
/* 197 */     buf.writeIntLE(this.worldSoundEventIndex);
/* 198 */     buf.writeIntLE(this.localSoundEventIndex);
/* 199 */     buf.writeByte(this.waitForAnimationToFinish ? 1 : 0);
/* 200 */     buf.writeByte(this.clearAnimationOnFinish ? 1 : 0);
/* 201 */     buf.writeByte(this.clearSoundEventOnFinish ? 1 : 0);
/* 202 */     if (this.cameraShake != null) { this.cameraShake.serialize(buf); } else { buf.writeZero(9); }
/* 203 */      if (this.movementEffects != null) { this.movementEffects.serialize(buf); } else { buf.writeZero(7); }
/* 204 */      buf.writeFloatLE(this.startDelay);
/*     */     
/* 206 */     int particlesOffsetSlot = buf.writerIndex();
/* 207 */     buf.writeIntLE(0);
/* 208 */     int firstPersonParticlesOffsetSlot = buf.writerIndex();
/* 209 */     buf.writeIntLE(0);
/* 210 */     int trailsOffsetSlot = buf.writerIndex();
/* 211 */     buf.writeIntLE(0);
/* 212 */     int itemPlayerAnimationsIdOffsetSlot = buf.writerIndex();
/* 213 */     buf.writeIntLE(0);
/* 214 */     int itemAnimationIdOffsetSlot = buf.writerIndex();
/* 215 */     buf.writeIntLE(0);
/*     */     
/* 217 */     int varBlockStart = buf.writerIndex();
/* 218 */     if (this.particles != null) {
/* 219 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 220 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*     */     } else {
/* 222 */       buf.setIntLE(particlesOffsetSlot, -1);
/*     */     } 
/* 224 */     if (this.firstPersonParticles != null) {
/* 225 */       buf.setIntLE(firstPersonParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 226 */       if (this.firstPersonParticles.length > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", this.firstPersonParticles.length, 4096000);  VarInt.write(buf, this.firstPersonParticles.length); for (ModelParticle item : this.firstPersonParticles) item.serialize(buf); 
/*     */     } else {
/* 228 */       buf.setIntLE(firstPersonParticlesOffsetSlot, -1);
/*     */     } 
/* 230 */     if (this.trails != null) {
/* 231 */       buf.setIntLE(trailsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 232 */       if (this.trails.length > 4096000) throw ProtocolException.arrayTooLong("Trails", this.trails.length, 4096000);  VarInt.write(buf, this.trails.length); for (ModelTrail item : this.trails) item.serialize(buf); 
/*     */     } else {
/* 234 */       buf.setIntLE(trailsOffsetSlot, -1);
/*     */     } 
/* 236 */     if (this.itemPlayerAnimationsId != null) {
/* 237 */       buf.setIntLE(itemPlayerAnimationsIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 238 */       PacketIO.writeVarString(buf, this.itemPlayerAnimationsId, 4096000);
/*     */     } else {
/* 240 */       buf.setIntLE(itemPlayerAnimationsIdOffsetSlot, -1);
/*     */     } 
/* 242 */     if (this.itemAnimationId != null) {
/* 243 */       buf.setIntLE(itemAnimationIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 244 */       PacketIO.writeVarString(buf, this.itemAnimationId, 4096000);
/*     */     } else {
/* 246 */       buf.setIntLE(itemAnimationIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 252 */     int size = 52;
/* 253 */     if (this.particles != null) {
/* 254 */       int particlesSize = 0;
/* 255 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/* 256 */       size += VarInt.size(this.particles.length) + particlesSize;
/*     */     } 
/* 258 */     if (this.firstPersonParticles != null) {
/* 259 */       int firstPersonParticlesSize = 0;
/* 260 */       for (ModelParticle elem : this.firstPersonParticles) firstPersonParticlesSize += elem.computeSize(); 
/* 261 */       size += VarInt.size(this.firstPersonParticles.length) + firstPersonParticlesSize;
/*     */     } 
/* 263 */     if (this.trails != null) {
/* 264 */       int trailsSize = 0;
/* 265 */       for (ModelTrail elem : this.trails) trailsSize += elem.computeSize(); 
/* 266 */       size += VarInt.size(this.trails.length) + trailsSize;
/*     */     } 
/* 268 */     if (this.itemPlayerAnimationsId != null) size += PacketIO.stringSize(this.itemPlayerAnimationsId); 
/* 269 */     if (this.itemAnimationId != null) size += PacketIO.stringSize(this.itemAnimationId);
/*     */     
/* 271 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 275 */     if (buffer.readableBytes() - offset < 52) {
/* 276 */       return ValidationResult.error("Buffer too small: expected at least 52 bytes");
/*     */     }
/*     */     
/* 279 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 282 */     if ((nullBits & 0x1) != 0) {
/* 283 */       int particlesOffset = buffer.getIntLE(offset + 32);
/* 284 */       if (particlesOffset < 0) {
/* 285 */         return ValidationResult.error("Invalid offset for Particles");
/*     */       }
/* 287 */       int pos = offset + 52 + particlesOffset;
/* 288 */       if (pos >= buffer.writerIndex()) {
/* 289 */         return ValidationResult.error("Offset out of bounds for Particles");
/*     */       }
/* 291 */       int particlesCount = VarInt.peek(buffer, pos);
/* 292 */       if (particlesCount < 0) {
/* 293 */         return ValidationResult.error("Invalid array count for Particles");
/*     */       }
/* 295 */       if (particlesCount > 4096000) {
/* 296 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*     */       }
/* 298 */       pos += VarInt.length(buffer, pos);
/* 299 */       for (int i = 0; i < particlesCount; i++) {
/* 300 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 301 */         if (!structResult.isValid()) {
/* 302 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*     */         }
/* 304 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 308 */     if ((nullBits & 0x2) != 0) {
/* 309 */       int firstPersonParticlesOffset = buffer.getIntLE(offset + 36);
/* 310 */       if (firstPersonParticlesOffset < 0) {
/* 311 */         return ValidationResult.error("Invalid offset for FirstPersonParticles");
/*     */       }
/* 313 */       int pos = offset + 52 + firstPersonParticlesOffset;
/* 314 */       if (pos >= buffer.writerIndex()) {
/* 315 */         return ValidationResult.error("Offset out of bounds for FirstPersonParticles");
/*     */       }
/* 317 */       int firstPersonParticlesCount = VarInt.peek(buffer, pos);
/* 318 */       if (firstPersonParticlesCount < 0) {
/* 319 */         return ValidationResult.error("Invalid array count for FirstPersonParticles");
/*     */       }
/* 321 */       if (firstPersonParticlesCount > 4096000) {
/* 322 */         return ValidationResult.error("FirstPersonParticles exceeds max length 4096000");
/*     */       }
/* 324 */       pos += VarInt.length(buffer, pos);
/* 325 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 326 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 327 */         if (!structResult.isValid()) {
/* 328 */           return ValidationResult.error("Invalid ModelParticle in FirstPersonParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 330 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 334 */     if ((nullBits & 0x4) != 0) {
/* 335 */       int trailsOffset = buffer.getIntLE(offset + 40);
/* 336 */       if (trailsOffset < 0) {
/* 337 */         return ValidationResult.error("Invalid offset for Trails");
/*     */       }
/* 339 */       int pos = offset + 52 + trailsOffset;
/* 340 */       if (pos >= buffer.writerIndex()) {
/* 341 */         return ValidationResult.error("Offset out of bounds for Trails");
/*     */       }
/* 343 */       int trailsCount = VarInt.peek(buffer, pos);
/* 344 */       if (trailsCount < 0) {
/* 345 */         return ValidationResult.error("Invalid array count for Trails");
/*     */       }
/* 347 */       if (trailsCount > 4096000) {
/* 348 */         return ValidationResult.error("Trails exceeds max length 4096000");
/*     */       }
/* 350 */       pos += VarInt.length(buffer, pos);
/* 351 */       for (int i = 0; i < trailsCount; i++) {
/* 352 */         ValidationResult structResult = ModelTrail.validateStructure(buffer, pos);
/* 353 */         if (!structResult.isValid()) {
/* 354 */           return ValidationResult.error("Invalid ModelTrail in Trails[" + i + "]: " + structResult.error());
/*     */         }
/* 356 */         pos += ModelTrail.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 360 */     if ((nullBits & 0x8) != 0) {
/* 361 */       int itemPlayerAnimationsIdOffset = buffer.getIntLE(offset + 44);
/* 362 */       if (itemPlayerAnimationsIdOffset < 0) {
/* 363 */         return ValidationResult.error("Invalid offset for ItemPlayerAnimationsId");
/*     */       }
/* 365 */       int pos = offset + 52 + itemPlayerAnimationsIdOffset;
/* 366 */       if (pos >= buffer.writerIndex()) {
/* 367 */         return ValidationResult.error("Offset out of bounds for ItemPlayerAnimationsId");
/*     */       }
/* 369 */       int itemPlayerAnimationsIdLen = VarInt.peek(buffer, pos);
/* 370 */       if (itemPlayerAnimationsIdLen < 0) {
/* 371 */         return ValidationResult.error("Invalid string length for ItemPlayerAnimationsId");
/*     */       }
/* 373 */       if (itemPlayerAnimationsIdLen > 4096000) {
/* 374 */         return ValidationResult.error("ItemPlayerAnimationsId exceeds max length 4096000");
/*     */       }
/* 376 */       pos += VarInt.length(buffer, pos);
/* 377 */       pos += itemPlayerAnimationsIdLen;
/* 378 */       if (pos > buffer.writerIndex()) {
/* 379 */         return ValidationResult.error("Buffer overflow reading ItemPlayerAnimationsId");
/*     */       }
/*     */     } 
/*     */     
/* 383 */     if ((nullBits & 0x10) != 0) {
/* 384 */       int itemAnimationIdOffset = buffer.getIntLE(offset + 48);
/* 385 */       if (itemAnimationIdOffset < 0) {
/* 386 */         return ValidationResult.error("Invalid offset for ItemAnimationId");
/*     */       }
/* 388 */       int pos = offset + 52 + itemAnimationIdOffset;
/* 389 */       if (pos >= buffer.writerIndex()) {
/* 390 */         return ValidationResult.error("Offset out of bounds for ItemAnimationId");
/*     */       }
/* 392 */       int itemAnimationIdLen = VarInt.peek(buffer, pos);
/* 393 */       if (itemAnimationIdLen < 0) {
/* 394 */         return ValidationResult.error("Invalid string length for ItemAnimationId");
/*     */       }
/* 396 */       if (itemAnimationIdLen > 4096000) {
/* 397 */         return ValidationResult.error("ItemAnimationId exceeds max length 4096000");
/*     */       }
/* 399 */       pos += VarInt.length(buffer, pos);
/* 400 */       pos += itemAnimationIdLen;
/* 401 */       if (pos > buffer.writerIndex()) {
/* 402 */         return ValidationResult.error("Buffer overflow reading ItemAnimationId");
/*     */       }
/*     */     } 
/* 405 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionEffects clone() {
/* 409 */     InteractionEffects copy = new InteractionEffects();
/* 410 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 411 */     copy.firstPersonParticles = (this.firstPersonParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.firstPersonParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 412 */     copy.worldSoundEventIndex = this.worldSoundEventIndex;
/* 413 */     copy.localSoundEventIndex = this.localSoundEventIndex;
/* 414 */     copy.trails = (this.trails != null) ? (ModelTrail[])Arrays.<ModelTrail>stream(this.trails).map(e -> e.clone()).toArray(x$0 -> new ModelTrail[x$0]) : null;
/* 415 */     copy.waitForAnimationToFinish = this.waitForAnimationToFinish;
/* 416 */     copy.itemPlayerAnimationsId = this.itemPlayerAnimationsId;
/* 417 */     copy.itemAnimationId = this.itemAnimationId;
/* 418 */     copy.clearAnimationOnFinish = this.clearAnimationOnFinish;
/* 419 */     copy.clearSoundEventOnFinish = this.clearSoundEventOnFinish;
/* 420 */     copy.cameraShake = (this.cameraShake != null) ? this.cameraShake.clone() : null;
/* 421 */     copy.movementEffects = (this.movementEffects != null) ? this.movementEffects.clone() : null;
/* 422 */     copy.startDelay = this.startDelay;
/* 423 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionEffects other;
/* 429 */     if (this == obj) return true; 
/* 430 */     if (obj instanceof InteractionEffects) { other = (InteractionEffects)obj; } else { return false; }
/* 431 */      return (Arrays.equals((Object[])this.particles, (Object[])other.particles) && Arrays.equals((Object[])this.firstPersonParticles, (Object[])other.firstPersonParticles) && this.worldSoundEventIndex == other.worldSoundEventIndex && this.localSoundEventIndex == other.localSoundEventIndex && Arrays.equals((Object[])this.trails, (Object[])other.trails) && this.waitForAnimationToFinish == other.waitForAnimationToFinish && Objects.equals(this.itemPlayerAnimationsId, other.itemPlayerAnimationsId) && Objects.equals(this.itemAnimationId, other.itemAnimationId) && this.clearAnimationOnFinish == other.clearAnimationOnFinish && this.clearSoundEventOnFinish == other.clearSoundEventOnFinish && Objects.equals(this.cameraShake, other.cameraShake) && Objects.equals(this.movementEffects, other.movementEffects) && this.startDelay == other.startDelay);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 436 */     int result = 1;
/* 437 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 438 */     result = 31 * result + Arrays.hashCode((Object[])this.firstPersonParticles);
/* 439 */     result = 31 * result + Integer.hashCode(this.worldSoundEventIndex);
/* 440 */     result = 31 * result + Integer.hashCode(this.localSoundEventIndex);
/* 441 */     result = 31 * result + Arrays.hashCode((Object[])this.trails);
/* 442 */     result = 31 * result + Boolean.hashCode(this.waitForAnimationToFinish);
/* 443 */     result = 31 * result + Objects.hashCode(this.itemPlayerAnimationsId);
/* 444 */     result = 31 * result + Objects.hashCode(this.itemAnimationId);
/* 445 */     result = 31 * result + Boolean.hashCode(this.clearAnimationOnFinish);
/* 446 */     result = 31 * result + Boolean.hashCode(this.clearSoundEventOnFinish);
/* 447 */     result = 31 * result + Objects.hashCode(this.cameraShake);
/* 448 */     result = 31 * result + Objects.hashCode(this.movementEffects);
/* 449 */     result = 31 * result + Float.hashCode(this.startDelay);
/* 450 */     return result;
/*     */   }
/*     */   
/*     */   public InteractionEffects() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */