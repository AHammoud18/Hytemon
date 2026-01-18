/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class OffsetNoise
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 282624028;
/*     */   @Nullable
/*     */   public NoiseConfig[] x;
/*     */   @Nullable
/*     */   public NoiseConfig[] y;
/*     */   @Nullable
/*     */   public NoiseConfig[] z;
/*     */   
/*     */   public OffsetNoise() {}
/*     */   
/*     */   public OffsetNoise(@Nullable NoiseConfig[] x, @Nullable NoiseConfig[] y, @Nullable NoiseConfig[] z) {
/*  28 */     this.x = x;
/*  29 */     this.y = y;
/*  30 */     this.z = z;
/*     */   }
/*     */   
/*     */   public OffsetNoise(@Nonnull OffsetNoise other) {
/*  34 */     this.x = other.x;
/*  35 */     this.y = other.y;
/*  36 */     this.z = other.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static OffsetNoise deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     OffsetNoise obj = new OffsetNoise();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       int xCount = VarInt.peek(buf, varPos0);
/*  47 */       if (xCount < 0) throw ProtocolException.negativeLength("X", xCount); 
/*  48 */       if (xCount > 4096000) throw ProtocolException.arrayTooLong("X", xCount, 4096000); 
/*  49 */       int varIntLen = VarInt.length(buf, varPos0);
/*  50 */       if ((varPos0 + varIntLen) + xCount * 23L > buf.readableBytes())
/*  51 */         throw ProtocolException.bufferTooSmall("X", varPos0 + varIntLen + xCount * 23, buf.readableBytes()); 
/*  52 */       obj.x = new NoiseConfig[xCount];
/*  53 */       int elemPos = varPos0 + varIntLen;
/*  54 */       for (int i = 0; i < xCount; i++) {
/*  55 */         obj.x[i] = NoiseConfig.deserialize(buf, elemPos);
/*  56 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  59 */     if ((nullBits & 0x2) != 0) {
/*  60 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  61 */       int yCount = VarInt.peek(buf, varPos1);
/*  62 */       if (yCount < 0) throw ProtocolException.negativeLength("Y", yCount); 
/*  63 */       if (yCount > 4096000) throw ProtocolException.arrayTooLong("Y", yCount, 4096000); 
/*  64 */       int varIntLen = VarInt.length(buf, varPos1);
/*  65 */       if ((varPos1 + varIntLen) + yCount * 23L > buf.readableBytes())
/*  66 */         throw ProtocolException.bufferTooSmall("Y", varPos1 + varIntLen + yCount * 23, buf.readableBytes()); 
/*  67 */       obj.y = new NoiseConfig[yCount];
/*  68 */       int elemPos = varPos1 + varIntLen;
/*  69 */       for (int i = 0; i < yCount; i++) {
/*  70 */         obj.y[i] = NoiseConfig.deserialize(buf, elemPos);
/*  71 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  74 */     if ((nullBits & 0x4) != 0) {
/*  75 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  76 */       int zCount = VarInt.peek(buf, varPos2);
/*  77 */       if (zCount < 0) throw ProtocolException.negativeLength("Z", zCount); 
/*  78 */       if (zCount > 4096000) throw ProtocolException.arrayTooLong("Z", zCount, 4096000); 
/*  79 */       int varIntLen = VarInt.length(buf, varPos2);
/*  80 */       if ((varPos2 + varIntLen) + zCount * 23L > buf.readableBytes())
/*  81 */         throw ProtocolException.bufferTooSmall("Z", varPos2 + varIntLen + zCount * 23, buf.readableBytes()); 
/*  82 */       obj.z = new NoiseConfig[zCount];
/*  83 */       int elemPos = varPos2 + varIntLen;
/*  84 */       for (int i = 0; i < zCount; i++) {
/*  85 */         obj.z[i] = NoiseConfig.deserialize(buf, elemPos);
/*  86 */         elemPos += NoiseConfig.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  94 */     byte nullBits = buf.getByte(offset);
/*  95 */     int maxEnd = 13;
/*  96 */     if ((nullBits & 0x1) != 0) {
/*  97 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  98 */       int pos0 = offset + 13 + fieldOffset0;
/*  99 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 100 */       for (int i = 0; i < arrLen; ) { pos0 += NoiseConfig.computeBytesConsumed(buf, pos0); i++; }
/* 101 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 103 */     if ((nullBits & 0x2) != 0) {
/* 104 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 105 */       int pos1 = offset + 13 + fieldOffset1;
/* 106 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 107 */       for (int i = 0; i < arrLen; ) { pos1 += NoiseConfig.computeBytesConsumed(buf, pos1); i++; }
/* 108 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 110 */     if ((nullBits & 0x4) != 0) {
/* 111 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 112 */       int pos2 = offset + 13 + fieldOffset2;
/* 113 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 114 */       for (int i = 0; i < arrLen; ) { pos2 += NoiseConfig.computeBytesConsumed(buf, pos2); i++; }
/* 115 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 117 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 122 */     int startPos = buf.writerIndex();
/* 123 */     byte nullBits = 0;
/* 124 */     if (this.x != null) nullBits = (byte)(nullBits | 0x1); 
/* 125 */     if (this.y != null) nullBits = (byte)(nullBits | 0x2); 
/* 126 */     if (this.z != null) nullBits = (byte)(nullBits | 0x4); 
/* 127 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 130 */     int xOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int yOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/* 134 */     int zOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/*     */     
/* 137 */     int varBlockStart = buf.writerIndex();
/* 138 */     if (this.x != null) {
/* 139 */       buf.setIntLE(xOffsetSlot, buf.writerIndex() - varBlockStart);
/* 140 */       if (this.x.length > 4096000) throw ProtocolException.arrayTooLong("X", this.x.length, 4096000);  VarInt.write(buf, this.x.length); for (NoiseConfig item : this.x) item.serialize(buf); 
/*     */     } else {
/* 142 */       buf.setIntLE(xOffsetSlot, -1);
/*     */     } 
/* 144 */     if (this.y != null) {
/* 145 */       buf.setIntLE(yOffsetSlot, buf.writerIndex() - varBlockStart);
/* 146 */       if (this.y.length > 4096000) throw ProtocolException.arrayTooLong("Y", this.y.length, 4096000);  VarInt.write(buf, this.y.length); for (NoiseConfig item : this.y) item.serialize(buf); 
/*     */     } else {
/* 148 */       buf.setIntLE(yOffsetSlot, -1);
/*     */     } 
/* 150 */     if (this.z != null) {
/* 151 */       buf.setIntLE(zOffsetSlot, buf.writerIndex() - varBlockStart);
/* 152 */       if (this.z.length > 4096000) throw ProtocolException.arrayTooLong("Z", this.z.length, 4096000);  VarInt.write(buf, this.z.length); for (NoiseConfig item : this.z) item.serialize(buf); 
/*     */     } else {
/* 154 */       buf.setIntLE(zOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 160 */     int size = 13;
/* 161 */     if (this.x != null) size += VarInt.size(this.x.length) + this.x.length * 23; 
/* 162 */     if (this.y != null) size += VarInt.size(this.y.length) + this.y.length * 23; 
/* 163 */     if (this.z != null) size += VarInt.size(this.z.length) + this.z.length * 23;
/*     */     
/* 165 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 169 */     if (buffer.readableBytes() - offset < 13) {
/* 170 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 173 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 176 */     if ((nullBits & 0x1) != 0) {
/* 177 */       int xOffset = buffer.getIntLE(offset + 1);
/* 178 */       if (xOffset < 0) {
/* 179 */         return ValidationResult.error("Invalid offset for X");
/*     */       }
/* 181 */       int pos = offset + 13 + xOffset;
/* 182 */       if (pos >= buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Offset out of bounds for X");
/*     */       }
/* 185 */       int xCount = VarInt.peek(buffer, pos);
/* 186 */       if (xCount < 0) {
/* 187 */         return ValidationResult.error("Invalid array count for X");
/*     */       }
/* 189 */       if (xCount > 4096000) {
/* 190 */         return ValidationResult.error("X exceeds max length 4096000");
/*     */       }
/* 192 */       pos += VarInt.length(buffer, pos);
/* 193 */       pos += xCount * 23;
/* 194 */       if (pos > buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Buffer overflow reading X");
/*     */       }
/*     */     } 
/*     */     
/* 199 */     if ((nullBits & 0x2) != 0) {
/* 200 */       int yOffset = buffer.getIntLE(offset + 5);
/* 201 */       if (yOffset < 0) {
/* 202 */         return ValidationResult.error("Invalid offset for Y");
/*     */       }
/* 204 */       int pos = offset + 13 + yOffset;
/* 205 */       if (pos >= buffer.writerIndex()) {
/* 206 */         return ValidationResult.error("Offset out of bounds for Y");
/*     */       }
/* 208 */       int yCount = VarInt.peek(buffer, pos);
/* 209 */       if (yCount < 0) {
/* 210 */         return ValidationResult.error("Invalid array count for Y");
/*     */       }
/* 212 */       if (yCount > 4096000) {
/* 213 */         return ValidationResult.error("Y exceeds max length 4096000");
/*     */       }
/* 215 */       pos += VarInt.length(buffer, pos);
/* 216 */       pos += yCount * 23;
/* 217 */       if (pos > buffer.writerIndex()) {
/* 218 */         return ValidationResult.error("Buffer overflow reading Y");
/*     */       }
/*     */     } 
/*     */     
/* 222 */     if ((nullBits & 0x4) != 0) {
/* 223 */       int zOffset = buffer.getIntLE(offset + 9);
/* 224 */       if (zOffset < 0) {
/* 225 */         return ValidationResult.error("Invalid offset for Z");
/*     */       }
/* 227 */       int pos = offset + 13 + zOffset;
/* 228 */       if (pos >= buffer.writerIndex()) {
/* 229 */         return ValidationResult.error("Offset out of bounds for Z");
/*     */       }
/* 231 */       int zCount = VarInt.peek(buffer, pos);
/* 232 */       if (zCount < 0) {
/* 233 */         return ValidationResult.error("Invalid array count for Z");
/*     */       }
/* 235 */       if (zCount > 4096000) {
/* 236 */         return ValidationResult.error("Z exceeds max length 4096000");
/*     */       }
/* 238 */       pos += VarInt.length(buffer, pos);
/* 239 */       pos += zCount * 23;
/* 240 */       if (pos > buffer.writerIndex()) {
/* 241 */         return ValidationResult.error("Buffer overflow reading Z");
/*     */       }
/*     */     } 
/* 244 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public OffsetNoise clone() {
/* 248 */     OffsetNoise copy = new OffsetNoise();
/* 249 */     copy.x = (this.x != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.x).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 250 */     copy.y = (this.y != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.y).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 251 */     copy.z = (this.z != null) ? (NoiseConfig[])Arrays.<NoiseConfig>stream(this.z).map(e -> e.clone()).toArray(x$0 -> new NoiseConfig[x$0]) : null;
/* 252 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     OffsetNoise other;
/* 258 */     if (this == obj) return true; 
/* 259 */     if (obj instanceof OffsetNoise) { other = (OffsetNoise)obj; } else { return false; }
/* 260 */      return (Arrays.equals((Object[])this.x, (Object[])other.x) && Arrays.equals((Object[])this.y, (Object[])other.y) && Arrays.equals((Object[])this.z, (Object[])other.z));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 265 */     int result = 1;
/* 266 */     result = 31 * result + Arrays.hashCode((Object[])this.x);
/* 267 */     result = 31 * result + Arrays.hashCode((Object[])this.y);
/* 268 */     result = 31 * result + Arrays.hashCode((Object[])this.z);
/* 269 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\OffsetNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */