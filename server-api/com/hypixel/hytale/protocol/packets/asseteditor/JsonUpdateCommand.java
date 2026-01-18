/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
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
/*     */ public class JsonUpdateCommand {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 7;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 23;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  20 */   public JsonUpdateType type = JsonUpdateType.SetProperty;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String[] path;
/*     */   
/*     */   @Nullable
/*     */   public String value;
/*     */ 
/*     */   
/*     */   public JsonUpdateCommand(@Nonnull JsonUpdateType type, @Nullable String[] path, @Nullable String value, @Nullable String previousValue, @Nullable String[] firstCreatedProperty, @Nullable AssetEditorRebuildCaches rebuildCaches) {
/*  31 */     this.type = type;
/*  32 */     this.path = path;
/*  33 */     this.value = value;
/*  34 */     this.previousValue = previousValue;
/*  35 */     this.firstCreatedProperty = firstCreatedProperty;
/*  36 */     this.rebuildCaches = rebuildCaches; } @Nullable
/*     */   public String previousValue; @Nullable
/*     */   public String[] firstCreatedProperty; @Nullable
/*     */   public AssetEditorRebuildCaches rebuildCaches; public JsonUpdateCommand(@Nonnull JsonUpdateCommand other) {
/*  40 */     this.type = other.type;
/*  41 */     this.path = other.path;
/*  42 */     this.value = other.value;
/*  43 */     this.previousValue = other.previousValue;
/*  44 */     this.firstCreatedProperty = other.firstCreatedProperty;
/*  45 */     this.rebuildCaches = other.rebuildCaches;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static JsonUpdateCommand deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     JsonUpdateCommand obj = new JsonUpdateCommand();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.type = JsonUpdateType.fromValue(buf.getByte(offset + 1));
/*  53 */     if ((nullBits & 0x10) != 0) obj.rebuildCaches = AssetEditorRebuildCaches.deserialize(buf, offset + 2);
/*     */     
/*  55 */     if ((nullBits & 0x1) != 0) {
/*  56 */       int varPos0 = offset + 23 + buf.getIntLE(offset + 7);
/*  57 */       int pathCount = VarInt.peek(buf, varPos0);
/*  58 */       if (pathCount < 0) throw ProtocolException.negativeLength("Path", pathCount); 
/*  59 */       if (pathCount > 4096000) throw ProtocolException.arrayTooLong("Path", pathCount, 4096000); 
/*  60 */       int varIntLen = VarInt.length(buf, varPos0);
/*  61 */       if ((varPos0 + varIntLen) + pathCount * 1L > buf.readableBytes())
/*  62 */         throw ProtocolException.bufferTooSmall("Path", varPos0 + varIntLen + pathCount * 1, buf.readableBytes()); 
/*  63 */       obj.path = new String[pathCount];
/*  64 */       int elemPos = varPos0 + varIntLen;
/*  65 */       for (int i = 0; i < pathCount; i++) {
/*  66 */         int strLen = VarInt.peek(buf, elemPos);
/*  67 */         if (strLen < 0) throw ProtocolException.negativeLength("path[" + i + "]", strLen); 
/*  68 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("path[" + i + "]", strLen, 4096000); 
/*  69 */         int strVarLen = VarInt.length(buf, elemPos);
/*  70 */         obj.path[i] = PacketIO.readVarString(buf, elemPos);
/*  71 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*  74 */     if ((nullBits & 0x2) != 0) {
/*  75 */       int varPos1 = offset + 23 + buf.getIntLE(offset + 11);
/*  76 */       int valueLen = VarInt.peek(buf, varPos1);
/*  77 */       if (valueLen < 0) throw ProtocolException.negativeLength("Value", valueLen); 
/*  78 */       if (valueLen > 4096000) throw ProtocolException.stringTooLong("Value", valueLen, 4096000); 
/*  79 */       obj.value = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  81 */     if ((nullBits & 0x4) != 0) {
/*  82 */       int varPos2 = offset + 23 + buf.getIntLE(offset + 15);
/*  83 */       int previousValueLen = VarInt.peek(buf, varPos2);
/*  84 */       if (previousValueLen < 0) throw ProtocolException.negativeLength("PreviousValue", previousValueLen); 
/*  85 */       if (previousValueLen > 4096000) throw ProtocolException.stringTooLong("PreviousValue", previousValueLen, 4096000); 
/*  86 */       obj.previousValue = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  88 */     if ((nullBits & 0x8) != 0) {
/*  89 */       int varPos3 = offset + 23 + buf.getIntLE(offset + 19);
/*  90 */       int firstCreatedPropertyCount = VarInt.peek(buf, varPos3);
/*  91 */       if (firstCreatedPropertyCount < 0) throw ProtocolException.negativeLength("FirstCreatedProperty", firstCreatedPropertyCount); 
/*  92 */       if (firstCreatedPropertyCount > 4096000) throw ProtocolException.arrayTooLong("FirstCreatedProperty", firstCreatedPropertyCount, 4096000); 
/*  93 */       int varIntLen = VarInt.length(buf, varPos3);
/*  94 */       if ((varPos3 + varIntLen) + firstCreatedPropertyCount * 1L > buf.readableBytes())
/*  95 */         throw ProtocolException.bufferTooSmall("FirstCreatedProperty", varPos3 + varIntLen + firstCreatedPropertyCount * 1, buf.readableBytes()); 
/*  96 */       obj.firstCreatedProperty = new String[firstCreatedPropertyCount];
/*  97 */       int elemPos = varPos3 + varIntLen;
/*  98 */       for (int i = 0; i < firstCreatedPropertyCount; i++) {
/*  99 */         int strLen = VarInt.peek(buf, elemPos);
/* 100 */         if (strLen < 0) throw ProtocolException.negativeLength("firstCreatedProperty[" + i + "]", strLen); 
/* 101 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("firstCreatedProperty[" + i + "]", strLen, 4096000); 
/* 102 */         int strVarLen = VarInt.length(buf, elemPos);
/* 103 */         obj.firstCreatedProperty[i] = PacketIO.readVarString(buf, elemPos);
/* 104 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 112 */     byte nullBits = buf.getByte(offset);
/* 113 */     int maxEnd = 23;
/* 114 */     if ((nullBits & 0x1) != 0) {
/* 115 */       int fieldOffset0 = buf.getIntLE(offset + 7);
/* 116 */       int pos0 = offset + 23 + fieldOffset0;
/* 117 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 118 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; i++; }
/* 119 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 121 */     if ((nullBits & 0x2) != 0) {
/* 122 */       int fieldOffset1 = buf.getIntLE(offset + 11);
/* 123 */       int pos1 = offset + 23 + fieldOffset1;
/* 124 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 125 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 127 */     if ((nullBits & 0x4) != 0) {
/* 128 */       int fieldOffset2 = buf.getIntLE(offset + 15);
/* 129 */       int pos2 = offset + 23 + fieldOffset2;
/* 130 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 131 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 133 */     if ((nullBits & 0x8) != 0) {
/* 134 */       int fieldOffset3 = buf.getIntLE(offset + 19);
/* 135 */       int pos3 = offset + 23 + fieldOffset3;
/* 136 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 137 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl; i++; }
/* 138 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 140 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 145 */     int startPos = buf.writerIndex();
/* 146 */     byte nullBits = 0;
/* 147 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/* 148 */     if (this.value != null) nullBits = (byte)(nullBits | 0x2); 
/* 149 */     if (this.previousValue != null) nullBits = (byte)(nullBits | 0x4); 
/* 150 */     if (this.firstCreatedProperty != null) nullBits = (byte)(nullBits | 0x8); 
/* 151 */     if (this.rebuildCaches != null) nullBits = (byte)(nullBits | 0x10); 
/* 152 */     buf.writeByte(nullBits);
/*     */     
/* 154 */     buf.writeByte(this.type.getValue());
/* 155 */     if (this.rebuildCaches != null) { this.rebuildCaches.serialize(buf); } else { buf.writeZero(5); }
/*     */     
/* 157 */     int pathOffsetSlot = buf.writerIndex();
/* 158 */     buf.writeIntLE(0);
/* 159 */     int valueOffsetSlot = buf.writerIndex();
/* 160 */     buf.writeIntLE(0);
/* 161 */     int previousValueOffsetSlot = buf.writerIndex();
/* 162 */     buf.writeIntLE(0);
/* 163 */     int firstCreatedPropertyOffsetSlot = buf.writerIndex();
/* 164 */     buf.writeIntLE(0);
/*     */     
/* 166 */     int varBlockStart = buf.writerIndex();
/* 167 */     if (this.path != null) {
/* 168 */       buf.setIntLE(pathOffsetSlot, buf.writerIndex() - varBlockStart);
/* 169 */       if (this.path.length > 4096000) throw ProtocolException.arrayTooLong("Path", this.path.length, 4096000);  VarInt.write(buf, this.path.length); for (String item : this.path) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 171 */       buf.setIntLE(pathOffsetSlot, -1);
/*     */     } 
/* 173 */     if (this.value != null) {
/* 174 */       buf.setIntLE(valueOffsetSlot, buf.writerIndex() - varBlockStart);
/* 175 */       PacketIO.writeVarString(buf, this.value, 4096000);
/*     */     } else {
/* 177 */       buf.setIntLE(valueOffsetSlot, -1);
/*     */     } 
/* 179 */     if (this.previousValue != null) {
/* 180 */       buf.setIntLE(previousValueOffsetSlot, buf.writerIndex() - varBlockStart);
/* 181 */       PacketIO.writeVarString(buf, this.previousValue, 4096000);
/*     */     } else {
/* 183 */       buf.setIntLE(previousValueOffsetSlot, -1);
/*     */     } 
/* 185 */     if (this.firstCreatedProperty != null) {
/* 186 */       buf.setIntLE(firstCreatedPropertyOffsetSlot, buf.writerIndex() - varBlockStart);
/* 187 */       if (this.firstCreatedProperty.length > 4096000) throw ProtocolException.arrayTooLong("FirstCreatedProperty", this.firstCreatedProperty.length, 4096000);  VarInt.write(buf, this.firstCreatedProperty.length); for (String item : this.firstCreatedProperty) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 189 */       buf.setIntLE(firstCreatedPropertyOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 195 */     int size = 23;
/* 196 */     if (this.path != null) {
/* 197 */       int pathSize = 0;
/* 198 */       for (String elem : this.path) pathSize += PacketIO.stringSize(elem); 
/* 199 */       size += VarInt.size(this.path.length) + pathSize;
/*     */     } 
/* 201 */     if (this.value != null) size += PacketIO.stringSize(this.value); 
/* 202 */     if (this.previousValue != null) size += PacketIO.stringSize(this.previousValue); 
/* 203 */     if (this.firstCreatedProperty != null) {
/* 204 */       int firstCreatedPropertySize = 0;
/* 205 */       for (String elem : this.firstCreatedProperty) firstCreatedPropertySize += PacketIO.stringSize(elem); 
/* 206 */       size += VarInt.size(this.firstCreatedProperty.length) + firstCreatedPropertySize;
/*     */     } 
/*     */     
/* 209 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 213 */     if (buffer.readableBytes() - offset < 23) {
/* 214 */       return ValidationResult.error("Buffer too small: expected at least 23 bytes");
/*     */     }
/*     */     
/* 217 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 220 */     if ((nullBits & 0x1) != 0) {
/* 221 */       int pathOffset = buffer.getIntLE(offset + 7);
/* 222 */       if (pathOffset < 0) {
/* 223 */         return ValidationResult.error("Invalid offset for Path");
/*     */       }
/* 225 */       int pos = offset + 23 + pathOffset;
/* 226 */       if (pos >= buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Offset out of bounds for Path");
/*     */       }
/* 229 */       int pathCount = VarInt.peek(buffer, pos);
/* 230 */       if (pathCount < 0) {
/* 231 */         return ValidationResult.error("Invalid array count for Path");
/*     */       }
/* 233 */       if (pathCount > 4096000) {
/* 234 */         return ValidationResult.error("Path exceeds max length 4096000");
/*     */       }
/* 236 */       pos += VarInt.length(buffer, pos);
/* 237 */       for (int i = 0; i < pathCount; i++) {
/* 238 */         int strLen = VarInt.peek(buffer, pos);
/* 239 */         if (strLen < 0) {
/* 240 */           return ValidationResult.error("Invalid string length in Path");
/*     */         }
/* 242 */         pos += VarInt.length(buffer, pos);
/* 243 */         pos += strLen;
/* 244 */         if (pos > buffer.writerIndex()) {
/* 245 */           return ValidationResult.error("Buffer overflow reading string in Path");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     if ((nullBits & 0x2) != 0) {
/* 251 */       int valueOffset = buffer.getIntLE(offset + 11);
/* 252 */       if (valueOffset < 0) {
/* 253 */         return ValidationResult.error("Invalid offset for Value");
/*     */       }
/* 255 */       int pos = offset + 23 + valueOffset;
/* 256 */       if (pos >= buffer.writerIndex()) {
/* 257 */         return ValidationResult.error("Offset out of bounds for Value");
/*     */       }
/* 259 */       int valueLen = VarInt.peek(buffer, pos);
/* 260 */       if (valueLen < 0) {
/* 261 */         return ValidationResult.error("Invalid string length for Value");
/*     */       }
/* 263 */       if (valueLen > 4096000) {
/* 264 */         return ValidationResult.error("Value exceeds max length 4096000");
/*     */       }
/* 266 */       pos += VarInt.length(buffer, pos);
/* 267 */       pos += valueLen;
/* 268 */       if (pos > buffer.writerIndex()) {
/* 269 */         return ValidationResult.error("Buffer overflow reading Value");
/*     */       }
/*     */     } 
/*     */     
/* 273 */     if ((nullBits & 0x4) != 0) {
/* 274 */       int previousValueOffset = buffer.getIntLE(offset + 15);
/* 275 */       if (previousValueOffset < 0) {
/* 276 */         return ValidationResult.error("Invalid offset for PreviousValue");
/*     */       }
/* 278 */       int pos = offset + 23 + previousValueOffset;
/* 279 */       if (pos >= buffer.writerIndex()) {
/* 280 */         return ValidationResult.error("Offset out of bounds for PreviousValue");
/*     */       }
/* 282 */       int previousValueLen = VarInt.peek(buffer, pos);
/* 283 */       if (previousValueLen < 0) {
/* 284 */         return ValidationResult.error("Invalid string length for PreviousValue");
/*     */       }
/* 286 */       if (previousValueLen > 4096000) {
/* 287 */         return ValidationResult.error("PreviousValue exceeds max length 4096000");
/*     */       }
/* 289 */       pos += VarInt.length(buffer, pos);
/* 290 */       pos += previousValueLen;
/* 291 */       if (pos > buffer.writerIndex()) {
/* 292 */         return ValidationResult.error("Buffer overflow reading PreviousValue");
/*     */       }
/*     */     } 
/*     */     
/* 296 */     if ((nullBits & 0x8) != 0) {
/* 297 */       int firstCreatedPropertyOffset = buffer.getIntLE(offset + 19);
/* 298 */       if (firstCreatedPropertyOffset < 0) {
/* 299 */         return ValidationResult.error("Invalid offset for FirstCreatedProperty");
/*     */       }
/* 301 */       int pos = offset + 23 + firstCreatedPropertyOffset;
/* 302 */       if (pos >= buffer.writerIndex()) {
/* 303 */         return ValidationResult.error("Offset out of bounds for FirstCreatedProperty");
/*     */       }
/* 305 */       int firstCreatedPropertyCount = VarInt.peek(buffer, pos);
/* 306 */       if (firstCreatedPropertyCount < 0) {
/* 307 */         return ValidationResult.error("Invalid array count for FirstCreatedProperty");
/*     */       }
/* 309 */       if (firstCreatedPropertyCount > 4096000) {
/* 310 */         return ValidationResult.error("FirstCreatedProperty exceeds max length 4096000");
/*     */       }
/* 312 */       pos += VarInt.length(buffer, pos);
/* 313 */       for (int i = 0; i < firstCreatedPropertyCount; i++) {
/* 314 */         int strLen = VarInt.peek(buffer, pos);
/* 315 */         if (strLen < 0) {
/* 316 */           return ValidationResult.error("Invalid string length in FirstCreatedProperty");
/*     */         }
/* 318 */         pos += VarInt.length(buffer, pos);
/* 319 */         pos += strLen;
/* 320 */         if (pos > buffer.writerIndex()) {
/* 321 */           return ValidationResult.error("Buffer overflow reading string in FirstCreatedProperty");
/*     */         }
/*     */       } 
/*     */     } 
/* 325 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public JsonUpdateCommand clone() {
/* 329 */     JsonUpdateCommand copy = new JsonUpdateCommand();
/* 330 */     copy.type = this.type;
/* 331 */     copy.path = (this.path != null) ? Arrays.<String>copyOf(this.path, this.path.length) : null;
/* 332 */     copy.value = this.value;
/* 333 */     copy.previousValue = this.previousValue;
/* 334 */     copy.firstCreatedProperty = (this.firstCreatedProperty != null) ? Arrays.<String>copyOf(this.firstCreatedProperty, this.firstCreatedProperty.length) : null;
/* 335 */     copy.rebuildCaches = (this.rebuildCaches != null) ? this.rebuildCaches.clone() : null;
/* 336 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     JsonUpdateCommand other;
/* 342 */     if (this == obj) return true; 
/* 343 */     if (obj instanceof JsonUpdateCommand) { other = (JsonUpdateCommand)obj; } else { return false; }
/* 344 */      return (Objects.equals(this.type, other.type) && Arrays.equals((Object[])this.path, (Object[])other.path) && Objects.equals(this.value, other.value) && Objects.equals(this.previousValue, other.previousValue) && Arrays.equals((Object[])this.firstCreatedProperty, (Object[])other.firstCreatedProperty) && Objects.equals(this.rebuildCaches, other.rebuildCaches));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 349 */     int result = 1;
/* 350 */     result = 31 * result + Objects.hashCode(this.type);
/* 351 */     result = 31 * result + Arrays.hashCode((Object[])this.path);
/* 352 */     result = 31 * result + Objects.hashCode(this.value);
/* 353 */     result = 31 * result + Objects.hashCode(this.previousValue);
/* 354 */     result = 31 * result + Arrays.hashCode((Object[])this.firstCreatedProperty);
/* 355 */     result = 31 * result + Objects.hashCode(this.rebuildCaches);
/* 356 */     return result;
/*     */   }
/*     */   
/*     */   public JsonUpdateCommand() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\JsonUpdateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */