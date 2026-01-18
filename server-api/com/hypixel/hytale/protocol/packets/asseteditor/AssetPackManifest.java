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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetPackManifest
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 6;
/*     */   public static final int VARIABLE_BLOCK_START = 25;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String name;
/*     */   
/*     */   public AssetPackManifest(@Nullable String name, @Nullable String group, @Nullable String website, @Nullable String description, @Nullable String version, @Nullable AuthorInfo[] authors) {
/*  31 */     this.name = name;
/*  32 */     this.group = group;
/*  33 */     this.website = website;
/*  34 */     this.description = description;
/*  35 */     this.version = version;
/*  36 */     this.authors = authors; } @Nullable public String group; @Nullable
/*     */   public String website; @Nullable
/*     */   public String description; @Nullable
/*     */   public String version; @Nullable
/*  40 */   public AuthorInfo[] authors; public AssetPackManifest() {} public AssetPackManifest(@Nonnull AssetPackManifest other) { this.name = other.name;
/*  41 */     this.group = other.group;
/*  42 */     this.website = other.website;
/*  43 */     this.description = other.description;
/*  44 */     this.version = other.version;
/*  45 */     this.authors = other.authors; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetPackManifest deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     AssetPackManifest obj = new AssetPackManifest();
/*  51 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  53 */     if ((nullBits & 0x1) != 0) {
/*  54 */       int varPos0 = offset + 25 + buf.getIntLE(offset + 1);
/*  55 */       int nameLen = VarInt.peek(buf, varPos0);
/*  56 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  57 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  58 */       obj.name = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  60 */     if ((nullBits & 0x2) != 0) {
/*  61 */       int varPos1 = offset + 25 + buf.getIntLE(offset + 5);
/*  62 */       int groupLen = VarInt.peek(buf, varPos1);
/*  63 */       if (groupLen < 0) throw ProtocolException.negativeLength("Group", groupLen); 
/*  64 */       if (groupLen > 4096000) throw ProtocolException.stringTooLong("Group", groupLen, 4096000); 
/*  65 */       obj.group = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  67 */     if ((nullBits & 0x4) != 0) {
/*  68 */       int varPos2 = offset + 25 + buf.getIntLE(offset + 9);
/*  69 */       int websiteLen = VarInt.peek(buf, varPos2);
/*  70 */       if (websiteLen < 0) throw ProtocolException.negativeLength("Website", websiteLen); 
/*  71 */       if (websiteLen > 4096000) throw ProtocolException.stringTooLong("Website", websiteLen, 4096000); 
/*  72 */       obj.website = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  74 */     if ((nullBits & 0x8) != 0) {
/*  75 */       int varPos3 = offset + 25 + buf.getIntLE(offset + 13);
/*  76 */       int descriptionLen = VarInt.peek(buf, varPos3);
/*  77 */       if (descriptionLen < 0) throw ProtocolException.negativeLength("Description", descriptionLen); 
/*  78 */       if (descriptionLen > 4096000) throw ProtocolException.stringTooLong("Description", descriptionLen, 4096000); 
/*  79 */       obj.description = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*     */     } 
/*  81 */     if ((nullBits & 0x10) != 0) {
/*  82 */       int varPos4 = offset + 25 + buf.getIntLE(offset + 17);
/*  83 */       int versionLen = VarInt.peek(buf, varPos4);
/*  84 */       if (versionLen < 0) throw ProtocolException.negativeLength("Version", versionLen); 
/*  85 */       if (versionLen > 4096000) throw ProtocolException.stringTooLong("Version", versionLen, 4096000); 
/*  86 */       obj.version = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*     */     } 
/*  88 */     if ((nullBits & 0x20) != 0) {
/*  89 */       int varPos5 = offset + 25 + buf.getIntLE(offset + 21);
/*  90 */       int authorsCount = VarInt.peek(buf, varPos5);
/*  91 */       if (authorsCount < 0) throw ProtocolException.negativeLength("Authors", authorsCount); 
/*  92 */       if (authorsCount > 4096000) throw ProtocolException.arrayTooLong("Authors", authorsCount, 4096000); 
/*  93 */       int varIntLen = VarInt.length(buf, varPos5);
/*  94 */       if ((varPos5 + varIntLen) + authorsCount * 1L > buf.readableBytes())
/*  95 */         throw ProtocolException.bufferTooSmall("Authors", varPos5 + varIntLen + authorsCount * 1, buf.readableBytes()); 
/*  96 */       obj.authors = new AuthorInfo[authorsCount];
/*  97 */       int elemPos = varPos5 + varIntLen;
/*  98 */       for (int i = 0; i < authorsCount; i++) {
/*  99 */         obj.authors[i] = AuthorInfo.deserialize(buf, elemPos);
/* 100 */         elemPos += AuthorInfo.computeBytesConsumed(buf, elemPos);
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
/* 111 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/* 112 */       int pos0 = offset + 25 + fieldOffset0;
/* 113 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 114 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 116 */     if ((nullBits & 0x2) != 0) {
/* 117 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 118 */       int pos1 = offset + 25 + fieldOffset1;
/* 119 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 120 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x4) != 0) {
/* 123 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 124 */       int pos2 = offset + 25 + fieldOffset2;
/* 125 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 126 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 128 */     if ((nullBits & 0x8) != 0) {
/* 129 */       int fieldOffset3 = buf.getIntLE(offset + 13);
/* 130 */       int pos3 = offset + 25 + fieldOffset3;
/* 131 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/* 132 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 134 */     if ((nullBits & 0x10) != 0) {
/* 135 */       int fieldOffset4 = buf.getIntLE(offset + 17);
/* 136 */       int pos4 = offset + 25 + fieldOffset4;
/* 137 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/* 138 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 140 */     if ((nullBits & 0x20) != 0) {
/* 141 */       int fieldOffset5 = buf.getIntLE(offset + 21);
/* 142 */       int pos5 = offset + 25 + fieldOffset5;
/* 143 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 144 */       for (int i = 0; i < arrLen; ) { pos5 += AuthorInfo.computeBytesConsumed(buf, pos5); i++; }
/* 145 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 147 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 152 */     int startPos = buf.writerIndex();
/* 153 */     byte nullBits = 0;
/* 154 */     if (this.name != null) nullBits = (byte)(nullBits | 0x1); 
/* 155 */     if (this.group != null) nullBits = (byte)(nullBits | 0x2); 
/* 156 */     if (this.website != null) nullBits = (byte)(nullBits | 0x4); 
/* 157 */     if (this.description != null) nullBits = (byte)(nullBits | 0x8); 
/* 158 */     if (this.version != null) nullBits = (byte)(nullBits | 0x10); 
/* 159 */     if (this.authors != null) nullBits = (byte)(nullBits | 0x20); 
/* 160 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 163 */     int nameOffsetSlot = buf.writerIndex();
/* 164 */     buf.writeIntLE(0);
/* 165 */     int groupOffsetSlot = buf.writerIndex();
/* 166 */     buf.writeIntLE(0);
/* 167 */     int websiteOffsetSlot = buf.writerIndex();
/* 168 */     buf.writeIntLE(0);
/* 169 */     int descriptionOffsetSlot = buf.writerIndex();
/* 170 */     buf.writeIntLE(0);
/* 171 */     int versionOffsetSlot = buf.writerIndex();
/* 172 */     buf.writeIntLE(0);
/* 173 */     int authorsOffsetSlot = buf.writerIndex();
/* 174 */     buf.writeIntLE(0);
/*     */     
/* 176 */     int varBlockStart = buf.writerIndex();
/* 177 */     if (this.name != null) {
/* 178 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 179 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 181 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 183 */     if (this.group != null) {
/* 184 */       buf.setIntLE(groupOffsetSlot, buf.writerIndex() - varBlockStart);
/* 185 */       PacketIO.writeVarString(buf, this.group, 4096000);
/*     */     } else {
/* 187 */       buf.setIntLE(groupOffsetSlot, -1);
/*     */     } 
/* 189 */     if (this.website != null) {
/* 190 */       buf.setIntLE(websiteOffsetSlot, buf.writerIndex() - varBlockStart);
/* 191 */       PacketIO.writeVarString(buf, this.website, 4096000);
/*     */     } else {
/* 193 */       buf.setIntLE(websiteOffsetSlot, -1);
/*     */     } 
/* 195 */     if (this.description != null) {
/* 196 */       buf.setIntLE(descriptionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 197 */       PacketIO.writeVarString(buf, this.description, 4096000);
/*     */     } else {
/* 199 */       buf.setIntLE(descriptionOffsetSlot, -1);
/*     */     } 
/* 201 */     if (this.version != null) {
/* 202 */       buf.setIntLE(versionOffsetSlot, buf.writerIndex() - varBlockStart);
/* 203 */       PacketIO.writeVarString(buf, this.version, 4096000);
/*     */     } else {
/* 205 */       buf.setIntLE(versionOffsetSlot, -1);
/*     */     } 
/* 207 */     if (this.authors != null) {
/* 208 */       buf.setIntLE(authorsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 209 */       if (this.authors.length > 4096000) throw ProtocolException.arrayTooLong("Authors", this.authors.length, 4096000);  VarInt.write(buf, this.authors.length); for (AuthorInfo item : this.authors) item.serialize(buf); 
/*     */     } else {
/* 211 */       buf.setIntLE(authorsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 217 */     int size = 25;
/* 218 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 219 */     if (this.group != null) size += PacketIO.stringSize(this.group); 
/* 220 */     if (this.website != null) size += PacketIO.stringSize(this.website); 
/* 221 */     if (this.description != null) size += PacketIO.stringSize(this.description); 
/* 222 */     if (this.version != null) size += PacketIO.stringSize(this.version); 
/* 223 */     if (this.authors != null) {
/* 224 */       int authorsSize = 0;
/* 225 */       for (AuthorInfo elem : this.authors) authorsSize += elem.computeSize(); 
/* 226 */       size += VarInt.size(this.authors.length) + authorsSize;
/*     */     } 
/*     */     
/* 229 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 233 */     if (buffer.readableBytes() - offset < 25) {
/* 234 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*     */     }
/*     */     
/* 237 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 240 */     if ((nullBits & 0x1) != 0) {
/* 241 */       int nameOffset = buffer.getIntLE(offset + 1);
/* 242 */       if (nameOffset < 0) {
/* 243 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 245 */       int pos = offset + 25 + nameOffset;
/* 246 */       if (pos >= buffer.writerIndex()) {
/* 247 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 249 */       int nameLen = VarInt.peek(buffer, pos);
/* 250 */       if (nameLen < 0) {
/* 251 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 253 */       if (nameLen > 4096000) {
/* 254 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 256 */       pos += VarInt.length(buffer, pos);
/* 257 */       pos += nameLen;
/* 258 */       if (pos > buffer.writerIndex()) {
/* 259 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 263 */     if ((nullBits & 0x2) != 0) {
/* 264 */       int groupOffset = buffer.getIntLE(offset + 5);
/* 265 */       if (groupOffset < 0) {
/* 266 */         return ValidationResult.error("Invalid offset for Group");
/*     */       }
/* 268 */       int pos = offset + 25 + groupOffset;
/* 269 */       if (pos >= buffer.writerIndex()) {
/* 270 */         return ValidationResult.error("Offset out of bounds for Group");
/*     */       }
/* 272 */       int groupLen = VarInt.peek(buffer, pos);
/* 273 */       if (groupLen < 0) {
/* 274 */         return ValidationResult.error("Invalid string length for Group");
/*     */       }
/* 276 */       if (groupLen > 4096000) {
/* 277 */         return ValidationResult.error("Group exceeds max length 4096000");
/*     */       }
/* 279 */       pos += VarInt.length(buffer, pos);
/* 280 */       pos += groupLen;
/* 281 */       if (pos > buffer.writerIndex()) {
/* 282 */         return ValidationResult.error("Buffer overflow reading Group");
/*     */       }
/*     */     } 
/*     */     
/* 286 */     if ((nullBits & 0x4) != 0) {
/* 287 */       int websiteOffset = buffer.getIntLE(offset + 9);
/* 288 */       if (websiteOffset < 0) {
/* 289 */         return ValidationResult.error("Invalid offset for Website");
/*     */       }
/* 291 */       int pos = offset + 25 + websiteOffset;
/* 292 */       if (pos >= buffer.writerIndex()) {
/* 293 */         return ValidationResult.error("Offset out of bounds for Website");
/*     */       }
/* 295 */       int websiteLen = VarInt.peek(buffer, pos);
/* 296 */       if (websiteLen < 0) {
/* 297 */         return ValidationResult.error("Invalid string length for Website");
/*     */       }
/* 299 */       if (websiteLen > 4096000) {
/* 300 */         return ValidationResult.error("Website exceeds max length 4096000");
/*     */       }
/* 302 */       pos += VarInt.length(buffer, pos);
/* 303 */       pos += websiteLen;
/* 304 */       if (pos > buffer.writerIndex()) {
/* 305 */         return ValidationResult.error("Buffer overflow reading Website");
/*     */       }
/*     */     } 
/*     */     
/* 309 */     if ((nullBits & 0x8) != 0) {
/* 310 */       int descriptionOffset = buffer.getIntLE(offset + 13);
/* 311 */       if (descriptionOffset < 0) {
/* 312 */         return ValidationResult.error("Invalid offset for Description");
/*     */       }
/* 314 */       int pos = offset + 25 + descriptionOffset;
/* 315 */       if (pos >= buffer.writerIndex()) {
/* 316 */         return ValidationResult.error("Offset out of bounds for Description");
/*     */       }
/* 318 */       int descriptionLen = VarInt.peek(buffer, pos);
/* 319 */       if (descriptionLen < 0) {
/* 320 */         return ValidationResult.error("Invalid string length for Description");
/*     */       }
/* 322 */       if (descriptionLen > 4096000) {
/* 323 */         return ValidationResult.error("Description exceeds max length 4096000");
/*     */       }
/* 325 */       pos += VarInt.length(buffer, pos);
/* 326 */       pos += descriptionLen;
/* 327 */       if (pos > buffer.writerIndex()) {
/* 328 */         return ValidationResult.error("Buffer overflow reading Description");
/*     */       }
/*     */     } 
/*     */     
/* 332 */     if ((nullBits & 0x10) != 0) {
/* 333 */       int versionOffset = buffer.getIntLE(offset + 17);
/* 334 */       if (versionOffset < 0) {
/* 335 */         return ValidationResult.error("Invalid offset for Version");
/*     */       }
/* 337 */       int pos = offset + 25 + versionOffset;
/* 338 */       if (pos >= buffer.writerIndex()) {
/* 339 */         return ValidationResult.error("Offset out of bounds for Version");
/*     */       }
/* 341 */       int versionLen = VarInt.peek(buffer, pos);
/* 342 */       if (versionLen < 0) {
/* 343 */         return ValidationResult.error("Invalid string length for Version");
/*     */       }
/* 345 */       if (versionLen > 4096000) {
/* 346 */         return ValidationResult.error("Version exceeds max length 4096000");
/*     */       }
/* 348 */       pos += VarInt.length(buffer, pos);
/* 349 */       pos += versionLen;
/* 350 */       if (pos > buffer.writerIndex()) {
/* 351 */         return ValidationResult.error("Buffer overflow reading Version");
/*     */       }
/*     */     } 
/*     */     
/* 355 */     if ((nullBits & 0x20) != 0) {
/* 356 */       int authorsOffset = buffer.getIntLE(offset + 21);
/* 357 */       if (authorsOffset < 0) {
/* 358 */         return ValidationResult.error("Invalid offset for Authors");
/*     */       }
/* 360 */       int pos = offset + 25 + authorsOffset;
/* 361 */       if (pos >= buffer.writerIndex()) {
/* 362 */         return ValidationResult.error("Offset out of bounds for Authors");
/*     */       }
/* 364 */       int authorsCount = VarInt.peek(buffer, pos);
/* 365 */       if (authorsCount < 0) {
/* 366 */         return ValidationResult.error("Invalid array count for Authors");
/*     */       }
/* 368 */       if (authorsCount > 4096000) {
/* 369 */         return ValidationResult.error("Authors exceeds max length 4096000");
/*     */       }
/* 371 */       pos += VarInt.length(buffer, pos);
/* 372 */       for (int i = 0; i < authorsCount; i++) {
/* 373 */         ValidationResult structResult = AuthorInfo.validateStructure(buffer, pos);
/* 374 */         if (!structResult.isValid()) {
/* 375 */           return ValidationResult.error("Invalid AuthorInfo in Authors[" + i + "]: " + structResult.error());
/*     */         }
/* 377 */         pos += AuthorInfo.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 380 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetPackManifest clone() {
/* 384 */     AssetPackManifest copy = new AssetPackManifest();
/* 385 */     copy.name = this.name;
/* 386 */     copy.group = this.group;
/* 387 */     copy.website = this.website;
/* 388 */     copy.description = this.description;
/* 389 */     copy.version = this.version;
/* 390 */     copy.authors = (this.authors != null) ? (AuthorInfo[])Arrays.<AuthorInfo>stream(this.authors).map(e -> e.clone()).toArray(x$0 -> new AuthorInfo[x$0]) : null;
/* 391 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetPackManifest other;
/* 397 */     if (this == obj) return true; 
/* 398 */     if (obj instanceof AssetPackManifest) { other = (AssetPackManifest)obj; } else { return false; }
/* 399 */      return (Objects.equals(this.name, other.name) && Objects.equals(this.group, other.group) && Objects.equals(this.website, other.website) && Objects.equals(this.description, other.description) && Objects.equals(this.version, other.version) && Arrays.equals((Object[])this.authors, (Object[])other.authors));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 404 */     int result = 1;
/* 405 */     result = 31 * result + Objects.hashCode(this.name);
/* 406 */     result = 31 * result + Objects.hashCode(this.group);
/* 407 */     result = 31 * result + Objects.hashCode(this.website);
/* 408 */     result = 31 * result + Objects.hashCode(this.description);
/* 409 */     result = 31 * result + Objects.hashCode(this.version);
/* 410 */     result = 31 * result + Arrays.hashCode((Object[])this.authors);
/* 411 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetPackManifest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */