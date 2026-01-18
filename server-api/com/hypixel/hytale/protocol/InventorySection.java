/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InventorySection
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 3;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 3;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<Integer, ItemWithAllMetadata> items;
/*     */   public short capacity;
/*     */   
/*     */   public InventorySection() {}
/*     */   
/*     */   public InventorySection(@Nullable Map<Integer, ItemWithAllMetadata> items, short capacity) {
/*  27 */     this.items = items;
/*  28 */     this.capacity = capacity;
/*     */   }
/*     */   
/*     */   public InventorySection(@Nonnull InventorySection other) {
/*  32 */     this.items = other.items;
/*  33 */     this.capacity = other.capacity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InventorySection deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     InventorySection obj = new InventorySection();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.capacity = buf.getShortLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 3;
/*  43 */     if ((nullBits & 0x1) != 0) { int itemsCount = VarInt.peek(buf, pos);
/*  44 */       if (itemsCount < 0) throw ProtocolException.negativeLength("Items", itemsCount); 
/*  45 */       if (itemsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Items", itemsCount, 4096000); 
/*  46 */       pos += VarInt.size(itemsCount);
/*  47 */       obj.items = new HashMap<>(itemsCount);
/*  48 */       for (int i = 0; i < itemsCount; i++) {
/*  49 */         int key = buf.getIntLE(pos); pos += 4;
/*  50 */         ItemWithAllMetadata val = ItemWithAllMetadata.deserialize(buf, pos);
/*  51 */         pos += ItemWithAllMetadata.computeBytesConsumed(buf, pos);
/*  52 */         if (obj.items.put(Integer.valueOf(key), val) != null)
/*  53 */           throw ProtocolException.duplicateKey("items", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 3;
/*  62 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += ItemWithAllMetadata.computeBytesConsumed(buf, pos); i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.items != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeShortLE(this.capacity);
/*     */     
/*  75 */     if (this.items != null) { if (this.items.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Items", this.items.size(), 4096000);  VarInt.write(buf, this.items.size()); for (Map.Entry<Integer, ItemWithAllMetadata> e : this.items.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((ItemWithAllMetadata)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  80 */     int size = 3;
/*  81 */     if (this.items != null) {
/*  82 */       int itemsSize = 0;
/*  83 */       for (Map.Entry<Integer, ItemWithAllMetadata> kvp : this.items.entrySet()) itemsSize += 4 + ((ItemWithAllMetadata)kvp.getValue()).computeSize(); 
/*  84 */       size += VarInt.size(this.items.size()) + itemsSize;
/*     */     } 
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 3) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 3;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int itemsCount = VarInt.peek(buffer, pos);
/* 101 */       if (itemsCount < 0) {
/* 102 */         return ValidationResult.error("Invalid dictionary count for Items");
/*     */       }
/* 104 */       if (itemsCount > 4096000) {
/* 105 */         return ValidationResult.error("Items exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       for (int i = 0; i < itemsCount; i++) {
/* 109 */         pos += 4;
/* 110 */         if (pos > buffer.writerIndex()) {
/* 111 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 113 */         pos += ItemWithAllMetadata.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InventorySection clone() {
/* 121 */     InventorySection copy = new InventorySection();
/* 122 */     if (this.items != null) {
/* 123 */       Map<Integer, ItemWithAllMetadata> m = new HashMap<>();
/* 124 */       for (Map.Entry<Integer, ItemWithAllMetadata> e : this.items.entrySet()) m.put(e.getKey(), ((ItemWithAllMetadata)e.getValue()).clone()); 
/* 125 */       copy.items = m;
/*     */     } 
/* 127 */     copy.capacity = this.capacity;
/* 128 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InventorySection other;
/* 134 */     if (this == obj) return true; 
/* 135 */     if (obj instanceof InventorySection) { other = (InventorySection)obj; } else { return false; }
/* 136 */      return (Objects.equals(this.items, other.items) && this.capacity == other.capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     return Objects.hash(new Object[] { this.items, Short.valueOf(this.capacity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InventorySection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */