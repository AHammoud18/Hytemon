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
/*     */ public class Fluid
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 22;
/*     */   public static final int VARIABLE_FIELD_COUNT = 5;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   @Nonnull
/*  24 */   public Opacity opacity = Opacity.Solid; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String id; public int maxFluidLevel; @Nullable
/*     */   public BlockTextures[] cubeTextures; public boolean requiresAlphaBlending; @Nullable
/*     */   public ShaderType[] shaderEffect; @Nullable
/*     */   public ColorLight light; public int fluidFXIndex; public int blockSoundSetIndex;
/*     */   @Nullable
/*     */   public String blockParticleSetId;
/*     */   @Nullable
/*     */   public Color particleColor;
/*     */   @Nullable
/*     */   public int[] tagIndexes;
/*     */   
/*     */   public Fluid(@Nullable String id, int maxFluidLevel, @Nullable BlockTextures[] cubeTextures, boolean requiresAlphaBlending, @Nonnull Opacity opacity, @Nullable ShaderType[] shaderEffect, @Nullable ColorLight light, int fluidFXIndex, int blockSoundSetIndex, @Nullable String blockParticleSetId, @Nullable Color particleColor, @Nullable int[] tagIndexes) {
/*  37 */     this.id = id;
/*  38 */     this.maxFluidLevel = maxFluidLevel;
/*  39 */     this.cubeTextures = cubeTextures;
/*  40 */     this.requiresAlphaBlending = requiresAlphaBlending;
/*  41 */     this.opacity = opacity;
/*  42 */     this.shaderEffect = shaderEffect;
/*  43 */     this.light = light;
/*  44 */     this.fluidFXIndex = fluidFXIndex;
/*  45 */     this.blockSoundSetIndex = blockSoundSetIndex;
/*  46 */     this.blockParticleSetId = blockParticleSetId;
/*  47 */     this.particleColor = particleColor;
/*  48 */     this.tagIndexes = tagIndexes;
/*     */   }
/*     */   
/*     */   public Fluid(@Nonnull Fluid other) {
/*  52 */     this.id = other.id;
/*  53 */     this.maxFluidLevel = other.maxFluidLevel;
/*  54 */     this.cubeTextures = other.cubeTextures;
/*  55 */     this.requiresAlphaBlending = other.requiresAlphaBlending;
/*  56 */     this.opacity = other.opacity;
/*  57 */     this.shaderEffect = other.shaderEffect;
/*  58 */     this.light = other.light;
/*  59 */     this.fluidFXIndex = other.fluidFXIndex;
/*  60 */     this.blockSoundSetIndex = other.blockSoundSetIndex;
/*  61 */     this.blockParticleSetId = other.blockParticleSetId;
/*  62 */     this.particleColor = other.particleColor;
/*  63 */     this.tagIndexes = other.tagIndexes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Fluid deserialize(@Nonnull ByteBuf buf, int offset) {
/*  68 */     Fluid obj = new Fluid();
/*  69 */     byte nullBits = buf.getByte(offset);
/*  70 */     obj.maxFluidLevel = buf.getIntLE(offset + 1);
/*  71 */     obj.requiresAlphaBlending = (buf.getByte(offset + 5) != 0);
/*  72 */     obj.opacity = Opacity.fromValue(buf.getByte(offset + 6));
/*  73 */     if ((nullBits & 0x8) != 0) obj.light = ColorLight.deserialize(buf, offset + 7); 
/*  74 */     obj.fluidFXIndex = buf.getIntLE(offset + 11);
/*  75 */     obj.blockSoundSetIndex = buf.getIntLE(offset + 15);
/*  76 */     if ((nullBits & 0x20) != 0) obj.particleColor = Color.deserialize(buf, offset + 19);
/*     */     
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int varPos0 = offset + 42 + buf.getIntLE(offset + 22);
/*  80 */       int idLen = VarInt.peek(buf, varPos0);
/*  81 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  82 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  83 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  85 */     if ((nullBits & 0x2) != 0) {
/*  86 */       int varPos1 = offset + 42 + buf.getIntLE(offset + 26);
/*  87 */       int cubeTexturesCount = VarInt.peek(buf, varPos1);
/*  88 */       if (cubeTexturesCount < 0) throw ProtocolException.negativeLength("CubeTextures", cubeTexturesCount); 
/*  89 */       if (cubeTexturesCount > 4096000) throw ProtocolException.arrayTooLong("CubeTextures", cubeTexturesCount, 4096000); 
/*  90 */       int varIntLen = VarInt.length(buf, varPos1);
/*  91 */       if ((varPos1 + varIntLen) + cubeTexturesCount * 5L > buf.readableBytes())
/*  92 */         throw ProtocolException.bufferTooSmall("CubeTextures", varPos1 + varIntLen + cubeTexturesCount * 5, buf.readableBytes()); 
/*  93 */       obj.cubeTextures = new BlockTextures[cubeTexturesCount];
/*  94 */       int elemPos = varPos1 + varIntLen;
/*  95 */       for (int i = 0; i < cubeTexturesCount; i++) {
/*  96 */         obj.cubeTextures[i] = BlockTextures.deserialize(buf, elemPos);
/*  97 */         elemPos += BlockTextures.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 100 */     if ((nullBits & 0x4) != 0) {
/* 101 */       int varPos2 = offset + 42 + buf.getIntLE(offset + 30);
/* 102 */       int shaderEffectCount = VarInt.peek(buf, varPos2);
/* 103 */       if (shaderEffectCount < 0) throw ProtocolException.negativeLength("ShaderEffect", shaderEffectCount); 
/* 104 */       if (shaderEffectCount > 4096000) throw ProtocolException.arrayTooLong("ShaderEffect", shaderEffectCount, 4096000); 
/* 105 */       int varIntLen = VarInt.length(buf, varPos2);
/* 106 */       if ((varPos2 + varIntLen) + shaderEffectCount * 1L > buf.readableBytes())
/* 107 */         throw ProtocolException.bufferTooSmall("ShaderEffect", varPos2 + varIntLen + shaderEffectCount * 1, buf.readableBytes()); 
/* 108 */       obj.shaderEffect = new ShaderType[shaderEffectCount];
/* 109 */       int elemPos = varPos2 + varIntLen;
/* 110 */       for (int i = 0; i < shaderEffectCount; i++) {
/* 111 */         obj.shaderEffect[i] = ShaderType.fromValue(buf.getByte(elemPos)); elemPos++;
/*     */       } 
/*     */     } 
/* 114 */     if ((nullBits & 0x10) != 0) {
/* 115 */       int varPos3 = offset + 42 + buf.getIntLE(offset + 34);
/* 116 */       int blockParticleSetIdLen = VarInt.peek(buf, varPos3);
/* 117 */       if (blockParticleSetIdLen < 0) throw ProtocolException.negativeLength("BlockParticleSetId", blockParticleSetIdLen); 
/* 118 */       if (blockParticleSetIdLen > 4096000) throw ProtocolException.stringTooLong("BlockParticleSetId", blockParticleSetIdLen, 4096000); 
/* 119 */       obj.blockParticleSetId = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/* 121 */     if ((nullBits & 0x40) != 0) {
/* 122 */       int varPos4 = offset + 42 + buf.getIntLE(offset + 38);
/* 123 */       int tagIndexesCount = VarInt.peek(buf, varPos4);
/* 124 */       if (tagIndexesCount < 0) throw ProtocolException.negativeLength("TagIndexes", tagIndexesCount); 
/* 125 */       if (tagIndexesCount > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", tagIndexesCount, 4096000); 
/* 126 */       int varIntLen = VarInt.length(buf, varPos4);
/* 127 */       if ((varPos4 + varIntLen) + tagIndexesCount * 4L > buf.readableBytes())
/* 128 */         throw ProtocolException.bufferTooSmall("TagIndexes", varPos4 + varIntLen + tagIndexesCount * 4, buf.readableBytes()); 
/* 129 */       obj.tagIndexes = new int[tagIndexesCount];
/* 130 */       for (int i = 0; i < tagIndexesCount; i++) {
/* 131 */         obj.tagIndexes[i] = buf.getIntLE(varPos4 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 139 */     byte nullBits = buf.getByte(offset);
/* 140 */     int maxEnd = 42;
/* 141 */     if ((nullBits & 0x1) != 0) {
/* 142 */       int fieldOffset0 = buf.getIntLE(offset + 22);
/* 143 */       int pos0 = offset + 42 + fieldOffset0;
/* 144 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 145 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 147 */     if ((nullBits & 0x2) != 0) {
/* 148 */       int fieldOffset1 = buf.getIntLE(offset + 26);
/* 149 */       int pos1 = offset + 42 + fieldOffset1;
/* 150 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 151 */       for (int i = 0; i < arrLen; ) { pos1 += BlockTextures.computeBytesConsumed(buf, pos1); i++; }
/* 152 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 154 */     if ((nullBits & 0x4) != 0) {
/* 155 */       int fieldOffset2 = buf.getIntLE(offset + 30);
/* 156 */       int pos2 = offset + 42 + fieldOffset2;
/* 157 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/* 158 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 160 */     if ((nullBits & 0x10) != 0) {
/* 161 */       int fieldOffset3 = buf.getIntLE(offset + 34);
/* 162 */       int pos3 = offset + 42 + fieldOffset3;
/* 163 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 164 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 166 */     if ((nullBits & 0x40) != 0) {
/* 167 */       int fieldOffset4 = buf.getIntLE(offset + 38);
/* 168 */       int pos4 = offset + 42 + fieldOffset4;
/* 169 */       int arrLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + arrLen * 4;
/* 170 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 172 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 177 */     int startPos = buf.writerIndex();
/* 178 */     byte nullBits = 0;
/* 179 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 180 */     if (this.cubeTextures != null) nullBits = (byte)(nullBits | 0x2); 
/* 181 */     if (this.shaderEffect != null) nullBits = (byte)(nullBits | 0x4); 
/* 182 */     if (this.light != null) nullBits = (byte)(nullBits | 0x8); 
/* 183 */     if (this.blockParticleSetId != null) nullBits = (byte)(nullBits | 0x10); 
/* 184 */     if (this.particleColor != null) nullBits = (byte)(nullBits | 0x20); 
/* 185 */     if (this.tagIndexes != null) nullBits = (byte)(nullBits | 0x40); 
/* 186 */     buf.writeByte(nullBits);
/*     */     
/* 188 */     buf.writeIntLE(this.maxFluidLevel);
/* 189 */     buf.writeByte(this.requiresAlphaBlending ? 1 : 0);
/* 190 */     buf.writeByte(this.opacity.getValue());
/* 191 */     if (this.light != null) { this.light.serialize(buf); } else { buf.writeZero(4); }
/* 192 */      buf.writeIntLE(this.fluidFXIndex);
/* 193 */     buf.writeIntLE(this.blockSoundSetIndex);
/* 194 */     if (this.particleColor != null) { this.particleColor.serialize(buf); } else { buf.writeZero(3); }
/*     */     
/* 196 */     int idOffsetSlot = buf.writerIndex();
/* 197 */     buf.writeIntLE(0);
/* 198 */     int cubeTexturesOffsetSlot = buf.writerIndex();
/* 199 */     buf.writeIntLE(0);
/* 200 */     int shaderEffectOffsetSlot = buf.writerIndex();
/* 201 */     buf.writeIntLE(0);
/* 202 */     int blockParticleSetIdOffsetSlot = buf.writerIndex();
/* 203 */     buf.writeIntLE(0);
/* 204 */     int tagIndexesOffsetSlot = buf.writerIndex();
/* 205 */     buf.writeIntLE(0);
/*     */     
/* 207 */     int varBlockStart = buf.writerIndex();
/* 208 */     if (this.id != null) {
/* 209 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 210 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 212 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 214 */     if (this.cubeTextures != null) {
/* 215 */       buf.setIntLE(cubeTexturesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 216 */       if (this.cubeTextures.length > 4096000) throw ProtocolException.arrayTooLong("CubeTextures", this.cubeTextures.length, 4096000);  VarInt.write(buf, this.cubeTextures.length); for (BlockTextures item : this.cubeTextures) item.serialize(buf); 
/*     */     } else {
/* 218 */       buf.setIntLE(cubeTexturesOffsetSlot, -1);
/*     */     } 
/* 220 */     if (this.shaderEffect != null) {
/* 221 */       buf.setIntLE(shaderEffectOffsetSlot, buf.writerIndex() - varBlockStart);
/* 222 */       if (this.shaderEffect.length > 4096000) throw ProtocolException.arrayTooLong("ShaderEffect", this.shaderEffect.length, 4096000);  VarInt.write(buf, this.shaderEffect.length); for (ShaderType item : this.shaderEffect) buf.writeByte(item.getValue()); 
/*     */     } else {
/* 224 */       buf.setIntLE(shaderEffectOffsetSlot, -1);
/*     */     } 
/* 226 */     if (this.blockParticleSetId != null) {
/* 227 */       buf.setIntLE(blockParticleSetIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 228 */       PacketIO.writeVarString(buf, this.blockParticleSetId, 4096000);
/*     */     } else {
/* 230 */       buf.setIntLE(blockParticleSetIdOffsetSlot, -1);
/*     */     } 
/* 232 */     if (this.tagIndexes != null) {
/* 233 */       buf.setIntLE(tagIndexesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 234 */       if (this.tagIndexes.length > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", this.tagIndexes.length, 4096000);  VarInt.write(buf, this.tagIndexes.length); for (int item : this.tagIndexes) buf.writeIntLE(item); 
/*     */     } else {
/* 236 */       buf.setIntLE(tagIndexesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 242 */     int size = 42;
/* 243 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 244 */     if (this.cubeTextures != null) {
/* 245 */       int cubeTexturesSize = 0;
/* 246 */       for (BlockTextures elem : this.cubeTextures) cubeTexturesSize += elem.computeSize(); 
/* 247 */       size += VarInt.size(this.cubeTextures.length) + cubeTexturesSize;
/*     */     } 
/* 249 */     if (this.shaderEffect != null) size += VarInt.size(this.shaderEffect.length) + this.shaderEffect.length * 1; 
/* 250 */     if (this.blockParticleSetId != null) size += PacketIO.stringSize(this.blockParticleSetId); 
/* 251 */     if (this.tagIndexes != null) size += VarInt.size(this.tagIndexes.length) + this.tagIndexes.length * 4;
/*     */     
/* 253 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 257 */     if (buffer.readableBytes() - offset < 42) {
/* 258 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 261 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 264 */     if ((nullBits & 0x1) != 0) {
/* 265 */       int idOffset = buffer.getIntLE(offset + 22);
/* 266 */       if (idOffset < 0) {
/* 267 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 269 */       int pos = offset + 42 + idOffset;
/* 270 */       if (pos >= buffer.writerIndex()) {
/* 271 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 273 */       int idLen = VarInt.peek(buffer, pos);
/* 274 */       if (idLen < 0) {
/* 275 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 277 */       if (idLen > 4096000) {
/* 278 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 280 */       pos += VarInt.length(buffer, pos);
/* 281 */       pos += idLen;
/* 282 */       if (pos > buffer.writerIndex()) {
/* 283 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 287 */     if ((nullBits & 0x2) != 0) {
/* 288 */       int cubeTexturesOffset = buffer.getIntLE(offset + 26);
/* 289 */       if (cubeTexturesOffset < 0) {
/* 290 */         return ValidationResult.error("Invalid offset for CubeTextures");
/*     */       }
/* 292 */       int pos = offset + 42 + cubeTexturesOffset;
/* 293 */       if (pos >= buffer.writerIndex()) {
/* 294 */         return ValidationResult.error("Offset out of bounds for CubeTextures");
/*     */       }
/* 296 */       int cubeTexturesCount = VarInt.peek(buffer, pos);
/* 297 */       if (cubeTexturesCount < 0) {
/* 298 */         return ValidationResult.error("Invalid array count for CubeTextures");
/*     */       }
/* 300 */       if (cubeTexturesCount > 4096000) {
/* 301 */         return ValidationResult.error("CubeTextures exceeds max length 4096000");
/*     */       }
/* 303 */       pos += VarInt.length(buffer, pos);
/* 304 */       for (int i = 0; i < cubeTexturesCount; i++) {
/* 305 */         ValidationResult structResult = BlockTextures.validateStructure(buffer, pos);
/* 306 */         if (!structResult.isValid()) {
/* 307 */           return ValidationResult.error("Invalid BlockTextures in CubeTextures[" + i + "]: " + structResult.error());
/*     */         }
/* 309 */         pos += BlockTextures.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 313 */     if ((nullBits & 0x4) != 0) {
/* 314 */       int shaderEffectOffset = buffer.getIntLE(offset + 30);
/* 315 */       if (shaderEffectOffset < 0) {
/* 316 */         return ValidationResult.error("Invalid offset for ShaderEffect");
/*     */       }
/* 318 */       int pos = offset + 42 + shaderEffectOffset;
/* 319 */       if (pos >= buffer.writerIndex()) {
/* 320 */         return ValidationResult.error("Offset out of bounds for ShaderEffect");
/*     */       }
/* 322 */       int shaderEffectCount = VarInt.peek(buffer, pos);
/* 323 */       if (shaderEffectCount < 0) {
/* 324 */         return ValidationResult.error("Invalid array count for ShaderEffect");
/*     */       }
/* 326 */       if (shaderEffectCount > 4096000) {
/* 327 */         return ValidationResult.error("ShaderEffect exceeds max length 4096000");
/*     */       }
/* 329 */       pos += VarInt.length(buffer, pos);
/* 330 */       pos += shaderEffectCount * 1;
/* 331 */       if (pos > buffer.writerIndex()) {
/* 332 */         return ValidationResult.error("Buffer overflow reading ShaderEffect");
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if ((nullBits & 0x10) != 0) {
/* 337 */       int blockParticleSetIdOffset = buffer.getIntLE(offset + 34);
/* 338 */       if (blockParticleSetIdOffset < 0) {
/* 339 */         return ValidationResult.error("Invalid offset for BlockParticleSetId");
/*     */       }
/* 341 */       int pos = offset + 42 + blockParticleSetIdOffset;
/* 342 */       if (pos >= buffer.writerIndex()) {
/* 343 */         return ValidationResult.error("Offset out of bounds for BlockParticleSetId");
/*     */       }
/* 345 */       int blockParticleSetIdLen = VarInt.peek(buffer, pos);
/* 346 */       if (blockParticleSetIdLen < 0) {
/* 347 */         return ValidationResult.error("Invalid string length for BlockParticleSetId");
/*     */       }
/* 349 */       if (blockParticleSetIdLen > 4096000) {
/* 350 */         return ValidationResult.error("BlockParticleSetId exceeds max length 4096000");
/*     */       }
/* 352 */       pos += VarInt.length(buffer, pos);
/* 353 */       pos += blockParticleSetIdLen;
/* 354 */       if (pos > buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Buffer overflow reading BlockParticleSetId");
/*     */       }
/*     */     } 
/*     */     
/* 359 */     if ((nullBits & 0x40) != 0) {
/* 360 */       int tagIndexesOffset = buffer.getIntLE(offset + 38);
/* 361 */       if (tagIndexesOffset < 0) {
/* 362 */         return ValidationResult.error("Invalid offset for TagIndexes");
/*     */       }
/* 364 */       int pos = offset + 42 + tagIndexesOffset;
/* 365 */       if (pos >= buffer.writerIndex()) {
/* 366 */         return ValidationResult.error("Offset out of bounds for TagIndexes");
/*     */       }
/* 368 */       int tagIndexesCount = VarInt.peek(buffer, pos);
/* 369 */       if (tagIndexesCount < 0) {
/* 370 */         return ValidationResult.error("Invalid array count for TagIndexes");
/*     */       }
/* 372 */       if (tagIndexesCount > 4096000) {
/* 373 */         return ValidationResult.error("TagIndexes exceeds max length 4096000");
/*     */       }
/* 375 */       pos += VarInt.length(buffer, pos);
/* 376 */       pos += tagIndexesCount * 4;
/* 377 */       if (pos > buffer.writerIndex()) {
/* 378 */         return ValidationResult.error("Buffer overflow reading TagIndexes");
/*     */       }
/*     */     } 
/* 381 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Fluid clone() {
/* 385 */     Fluid copy = new Fluid();
/* 386 */     copy.id = this.id;
/* 387 */     copy.maxFluidLevel = this.maxFluidLevel;
/* 388 */     copy.cubeTextures = (this.cubeTextures != null) ? (BlockTextures[])Arrays.<BlockTextures>stream(this.cubeTextures).map(e -> e.clone()).toArray(x$0 -> new BlockTextures[x$0]) : null;
/* 389 */     copy.requiresAlphaBlending = this.requiresAlphaBlending;
/* 390 */     copy.opacity = this.opacity;
/* 391 */     copy.shaderEffect = (this.shaderEffect != null) ? Arrays.<ShaderType>copyOf(this.shaderEffect, this.shaderEffect.length) : null;
/* 392 */     copy.light = (this.light != null) ? this.light.clone() : null;
/* 393 */     copy.fluidFXIndex = this.fluidFXIndex;
/* 394 */     copy.blockSoundSetIndex = this.blockSoundSetIndex;
/* 395 */     copy.blockParticleSetId = this.blockParticleSetId;
/* 396 */     copy.particleColor = (this.particleColor != null) ? this.particleColor.clone() : null;
/* 397 */     copy.tagIndexes = (this.tagIndexes != null) ? Arrays.copyOf(this.tagIndexes, this.tagIndexes.length) : null;
/* 398 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Fluid other;
/* 404 */     if (this == obj) return true; 
/* 405 */     if (obj instanceof Fluid) { other = (Fluid)obj; } else { return false; }
/* 406 */      return (Objects.equals(this.id, other.id) && this.maxFluidLevel == other.maxFluidLevel && Arrays.equals((Object[])this.cubeTextures, (Object[])other.cubeTextures) && this.requiresAlphaBlending == other.requiresAlphaBlending && Objects.equals(this.opacity, other.opacity) && Arrays.equals((Object[])this.shaderEffect, (Object[])other.shaderEffect) && Objects.equals(this.light, other.light) && this.fluidFXIndex == other.fluidFXIndex && this.blockSoundSetIndex == other.blockSoundSetIndex && Objects.equals(this.blockParticleSetId, other.blockParticleSetId) && Objects.equals(this.particleColor, other.particleColor) && Arrays.equals(this.tagIndexes, other.tagIndexes));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 411 */     int result = 1;
/* 412 */     result = 31 * result + Objects.hashCode(this.id);
/* 413 */     result = 31 * result + Integer.hashCode(this.maxFluidLevel);
/* 414 */     result = 31 * result + Arrays.hashCode((Object[])this.cubeTextures);
/* 415 */     result = 31 * result + Boolean.hashCode(this.requiresAlphaBlending);
/* 416 */     result = 31 * result + Objects.hashCode(this.opacity);
/* 417 */     result = 31 * result + Arrays.hashCode((Object[])this.shaderEffect);
/* 418 */     result = 31 * result + Objects.hashCode(this.light);
/* 419 */     result = 31 * result + Integer.hashCode(this.fluidFXIndex);
/* 420 */     result = 31 * result + Integer.hashCode(this.blockSoundSetIndex);
/* 421 */     result = 31 * result + Objects.hashCode(this.blockParticleSetId);
/* 422 */     result = 31 * result + Objects.hashCode(this.particleColor);
/* 423 */     result = 31 * result + Arrays.hashCode(this.tagIndexes);
/* 424 */     return result;
/*     */   }
/*     */   
/*     */   public Fluid() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Fluid.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */