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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AmbienceFXConditions
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 2;
/*     */   public static final int FIXED_BLOCK_SIZE = 41;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 57;
/*     */   public static final int MAX_SIZE = 102400077;
/*     */   public boolean never;
/*     */   @Nullable
/*     */   public int[] environmentIndices;
/*     */   @Nullable
/*     */   public int[] weatherIndices;
/*     */   @Nullable
/*     */   public int[] fluidFXIndices;
/*     */   public int environmentTagPatternIndex;
/*     */   public int weatherTagPatternIndex;
/*     */   
/*     */   public AmbienceFXConditions(boolean never, @Nullable int[] environmentIndices, @Nullable int[] weatherIndices, @Nullable int[] fluidFXIndices, int environmentTagPatternIndex, int weatherTagPatternIndex, @Nullable AmbienceFXBlockSoundSet[] surroundingBlockSoundSets, @Nullable Range altitude, @Nullable Rangeb walls, boolean roof, int roofMaterialTagPatternIndex, boolean floor, @Nullable Rangeb sunLightLevel, @Nullable Rangeb torchLightLevel, @Nullable Rangeb globalLightLevel, @Nullable Rangef dayTime) {
/*  41 */     this.never = never;
/*  42 */     this.environmentIndices = environmentIndices;
/*  43 */     this.weatherIndices = weatherIndices;
/*  44 */     this.fluidFXIndices = fluidFXIndices;
/*  45 */     this.environmentTagPatternIndex = environmentTagPatternIndex;
/*  46 */     this.weatherTagPatternIndex = weatherTagPatternIndex;
/*  47 */     this.surroundingBlockSoundSets = surroundingBlockSoundSets;
/*  48 */     this.altitude = altitude;
/*  49 */     this.walls = walls;
/*  50 */     this.roof = roof;
/*  51 */     this.roofMaterialTagPatternIndex = roofMaterialTagPatternIndex;
/*  52 */     this.floor = floor;
/*  53 */     this.sunLightLevel = sunLightLevel;
/*  54 */     this.torchLightLevel = torchLightLevel;
/*  55 */     this.globalLightLevel = globalLightLevel;
/*  56 */     this.dayTime = dayTime; } @Nullable public AmbienceFXBlockSoundSet[] surroundingBlockSoundSets; @Nullable public Range altitude; @Nullable public Rangeb walls; public boolean roof; public int roofMaterialTagPatternIndex; public boolean floor; @Nullable
/*     */   public Rangeb sunLightLevel; @Nullable
/*     */   public Rangeb torchLightLevel; @Nullable
/*     */   public Rangeb globalLightLevel; @Nullable
/*  60 */   public Rangef dayTime; public AmbienceFXConditions() {} public AmbienceFXConditions(@Nonnull AmbienceFXConditions other) { this.never = other.never;
/*  61 */     this.environmentIndices = other.environmentIndices;
/*  62 */     this.weatherIndices = other.weatherIndices;
/*  63 */     this.fluidFXIndices = other.fluidFXIndices;
/*  64 */     this.environmentTagPatternIndex = other.environmentTagPatternIndex;
/*  65 */     this.weatherTagPatternIndex = other.weatherTagPatternIndex;
/*  66 */     this.surroundingBlockSoundSets = other.surroundingBlockSoundSets;
/*  67 */     this.altitude = other.altitude;
/*  68 */     this.walls = other.walls;
/*  69 */     this.roof = other.roof;
/*  70 */     this.roofMaterialTagPatternIndex = other.roofMaterialTagPatternIndex;
/*  71 */     this.floor = other.floor;
/*  72 */     this.sunLightLevel = other.sunLightLevel;
/*  73 */     this.torchLightLevel = other.torchLightLevel;
/*  74 */     this.globalLightLevel = other.globalLightLevel;
/*  75 */     this.dayTime = other.dayTime; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AmbienceFXConditions deserialize(@Nonnull ByteBuf buf, int offset) {
/*  80 */     AmbienceFXConditions obj = new AmbienceFXConditions();
/*  81 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/*  82 */     obj.never = (buf.getByte(offset + 2) != 0);
/*  83 */     obj.environmentTagPatternIndex = buf.getIntLE(offset + 3);
/*  84 */     obj.weatherTagPatternIndex = buf.getIntLE(offset + 7);
/*  85 */     if ((nullBits[0] & 0x10) != 0) obj.altitude = Range.deserialize(buf, offset + 11); 
/*  86 */     if ((nullBits[0] & 0x20) != 0) obj.walls = Rangeb.deserialize(buf, offset + 19); 
/*  87 */     obj.roof = (buf.getByte(offset + 21) != 0);
/*  88 */     obj.roofMaterialTagPatternIndex = buf.getIntLE(offset + 22);
/*  89 */     obj.floor = (buf.getByte(offset + 26) != 0);
/*  90 */     if ((nullBits[0] & 0x40) != 0) obj.sunLightLevel = Rangeb.deserialize(buf, offset + 27); 
/*  91 */     if ((nullBits[0] & 0x80) != 0) obj.torchLightLevel = Rangeb.deserialize(buf, offset + 29); 
/*  92 */     if ((nullBits[1] & 0x1) != 0) obj.globalLightLevel = Rangeb.deserialize(buf, offset + 31); 
/*  93 */     if ((nullBits[1] & 0x2) != 0) obj.dayTime = Rangef.deserialize(buf, offset + 33);
/*     */     
/*  95 */     if ((nullBits[0] & 0x1) != 0) {
/*  96 */       int varPos0 = offset + 57 + buf.getIntLE(offset + 41);
/*  97 */       int environmentIndicesCount = VarInt.peek(buf, varPos0);
/*  98 */       if (environmentIndicesCount < 0) throw ProtocolException.negativeLength("EnvironmentIndices", environmentIndicesCount); 
/*  99 */       if (environmentIndicesCount > 4096000) throw ProtocolException.arrayTooLong("EnvironmentIndices", environmentIndicesCount, 4096000); 
/* 100 */       int varIntLen = VarInt.length(buf, varPos0);
/* 101 */       if ((varPos0 + varIntLen) + environmentIndicesCount * 4L > buf.readableBytes())
/* 102 */         throw ProtocolException.bufferTooSmall("EnvironmentIndices", varPos0 + varIntLen + environmentIndicesCount * 4, buf.readableBytes()); 
/* 103 */       obj.environmentIndices = new int[environmentIndicesCount];
/* 104 */       for (int i = 0; i < environmentIndicesCount; i++) {
/* 105 */         obj.environmentIndices[i] = buf.getIntLE(varPos0 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 108 */     if ((nullBits[0] & 0x2) != 0) {
/* 109 */       int varPos1 = offset + 57 + buf.getIntLE(offset + 45);
/* 110 */       int weatherIndicesCount = VarInt.peek(buf, varPos1);
/* 111 */       if (weatherIndicesCount < 0) throw ProtocolException.negativeLength("WeatherIndices", weatherIndicesCount); 
/* 112 */       if (weatherIndicesCount > 4096000) throw ProtocolException.arrayTooLong("WeatherIndices", weatherIndicesCount, 4096000); 
/* 113 */       int varIntLen = VarInt.length(buf, varPos1);
/* 114 */       if ((varPos1 + varIntLen) + weatherIndicesCount * 4L > buf.readableBytes())
/* 115 */         throw ProtocolException.bufferTooSmall("WeatherIndices", varPos1 + varIntLen + weatherIndicesCount * 4, buf.readableBytes()); 
/* 116 */       obj.weatherIndices = new int[weatherIndicesCount];
/* 117 */       for (int i = 0; i < weatherIndicesCount; i++) {
/* 118 */         obj.weatherIndices[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 121 */     if ((nullBits[0] & 0x4) != 0) {
/* 122 */       int varPos2 = offset + 57 + buf.getIntLE(offset + 49);
/* 123 */       int fluidFXIndicesCount = VarInt.peek(buf, varPos2);
/* 124 */       if (fluidFXIndicesCount < 0) throw ProtocolException.negativeLength("FluidFXIndices", fluidFXIndicesCount); 
/* 125 */       if (fluidFXIndicesCount > 4096000) throw ProtocolException.arrayTooLong("FluidFXIndices", fluidFXIndicesCount, 4096000); 
/* 126 */       int varIntLen = VarInt.length(buf, varPos2);
/* 127 */       if ((varPos2 + varIntLen) + fluidFXIndicesCount * 4L > buf.readableBytes())
/* 128 */         throw ProtocolException.bufferTooSmall("FluidFXIndices", varPos2 + varIntLen + fluidFXIndicesCount * 4, buf.readableBytes()); 
/* 129 */       obj.fluidFXIndices = new int[fluidFXIndicesCount];
/* 130 */       for (int i = 0; i < fluidFXIndicesCount; i++) {
/* 131 */         obj.fluidFXIndices[i] = buf.getIntLE(varPos2 + varIntLen + i * 4);
/*     */       }
/*     */     } 
/* 134 */     if ((nullBits[0] & 0x8) != 0) {
/* 135 */       int varPos3 = offset + 57 + buf.getIntLE(offset + 53);
/* 136 */       int surroundingBlockSoundSetsCount = VarInt.peek(buf, varPos3);
/* 137 */       if (surroundingBlockSoundSetsCount < 0) throw ProtocolException.negativeLength("SurroundingBlockSoundSets", surroundingBlockSoundSetsCount); 
/* 138 */       if (surroundingBlockSoundSetsCount > 4096000) throw ProtocolException.arrayTooLong("SurroundingBlockSoundSets", surroundingBlockSoundSetsCount, 4096000); 
/* 139 */       int varIntLen = VarInt.length(buf, varPos3);
/* 140 */       if ((varPos3 + varIntLen) + surroundingBlockSoundSetsCount * 13L > buf.readableBytes())
/* 141 */         throw ProtocolException.bufferTooSmall("SurroundingBlockSoundSets", varPos3 + varIntLen + surroundingBlockSoundSetsCount * 13, buf.readableBytes()); 
/* 142 */       obj.surroundingBlockSoundSets = new AmbienceFXBlockSoundSet[surroundingBlockSoundSetsCount];
/* 143 */       int elemPos = varPos3 + varIntLen;
/* 144 */       for (int i = 0; i < surroundingBlockSoundSetsCount; i++) {
/* 145 */         obj.surroundingBlockSoundSets[i] = AmbienceFXBlockSoundSet.deserialize(buf, elemPos);
/* 146 */         elemPos += AmbienceFXBlockSoundSet.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 154 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 2);
/* 155 */     int maxEnd = 57;
/* 156 */     if ((nullBits[0] & 0x1) != 0) {
/* 157 */       int fieldOffset0 = buf.getIntLE(offset + 41);
/* 158 */       int pos0 = offset + 57 + fieldOffset0;
/* 159 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 4;
/* 160 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 162 */     if ((nullBits[0] & 0x2) != 0) {
/* 163 */       int fieldOffset1 = buf.getIntLE(offset + 45);
/* 164 */       int pos1 = offset + 57 + fieldOffset1;
/* 165 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/* 166 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 168 */     if ((nullBits[0] & 0x4) != 0) {
/* 169 */       int fieldOffset2 = buf.getIntLE(offset + 49);
/* 170 */       int pos2 = offset + 57 + fieldOffset2;
/* 171 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 4;
/* 172 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 174 */     if ((nullBits[0] & 0x8) != 0) {
/* 175 */       int fieldOffset3 = buf.getIntLE(offset + 53);
/* 176 */       int pos3 = offset + 57 + fieldOffset3;
/* 177 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 178 */       for (int i = 0; i < arrLen; ) { pos3 += AmbienceFXBlockSoundSet.computeBytesConsumed(buf, pos3); i++; }
/* 179 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 181 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 186 */     int startPos = buf.writerIndex();
/* 187 */     byte[] nullBits = new byte[2];
/* 188 */     if (this.environmentIndices != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/* 189 */     if (this.weatherIndices != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/* 190 */     if (this.fluidFXIndices != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/* 191 */     if (this.surroundingBlockSoundSets != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/* 192 */     if (this.altitude != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/* 193 */     if (this.walls != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/* 194 */     if (this.sunLightLevel != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/* 195 */     if (this.torchLightLevel != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/* 196 */     if (this.globalLightLevel != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/* 197 */     if (this.dayTime != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/* 198 */     buf.writeBytes(nullBits);
/*     */     
/* 200 */     buf.writeByte(this.never ? 1 : 0);
/* 201 */     buf.writeIntLE(this.environmentTagPatternIndex);
/* 202 */     buf.writeIntLE(this.weatherTagPatternIndex);
/* 203 */     if (this.altitude != null) { this.altitude.serialize(buf); } else { buf.writeZero(8); }
/* 204 */      if (this.walls != null) { this.walls.serialize(buf); } else { buf.writeZero(2); }
/* 205 */      buf.writeByte(this.roof ? 1 : 0);
/* 206 */     buf.writeIntLE(this.roofMaterialTagPatternIndex);
/* 207 */     buf.writeByte(this.floor ? 1 : 0);
/* 208 */     if (this.sunLightLevel != null) { this.sunLightLevel.serialize(buf); } else { buf.writeZero(2); }
/* 209 */      if (this.torchLightLevel != null) { this.torchLightLevel.serialize(buf); } else { buf.writeZero(2); }
/* 210 */      if (this.globalLightLevel != null) { this.globalLightLevel.serialize(buf); } else { buf.writeZero(2); }
/* 211 */      if (this.dayTime != null) { this.dayTime.serialize(buf); } else { buf.writeZero(8); }
/*     */     
/* 213 */     int environmentIndicesOffsetSlot = buf.writerIndex();
/* 214 */     buf.writeIntLE(0);
/* 215 */     int weatherIndicesOffsetSlot = buf.writerIndex();
/* 216 */     buf.writeIntLE(0);
/* 217 */     int fluidFXIndicesOffsetSlot = buf.writerIndex();
/* 218 */     buf.writeIntLE(0);
/* 219 */     int surroundingBlockSoundSetsOffsetSlot = buf.writerIndex();
/* 220 */     buf.writeIntLE(0);
/*     */     
/* 222 */     int varBlockStart = buf.writerIndex();
/* 223 */     if (this.environmentIndices != null) {
/* 224 */       buf.setIntLE(environmentIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 225 */       if (this.environmentIndices.length > 4096000) throw ProtocolException.arrayTooLong("EnvironmentIndices", this.environmentIndices.length, 4096000);  VarInt.write(buf, this.environmentIndices.length); for (int item : this.environmentIndices) buf.writeIntLE(item); 
/*     */     } else {
/* 227 */       buf.setIntLE(environmentIndicesOffsetSlot, -1);
/*     */     } 
/* 229 */     if (this.weatherIndices != null) {
/* 230 */       buf.setIntLE(weatherIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 231 */       if (this.weatherIndices.length > 4096000) throw ProtocolException.arrayTooLong("WeatherIndices", this.weatherIndices.length, 4096000);  VarInt.write(buf, this.weatherIndices.length); for (int item : this.weatherIndices) buf.writeIntLE(item); 
/*     */     } else {
/* 233 */       buf.setIntLE(weatherIndicesOffsetSlot, -1);
/*     */     } 
/* 235 */     if (this.fluidFXIndices != null) {
/* 236 */       buf.setIntLE(fluidFXIndicesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 237 */       if (this.fluidFXIndices.length > 4096000) throw ProtocolException.arrayTooLong("FluidFXIndices", this.fluidFXIndices.length, 4096000);  VarInt.write(buf, this.fluidFXIndices.length); for (int item : this.fluidFXIndices) buf.writeIntLE(item); 
/*     */     } else {
/* 239 */       buf.setIntLE(fluidFXIndicesOffsetSlot, -1);
/*     */     } 
/* 241 */     if (this.surroundingBlockSoundSets != null) {
/* 242 */       buf.setIntLE(surroundingBlockSoundSetsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 243 */       if (this.surroundingBlockSoundSets.length > 4096000) throw ProtocolException.arrayTooLong("SurroundingBlockSoundSets", this.surroundingBlockSoundSets.length, 4096000);  VarInt.write(buf, this.surroundingBlockSoundSets.length); for (AmbienceFXBlockSoundSet item : this.surroundingBlockSoundSets) item.serialize(buf); 
/*     */     } else {
/* 245 */       buf.setIntLE(surroundingBlockSoundSetsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 251 */     int size = 57;
/* 252 */     if (this.environmentIndices != null) size += VarInt.size(this.environmentIndices.length) + this.environmentIndices.length * 4; 
/* 253 */     if (this.weatherIndices != null) size += VarInt.size(this.weatherIndices.length) + this.weatherIndices.length * 4; 
/* 254 */     if (this.fluidFXIndices != null) size += VarInt.size(this.fluidFXIndices.length) + this.fluidFXIndices.length * 4; 
/* 255 */     if (this.surroundingBlockSoundSets != null) size += VarInt.size(this.surroundingBlockSoundSets.length) + this.surroundingBlockSoundSets.length * 13;
/*     */     
/* 257 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 261 */     if (buffer.readableBytes() - offset < 57) {
/* 262 */       return ValidationResult.error("Buffer too small: expected at least 57 bytes");
/*     */     }
/*     */     
/* 265 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 2);
/*     */     
/* 267 */     if ((nullBits[0] & 0x1) != 0) {
/* 268 */       int environmentIndicesOffset = buffer.getIntLE(offset + 41);
/* 269 */       if (environmentIndicesOffset < 0) {
/* 270 */         return ValidationResult.error("Invalid offset for EnvironmentIndices");
/*     */       }
/* 272 */       int pos = offset + 57 + environmentIndicesOffset;
/* 273 */       if (pos >= buffer.writerIndex()) {
/* 274 */         return ValidationResult.error("Offset out of bounds for EnvironmentIndices");
/*     */       }
/* 276 */       int environmentIndicesCount = VarInt.peek(buffer, pos);
/* 277 */       if (environmentIndicesCount < 0) {
/* 278 */         return ValidationResult.error("Invalid array count for EnvironmentIndices");
/*     */       }
/* 280 */       if (environmentIndicesCount > 4096000) {
/* 281 */         return ValidationResult.error("EnvironmentIndices exceeds max length 4096000");
/*     */       }
/* 283 */       pos += VarInt.length(buffer, pos);
/* 284 */       pos += environmentIndicesCount * 4;
/* 285 */       if (pos > buffer.writerIndex()) {
/* 286 */         return ValidationResult.error("Buffer overflow reading EnvironmentIndices");
/*     */       }
/*     */     } 
/*     */     
/* 290 */     if ((nullBits[0] & 0x2) != 0) {
/* 291 */       int weatherIndicesOffset = buffer.getIntLE(offset + 45);
/* 292 */       if (weatherIndicesOffset < 0) {
/* 293 */         return ValidationResult.error("Invalid offset for WeatherIndices");
/*     */       }
/* 295 */       int pos = offset + 57 + weatherIndicesOffset;
/* 296 */       if (pos >= buffer.writerIndex()) {
/* 297 */         return ValidationResult.error("Offset out of bounds for WeatherIndices");
/*     */       }
/* 299 */       int weatherIndicesCount = VarInt.peek(buffer, pos);
/* 300 */       if (weatherIndicesCount < 0) {
/* 301 */         return ValidationResult.error("Invalid array count for WeatherIndices");
/*     */       }
/* 303 */       if (weatherIndicesCount > 4096000) {
/* 304 */         return ValidationResult.error("WeatherIndices exceeds max length 4096000");
/*     */       }
/* 306 */       pos += VarInt.length(buffer, pos);
/* 307 */       pos += weatherIndicesCount * 4;
/* 308 */       if (pos > buffer.writerIndex()) {
/* 309 */         return ValidationResult.error("Buffer overflow reading WeatherIndices");
/*     */       }
/*     */     } 
/*     */     
/* 313 */     if ((nullBits[0] & 0x4) != 0) {
/* 314 */       int fluidFXIndicesOffset = buffer.getIntLE(offset + 49);
/* 315 */       if (fluidFXIndicesOffset < 0) {
/* 316 */         return ValidationResult.error("Invalid offset for FluidFXIndices");
/*     */       }
/* 318 */       int pos = offset + 57 + fluidFXIndicesOffset;
/* 319 */       if (pos >= buffer.writerIndex()) {
/* 320 */         return ValidationResult.error("Offset out of bounds for FluidFXIndices");
/*     */       }
/* 322 */       int fluidFXIndicesCount = VarInt.peek(buffer, pos);
/* 323 */       if (fluidFXIndicesCount < 0) {
/* 324 */         return ValidationResult.error("Invalid array count for FluidFXIndices");
/*     */       }
/* 326 */       if (fluidFXIndicesCount > 4096000) {
/* 327 */         return ValidationResult.error("FluidFXIndices exceeds max length 4096000");
/*     */       }
/* 329 */       pos += VarInt.length(buffer, pos);
/* 330 */       pos += fluidFXIndicesCount * 4;
/* 331 */       if (pos > buffer.writerIndex()) {
/* 332 */         return ValidationResult.error("Buffer overflow reading FluidFXIndices");
/*     */       }
/*     */     } 
/*     */     
/* 336 */     if ((nullBits[0] & 0x8) != 0) {
/* 337 */       int surroundingBlockSoundSetsOffset = buffer.getIntLE(offset + 53);
/* 338 */       if (surroundingBlockSoundSetsOffset < 0) {
/* 339 */         return ValidationResult.error("Invalid offset for SurroundingBlockSoundSets");
/*     */       }
/* 341 */       int pos = offset + 57 + surroundingBlockSoundSetsOffset;
/* 342 */       if (pos >= buffer.writerIndex()) {
/* 343 */         return ValidationResult.error("Offset out of bounds for SurroundingBlockSoundSets");
/*     */       }
/* 345 */       int surroundingBlockSoundSetsCount = VarInt.peek(buffer, pos);
/* 346 */       if (surroundingBlockSoundSetsCount < 0) {
/* 347 */         return ValidationResult.error("Invalid array count for SurroundingBlockSoundSets");
/*     */       }
/* 349 */       if (surroundingBlockSoundSetsCount > 4096000) {
/* 350 */         return ValidationResult.error("SurroundingBlockSoundSets exceeds max length 4096000");
/*     */       }
/* 352 */       pos += VarInt.length(buffer, pos);
/* 353 */       pos += surroundingBlockSoundSetsCount * 13;
/* 354 */       if (pos > buffer.writerIndex()) {
/* 355 */         return ValidationResult.error("Buffer overflow reading SurroundingBlockSoundSets");
/*     */       }
/*     */     } 
/* 358 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AmbienceFXConditions clone() {
/* 362 */     AmbienceFXConditions copy = new AmbienceFXConditions();
/* 363 */     copy.never = this.never;
/* 364 */     copy.environmentIndices = (this.environmentIndices != null) ? Arrays.copyOf(this.environmentIndices, this.environmentIndices.length) : null;
/* 365 */     copy.weatherIndices = (this.weatherIndices != null) ? Arrays.copyOf(this.weatherIndices, this.weatherIndices.length) : null;
/* 366 */     copy.fluidFXIndices = (this.fluidFXIndices != null) ? Arrays.copyOf(this.fluidFXIndices, this.fluidFXIndices.length) : null;
/* 367 */     copy.environmentTagPatternIndex = this.environmentTagPatternIndex;
/* 368 */     copy.weatherTagPatternIndex = this.weatherTagPatternIndex;
/* 369 */     copy.surroundingBlockSoundSets = (this.surroundingBlockSoundSets != null) ? (AmbienceFXBlockSoundSet[])Arrays.<AmbienceFXBlockSoundSet>stream(this.surroundingBlockSoundSets).map(e -> e.clone()).toArray(x$0 -> new AmbienceFXBlockSoundSet[x$0]) : null;
/* 370 */     copy.altitude = (this.altitude != null) ? this.altitude.clone() : null;
/* 371 */     copy.walls = (this.walls != null) ? this.walls.clone() : null;
/* 372 */     copy.roof = this.roof;
/* 373 */     copy.roofMaterialTagPatternIndex = this.roofMaterialTagPatternIndex;
/* 374 */     copy.floor = this.floor;
/* 375 */     copy.sunLightLevel = (this.sunLightLevel != null) ? this.sunLightLevel.clone() : null;
/* 376 */     copy.torchLightLevel = (this.torchLightLevel != null) ? this.torchLightLevel.clone() : null;
/* 377 */     copy.globalLightLevel = (this.globalLightLevel != null) ? this.globalLightLevel.clone() : null;
/* 378 */     copy.dayTime = (this.dayTime != null) ? this.dayTime.clone() : null;
/* 379 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AmbienceFXConditions other;
/* 385 */     if (this == obj) return true; 
/* 386 */     if (obj instanceof AmbienceFXConditions) { other = (AmbienceFXConditions)obj; } else { return false; }
/* 387 */      return (this.never == other.never && Arrays.equals(this.environmentIndices, other.environmentIndices) && Arrays.equals(this.weatherIndices, other.weatherIndices) && Arrays.equals(this.fluidFXIndices, other.fluidFXIndices) && this.environmentTagPatternIndex == other.environmentTagPatternIndex && this.weatherTagPatternIndex == other.weatherTagPatternIndex && Arrays.equals((Object[])this.surroundingBlockSoundSets, (Object[])other.surroundingBlockSoundSets) && Objects.equals(this.altitude, other.altitude) && Objects.equals(this.walls, other.walls) && this.roof == other.roof && this.roofMaterialTagPatternIndex == other.roofMaterialTagPatternIndex && this.floor == other.floor && Objects.equals(this.sunLightLevel, other.sunLightLevel) && Objects.equals(this.torchLightLevel, other.torchLightLevel) && Objects.equals(this.globalLightLevel, other.globalLightLevel) && Objects.equals(this.dayTime, other.dayTime));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 392 */     int result = 1;
/* 393 */     result = 31 * result + Boolean.hashCode(this.never);
/* 394 */     result = 31 * result + Arrays.hashCode(this.environmentIndices);
/* 395 */     result = 31 * result + Arrays.hashCode(this.weatherIndices);
/* 396 */     result = 31 * result + Arrays.hashCode(this.fluidFXIndices);
/* 397 */     result = 31 * result + Integer.hashCode(this.environmentTagPatternIndex);
/* 398 */     result = 31 * result + Integer.hashCode(this.weatherTagPatternIndex);
/* 399 */     result = 31 * result + Arrays.hashCode((Object[])this.surroundingBlockSoundSets);
/* 400 */     result = 31 * result + Objects.hashCode(this.altitude);
/* 401 */     result = 31 * result + Objects.hashCode(this.walls);
/* 402 */     result = 31 * result + Boolean.hashCode(this.roof);
/* 403 */     result = 31 * result + Integer.hashCode(this.roofMaterialTagPatternIndex);
/* 404 */     result = 31 * result + Boolean.hashCode(this.floor);
/* 405 */     result = 31 * result + Objects.hashCode(this.sunLightLevel);
/* 406 */     result = 31 * result + Objects.hashCode(this.torchLightLevel);
/* 407 */     result = 31 * result + Objects.hashCode(this.globalLightLevel);
/* 408 */     result = 31 * result + Objects.hashCode(this.dayTime);
/* 409 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXConditions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */