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
/*     */ public class AssetEditorCreateAsset
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 327;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 10;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 22;
/*     */   
/*     */   public int getId() {
/*  25 */     return 327;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 53248051;
/*     */   
/*     */   public int token;
/*     */   
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */ 
/*     */   
/*     */   public AssetEditorCreateAsset(int token, @Nullable AssetPath path, @Nullable byte[] data, @Nullable AssetEditorRebuildCaches rebuildCaches, @Nullable String buttonId) {
/*  38 */     this.token = token;
/*  39 */     this.path = path;
/*  40 */     this.data = data;
/*  41 */     this.rebuildCaches = rebuildCaches;
/*  42 */     this.buttonId = buttonId; } @Nullable
/*     */   public byte[] data; @Nullable
/*     */   public AssetEditorRebuildCaches rebuildCaches; @Nullable
/*     */   public String buttonId; public AssetEditorCreateAsset() {} public AssetEditorCreateAsset(@Nonnull AssetEditorCreateAsset other) {
/*  46 */     this.token = other.token;
/*  47 */     this.path = other.path;
/*  48 */     this.data = other.data;
/*  49 */     this.rebuildCaches = other.rebuildCaches;
/*  50 */     this.buttonId = other.buttonId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorCreateAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorCreateAsset obj = new AssetEditorCreateAsset();
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     obj.token = buf.getIntLE(offset + 1);
/*  58 */     if ((nullBits & 0x4) != 0) obj.rebuildCaches = AssetEditorRebuildCaches.deserialize(buf, offset + 5);
/*     */     
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int varPos0 = offset + 22 + buf.getIntLE(offset + 10);
/*  62 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 22 + buf.getIntLE(offset + 14);
/*  66 */       int dataCount = VarInt.peek(buf, varPos1);
/*  67 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  68 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  69 */       int varIntLen = VarInt.length(buf, varPos1);
/*  70 */       if ((varPos1 + varIntLen) + dataCount * 1L > buf.readableBytes())
/*  71 */         throw ProtocolException.bufferTooSmall("Data", varPos1 + varIntLen + dataCount * 1, buf.readableBytes()); 
/*  72 */       obj.data = new byte[dataCount];
/*  73 */       for (int i = 0; i < dataCount; i++) {
/*  74 */         obj.data[i] = buf.getByte(varPos1 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*  77 */     if ((nullBits & 0x8) != 0) {
/*  78 */       int varPos2 = offset + 22 + buf.getIntLE(offset + 18);
/*  79 */       int buttonIdLen = VarInt.peek(buf, varPos2);
/*  80 */       if (buttonIdLen < 0) throw ProtocolException.negativeLength("ButtonId", buttonIdLen); 
/*  81 */       if (buttonIdLen > 4096000) throw ProtocolException.stringTooLong("ButtonId", buttonIdLen, 4096000); 
/*  82 */       obj.buttonId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  85 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  89 */     byte nullBits = buf.getByte(offset);
/*  90 */     int maxEnd = 22;
/*  91 */     if ((nullBits & 0x1) != 0) {
/*  92 */       int fieldOffset0 = buf.getIntLE(offset + 10);
/*  93 */       int pos0 = offset + 22 + fieldOffset0;
/*  94 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  95 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  97 */     if ((nullBits & 0x2) != 0) {
/*  98 */       int fieldOffset1 = buf.getIntLE(offset + 14);
/*  99 */       int pos1 = offset + 22 + fieldOffset1;
/* 100 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 1;
/* 101 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 103 */     if ((nullBits & 0x8) != 0) {
/* 104 */       int fieldOffset2 = buf.getIntLE(offset + 18);
/* 105 */       int pos2 = offset + 22 + fieldOffset2;
/* 106 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
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
/* 117 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/* 118 */     if (this.data != null) nullBits = (byte)(nullBits | 0x2); 
/* 119 */     if (this.rebuildCaches != null) nullBits = (byte)(nullBits | 0x4); 
/* 120 */     if (this.buttonId != null) nullBits = (byte)(nullBits | 0x8); 
/* 121 */     buf.writeByte(nullBits);
/*     */     
/* 123 */     buf.writeIntLE(this.token);
/* 124 */     if (this.rebuildCaches != null) { this.rebuildCaches.serialize(buf); } else { buf.writeZero(5); }
/*     */     
/* 126 */     int pathOffsetSlot = buf.writerIndex();
/* 127 */     buf.writeIntLE(0);
/* 128 */     int dataOffsetSlot = buf.writerIndex();
/* 129 */     buf.writeIntLE(0);
/* 130 */     int buttonIdOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/*     */     
/* 133 */     int varBlockStart = buf.writerIndex();
/* 134 */     if (this.path != null) {
/* 135 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 136 */       this.path.serialize(buf);
/*     */     } else {
/* 138 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 140 */     if (this.data != null) {
/* 141 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 142 */       if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (byte item : this.data) buf.writeByte(item); 
/*     */     } else {
/* 144 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/* 146 */     if (this.buttonId != null) {
/* 147 */       buf.setIntLE(buttonIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       PacketIO.writeVarString(buf, this.buttonId, 4096000);
/*     */     } else {
/* 150 */       buf.setIntLE(buttonIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 156 */     int size = 22;
/* 157 */     if (this.path != null) size += this.path.computeSize(); 
/* 158 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 1; 
/* 159 */     if (this.buttonId != null) size += PacketIO.stringSize(this.buttonId);
/*     */     
/* 161 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 165 */     if (buffer.readableBytes() - offset < 22) {
/* 166 */       return ValidationResult.error("Buffer too small: expected at least 22 bytes");
/*     */     }
/*     */     
/* 169 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 172 */     if ((nullBits & 0x1) != 0) {
/* 173 */       int pathOffset = buffer.getIntLE(offset + 10);
/* 174 */       if (pathOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 177 */       int pos = offset + 22 + pathOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 181 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 182 */       if (!pathResult.isValid()) {
/* 183 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 185 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 188 */     if ((nullBits & 0x2) != 0) {
/* 189 */       int dataOffset = buffer.getIntLE(offset + 14);
/* 190 */       if (dataOffset < 0) {
/* 191 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 193 */       int pos = offset + 22 + dataOffset;
/* 194 */       if (pos >= buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 197 */       int dataCount = VarInt.peek(buffer, pos);
/* 198 */       if (dataCount < 0) {
/* 199 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 201 */       if (dataCount > 4096000) {
/* 202 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 204 */       pos += VarInt.length(buffer, pos);
/* 205 */       pos += dataCount * 1;
/* 206 */       if (pos > buffer.writerIndex()) {
/* 207 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if ((nullBits & 0x8) != 0) {
/* 212 */       int buttonIdOffset = buffer.getIntLE(offset + 18);
/* 213 */       if (buttonIdOffset < 0) {
/* 214 */         return ValidationResult.error("Invalid offset for ButtonId");
/*     */       }
/* 216 */       int pos = offset + 22 + buttonIdOffset;
/* 217 */       if (pos >= buffer.writerIndex()) {
/* 218 */         return ValidationResult.error("Offset out of bounds for ButtonId");
/*     */       }
/* 220 */       int buttonIdLen = VarInt.peek(buffer, pos);
/* 221 */       if (buttonIdLen < 0) {
/* 222 */         return ValidationResult.error("Invalid string length for ButtonId");
/*     */       }
/* 224 */       if (buttonIdLen > 4096000) {
/* 225 */         return ValidationResult.error("ButtonId exceeds max length 4096000");
/*     */       }
/* 227 */       pos += VarInt.length(buffer, pos);
/* 228 */       pos += buttonIdLen;
/* 229 */       if (pos > buffer.writerIndex()) {
/* 230 */         return ValidationResult.error("Buffer overflow reading ButtonId");
/*     */       }
/*     */     } 
/* 233 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorCreateAsset clone() {
/* 237 */     AssetEditorCreateAsset copy = new AssetEditorCreateAsset();
/* 238 */     copy.token = this.token;
/* 239 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 240 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 241 */     copy.rebuildCaches = (this.rebuildCaches != null) ? this.rebuildCaches.clone() : null;
/* 242 */     copy.buttonId = this.buttonId;
/* 243 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorCreateAsset other;
/* 249 */     if (this == obj) return true; 
/* 250 */     if (obj instanceof AssetEditorCreateAsset) { other = (AssetEditorCreateAsset)obj; } else { return false; }
/* 251 */      return (this.token == other.token && Objects.equals(this.path, other.path) && Arrays.equals(this.data, other.data) && Objects.equals(this.rebuildCaches, other.rebuildCaches) && Objects.equals(this.buttonId, other.buttonId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     int result = 1;
/* 257 */     result = 31 * result + Integer.hashCode(this.token);
/* 258 */     result = 31 * result + Objects.hashCode(this.path);
/* 259 */     result = 31 * result + Arrays.hashCode(this.data);
/* 260 */     result = 31 * result + Objects.hashCode(this.rebuildCaches);
/* 261 */     result = 31 * result + Objects.hashCode(this.buttonId);
/* 262 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorCreateAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */