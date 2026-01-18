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
/*     */ 
/*     */ 
/*     */ public class ItemReticleConfig
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 4;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public ItemReticleConfig(@Nullable String id, @Nullable String[] base, @Nullable Map<Integer, ItemReticle> serverEvents, @Nullable Map<ItemReticleClientEvent, ItemReticle> clientEvents) {
/*  29 */     this.id = id;
/*  30 */     this.base = base;
/*  31 */     this.serverEvents = serverEvents;
/*  32 */     this.clientEvents = clientEvents; } @Nullable
/*     */   public String id; @Nullable
/*     */   public String[] base; @Nullable
/*     */   public Map<Integer, ItemReticle> serverEvents; @Nullable
/*  36 */   public Map<ItemReticleClientEvent, ItemReticle> clientEvents; public ItemReticleConfig() {} public ItemReticleConfig(@Nonnull ItemReticleConfig other) { this.id = other.id;
/*  37 */     this.base = other.base;
/*  38 */     this.serverEvents = other.serverEvents;
/*  39 */     this.clientEvents = other.clientEvents; }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ItemReticleConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     ItemReticleConfig obj = new ItemReticleConfig();
/*  45 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  47 */     if ((nullBits & 0x1) != 0) {
/*  48 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 1);
/*  49 */       int idLen = VarInt.peek(buf, varPos0);
/*  50 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  51 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  52 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  54 */     if ((nullBits & 0x2) != 0) {
/*  55 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 5);
/*  56 */       int baseCount = VarInt.peek(buf, varPos1);
/*  57 */       if (baseCount < 0) throw ProtocolException.negativeLength("Base", baseCount); 
/*  58 */       if (baseCount > 4096000) throw ProtocolException.arrayTooLong("Base", baseCount, 4096000); 
/*  59 */       int varIntLen = VarInt.length(buf, varPos1);
/*  60 */       if ((varPos1 + varIntLen) + baseCount * 1L > buf.readableBytes())
/*  61 */         throw ProtocolException.bufferTooSmall("Base", varPos1 + varIntLen + baseCount * 1, buf.readableBytes()); 
/*  62 */       obj.base = new String[baseCount];
/*  63 */       int elemPos = varPos1 + varIntLen;
/*  64 */       for (int i = 0; i < baseCount; i++) {
/*  65 */         int strLen = VarInt.peek(buf, elemPos);
/*  66 */         if (strLen < 0) throw ProtocolException.negativeLength("base[" + i + "]", strLen); 
/*  67 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("base[" + i + "]", strLen, 4096000); 
/*  68 */         int strVarLen = VarInt.length(buf, elemPos);
/*  69 */         obj.base[i] = PacketIO.readVarString(buf, elemPos);
/*  70 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*  73 */     if ((nullBits & 0x4) != 0) {
/*  74 */       int varPos2 = offset + 17 + buf.getIntLE(offset + 9);
/*  75 */       int serverEventsCount = VarInt.peek(buf, varPos2);
/*  76 */       if (serverEventsCount < 0) throw ProtocolException.negativeLength("ServerEvents", serverEventsCount); 
/*  77 */       if (serverEventsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ServerEvents", serverEventsCount, 4096000); 
/*  78 */       int varIntLen = VarInt.length(buf, varPos2);
/*  79 */       obj.serverEvents = new HashMap<>(serverEventsCount);
/*  80 */       int dictPos = varPos2 + varIntLen;
/*  81 */       for (int i = 0; i < serverEventsCount; i++) {
/*  82 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  83 */         ItemReticle val = ItemReticle.deserialize(buf, dictPos);
/*  84 */         dictPos += ItemReticle.computeBytesConsumed(buf, dictPos);
/*  85 */         if (obj.serverEvents.put(Integer.valueOf(key), val) != null)
/*  86 */           throw ProtocolException.duplicateKey("serverEvents", Integer.valueOf(key)); 
/*     */       } 
/*     */     } 
/*  89 */     if ((nullBits & 0x8) != 0) {
/*  90 */       int varPos3 = offset + 17 + buf.getIntLE(offset + 13);
/*  91 */       int clientEventsCount = VarInt.peek(buf, varPos3);
/*  92 */       if (clientEventsCount < 0) throw ProtocolException.negativeLength("ClientEvents", clientEventsCount); 
/*  93 */       if (clientEventsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ClientEvents", clientEventsCount, 4096000); 
/*  94 */       int varIntLen = VarInt.length(buf, varPos3);
/*  95 */       obj.clientEvents = new HashMap<>(clientEventsCount);
/*  96 */       int dictPos = varPos3 + varIntLen;
/*  97 */       for (int i = 0; i < clientEventsCount; i++) {
/*  98 */         ItemReticleClientEvent key = ItemReticleClientEvent.fromValue(buf.getByte(dictPos)); dictPos++;
/*  99 */         ItemReticle val = ItemReticle.deserialize(buf, dictPos);
/* 100 */         dictPos += ItemReticle.computeBytesConsumed(buf, dictPos);
/* 101 */         if (obj.clientEvents.put(key, val) != null) {
/* 102 */           throw ProtocolException.duplicateKey("clientEvents", key);
/*     */         }
/*     */       } 
/*     */     } 
/* 106 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 110 */     byte nullBits = buf.getByte(offset);
/* 111 */     int maxEnd = 17;
/* 112 */     if ((nullBits & 0x1) != 0) {
/* 113 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/* 114 */       int pos0 = offset + 17 + fieldOffset0;
/* 115 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 116 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 118 */     if ((nullBits & 0x2) != 0) {
/* 119 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 120 */       int pos1 = offset + 17 + fieldOffset1;
/* 121 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 122 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/* 123 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 125 */     if ((nullBits & 0x4) != 0) {
/* 126 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/* 127 */       int pos2 = offset + 17 + fieldOffset2;
/* 128 */       int dictLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2);
/* 129 */       for (int i = 0; i < dictLen; ) { pos2 += 4; pos2 += ItemReticle.computeBytesConsumed(buf, pos2); i++; }
/* 130 */        if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 132 */     if ((nullBits & 0x8) != 0) {
/* 133 */       int fieldOffset3 = buf.getIntLE(offset + 13);
/* 134 */       int pos3 = offset + 17 + fieldOffset3;
/* 135 */       int dictLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/* 136 */       for (int i = 0; i < dictLen; ) { pos3 = ++pos3 + ItemReticle.computeBytesConsumed(buf, pos3); i++; }
/* 137 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 139 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 144 */     int startPos = buf.writerIndex();
/* 145 */     byte nullBits = 0;
/* 146 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 147 */     if (this.base != null) nullBits = (byte)(nullBits | 0x2); 
/* 148 */     if (this.serverEvents != null) nullBits = (byte)(nullBits | 0x4); 
/* 149 */     if (this.clientEvents != null) nullBits = (byte)(nullBits | 0x8); 
/* 150 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 153 */     int idOffsetSlot = buf.writerIndex();
/* 154 */     buf.writeIntLE(0);
/* 155 */     int baseOffsetSlot = buf.writerIndex();
/* 156 */     buf.writeIntLE(0);
/* 157 */     int serverEventsOffsetSlot = buf.writerIndex();
/* 158 */     buf.writeIntLE(0);
/* 159 */     int clientEventsOffsetSlot = buf.writerIndex();
/* 160 */     buf.writeIntLE(0);
/*     */     
/* 162 */     int varBlockStart = buf.writerIndex();
/* 163 */     if (this.id != null) {
/* 164 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 165 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 167 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 169 */     if (this.base != null) {
/* 170 */       buf.setIntLE(baseOffsetSlot, buf.writerIndex() - varBlockStart);
/* 171 */       if (this.base.length > 4096000) throw ProtocolException.arrayTooLong("Base", this.base.length, 4096000);  VarInt.write(buf, this.base.length); for (String item : this.base) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 173 */       buf.setIntLE(baseOffsetSlot, -1);
/*     */     } 
/* 175 */     if (this.serverEvents != null)
/* 176 */     { buf.setIntLE(serverEventsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 177 */       if (this.serverEvents.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ServerEvents", this.serverEvents.size(), 4096000);  VarInt.write(buf, this.serverEvents.size()); for (Map.Entry<Integer, ItemReticle> e : this.serverEvents.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((ItemReticle)e.getValue()).serialize(buf); }
/*     */        }
/* 179 */     else { buf.setIntLE(serverEventsOffsetSlot, -1); }
/*     */     
/* 181 */     if (this.clientEvents != null)
/* 182 */     { buf.setIntLE(clientEventsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 183 */       if (this.clientEvents.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ClientEvents", this.clientEvents.size(), 4096000);  VarInt.write(buf, this.clientEvents.size()); for (Map.Entry<ItemReticleClientEvent, ItemReticle> e : this.clientEvents.entrySet()) { buf.writeByte(((ItemReticleClientEvent)e.getKey()).getValue()); ((ItemReticle)e.getValue()).serialize(buf); }
/*     */        }
/* 185 */     else { buf.setIntLE(clientEventsOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 191 */     int size = 17;
/* 192 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 193 */     if (this.base != null) {
/* 194 */       int baseSize = 0;
/* 195 */       for (String elem : this.base) baseSize += PacketIO.stringSize(elem); 
/* 196 */       size += VarInt.size(this.base.length) + baseSize;
/*     */     } 
/* 198 */     if (this.serverEvents != null) {
/* 199 */       int serverEventsSize = 0;
/* 200 */       for (Map.Entry<Integer, ItemReticle> kvp : this.serverEvents.entrySet()) serverEventsSize += 4 + ((ItemReticle)kvp.getValue()).computeSize(); 
/* 201 */       size += VarInt.size(this.serverEvents.size()) + serverEventsSize;
/*     */     } 
/* 203 */     if (this.clientEvents != null) {
/* 204 */       int clientEventsSize = 0;
/* 205 */       for (Map.Entry<ItemReticleClientEvent, ItemReticle> kvp : this.clientEvents.entrySet()) clientEventsSize += 1 + ((ItemReticle)kvp.getValue()).computeSize(); 
/* 206 */       size += VarInt.size(this.clientEvents.size()) + clientEventsSize;
/*     */     } 
/*     */     
/* 209 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 213 */     if (buffer.readableBytes() - offset < 17) {
/* 214 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 217 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 220 */     if ((nullBits & 0x1) != 0) {
/* 221 */       int idOffset = buffer.getIntLE(offset + 1);
/* 222 */       if (idOffset < 0) {
/* 223 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 225 */       int pos = offset + 17 + idOffset;
/* 226 */       if (pos >= buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 229 */       int idLen = VarInt.peek(buffer, pos);
/* 230 */       if (idLen < 0) {
/* 231 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 233 */       if (idLen > 4096000) {
/* 234 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 236 */       pos += VarInt.length(buffer, pos);
/* 237 */       pos += idLen;
/* 238 */       if (pos > buffer.writerIndex()) {
/* 239 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 243 */     if ((nullBits & 0x2) != 0) {
/* 244 */       int baseOffset = buffer.getIntLE(offset + 5);
/* 245 */       if (baseOffset < 0) {
/* 246 */         return ValidationResult.error("Invalid offset for Base");
/*     */       }
/* 248 */       int pos = offset + 17 + baseOffset;
/* 249 */       if (pos >= buffer.writerIndex()) {
/* 250 */         return ValidationResult.error("Offset out of bounds for Base");
/*     */       }
/* 252 */       int baseCount = VarInt.peek(buffer, pos);
/* 253 */       if (baseCount < 0) {
/* 254 */         return ValidationResult.error("Invalid array count for Base");
/*     */       }
/* 256 */       if (baseCount > 4096000) {
/* 257 */         return ValidationResult.error("Base exceeds max length 4096000");
/*     */       }
/* 259 */       pos += VarInt.length(buffer, pos);
/* 260 */       for (int i = 0; i < baseCount; i++) {
/* 261 */         int strLen = VarInt.peek(buffer, pos);
/* 262 */         if (strLen < 0) {
/* 263 */           return ValidationResult.error("Invalid string length in Base");
/*     */         }
/* 265 */         pos += VarInt.length(buffer, pos);
/* 266 */         pos += strLen;
/* 267 */         if (pos > buffer.writerIndex()) {
/* 268 */           return ValidationResult.error("Buffer overflow reading string in Base");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     if ((nullBits & 0x4) != 0) {
/* 274 */       int serverEventsOffset = buffer.getIntLE(offset + 9);
/* 275 */       if (serverEventsOffset < 0) {
/* 276 */         return ValidationResult.error("Invalid offset for ServerEvents");
/*     */       }
/* 278 */       int pos = offset + 17 + serverEventsOffset;
/* 279 */       if (pos >= buffer.writerIndex()) {
/* 280 */         return ValidationResult.error("Offset out of bounds for ServerEvents");
/*     */       }
/* 282 */       int serverEventsCount = VarInt.peek(buffer, pos);
/* 283 */       if (serverEventsCount < 0) {
/* 284 */         return ValidationResult.error("Invalid dictionary count for ServerEvents");
/*     */       }
/* 286 */       if (serverEventsCount > 4096000) {
/* 287 */         return ValidationResult.error("ServerEvents exceeds max length 4096000");
/*     */       }
/* 289 */       pos += VarInt.length(buffer, pos);
/* 290 */       for (int i = 0; i < serverEventsCount; i++) {
/* 291 */         pos += 4;
/* 292 */         if (pos > buffer.writerIndex()) {
/* 293 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 295 */         pos += ItemReticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 300 */     if ((nullBits & 0x8) != 0) {
/* 301 */       int clientEventsOffset = buffer.getIntLE(offset + 13);
/* 302 */       if (clientEventsOffset < 0) {
/* 303 */         return ValidationResult.error("Invalid offset for ClientEvents");
/*     */       }
/* 305 */       int pos = offset + 17 + clientEventsOffset;
/* 306 */       if (pos >= buffer.writerIndex()) {
/* 307 */         return ValidationResult.error("Offset out of bounds for ClientEvents");
/*     */       }
/* 309 */       int clientEventsCount = VarInt.peek(buffer, pos);
/* 310 */       if (clientEventsCount < 0) {
/* 311 */         return ValidationResult.error("Invalid dictionary count for ClientEvents");
/*     */       }
/* 313 */       if (clientEventsCount > 4096000) {
/* 314 */         return ValidationResult.error("ClientEvents exceeds max length 4096000");
/*     */       }
/* 316 */       pos += VarInt.length(buffer, pos);
/* 317 */       for (int i = 0; i < clientEventsCount; i++) {
/* 318 */         pos++;
/*     */         
/* 320 */         pos += ItemReticle.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 324 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemReticleConfig clone() {
/* 328 */     ItemReticleConfig copy = new ItemReticleConfig();
/* 329 */     copy.id = this.id;
/* 330 */     copy.base = (this.base != null) ? Arrays.<String>copyOf(this.base, this.base.length) : null;
/* 331 */     if (this.serverEvents != null) {
/* 332 */       Map<Integer, ItemReticle> m = new HashMap<>();
/* 333 */       for (Map.Entry<Integer, ItemReticle> e : this.serverEvents.entrySet()) m.put(e.getKey(), ((ItemReticle)e.getValue()).clone()); 
/* 334 */       copy.serverEvents = m;
/*     */     } 
/* 336 */     if (this.clientEvents != null) {
/* 337 */       Map<ItemReticleClientEvent, ItemReticle> m = new HashMap<>();
/* 338 */       for (Map.Entry<ItemReticleClientEvent, ItemReticle> e : this.clientEvents.entrySet()) m.put(e.getKey(), ((ItemReticle)e.getValue()).clone()); 
/* 339 */       copy.clientEvents = m;
/*     */     } 
/* 341 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemReticleConfig other;
/* 347 */     if (this == obj) return true; 
/* 348 */     if (obj instanceof ItemReticleConfig) { other = (ItemReticleConfig)obj; } else { return false; }
/* 349 */      return (Objects.equals(this.id, other.id) && Arrays.equals((Object[])this.base, (Object[])other.base) && Objects.equals(this.serverEvents, other.serverEvents) && Objects.equals(this.clientEvents, other.clientEvents));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 354 */     int result = 1;
/* 355 */     result = 31 * result + Objects.hashCode(this.id);
/* 356 */     result = 31 * result + Arrays.hashCode((Object[])this.base);
/* 357 */     result = 31 * result + Objects.hashCode(this.serverEvents);
/* 358 */     result = 31 * result + Objects.hashCode(this.clientEvents);
/* 359 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemReticleConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */