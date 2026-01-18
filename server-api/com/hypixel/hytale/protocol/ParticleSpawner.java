/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ParticleSpawner {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 131;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 147;
/*     */   public static final int MAX_SIZE = 651264332;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nullable
/*     */   public Particle particle;
/*     */   @Nonnull
/*  22 */   public EmitShape shape = EmitShape.Sphere;
/*     */   
/*     */   @Nullable
/*     */   public RangeVector3f emitOffset;
/*     */   
/*     */   public float cameraOffset;
/*     */   
/*     */   public boolean useEmitDirection;
/*     */   public float lifeSpan;
/*     */   @Nullable
/*     */   public Rangef spawnRate;
/*     */   @Nonnull
/*  34 */   public ParticleRotationInfluence particleRotationInfluence = ParticleRotationInfluence.None; public boolean spawnBurst; @Nullable
/*     */   public Rangef waveDelay; @Nullable
/*     */   public Range totalParticles; public int maxConcurrentParticles; @Nullable
/*     */   public InitialVelocity initialVelocity; public float velocityStretchMultiplier; public boolean particleRotateWithSpawner; public boolean isLowRes; public float trailSpawnerPositionMultiplier; public float trailSpawnerRotationMultiplier; @Nullable
/*     */   public ParticleCollision particleCollision;
/*     */   @Nonnull
/*  40 */   public FXRenderMode renderMode = FXRenderMode.BlendLinear; public float lightInfluence;
/*     */   public boolean linearFiltering;
/*     */   @Nullable
/*     */   public Rangef particleLifeSpan;
/*     */   @Nullable
/*     */   public UVMotion uvMotion;
/*     */   @Nullable
/*     */   public ParticleAttractor[] attractors;
/*     */   @Nullable
/*     */   public IntersectionHighlight intersectionHighlight;
/*     */   
/*     */   public ParticleSpawner(@Nullable String id, @Nullable Particle particle, @Nonnull EmitShape shape, @Nullable RangeVector3f emitOffset, float cameraOffset, boolean useEmitDirection, float lifeSpan, @Nullable Rangef spawnRate, boolean spawnBurst, @Nullable Rangef waveDelay, @Nullable Range totalParticles, int maxConcurrentParticles, @Nullable InitialVelocity initialVelocity, float velocityStretchMultiplier, @Nonnull ParticleRotationInfluence particleRotationInfluence, boolean particleRotateWithSpawner, boolean isLowRes, float trailSpawnerPositionMultiplier, float trailSpawnerRotationMultiplier, @Nullable ParticleCollision particleCollision, @Nonnull FXRenderMode renderMode, float lightInfluence, boolean linearFiltering, @Nullable Rangef particleLifeSpan, @Nullable UVMotion uvMotion, @Nullable ParticleAttractor[] attractors, @Nullable IntersectionHighlight intersectionHighlight) {
/*  52 */     this.id = id;
/*  53 */     this.particle = particle;
/*  54 */     this.shape = shape;
/*  55 */     this.emitOffset = emitOffset;
/*  56 */     this.cameraOffset = cameraOffset;
/*  57 */     this.useEmitDirection = useEmitDirection;
/*  58 */     this.lifeSpan = lifeSpan;
/*  59 */     this.spawnRate = spawnRate;
/*  60 */     this.spawnBurst = spawnBurst;
/*  61 */     this.waveDelay = waveDelay;
/*  62 */     this.totalParticles = totalParticles;
/*  63 */     this.maxConcurrentParticles = maxConcurrentParticles;
/*  64 */     this.initialVelocity = initialVelocity;
/*  65 */     this.velocityStretchMultiplier = velocityStretchMultiplier;
/*  66 */     this.particleRotationInfluence = particleRotationInfluence;
/*  67 */     this.particleRotateWithSpawner = particleRotateWithSpawner;
/*  68 */     this.isLowRes = isLowRes;
/*  69 */     this.trailSpawnerPositionMultiplier = trailSpawnerPositionMultiplier;
/*  70 */     this.trailSpawnerRotationMultiplier = trailSpawnerRotationMultiplier;
/*  71 */     this.particleCollision = particleCollision;
/*  72 */     this.renderMode = renderMode;
/*  73 */     this.lightInfluence = lightInfluence;
/*  74 */     this.linearFiltering = linearFiltering;
/*  75 */     this.particleLifeSpan = particleLifeSpan;
/*  76 */     this.uvMotion = uvMotion;
/*  77 */     this.attractors = attractors;
/*  78 */     this.intersectionHighlight = intersectionHighlight;
/*     */   }
/*     */   
/*     */   public ParticleSpawner(@Nonnull ParticleSpawner other) {
/*  82 */     this.id = other.id;
/*  83 */     this.particle = other.particle;
/*  84 */     this.shape = other.shape;
/*  85 */     this.emitOffset = other.emitOffset;
/*  86 */     this.cameraOffset = other.cameraOffset;
/*  87 */     this.useEmitDirection = other.useEmitDirection;
/*  88 */     this.lifeSpan = other.lifeSpan;
/*  89 */     this.spawnRate = other.spawnRate;
/*  90 */     this.spawnBurst = other.spawnBurst;
/*  91 */     this.waveDelay = other.waveDelay;
/*  92 */     this.totalParticles = other.totalParticles;
/*  93 */     this.maxConcurrentParticles = other.maxConcurrentParticles;
/*  94 */     this.initialVelocity = other.initialVelocity;
/*  95 */     this.velocityStretchMultiplier = other.velocityStretchMultiplier;
/*  96 */     this.particleRotationInfluence = other.particleRotationInfluence;
/*  97 */     this.particleRotateWithSpawner = other.particleRotateWithSpawner;
/*  98 */     this.isLowRes = other.isLowRes;
/*  99 */     this.trailSpawnerPositionMultiplier = other.trailSpawnerPositionMultiplier;
/* 100 */     this.trailSpawnerRotationMultiplier = other.trailSpawnerRotationMultiplier;
/* 101 */     this.particleCollision = other.particleCollision;
/* 102 */     this.renderMode = other.renderMode;
/* 103 */     this.lightInfluence = other.lightInfluence;
/* 104 */     this.linearFiltering = other.linearFiltering;
/* 105 */     this.particleLifeSpan = other.particleLifeSpan;
/* 106 */     this.uvMotion = other.uvMotion;
/* 107 */     this.attractors = other.attractors;
/* 108 */     this.intersectionHighlight = other.intersectionHighlight;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ParticleSpawner deserialize(@Nonnull ByteBuf buf, int offset) {
/* 113 */     ParticleSpawner obj = new ParticleSpawner();
/* 114 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 115 */     obj.shape = EmitShape.fromValue(buf.getByte(offset + 2));
/* 116 */     if ((nullBits[0] & 0x4) != 0) obj.emitOffset = RangeVector3f.deserialize(buf, offset + 3); 
/* 117 */     obj.cameraOffset = buf.getFloatLE(offset + 28);
/* 118 */     obj.useEmitDirection = (buf.getByte(offset + 32) != 0);
/* 119 */     obj.lifeSpan = buf.getFloatLE(offset + 33);
/* 120 */     if ((nullBits[0] & 0x8) != 0) obj.spawnRate = Rangef.deserialize(buf, offset + 37); 
/* 121 */     obj.spawnBurst = (buf.getByte(offset + 45) != 0);
/* 122 */     if ((nullBits[0] & 0x10) != 0) obj.waveDelay = Rangef.deserialize(buf, offset + 46); 
/* 123 */     if ((nullBits[0] & 0x20) != 0) obj.totalParticles = Range.deserialize(buf, offset + 54); 
/* 124 */     obj.maxConcurrentParticles = buf.getIntLE(offset + 62);
/* 125 */     if ((nullBits[0] & 0x40) != 0) obj.initialVelocity = InitialVelocity.deserialize(buf, offset + 66); 
/* 126 */     obj.velocityStretchMultiplier = buf.getFloatLE(offset + 91);
/* 127 */     obj.particleRotationInfluence = ParticleRotationInfluence.fromValue(buf.getByte(offset + 95));
/* 128 */     obj.particleRotateWithSpawner = (buf.getByte(offset + 96) != 0);
/* 129 */     obj.isLowRes = (buf.getByte(offset + 97) != 0);
/* 130 */     obj.trailSpawnerPositionMultiplier = buf.getFloatLE(offset + 98);
/* 131 */     obj.trailSpawnerRotationMultiplier = buf.getFloatLE(offset + 102);
/* 132 */     if ((nullBits[0] & 0x80) != 0) obj.particleCollision = ParticleCollision.deserialize(buf, offset + 106); 
/* 133 */     obj.renderMode = FXRenderMode.fromValue(buf.getByte(offset + 109));
/* 134 */     obj.lightInfluence = buf.getFloatLE(offset + 110);
/* 135 */     obj.linearFiltering = (buf.getByte(offset + 114) != 0);
/* 136 */     if ((nullBits[1] & 0x1) != 0) obj.particleLifeSpan = Rangef.deserialize(buf, offset + 115); 
/* 137 */     if ((nullBits[1] & 0x8) != 0) obj.intersectionHighlight = IntersectionHighlight.deserialize(buf, offset + 123);
/*     */     
/* 139 */     if ((nullBits[0] & 0x1) != 0) {
/* 140 */       int varPos0 = offset + 147 + buf.getIntLE(offset + 131);
/* 141 */       int idLen = VarInt.peek(buf, varPos0);
/* 142 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/* 143 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/* 144 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/* 146 */     if ((nullBits[0] & 0x2) != 0) {
/* 147 */       int varPos1 = offset + 147 + buf.getIntLE(offset + 135);
/* 148 */       obj.particle = Particle.deserialize(buf, varPos1);
/*     */     } 
/* 150 */     if ((nullBits[1] & 0x2) != 0) {
/* 151 */       int varPos2 = offset + 147 + buf.getIntLE(offset + 139);
/* 152 */       obj.uvMotion = UVMotion.deserialize(buf, varPos2);
/*     */     } 
/* 154 */     if ((nullBits[1] & 0x4) != 0) {
/* 155 */       int varPos3 = offset + 147 + buf.getIntLE(offset + 143);
/* 156 */       int attractorsCount = VarInt.peek(buf, varPos3);
/* 157 */       if (attractorsCount < 0) throw ProtocolException.negativeLength("Attractors", attractorsCount); 
/* 158 */       if (attractorsCount > 4096000) throw ProtocolException.arrayTooLong("Attractors", attractorsCount, 4096000); 
/* 159 */       int varIntLen = VarInt.length(buf, varPos3);
/* 160 */       if ((varPos3 + varIntLen) + attractorsCount * 85L > buf.readableBytes())
/* 161 */         throw ProtocolException.bufferTooSmall("Attractors", varPos3 + varIntLen + attractorsCount * 85, buf.readableBytes()); 
/* 162 */       obj.attractors = new ParticleAttractor[attractorsCount];
/* 163 */       int elemPos = varPos3 + varIntLen;
/* 164 */       for (int i = 0; i < attractorsCount; i++) {
/* 165 */         obj.attractors[i] = ParticleAttractor.deserialize(buf, elemPos);
/* 166 */         elemPos += ParticleAttractor.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 174 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 175 */     int maxEnd = 147;
/* 176 */     if ((nullBits[0] & 0x1) != 0) {
/* 177 */       int fieldOffset0 = buf.getIntLE(offset + 131);
/* 178 */       int pos0 = offset + 147 + fieldOffset0;
/* 179 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 180 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 182 */     if ((nullBits[0] & 0x2) != 0) {
/* 183 */       int fieldOffset1 = buf.getIntLE(offset + 135);
/* 184 */       int pos1 = offset + 147 + fieldOffset1;
/* 185 */       pos1 += Particle.computeBytesConsumed(buf, pos1);
/* 186 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 188 */     if ((nullBits[1] & 0x2) != 0) {
/* 189 */       int fieldOffset2 = buf.getIntLE(offset + 139);
/* 190 */       int pos2 = offset + 147 + fieldOffset2;
/* 191 */       pos2 += UVMotion.computeBytesConsumed(buf, pos2);
/* 192 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 194 */     if ((nullBits[1] & 0x4) != 0) {
/* 195 */       int fieldOffset3 = buf.getIntLE(offset + 143);
/* 196 */       int pos3 = offset + 147 + fieldOffset3;
/* 197 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 198 */       for (int i = 0; i < arrLen; ) { pos3 += ParticleAttractor.computeBytesConsumed(buf, pos3); i++; }
/* 199 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 201 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 206 */     int startPos = buf.writerIndex();
/* 207 */     byte[] nullBits = new byte[2];
/* 208 */     if (this.id != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 209 */     if (this.particle != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 210 */     if (this.emitOffset != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 211 */     if (this.spawnRate != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 212 */     if (this.waveDelay != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 213 */     if (this.totalParticles != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 214 */     if (this.initialVelocity != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 215 */     if (this.particleCollision != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 216 */     if (this.particleLifeSpan != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 217 */     if (this.uvMotion != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 218 */     if (this.attractors != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/* 219 */     if (this.intersectionHighlight != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/* 220 */     buf.writeBytes(nullBits);
/*     */     
/* 222 */     buf.writeByte(this.shape.getValue());
/* 223 */     if (this.emitOffset != null) { this.emitOffset.serialize(buf); } else { buf.writeZero(25); }
/* 224 */      buf.writeFloatLE(this.cameraOffset);
/* 225 */     buf.writeByte(this.useEmitDirection ? 1 : 0);
/* 226 */     buf.writeFloatLE(this.lifeSpan);
/* 227 */     if (this.spawnRate != null) { this.spawnRate.serialize(buf); } else { buf.writeZero(8); }
/* 228 */      buf.writeByte(this.spawnBurst ? 1 : 0);
/* 229 */     if (this.waveDelay != null) { this.waveDelay.serialize(buf); } else { buf.writeZero(8); }
/* 230 */      if (this.totalParticles != null) { this.totalParticles.serialize(buf); } else { buf.writeZero(8); }
/* 231 */      buf.writeIntLE(this.maxConcurrentParticles);
/* 232 */     if (this.initialVelocity != null) { this.initialVelocity.serialize(buf); } else { buf.writeZero(25); }
/* 233 */      buf.writeFloatLE(this.velocityStretchMultiplier);
/* 234 */     buf.writeByte(this.particleRotationInfluence.getValue());
/* 235 */     buf.writeByte(this.particleRotateWithSpawner ? 1 : 0);
/* 236 */     buf.writeByte(this.isLowRes ? 1 : 0);
/* 237 */     buf.writeFloatLE(this.trailSpawnerPositionMultiplier);
/* 238 */     buf.writeFloatLE(this.trailSpawnerRotationMultiplier);
/* 239 */     if (this.particleCollision != null) { this.particleCollision.serialize(buf); } else { buf.writeZero(3); }
/* 240 */      buf.writeByte(this.renderMode.getValue());
/* 241 */     buf.writeFloatLE(this.lightInfluence);
/* 242 */     buf.writeByte(this.linearFiltering ? 1 : 0);
/* 243 */     if (this.particleLifeSpan != null) { this.particleLifeSpan.serialize(buf); } else { buf.writeZero(8); }
/* 244 */      if (this.intersectionHighlight != null) { this.intersectionHighlight.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/* 246 */     int idOffsetSlot = buf.writerIndex();
/* 247 */     buf.writeIntLE(0);
/* 248 */     int particleOffsetSlot = buf.writerIndex();
/* 249 */     buf.writeIntLE(0);
/* 250 */     int uvMotionOffsetSlot = buf.writerIndex();
/* 251 */     buf.writeIntLE(0);
/* 252 */     int attractorsOffsetSlot = buf.writerIndex();
/* 253 */     buf.writeIntLE(0);
/*     */     
/* 255 */     int varBlockStart = buf.writerIndex();
/* 256 */     if (this.id != null) {
/* 257 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 258 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 260 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 262 */     if (this.particle != null) {
/* 263 */       buf.setIntLE(particleOffsetSlot, buf.writerIndex() - varBlockStart);
/* 264 */       this.particle.serialize(buf);
/*     */     } else {
/* 266 */       buf.setIntLE(particleOffsetSlot, -1);
/*     */     } 
/* 268 */     if (this.uvMotion != null) {
/* 269 */       buf.setIntLE(uvMotionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 270 */       this.uvMotion.serialize(buf);
/*     */     } else {
/* 272 */       buf.setIntLE(uvMotionOffsetSlot, -1);
/*     */     } 
/* 274 */     if (this.attractors != null) {
/* 275 */       buf.setIntLE(attractorsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 276 */       if (this.attractors.length > 4096000) throw ProtocolException.arrayTooLong("Attractors", this.attractors.length, 4096000);  VarInt.write(buf, this.attractors.length); for (ParticleAttractor item : this.attractors) item.serialize(buf); 
/*     */     } else {
/* 278 */       buf.setIntLE(attractorsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 284 */     int size = 147;
/* 285 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 286 */     if (this.particle != null) size += this.particle.computeSize(); 
/* 287 */     if (this.uvMotion != null) size += this.uvMotion.computeSize(); 
/* 288 */     if (this.attractors != null) size += VarInt.size(this.attractors.length) + this.attractors.length * 85;
/*     */     
/* 290 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 294 */     if (buffer.readableBytes() - offset < 147) {
/* 295 */       return ValidationResult.error("Buffer too small: expected at least 147 bytes");
/*     */     }
/*     */     
/* 298 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 300 */     if ((nullBits[0] & 0x1) != 0) {
/* 301 */       int idOffset = buffer.getIntLE(offset + 131);
/* 302 */       if (idOffset < 0) {
/* 303 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 305 */       int pos = offset + 147 + idOffset;
/* 306 */       if (pos >= buffer.writerIndex()) {
/* 307 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 309 */       int idLen = VarInt.peek(buffer, pos);
/* 310 */       if (idLen < 0) {
/* 311 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 313 */       if (idLen > 4096000) {
/* 314 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 316 */       pos += VarInt.length(buffer, pos);
/* 317 */       pos += idLen;
/* 318 */       if (pos > buffer.writerIndex()) {
/* 319 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 323 */     if ((nullBits[0] & 0x2) != 0) {
/* 324 */       int particleOffset = buffer.getIntLE(offset + 135);
/* 325 */       if (particleOffset < 0) {
/* 326 */         return ValidationResult.error("Invalid offset for Particle");
/*     */       }
/* 328 */       int pos = offset + 147 + particleOffset;
/* 329 */       if (pos >= buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Offset out of bounds for Particle");
/*     */       }
/* 332 */       ValidationResult particleResult = Particle.validateStructure(buffer, pos);
/* 333 */       if (!particleResult.isValid()) {
/* 334 */         return ValidationResult.error("Invalid Particle: " + particleResult.error());
/*     */       }
/* 336 */       pos += Particle.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 339 */     if ((nullBits[1] & 0x2) != 0) {
/* 340 */       int uvMotionOffset = buffer.getIntLE(offset + 139);
/* 341 */       if (uvMotionOffset < 0) {
/* 342 */         return ValidationResult.error("Invalid offset for UvMotion");
/*     */       }
/* 344 */       int pos = offset + 147 + uvMotionOffset;
/* 345 */       if (pos >= buffer.writerIndex()) {
/* 346 */         return ValidationResult.error("Offset out of bounds for UvMotion");
/*     */       }
/* 348 */       ValidationResult uvMotionResult = UVMotion.validateStructure(buffer, pos);
/* 349 */       if (!uvMotionResult.isValid()) {
/* 350 */         return ValidationResult.error("Invalid UvMotion: " + uvMotionResult.error());
/*     */       }
/* 352 */       pos += UVMotion.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 355 */     if ((nullBits[1] & 0x4) != 0) {
/* 356 */       int attractorsOffset = buffer.getIntLE(offset + 143);
/* 357 */       if (attractorsOffset < 0) {
/* 358 */         return ValidationResult.error("Invalid offset for Attractors");
/*     */       }
/* 360 */       int pos = offset + 147 + attractorsOffset;
/* 361 */       if (pos >= buffer.writerIndex()) {
/* 362 */         return ValidationResult.error("Offset out of bounds for Attractors");
/*     */       }
/* 364 */       int attractorsCount = VarInt.peek(buffer, pos);
/* 365 */       if (attractorsCount < 0) {
/* 366 */         return ValidationResult.error("Invalid array count for Attractors");
/*     */       }
/* 368 */       if (attractorsCount > 4096000) {
/* 369 */         return ValidationResult.error("Attractors exceeds max length 4096000");
/*     */       }
/* 371 */       pos += VarInt.length(buffer, pos);
/* 372 */       pos += attractorsCount * 85;
/* 373 */       if (pos > buffer.writerIndex()) {
/* 374 */         return ValidationResult.error("Buffer overflow reading Attractors");
/*     */       }
/*     */     } 
/* 377 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ParticleSpawner clone() {
/* 381 */     ParticleSpawner copy = new ParticleSpawner();
/* 382 */     copy.id = this.id;
/* 383 */     copy.particle = (this.particle != null) ? this.particle.clone() : null;
/* 384 */     copy.shape = this.shape;
/* 385 */     copy.emitOffset = (this.emitOffset != null) ? this.emitOffset.clone() : null;
/* 386 */     copy.cameraOffset = this.cameraOffset;
/* 387 */     copy.useEmitDirection = this.useEmitDirection;
/* 388 */     copy.lifeSpan = this.lifeSpan;
/* 389 */     copy.spawnRate = (this.spawnRate != null) ? this.spawnRate.clone() : null;
/* 390 */     copy.spawnBurst = this.spawnBurst;
/* 391 */     copy.waveDelay = (this.waveDelay != null) ? this.waveDelay.clone() : null;
/* 392 */     copy.totalParticles = (this.totalParticles != null) ? this.totalParticles.clone() : null;
/* 393 */     copy.maxConcurrentParticles = this.maxConcurrentParticles;
/* 394 */     copy.initialVelocity = (this.initialVelocity != null) ? this.initialVelocity.clone() : null;
/* 395 */     copy.velocityStretchMultiplier = this.velocityStretchMultiplier;
/* 396 */     copy.particleRotationInfluence = this.particleRotationInfluence;
/* 397 */     copy.particleRotateWithSpawner = this.particleRotateWithSpawner;
/* 398 */     copy.isLowRes = this.isLowRes;
/* 399 */     copy.trailSpawnerPositionMultiplier = this.trailSpawnerPositionMultiplier;
/* 400 */     copy.trailSpawnerRotationMultiplier = this.trailSpawnerRotationMultiplier;
/* 401 */     copy.particleCollision = (this.particleCollision != null) ? this.particleCollision.clone() : null;
/* 402 */     copy.renderMode = this.renderMode;
/* 403 */     copy.lightInfluence = this.lightInfluence;
/* 404 */     copy.linearFiltering = this.linearFiltering;
/* 405 */     copy.particleLifeSpan = (this.particleLifeSpan != null) ? this.particleLifeSpan.clone() : null;
/* 406 */     copy.uvMotion = (this.uvMotion != null) ? this.uvMotion.clone() : null;
/* 407 */     copy.attractors = (this.attractors != null) ? (ParticleAttractor[])Arrays.<ParticleAttractor>stream(this.attractors).map(e -> e.clone()).toArray(x$0 -> new ParticleAttractor[x$0]) : null;
/* 408 */     copy.intersectionHighlight = (this.intersectionHighlight != null) ? this.intersectionHighlight.clone() : null;
/* 409 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ParticleSpawner other;
/* 415 */     if (this == obj) return true; 
/* 416 */     if (obj instanceof ParticleSpawner) { other = (ParticleSpawner)obj; } else { return false; }
/* 417 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.particle, other.particle) && Objects.equals(this.shape, other.shape) && Objects.equals(this.emitOffset, other.emitOffset) && this.cameraOffset == other.cameraOffset && this.useEmitDirection == other.useEmitDirection && this.lifeSpan == other.lifeSpan && Objects.equals(this.spawnRate, other.spawnRate) && this.spawnBurst == other.spawnBurst && Objects.equals(this.waveDelay, other.waveDelay) && Objects.equals(this.totalParticles, other.totalParticles) && this.maxConcurrentParticles == other.maxConcurrentParticles && Objects.equals(this.initialVelocity, other.initialVelocity) && this.velocityStretchMultiplier == other.velocityStretchMultiplier && Objects.equals(this.particleRotationInfluence, other.particleRotationInfluence) && this.particleRotateWithSpawner == other.particleRotateWithSpawner && this.isLowRes == other.isLowRes && this.trailSpawnerPositionMultiplier == other.trailSpawnerPositionMultiplier && this.trailSpawnerRotationMultiplier == other.trailSpawnerRotationMultiplier && Objects.equals(this.particleCollision, other.particleCollision) && Objects.equals(this.renderMode, other.renderMode) && this.lightInfluence == other.lightInfluence && this.linearFiltering == other.linearFiltering && Objects.equals(this.particleLifeSpan, other.particleLifeSpan) && Objects.equals(this.uvMotion, other.uvMotion) && Arrays.equals((Object[])this.attractors, (Object[])other.attractors) && Objects.equals(this.intersectionHighlight, other.intersectionHighlight));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 422 */     int result = 1;
/* 423 */     result = 31 * result + Objects.hashCode(this.id);
/* 424 */     result = 31 * result + Objects.hashCode(this.particle);
/* 425 */     result = 31 * result + Objects.hashCode(this.shape);
/* 426 */     result = 31 * result + Objects.hashCode(this.emitOffset);
/* 427 */     result = 31 * result + Float.hashCode(this.cameraOffset);
/* 428 */     result = 31 * result + Boolean.hashCode(this.useEmitDirection);
/* 429 */     result = 31 * result + Float.hashCode(this.lifeSpan);
/* 430 */     result = 31 * result + Objects.hashCode(this.spawnRate);
/* 431 */     result = 31 * result + Boolean.hashCode(this.spawnBurst);
/* 432 */     result = 31 * result + Objects.hashCode(this.waveDelay);
/* 433 */     result = 31 * result + Objects.hashCode(this.totalParticles);
/* 434 */     result = 31 * result + Integer.hashCode(this.maxConcurrentParticles);
/* 435 */     result = 31 * result + Objects.hashCode(this.initialVelocity);
/* 436 */     result = 31 * result + Float.hashCode(this.velocityStretchMultiplier);
/* 437 */     result = 31 * result + Objects.hashCode(this.particleRotationInfluence);
/* 438 */     result = 31 * result + Boolean.hashCode(this.particleRotateWithSpawner);
/* 439 */     result = 31 * result + Boolean.hashCode(this.isLowRes);
/* 440 */     result = 31 * result + Float.hashCode(this.trailSpawnerPositionMultiplier);
/* 441 */     result = 31 * result + Float.hashCode(this.trailSpawnerRotationMultiplier);
/* 442 */     result = 31 * result + Objects.hashCode(this.particleCollision);
/* 443 */     result = 31 * result + Objects.hashCode(this.renderMode);
/* 444 */     result = 31 * result + Float.hashCode(this.lightInfluence);
/* 445 */     result = 31 * result + Boolean.hashCode(this.linearFiltering);
/* 446 */     result = 31 * result + Objects.hashCode(this.particleLifeSpan);
/* 447 */     result = 31 * result + Objects.hashCode(this.uvMotion);
/* 448 */     result = 31 * result + Arrays.hashCode((Object[])this.attractors);
/* 449 */     result = 31 * result + Objects.hashCode(this.intersectionHighlight);
/* 450 */     return result;
/*     */   }
/*     */   
/*     */   public ParticleSpawner() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ParticleSpawner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */