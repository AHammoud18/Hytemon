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
/*     */ public class ItemCategory
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 22;
/*     */   @Nonnull
/*  24 */   public ItemGridInfoDisplayMode infoDisplayMode = ItemGridInfoDisplayMode.Tooltip; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public String id; @Nullable
/*     */   public String name; @Nullable
/*     */   public String icon; public int order; @Nullable
/*     */   public ItemCategory[] children;
/*     */   
/*     */   public ItemCategory(@Nullable String id, @Nullable String name, @Nullable String icon, int order, @Nonnull ItemGridInfoDisplayMode infoDisplayMode, @Nullable ItemCategory[] children) {
/*  31 */     this.id = id;
/*  32 */     this.name = name;
/*  33 */     this.icon = icon;
/*  34 */     this.order = order;
/*  35 */     this.infoDisplayMode = infoDisplayMode;
/*  36 */     this.children = children;
/*     */   }
/*     */   
/*     */   public ItemCategory(@Nonnull ItemCategory other) {
/*  40 */     this.id = other.id;
/*  41 */     this.name = other.name;
/*  42 */     this.icon = other.icon;
/*  43 */     this.order = other.order;
/*  44 */     this.infoDisplayMode = other.infoDisplayMode;
/*  45 */     this.children = other.children;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemCategory deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ItemCategory obj = new ItemCategory();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.order = buf.getIntLE(offset + 1);
/*  53 */     obj.infoDisplayMode = ItemGridInfoDisplayMode.fromValue(buf.getByte(offset + 5));
/*     */     
/*  55 */     if ((nullBits & 0x1) != 0) {
/*  56 */       int varPos0 = offset + 22 + buf.getIntLE(offset + 6);
/*  57 */       int idLen = VarInt.peek(buf, varPos0);
/*  58 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  59 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  60 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  62 */     if ((nullBits & 0x2) != 0) {
/*  63 */       int varPos1 = offset + 22 + buf.getIntLE(offset + 10);
/*  64 */       int nameLen = VarInt.peek(buf, varPos1);
/*  65 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  66 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  67 */       obj.name = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  69 */     if ((nullBits & 0x4) != 0) {
/*  70 */       int varPos2 = offset + 22 + buf.getIntLE(offset + 14);
/*  71 */       int iconLen = VarInt.peek(buf, varPos2);
/*  72 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  73 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  74 */       obj.icon = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  76 */     if ((nullBits & 0x8) != 0) {
/*  77 */       int varPos3 = offset + 22 + buf.getIntLE(offset + 18);
/*  78 */       int childrenCount = VarInt.peek(buf, varPos3);
/*  79 */       if (childrenCount < 0) throw ProtocolException.negativeLength("Children", childrenCount); 
/*  80 */       if (childrenCount > 4096000) throw ProtocolException.arrayTooLong("Children", childrenCount, 4096000); 
/*  81 */       int varIntLen = VarInt.length(buf, varPos3);
/*  82 */       if ((varPos3 + varIntLen) + childrenCount * 6L > buf.readableBytes())
/*  83 */         throw ProtocolException.bufferTooSmall("Children", varPos3 + varIntLen + childrenCount * 6, buf.readableBytes()); 
/*  84 */       obj.children = new ItemCategory[childrenCount];
/*  85 */       int elemPos = varPos3 + varIntLen;
/*  86 */       for (int i = 0; i < childrenCount; i++) {
/*  87 */         obj.children[i] = deserialize(buf, elemPos);
/*  88 */         elemPos += computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  96 */     byte nullBits = buf.getByte(offset);
/*  97 */     int maxEnd = 22;
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/* 100 */       int pos0 = offset + 22 + fieldOffset0;
/* 101 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 102 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 104 */     if ((nullBits & 0x2) != 0) {
/* 105 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/* 106 */       int pos1 = offset + 22 + fieldOffset1;
/* 107 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 108 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 110 */     if ((nullBits & 0x4) != 0) {
/* 111 */       int fieldOffset2 = buf.getIntLE(offset + 14);
/* 112 */       int pos2 = offset + 22 + fieldOffset2;
/* 113 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 114 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 116 */     if ((nullBits & 0x8) != 0) {
/* 117 */       int fieldOffset3 = buf.getIntLE(offset + 18);
/* 118 */       int pos3 = offset + 22 + fieldOffset3;
/* 119 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 120 */       for (int i = 0; i < arrLen; ) { pos3 += computeBytesConsumed(buf, pos3); i++; }
/* 121 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 123 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 128 */     int startPos = buf.writerIndex();
/* 129 */     byte nullBits = 0;
/* 130 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 131 */     if (this.name != null) nullBits = (byte)(nullBits | 0x2); 
/* 132 */     if (this.icon != null) nullBits = (byte)(nullBits | 0x4); 
/* 133 */     if (this.children != null) nullBits = (byte)(nullBits | 0x8); 
/* 134 */     buf.writeByte(nullBits);
/*     */     
/* 136 */     buf.writeIntLE(this.order);
/* 137 */     buf.writeByte(this.infoDisplayMode.getValue());
/*     */     
/* 139 */     int idOffsetSlot = buf.writerIndex();
/* 140 */     buf.writeIntLE(0);
/* 141 */     int nameOffsetSlot = buf.writerIndex();
/* 142 */     buf.writeIntLE(0);
/* 143 */     int iconOffsetSlot = buf.writerIndex();
/* 144 */     buf.writeIntLE(0);
/* 145 */     int childrenOffsetSlot = buf.writerIndex();
/* 146 */     buf.writeIntLE(0);
/*     */     
/* 148 */     int varBlockStart = buf.writerIndex();
/* 149 */     if (this.id != null) {
/* 150 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 151 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 153 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 155 */     if (this.name != null) {
/* 156 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 157 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 159 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 161 */     if (this.icon != null) {
/* 162 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/* 163 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*     */     } else {
/* 165 */       buf.setIntLE(iconOffsetSlot, -1);
/*     */     } 
/* 167 */     if (this.children != null) {
/* 168 */       buf.setIntLE(childrenOffsetSlot, buf.writerIndex() - varBlockStart);
/* 169 */       if (this.children.length > 4096000) throw ProtocolException.arrayTooLong("Children", this.children.length, 4096000);  VarInt.write(buf, this.children.length); for (ItemCategory item : this.children) item.serialize(buf); 
/*     */     } else {
/* 171 */       buf.setIntLE(childrenOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 177 */     int size = 22;
/* 178 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 179 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 180 */     if (this.icon != null) size += PacketIO.stringSize(this.icon); 
/* 181 */     if (this.children != null) {
/* 182 */       int childrenSize = 0;
/* 183 */       for (ItemCategory elem : this.children) childrenSize += elem.computeSize(); 
/* 184 */       size += VarInt.size(this.children.length) + childrenSize;
/*     */     } 
/*     */     
/* 187 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 191 */     if (buffer.readableBytes() - offset < 22) {
/* 192 */       return ValidationResult.error("Buffer too small: expected at least 22 bytes");
/*     */     }
/*     */     
/* 195 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 198 */     if ((nullBits & 0x1) != 0) {
/* 199 */       int idOffset = buffer.getIntLE(offset + 6);
/* 200 */       if (idOffset < 0) {
/* 201 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 203 */       int pos = offset + 22 + idOffset;
/* 204 */       if (pos >= buffer.writerIndex()) {
/* 205 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 207 */       int idLen = VarInt.peek(buffer, pos);
/* 208 */       if (idLen < 0) {
/* 209 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 211 */       if (idLen > 4096000) {
/* 212 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 214 */       pos += VarInt.length(buffer, pos);
/* 215 */       pos += idLen;
/* 216 */       if (pos > buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 221 */     if ((nullBits & 0x2) != 0) {
/* 222 */       int nameOffset = buffer.getIntLE(offset + 10);
/* 223 */       if (nameOffset < 0) {
/* 224 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 226 */       int pos = offset + 22 + nameOffset;
/* 227 */       if (pos >= buffer.writerIndex()) {
/* 228 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 230 */       int nameLen = VarInt.peek(buffer, pos);
/* 231 */       if (nameLen < 0) {
/* 232 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 234 */       if (nameLen > 4096000) {
/* 235 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 237 */       pos += VarInt.length(buffer, pos);
/* 238 */       pos += nameLen;
/* 239 */       if (pos > buffer.writerIndex()) {
/* 240 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 244 */     if ((nullBits & 0x4) != 0) {
/* 245 */       int iconOffset = buffer.getIntLE(offset + 14);
/* 246 */       if (iconOffset < 0) {
/* 247 */         return ValidationResult.error("Invalid offset for Icon");
/*     */       }
/* 249 */       int pos = offset + 22 + iconOffset;
/* 250 */       if (pos >= buffer.writerIndex()) {
/* 251 */         return ValidationResult.error("Offset out of bounds for Icon");
/*     */       }
/* 253 */       int iconLen = VarInt.peek(buffer, pos);
/* 254 */       if (iconLen < 0) {
/* 255 */         return ValidationResult.error("Invalid string length for Icon");
/*     */       }
/* 257 */       if (iconLen > 4096000) {
/* 258 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*     */       }
/* 260 */       pos += VarInt.length(buffer, pos);
/* 261 */       pos += iconLen;
/* 262 */       if (pos > buffer.writerIndex()) {
/* 263 */         return ValidationResult.error("Buffer overflow reading Icon");
/*     */       }
/*     */     } 
/*     */     
/* 267 */     if ((nullBits & 0x8) != 0) {
/* 268 */       int childrenOffset = buffer.getIntLE(offset + 18);
/* 269 */       if (childrenOffset < 0) {
/* 270 */         return ValidationResult.error("Invalid offset for Children");
/*     */       }
/* 272 */       int pos = offset + 22 + childrenOffset;
/* 273 */       if (pos >= buffer.writerIndex()) {
/* 274 */         return ValidationResult.error("Offset out of bounds for Children");
/*     */       }
/* 276 */       int childrenCount = VarInt.peek(buffer, pos);
/* 277 */       if (childrenCount < 0) {
/* 278 */         return ValidationResult.error("Invalid array count for Children");
/*     */       }
/* 280 */       if (childrenCount > 4096000) {
/* 281 */         return ValidationResult.error("Children exceeds max length 4096000");
/*     */       }
/* 283 */       pos += VarInt.length(buffer, pos);
/* 284 */       for (int i = 0; i < childrenCount; i++) {
/* 285 */         ValidationResult structResult = validateStructure(buffer, pos);
/* 286 */         if (!structResult.isValid()) {
/* 287 */           return ValidationResult.error("Invalid ItemCategory in Children[" + i + "]: " + structResult.error());
/*     */         }
/* 289 */         pos += computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 292 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemCategory clone() {
/* 296 */     ItemCategory copy = new ItemCategory();
/* 297 */     copy.id = this.id;
/* 298 */     copy.name = this.name;
/* 299 */     copy.icon = this.icon;
/* 300 */     copy.order = this.order;
/* 301 */     copy.infoDisplayMode = this.infoDisplayMode;
/* 302 */     copy.children = (this.children != null) ? (ItemCategory[])Arrays.<ItemCategory>stream(this.children).map(e -> e.clone()).toArray(x$0 -> new ItemCategory[x$0]) : null;
/* 303 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemCategory other;
/* 309 */     if (this == obj) return true; 
/* 310 */     if (obj instanceof ItemCategory) { other = (ItemCategory)obj; } else { return false; }
/* 311 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.icon, other.icon) && this.order == other.order && Objects.equals(this.infoDisplayMode, other.infoDisplayMode) && Arrays.equals((Object[])this.children, (Object[])other.children));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 316 */     int result = 1;
/* 317 */     result = 31 * result + Objects.hashCode(this.id);
/* 318 */     result = 31 * result + Objects.hashCode(this.name);
/* 319 */     result = 31 * result + Objects.hashCode(this.icon);
/* 320 */     result = 31 * result + Integer.hashCode(this.order);
/* 321 */     result = 31 * result + Objects.hashCode(this.infoDisplayMode);
/* 322 */     result = 31 * result + Arrays.hashCode((Object[])this.children);
/* 323 */     return result;
/*     */   }
/*     */   
/*     */   public ItemCategory() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */