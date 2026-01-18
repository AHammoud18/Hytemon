/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormattedMessage
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 34;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nonnull
/*  26 */   public MaybeBool bold = MaybeBool.Null; @Nullable public String rawText; @Nullable public String messageId; @Nullable public FormattedMessage[] children; @Nullable public Map<String, ParamValue> params; @Nullable public Map<String, FormattedMessage> messageParams; @Nullable public String color; @Nonnull
/*  27 */   public MaybeBool italic = MaybeBool.Null; @Nonnull
/*  28 */   public MaybeBool monospace = MaybeBool.Null; @Nonnull
/*  29 */   public MaybeBool underlined = MaybeBool.Null;
/*     */   
/*     */   @Nullable
/*     */   public String link;
/*     */   
/*     */   public boolean markupEnabled;
/*     */   
/*     */   public FormattedMessage(@Nullable String rawText, @Nullable String messageId, @Nullable FormattedMessage[] children, @Nullable Map<String, ParamValue> params, @Nullable Map<String, FormattedMessage> messageParams, @Nullable String color, @Nonnull MaybeBool bold, @Nonnull MaybeBool italic, @Nonnull MaybeBool monospace, @Nonnull MaybeBool underlined, @Nullable String link, boolean markupEnabled) {
/*  37 */     this.rawText = rawText;
/*  38 */     this.messageId = messageId;
/*  39 */     this.children = children;
/*  40 */     this.params = params;
/*  41 */     this.messageParams = messageParams;
/*  42 */     this.color = color;
/*  43 */     this.bold = bold;
/*  44 */     this.italic = italic;
/*  45 */     this.monospace = monospace;
/*  46 */     this.underlined = underlined;
/*  47 */     this.link = link;
/*  48 */     this.markupEnabled = markupEnabled;
/*     */   }
/*     */   
/*     */   public FormattedMessage(@Nonnull FormattedMessage other) {
/*  52 */     this.rawText = other.rawText;
/*  53 */     this.messageId = other.messageId;
/*  54 */     this.children = other.children;
/*  55 */     this.params = other.params;
/*  56 */     this.messageParams = other.messageParams;
/*  57 */     this.color = other.color;
/*  58 */     this.bold = other.bold;
/*  59 */     this.italic = other.italic;
/*  60 */     this.monospace = other.monospace;
/*  61 */     this.underlined = other.underlined;
/*  62 */     this.link = other.link;
/*  63 */     this.markupEnabled = other.markupEnabled;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static FormattedMessage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  68 */     FormattedMessage obj = new FormattedMessage();
/*  69 */     byte nullBits = buf.getByte(offset);
/*  70 */     obj.bold = MaybeBool.fromValue(buf.getByte(offset + 1));
/*  71 */     obj.italic = MaybeBool.fromValue(buf.getByte(offset + 2));
/*  72 */     obj.monospace = MaybeBool.fromValue(buf.getByte(offset + 3));
/*  73 */     obj.underlined = MaybeBool.fromValue(buf.getByte(offset + 4));
/*  74 */     obj.markupEnabled = (buf.getByte(offset + 5) != 0);
/*     */     
/*  76 */     if ((nullBits & 0x1) != 0) {
/*  77 */       int varPos0 = offset + 34 + buf.getIntLE(offset + 6);
/*  78 */       int rawTextLen = VarInt.peek(buf, varPos0);
/*  79 */       if (rawTextLen < 0) throw ProtocolException.negativeLength("RawText", rawTextLen); 
/*  80 */       if (rawTextLen > 4096000) throw ProtocolException.stringTooLong("RawText", rawTextLen, 4096000); 
/*  81 */       obj.rawText = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  83 */     if ((nullBits & 0x2) != 0) {
/*  84 */       int varPos1 = offset + 34 + buf.getIntLE(offset + 10);
/*  85 */       int messageIdLen = VarInt.peek(buf, varPos1);
/*  86 */       if (messageIdLen < 0) throw ProtocolException.negativeLength("MessageId", messageIdLen); 
/*  87 */       if (messageIdLen > 4096000) throw ProtocolException.stringTooLong("MessageId", messageIdLen, 4096000); 
/*  88 */       obj.messageId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  90 */     if ((nullBits & 0x4) != 0) {
/*  91 */       int varPos2 = offset + 34 + buf.getIntLE(offset + 14);
/*  92 */       int childrenCount = VarInt.peek(buf, varPos2);
/*  93 */       if (childrenCount < 0) throw ProtocolException.negativeLength("Children", childrenCount); 
/*  94 */       if (childrenCount > 4096000) throw ProtocolException.arrayTooLong("Children", childrenCount, 4096000); 
/*  95 */       int varIntLen = VarInt.length(buf, varPos2);
/*  96 */       if ((varPos2 + varIntLen) + childrenCount * 6L > buf.readableBytes())
/*  97 */         throw ProtocolException.bufferTooSmall("Children", varPos2 + varIntLen + childrenCount * 6, buf.readableBytes()); 
/*  98 */       obj.children = new FormattedMessage[childrenCount];
/*  99 */       int elemPos = varPos2 + varIntLen;
/* 100 */       for (int i = 0; i < childrenCount; i++) {
/* 101 */         obj.children[i] = deserialize(buf, elemPos);
/* 102 */         elemPos += computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 105 */     if ((nullBits & 0x8) != 0) {
/* 106 */       int varPos3 = offset + 34 + buf.getIntLE(offset + 18);
/* 107 */       int paramsCount = VarInt.peek(buf, varPos3);
/* 108 */       if (paramsCount < 0) throw ProtocolException.negativeLength("Params", paramsCount); 
/* 109 */       if (paramsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Params", paramsCount, 4096000); 
/* 110 */       int varIntLen = VarInt.length(buf, varPos3);
/* 111 */       obj.params = new HashMap<>(paramsCount);
/* 112 */       int dictPos = varPos3 + varIntLen;
/* 113 */       for (int i = 0; i < paramsCount; i++) {
/* 114 */         int keyLen = VarInt.peek(buf, dictPos);
/* 115 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 116 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 117 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 118 */         String key = PacketIO.readVarString(buf, dictPos);
/* 119 */         dictPos += keyVarLen + keyLen;
/* 120 */         ParamValue val = ParamValue.deserialize(buf, dictPos);
/* 121 */         dictPos += ParamValue.computeBytesConsumed(buf, dictPos);
/* 122 */         if (obj.params.put(key, val) != null)
/* 123 */           throw ProtocolException.duplicateKey("params", key); 
/*     */       } 
/*     */     } 
/* 126 */     if ((nullBits & 0x10) != 0) {
/* 127 */       int varPos4 = offset + 34 + buf.getIntLE(offset + 22);
/* 128 */       int messageParamsCount = VarInt.peek(buf, varPos4);
/* 129 */       if (messageParamsCount < 0) throw ProtocolException.negativeLength("MessageParams", messageParamsCount); 
/* 130 */       if (messageParamsCount > 4096000) throw ProtocolException.dictionaryTooLarge("MessageParams", messageParamsCount, 4096000); 
/* 131 */       int varIntLen = VarInt.length(buf, varPos4);
/* 132 */       obj.messageParams = new HashMap<>(messageParamsCount);
/* 133 */       int dictPos = varPos4 + varIntLen;
/* 134 */       for (int i = 0; i < messageParamsCount; i++) {
/* 135 */         int keyLen = VarInt.peek(buf, dictPos);
/* 136 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/* 137 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/* 138 */         int keyVarLen = VarInt.length(buf, dictPos);
/* 139 */         String key = PacketIO.readVarString(buf, dictPos);
/* 140 */         dictPos += keyVarLen + keyLen;
/* 141 */         FormattedMessage val = deserialize(buf, dictPos);
/* 142 */         dictPos += computeBytesConsumed(buf, dictPos);
/* 143 */         if (obj.messageParams.put(key, val) != null)
/* 144 */           throw ProtocolException.duplicateKey("messageParams", key); 
/*     */       } 
/*     */     } 
/* 147 */     if ((nullBits & 0x20) != 0) {
/* 148 */       int varPos5 = offset + 34 + buf.getIntLE(offset + 26);
/* 149 */       int colorLen = VarInt.peek(buf, varPos5);
/* 150 */       if (colorLen < 0) throw ProtocolException.negativeLength("Color", colorLen); 
/* 151 */       if (colorLen > 4096000) throw ProtocolException.stringTooLong("Color", colorLen, 4096000); 
/* 152 */       obj.color = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*     */     } 
/* 154 */     if ((nullBits & 0x40) != 0) {
/* 155 */       int varPos6 = offset + 34 + buf.getIntLE(offset + 30);
/* 156 */       int linkLen = VarInt.peek(buf, varPos6);
/* 157 */       if (linkLen < 0) throw ProtocolException.negativeLength("Link", linkLen); 
/* 158 */       if (linkLen > 4096000) throw ProtocolException.stringTooLong("Link", linkLen, 4096000); 
/* 159 */       obj.link = PacketIO.readVarString(buf, varPos6, PacketIO.UTF8);
/*     */     } 
/*     */     
/* 162 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 166 */     byte nullBits = buf.getByte(offset);
/* 167 */     int maxEnd = 34;
/* 168 */     if ((nullBits & 0x1) != 0) {
/* 169 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/* 170 */       int pos0 = offset + 34 + fieldOffset0;
/* 171 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 172 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 174 */     if ((nullBits & 0x2) != 0) {
/* 175 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/* 176 */       int pos1 = offset + 34 + fieldOffset1;
/* 177 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 178 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 180 */     if ((nullBits & 0x4) != 0) {
/* 181 */       int fieldOffset2 = buf.getIntLE(offset + 14);
/* 182 */       int pos2 = offset + 34 + fieldOffset2;
/* 183 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 184 */       for (int i = 0; i < arrLen; ) { pos2 += computeBytesConsumed(buf, pos2); i++; }
/* 185 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 187 */     if ((nullBits & 0x8) != 0) {
/* 188 */       int fieldOffset3 = buf.getIntLE(offset + 18);
/* 189 */       int pos3 = offset + 34 + fieldOffset3;
/* 190 */       int dictLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 191 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl; pos3 += ParamValue.computeBytesConsumed(buf, pos3); i++; }
/* 192 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 194 */     if ((nullBits & 0x10) != 0) {
/* 195 */       int fieldOffset4 = buf.getIntLE(offset + 22);
/* 196 */       int pos4 = offset + 34 + fieldOffset4;
/* 197 */       int dictLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4);
/* 198 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl; pos4 += computeBytesConsumed(buf, pos4); i++; }
/* 199 */        if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 201 */     if ((nullBits & 0x20) != 0) {
/* 202 */       int fieldOffset5 = buf.getIntLE(offset + 26);
/* 203 */       int pos5 = offset + 34 + fieldOffset5;
/* 204 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/* 205 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 207 */     if ((nullBits & 0x40) != 0) {
/* 208 */       int fieldOffset6 = buf.getIntLE(offset + 30);
/* 209 */       int pos6 = offset + 34 + fieldOffset6;
/* 210 */       int sl = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6) + sl;
/* 211 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 213 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 218 */     int startPos = buf.writerIndex();
/* 219 */     byte nullBits = 0;
/* 220 */     if (this.rawText != null) nullBits = (byte)(nullBits | 0x1); 
/* 221 */     if (this.messageId != null) nullBits = (byte)(nullBits | 0x2); 
/* 222 */     if (this.children != null) nullBits = (byte)(nullBits | 0x4); 
/* 223 */     if (this.params != null) nullBits = (byte)(nullBits | 0x8); 
/* 224 */     if (this.messageParams != null) nullBits = (byte)(nullBits | 0x10); 
/* 225 */     if (this.color != null) nullBits = (byte)(nullBits | 0x20); 
/* 226 */     if (this.link != null) nullBits = (byte)(nullBits | 0x40); 
/* 227 */     buf.writeByte(nullBits);
/*     */     
/* 229 */     buf.writeByte(this.bold.getValue());
/* 230 */     buf.writeByte(this.italic.getValue());
/* 231 */     buf.writeByte(this.monospace.getValue());
/* 232 */     buf.writeByte(this.underlined.getValue());
/* 233 */     buf.writeByte(this.markupEnabled ? 1 : 0);
/*     */     
/* 235 */     int rawTextOffsetSlot = buf.writerIndex();
/* 236 */     buf.writeIntLE(0);
/* 237 */     int messageIdOffsetSlot = buf.writerIndex();
/* 238 */     buf.writeIntLE(0);
/* 239 */     int childrenOffsetSlot = buf.writerIndex();
/* 240 */     buf.writeIntLE(0);
/* 241 */     int paramsOffsetSlot = buf.writerIndex();
/* 242 */     buf.writeIntLE(0);
/* 243 */     int messageParamsOffsetSlot = buf.writerIndex();
/* 244 */     buf.writeIntLE(0);
/* 245 */     int colorOffsetSlot = buf.writerIndex();
/* 246 */     buf.writeIntLE(0);
/* 247 */     int linkOffsetSlot = buf.writerIndex();
/* 248 */     buf.writeIntLE(0);
/*     */     
/* 250 */     int varBlockStart = buf.writerIndex();
/* 251 */     if (this.rawText != null) {
/* 252 */       buf.setIntLE(rawTextOffsetSlot, buf.writerIndex() - varBlockStart);
/* 253 */       PacketIO.writeVarString(buf, this.rawText, 4096000);
/*     */     } else {
/* 255 */       buf.setIntLE(rawTextOffsetSlot, -1);
/*     */     } 
/* 257 */     if (this.messageId != null) {
/* 258 */       buf.setIntLE(messageIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 259 */       PacketIO.writeVarString(buf, this.messageId, 4096000);
/*     */     } else {
/* 261 */       buf.setIntLE(messageIdOffsetSlot, -1);
/*     */     } 
/* 263 */     if (this.children != null) {
/* 264 */       buf.setIntLE(childrenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 265 */       if (this.children.length > 4096000) throw ProtocolException.arrayTooLong("Children", this.children.length, 4096000);  VarInt.write(buf, this.children.length); for (FormattedMessage item : this.children) item.serialize(buf); 
/*     */     } else {
/* 267 */       buf.setIntLE(childrenOffsetSlot, -1);
/*     */     } 
/* 269 */     if (this.params != null)
/* 270 */     { buf.setIntLE(paramsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 271 */       if (this.params.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Params", this.params.size(), 4096000);  VarInt.write(buf, this.params.size()); for (Map.Entry<String, ParamValue> e : this.params.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((ParamValue)e.getValue()).serializeWithTypeId(buf); }
/*     */        }
/* 273 */     else { buf.setIntLE(paramsOffsetSlot, -1); }
/*     */     
/* 275 */     if (this.messageParams != null)
/* 276 */     { buf.setIntLE(messageParamsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 277 */       if (this.messageParams.size() > 4096000) throw ProtocolException.dictionaryTooLarge("MessageParams", this.messageParams.size(), 4096000);  VarInt.write(buf, this.messageParams.size()); for (Map.Entry<String, FormattedMessage> e : this.messageParams.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((FormattedMessage)e.getValue()).serialize(buf); }
/*     */        }
/* 279 */     else { buf.setIntLE(messageParamsOffsetSlot, -1); }
/*     */     
/* 281 */     if (this.color != null) {
/* 282 */       buf.setIntLE(colorOffsetSlot, buf.writerIndex() - varBlockStart);
/* 283 */       PacketIO.writeVarString(buf, this.color, 4096000);
/*     */     } else {
/* 285 */       buf.setIntLE(colorOffsetSlot, -1);
/*     */     } 
/* 287 */     if (this.link != null) {
/* 288 */       buf.setIntLE(linkOffsetSlot, buf.writerIndex() - varBlockStart);
/* 289 */       PacketIO.writeVarString(buf, this.link, 4096000);
/*     */     } else {
/* 291 */       buf.setIntLE(linkOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 297 */     int size = 34;
/* 298 */     if (this.rawText != null) size += PacketIO.stringSize(this.rawText); 
/* 299 */     if (this.messageId != null) size += PacketIO.stringSize(this.messageId); 
/* 300 */     if (this.children != null) {
/* 301 */       int childrenSize = 0;
/* 302 */       for (FormattedMessage elem : this.children) childrenSize += elem.computeSize(); 
/* 303 */       size += VarInt.size(this.children.length) + childrenSize;
/*     */     } 
/* 305 */     if (this.params != null) {
/* 306 */       int paramsSize = 0;
/* 307 */       for (Map.Entry<String, ParamValue> kvp : this.params.entrySet()) paramsSize += PacketIO.stringSize(kvp.getKey()) + ((ParamValue)kvp.getValue()).computeSizeWithTypeId(); 
/* 308 */       size += VarInt.size(this.params.size()) + paramsSize;
/*     */     } 
/* 310 */     if (this.messageParams != null) {
/* 311 */       int messageParamsSize = 0;
/* 312 */       for (Map.Entry<String, FormattedMessage> kvp : this.messageParams.entrySet()) messageParamsSize += PacketIO.stringSize(kvp.getKey()) + ((FormattedMessage)kvp.getValue()).computeSize(); 
/* 313 */       size += VarInt.size(this.messageParams.size()) + messageParamsSize;
/*     */     } 
/* 315 */     if (this.color != null) size += PacketIO.stringSize(this.color); 
/* 316 */     if (this.link != null) size += PacketIO.stringSize(this.link);
/*     */     
/* 318 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 322 */     if (buffer.readableBytes() - offset < 34) {
/* 323 */       return ValidationResult.error("Buffer too small: expected at least 34 bytes");
/*     */     }
/*     */     
/* 326 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 329 */     if ((nullBits & 0x1) != 0) {
/* 330 */       int rawTextOffset = buffer.getIntLE(offset + 6);
/* 331 */       if (rawTextOffset < 0) {
/* 332 */         return ValidationResult.error("Invalid offset for RawText");
/*     */       }
/* 334 */       int pos = offset + 34 + rawTextOffset;
/* 335 */       if (pos >= buffer.writerIndex()) {
/* 336 */         return ValidationResult.error("Offset out of bounds for RawText");
/*     */       }
/* 338 */       int rawTextLen = VarInt.peek(buffer, pos);
/* 339 */       if (rawTextLen < 0) {
/* 340 */         return ValidationResult.error("Invalid string length for RawText");
/*     */       }
/* 342 */       if (rawTextLen > 4096000) {
/* 343 */         return ValidationResult.error("RawText exceeds max length 4096000");
/*     */       }
/* 345 */       pos += VarInt.length(buffer, pos);
/* 346 */       pos += rawTextLen;
/* 347 */       if (pos > buffer.writerIndex()) {
/* 348 */         return ValidationResult.error("Buffer overflow reading RawText");
/*     */       }
/*     */     } 
/*     */     
/* 352 */     if ((nullBits & 0x2) != 0) {
/* 353 */       int messageIdOffset = buffer.getIntLE(offset + 10);
/* 354 */       if (messageIdOffset < 0) {
/* 355 */         return ValidationResult.error("Invalid offset for MessageId");
/*     */       }
/* 357 */       int pos = offset + 34 + messageIdOffset;
/* 358 */       if (pos >= buffer.writerIndex()) {
/* 359 */         return ValidationResult.error("Offset out of bounds for MessageId");
/*     */       }
/* 361 */       int messageIdLen = VarInt.peek(buffer, pos);
/* 362 */       if (messageIdLen < 0) {
/* 363 */         return ValidationResult.error("Invalid string length for MessageId");
/*     */       }
/* 365 */       if (messageIdLen > 4096000) {
/* 366 */         return ValidationResult.error("MessageId exceeds max length 4096000");
/*     */       }
/* 368 */       pos += VarInt.length(buffer, pos);
/* 369 */       pos += messageIdLen;
/* 370 */       if (pos > buffer.writerIndex()) {
/* 371 */         return ValidationResult.error("Buffer overflow reading MessageId");
/*     */       }
/*     */     } 
/*     */     
/* 375 */     if ((nullBits & 0x4) != 0) {
/* 376 */       int childrenOffset = buffer.getIntLE(offset + 14);
/* 377 */       if (childrenOffset < 0) {
/* 378 */         return ValidationResult.error("Invalid offset for Children");
/*     */       }
/* 380 */       int pos = offset + 34 + childrenOffset;
/* 381 */       if (pos >= buffer.writerIndex()) {
/* 382 */         return ValidationResult.error("Offset out of bounds for Children");
/*     */       }
/* 384 */       int childrenCount = VarInt.peek(buffer, pos);
/* 385 */       if (childrenCount < 0) {
/* 386 */         return ValidationResult.error("Invalid array count for Children");
/*     */       }
/* 388 */       if (childrenCount > 4096000) {
/* 389 */         return ValidationResult.error("Children exceeds max length 4096000");
/*     */       }
/* 391 */       pos += VarInt.length(buffer, pos);
/* 392 */       for (int i = 0; i < childrenCount; i++) {
/* 393 */         ValidationResult structResult = validateStructure(buffer, pos);
/* 394 */         if (!structResult.isValid()) {
/* 395 */           return ValidationResult.error("Invalid FormattedMessage in Children[" + i + "]: " + structResult.error());
/*     */         }
/* 397 */         pos += computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 401 */     if ((nullBits & 0x8) != 0) {
/* 402 */       int paramsOffset = buffer.getIntLE(offset + 18);
/* 403 */       if (paramsOffset < 0) {
/* 404 */         return ValidationResult.error("Invalid offset for Params");
/*     */       }
/* 406 */       int pos = offset + 34 + paramsOffset;
/* 407 */       if (pos >= buffer.writerIndex()) {
/* 408 */         return ValidationResult.error("Offset out of bounds for Params");
/*     */       }
/* 410 */       int paramsCount = VarInt.peek(buffer, pos);
/* 411 */       if (paramsCount < 0) {
/* 412 */         return ValidationResult.error("Invalid dictionary count for Params");
/*     */       }
/* 414 */       if (paramsCount > 4096000) {
/* 415 */         return ValidationResult.error("Params exceeds max length 4096000");
/*     */       }
/* 417 */       pos += VarInt.length(buffer, pos);
/* 418 */       for (int i = 0; i < paramsCount; i++) {
/* 419 */         int keyLen = VarInt.peek(buffer, pos);
/* 420 */         if (keyLen < 0) {
/* 421 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 423 */         if (keyLen > 4096000) {
/* 424 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 426 */         pos += VarInt.length(buffer, pos);
/* 427 */         pos += keyLen;
/* 428 */         if (pos > buffer.writerIndex()) {
/* 429 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 431 */         pos += ParamValue.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 436 */     if ((nullBits & 0x10) != 0) {
/* 437 */       int messageParamsOffset = buffer.getIntLE(offset + 22);
/* 438 */       if (messageParamsOffset < 0) {
/* 439 */         return ValidationResult.error("Invalid offset for MessageParams");
/*     */       }
/* 441 */       int pos = offset + 34 + messageParamsOffset;
/* 442 */       if (pos >= buffer.writerIndex()) {
/* 443 */         return ValidationResult.error("Offset out of bounds for MessageParams");
/*     */       }
/* 445 */       int messageParamsCount = VarInt.peek(buffer, pos);
/* 446 */       if (messageParamsCount < 0) {
/* 447 */         return ValidationResult.error("Invalid dictionary count for MessageParams");
/*     */       }
/* 449 */       if (messageParamsCount > 4096000) {
/* 450 */         return ValidationResult.error("MessageParams exceeds max length 4096000");
/*     */       }
/* 452 */       pos += VarInt.length(buffer, pos);
/* 453 */       for (int i = 0; i < messageParamsCount; i++) {
/* 454 */         int keyLen = VarInt.peek(buffer, pos);
/* 455 */         if (keyLen < 0) {
/* 456 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 458 */         if (keyLen > 4096000) {
/* 459 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 461 */         pos += VarInt.length(buffer, pos);
/* 462 */         pos += keyLen;
/* 463 */         if (pos > buffer.writerIndex()) {
/* 464 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 466 */         pos += computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 471 */     if ((nullBits & 0x20) != 0) {
/* 472 */       int colorOffset = buffer.getIntLE(offset + 26);
/* 473 */       if (colorOffset < 0) {
/* 474 */         return ValidationResult.error("Invalid offset for Color");
/*     */       }
/* 476 */       int pos = offset + 34 + colorOffset;
/* 477 */       if (pos >= buffer.writerIndex()) {
/* 478 */         return ValidationResult.error("Offset out of bounds for Color");
/*     */       }
/* 480 */       int colorLen = VarInt.peek(buffer, pos);
/* 481 */       if (colorLen < 0) {
/* 482 */         return ValidationResult.error("Invalid string length for Color");
/*     */       }
/* 484 */       if (colorLen > 4096000) {
/* 485 */         return ValidationResult.error("Color exceeds max length 4096000");
/*     */       }
/* 487 */       pos += VarInt.length(buffer, pos);
/* 488 */       pos += colorLen;
/* 489 */       if (pos > buffer.writerIndex()) {
/* 490 */         return ValidationResult.error("Buffer overflow reading Color");
/*     */       }
/*     */     } 
/*     */     
/* 494 */     if ((nullBits & 0x40) != 0) {
/* 495 */       int linkOffset = buffer.getIntLE(offset + 30);
/* 496 */       if (linkOffset < 0) {
/* 497 */         return ValidationResult.error("Invalid offset for Link");
/*     */       }
/* 499 */       int pos = offset + 34 + linkOffset;
/* 500 */       if (pos >= buffer.writerIndex()) {
/* 501 */         return ValidationResult.error("Offset out of bounds for Link");
/*     */       }
/* 503 */       int linkLen = VarInt.peek(buffer, pos);
/* 504 */       if (linkLen < 0) {
/* 505 */         return ValidationResult.error("Invalid string length for Link");
/*     */       }
/* 507 */       if (linkLen > 4096000) {
/* 508 */         return ValidationResult.error("Link exceeds max length 4096000");
/*     */       }
/* 510 */       pos += VarInt.length(buffer, pos);
/* 511 */       pos += linkLen;
/* 512 */       if (pos > buffer.writerIndex()) {
/* 513 */         return ValidationResult.error("Buffer overflow reading Link");
/*     */       }
/*     */     } 
/* 516 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public FormattedMessage clone() {
/* 520 */     FormattedMessage copy = new FormattedMessage();
/* 521 */     copy.rawText = this.rawText;
/* 522 */     copy.messageId = this.messageId;
/* 523 */     copy.children = (this.children != null) ? (FormattedMessage[])Arrays.<FormattedMessage>stream(this.children).map(e -> e.clone()).toArray(x$0 -> new FormattedMessage[x$0]) : null;
/* 524 */     copy.params = (this.params != null) ? new HashMap<>(this.params) : null;
/* 525 */     if (this.messageParams != null) {
/* 526 */       Map<String, FormattedMessage> m = new HashMap<>();
/* 527 */       for (Map.Entry<String, FormattedMessage> e : this.messageParams.entrySet()) m.put(e.getKey(), ((FormattedMessage)e.getValue()).clone()); 
/* 528 */       copy.messageParams = m;
/*     */     } 
/* 530 */     copy.color = this.color;
/* 531 */     copy.bold = this.bold;
/* 532 */     copy.italic = this.italic;
/* 533 */     copy.monospace = this.monospace;
/* 534 */     copy.underlined = this.underlined;
/* 535 */     copy.link = this.link;
/* 536 */     copy.markupEnabled = this.markupEnabled;
/* 537 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     FormattedMessage other;
/* 543 */     if (this == obj) return true; 
/* 544 */     if (obj instanceof FormattedMessage) { other = (FormattedMessage)obj; } else { return false; }
/* 545 */      return (Objects.equals(this.rawText, other.rawText) && Objects.equals(this.messageId, other.messageId) && Arrays.equals((Object[])this.children, (Object[])other.children) && Objects.equals(this.params, other.params) && Objects.equals(this.messageParams, other.messageParams) && Objects.equals(this.color, other.color) && Objects.equals(this.bold, other.bold) && Objects.equals(this.italic, other.italic) && Objects.equals(this.monospace, other.monospace) && Objects.equals(this.underlined, other.underlined) && Objects.equals(this.link, other.link) && this.markupEnabled == other.markupEnabled);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 550 */     int result = 1;
/* 551 */     result = 31 * result + Objects.hashCode(this.rawText);
/* 552 */     result = 31 * result + Objects.hashCode(this.messageId);
/* 553 */     result = 31 * result + Arrays.hashCode((Object[])this.children);
/* 554 */     result = 31 * result + Objects.hashCode(this.params);
/* 555 */     result = 31 * result + Objects.hashCode(this.messageParams);
/* 556 */     result = 31 * result + Objects.hashCode(this.color);
/* 557 */     result = 31 * result + Objects.hashCode(this.bold);
/* 558 */     result = 31 * result + Objects.hashCode(this.italic);
/* 559 */     result = 31 * result + Objects.hashCode(this.monospace);
/* 560 */     result = 31 * result + Objects.hashCode(this.underlined);
/* 561 */     result = 31 * result + Objects.hashCode(this.link);
/* 562 */     result = 31 * result + Boolean.hashCode(this.markupEnabled);
/* 563 */     return result;
/*     */   }
/*     */   
/*     */   public FormattedMessage() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\FormattedMessage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */