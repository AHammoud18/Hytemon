/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Transform;
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
/*     */ public class MapMarker
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 38;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 54;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public MapMarker(@Nullable String id, @Nullable String name, @Nullable String markerImage, @Nullable Transform transform, @Nullable ContextMenuItem[] contextMenuItems) {
/*  30 */     this.id = id;
/*  31 */     this.name = name;
/*  32 */     this.markerImage = markerImage;
/*  33 */     this.transform = transform;
/*  34 */     this.contextMenuItems = contextMenuItems; } @Nullable public String id; @Nullable
/*     */   public String name; @Nullable
/*     */   public String markerImage; @Nullable
/*     */   public Transform transform; @Nullable
/*  38 */   public ContextMenuItem[] contextMenuItems; public MapMarker() {} public MapMarker(@Nonnull MapMarker other) { this.id = other.id;
/*  39 */     this.name = other.name;
/*  40 */     this.markerImage = other.markerImage;
/*  41 */     this.transform = other.transform;
/*  42 */     this.contextMenuItems = other.contextMenuItems; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static MapMarker deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     MapMarker obj = new MapMarker();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     if ((nullBits & 0x8) != 0) obj.transform = Transform.deserialize(buf, offset + 1);
/*     */     
/*  51 */     if ((nullBits & 0x1) != 0) {
/*  52 */       int varPos0 = offset + 54 + buf.getIntLE(offset + 38);
/*  53 */       int idLen = VarInt.peek(buf, varPos0);
/*  54 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  55 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  56 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  58 */     if ((nullBits & 0x2) != 0) {
/*  59 */       int varPos1 = offset + 54 + buf.getIntLE(offset + 42);
/*  60 */       int nameLen = VarInt.peek(buf, varPos1);
/*  61 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  62 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  63 */       obj.name = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*  65 */     if ((nullBits & 0x4) != 0) {
/*  66 */       int varPos2 = offset + 54 + buf.getIntLE(offset + 46);
/*  67 */       int markerImageLen = VarInt.peek(buf, varPos2);
/*  68 */       if (markerImageLen < 0) throw ProtocolException.negativeLength("MarkerImage", markerImageLen); 
/*  69 */       if (markerImageLen > 4096000) throw ProtocolException.stringTooLong("MarkerImage", markerImageLen, 4096000); 
/*  70 */       obj.markerImage = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/*  72 */     if ((nullBits & 0x10) != 0) {
/*  73 */       int varPos3 = offset + 54 + buf.getIntLE(offset + 50);
/*  74 */       int contextMenuItemsCount = VarInt.peek(buf, varPos3);
/*  75 */       if (contextMenuItemsCount < 0) throw ProtocolException.negativeLength("ContextMenuItems", contextMenuItemsCount); 
/*  76 */       if (contextMenuItemsCount > 4096000) throw ProtocolException.arrayTooLong("ContextMenuItems", contextMenuItemsCount, 4096000); 
/*  77 */       int varIntLen = VarInt.length(buf, varPos3);
/*  78 */       if ((varPos3 + varIntLen) + contextMenuItemsCount * 0L > buf.readableBytes())
/*  79 */         throw ProtocolException.bufferTooSmall("ContextMenuItems", varPos3 + varIntLen + contextMenuItemsCount * 0, buf.readableBytes()); 
/*  80 */       obj.contextMenuItems = new ContextMenuItem[contextMenuItemsCount];
/*  81 */       int elemPos = varPos3 + varIntLen;
/*  82 */       for (int i = 0; i < contextMenuItemsCount; i++) {
/*  83 */         obj.contextMenuItems[i] = ContextMenuItem.deserialize(buf, elemPos);
/*  84 */         elemPos += ContextMenuItem.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  92 */     byte nullBits = buf.getByte(offset);
/*  93 */     int maxEnd = 54;
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int fieldOffset0 = buf.getIntLE(offset + 38);
/*  96 */       int pos0 = offset + 54 + fieldOffset0;
/*  97 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  98 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 100 */     if ((nullBits & 0x2) != 0) {
/* 101 */       int fieldOffset1 = buf.getIntLE(offset + 42);
/* 102 */       int pos1 = offset + 54 + fieldOffset1;
/* 103 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 104 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 106 */     if ((nullBits & 0x4) != 0) {
/* 107 */       int fieldOffset2 = buf.getIntLE(offset + 46);
/* 108 */       int pos2 = offset + 54 + fieldOffset2;
/* 109 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 110 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 112 */     if ((nullBits & 0x10) != 0) {
/* 113 */       int fieldOffset3 = buf.getIntLE(offset + 50);
/* 114 */       int pos3 = offset + 54 + fieldOffset3;
/* 115 */       int arrLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 116 */       for (int i = 0; i < arrLen; ) { pos3 += ContextMenuItem.computeBytesConsumed(buf, pos3); i++; }
/* 117 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 119 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 124 */     int startPos = buf.writerIndex();
/* 125 */     byte nullBits = 0;
/* 126 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 127 */     if (this.name != null) nullBits = (byte)(nullBits | 0x2); 
/* 128 */     if (this.markerImage != null) nullBits = (byte)(nullBits | 0x4); 
/* 129 */     if (this.transform != null) nullBits = (byte)(nullBits | 0x8); 
/* 130 */     if (this.contextMenuItems != null) nullBits = (byte)(nullBits | 0x10); 
/* 131 */     buf.writeByte(nullBits);
/*     */     
/* 133 */     if (this.transform != null) { this.transform.serialize(buf); } else { buf.writeZero(37); }
/*     */     
/* 135 */     int idOffsetSlot = buf.writerIndex();
/* 136 */     buf.writeIntLE(0);
/* 137 */     int nameOffsetSlot = buf.writerIndex();
/* 138 */     buf.writeIntLE(0);
/* 139 */     int markerImageOffsetSlot = buf.writerIndex();
/* 140 */     buf.writeIntLE(0);
/* 141 */     int contextMenuItemsOffsetSlot = buf.writerIndex();
/* 142 */     buf.writeIntLE(0);
/*     */     
/* 144 */     int varBlockStart = buf.writerIndex();
/* 145 */     if (this.id != null) {
/* 146 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 147 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 149 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 151 */     if (this.name != null) {
/* 152 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/* 153 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*     */     } else {
/* 155 */       buf.setIntLE(nameOffsetSlot, -1);
/*     */     } 
/* 157 */     if (this.markerImage != null) {
/* 158 */       buf.setIntLE(markerImageOffsetSlot, buf.writerIndex() - varBlockStart);
/* 159 */       PacketIO.writeVarString(buf, this.markerImage, 4096000);
/*     */     } else {
/* 161 */       buf.setIntLE(markerImageOffsetSlot, -1);
/*     */     } 
/* 163 */     if (this.contextMenuItems != null) {
/* 164 */       buf.setIntLE(contextMenuItemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 165 */       if (this.contextMenuItems.length > 4096000) throw ProtocolException.arrayTooLong("ContextMenuItems", this.contextMenuItems.length, 4096000);  VarInt.write(buf, this.contextMenuItems.length); for (ContextMenuItem item : this.contextMenuItems) item.serialize(buf); 
/*     */     } else {
/* 167 */       buf.setIntLE(contextMenuItemsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 173 */     int size = 54;
/* 174 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 175 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/* 176 */     if (this.markerImage != null) size += PacketIO.stringSize(this.markerImage); 
/* 177 */     if (this.contextMenuItems != null) {
/* 178 */       int contextMenuItemsSize = 0;
/* 179 */       for (ContextMenuItem elem : this.contextMenuItems) contextMenuItemsSize += elem.computeSize(); 
/* 180 */       size += VarInt.size(this.contextMenuItems.length) + contextMenuItemsSize;
/*     */     } 
/*     */     
/* 183 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 187 */     if (buffer.readableBytes() - offset < 54) {
/* 188 */       return ValidationResult.error("Buffer too small: expected at least 54 bytes");
/*     */     }
/*     */     
/* 191 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 194 */     if ((nullBits & 0x1) != 0) {
/* 195 */       int idOffset = buffer.getIntLE(offset + 38);
/* 196 */       if (idOffset < 0) {
/* 197 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 199 */       int pos = offset + 54 + idOffset;
/* 200 */       if (pos >= buffer.writerIndex()) {
/* 201 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 203 */       int idLen = VarInt.peek(buffer, pos);
/* 204 */       if (idLen < 0) {
/* 205 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 207 */       if (idLen > 4096000) {
/* 208 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 210 */       pos += VarInt.length(buffer, pos);
/* 211 */       pos += idLen;
/* 212 */       if (pos > buffer.writerIndex()) {
/* 213 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 217 */     if ((nullBits & 0x2) != 0) {
/* 218 */       int nameOffset = buffer.getIntLE(offset + 42);
/* 219 */       if (nameOffset < 0) {
/* 220 */         return ValidationResult.error("Invalid offset for Name");
/*     */       }
/* 222 */       int pos = offset + 54 + nameOffset;
/* 223 */       if (pos >= buffer.writerIndex()) {
/* 224 */         return ValidationResult.error("Offset out of bounds for Name");
/*     */       }
/* 226 */       int nameLen = VarInt.peek(buffer, pos);
/* 227 */       if (nameLen < 0) {
/* 228 */         return ValidationResult.error("Invalid string length for Name");
/*     */       }
/* 230 */       if (nameLen > 4096000) {
/* 231 */         return ValidationResult.error("Name exceeds max length 4096000");
/*     */       }
/* 233 */       pos += VarInt.length(buffer, pos);
/* 234 */       pos += nameLen;
/* 235 */       if (pos > buffer.writerIndex()) {
/* 236 */         return ValidationResult.error("Buffer overflow reading Name");
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if ((nullBits & 0x4) != 0) {
/* 241 */       int markerImageOffset = buffer.getIntLE(offset + 46);
/* 242 */       if (markerImageOffset < 0) {
/* 243 */         return ValidationResult.error("Invalid offset for MarkerImage");
/*     */       }
/* 245 */       int pos = offset + 54 + markerImageOffset;
/* 246 */       if (pos >= buffer.writerIndex()) {
/* 247 */         return ValidationResult.error("Offset out of bounds for MarkerImage");
/*     */       }
/* 249 */       int markerImageLen = VarInt.peek(buffer, pos);
/* 250 */       if (markerImageLen < 0) {
/* 251 */         return ValidationResult.error("Invalid string length for MarkerImage");
/*     */       }
/* 253 */       if (markerImageLen > 4096000) {
/* 254 */         return ValidationResult.error("MarkerImage exceeds max length 4096000");
/*     */       }
/* 256 */       pos += VarInt.length(buffer, pos);
/* 257 */       pos += markerImageLen;
/* 258 */       if (pos > buffer.writerIndex()) {
/* 259 */         return ValidationResult.error("Buffer overflow reading MarkerImage");
/*     */       }
/*     */     } 
/*     */     
/* 263 */     if ((nullBits & 0x10) != 0) {
/* 264 */       int contextMenuItemsOffset = buffer.getIntLE(offset + 50);
/* 265 */       if (contextMenuItemsOffset < 0) {
/* 266 */         return ValidationResult.error("Invalid offset for ContextMenuItems");
/*     */       }
/* 268 */       int pos = offset + 54 + contextMenuItemsOffset;
/* 269 */       if (pos >= buffer.writerIndex()) {
/* 270 */         return ValidationResult.error("Offset out of bounds for ContextMenuItems");
/*     */       }
/* 272 */       int contextMenuItemsCount = VarInt.peek(buffer, pos);
/* 273 */       if (contextMenuItemsCount < 0) {
/* 274 */         return ValidationResult.error("Invalid array count for ContextMenuItems");
/*     */       }
/* 276 */       if (contextMenuItemsCount > 4096000) {
/* 277 */         return ValidationResult.error("ContextMenuItems exceeds max length 4096000");
/*     */       }
/* 279 */       pos += VarInt.length(buffer, pos);
/* 280 */       for (int i = 0; i < contextMenuItemsCount; i++) {
/* 281 */         ValidationResult structResult = ContextMenuItem.validateStructure(buffer, pos);
/* 282 */         if (!structResult.isValid()) {
/* 283 */           return ValidationResult.error("Invalid ContextMenuItem in ContextMenuItems[" + i + "]: " + structResult.error());
/*     */         }
/* 285 */         pos += ContextMenuItem.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 288 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MapMarker clone() {
/* 292 */     MapMarker copy = new MapMarker();
/* 293 */     copy.id = this.id;
/* 294 */     copy.name = this.name;
/* 295 */     copy.markerImage = this.markerImage;
/* 296 */     copy.transform = (this.transform != null) ? this.transform.clone() : null;
/* 297 */     copy.contextMenuItems = (this.contextMenuItems != null) ? (ContextMenuItem[])Arrays.<ContextMenuItem>stream(this.contextMenuItems).map(e -> e.clone()).toArray(x$0 -> new ContextMenuItem[x$0]) : null;
/* 298 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MapMarker other;
/* 304 */     if (this == obj) return true; 
/* 305 */     if (obj instanceof MapMarker) { other = (MapMarker)obj; } else { return false; }
/* 306 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.markerImage, other.markerImage) && Objects.equals(this.transform, other.transform) && Arrays.equals((Object[])this.contextMenuItems, (Object[])other.contextMenuItems));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 311 */     int result = 1;
/* 312 */     result = 31 * result + Objects.hashCode(this.id);
/* 313 */     result = 31 * result + Objects.hashCode(this.name);
/* 314 */     result = 31 * result + Objects.hashCode(this.markerImage);
/* 315 */     result = 31 * result + Objects.hashCode(this.transform);
/* 316 */     result = 31 * result + Arrays.hashCode((Object[])this.contextMenuItems);
/* 317 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\MapMarker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */