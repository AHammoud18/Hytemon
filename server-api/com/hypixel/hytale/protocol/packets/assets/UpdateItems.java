/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ import com.hypixel.hytale.protocol.ItemBase;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
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
/*     */ public class UpdateItems implements Packet {
/*     */   public static final int PACKET_ID = 54;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 54;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, ItemBase> items;
/*     */   @Nullable
/*     */   public String[] removedItems;
/*     */   public boolean updateModels;
/*     */   public boolean updateIcons;
/*     */   
/*     */   public UpdateItems(@Nonnull UpdateType type, @Nullable Map<String, ItemBase> items, @Nullable String[] removedItems, boolean updateModels, boolean updateIcons) {
/*  40 */     this.type = type;
/*  41 */     this.items = items;
/*  42 */     this.removedItems = removedItems;
/*  43 */     this.updateModels = updateModels;
/*  44 */     this.updateIcons = updateIcons;
/*     */   }
/*     */   
/*     */   public UpdateItems(@Nonnull UpdateItems other) {
/*  48 */     this.type = other.type;
/*  49 */     this.items = other.items;
/*  50 */     this.removedItems = other.removedItems;
/*  51 */     this.updateModels = other.updateModels;
/*  52 */     this.updateIcons = other.updateIcons;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateItems deserialize(@Nonnull ByteBuf buf, int offset) {
/*  57 */     UpdateItems obj = new UpdateItems();
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  60 */     obj.updateModels = (buf.getByte(offset + 2) != 0);
/*  61 */     obj.updateIcons = (buf.getByte(offset + 3) != 0);
/*     */     
/*  63 */     if ((nullBits & 0x1) != 0) {
/*  64 */       int varPos0 = offset + 12 + buf.getIntLE(offset + 4);
/*  65 */       int itemsCount = VarInt.peek(buf, varPos0);
/*  66 */       if (itemsCount < 0) throw ProtocolException.negativeLength("Items", itemsCount); 
/*  67 */       if (itemsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Items", itemsCount, 4096000); 
/*  68 */       int varIntLen = VarInt.length(buf, varPos0);
/*  69 */       obj.items = new HashMap<>(itemsCount);
/*  70 */       int dictPos = varPos0 + varIntLen;
/*  71 */       for (int i = 0; i < itemsCount; i++) {
/*  72 */         int keyLen = VarInt.peek(buf, dictPos);
/*  73 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  74 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  75 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  76 */         String key = PacketIO.readVarString(buf, dictPos);
/*  77 */         dictPos += keyVarLen + keyLen;
/*  78 */         ItemBase val = ItemBase.deserialize(buf, dictPos);
/*  79 */         dictPos += ItemBase.computeBytesConsumed(buf, dictPos);
/*  80 */         if (obj.items.put(key, val) != null)
/*  81 */           throw ProtocolException.duplicateKey("items", key); 
/*     */       } 
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int varPos1 = offset + 12 + buf.getIntLE(offset + 8);
/*  86 */       int removedItemsCount = VarInt.peek(buf, varPos1);
/*  87 */       if (removedItemsCount < 0) throw ProtocolException.negativeLength("RemovedItems", removedItemsCount); 
/*  88 */       if (removedItemsCount > 4096000) throw ProtocolException.arrayTooLong("RemovedItems", removedItemsCount, 4096000); 
/*  89 */       int varIntLen = VarInt.length(buf, varPos1);
/*  90 */       if ((varPos1 + varIntLen) + removedItemsCount * 1L > buf.readableBytes())
/*  91 */         throw ProtocolException.bufferTooSmall("RemovedItems", varPos1 + varIntLen + removedItemsCount * 1, buf.readableBytes()); 
/*  92 */       obj.removedItems = new String[removedItemsCount];
/*  93 */       int elemPos = varPos1 + varIntLen;
/*  94 */       for (int i = 0; i < removedItemsCount; i++) {
/*  95 */         int strLen = VarInt.peek(buf, elemPos);
/*  96 */         if (strLen < 0) throw ProtocolException.negativeLength("removedItems[" + i + "]", strLen); 
/*  97 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("removedItems[" + i + "]", strLen, 4096000); 
/*  98 */         int strVarLen = VarInt.length(buf, elemPos);
/*  99 */         obj.removedItems[i] = PacketIO.readVarString(buf, elemPos);
/* 100 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 108 */     byte nullBits = buf.getByte(offset);
/* 109 */     int maxEnd = 12;
/* 110 */     if ((nullBits & 0x1) != 0) {
/* 111 */       int fieldOffset0 = buf.getIntLE(offset + 4);
/* 112 */       int pos0 = offset + 12 + fieldOffset0;
/* 113 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 114 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; pos0 += ItemBase.computeBytesConsumed(buf, pos0); i++; }
/* 115 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 117 */     if ((nullBits & 0x2) != 0) {
/* 118 */       int fieldOffset1 = buf.getIntLE(offset + 8);
/* 119 */       int pos1 = offset + 12 + fieldOffset1;
/* 120 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 121 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/* 122 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 124 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 130 */     int startPos = buf.writerIndex();
/* 131 */     byte nullBits = 0;
/* 132 */     if (this.items != null) nullBits = (byte)(nullBits | 0x1); 
/* 133 */     if (this.removedItems != null) nullBits = (byte)(nullBits | 0x2); 
/* 134 */     buf.writeByte(nullBits);
/*     */     
/* 136 */     buf.writeByte(this.type.getValue());
/* 137 */     buf.writeByte(this.updateModels ? 1 : 0);
/* 138 */     buf.writeByte(this.updateIcons ? 1 : 0);
/*     */     
/* 140 */     int itemsOffsetSlot = buf.writerIndex();
/* 141 */     buf.writeIntLE(0);
/* 142 */     int removedItemsOffsetSlot = buf.writerIndex();
/* 143 */     buf.writeIntLE(0);
/*     */     
/* 145 */     int varBlockStart = buf.writerIndex();
/* 146 */     if (this.items != null)
/* 147 */     { buf.setIntLE(itemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       if (this.items.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Items", this.items.size(), 4096000);  VarInt.write(buf, this.items.size()); for (Map.Entry<String, ItemBase> e : this.items.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((ItemBase)e.getValue()).serialize(buf); }
/*     */        }
/* 150 */     else { buf.setIntLE(itemsOffsetSlot, -1); }
/*     */     
/* 152 */     if (this.removedItems != null) {
/* 153 */       buf.setIntLE(removedItemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 154 */       if (this.removedItems.length > 4096000) throw ProtocolException.arrayTooLong("RemovedItems", this.removedItems.length, 4096000);  VarInt.write(buf, this.removedItems.length); for (String item : this.removedItems) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 156 */       buf.setIntLE(removedItemsOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 162 */     int size = 12;
/* 163 */     if (this.items != null) {
/* 164 */       int itemsSize = 0;
/* 165 */       for (Map.Entry<String, ItemBase> kvp : this.items.entrySet()) itemsSize += PacketIO.stringSize(kvp.getKey()) + ((ItemBase)kvp.getValue()).computeSize(); 
/* 166 */       size += VarInt.size(this.items.size()) + itemsSize;
/*     */     } 
/* 168 */     if (this.removedItems != null) {
/* 169 */       int removedItemsSize = 0;
/* 170 */       for (String elem : this.removedItems) removedItemsSize += PacketIO.stringSize(elem); 
/* 171 */       size += VarInt.size(this.removedItems.length) + removedItemsSize;
/*     */     } 
/*     */     
/* 174 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 178 */     if (buffer.readableBytes() - offset < 12) {
/* 179 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */     
/* 182 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 185 */     if ((nullBits & 0x1) != 0) {
/* 186 */       int itemsOffset = buffer.getIntLE(offset + 4);
/* 187 */       if (itemsOffset < 0) {
/* 188 */         return ValidationResult.error("Invalid offset for Items");
/*     */       }
/* 190 */       int pos = offset + 12 + itemsOffset;
/* 191 */       if (pos >= buffer.writerIndex()) {
/* 192 */         return ValidationResult.error("Offset out of bounds for Items");
/*     */       }
/* 194 */       int itemsCount = VarInt.peek(buffer, pos);
/* 195 */       if (itemsCount < 0) {
/* 196 */         return ValidationResult.error("Invalid dictionary count for Items");
/*     */       }
/* 198 */       if (itemsCount > 4096000) {
/* 199 */         return ValidationResult.error("Items exceeds max length 4096000");
/*     */       }
/* 201 */       pos += VarInt.length(buffer, pos);
/* 202 */       for (int i = 0; i < itemsCount; i++) {
/* 203 */         int keyLen = VarInt.peek(buffer, pos);
/* 204 */         if (keyLen < 0) {
/* 205 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 207 */         if (keyLen > 4096000) {
/* 208 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 210 */         pos += VarInt.length(buffer, pos);
/* 211 */         pos += keyLen;
/* 212 */         if (pos > buffer.writerIndex()) {
/* 213 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 215 */         pos += ItemBase.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 220 */     if ((nullBits & 0x2) != 0) {
/* 221 */       int removedItemsOffset = buffer.getIntLE(offset + 8);
/* 222 */       if (removedItemsOffset < 0) {
/* 223 */         return ValidationResult.error("Invalid offset for RemovedItems");
/*     */       }
/* 225 */       int pos = offset + 12 + removedItemsOffset;
/* 226 */       if (pos >= buffer.writerIndex()) {
/* 227 */         return ValidationResult.error("Offset out of bounds for RemovedItems");
/*     */       }
/* 229 */       int removedItemsCount = VarInt.peek(buffer, pos);
/* 230 */       if (removedItemsCount < 0) {
/* 231 */         return ValidationResult.error("Invalid array count for RemovedItems");
/*     */       }
/* 233 */       if (removedItemsCount > 4096000) {
/* 234 */         return ValidationResult.error("RemovedItems exceeds max length 4096000");
/*     */       }
/* 236 */       pos += VarInt.length(buffer, pos);
/* 237 */       for (int i = 0; i < removedItemsCount; i++) {
/* 238 */         int strLen = VarInt.peek(buffer, pos);
/* 239 */         if (strLen < 0) {
/* 240 */           return ValidationResult.error("Invalid string length in RemovedItems");
/*     */         }
/* 242 */         pos += VarInt.length(buffer, pos);
/* 243 */         pos += strLen;
/* 244 */         if (pos > buffer.writerIndex()) {
/* 245 */           return ValidationResult.error("Buffer overflow reading string in RemovedItems");
/*     */         }
/*     */       } 
/*     */     } 
/* 249 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateItems clone() {
/* 253 */     UpdateItems copy = new UpdateItems();
/* 254 */     copy.type = this.type;
/* 255 */     if (this.items != null) {
/* 256 */       Map<String, ItemBase> m = new HashMap<>();
/* 257 */       for (Map.Entry<String, ItemBase> e : this.items.entrySet()) m.put(e.getKey(), ((ItemBase)e.getValue()).clone()); 
/* 258 */       copy.items = m;
/*     */     } 
/* 260 */     copy.removedItems = (this.removedItems != null) ? Arrays.<String>copyOf(this.removedItems, this.removedItems.length) : null;
/* 261 */     copy.updateModels = this.updateModels;
/* 262 */     copy.updateIcons = this.updateIcons;
/* 263 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateItems other;
/* 269 */     if (this == obj) return true; 
/* 270 */     if (obj instanceof UpdateItems) { other = (UpdateItems)obj; } else { return false; }
/* 271 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.items, other.items) && Arrays.equals((Object[])this.removedItems, (Object[])other.removedItems) && this.updateModels == other.updateModels && this.updateIcons == other.updateIcons);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 276 */     int result = 1;
/* 277 */     result = 31 * result + Objects.hashCode(this.type);
/* 278 */     result = 31 * result + Objects.hashCode(this.items);
/* 279 */     result = 31 * result + Arrays.hashCode((Object[])this.removedItems);
/* 280 */     result = 31 * result + Boolean.hashCode(this.updateModels);
/* 281 */     result = 31 * result + Boolean.hashCode(this.updateIcons);
/* 282 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateItems() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateItems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */