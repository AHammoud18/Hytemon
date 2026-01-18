/*     */ package com.hypixel.hytale.protocol.packets.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.InteractionChainData;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionSyncData;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
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
/*     */ public class SyncInteractionChain
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 33;
/*     */   public static final int VARIABLE_FIELD_COUNT = 7;
/*     */   public static final int VARIABLE_BLOCK_START = 61;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int activeHotbarSlot;
/*  32 */   public int overrideRootInteraction = Integer.MIN_VALUE; public int activeUtilitySlot; public int activeToolsSlot; @Nullable public String itemInHandId; @Nullable public String utilityItemId; @Nullable public String toolsItemId; public boolean initial; public boolean desync; @Nonnull
/*  33 */   public InteractionType interactionType = InteractionType.Primary; public int equipSlot; public int chainId; @Nullable
/*     */   public ForkedChainId forkedId;
/*     */   @Nullable
/*     */   public InteractionChainData data;
/*     */   @Nonnull
/*  38 */   public InteractionState state = InteractionState.Finished;
/*     */   
/*     */   @Nullable
/*     */   public SyncInteractionChain[] newForks;
/*     */   public int operationBaseIndex;
/*     */   @Nullable
/*     */   public InteractionSyncData[] interactionData;
/*     */   
/*     */   public SyncInteractionChain(int activeHotbarSlot, int activeUtilitySlot, int activeToolsSlot, @Nullable String itemInHandId, @Nullable String utilityItemId, @Nullable String toolsItemId, boolean initial, boolean desync, int overrideRootInteraction, @Nonnull InteractionType interactionType, int equipSlot, int chainId, @Nullable ForkedChainId forkedId, @Nullable InteractionChainData data, @Nonnull InteractionState state, @Nullable SyncInteractionChain[] newForks, int operationBaseIndex, @Nullable InteractionSyncData[] interactionData) {
/*  47 */     this.activeHotbarSlot = activeHotbarSlot;
/*  48 */     this.activeUtilitySlot = activeUtilitySlot;
/*  49 */     this.activeToolsSlot = activeToolsSlot;
/*  50 */     this.itemInHandId = itemInHandId;
/*  51 */     this.utilityItemId = utilityItemId;
/*  52 */     this.toolsItemId = toolsItemId;
/*  53 */     this.initial = initial;
/*  54 */     this.desync = desync;
/*  55 */     this.overrideRootInteraction = overrideRootInteraction;
/*  56 */     this.interactionType = interactionType;
/*  57 */     this.equipSlot = equipSlot;
/*  58 */     this.chainId = chainId;
/*  59 */     this.forkedId = forkedId;
/*  60 */     this.data = data;
/*  61 */     this.state = state;
/*  62 */     this.newForks = newForks;
/*  63 */     this.operationBaseIndex = operationBaseIndex;
/*  64 */     this.interactionData = interactionData;
/*     */   }
/*     */   
/*     */   public SyncInteractionChain(@Nonnull SyncInteractionChain other) {
/*  68 */     this.activeHotbarSlot = other.activeHotbarSlot;
/*  69 */     this.activeUtilitySlot = other.activeUtilitySlot;
/*  70 */     this.activeToolsSlot = other.activeToolsSlot;
/*  71 */     this.itemInHandId = other.itemInHandId;
/*  72 */     this.utilityItemId = other.utilityItemId;
/*  73 */     this.toolsItemId = other.toolsItemId;
/*  74 */     this.initial = other.initial;
/*  75 */     this.desync = other.desync;
/*  76 */     this.overrideRootInteraction = other.overrideRootInteraction;
/*  77 */     this.interactionType = other.interactionType;
/*  78 */     this.equipSlot = other.equipSlot;
/*  79 */     this.chainId = other.chainId;
/*  80 */     this.forkedId = other.forkedId;
/*  81 */     this.data = other.data;
/*  82 */     this.state = other.state;
/*  83 */     this.newForks = other.newForks;
/*  84 */     this.operationBaseIndex = other.operationBaseIndex;
/*  85 */     this.interactionData = other.interactionData;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SyncInteractionChain deserialize(@Nonnull ByteBuf buf, int offset) {
/*  90 */     SyncInteractionChain obj = new SyncInteractionChain();
/*  91 */     byte nullBits = buf.getByte(offset);
/*  92 */     obj.activeHotbarSlot = buf.getIntLE(offset + 1);
/*  93 */     obj.activeUtilitySlot = buf.getIntLE(offset + 5);
/*  94 */     obj.activeToolsSlot = buf.getIntLE(offset + 9);
/*  95 */     obj.initial = (buf.getByte(offset + 13) != 0);
/*  96 */     obj.desync = (buf.getByte(offset + 14) != 0);
/*  97 */     obj.overrideRootInteraction = buf.getIntLE(offset + 15);
/*  98 */     obj.interactionType = InteractionType.fromValue(buf.getByte(offset + 19));
/*  99 */     obj.equipSlot = buf.getIntLE(offset + 20);
/* 100 */     obj.chainId = buf.getIntLE(offset + 24);
/* 101 */     obj.state = InteractionState.fromValue(buf.getByte(offset + 28));
/* 102 */     obj.operationBaseIndex = buf.getIntLE(offset + 29);
/*     */     
/* 104 */     if ((nullBits & 0x1) != 0) {
/* 105 */       int varPos0 = offset + 61 + buf.getIntLE(offset + 33);
/* 106 */       int itemInHandIdLen = VarInt.peek(buf, varPos0);
/* 107 */       if (itemInHandIdLen < 0) throw ProtocolException.negativeLength("ItemInHandId", itemInHandIdLen); 
/* 108 */       if (itemInHandIdLen > 4096000) throw ProtocolException.stringTooLong("ItemInHandId", itemInHandIdLen, 4096000); 
/* 109 */       obj.itemInHandId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/* 111 */     if ((nullBits & 0x2) != 0) {
/* 112 */       int varPos1 = offset + 61 + buf.getIntLE(offset + 37);
/* 113 */       int utilityItemIdLen = VarInt.peek(buf, varPos1);
/* 114 */       if (utilityItemIdLen < 0) throw ProtocolException.negativeLength("UtilityItemId", utilityItemIdLen); 
/* 115 */       if (utilityItemIdLen > 4096000) throw ProtocolException.stringTooLong("UtilityItemId", utilityItemIdLen, 4096000); 
/* 116 */       obj.utilityItemId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/* 118 */     if ((nullBits & 0x4) != 0) {
/* 119 */       int varPos2 = offset + 61 + buf.getIntLE(offset + 41);
/* 120 */       int toolsItemIdLen = VarInt.peek(buf, varPos2);
/* 121 */       if (toolsItemIdLen < 0) throw ProtocolException.negativeLength("ToolsItemId", toolsItemIdLen); 
/* 122 */       if (toolsItemIdLen > 4096000) throw ProtocolException.stringTooLong("ToolsItemId", toolsItemIdLen, 4096000); 
/* 123 */       obj.toolsItemId = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*     */     } 
/* 125 */     if ((nullBits & 0x8) != 0) {
/* 126 */       int varPos3 = offset + 61 + buf.getIntLE(offset + 45);
/* 127 */       obj.forkedId = ForkedChainId.deserialize(buf, varPos3);
/*     */     } 
/* 129 */     if ((nullBits & 0x10) != 0) {
/* 130 */       int varPos4 = offset + 61 + buf.getIntLE(offset + 49);
/* 131 */       obj.data = InteractionChainData.deserialize(buf, varPos4);
/*     */     } 
/* 133 */     if ((nullBits & 0x20) != 0) {
/* 134 */       int varPos5 = offset + 61 + buf.getIntLE(offset + 53);
/* 135 */       int newForksCount = VarInt.peek(buf, varPos5);
/* 136 */       if (newForksCount < 0) throw ProtocolException.negativeLength("NewForks", newForksCount); 
/* 137 */       if (newForksCount > 4096000) throw ProtocolException.arrayTooLong("NewForks", newForksCount, 4096000); 
/* 138 */       int varIntLen = VarInt.length(buf, varPos5);
/* 139 */       if ((varPos5 + varIntLen) + newForksCount * 33L > buf.readableBytes())
/* 140 */         throw ProtocolException.bufferTooSmall("NewForks", varPos5 + varIntLen + newForksCount * 33, buf.readableBytes()); 
/* 141 */       obj.newForks = new SyncInteractionChain[newForksCount];
/* 142 */       int elemPos = varPos5 + varIntLen;
/* 143 */       for (int i = 0; i < newForksCount; i++) {
/* 144 */         obj.newForks[i] = deserialize(buf, elemPos);
/* 145 */         elemPos += computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/* 148 */     if ((nullBits & 0x40) != 0) {
/* 149 */       int varPos6 = offset + 61 + buf.getIntLE(offset + 57);
/* 150 */       int interactionDataCount = VarInt.peek(buf, varPos6);
/* 151 */       if (interactionDataCount < 0) throw ProtocolException.negativeLength("InteractionData", interactionDataCount); 
/* 152 */       if (interactionDataCount > 4096000) throw ProtocolException.arrayTooLong("InteractionData", interactionDataCount, 4096000); 
/* 153 */       int varIntLen = VarInt.length(buf, varPos6);
/* 154 */       int interactionDataBitfieldSize = (interactionDataCount + 7) / 8;
/* 155 */       byte[] interactionDataBitfield = PacketIO.readBytes(buf, varPos6 + varIntLen, interactionDataBitfieldSize);
/* 156 */       obj.interactionData = new InteractionSyncData[interactionDataCount];
/* 157 */       int elemPos = varPos6 + varIntLen + interactionDataBitfieldSize;
/* 158 */       for (int i = 0; i < interactionDataCount; i++) {
/* 159 */         if ((interactionDataBitfield[i / 8] & 1 << i % 8) != 0) {
/* 160 */           obj.interactionData[i] = InteractionSyncData.deserialize(buf, elemPos);
/* 161 */           elemPos += InteractionSyncData.computeBytesConsumed(buf, elemPos);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 170 */     byte nullBits = buf.getByte(offset);
/* 171 */     int maxEnd = 61;
/* 172 */     if ((nullBits & 0x1) != 0) {
/* 173 */       int fieldOffset0 = buf.getIntLE(offset + 33);
/* 174 */       int pos0 = offset + 61 + fieldOffset0;
/* 175 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 176 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 178 */     if ((nullBits & 0x2) != 0) {
/* 179 */       int fieldOffset1 = buf.getIntLE(offset + 37);
/* 180 */       int pos1 = offset + 61 + fieldOffset1;
/* 181 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 182 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 184 */     if ((nullBits & 0x4) != 0) {
/* 185 */       int fieldOffset2 = buf.getIntLE(offset + 41);
/* 186 */       int pos2 = offset + 61 + fieldOffset2;
/* 187 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/* 188 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/* 190 */     if ((nullBits & 0x8) != 0) {
/* 191 */       int fieldOffset3 = buf.getIntLE(offset + 45);
/* 192 */       int pos3 = offset + 61 + fieldOffset3;
/* 193 */       pos3 += ForkedChainId.computeBytesConsumed(buf, pos3);
/* 194 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*     */     } 
/* 196 */     if ((nullBits & 0x10) != 0) {
/* 197 */       int fieldOffset4 = buf.getIntLE(offset + 49);
/* 198 */       int pos4 = offset + 61 + fieldOffset4;
/* 199 */       pos4 += InteractionChainData.computeBytesConsumed(buf, pos4);
/* 200 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*     */     } 
/* 202 */     if ((nullBits & 0x20) != 0) {
/* 203 */       int fieldOffset5 = buf.getIntLE(offset + 53);
/* 204 */       int pos5 = offset + 61 + fieldOffset5;
/* 205 */       int arrLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/* 206 */       for (int i = 0; i < arrLen; ) { pos5 += computeBytesConsumed(buf, pos5); i++; }
/* 207 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*     */     } 
/* 209 */     if ((nullBits & 0x40) != 0) {
/* 210 */       int fieldOffset6 = buf.getIntLE(offset + 57);
/* 211 */       int pos6 = offset + 61 + fieldOffset6;
/* 212 */       int arrLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/* 213 */       int bitfieldSize = (arrLen + 7) / 8;
/* 214 */       byte[] bitfield = PacketIO.readBytes(buf, pos6, bitfieldSize);
/* 215 */       pos6 += bitfieldSize;
/* 216 */       for (int i = 0; i < arrLen; ) { if ((bitfield[i / 8] & 1 << i % 8) != 0) pos6 += InteractionSyncData.computeBytesConsumed(buf, pos6);  i++; }
/* 217 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*     */     } 
/* 219 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 224 */     int startPos = buf.writerIndex();
/* 225 */     byte nullBits = 0;
/* 226 */     if (this.itemInHandId != null) nullBits = (byte)(nullBits | 0x1); 
/* 227 */     if (this.utilityItemId != null) nullBits = (byte)(nullBits | 0x2); 
/* 228 */     if (this.toolsItemId != null) nullBits = (byte)(nullBits | 0x4); 
/* 229 */     if (this.forkedId != null) nullBits = (byte)(nullBits | 0x8); 
/* 230 */     if (this.data != null) nullBits = (byte)(nullBits | 0x10); 
/* 231 */     if (this.newForks != null) nullBits = (byte)(nullBits | 0x20); 
/* 232 */     if (this.interactionData != null) nullBits = (byte)(nullBits | 0x40); 
/* 233 */     buf.writeByte(nullBits);
/*     */     
/* 235 */     buf.writeIntLE(this.activeHotbarSlot);
/* 236 */     buf.writeIntLE(this.activeUtilitySlot);
/* 237 */     buf.writeIntLE(this.activeToolsSlot);
/* 238 */     buf.writeByte(this.initial ? 1 : 0);
/* 239 */     buf.writeByte(this.desync ? 1 : 0);
/* 240 */     buf.writeIntLE(this.overrideRootInteraction);
/* 241 */     buf.writeByte(this.interactionType.getValue());
/* 242 */     buf.writeIntLE(this.equipSlot);
/* 243 */     buf.writeIntLE(this.chainId);
/* 244 */     buf.writeByte(this.state.getValue());
/* 245 */     buf.writeIntLE(this.operationBaseIndex);
/*     */     
/* 247 */     int itemInHandIdOffsetSlot = buf.writerIndex();
/* 248 */     buf.writeIntLE(0);
/* 249 */     int utilityItemIdOffsetSlot = buf.writerIndex();
/* 250 */     buf.writeIntLE(0);
/* 251 */     int toolsItemIdOffsetSlot = buf.writerIndex();
/* 252 */     buf.writeIntLE(0);
/* 253 */     int forkedIdOffsetSlot = buf.writerIndex();
/* 254 */     buf.writeIntLE(0);
/* 255 */     int dataOffsetSlot = buf.writerIndex();
/* 256 */     buf.writeIntLE(0);
/* 257 */     int newForksOffsetSlot = buf.writerIndex();
/* 258 */     buf.writeIntLE(0);
/* 259 */     int interactionDataOffsetSlot = buf.writerIndex();
/* 260 */     buf.writeIntLE(0);
/*     */     
/* 262 */     int varBlockStart = buf.writerIndex();
/* 263 */     if (this.itemInHandId != null) {
/* 264 */       buf.setIntLE(itemInHandIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 265 */       PacketIO.writeVarString(buf, this.itemInHandId, 4096000);
/*     */     } else {
/* 267 */       buf.setIntLE(itemInHandIdOffsetSlot, -1);
/*     */     } 
/* 269 */     if (this.utilityItemId != null) {
/* 270 */       buf.setIntLE(utilityItemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 271 */       PacketIO.writeVarString(buf, this.utilityItemId, 4096000);
/*     */     } else {
/* 273 */       buf.setIntLE(utilityItemIdOffsetSlot, -1);
/*     */     } 
/* 275 */     if (this.toolsItemId != null) {
/* 276 */       buf.setIntLE(toolsItemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 277 */       PacketIO.writeVarString(buf, this.toolsItemId, 4096000);
/*     */     } else {
/* 279 */       buf.setIntLE(toolsItemIdOffsetSlot, -1);
/*     */     } 
/* 281 */     if (this.forkedId != null) {
/* 282 */       buf.setIntLE(forkedIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 283 */       this.forkedId.serialize(buf);
/*     */     } else {
/* 285 */       buf.setIntLE(forkedIdOffsetSlot, -1);
/*     */     } 
/* 287 */     if (this.data != null) {
/* 288 */       buf.setIntLE(dataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 289 */       this.data.serialize(buf);
/*     */     } else {
/* 291 */       buf.setIntLE(dataOffsetSlot, -1);
/*     */     } 
/* 293 */     if (this.newForks != null) {
/* 294 */       buf.setIntLE(newForksOffsetSlot, buf.writerIndex() - varBlockStart);
/* 295 */       if (this.newForks.length > 4096000) throw ProtocolException.arrayTooLong("NewForks", this.newForks.length, 4096000);  VarInt.write(buf, this.newForks.length); for (SyncInteractionChain item : this.newForks) item.serialize(buf); 
/*     */     } else {
/* 297 */       buf.setIntLE(newForksOffsetSlot, -1);
/*     */     } 
/* 299 */     if (this.interactionData != null) {
/* 300 */       buf.setIntLE(interactionDataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 301 */       if (this.interactionData.length > 4096000) throw ProtocolException.arrayTooLong("InteractionData", this.interactionData.length, 4096000);  VarInt.write(buf, this.interactionData.length);
/* 302 */       int interactionDataBitfieldSize = (this.interactionData.length + 7) / 8;
/* 303 */       byte[] interactionDataBitfield = new byte[interactionDataBitfieldSize]; int i;
/* 304 */       for (i = 0; i < this.interactionData.length; i++) {
/* 305 */         if (this.interactionData[i] != null) interactionDataBitfield[i / 8] = (byte)(interactionDataBitfield[i / 8] | (byte)(1 << i % 8)); 
/*     */       } 
/* 307 */       buf.writeBytes(interactionDataBitfield);
/* 308 */       for (i = 0; i < this.interactionData.length; i++) {
/* 309 */         if (this.interactionData[i] != null) this.interactionData[i].serialize(buf); 
/*     */       } 
/*     */     } else {
/* 312 */       buf.setIntLE(interactionDataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 318 */     int size = 61;
/* 319 */     if (this.itemInHandId != null) size += PacketIO.stringSize(this.itemInHandId); 
/* 320 */     if (this.utilityItemId != null) size += PacketIO.stringSize(this.utilityItemId); 
/* 321 */     if (this.toolsItemId != null) size += PacketIO.stringSize(this.toolsItemId); 
/* 322 */     if (this.forkedId != null) size += this.forkedId.computeSize(); 
/* 323 */     if (this.data != null) size += this.data.computeSize(); 
/* 324 */     if (this.newForks != null) {
/* 325 */       int newForksSize = 0;
/* 326 */       for (SyncInteractionChain elem : this.newForks) newForksSize += elem.computeSize(); 
/* 327 */       size += VarInt.size(this.newForks.length) + newForksSize;
/*     */     } 
/* 329 */     if (this.interactionData != null) {
/* 330 */       int interactionDataSize = 0;
/* 331 */       for (InteractionSyncData elem : this.interactionData) { if (elem != null) interactionDataSize += elem.computeSize();  }
/* 332 */        size += VarInt.size(this.interactionData.length) + (this.interactionData.length + 7) / 8 + interactionDataSize;
/*     */     } 
/*     */     
/* 335 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 339 */     if (buffer.readableBytes() - offset < 61) {
/* 340 */       return ValidationResult.error("Buffer too small: expected at least 61 bytes");
/*     */     }
/*     */     
/* 343 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 346 */     if ((nullBits & 0x1) != 0) {
/* 347 */       int itemInHandIdOffset = buffer.getIntLE(offset + 33);
/* 348 */       if (itemInHandIdOffset < 0) {
/* 349 */         return ValidationResult.error("Invalid offset for ItemInHandId");
/*     */       }
/* 351 */       int pos = offset + 61 + itemInHandIdOffset;
/* 352 */       if (pos >= buffer.writerIndex()) {
/* 353 */         return ValidationResult.error("Offset out of bounds for ItemInHandId");
/*     */       }
/* 355 */       int itemInHandIdLen = VarInt.peek(buffer, pos);
/* 356 */       if (itemInHandIdLen < 0) {
/* 357 */         return ValidationResult.error("Invalid string length for ItemInHandId");
/*     */       }
/* 359 */       if (itemInHandIdLen > 4096000) {
/* 360 */         return ValidationResult.error("ItemInHandId exceeds max length 4096000");
/*     */       }
/* 362 */       pos += VarInt.length(buffer, pos);
/* 363 */       pos += itemInHandIdLen;
/* 364 */       if (pos > buffer.writerIndex()) {
/* 365 */         return ValidationResult.error("Buffer overflow reading ItemInHandId");
/*     */       }
/*     */     } 
/*     */     
/* 369 */     if ((nullBits & 0x2) != 0) {
/* 370 */       int utilityItemIdOffset = buffer.getIntLE(offset + 37);
/* 371 */       if (utilityItemIdOffset < 0) {
/* 372 */         return ValidationResult.error("Invalid offset for UtilityItemId");
/*     */       }
/* 374 */       int pos = offset + 61 + utilityItemIdOffset;
/* 375 */       if (pos >= buffer.writerIndex()) {
/* 376 */         return ValidationResult.error("Offset out of bounds for UtilityItemId");
/*     */       }
/* 378 */       int utilityItemIdLen = VarInt.peek(buffer, pos);
/* 379 */       if (utilityItemIdLen < 0) {
/* 380 */         return ValidationResult.error("Invalid string length for UtilityItemId");
/*     */       }
/* 382 */       if (utilityItemIdLen > 4096000) {
/* 383 */         return ValidationResult.error("UtilityItemId exceeds max length 4096000");
/*     */       }
/* 385 */       pos += VarInt.length(buffer, pos);
/* 386 */       pos += utilityItemIdLen;
/* 387 */       if (pos > buffer.writerIndex()) {
/* 388 */         return ValidationResult.error("Buffer overflow reading UtilityItemId");
/*     */       }
/*     */     } 
/*     */     
/* 392 */     if ((nullBits & 0x4) != 0) {
/* 393 */       int toolsItemIdOffset = buffer.getIntLE(offset + 41);
/* 394 */       if (toolsItemIdOffset < 0) {
/* 395 */         return ValidationResult.error("Invalid offset for ToolsItemId");
/*     */       }
/* 397 */       int pos = offset + 61 + toolsItemIdOffset;
/* 398 */       if (pos >= buffer.writerIndex()) {
/* 399 */         return ValidationResult.error("Offset out of bounds for ToolsItemId");
/*     */       }
/* 401 */       int toolsItemIdLen = VarInt.peek(buffer, pos);
/* 402 */       if (toolsItemIdLen < 0) {
/* 403 */         return ValidationResult.error("Invalid string length for ToolsItemId");
/*     */       }
/* 405 */       if (toolsItemIdLen > 4096000) {
/* 406 */         return ValidationResult.error("ToolsItemId exceeds max length 4096000");
/*     */       }
/* 408 */       pos += VarInt.length(buffer, pos);
/* 409 */       pos += toolsItemIdLen;
/* 410 */       if (pos > buffer.writerIndex()) {
/* 411 */         return ValidationResult.error("Buffer overflow reading ToolsItemId");
/*     */       }
/*     */     } 
/*     */     
/* 415 */     if ((nullBits & 0x8) != 0) {
/* 416 */       int forkedIdOffset = buffer.getIntLE(offset + 45);
/* 417 */       if (forkedIdOffset < 0) {
/* 418 */         return ValidationResult.error("Invalid offset for ForkedId");
/*     */       }
/* 420 */       int pos = offset + 61 + forkedIdOffset;
/* 421 */       if (pos >= buffer.writerIndex()) {
/* 422 */         return ValidationResult.error("Offset out of bounds for ForkedId");
/*     */       }
/* 424 */       ValidationResult forkedIdResult = ForkedChainId.validateStructure(buffer, pos);
/* 425 */       if (!forkedIdResult.isValid()) {
/* 426 */         return ValidationResult.error("Invalid ForkedId: " + forkedIdResult.error());
/*     */       }
/* 428 */       pos += ForkedChainId.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 431 */     if ((nullBits & 0x10) != 0) {
/* 432 */       int dataOffset = buffer.getIntLE(offset + 49);
/* 433 */       if (dataOffset < 0) {
/* 434 */         return ValidationResult.error("Invalid offset for Data");
/*     */       }
/* 436 */       int pos = offset + 61 + dataOffset;
/* 437 */       if (pos >= buffer.writerIndex()) {
/* 438 */         return ValidationResult.error("Offset out of bounds for Data");
/*     */       }
/* 440 */       ValidationResult dataResult = InteractionChainData.validateStructure(buffer, pos);
/* 441 */       if (!dataResult.isValid()) {
/* 442 */         return ValidationResult.error("Invalid Data: " + dataResult.error());
/*     */       }
/* 444 */       pos += InteractionChainData.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 447 */     if ((nullBits & 0x20) != 0) {
/* 448 */       int newForksOffset = buffer.getIntLE(offset + 53);
/* 449 */       if (newForksOffset < 0) {
/* 450 */         return ValidationResult.error("Invalid offset for NewForks");
/*     */       }
/* 452 */       int pos = offset + 61 + newForksOffset;
/* 453 */       if (pos >= buffer.writerIndex()) {
/* 454 */         return ValidationResult.error("Offset out of bounds for NewForks");
/*     */       }
/* 456 */       int newForksCount = VarInt.peek(buffer, pos);
/* 457 */       if (newForksCount < 0) {
/* 458 */         return ValidationResult.error("Invalid array count for NewForks");
/*     */       }
/* 460 */       if (newForksCount > 4096000) {
/* 461 */         return ValidationResult.error("NewForks exceeds max length 4096000");
/*     */       }
/* 463 */       pos += VarInt.length(buffer, pos);
/* 464 */       for (int i = 0; i < newForksCount; i++) {
/* 465 */         ValidationResult structResult = validateStructure(buffer, pos);
/* 466 */         if (!structResult.isValid()) {
/* 467 */           return ValidationResult.error("Invalid SyncInteractionChain in NewForks[" + i + "]: " + structResult.error());
/*     */         }
/* 469 */         pos += computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 473 */     if ((nullBits & 0x40) != 0) {
/* 474 */       int interactionDataOffset = buffer.getIntLE(offset + 57);
/* 475 */       if (interactionDataOffset < 0) {
/* 476 */         return ValidationResult.error("Invalid offset for InteractionData");
/*     */       }
/* 478 */       int pos = offset + 61 + interactionDataOffset;
/* 479 */       if (pos >= buffer.writerIndex()) {
/* 480 */         return ValidationResult.error("Offset out of bounds for InteractionData");
/*     */       }
/* 482 */       int interactionDataCount = VarInt.peek(buffer, pos);
/* 483 */       if (interactionDataCount < 0) {
/* 484 */         return ValidationResult.error("Invalid array count for InteractionData");
/*     */       }
/* 486 */       if (interactionDataCount > 4096000) {
/* 487 */         return ValidationResult.error("InteractionData exceeds max length 4096000");
/*     */       }
/* 489 */       pos += VarInt.length(buffer, pos);
/* 490 */       for (int i = 0; i < interactionDataCount; i++) {
/* 491 */         ValidationResult structResult = InteractionSyncData.validateStructure(buffer, pos);
/* 492 */         if (!structResult.isValid()) {
/* 493 */           return ValidationResult.error("Invalid InteractionSyncData in InteractionData[" + i + "]: " + structResult.error());
/*     */         }
/* 495 */         pos += InteractionSyncData.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 498 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SyncInteractionChain clone() {
/* 502 */     SyncInteractionChain copy = new SyncInteractionChain();
/* 503 */     copy.activeHotbarSlot = this.activeHotbarSlot;
/* 504 */     copy.activeUtilitySlot = this.activeUtilitySlot;
/* 505 */     copy.activeToolsSlot = this.activeToolsSlot;
/* 506 */     copy.itemInHandId = this.itemInHandId;
/* 507 */     copy.utilityItemId = this.utilityItemId;
/* 508 */     copy.toolsItemId = this.toolsItemId;
/* 509 */     copy.initial = this.initial;
/* 510 */     copy.desync = this.desync;
/* 511 */     copy.overrideRootInteraction = this.overrideRootInteraction;
/* 512 */     copy.interactionType = this.interactionType;
/* 513 */     copy.equipSlot = this.equipSlot;
/* 514 */     copy.chainId = this.chainId;
/* 515 */     copy.forkedId = (this.forkedId != null) ? this.forkedId.clone() : null;
/* 516 */     copy.data = (this.data != null) ? this.data.clone() : null;
/* 517 */     copy.state = this.state;
/* 518 */     copy.newForks = (this.newForks != null) ? (SyncInteractionChain[])Arrays.<SyncInteractionChain>stream(this.newForks).map(e -> e.clone()).toArray(x$0 -> new SyncInteractionChain[x$0]) : null;
/* 519 */     copy.operationBaseIndex = this.operationBaseIndex;
/* 520 */     copy.interactionData = (this.interactionData != null) ? (InteractionSyncData[])Arrays.<InteractionSyncData>stream(this.interactionData).map(e -> (e != null) ? e.clone() : null).toArray(x$0 -> new InteractionSyncData[x$0]) : null;
/* 521 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SyncInteractionChain other;
/* 527 */     if (this == obj) return true; 
/* 528 */     if (obj instanceof SyncInteractionChain) { other = (SyncInteractionChain)obj; } else { return false; }
/* 529 */      return (this.activeHotbarSlot == other.activeHotbarSlot && this.activeUtilitySlot == other.activeUtilitySlot && this.activeToolsSlot == other.activeToolsSlot && Objects.equals(this.itemInHandId, other.itemInHandId) && Objects.equals(this.utilityItemId, other.utilityItemId) && Objects.equals(this.toolsItemId, other.toolsItemId) && this.initial == other.initial && this.desync == other.desync && this.overrideRootInteraction == other.overrideRootInteraction && Objects.equals(this.interactionType, other.interactionType) && this.equipSlot == other.equipSlot && this.chainId == other.chainId && Objects.equals(this.forkedId, other.forkedId) && Objects.equals(this.data, other.data) && Objects.equals(this.state, other.state) && Arrays.equals((Object[])this.newForks, (Object[])other.newForks) && this.operationBaseIndex == other.operationBaseIndex && Arrays.equals((Object[])this.interactionData, (Object[])other.interactionData));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 534 */     int result = 1;
/* 535 */     result = 31 * result + Integer.hashCode(this.activeHotbarSlot);
/* 536 */     result = 31 * result + Integer.hashCode(this.activeUtilitySlot);
/* 537 */     result = 31 * result + Integer.hashCode(this.activeToolsSlot);
/* 538 */     result = 31 * result + Objects.hashCode(this.itemInHandId);
/* 539 */     result = 31 * result + Objects.hashCode(this.utilityItemId);
/* 540 */     result = 31 * result + Objects.hashCode(this.toolsItemId);
/* 541 */     result = 31 * result + Boolean.hashCode(this.initial);
/* 542 */     result = 31 * result + Boolean.hashCode(this.desync);
/* 543 */     result = 31 * result + Integer.hashCode(this.overrideRootInteraction);
/* 544 */     result = 31 * result + Objects.hashCode(this.interactionType);
/* 545 */     result = 31 * result + Integer.hashCode(this.equipSlot);
/* 546 */     result = 31 * result + Integer.hashCode(this.chainId);
/* 547 */     result = 31 * result + Objects.hashCode(this.forkedId);
/* 548 */     result = 31 * result + Objects.hashCode(this.data);
/* 549 */     result = 31 * result + Objects.hashCode(this.state);
/* 550 */     result = 31 * result + Arrays.hashCode((Object[])this.newForks);
/* 551 */     result = 31 * result + Integer.hashCode(this.operationBaseIndex);
/* 552 */     result = 31 * result + Arrays.hashCode((Object[])this.interactionData);
/* 553 */     return result;
/*     */   }
/*     */   
/*     */   public SyncInteractionChain() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interaction\SyncInteractionChain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */