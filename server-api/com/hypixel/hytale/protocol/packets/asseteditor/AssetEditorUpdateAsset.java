/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class AssetEditorUpdateAsset
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 324;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 324;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 21; public static final int MAX_SIZE = 53248050; public int token; @Nullable
/*     */   public String assetType;
/*     */   @Nullable
/*     */   public AssetPath path;
/*  31 */   public int assetIndex = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   public byte[] data;
/*     */ 
/*     */   
/*     */   public AssetEditorUpdateAsset(int token, @Nullable String assetType, @Nullable AssetPath path, int assetIndex, @Nullable byte[] data) {
/*  38 */     this.token = token;
/*  39 */     this.assetType = assetType;
/*  40 */     this.path = path;
/*  41 */     this.assetIndex = assetIndex;
/*  42 */     this.data = data;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateAsset(@Nonnull AssetEditorUpdateAsset other) {
/*  46 */     this.token = other.token;
/*  47 */     this.assetType = other.assetType;
/*  48 */     this.path = other.path;
/*  49 */     this.assetIndex = other.assetIndex;
/*  50 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorUpdateAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorUpdateAsset obj = new AssetEditorUpdateAsset();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.token = buf.getIntLE(offset + 1);
/*  58 */     obj.assetIndex = buf.getIntLE(offset + 5);
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 21 + buf.getIntLE(offset + 9);
/*  62 */       int assetTypeLen = VarInt.peek(buf, varPos0);
/*  63 */       if (assetTypeLen < 0) throw ProtocolException.negativeLength("AssetType", assetTypeLen); 
/*  64 */       if (assetTypeLen > 4096000) throw ProtocolException.stringTooLong("AssetType", assetTypeLen, 4096000); 
/*  65 */       obj.assetType = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 21 + buf.getIntLE(offset + 13);
/*  69 */       obj.path = AssetPath.deserialize(buf, varPos1);
/*     */     } 
/*  71 */     if ((nullBits & 0x4) != 0) {
/*  72 */       int varPos2 = offset + 21 + buf.getIntLE(offset + 17);
/*  73 */       int dataCount = VarInt.peek(buf, varPos2);
/*  74 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  75 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  76 */       int varIntLen = VarInt.length(buf, varPos2);
/*  77 */       if ((varPos2 + varIntLen) + dataCount * 1L > buf.readableBytes())
/*  78 */         throw ProtocolException.bufferTooSmall("Data", varPos2 + varIntLen + dataCount * 1, buf.readableBytes()); 
/*  79 */       obj.data = new byte[dataCount];
/*  80 */       for (int i = 0; i < dataCount; i++) {
/*  81 */         obj.data[i] = buf.getByte(varPos2 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 21;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  93 */       int pos0 = offset + 21 + fieldOffset0;
/*  94 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x2) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  99 */       int pos1 = offset + 21 + fieldOffset1;
/* 100 */       pos1 += AssetPath.computeBytesConsumed(buf, pos1);
/* 101 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 103 */     if ((nullBits & 0x4) != 0) {
/* 104 */       int fieldOffset2 = buf.getIntLE(offset + 17);
/* 105 */       int pos2 = offset + 21 + fieldOffset2;
/* 106 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/* 107 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 109 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 115 */     int startPos = buf.writerIndex();
/* 116 */     byte nullBits = 0;
/* 117 */     if (this.assetType != null) nullBits = (byte)(nullBits | 0x1); 
/* 118 */     if (this.path != null) nullBits = (byte)(nullBits | 0x2); 
/* 119 */     if (this.data != null) nullBits = (byte)(nullBits | 0x4); 
/* 120 */     buf.writeByte(nullBits);
/*     */     
/* 122 */     buf.writeIntLE(this.token);
/* 123 */     buf.writeIntLE(this.assetIndex);
/*     */     
/* 125 */     int assetTypeOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/* 127 */     int pathOffsetSlot = buf.writerIndex();
/* 128 */     buf.writeIntLE(0);
/* 129 */     int dataOffsetSlot = buf.writerIndex();
/* 130 */     buf.writeIntLE(0);
/*     */     
/* 132 */     int varBlockStart = buf.writerIndex();
/* 133 */     if (this.assetType != null) {
/* 134 */       buf.setIntLE(assetTypeOffsetSlot, buf.writerIndex() - varBlockStart);
/* 135 */       PacketIO.writeVarString(buf, this.assetType, 4096000);
/*     */     } else {
/* 137 */       buf.setIntLE(assetTypeOffsetSlot, -1);
/*     */     } 
/* 139 */     if (this.path != null) {
/* 140 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 141 */       this.path.serialize(buf);
/*     */     } else {
/* 143 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 145 */     if (this.data != null) {
/* 146 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 147 */       if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (byte item : this.data) buf.writeByte(item); 
/*     */     } else {
/* 149 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 155 */     int size = 21;
/* 156 */     if (this.assetType != null) size += PacketIO.stringSize(this.assetType); 
/* 157 */     if (this.path != null) size += this.path.computeSize(); 
/* 158 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 1;
/*     */     
/* 160 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 164 */     if (buffer.readableBytes() - offset < 21) {
/* 165 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/* 168 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 171 */     if ((nullBits & 0x1) != 0) {
/* 172 */       int assetTypeOffset = buffer.getIntLE(offset + 9);
/* 173 */       if (assetTypeOffset < 0) {
/* 174 */         return ValidationResult.error("Invalid offset for AssetType");
/*     */       }
/* 176 */       int pos = offset + 21 + assetTypeOffset;
/* 177 */       if (pos >= buffer.writerIndex()) {
/* 178 */         return ValidationResult.error("Offset out of bounds for AssetType");
/*     */       }
/* 180 */       int assetTypeLen = VarInt.peek(buffer, pos);
/* 181 */       if (assetTypeLen < 0) {
/* 182 */         return ValidationResult.error("Invalid string length for AssetType");
/*     */       }
/* 184 */       if (assetTypeLen > 4096000) {
/* 185 */         return ValidationResult.error("AssetType exceeds max length 4096000");
/*     */       }
/* 187 */       pos += VarInt.length(buffer, pos);
/* 188 */       pos += assetTypeLen;
/* 189 */       if (pos > buffer.writerIndex()) {
/* 190 */         return ValidationResult.error("Buffer overflow reading AssetType");
/*     */       }
/*     */     } 
/*     */     
/* 194 */     if ((nullBits & 0x2) != 0) {
/* 195 */       int pathOffset = buffer.getIntLE(offset + 13);
/* 196 */       if (pathOffset < 0) {
/* 197 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 199 */       int pos = offset + 21 + pathOffset;
/* 200 */       if (pos >= buffer.writerIndex()) {
/* 201 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 203 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 204 */       if (!pathResult.isValid()) {
/* 205 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 207 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 210 */     if ((nullBits & 0x4) != 0) {
/* 211 */       int dataOffset = buffer.getIntLE(offset + 17);
/* 212 */       if (dataOffset < 0) {
/* 213 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 215 */       int pos = offset + 21 + dataOffset;
/* 216 */       if (pos >= buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 219 */       int dataCount = VarInt.peek(buffer, pos);
/* 220 */       if (dataCount < 0) {
/* 221 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 223 */       if (dataCount > 4096000) {
/* 224 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 226 */       pos += VarInt.length(buffer, pos);
/* 227 */       pos += dataCount * 1;
/* 228 */       if (pos > buffer.writerIndex()) {
/* 229 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 232 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateAsset clone() {
/* 236 */     AssetEditorUpdateAsset copy = new AssetEditorUpdateAsset();
/* 237 */     copy.token = this.token;
/* 238 */     copy.assetType = this.assetType;
/* 239 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 240 */     copy.assetIndex = this.assetIndex;
/* 241 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 242 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorUpdateAsset other;
/* 248 */     if (this == obj) return true; 
/* 249 */     if (obj instanceof AssetEditorUpdateAsset) { other = (AssetEditorUpdateAsset)obj; } else { return false; }
/* 250 */      return (this.token == other.token && Objects.equals(this.assetType, other.assetType) && Objects.equals(this.path, other.path) && this.assetIndex == other.assetIndex && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 255 */     int result = 1;
/* 256 */     result = 31 * result + Integer.hashCode(this.token);
/* 257 */     result = 31 * result + Objects.hashCode(this.assetType);
/* 258 */     result = 31 * result + Objects.hashCode(this.path);
/* 259 */     result = 31 * result + Integer.hashCode(this.assetIndex);
/* 260 */     result = 31 * result + Arrays.hashCode(this.data);
/* 261 */     return result;
/*     */   }
/*     */   
/*     */   public AssetEditorUpdateAsset() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUpdateAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */