/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class AssetEditorAssetUpdated
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 326;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 326;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 2; public static final int VARIABLE_BLOCK_START = 9; public static final int MAX_SIZE = 36864033; @Nullable
/*     */   public AssetPath path;
/*     */   @Nullable
/*     */   public byte[] data;
/*     */   
/*     */   public AssetEditorAssetUpdated() {}
/*     */   
/*     */   public AssetEditorAssetUpdated(@Nullable AssetPath path, @Nullable byte[] data) {
/*  35 */     this.path = path;
/*  36 */     this.data = data;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetUpdated(@Nonnull AssetEditorAssetUpdated other) {
/*  40 */     this.path = other.path;
/*  41 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAssetUpdated deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorAssetUpdated obj = new AssetEditorAssetUpdated();
/*  47 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  51 */       obj.path = AssetPath.deserialize(buf, varPos0);
/*     */     } 
/*  53 */     if ((nullBits & 0x2) != 0) {
/*  54 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  55 */       int dataCount = VarInt.peek(buf, varPos1);
/*  56 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  57 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  58 */       int varIntLen = VarInt.length(buf, varPos1);
/*  59 */       if ((varPos1 + varIntLen) + dataCount * 1L > buf.readableBytes())
/*  60 */         throw ProtocolException.bufferTooSmall("Data", varPos1 + varIntLen + dataCount * 1, buf.readableBytes()); 
/*  61 */       obj.data = new byte[dataCount];
/*  62 */       for (int i = 0; i < dataCount; i++) {
/*  63 */         obj.data[i] = buf.getByte(varPos1 + varIntLen + i * 1);
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  71 */     byte nullBits = buf.getByte(offset);
/*  72 */     int maxEnd = 9;
/*  73 */     if ((nullBits & 0x1) != 0) {
/*  74 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  75 */       int pos0 = offset + 9 + fieldOffset0;
/*  76 */       pos0 += AssetPath.computeBytesConsumed(buf, pos0);
/*  77 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  79 */     if ((nullBits & 0x2) != 0) {
/*  80 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  81 */       int pos1 = offset + 9 + fieldOffset1;
/*  82 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 1;
/*  83 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  85 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  91 */     int startPos = buf.writerIndex();
/*  92 */     byte nullBits = 0;
/*  93 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  94 */     if (this.data != null) nullBits = (byte)(nullBits | 0x2); 
/*  95 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  98 */     int pathOffsetSlot = buf.writerIndex();
/*  99 */     buf.writeIntLE(0);
/* 100 */     int dataOffsetSlot = buf.writerIndex();
/* 101 */     buf.writeIntLE(0);
/*     */     
/* 103 */     int varBlockStart = buf.writerIndex();
/* 104 */     if (this.path != null) {
/* 105 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 106 */       this.path.serialize(buf);
/*     */     } else {
/* 108 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 110 */     if (this.data != null) {
/* 111 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 112 */       if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (byte item : this.data) buf.writeByte(item); 
/*     */     } else {
/* 114 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 120 */     int size = 9;
/* 121 */     if (this.path != null) size += this.path.computeSize(); 
/* 122 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 1;
/*     */     
/* 124 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 128 */     if (buffer.readableBytes() - offset < 9) {
/* 129 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 132 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 135 */     if ((nullBits & 0x1) != 0) {
/* 136 */       int pathOffset = buffer.getIntLE(offset + 1);
/* 137 */       if (pathOffset < 0) {
/* 138 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 140 */       int pos = offset + 9 + pathOffset;
/* 141 */       if (pos >= buffer.writerIndex()) {
/* 142 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 144 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 145 */       if (!pathResult.isValid()) {
/* 146 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 148 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 151 */     if ((nullBits & 0x2) != 0) {
/* 152 */       int dataOffset = buffer.getIntLE(offset + 5);
/* 153 */       if (dataOffset < 0) {
/* 154 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 156 */       int pos = offset + 9 + dataOffset;
/* 157 */       if (pos >= buffer.writerIndex()) {
/* 158 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 160 */       int dataCount = VarInt.peek(buffer, pos);
/* 161 */       if (dataCount < 0) {
/* 162 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 164 */       if (dataCount > 4096000) {
/* 165 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 167 */       pos += VarInt.length(buffer, pos);
/* 168 */       pos += dataCount * 1;
/* 169 */       if (pos > buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 173 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetUpdated clone() {
/* 177 */     AssetEditorAssetUpdated copy = new AssetEditorAssetUpdated();
/* 178 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 179 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 180 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAssetUpdated other;
/* 186 */     if (this == obj) return true; 
/* 187 */     if (obj instanceof AssetEditorAssetUpdated) { other = (AssetEditorAssetUpdated)obj; } else { return false; }
/* 188 */      return (Objects.equals(this.path, other.path) && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 193 */     int result = 1;
/* 194 */     result = 31 * result + Objects.hashCode(this.path);
/* 195 */     result = 31 * result + Arrays.hashCode(this.data);
/* 196 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAssetUpdated.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */