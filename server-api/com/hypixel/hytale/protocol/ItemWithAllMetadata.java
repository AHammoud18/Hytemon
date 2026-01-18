/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ItemWithAllMetadata
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 22;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 32768040;
/*     */   @Nonnull
/*  20 */   public String itemId = "";
/*     */   
/*     */   public int quantity;
/*     */   
/*     */   public double durability;
/*     */   public double maxDurability;
/*     */   public boolean overrideDroppedItemAnimation;
/*     */   @Nullable
/*     */   public String metadata;
/*     */   
/*     */   public ItemWithAllMetadata(@Nonnull String itemId, int quantity, double durability, double maxDurability, boolean overrideDroppedItemAnimation, @Nullable String metadata) {
/*  31 */     this.itemId = itemId;
/*  32 */     this.quantity = quantity;
/*  33 */     this.durability = durability;
/*  34 */     this.maxDurability = maxDurability;
/*  35 */     this.overrideDroppedItemAnimation = overrideDroppedItemAnimation;
/*  36 */     this.metadata = metadata;
/*     */   }
/*     */   
/*     */   public ItemWithAllMetadata(@Nonnull ItemWithAllMetadata other) {
/*  40 */     this.itemId = other.itemId;
/*  41 */     this.quantity = other.quantity;
/*  42 */     this.durability = other.durability;
/*  43 */     this.maxDurability = other.maxDurability;
/*  44 */     this.overrideDroppedItemAnimation = other.overrideDroppedItemAnimation;
/*  45 */     this.metadata = other.metadata;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemWithAllMetadata deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     ItemWithAllMetadata obj = new ItemWithAllMetadata();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.quantity = buf.getIntLE(offset + 1);
/*  53 */     obj.durability = buf.getDoubleLE(offset + 5);
/*  54 */     obj.maxDurability = buf.getDoubleLE(offset + 13);
/*  55 */     obj.overrideDroppedItemAnimation = (buf.getByte(offset + 21) != 0);
/*     */ 
/*     */     
/*  58 */     int varPos0 = offset + 30 + buf.getIntLE(offset + 22);
/*  59 */     int itemIdLen = VarInt.peek(buf, varPos0);
/*  60 */     if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  61 */     if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  62 */     obj.itemId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     
/*  64 */     if ((nullBits & 0x1) != 0) {
/*  65 */       int varPos1 = offset + 30 + buf.getIntLE(offset + 26);
/*  66 */       int metadataLen = VarInt.peek(buf, varPos1);
/*  67 */       if (metadataLen < 0) throw ProtocolException.negativeLength("Metadata", metadataLen); 
/*  68 */       if (metadataLen > 4096000) throw ProtocolException.stringTooLong("Metadata", metadataLen, 4096000); 
/*  69 */       obj.metadata = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 30;
/*     */     
/*  79 */     int fieldOffset0 = buf.getIntLE(offset + 22);
/*  80 */     int pos0 = offset + 30 + fieldOffset0;
/*  81 */     int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  82 */     if (pos0 - offset > maxEnd) maxEnd = pos0 - offset;
/*     */     
/*  84 */     if ((nullBits & 0x1) != 0) {
/*  85 */       int fieldOffset1 = buf.getIntLE(offset + 26);
/*  86 */       int pos1 = offset + 30 + fieldOffset1;
/*  87 */       sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  88 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  90 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  95 */     int startPos = buf.writerIndex();
/*  96 */     byte nullBits = 0;
/*  97 */     if (this.metadata != null) nullBits = (byte)(nullBits | 0x1); 
/*  98 */     buf.writeByte(nullBits);
/*     */     
/* 100 */     buf.writeIntLE(this.quantity);
/* 101 */     buf.writeDoubleLE(this.durability);
/* 102 */     buf.writeDoubleLE(this.maxDurability);
/* 103 */     buf.writeByte(this.overrideDroppedItemAnimation ? 1 : 0);
/*     */     
/* 105 */     int itemIdOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int metadataOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     buf.setIntLE(itemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 112 */     PacketIO.writeVarString(buf, this.itemId, 4096000);
/* 113 */     if (this.metadata != null) {
/* 114 */       buf.setIntLE(metadataOffsetSlot, buf.writerIndex() - varBlockStart);
/* 115 */       PacketIO.writeVarString(buf, this.metadata, 4096000);
/*     */     } else {
/* 117 */       buf.setIntLE(metadataOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 123 */     int size = 30;
/* 124 */     size += PacketIO.stringSize(this.itemId);
/* 125 */     if (this.metadata != null) size += PacketIO.stringSize(this.metadata);
/*     */     
/* 127 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 131 */     if (buffer.readableBytes() - offset < 30) {
/* 132 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */     
/* 135 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */ 
/*     */     
/* 139 */     int itemIdOffset = buffer.getIntLE(offset + 22);
/* 140 */     if (itemIdOffset < 0) {
/* 141 */       return ValidationResult.error("Invalid offset for ItemId");
/*     */     }
/* 143 */     int pos = offset + 30 + itemIdOffset;
/* 144 */     if (pos >= buffer.writerIndex()) {
/* 145 */       return ValidationResult.error("Offset out of bounds for ItemId");
/*     */     }
/* 147 */     int itemIdLen = VarInt.peek(buffer, pos);
/* 148 */     if (itemIdLen < 0) {
/* 149 */       return ValidationResult.error("Invalid string length for ItemId");
/*     */     }
/* 151 */     if (itemIdLen > 4096000) {
/* 152 */       return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */     }
/* 154 */     pos += VarInt.length(buffer, pos);
/* 155 */     pos += itemIdLen;
/* 156 */     if (pos > buffer.writerIndex()) {
/* 157 */       return ValidationResult.error("Buffer overflow reading ItemId");
/*     */     }
/*     */ 
/*     */     
/* 161 */     if ((nullBits & 0x1) != 0) {
/* 162 */       int metadataOffset = buffer.getIntLE(offset + 26);
/* 163 */       if (metadataOffset < 0) {
/* 164 */         return ValidationResult.error("Invalid offset for Metadata");
/*     */       }
/* 166 */       pos = offset + 30 + metadataOffset;
/* 167 */       if (pos >= buffer.writerIndex()) {
/* 168 */         return ValidationResult.error("Offset out of bounds for Metadata");
/*     */       }
/* 170 */       int metadataLen = VarInt.peek(buffer, pos);
/* 171 */       if (metadataLen < 0) {
/* 172 */         return ValidationResult.error("Invalid string length for Metadata");
/*     */       }
/* 174 */       if (metadataLen > 4096000) {
/* 175 */         return ValidationResult.error("Metadata exceeds max length 4096000");
/*     */       }
/* 177 */       pos += VarInt.length(buffer, pos);
/* 178 */       pos += metadataLen;
/* 179 */       if (pos > buffer.writerIndex()) {
/* 180 */         return ValidationResult.error("Buffer overflow reading Metadata");
/*     */       }
/*     */     } 
/* 183 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemWithAllMetadata clone() {
/* 187 */     ItemWithAllMetadata copy = new ItemWithAllMetadata();
/* 188 */     copy.itemId = this.itemId;
/* 189 */     copy.quantity = this.quantity;
/* 190 */     copy.durability = this.durability;
/* 191 */     copy.maxDurability = this.maxDurability;
/* 192 */     copy.overrideDroppedItemAnimation = this.overrideDroppedItemAnimation;
/* 193 */     copy.metadata = this.metadata;
/* 194 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemWithAllMetadata other;
/* 200 */     if (this == obj) return true; 
/* 201 */     if (obj instanceof ItemWithAllMetadata) { other = (ItemWithAllMetadata)obj; } else { return false; }
/* 202 */      return (Objects.equals(this.itemId, other.itemId) && this.quantity == other.quantity && this.durability == other.durability && this.maxDurability == other.maxDurability && this.overrideDroppedItemAnimation == other.overrideDroppedItemAnimation && Objects.equals(this.metadata, other.metadata));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 207 */     return Objects.hash(new Object[] { this.itemId, Integer.valueOf(this.quantity), Double.valueOf(this.durability), Double.valueOf(this.maxDurability), Boolean.valueOf(this.overrideDroppedItemAnimation), this.metadata });
/*     */   }
/*     */   
/*     */   public ItemWithAllMetadata() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemWithAllMetadata.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */