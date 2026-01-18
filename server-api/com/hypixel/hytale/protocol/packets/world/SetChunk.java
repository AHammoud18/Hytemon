/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetChunk
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 131;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 25;
/*     */   
/*     */   public int getId() {
/*  25 */     return 131;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 12288040;
/*     */   
/*     */   public int x;
/*     */   
/*     */   public int y;
/*     */   
/*     */   public int z;
/*     */ 
/*     */   
/*     */   public SetChunk(int x, int y, int z, @Nullable byte[] localLight, @Nullable byte[] globalLight, @Nullable byte[] data) {
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.z = z;
/*  42 */     this.localLight = localLight;
/*  43 */     this.globalLight = globalLight;
/*  44 */     this.data = data; } @Nullable
/*     */   public byte[] localLight; @Nullable
/*     */   public byte[] globalLight; @Nullable
/*     */   public byte[] data; public SetChunk() {} public SetChunk(@Nonnull SetChunk other) {
/*  48 */     this.x = other.x;
/*  49 */     this.y = other.y;
/*  50 */     this.z = other.z;
/*  51 */     this.localLight = other.localLight;
/*  52 */     this.globalLight = other.globalLight;
/*  53 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetChunk deserialize(@Nonnull ByteBuf buf, int offset) {
/*  58 */     SetChunk obj = new SetChunk();
/*  59 */     byte nullBits = buf.getByte(offset);
/*  60 */     obj.x = buf.getIntLE(offset + 1);
/*  61 */     obj.y = buf.getIntLE(offset + 5);
/*  62 */     obj.z = buf.getIntLE(offset + 9);
/*     */     
/*  64 */     if ((nullBits & 0x1) != 0) {
/*  65 */       int varPos0 = offset + 25 + buf.getIntLE(offset + 13);
/*  66 */       int localLightCount = VarInt.peek(buf, varPos0);
/*  67 */       if (localLightCount < 0) throw ProtocolException.negativeLength("LocalLight", localLightCount); 
/*  68 */       if (localLightCount > 4096000) throw ProtocolException.arrayTooLong("LocalLight", localLightCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos0);
/*  70 */       if ((varPos0 + varIntLen) + localLightCount * 1L > buf.readableBytes())
/*  71 */         throw ProtocolException.bufferTooSmall("LocalLight", varPos0 + varIntLen + localLightCount * 1, buf.readableBytes()); 
/*  72 */       obj.localLight = new byte[localLightCount];
/*  73 */       for (int i = 0; i < localLightCount; i++) {
/*  74 */         obj.localLight[i] = buf.getByte(varPos0 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*  77 */     if ((nullBits & 0x2) != 0) {
/*  78 */       int varPos1 = offset + 25 + buf.getIntLE(offset + 17);
/*  79 */       int globalLightCount = VarInt.peek(buf, varPos1);
/*  80 */       if (globalLightCount < 0) throw ProtocolException.negativeLength("GlobalLight", globalLightCount); 
/*  81 */       if (globalLightCount > 4096000) throw ProtocolException.arrayTooLong("GlobalLight", globalLightCount, 4096000); 
/*  82 */       int varIntLen = VarInt.length(buf, varPos1);
/*  83 */       if ((varPos1 + varIntLen) + globalLightCount * 1L > buf.readableBytes())
/*  84 */         throw ProtocolException.bufferTooSmall("GlobalLight", varPos1 + varIntLen + globalLightCount * 1, buf.readableBytes()); 
/*  85 */       obj.globalLight = new byte[globalLightCount];
/*  86 */       for (int i = 0; i < globalLightCount; i++) {
/*  87 */         obj.globalLight[i] = buf.getByte(varPos1 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*  90 */     if ((nullBits & 0x4) != 0) {
/*  91 */       int varPos2 = offset + 25 + buf.getIntLE(offset + 21);
/*  92 */       int dataCount = VarInt.peek(buf, varPos2);
/*  93 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  94 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  95 */       int varIntLen = VarInt.length(buf, varPos2);
/*  96 */       if ((varPos2 + varIntLen) + dataCount * 1L > buf.readableBytes())
/*  97 */         throw ProtocolException.bufferTooSmall("Data", varPos2 + varIntLen + dataCount * 1, buf.readableBytes()); 
/*  98 */       obj.data = new byte[dataCount];
/*  99 */       for (int i = 0; i < dataCount; i++) {
/* 100 */         obj.data[i] = buf.getByte(varPos2 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 108 */     byte nullBits = buf.getByte(offset);
/* 109 */     int maxEnd = 25;
/* 110 */     if ((nullBits & 0x1) != 0) {
/* 111 */       int fieldOffset0 = buf.getIntLE(offset + 13);
/* 112 */       int pos0 = offset + 25 + fieldOffset0;
/* 113 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + arrLen * 1;
/* 114 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 116 */     if ((nullBits & 0x2) != 0) {
/* 117 */       int fieldOffset1 = buf.getIntLE(offset + 17);
/* 118 */       int pos1 = offset + 25 + fieldOffset1;
/* 119 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 1;
/* 120 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x4) != 0) {
/* 123 */       int fieldOffset2 = buf.getIntLE(offset + 21);
/* 124 */       int pos2 = offset + 25 + fieldOffset2;
/* 125 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/* 126 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 128 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 134 */     int startPos = buf.writerIndex();
/* 135 */     byte nullBits = 0;
/* 136 */     if (this.localLight != null) nullBits = (byte)(nullBits | 0x1); 
/* 137 */     if (this.globalLight != null) nullBits = (byte)(nullBits | 0x2); 
/* 138 */     if (this.data != null) nullBits = (byte)(nullBits | 0x4); 
/* 139 */     buf.writeByte(nullBits);
/*     */     
/* 141 */     buf.writeIntLE(this.x);
/* 142 */     buf.writeIntLE(this.y);
/* 143 */     buf.writeIntLE(this.z);
/*     */     
/* 145 */     int localLightOffsetSlot = buf.writerIndex();
/* 146 */     buf.writeIntLE(0);
/* 147 */     int globalLightOffsetSlot = buf.writerIndex();
/* 148 */     buf.writeIntLE(0);
/* 149 */     int dataOffsetSlot = buf.writerIndex();
/* 150 */     buf.writeIntLE(0);
/*     */     
/* 152 */     int varBlockStart = buf.writerIndex();
/* 153 */     if (this.localLight != null) {
/* 154 */       buf.setIntLE(localLightOffsetSlot, buf.writerIndex() - varBlockStart);
/* 155 */       if (this.localLight.length > 4096000) throw ProtocolException.arrayTooLong("LocalLight", this.localLight.length, 4096000);  VarInt.write(buf, this.localLight.length); for (byte item : this.localLight) buf.writeByte(item); 
/*     */     } else {
/* 157 */       buf.setIntLE(localLightOffsetSlot, -1);
/*     */     } 
/* 159 */     if (this.globalLight != null) {
/* 160 */       buf.setIntLE(globalLightOffsetSlot, buf.writerIndex() - varBlockStart);
/* 161 */       if (this.globalLight.length > 4096000) throw ProtocolException.arrayTooLong("GlobalLight", this.globalLight.length, 4096000);  VarInt.write(buf, this.globalLight.length); for (byte item : this.globalLight) buf.writeByte(item); 
/*     */     } else {
/* 163 */       buf.setIntLE(globalLightOffsetSlot, -1);
/*     */     } 
/* 165 */     if (this.data != null) {
/* 166 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 167 */       if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (byte item : this.data) buf.writeByte(item); 
/*     */     } else {
/* 169 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 175 */     int size = 25;
/* 176 */     if (this.localLight != null) size += VarInt.size(this.localLight.length) + this.localLight.length * 1; 
/* 177 */     if (this.globalLight != null) size += VarInt.size(this.globalLight.length) + this.globalLight.length * 1; 
/* 178 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 1;
/*     */     
/* 180 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 184 */     if (buffer.readableBytes() - offset < 25) {
/* 185 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*     */     }
/*     */     
/* 188 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 191 */     if ((nullBits & 0x1) != 0) {
/* 192 */       int localLightOffset = buffer.getIntLE(offset + 13);
/* 193 */       if (localLightOffset < 0) {
/* 194 */         return ValidationResult.error("Invalid offset for LocalLight");
/*     */       }
/* 196 */       int pos = offset + 25 + localLightOffset;
/* 197 */       if (pos >= buffer.writerIndex()) {
/* 198 */         return ValidationResult.error("Offset out of bounds for LocalLight");
/*     */       }
/* 200 */       int localLightCount = VarInt.peek(buffer, pos);
/* 201 */       if (localLightCount < 0) {
/* 202 */         return ValidationResult.error("Invalid array count for LocalLight");
/*     */       }
/* 204 */       if (localLightCount > 4096000) {
/* 205 */         return ValidationResult.error("LocalLight exceeds max length 4096000");
/*     */       }
/* 207 */       pos += VarInt.length(buffer, pos);
/* 208 */       pos += localLightCount * 1;
/* 209 */       if (pos > buffer.writerIndex()) {
/* 210 */         return ValidationResult.error("Buffer overflow reading LocalLight");
/*     */       }
/*     */     } 
/*     */     
/* 214 */     if ((nullBits & 0x2) != 0) {
/* 215 */       int globalLightOffset = buffer.getIntLE(offset + 17);
/* 216 */       if (globalLightOffset < 0) {
/* 217 */         return ValidationResult.error("Invalid offset for GlobalLight");
/*     */       }
/* 219 */       int pos = offset + 25 + globalLightOffset;
/* 220 */       if (pos >= buffer.writerIndex()) {
/* 221 */         return ValidationResult.error("Offset out of bounds for GlobalLight");
/*     */       }
/* 223 */       int globalLightCount = VarInt.peek(buffer, pos);
/* 224 */       if (globalLightCount < 0) {
/* 225 */         return ValidationResult.error("Invalid array count for GlobalLight");
/*     */       }
/* 227 */       if (globalLightCount > 4096000) {
/* 228 */         return ValidationResult.error("GlobalLight exceeds max length 4096000");
/*     */       }
/* 230 */       pos += VarInt.length(buffer, pos);
/* 231 */       pos += globalLightCount * 1;
/* 232 */       if (pos > buffer.writerIndex()) {
/* 233 */         return ValidationResult.error("Buffer overflow reading GlobalLight");
/*     */       }
/*     */     } 
/*     */     
/* 237 */     if ((nullBits & 0x4) != 0) {
/* 238 */       int dataOffset = buffer.getIntLE(offset + 21);
/* 239 */       if (dataOffset < 0) {
/* 240 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 242 */       int pos = offset + 25 + dataOffset;
/* 243 */       if (pos >= buffer.writerIndex()) {
/* 244 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 246 */       int dataCount = VarInt.peek(buffer, pos);
/* 247 */       if (dataCount < 0) {
/* 248 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 250 */       if (dataCount > 4096000) {
/* 251 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 253 */       pos += VarInt.length(buffer, pos);
/* 254 */       pos += dataCount * 1;
/* 255 */       if (pos > buffer.writerIndex()) {
/* 256 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 259 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetChunk clone() {
/* 263 */     SetChunk copy = new SetChunk();
/* 264 */     copy.x = this.x;
/* 265 */     copy.y = this.y;
/* 266 */     copy.z = this.z;
/* 267 */     copy.localLight = (this.localLight != null) ? Arrays.copyOf(this.localLight, this.localLight.length) : null;
/* 268 */     copy.globalLight = (this.globalLight != null) ? Arrays.copyOf(this.globalLight, this.globalLight.length) : null;
/* 269 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 270 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetChunk other;
/* 276 */     if (this == obj) return true; 
/* 277 */     if (obj instanceof SetChunk) { other = (SetChunk)obj; } else { return false; }
/* 278 */      return (this.x == other.x && this.y == other.y && this.z == other.z && Arrays.equals(this.localLight, other.localLight) && Arrays.equals(this.globalLight, other.globalLight) && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 283 */     int result = 1;
/* 284 */     result = 31 * result + Integer.hashCode(this.x);
/* 285 */     result = 31 * result + Integer.hashCode(this.y);
/* 286 */     result = 31 * result + Integer.hashCode(this.z);
/* 287 */     result = 31 * result + Arrays.hashCode(this.localLight);
/* 288 */     result = 31 * result + Arrays.hashCode(this.globalLight);
/* 289 */     result = 31 * result + Arrays.hashCode(this.data);
/* 290 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */