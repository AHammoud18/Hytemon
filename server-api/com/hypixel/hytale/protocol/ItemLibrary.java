/*     */ package com.hypixel.hytale.protocol;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemLibrary {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public ItemBase[] items;
/*     */   @Nullable
/*     */   public Map<Integer, String>[] blockMap;
/*     */   
/*     */   public ItemLibrary() {}
/*     */   
/*     */   public ItemLibrary(@Nullable ItemBase[] items, @Nullable Map<Integer, String>[] blockMap) {
/*  27 */     this.items = items;
/*  28 */     this.blockMap = blockMap;
/*     */   }
/*     */   
/*     */   public ItemLibrary(@Nonnull ItemLibrary other) {
/*  32 */     this.items = other.items;
/*  33 */     this.blockMap = other.blockMap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemLibrary deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemLibrary obj = new ItemLibrary();
/*  39 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  41 */     if ((nullBits & 0x1) != 0) {
/*  42 */       int varPos0 = offset + 9 + buf.getIntLE(offset + 1);
/*  43 */       int itemsCount = VarInt.peek(buf, varPos0);
/*  44 */       if (itemsCount < 0) throw ProtocolException.negativeLength("Items", itemsCount); 
/*  45 */       if (itemsCount > 4096000) throw ProtocolException.arrayTooLong("Items", itemsCount, 4096000); 
/*  46 */       int varIntLen = VarInt.length(buf, varPos0);
/*  47 */       if ((varPos0 + varIntLen) + itemsCount * 147L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Items", varPos0 + varIntLen + itemsCount * 147, buf.readableBytes()); 
/*  49 */       obj.items = new ItemBase[itemsCount];
/*  50 */       int elemPos = varPos0 + varIntLen;
/*  51 */       for (int i = 0; i < itemsCount; i++) {
/*  52 */         obj.items[i] = ItemBase.deserialize(buf, elemPos);
/*  53 */         elemPos += ItemBase.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 9 + buf.getIntLE(offset + 5);
/*  58 */       int blockMapCount = VarInt.peek(buf, varPos1);
/*  59 */       if (blockMapCount < 0) throw ProtocolException.negativeLength("BlockMap", blockMapCount); 
/*  60 */       if (blockMapCount > 4096000) throw ProtocolException.arrayTooLong("BlockMap", blockMapCount, 4096000); 
/*  61 */       int varIntLen = VarInt.length(buf, varPos1);
/*  62 */       Map[] arrayOfMap = new Map[blockMapCount];
/*  63 */       obj.blockMap = (Map<Integer, String>[])arrayOfMap;
/*  64 */       int elemPos = varPos1 + varIntLen;
/*  65 */       for (int i = 0; i < blockMapCount; i++) {
/*  66 */         int mapLen = VarInt.peek(buf, elemPos);
/*  67 */         int mapVarLen = VarInt.length(buf, elemPos);
/*  68 */         HashMap<Integer, String> map = new HashMap<>(mapLen);
/*  69 */         int mapPos = elemPos + mapVarLen;
/*  70 */         for (int j = 0; j < mapLen; j++) {
/*  71 */           int key = buf.getIntLE(mapPos); mapPos += 4;
/*  72 */           int valLen = VarInt.peek(buf, mapPos);
/*  73 */           if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  74 */           if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/*  75 */           int valVarLen = VarInt.length(buf, mapPos);
/*  76 */           String val = PacketIO.readVarString(buf, mapPos);
/*  77 */           mapPos += valVarLen + valLen;
/*  78 */           if (map.put(Integer.valueOf(key), val) != null)
/*  79 */             throw ProtocolException.duplicateKey("BlockMap[" + i + "]", Integer.valueOf(key)); 
/*     */         } 
/*  81 */         obj.blockMap[i] = map;
/*  82 */         elemPos = mapPos;
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int maxEnd = 9;
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  94 */       int pos0 = offset + 9 + fieldOffset0;
/*  95 */       int arrLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/*  96 */       for (int i = 0; i < arrLen; ) { pos0 += ItemBase.computeBytesConsumed(buf, pos0); i++; }
/*  97 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  99 */     if ((nullBits & 0x2) != 0) {
/* 100 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/* 101 */       int pos1 = offset + 9 + fieldOffset1;
/* 102 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 103 */       for (int i = 0; i < arrLen; ) { for (int dictLen = VarInt.peek(buf, pos1), j = 0; j < dictLen; ) { pos1 += 4; int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; j++; }  i++; }
/* 104 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 106 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 111 */     int startPos = buf.writerIndex();
/* 112 */     byte nullBits = 0;
/* 113 */     if (this.items != null) nullBits = (byte)(nullBits | 0x1); 
/* 114 */     if (this.blockMap != null) nullBits = (byte)(nullBits | 0x2); 
/* 115 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/* 118 */     int itemsOffsetSlot = buf.writerIndex();
/* 119 */     buf.writeIntLE(0);
/* 120 */     int blockMapOffsetSlot = buf.writerIndex();
/* 121 */     buf.writeIntLE(0);
/*     */     
/* 123 */     int varBlockStart = buf.writerIndex();
/* 124 */     if (this.items != null) {
/* 125 */       buf.setIntLE(itemsOffsetSlot, buf.writerIndex() - varBlockStart);
/* 126 */       if (this.items.length > 4096000) throw ProtocolException.arrayTooLong("Items", this.items.length, 4096000);  VarInt.write(buf, this.items.length); for (ItemBase item : this.items) item.serialize(buf); 
/*     */     } else {
/* 128 */       buf.setIntLE(itemsOffsetSlot, -1);
/*     */     } 
/* 130 */     if (this.blockMap != null)
/* 131 */     { buf.setIntLE(blockMapOffsetSlot, buf.writerIndex() - varBlockStart);
/* 132 */       if (this.blockMap.length > 4096000) throw ProtocolException.arrayTooLong("BlockMap", this.blockMap.length, 4096000);  VarInt.write(buf, this.blockMap.length); for (Map<Integer, String> item : this.blockMap) { VarInt.write(buf, item.size()); for (Map.Entry<Integer, String> entry : item.entrySet()) { buf.writeIntLE(((Integer)entry.getKey()).intValue()); PacketIO.writeVarString(buf, entry.getValue(), 4096000); }  }
/*     */        }
/* 134 */     else { buf.setIntLE(blockMapOffsetSlot, -1); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 140 */     int size = 9;
/* 141 */     if (this.items != null) {
/* 142 */       int itemsSize = 0;
/* 143 */       for (ItemBase elem : this.items) itemsSize += elem.computeSize(); 
/* 144 */       size += VarInt.size(this.items.length) + itemsSize;
/*     */     } 
/* 146 */     if (this.blockMap != null) {
/* 147 */       int blockMapSize = 0;
/* 148 */       for (Map<Integer, String> elem : this.blockMap) blockMapSize += VarInt.size(elem.size()) + elem.entrySet().stream().mapToInt(kvpInner -> 4 + PacketIO.stringSize((String)kvpInner.getValue())).sum(); 
/* 149 */       size += VarInt.size(this.blockMap.length) + blockMapSize;
/*     */     } 
/*     */     
/* 152 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 156 */     if (buffer.readableBytes() - offset < 9) {
/* 157 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 160 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 163 */     if ((nullBits & 0x1) != 0) {
/* 164 */       int itemsOffset = buffer.getIntLE(offset + 1);
/* 165 */       if (itemsOffset < 0) {
/* 166 */         return ValidationResult.error("Invalid offset for Items");
/*     */       }
/* 168 */       int pos = offset + 9 + itemsOffset;
/* 169 */       if (pos >= buffer.writerIndex()) {
/* 170 */         return ValidationResult.error("Offset out of bounds for Items");
/*     */       }
/* 172 */       int itemsCount = VarInt.peek(buffer, pos);
/* 173 */       if (itemsCount < 0) {
/* 174 */         return ValidationResult.error("Invalid array count for Items");
/*     */       }
/* 176 */       if (itemsCount > 4096000) {
/* 177 */         return ValidationResult.error("Items exceeds max length 4096000");
/*     */       }
/* 179 */       pos += VarInt.length(buffer, pos);
/* 180 */       for (int i = 0; i < itemsCount; i++) {
/* 181 */         ValidationResult structResult = ItemBase.validateStructure(buffer, pos);
/* 182 */         if (!structResult.isValid()) {
/* 183 */           return ValidationResult.error("Invalid ItemBase in Items[" + i + "]: " + structResult.error());
/*     */         }
/* 185 */         pos += ItemBase.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     if ((nullBits & 0x2) != 0) {
/* 190 */       int blockMapOffset = buffer.getIntLE(offset + 5);
/* 191 */       if (blockMapOffset < 0) {
/* 192 */         return ValidationResult.error("Invalid offset for BlockMap");
/*     */       }
/* 194 */       int pos = offset + 9 + blockMapOffset;
/* 195 */       if (pos >= buffer.writerIndex()) {
/* 196 */         return ValidationResult.error("Offset out of bounds for BlockMap");
/*     */       }
/* 198 */       int blockMapCount = VarInt.peek(buffer, pos);
/* 199 */       if (blockMapCount < 0) {
/* 200 */         return ValidationResult.error("Invalid array count for BlockMap");
/*     */       }
/* 202 */       if (blockMapCount > 4096000) {
/* 203 */         return ValidationResult.error("BlockMap exceeds max length 4096000");
/*     */       }
/* 205 */       pos += VarInt.length(buffer, pos);
/* 206 */       for (int i = 0; i < blockMapCount; i++) {
/* 207 */         int blockMapDictLen = VarInt.peek(buffer, pos);
/* 208 */         if (blockMapDictLen < 0) {
/* 209 */           return ValidationResult.error("Invalid dictionary count in BlockMap[" + i + "]");
/*     */         }
/* 211 */         pos += VarInt.length(buffer, pos);
/* 212 */         for (int j = 0; j < blockMapDictLen; j++) {
/* 213 */           pos += 4;
/* 214 */           if (pos > buffer.writerIndex()) {
/* 215 */             return ValidationResult.error("Buffer overflow reading blockMapKey");
/*     */           }
/* 217 */           int blockMapValLen = VarInt.peek(buffer, pos);
/* 218 */           if (blockMapValLen < 0) {
/* 219 */             return ValidationResult.error("Invalid string length for blockMapVal");
/*     */           }
/* 221 */           if (blockMapValLen > 4096000) {
/* 222 */             return ValidationResult.error("blockMapVal exceeds max length 4096000");
/*     */           }
/* 224 */           pos += VarInt.length(buffer, pos);
/* 225 */           pos += blockMapValLen;
/* 226 */           if (pos > buffer.writerIndex()) {
/* 227 */             return ValidationResult.error("Buffer overflow reading blockMapVal");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemLibrary clone() {
/* 236 */     ItemLibrary copy = new ItemLibrary();
/* 237 */     copy.items = (this.items != null) ? (ItemBase[])Arrays.<ItemBase>stream(this.items).map(e -> e.clone()).toArray(x$0 -> new ItemBase[x$0]) : null;
/* 238 */     copy.blockMap = (this.blockMap != null) ? (Map<Integer, String>[])Arrays.<Map<Integer, String>>stream(this.blockMap).map(d -> new HashMap<>(d)).toArray(x$0 -> new Map[x$0]) : null;
/* 239 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemLibrary other;
/* 245 */     if (this == obj) return true; 
/* 246 */     if (obj instanceof ItemLibrary) { other = (ItemLibrary)obj; } else { return false; }
/* 247 */      return (Arrays.equals((Object[])this.items, (Object[])other.items) && Arrays.equals((Object[])this.blockMap, (Object[])other.blockMap));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 252 */     int result = 1;
/* 253 */     result = 31 * result + Arrays.hashCode((Object[])this.items);
/* 254 */     result = 31 * result + Arrays.hashCode((Object[])this.blockMap);
/* 255 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemLibrary.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */