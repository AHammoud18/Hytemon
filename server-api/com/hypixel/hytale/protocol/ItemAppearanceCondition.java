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
/*     */ public class ItemAppearanceCondition
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 18;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 38;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  26 */   public ValueType conditionValueType = ValueType.Percent; @Nullable
/*     */   public ModelParticle[] particles; @Nullable
/*     */   public ModelParticle[] firstPersonParticles; @Nullable
/*     */   public String model; @Nullable
/*     */   public String texture; @Nullable
/*     */   public String modelVFXId; @Nullable
/*     */   public FloatRange condition; public int localSoundEventId; public int worldSoundEventId;
/*     */   public ItemAppearanceCondition(@Nullable ModelParticle[] particles, @Nullable ModelParticle[] firstPersonParticles, @Nullable String model, @Nullable String texture, @Nullable String modelVFXId, @Nullable FloatRange condition, @Nonnull ValueType conditionValueType, int localSoundEventId, int worldSoundEventId) {
/*  34 */     this.particles = particles;
/*  35 */     this.firstPersonParticles = firstPersonParticles;
/*  36 */     this.model = model;
/*  37 */     this.texture = texture;
/*  38 */     this.modelVFXId = modelVFXId;
/*  39 */     this.condition = condition;
/*  40 */     this.conditionValueType = conditionValueType;
/*  41 */     this.localSoundEventId = localSoundEventId;
/*  42 */     this.worldSoundEventId = worldSoundEventId;
/*     */   }
/*     */   
/*     */   public ItemAppearanceCondition(@Nonnull ItemAppearanceCondition other) {
/*  46 */     this.particles = other.particles;
/*  47 */     this.firstPersonParticles = other.firstPersonParticles;
/*  48 */     this.model = other.model;
/*  49 */     this.texture = other.texture;
/*  50 */     this.modelVFXId = other.modelVFXId;
/*  51 */     this.condition = other.condition;
/*  52 */     this.conditionValueType = other.conditionValueType;
/*  53 */     this.localSoundEventId = other.localSoundEventId;
/*  54 */     this.worldSoundEventId = other.worldSoundEventId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemAppearanceCondition deserialize(@Nonnull ByteBuf buf, int offset) {
/*  59 */     ItemAppearanceCondition obj = new ItemAppearanceCondition();
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     if ((nullBits & 0x20) != 0) obj.condition = FloatRange.deserialize(buf, offset + 1); 
/*  62 */     obj.conditionValueType = ValueType.fromValue(buf.getByte(offset + 9));
/*  63 */     obj.localSoundEventId = buf.getIntLE(offset + 10);
/*  64 */     obj.worldSoundEventId = buf.getIntLE(offset + 14);
/*     */     
/*  66 */     if ((nullBits & 0x1) != 0) {
/*  67 */       int varPos0 = offset + 38 + buf.getIntLE(offset + 18);
/*  68 */       int particlesCount = VarInt.peek(buf, varPos0);
/*  69 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  70 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  71 */       int varIntLen = VarInt.length(buf, varPos0);
/*  72 */       if ((varPos0 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/*  73 */         throw ProtocolException.bufferTooSmall("Particles", varPos0 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/*  74 */       obj.particles = new ModelParticle[particlesCount];
/*  75 */       int elemPos = varPos0 + varIntLen;
/*  76 */       for (int i = 0; i < particlesCount; i++) {
/*  77 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/*  78 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  81 */     if ((nullBits & 0x2) != 0) {
/*  82 */       int varPos1 = offset + 38 + buf.getIntLE(offset + 22);
/*  83 */       int firstPersonParticlesCount = VarInt.peek(buf, varPos1);
/*  84 */       if (firstPersonParticlesCount < 0) throw ProtocolException.negativeLength("FirstPersonParticles", firstPersonParticlesCount); 
/*  85 */       if (firstPersonParticlesCount > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", firstPersonParticlesCount, 4096000); 
/*  86 */       int varIntLen = VarInt.length(buf, varPos1);
/*  87 */       if ((varPos1 + varIntLen) + firstPersonParticlesCount * 34L > buf.readableBytes())
/*  88 */         throw ProtocolException.bufferTooSmall("FirstPersonParticles", varPos1 + varIntLen + firstPersonParticlesCount * 34, buf.readableBytes()); 
/*  89 */       obj.firstPersonParticles = new ModelParticle[firstPersonParticlesCount];
/*  90 */       int elemPos = varPos1 + varIntLen;
/*  91 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/*  92 */         obj.firstPersonParticles[i] = ModelParticle.deserialize(buf, elemPos);
/*  93 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  96 */     if ((nullBits & 0x4) != 0) {
/*  97 */       int varPos2 = offset + 38 + buf.getIntLE(offset + 26);
/*  98 */       int modelLen = VarInt.peek(buf, varPos2);
/*  99 */       if (modelLen < 0) throw ProtocolException.negativeLength("Model", modelLen); 
/* 100 */       if (modelLen > 4096000) throw ProtocolException.stringTooLong("Model", modelLen, 4096000); 
/* 101 */       obj.model = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/* 103 */     if ((nullBits & 0x8) != 0) {
/* 104 */       int varPos3 = offset + 38 + buf.getIntLE(offset + 30);
/* 105 */       int textureLen = VarInt.peek(buf, varPos3);
/* 106 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/* 107 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/* 108 */       obj.texture = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 110 */     if ((nullBits & 0x10) != 0) {
/* 111 */       int varPos4 = offset + 38 + buf.getIntLE(offset + 34);
/* 112 */       int modelVFXIdLen = VarInt.peek(buf, varPos4);
/* 113 */       if (modelVFXIdLen < 0) throw ProtocolException.negativeLength("ModelVFXId", modelVFXIdLen); 
/* 114 */       if (modelVFXIdLen > 4096000) throw ProtocolException.stringTooLong("ModelVFXId", modelVFXIdLen, 4096000); 
/* 115 */       obj.modelVFXId = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 118 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 122 */     byte nullBits = buf.getByte(offset);
/* 123 */     int maxEnd = 38;
/* 124 */     if ((nullBits & 0x1) != 0) {
/* 125 */       int fieldOffset0 = buf.getIntLE(offset + 18);
/* 126 */       int pos0 = offset + 38 + fieldOffset0;
/* 127 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 128 */       for (int i = 0; i < arrLen; ) { pos0 += ModelParticle.computeBytesConsumed(buf, pos0); i++; }
/* 129 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 131 */     if ((nullBits & 0x2) != 0) {
/* 132 */       int fieldOffset1 = buf.getIntLE(offset + 22);
/* 133 */       int pos1 = offset + 38 + fieldOffset1;
/* 134 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 135 */       for (int i = 0; i < arrLen; ) { pos1 += ModelParticle.computeBytesConsumed(buf, pos1); i++; }
/* 136 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 138 */     if ((nullBits & 0x4) != 0) {
/* 139 */       int fieldOffset2 = buf.getIntLE(offset + 26);
/* 140 */       int pos2 = offset + 38 + fieldOffset2;
/* 141 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 142 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 144 */     if ((nullBits & 0x8) != 0) {
/* 145 */       int fieldOffset3 = buf.getIntLE(offset + 30);
/* 146 */       int pos3 = offset + 38 + fieldOffset3;
/* 147 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 148 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 150 */     if ((nullBits & 0x10) != 0) {
/* 151 */       int fieldOffset4 = buf.getIntLE(offset + 34);
/* 152 */       int pos4 = offset + 38 + fieldOffset4;
/* 153 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 154 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 156 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 161 */     int startPos = buf.writerIndex();
/* 162 */     byte nullBits = 0;
/* 163 */     if (this.particles != null) nullBits = (byte)(nullBits | 0x1); 
/* 164 */     if (this.firstPersonParticles != null) nullBits = (byte)(nullBits | 0x2); 
/* 165 */     if (this.model != null) nullBits = (byte)(nullBits | 0x4); 
/* 166 */     if (this.texture != null) nullBits = (byte)(nullBits | 0x8); 
/* 167 */     if (this.modelVFXId != null) nullBits = (byte)(nullBits | 0x10); 
/* 168 */     if (this.condition != null) nullBits = (byte)(nullBits | 0x20); 
/* 169 */     buf.writeByte(nullBits);
/*     */     
/* 171 */     if (this.condition != null) { this.condition.serialize(buf); } else { buf.writeZero(8); }
/* 172 */      buf.writeByte(this.conditionValueType.getValue());
/* 173 */     buf.writeIntLE(this.localSoundEventId);
/* 174 */     buf.writeIntLE(this.worldSoundEventId);
/*     */     
/* 176 */     int particlesOffsetSlot = buf.writerIndex();
/* 177 */     buf.writeIntLE(0);
/* 178 */     int firstPersonParticlesOffsetSlot = buf.writerIndex();
/* 179 */     buf.writeIntLE(0);
/* 180 */     int modelOffsetSlot = buf.writerIndex();
/* 181 */     buf.writeIntLE(0);
/* 182 */     int textureOffsetSlot = buf.writerIndex();
/* 183 */     buf.writeIntLE(0);
/* 184 */     int modelVFXIdOffsetSlot = buf.writerIndex();
/* 185 */     buf.writeIntLE(0);
/*     */     
/* 187 */     int varBlockStart = buf.writerIndex();
/* 188 */     if (this.particles != null) {
/* 189 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 190 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*     */     } else {
/* 192 */       buf.setIntLE(particlesOffsetSlot, -1);
/*     */     } 
/* 194 */     if (this.firstPersonParticles != null) {
/* 195 */       buf.setIntLE(firstPersonParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 196 */       if (this.firstPersonParticles.length > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", this.firstPersonParticles.length, 4096000);  VarInt.write(buf, this.firstPersonParticles.length); for (ModelParticle item : this.firstPersonParticles) item.serialize(buf); 
/*     */     } else {
/* 198 */       buf.setIntLE(firstPersonParticlesOffsetSlot, -1);
/*     */     } 
/* 200 */     if (this.model != null) {
/* 201 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/* 202 */       PacketIO.writeVarString(buf, this.model, 4096000);
/*     */     } else {
/* 204 */       buf.setIntLE(modelOffsetSlot, -1);
/*     */     } 
/* 206 */     if (this.texture != null) {
/* 207 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/* 208 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*     */     } else {
/* 210 */       buf.setIntLE(textureOffsetSlot, -1);
/*     */     } 
/* 212 */     if (this.modelVFXId != null) {
/* 213 */       buf.setIntLE(modelVFXIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 214 */       PacketIO.writeVarString(buf, this.modelVFXId, 4096000);
/*     */     } else {
/* 216 */       buf.setIntLE(modelVFXIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 222 */     int size = 38;
/* 223 */     if (this.particles != null) {
/* 224 */       int particlesSize = 0;
/* 225 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/* 226 */       size += VarInt.size(this.particles.length) + particlesSize;
/*     */     } 
/* 228 */     if (this.firstPersonParticles != null) {
/* 229 */       int firstPersonParticlesSize = 0;
/* 230 */       for (ModelParticle elem : this.firstPersonParticles) firstPersonParticlesSize += elem.computeSize(); 
/* 231 */       size += VarInt.size(this.firstPersonParticles.length) + firstPersonParticlesSize;
/*     */     } 
/* 233 */     if (this.model != null) size += PacketIO.stringSize(this.model); 
/* 234 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/* 235 */     if (this.modelVFXId != null) size += PacketIO.stringSize(this.modelVFXId);
/*     */     
/* 237 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 241 */     if (buffer.readableBytes() - offset < 38) {
/* 242 */       return ValidationResult.error("Buffer too small: expected at least 38 bytes");
/*     */     }
/*     */     
/* 245 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 248 */     if ((nullBits & 0x1) != 0) {
/* 249 */       int particlesOffset = buffer.getIntLE(offset + 18);
/* 250 */       if (particlesOffset < 0) {
/* 251 */         return ValidationResult.error("Invalid offset for Particles");
/*     */       }
/* 253 */       int pos = offset + 38 + particlesOffset;
/* 254 */       if (pos >= buffer.writerIndex()) {
/* 255 */         return ValidationResult.error("Offset out of bounds for Particles");
/*     */       }
/* 257 */       int particlesCount = VarInt.peek(buffer, pos);
/* 258 */       if (particlesCount < 0) {
/* 259 */         return ValidationResult.error("Invalid array count for Particles");
/*     */       }
/* 261 */       if (particlesCount > 4096000) {
/* 262 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*     */       }
/* 264 */       pos += VarInt.length(buffer, pos);
/* 265 */       for (int i = 0; i < particlesCount; i++) {
/* 266 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 267 */         if (!structResult.isValid()) {
/* 268 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*     */         }
/* 270 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 274 */     if ((nullBits & 0x2) != 0) {
/* 275 */       int firstPersonParticlesOffset = buffer.getIntLE(offset + 22);
/* 276 */       if (firstPersonParticlesOffset < 0) {
/* 277 */         return ValidationResult.error("Invalid offset for FirstPersonParticles");
/*     */       }
/* 279 */       int pos = offset + 38 + firstPersonParticlesOffset;
/* 280 */       if (pos >= buffer.writerIndex()) {
/* 281 */         return ValidationResult.error("Offset out of bounds for FirstPersonParticles");
/*     */       }
/* 283 */       int firstPersonParticlesCount = VarInt.peek(buffer, pos);
/* 284 */       if (firstPersonParticlesCount < 0) {
/* 285 */         return ValidationResult.error("Invalid array count for FirstPersonParticles");
/*     */       }
/* 287 */       if (firstPersonParticlesCount > 4096000) {
/* 288 */         return ValidationResult.error("FirstPersonParticles exceeds max length 4096000");
/*     */       }
/* 290 */       pos += VarInt.length(buffer, pos);
/* 291 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 292 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 293 */         if (!structResult.isValid()) {
/* 294 */           return ValidationResult.error("Invalid ModelParticle in FirstPersonParticles[" + i + "]: " + structResult.error());
/*     */         }
/* 296 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     if ((nullBits & 0x4) != 0) {
/* 301 */       int modelOffset = buffer.getIntLE(offset + 26);
/* 302 */       if (modelOffset < 0) {
/* 303 */         return ValidationResult.error("Invalid offset for Model");
/*     */       }
/* 305 */       int pos = offset + 38 + modelOffset;
/* 306 */       if (pos >= buffer.writerIndex()) {
/* 307 */         return ValidationResult.error("Offset out of bounds for Model");
/*     */       }
/* 309 */       int modelLen = VarInt.peek(buffer, pos);
/* 310 */       if (modelLen < 0) {
/* 311 */         return ValidationResult.error("Invalid string length for Model");
/*     */       }
/* 313 */       if (modelLen > 4096000) {
/* 314 */         return ValidationResult.error("Model exceeds max length 4096000");
/*     */       }
/* 316 */       pos += VarInt.length(buffer, pos);
/* 317 */       pos += modelLen;
/* 318 */       if (pos > buffer.writerIndex()) {
/* 319 */         return ValidationResult.error("Buffer overflow reading Model");
/*     */       }
/*     */     } 
/*     */     
/* 323 */     if ((nullBits & 0x8) != 0) {
/* 324 */       int textureOffset = buffer.getIntLE(offset + 30);
/* 325 */       if (textureOffset < 0) {
/* 326 */         return ValidationResult.error("Invalid offset for Texture");
/*     */       }
/* 328 */       int pos = offset + 38 + textureOffset;
/* 329 */       if (pos >= buffer.writerIndex()) {
/* 330 */         return ValidationResult.error("Offset out of bounds for Texture");
/*     */       }
/* 332 */       int textureLen = VarInt.peek(buffer, pos);
/* 333 */       if (textureLen < 0) {
/* 334 */         return ValidationResult.error("Invalid string length for Texture");
/*     */       }
/* 336 */       if (textureLen > 4096000) {
/* 337 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*     */       }
/* 339 */       pos += VarInt.length(buffer, pos);
/* 340 */       pos += textureLen;
/* 341 */       if (pos > buffer.writerIndex()) {
/* 342 */         return ValidationResult.error("Buffer overflow reading Texture");
/*     */       }
/*     */     } 
/*     */     
/* 346 */     if ((nullBits & 0x10) != 0) {
/* 347 */       int modelVFXIdOffset = buffer.getIntLE(offset + 34);
/* 348 */       if (modelVFXIdOffset < 0) {
/* 349 */         return ValidationResult.error("Invalid offset for ModelVFXId");
/*     */       }
/* 351 */       int pos = offset + 38 + modelVFXIdOffset;
/* 352 */       if (pos >= buffer.writerIndex()) {
/* 353 */         return ValidationResult.error("Offset out of bounds for ModelVFXId");
/*     */       }
/* 355 */       int modelVFXIdLen = VarInt.peek(buffer, pos);
/* 356 */       if (modelVFXIdLen < 0) {
/* 357 */         return ValidationResult.error("Invalid string length for ModelVFXId");
/*     */       }
/* 359 */       if (modelVFXIdLen > 4096000) {
/* 360 */         return ValidationResult.error("ModelVFXId exceeds max length 4096000");
/*     */       }
/* 362 */       pos += VarInt.length(buffer, pos);
/* 363 */       pos += modelVFXIdLen;
/* 364 */       if (pos > buffer.writerIndex()) {
/* 365 */         return ValidationResult.error("Buffer overflow reading ModelVFXId");
/*     */       }
/*     */     } 
/* 368 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemAppearanceCondition clone() {
/* 372 */     ItemAppearanceCondition copy = new ItemAppearanceCondition();
/* 373 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 374 */     copy.firstPersonParticles = (this.firstPersonParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.firstPersonParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 375 */     copy.model = this.model;
/* 376 */     copy.texture = this.texture;
/* 377 */     copy.modelVFXId = this.modelVFXId;
/* 378 */     copy.condition = (this.condition != null) ? this.condition.clone() : null;
/* 379 */     copy.conditionValueType = this.conditionValueType;
/* 380 */     copy.localSoundEventId = this.localSoundEventId;
/* 381 */     copy.worldSoundEventId = this.worldSoundEventId;
/* 382 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemAppearanceCondition other;
/* 388 */     if (this == obj) return true; 
/* 389 */     if (obj instanceof ItemAppearanceCondition) { other = (ItemAppearanceCondition)obj; } else { return false; }
/* 390 */      return (Arrays.equals((Object[])this.particles, (Object[])other.particles) && Arrays.equals((Object[])this.firstPersonParticles, (Object[])other.firstPersonParticles) && Objects.equals(this.model, other.model) && Objects.equals(this.texture, other.texture) && Objects.equals(this.modelVFXId, other.modelVFXId) && Objects.equals(this.condition, other.condition) && Objects.equals(this.conditionValueType, other.conditionValueType) && this.localSoundEventId == other.localSoundEventId && this.worldSoundEventId == other.worldSoundEventId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 395 */     int result = 1;
/* 396 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 397 */     result = 31 * result + Arrays.hashCode((Object[])this.firstPersonParticles);
/* 398 */     result = 31 * result + Objects.hashCode(this.model);
/* 399 */     result = 31 * result + Objects.hashCode(this.texture);
/* 400 */     result = 31 * result + Objects.hashCode(this.modelVFXId);
/* 401 */     result = 31 * result + Objects.hashCode(this.condition);
/* 402 */     result = 31 * result + Objects.hashCode(this.conditionValueType);
/* 403 */     result = 31 * result + Integer.hashCode(this.localSoundEventId);
/* 404 */     result = 31 * result + Integer.hashCode(this.worldSoundEventId);
/* 405 */     return result;
/*     */   }
/*     */   
/*     */   public ItemAppearanceCondition() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemAppearanceCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */