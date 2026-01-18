/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetEditorAssetType
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 3;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 19;
/*     */   public static final int MAX_SIZE = 65536039;
/*     */   @Nonnull
/*  25 */   public AssetEditorEditorType editorType = AssetEditorEditorType.None; @Nullable
/*     */   public String id; @Nullable
/*     */   public String icon; public boolean isColoredIcon; @Nullable
/*     */   public String path; @Nullable
/*     */   public String fileExtension;
/*     */   public AssetEditorAssetType(@Nullable String id, @Nullable String icon, boolean isColoredIcon, @Nullable String path, @Nullable String fileExtension, @Nonnull AssetEditorEditorType editorType) {
/*  31 */     this.id = id;
/*  32 */     this.icon = icon;
/*  33 */     this.isColoredIcon = isColoredIcon;
/*  34 */     this.path = path;
/*  35 */     this.fileExtension = fileExtension;
/*  36 */     this.editorType = editorType;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetType(@Nonnull AssetEditorAssetType other) {
/*  40 */     this.id = other.id;
/*  41 */     this.icon = other.icon;
/*  42 */     this.isColoredIcon = other.isColoredIcon;
/*  43 */     this.path = other.path;
/*  44 */     this.fileExtension = other.fileExtension;
/*  45 */     this.editorType = other.editorType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorAssetType deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     AssetEditorAssetType obj = new AssetEditorAssetType();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.isColoredIcon = (buf.getByte(offset + 1) != 0);
/*  53 */     obj.editorType = AssetEditorEditorType.fromValue(buf.getByte(offset + 2));
/*     */     
/*  55 */     if ((nullBits & 0x1) != 0) {
/*  56 */       int varPos0 = offset + 19 + buf.getIntLE(offset + 3);
/*  57 */       int idLen = VarInt.peek(buf, varPos0);
/*  58 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  59 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  60 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  62 */     if ((nullBits & 0x2) != 0) {
/*  63 */       int varPos1 = offset + 19 + buf.getIntLE(offset + 7);
/*  64 */       int iconLen = VarInt.peek(buf, varPos1);
/*  65 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  66 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  67 */       obj.icon = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  69 */     if ((nullBits & 0x4) != 0) {
/*  70 */       int varPos2 = offset + 19 + buf.getIntLE(offset + 11);
/*  71 */       int pathLen = VarInt.peek(buf, varPos2);
/*  72 */       if (pathLen < 0) throw ProtocolException.negativeLength("Path", pathLen); 
/*  73 */       if (pathLen > 4096000) throw ProtocolException.stringTooLong("Path", pathLen, 4096000); 
/*  74 */       obj.path = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  76 */     if ((nullBits & 0x8) != 0) {
/*  77 */       int varPos3 = offset + 19 + buf.getIntLE(offset + 15);
/*  78 */       int fileExtensionLen = VarInt.peek(buf, varPos3);
/*  79 */       if (fileExtensionLen < 0) throw ProtocolException.negativeLength("FileExtension", fileExtensionLen); 
/*  80 */       if (fileExtensionLen > 4096000) throw ProtocolException.stringTooLong("FileExtension", fileExtensionLen, 4096000); 
/*  81 */       obj.fileExtension = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int maxEnd = 19;
/*  90 */     if ((nullBits & 0x1) != 0) {
/*  91 */       int fieldOffset0 = buf.getIntLE(offset + 3);
/*  92 */       int pos0 = offset + 19 + fieldOffset0;
/*  93 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  94 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  96 */     if ((nullBits & 0x2) != 0) {
/*  97 */       int fieldOffset1 = buf.getIntLE(offset + 7);
/*  98 */       int pos1 = offset + 19 + fieldOffset1;
/*  99 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 100 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 102 */     if ((nullBits & 0x4) != 0) {
/* 103 */       int fieldOffset2 = buf.getIntLE(offset + 11);
/* 104 */       int pos2 = offset + 19 + fieldOffset2;
/* 105 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 106 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 108 */     if ((nullBits & 0x8) != 0) {
/* 109 */       int fieldOffset3 = buf.getIntLE(offset + 15);
/* 110 */       int pos3 = offset + 19 + fieldOffset3;
/* 111 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 112 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 114 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 119 */     int startPos = buf.writerIndex();
/* 120 */     byte nullBits = 0;
/* 121 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 122 */     if (this.icon != null) nullBits = (byte)(nullBits | 0x2); 
/* 123 */     if (this.path != null) nullBits = (byte)(nullBits | 0x4); 
/* 124 */     if (this.fileExtension != null) nullBits = (byte)(nullBits | 0x8); 
/* 125 */     buf.writeByte(nullBits);
/*     */     
/* 127 */     buf.writeByte(this.isColoredIcon ? 1 : 0);
/* 128 */     buf.writeByte(this.editorType.getValue());
/*     */     
/* 130 */     int idOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int iconOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/* 134 */     int pathOffsetSlot = buf.writerIndex();
/* 135 */     buf.writeIntLE(0);
/* 136 */     int fileExtensionOffsetSlot = buf.writerIndex();
/* 137 */     buf.writeIntLE(0);
/*     */     
/* 139 */     int varBlockStart = buf.writerIndex();
/* 140 */     if (this.id != null) {
/* 141 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 142 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 144 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 146 */     if (this.icon != null) {
/* 147 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*     */     } else {
/* 150 */       buf.setIntLE(iconOffsetSlot, -1);
/*     */     } 
/* 152 */     if (this.path != null) {
/* 153 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 154 */       PacketIO.writeVarString(buf, this.path, 4096000);
/*     */     } else {
/* 156 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 158 */     if (this.fileExtension != null) {
/* 159 */       buf.setIntLE(fileExtensionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 160 */       PacketIO.writeVarString(buf, this.fileExtension, 4096000);
/*     */     } else {
/* 162 */       buf.setIntLE(fileExtensionOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 168 */     int size = 19;
/* 169 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 170 */     if (this.icon != null) size += PacketIO.stringSize(this.icon); 
/* 171 */     if (this.path != null) size += PacketIO.stringSize(this.path); 
/* 172 */     if (this.fileExtension != null) size += PacketIO.stringSize(this.fileExtension);
/*     */     
/* 174 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 178 */     if (buffer.readableBytes() - offset < 19) {
/* 179 */       return ValidationResult.error("Buffer too small: expected at least 19 bytes");
/*     */     }
/*     */     
/* 182 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 185 */     if ((nullBits & 0x1) != 0) {
/* 186 */       int idOffset = buffer.getIntLE(offset + 3);
/* 187 */       if (idOffset < 0) {
/* 188 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 190 */       int pos = offset + 19 + idOffset;
/* 191 */       if (pos >= buffer.writerIndex()) {
/* 192 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 194 */       int idLen = VarInt.peek(buffer, pos);
/* 195 */       if (idLen < 0) {
/* 196 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 198 */       if (idLen > 4096000) {
/* 199 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 201 */       pos += VarInt.length(buffer, pos);
/* 202 */       pos += idLen;
/* 203 */       if (pos > buffer.writerIndex()) {
/* 204 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 208 */     if ((nullBits & 0x2) != 0) {
/* 209 */       int iconOffset = buffer.getIntLE(offset + 7);
/* 210 */       if (iconOffset < 0) {
/* 211 */         return ValidationResult.error("Invalid offset for Icon");
/*     */       }
/* 213 */       int pos = offset + 19 + iconOffset;
/* 214 */       if (pos >= buffer.writerIndex()) {
/* 215 */         return ValidationResult.error("Offset out of bounds for Icon");
/*     */       }
/* 217 */       int iconLen = VarInt.peek(buffer, pos);
/* 218 */       if (iconLen < 0) {
/* 219 */         return ValidationResult.error("Invalid string length for Icon");
/*     */       }
/* 221 */       if (iconLen > 4096000) {
/* 222 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*     */       }
/* 224 */       pos += VarInt.length(buffer, pos);
/* 225 */       pos += iconLen;
/* 226 */       if (pos > buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Buffer overflow reading Icon");
/*     */       }
/*     */     } 
/*     */     
/* 231 */     if ((nullBits & 0x4) != 0) {
/* 232 */       int pathOffset = buffer.getIntLE(offset + 11);
/* 233 */       if (pathOffset < 0) {
/* 234 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 236 */       int pos = offset + 19 + pathOffset;
/* 237 */       if (pos >= buffer.writerIndex()) {
/* 238 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 240 */       int pathLen = VarInt.peek(buffer, pos);
/* 241 */       if (pathLen < 0) {
/* 242 */         return ValidationResult.error("Invalid string length for Path");
/*     */       }
/* 244 */       if (pathLen > 4096000) {
/* 245 */         return ValidationResult.error("Path exceeds max length 4096000");
/*     */       }
/* 247 */       pos += VarInt.length(buffer, pos);
/* 248 */       pos += pathLen;
/* 249 */       if (pos > buffer.writerIndex()) {
/* 250 */         return ValidationResult.error("Buffer overflow reading Path");
/*     */       }
/*     */     } 
/*     */     
/* 254 */     if ((nullBits & 0x8) != 0) {
/* 255 */       int fileExtensionOffset = buffer.getIntLE(offset + 15);
/* 256 */       if (fileExtensionOffset < 0) {
/* 257 */         return ValidationResult.error("Invalid offset for FileExtension");
/*     */       }
/* 259 */       int pos = offset + 19 + fileExtensionOffset;
/* 260 */       if (pos >= buffer.writerIndex()) {
/* 261 */         return ValidationResult.error("Offset out of bounds for FileExtension");
/*     */       }
/* 263 */       int fileExtensionLen = VarInt.peek(buffer, pos);
/* 264 */       if (fileExtensionLen < 0) {
/* 265 */         return ValidationResult.error("Invalid string length for FileExtension");
/*     */       }
/* 267 */       if (fileExtensionLen > 4096000) {
/* 268 */         return ValidationResult.error("FileExtension exceeds max length 4096000");
/*     */       }
/* 270 */       pos += VarInt.length(buffer, pos);
/* 271 */       pos += fileExtensionLen;
/* 272 */       if (pos > buffer.writerIndex()) {
/* 273 */         return ValidationResult.error("Buffer overflow reading FileExtension");
/*     */       }
/*     */     } 
/* 276 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorAssetType clone() {
/* 280 */     AssetEditorAssetType copy = new AssetEditorAssetType();
/* 281 */     copy.id = this.id;
/* 282 */     copy.icon = this.icon;
/* 283 */     copy.isColoredIcon = this.isColoredIcon;
/* 284 */     copy.path = this.path;
/* 285 */     copy.fileExtension = this.fileExtension;
/* 286 */     copy.editorType = this.editorType;
/* 287 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorAssetType other;
/* 293 */     if (this == obj) return true; 
/* 294 */     if (obj instanceof AssetEditorAssetType) { other = (AssetEditorAssetType)obj; } else { return false; }
/* 295 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.icon, other.icon) && this.isColoredIcon == other.isColoredIcon && Objects.equals(this.path, other.path) && Objects.equals(this.fileExtension, other.fileExtension) && Objects.equals(this.editorType, other.editorType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 300 */     return Objects.hash(new Object[] { this.id, this.icon, Boolean.valueOf(this.isColoredIcon), this.path, this.fileExtension, this.editorType });
/*     */   }
/*     */   
/*     */   public AssetEditorAssetType() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorAssetType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */