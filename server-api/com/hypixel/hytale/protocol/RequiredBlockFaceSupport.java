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
/*     */ public class RequiredBlockFaceSupport
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 33;
/*     */   public static final int MAX_SIZE = 98304053;
/*     */   @Nonnull
/*  26 */   public SupportMatch support = SupportMatch.Ignored; @Nullable public String faceType; @Nullable public String selfFaceType; @Nullable public String blockSetId; public int blockTypeId; public int tagIndex; public int fluidId; @Nonnull
/*  27 */   public SupportMatch matchSelf = SupportMatch.Ignored;
/*     */   
/*     */   public boolean allowSupportPropagation;
/*     */   
/*     */   public boolean rotate;
/*     */   @Nullable
/*     */   public Vector3i[] filler;
/*     */   
/*     */   public RequiredBlockFaceSupport(@Nullable String faceType, @Nullable String selfFaceType, @Nullable String blockSetId, int blockTypeId, int tagIndex, int fluidId, @Nonnull SupportMatch support, @Nonnull SupportMatch matchSelf, boolean allowSupportPropagation, boolean rotate, @Nullable Vector3i[] filler) {
/*  36 */     this.faceType = faceType;
/*  37 */     this.selfFaceType = selfFaceType;
/*  38 */     this.blockSetId = blockSetId;
/*  39 */     this.blockTypeId = blockTypeId;
/*  40 */     this.tagIndex = tagIndex;
/*  41 */     this.fluidId = fluidId;
/*  42 */     this.support = support;
/*  43 */     this.matchSelf = matchSelf;
/*  44 */     this.allowSupportPropagation = allowSupportPropagation;
/*  45 */     this.rotate = rotate;
/*  46 */     this.filler = filler;
/*     */   }
/*     */   
/*     */   public RequiredBlockFaceSupport(@Nonnull RequiredBlockFaceSupport other) {
/*  50 */     this.faceType = other.faceType;
/*  51 */     this.selfFaceType = other.selfFaceType;
/*  52 */     this.blockSetId = other.blockSetId;
/*  53 */     this.blockTypeId = other.blockTypeId;
/*  54 */     this.tagIndex = other.tagIndex;
/*  55 */     this.fluidId = other.fluidId;
/*  56 */     this.support = other.support;
/*  57 */     this.matchSelf = other.matchSelf;
/*  58 */     this.allowSupportPropagation = other.allowSupportPropagation;
/*  59 */     this.rotate = other.rotate;
/*  60 */     this.filler = other.filler;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RequiredBlockFaceSupport deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     RequiredBlockFaceSupport obj = new RequiredBlockFaceSupport();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.blockTypeId = buf.getIntLE(offset + 1);
/*  68 */     obj.tagIndex = buf.getIntLE(offset + 5);
/*  69 */     obj.fluidId = buf.getIntLE(offset + 9);
/*  70 */     obj.support = SupportMatch.fromValue(buf.getByte(offset + 13));
/*  71 */     obj.matchSelf = SupportMatch.fromValue(buf.getByte(offset + 14));
/*  72 */     obj.allowSupportPropagation = (buf.getByte(offset + 15) != 0);
/*  73 */     obj.rotate = (buf.getByte(offset + 16) != 0);
/*     */     
/*  75 */     if ((nullBits & 0x1) != 0) {
/*  76 */       int varPos0 = offset + 33 + buf.getIntLE(offset + 17);
/*  77 */       int faceTypeLen = VarInt.peek(buf, varPos0);
/*  78 */       if (faceTypeLen < 0) throw ProtocolException.negativeLength("FaceType", faceTypeLen); 
/*  79 */       if (faceTypeLen > 4096000) throw ProtocolException.stringTooLong("FaceType", faceTypeLen, 4096000); 
/*  80 */       obj.faceType = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  82 */     if ((nullBits & 0x2) != 0) {
/*  83 */       int varPos1 = offset + 33 + buf.getIntLE(offset + 21);
/*  84 */       int selfFaceTypeLen = VarInt.peek(buf, varPos1);
/*  85 */       if (selfFaceTypeLen < 0) throw ProtocolException.negativeLength("SelfFaceType", selfFaceTypeLen); 
/*  86 */       if (selfFaceTypeLen > 4096000) throw ProtocolException.stringTooLong("SelfFaceType", selfFaceTypeLen, 4096000); 
/*  87 */       obj.selfFaceType = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  89 */     if ((nullBits & 0x4) != 0) {
/*  90 */       int varPos2 = offset + 33 + buf.getIntLE(offset + 25);
/*  91 */       int blockSetIdLen = VarInt.peek(buf, varPos2);
/*  92 */       if (blockSetIdLen < 0) throw ProtocolException.negativeLength("BlockSetId", blockSetIdLen); 
/*  93 */       if (blockSetIdLen > 4096000) throw ProtocolException.stringTooLong("BlockSetId", blockSetIdLen, 4096000); 
/*  94 */       obj.blockSetId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  96 */     if ((nullBits & 0x8) != 0) {
/*  97 */       int varPos3 = offset + 33 + buf.getIntLE(offset + 29);
/*  98 */       int fillerCount = VarInt.peek(buf, varPos3);
/*  99 */       if (fillerCount < 0) throw ProtocolException.negativeLength("Filler", fillerCount); 
/* 100 */       if (fillerCount > 4096000) throw ProtocolException.arrayTooLong("Filler", fillerCount, 4096000); 
/* 101 */       int varIntLen = VarInt.length(buf, varPos3);
/* 102 */       if ((varPos3 + varIntLen) + fillerCount * 12L > buf.readableBytes())
/* 103 */         throw ProtocolException.bufferTooSmall("Filler", varPos3 + varIntLen + fillerCount * 12, buf.readableBytes()); 
/* 104 */       obj.filler = new Vector3i[fillerCount];
/* 105 */       int elemPos = varPos3 + varIntLen;
/* 106 */       for (int i = 0; i < fillerCount; i++) {
/* 107 */         obj.filler[i] = Vector3i.deserialize(buf, elemPos);
/* 108 */         elemPos += Vector3i.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 116 */     byte nullBits = buf.getByte(offset);
/* 117 */     int maxEnd = 33;
/* 118 */     if ((nullBits & 0x1) != 0) {
/* 119 */       int fieldOffset0 = buf.getIntLE(offset + 17);
/* 120 */       int pos0 = offset + 33 + fieldOffset0;
/* 121 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 122 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 124 */     if ((nullBits & 0x2) != 0) {
/* 125 */       int fieldOffset1 = buf.getIntLE(offset + 21);
/* 126 */       int pos1 = offset + 33 + fieldOffset1;
/* 127 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 128 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 130 */     if ((nullBits & 0x4) != 0) {
/* 131 */       int fieldOffset2 = buf.getIntLE(offset + 25);
/* 132 */       int pos2 = offset + 33 + fieldOffset2;
/* 133 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 134 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 136 */     if ((nullBits & 0x8) != 0) {
/* 137 */       int fieldOffset3 = buf.getIntLE(offset + 29);
/* 138 */       int pos3 = offset + 33 + fieldOffset3;
/* 139 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 140 */       for (int i = 0; i < arrLen; ) { pos3 += Vector3i.computeBytesConsumed(buf, pos3); i++; }
/* 141 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 143 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 148 */     int startPos = buf.writerIndex();
/* 149 */     byte nullBits = 0;
/* 150 */     if (this.faceType != null) nullBits = (byte)(nullBits | 0x1); 
/* 151 */     if (this.selfFaceType != null) nullBits = (byte)(nullBits | 0x2); 
/* 152 */     if (this.blockSetId != null) nullBits = (byte)(nullBits | 0x4); 
/* 153 */     if (this.filler != null) nullBits = (byte)(nullBits | 0x8); 
/* 154 */     buf.writeByte(nullBits);
/*     */     
/* 156 */     buf.writeIntLE(this.blockTypeId);
/* 157 */     buf.writeIntLE(this.tagIndex);
/* 158 */     buf.writeIntLE(this.fluidId);
/* 159 */     buf.writeByte(this.support.getValue());
/* 160 */     buf.writeByte(this.matchSelf.getValue());
/* 161 */     buf.writeByte(this.allowSupportPropagation ? 1 : 0);
/* 162 */     buf.writeByte(this.rotate ? 1 : 0);
/*     */     
/* 164 */     int faceTypeOffsetSlot = buf.writerIndex();
/* 165 */     buf.writeIntLE(0);
/* 166 */     int selfFaceTypeOffsetSlot = buf.writerIndex();
/* 167 */     buf.writeIntLE(0);
/* 168 */     int blockSetIdOffsetSlot = buf.writerIndex();
/* 169 */     buf.writeIntLE(0);
/* 170 */     int fillerOffsetSlot = buf.writerIndex();
/* 171 */     buf.writeIntLE(0);
/*     */     
/* 173 */     int varBlockStart = buf.writerIndex();
/* 174 */     if (this.faceType != null) {
/* 175 */       buf.setIntLE(faceTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 176 */       PacketIO.writeVarString(buf, this.faceType, 4096000);
/*     */     } else {
/* 178 */       buf.setIntLE(faceTypeOffsetSlot, -1);
/*     */     } 
/* 180 */     if (this.selfFaceType != null) {
/* 181 */       buf.setIntLE(selfFaceTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 182 */       PacketIO.writeVarString(buf, this.selfFaceType, 4096000);
/*     */     } else {
/* 184 */       buf.setIntLE(selfFaceTypeOffsetSlot, -1);
/*     */     } 
/* 186 */     if (this.blockSetId != null) {
/* 187 */       buf.setIntLE(blockSetIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 188 */       PacketIO.writeVarString(buf, this.blockSetId, 4096000);
/*     */     } else {
/* 190 */       buf.setIntLE(blockSetIdOffsetSlot, -1);
/*     */     } 
/* 192 */     if (this.filler != null) {
/* 193 */       buf.setIntLE(fillerOffsetSlot, buf.writerIndex() - varBlockStart);
/* 194 */       if (this.filler.length > 4096000) throw ProtocolException.arrayTooLong("Filler", this.filler.length, 4096000);  VarInt.write(buf, this.filler.length); for (Vector3i item : this.filler) item.serialize(buf); 
/*     */     } else {
/* 196 */       buf.setIntLE(fillerOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 202 */     int size = 33;
/* 203 */     if (this.faceType != null) size += PacketIO.stringSize(this.faceType); 
/* 204 */     if (this.selfFaceType != null) size += PacketIO.stringSize(this.selfFaceType); 
/* 205 */     if (this.blockSetId != null) size += PacketIO.stringSize(this.blockSetId); 
/* 206 */     if (this.filler != null) size += VarInt.size(this.filler.length) + this.filler.length * 12;
/*     */     
/* 208 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 212 */     if (buffer.readableBytes() - offset < 33) {
/* 213 */       return ValidationResult.error("Buffer too small: expected at least 33 bytes");
/*     */     }
/*     */     
/* 216 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 219 */     if ((nullBits & 0x1) != 0) {
/* 220 */       int faceTypeOffset = buffer.getIntLE(offset + 17);
/* 221 */       if (faceTypeOffset < 0) {
/* 222 */         return ValidationResult.error("Invalid offset for FaceType");
/*     */       }
/* 224 */       int pos = offset + 33 + faceTypeOffset;
/* 225 */       if (pos >= buffer.writerIndex()) {
/* 226 */         return ValidationResult.error("Offset out of bounds for FaceType");
/*     */       }
/* 228 */       int faceTypeLen = VarInt.peek(buffer, pos);
/* 229 */       if (faceTypeLen < 0) {
/* 230 */         return ValidationResult.error("Invalid string length for FaceType");
/*     */       }
/* 232 */       if (faceTypeLen > 4096000) {
/* 233 */         return ValidationResult.error("FaceType exceeds max length 4096000");
/*     */       }
/* 235 */       pos += VarInt.length(buffer, pos);
/* 236 */       pos += faceTypeLen;
/* 237 */       if (pos > buffer.writerIndex()) {
/* 238 */         return ValidationResult.error("Buffer overflow reading FaceType");
/*     */       }
/*     */     } 
/*     */     
/* 242 */     if ((nullBits & 0x2) != 0) {
/* 243 */       int selfFaceTypeOffset = buffer.getIntLE(offset + 21);
/* 244 */       if (selfFaceTypeOffset < 0) {
/* 245 */         return ValidationResult.error("Invalid offset for SelfFaceType");
/*     */       }
/* 247 */       int pos = offset + 33 + selfFaceTypeOffset;
/* 248 */       if (pos >= buffer.writerIndex()) {
/* 249 */         return ValidationResult.error("Offset out of bounds for SelfFaceType");
/*     */       }
/* 251 */       int selfFaceTypeLen = VarInt.peek(buffer, pos);
/* 252 */       if (selfFaceTypeLen < 0) {
/* 253 */         return ValidationResult.error("Invalid string length for SelfFaceType");
/*     */       }
/* 255 */       if (selfFaceTypeLen > 4096000) {
/* 256 */         return ValidationResult.error("SelfFaceType exceeds max length 4096000");
/*     */       }
/* 258 */       pos += VarInt.length(buffer, pos);
/* 259 */       pos += selfFaceTypeLen;
/* 260 */       if (pos > buffer.writerIndex()) {
/* 261 */         return ValidationResult.error("Buffer overflow reading SelfFaceType");
/*     */       }
/*     */     } 
/*     */     
/* 265 */     if ((nullBits & 0x4) != 0) {
/* 266 */       int blockSetIdOffset = buffer.getIntLE(offset + 25);
/* 267 */       if (blockSetIdOffset < 0) {
/* 268 */         return ValidationResult.error("Invalid offset for BlockSetId");
/*     */       }
/* 270 */       int pos = offset + 33 + blockSetIdOffset;
/* 271 */       if (pos >= buffer.writerIndex()) {
/* 272 */         return ValidationResult.error("Offset out of bounds for BlockSetId");
/*     */       }
/* 274 */       int blockSetIdLen = VarInt.peek(buffer, pos);
/* 275 */       if (blockSetIdLen < 0) {
/* 276 */         return ValidationResult.error("Invalid string length for BlockSetId");
/*     */       }
/* 278 */       if (blockSetIdLen > 4096000) {
/* 279 */         return ValidationResult.error("BlockSetId exceeds max length 4096000");
/*     */       }
/* 281 */       pos += VarInt.length(buffer, pos);
/* 282 */       pos += blockSetIdLen;
/* 283 */       if (pos > buffer.writerIndex()) {
/* 284 */         return ValidationResult.error("Buffer overflow reading BlockSetId");
/*     */       }
/*     */     } 
/*     */     
/* 288 */     if ((nullBits & 0x8) != 0) {
/* 289 */       int fillerOffset = buffer.getIntLE(offset + 29);
/* 290 */       if (fillerOffset < 0) {
/* 291 */         return ValidationResult.error("Invalid offset for Filler");
/*     */       }
/* 293 */       int pos = offset + 33 + fillerOffset;
/* 294 */       if (pos >= buffer.writerIndex()) {
/* 295 */         return ValidationResult.error("Offset out of bounds for Filler");
/*     */       }
/* 297 */       int fillerCount = VarInt.peek(buffer, pos);
/* 298 */       if (fillerCount < 0) {
/* 299 */         return ValidationResult.error("Invalid array count for Filler");
/*     */       }
/* 301 */       if (fillerCount > 4096000) {
/* 302 */         return ValidationResult.error("Filler exceeds max length 4096000");
/*     */       }
/* 304 */       pos += VarInt.length(buffer, pos);
/* 305 */       pos += fillerCount * 12;
/* 306 */       if (pos > buffer.writerIndex()) {
/* 307 */         return ValidationResult.error("Buffer overflow reading Filler");
/*     */       }
/*     */     } 
/* 310 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RequiredBlockFaceSupport clone() {
/* 314 */     RequiredBlockFaceSupport copy = new RequiredBlockFaceSupport();
/* 315 */     copy.faceType = this.faceType;
/* 316 */     copy.selfFaceType = this.selfFaceType;
/* 317 */     copy.blockSetId = this.blockSetId;
/* 318 */     copy.blockTypeId = this.blockTypeId;
/* 319 */     copy.tagIndex = this.tagIndex;
/* 320 */     copy.fluidId = this.fluidId;
/* 321 */     copy.support = this.support;
/* 322 */     copy.matchSelf = this.matchSelf;
/* 323 */     copy.allowSupportPropagation = this.allowSupportPropagation;
/* 324 */     copy.rotate = this.rotate;
/* 325 */     copy.filler = (this.filler != null) ? (Vector3i[])Arrays.<Vector3i>stream(this.filler).map(e -> e.clone()).toArray(x$0 -> new Vector3i[x$0]) : null;
/* 326 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RequiredBlockFaceSupport other;
/* 332 */     if (this == obj) return true; 
/* 333 */     if (obj instanceof RequiredBlockFaceSupport) { other = (RequiredBlockFaceSupport)obj; } else { return false; }
/* 334 */      return (Objects.equals(this.faceType, other.faceType) && Objects.equals(this.selfFaceType, other.selfFaceType) && Objects.equals(this.blockSetId, other.blockSetId) && this.blockTypeId == other.blockTypeId && this.tagIndex == other.tagIndex && this.fluidId == other.fluidId && Objects.equals(this.support, other.support) && Objects.equals(this.matchSelf, other.matchSelf) && this.allowSupportPropagation == other.allowSupportPropagation && this.rotate == other.rotate && Arrays.equals((Object[])this.filler, (Object[])other.filler));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 339 */     int result = 1;
/* 340 */     result = 31 * result + Objects.hashCode(this.faceType);
/* 341 */     result = 31 * result + Objects.hashCode(this.selfFaceType);
/* 342 */     result = 31 * result + Objects.hashCode(this.blockSetId);
/* 343 */     result = 31 * result + Integer.hashCode(this.blockTypeId);
/* 344 */     result = 31 * result + Integer.hashCode(this.tagIndex);
/* 345 */     result = 31 * result + Integer.hashCode(this.fluidId);
/* 346 */     result = 31 * result + Objects.hashCode(this.support);
/* 347 */     result = 31 * result + Objects.hashCode(this.matchSelf);
/* 348 */     result = 31 * result + Boolean.hashCode(this.allowSupportPropagation);
/* 349 */     result = 31 * result + Boolean.hashCode(this.rotate);
/* 350 */     result = 31 * result + Arrays.hashCode((Object[])this.filler);
/* 351 */     return result;
/*     */   }
/*     */   
/*     */   public RequiredBlockFaceSupport() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RequiredBlockFaceSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */