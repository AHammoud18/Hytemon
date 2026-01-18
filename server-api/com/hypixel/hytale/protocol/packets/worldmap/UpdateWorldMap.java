/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdateWorldMap
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 241;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   
/*     */   public int getId() {
/*  25 */     return 241;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 13; public static final int MAX_SIZE = 1677721600; @Nullable
/*     */   public MapChunk[] chunks; @Nullable
/*     */   public MapMarker[] addedMarkers;
/*     */   @Nullable
/*     */   public String[] removedMarkers;
/*     */   
/*     */   public UpdateWorldMap() {}
/*     */   
/*     */   public UpdateWorldMap(@Nullable MapChunk[] chunks, @Nullable MapMarker[] addedMarkers, @Nullable String[] removedMarkers) {
/*  36 */     this.chunks = chunks;
/*  37 */     this.addedMarkers = addedMarkers;
/*  38 */     this.removedMarkers = removedMarkers;
/*     */   }
/*     */   
/*     */   public UpdateWorldMap(@Nonnull UpdateWorldMap other) {
/*  42 */     this.chunks = other.chunks;
/*  43 */     this.addedMarkers = other.addedMarkers;
/*  44 */     this.removedMarkers = other.removedMarkers;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateWorldMap deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     UpdateWorldMap obj = new UpdateWorldMap();
/*  50 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  52 */     if ((nullBits & 0x1) != 0) {
/*  53 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  54 */       int chunksCount = VarInt.peek(buf, varPos0);
/*  55 */       if (chunksCount < 0) throw ProtocolException.negativeLength("Chunks", chunksCount); 
/*  56 */       if (chunksCount > 4096000) throw ProtocolException.arrayTooLong("Chunks", chunksCount, 4096000); 
/*  57 */       int varIntLen = VarInt.length(buf, varPos0);
/*  58 */       if ((varPos0 + varIntLen) + chunksCount * 9L > buf.readableBytes())
/*  59 */         throw ProtocolException.bufferTooSmall("Chunks", varPos0 + varIntLen + chunksCount * 9, buf.readableBytes()); 
/*  60 */       obj.chunks = new MapChunk[chunksCount];
/*  61 */       int elemPos = varPos0 + varIntLen;
/*  62 */       for (int i = 0; i < chunksCount; i++) {
/*  63 */         obj.chunks[i] = MapChunk.deserialize(buf, elemPos);
/*  64 */         elemPos += MapChunk.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  67 */     if ((nullBits & 0x2) != 0) {
/*  68 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  69 */       int addedMarkersCount = VarInt.peek(buf, varPos1);
/*  70 */       if (addedMarkersCount < 0) throw ProtocolException.negativeLength("AddedMarkers", addedMarkersCount); 
/*  71 */       if (addedMarkersCount > 4096000) throw ProtocolException.arrayTooLong("AddedMarkers", addedMarkersCount, 4096000); 
/*  72 */       int varIntLen = VarInt.length(buf, varPos1);
/*  73 */       if ((varPos1 + varIntLen) + addedMarkersCount * 38L > buf.readableBytes())
/*  74 */         throw ProtocolException.bufferTooSmall("AddedMarkers", varPos1 + varIntLen + addedMarkersCount * 38, buf.readableBytes()); 
/*  75 */       obj.addedMarkers = new MapMarker[addedMarkersCount];
/*  76 */       int elemPos = varPos1 + varIntLen;
/*  77 */       for (int i = 0; i < addedMarkersCount; i++) {
/*  78 */         obj.addedMarkers[i] = MapMarker.deserialize(buf, elemPos);
/*  79 */         elemPos += MapMarker.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  82 */     if ((nullBits & 0x4) != 0) {
/*  83 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  84 */       int removedMarkersCount = VarInt.peek(buf, varPos2);
/*  85 */       if (removedMarkersCount < 0) throw ProtocolException.negativeLength("RemovedMarkers", removedMarkersCount); 
/*  86 */       if (removedMarkersCount > 4096000) throw ProtocolException.arrayTooLong("RemovedMarkers", removedMarkersCount, 4096000); 
/*  87 */       int varIntLen = VarInt.length(buf, varPos2);
/*  88 */       if ((varPos2 + varIntLen) + removedMarkersCount * 1L > buf.readableBytes())
/*  89 */         throw ProtocolException.bufferTooSmall("RemovedMarkers", varPos2 + varIntLen + removedMarkersCount * 1, buf.readableBytes()); 
/*  90 */       obj.removedMarkers = new String[removedMarkersCount];
/*  91 */       int elemPos = varPos2 + varIntLen;
/*  92 */       for (int i = 0; i < removedMarkersCount; i++) {
/*  93 */         int strLen = VarInt.peek(buf, elemPos);
/*  94 */         if (strLen < 0) throw ProtocolException.negativeLength("removedMarkers[" + i + "]", strLen); 
/*  95 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("removedMarkers[" + i + "]", strLen, 4096000); 
/*  96 */         int strVarLen = VarInt.length(buf, elemPos);
/*  97 */         obj.removedMarkers[i] = PacketIO.readVarString(buf, elemPos);
/*  98 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 106 */     byte nullBits = buf.getByte(offset);
/* 107 */     int maxEnd = 13;
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/* 110 */       int pos0 = offset + 13 + fieldOffset0;
/* 111 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 112 */       for (int i = 0; i < arrLen; ) { pos0 += MapChunk.computeBytesConsumed(buf, pos0); i++; }
/* 113 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 115 */     if ((nullBits & 0x2) != 0) {
/* 116 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 117 */       int pos1 = offset + 13 + fieldOffset1;
/* 118 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 119 */       for (int i = 0; i < arrLen; ) { pos1 += MapMarker.computeBytesConsumed(buf, pos1); i++; }
/* 120 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 122 */     if ((nullBits & 0x4) != 0) {
/* 123 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 124 */       int pos2 = offset + 13 + fieldOffset2;
/* 125 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 126 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl; i++; }
/* 127 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 129 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 135 */     int startPos = buf.writerIndex();
/* 136 */     byte nullBits = 0;
/* 137 */     if (this.chunks != null) nullBits = (byte)(nullBits | 0x1); 
/* 138 */     if (this.addedMarkers != null) nullBits = (byte)(nullBits | 0x2); 
/* 139 */     if (this.removedMarkers != null) nullBits = (byte)(nullBits | 0x4); 
/* 140 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 143 */     int chunksOffsetSlot = buf.writerIndex();
/* 144 */     buf.writeIntLE(0);
/* 145 */     int addedMarkersOffsetSlot = buf.writerIndex();
/* 146 */     buf.writeIntLE(0);
/* 147 */     int removedMarkersOffsetSlot = buf.writerIndex();
/* 148 */     buf.writeIntLE(0);
/*     */     
/* 150 */     int varBlockStart = buf.writerIndex();
/* 151 */     if (this.chunks != null) {
/* 152 */       buf.setIntLE(chunksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 153 */       if (this.chunks.length > 4096000) throw ProtocolException.arrayTooLong("Chunks", this.chunks.length, 4096000);  VarInt.write(buf, this.chunks.length); for (MapChunk item : this.chunks) item.serialize(buf); 
/*     */     } else {
/* 155 */       buf.setIntLE(chunksOffsetSlot, -1);
/*     */     } 
/* 157 */     if (this.addedMarkers != null) {
/* 158 */       buf.setIntLE(addedMarkersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 159 */       if (this.addedMarkers.length > 4096000) throw ProtocolException.arrayTooLong("AddedMarkers", this.addedMarkers.length, 4096000);  VarInt.write(buf, this.addedMarkers.length); for (MapMarker item : this.addedMarkers) item.serialize(buf); 
/*     */     } else {
/* 161 */       buf.setIntLE(addedMarkersOffsetSlot, -1);
/*     */     } 
/* 163 */     if (this.removedMarkers != null) {
/* 164 */       buf.setIntLE(removedMarkersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 165 */       if (this.removedMarkers.length > 4096000) throw ProtocolException.arrayTooLong("RemovedMarkers", this.removedMarkers.length, 4096000);  VarInt.write(buf, this.removedMarkers.length); for (String item : this.removedMarkers) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 167 */       buf.setIntLE(removedMarkersOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 173 */     int size = 13;
/* 174 */     if (this.chunks != null) {
/* 175 */       int chunksSize = 0;
/* 176 */       for (MapChunk elem : this.chunks) chunksSize += elem.computeSize(); 
/* 177 */       size += VarInt.size(this.chunks.length) + chunksSize;
/*     */     } 
/* 179 */     if (this.addedMarkers != null) {
/* 180 */       int addedMarkersSize = 0;
/* 181 */       for (MapMarker elem : this.addedMarkers) addedMarkersSize += elem.computeSize(); 
/* 182 */       size += VarInt.size(this.addedMarkers.length) + addedMarkersSize;
/*     */     } 
/* 184 */     if (this.removedMarkers != null) {
/* 185 */       int removedMarkersSize = 0;
/* 186 */       for (String elem : this.removedMarkers) removedMarkersSize += PacketIO.stringSize(elem); 
/* 187 */       size += VarInt.size(this.removedMarkers.length) + removedMarkersSize;
/*     */     } 
/*     */     
/* 190 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 194 */     if (buffer.readableBytes() - offset < 13) {
/* 195 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 198 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 201 */     if ((nullBits & 0x1) != 0) {
/* 202 */       int chunksOffset = buffer.getIntLE(offset + 1);
/* 203 */       if (chunksOffset < 0) {
/* 204 */         return ValidationResult.error("Invalid offset for Chunks");
/*     */       }
/* 206 */       int pos = offset + 13 + chunksOffset;
/* 207 */       if (pos >= buffer.writerIndex()) {
/* 208 */         return ValidationResult.error("Offset out of bounds for Chunks");
/*     */       }
/* 210 */       int chunksCount = VarInt.peek(buffer, pos);
/* 211 */       if (chunksCount < 0) {
/* 212 */         return ValidationResult.error("Invalid array count for Chunks");
/*     */       }
/* 214 */       if (chunksCount > 4096000) {
/* 215 */         return ValidationResult.error("Chunks exceeds max length 4096000");
/*     */       }
/* 217 */       pos += VarInt.length(buffer, pos);
/* 218 */       for (int i = 0; i < chunksCount; i++) {
/* 219 */         ValidationResult structResult = MapChunk.validateStructure(buffer, pos);
/* 220 */         if (!structResult.isValid()) {
/* 221 */           return ValidationResult.error("Invalid MapChunk in Chunks[" + i + "]: " + structResult.error());
/*     */         }
/* 223 */         pos += MapChunk.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     if ((nullBits & 0x2) != 0) {
/* 228 */       int addedMarkersOffset = buffer.getIntLE(offset + 5);
/* 229 */       if (addedMarkersOffset < 0) {
/* 230 */         return ValidationResult.error("Invalid offset for AddedMarkers");
/*     */       }
/* 232 */       int pos = offset + 13 + addedMarkersOffset;
/* 233 */       if (pos >= buffer.writerIndex()) {
/* 234 */         return ValidationResult.error("Offset out of bounds for AddedMarkers");
/*     */       }
/* 236 */       int addedMarkersCount = VarInt.peek(buffer, pos);
/* 237 */       if (addedMarkersCount < 0) {
/* 238 */         return ValidationResult.error("Invalid array count for AddedMarkers");
/*     */       }
/* 240 */       if (addedMarkersCount > 4096000) {
/* 241 */         return ValidationResult.error("AddedMarkers exceeds max length 4096000");
/*     */       }
/* 243 */       pos += VarInt.length(buffer, pos);
/* 244 */       for (int i = 0; i < addedMarkersCount; i++) {
/* 245 */         ValidationResult structResult = MapMarker.validateStructure(buffer, pos);
/* 246 */         if (!structResult.isValid()) {
/* 247 */           return ValidationResult.error("Invalid MapMarker in AddedMarkers[" + i + "]: " + structResult.error());
/*     */         }
/* 249 */         pos += MapMarker.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 253 */     if ((nullBits & 0x4) != 0) {
/* 254 */       int removedMarkersOffset = buffer.getIntLE(offset + 9);
/* 255 */       if (removedMarkersOffset < 0) {
/* 256 */         return ValidationResult.error("Invalid offset for RemovedMarkers");
/*     */       }
/* 258 */       int pos = offset + 13 + removedMarkersOffset;
/* 259 */       if (pos >= buffer.writerIndex()) {
/* 260 */         return ValidationResult.error("Offset out of bounds for RemovedMarkers");
/*     */       }
/* 262 */       int removedMarkersCount = VarInt.peek(buffer, pos);
/* 263 */       if (removedMarkersCount < 0) {
/* 264 */         return ValidationResult.error("Invalid array count for RemovedMarkers");
/*     */       }
/* 266 */       if (removedMarkersCount > 4096000) {
/* 267 */         return ValidationResult.error("RemovedMarkers exceeds max length 4096000");
/*     */       }
/* 269 */       pos += VarInt.length(buffer, pos);
/* 270 */       for (int i = 0; i < removedMarkersCount; i++) {
/* 271 */         int strLen = VarInt.peek(buffer, pos);
/* 272 */         if (strLen < 0) {
/* 273 */           return ValidationResult.error("Invalid string length in RemovedMarkers");
/*     */         }
/* 275 */         pos += VarInt.length(buffer, pos);
/* 276 */         pos += strLen;
/* 277 */         if (pos > buffer.writerIndex()) {
/* 278 */           return ValidationResult.error("Buffer overflow reading string in RemovedMarkers");
/*     */         }
/*     */       } 
/*     */     } 
/* 282 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateWorldMap clone() {
/* 286 */     UpdateWorldMap copy = new UpdateWorldMap();
/* 287 */     copy.chunks = (this.chunks != null) ? (MapChunk[])Arrays.<MapChunk>stream(this.chunks).map(e -> e.clone()).toArray(x$0 -> new MapChunk[x$0]) : null;
/* 288 */     copy.addedMarkers = (this.addedMarkers != null) ? (MapMarker[])Arrays.<MapMarker>stream(this.addedMarkers).map(e -> e.clone()).toArray(x$0 -> new MapMarker[x$0]) : null;
/* 289 */     copy.removedMarkers = (this.removedMarkers != null) ? Arrays.<String>copyOf(this.removedMarkers, this.removedMarkers.length) : null;
/* 290 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateWorldMap other;
/* 296 */     if (this == obj) return true; 
/* 297 */     if (obj instanceof UpdateWorldMap) { other = (UpdateWorldMap)obj; } else { return false; }
/* 298 */      return (Arrays.equals((Object[])this.chunks, (Object[])other.chunks) && Arrays.equals((Object[])this.addedMarkers, (Object[])other.addedMarkers) && Arrays.equals((Object[])this.removedMarkers, (Object[])other.removedMarkers));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 303 */     int result = 1;
/* 304 */     result = 31 * result + Arrays.hashCode((Object[])this.chunks);
/* 305 */     result = 31 * result + Arrays.hashCode((Object[])this.addedMarkers);
/* 306 */     result = 31 * result + Arrays.hashCode((Object[])this.removedMarkers);
/* 307 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\UpdateWorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */