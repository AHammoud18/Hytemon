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
/*     */ public class MaterialQuantity
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 32768027;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   public int itemTag;
/*     */   @Nullable
/*     */   public String resourceTypeId;
/*     */   public int quantity;
/*     */   
/*     */   public MaterialQuantity() {}
/*     */   
/*     */   public MaterialQuantity(@Nullable String itemId, int itemTag, @Nullable String resourceTypeId, int quantity) {
/*  29 */     this.itemId = itemId;
/*  30 */     this.itemTag = itemTag;
/*  31 */     this.resourceTypeId = resourceTypeId;
/*  32 */     this.quantity = quantity;
/*     */   }
/*     */   
/*     */   public MaterialQuantity(@Nonnull MaterialQuantity other) {
/*  36 */     this.itemId = other.itemId;
/*  37 */     this.itemTag = other.itemTag;
/*  38 */     this.resourceTypeId = other.resourceTypeId;
/*  39 */     this.quantity = other.quantity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MaterialQuantity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     MaterialQuantity obj = new MaterialQuantity();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.itemTag = buf.getIntLE(offset + 1);
/*  47 */     obj.quantity = buf.getIntLE(offset + 5);
/*     */     
/*  49 */     if ((nullBits & 0x1) != 0) {
/*  50 */       int varPos0 = offset + 17 + buf.getIntLE(offset + 9);
/*  51 */       int itemIdLen = VarInt.peek(buf, varPos0);
/*  52 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  53 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  54 */       obj.itemId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  56 */     if ((nullBits & 0x2) != 0) {
/*  57 */       int varPos1 = offset + 17 + buf.getIntLE(offset + 13);
/*  58 */       int resourceTypeIdLen = VarInt.peek(buf, varPos1);
/*  59 */       if (resourceTypeIdLen < 0) throw ProtocolException.negativeLength("ResourceTypeId", resourceTypeIdLen); 
/*  60 */       if (resourceTypeIdLen > 4096000) throw ProtocolException.stringTooLong("ResourceTypeId", resourceTypeIdLen, 4096000); 
/*  61 */       obj.resourceTypeId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int maxEnd = 17;
/*  70 */     if ((nullBits & 0x1) != 0) {
/*  71 */       int fieldOffset0 = buf.getIntLE(offset + 9);
/*  72 */       int pos0 = offset + 17 + fieldOffset0;
/*  73 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  74 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int fieldOffset1 = buf.getIntLE(offset + 13);
/*  78 */       int pos1 = offset + 17 + fieldOffset1;
/*  79 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  80 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  82 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  87 */     int startPos = buf.writerIndex();
/*  88 */     byte nullBits = 0;
/*  89 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  90 */     if (this.resourceTypeId != null) nullBits = (byte)(nullBits | 0x2); 
/*  91 */     buf.writeByte(nullBits);
/*     */     
/*  93 */     buf.writeIntLE(this.itemTag);
/*  94 */     buf.writeIntLE(this.quantity);
/*     */     
/*  96 */     int itemIdOffsetSlot = buf.writerIndex();
/*  97 */     buf.writeIntLE(0);
/*  98 */     int resourceTypeIdOffsetSlot = buf.writerIndex();
/*  99 */     buf.writeIntLE(0);
/*     */     
/* 101 */     int varBlockStart = buf.writerIndex();
/* 102 */     if (this.itemId != null) {
/* 103 */       buf.setIntLE(itemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 104 */       PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */     } else {
/* 106 */       buf.setIntLE(itemIdOffsetSlot, -1);
/*     */     } 
/* 108 */     if (this.resourceTypeId != null) {
/* 109 */       buf.setIntLE(resourceTypeIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 110 */       PacketIO.writeVarString(buf, this.resourceTypeId, 4096000);
/*     */     } else {
/* 112 */       buf.setIntLE(resourceTypeIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 118 */     int size = 17;
/* 119 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId); 
/* 120 */     if (this.resourceTypeId != null) size += PacketIO.stringSize(this.resourceTypeId);
/*     */     
/* 122 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 126 */     if (buffer.readableBytes() - offset < 17) {
/* 127 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/* 130 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 133 */     if ((nullBits & 0x1) != 0) {
/* 134 */       int itemIdOffset = buffer.getIntLE(offset + 9);
/* 135 */       if (itemIdOffset < 0) {
/* 136 */         return ValidationResult.error("Invalid offset for ItemId");
/*     */       }
/* 138 */       int pos = offset + 17 + itemIdOffset;
/* 139 */       if (pos >= buffer.writerIndex()) {
/* 140 */         return ValidationResult.error("Offset out of bounds for ItemId");
/*     */       }
/* 142 */       int itemIdLen = VarInt.peek(buffer, pos);
/* 143 */       if (itemIdLen < 0) {
/* 144 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/* 146 */       if (itemIdLen > 4096000) {
/* 147 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/* 149 */       pos += VarInt.length(buffer, pos);
/* 150 */       pos += itemIdLen;
/* 151 */       if (pos > buffer.writerIndex()) {
/* 152 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if ((nullBits & 0x2) != 0) {
/* 157 */       int resourceTypeIdOffset = buffer.getIntLE(offset + 13);
/* 158 */       if (resourceTypeIdOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for ResourceTypeId");
/*     */       }
/* 161 */       int pos = offset + 17 + resourceTypeIdOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for ResourceTypeId");
/*     */       }
/* 165 */       int resourceTypeIdLen = VarInt.peek(buffer, pos);
/* 166 */       if (resourceTypeIdLen < 0) {
/* 167 */         return ValidationResult.error("Invalid string length for ResourceTypeId");
/*     */       }
/* 169 */       if (resourceTypeIdLen > 4096000) {
/* 170 */         return ValidationResult.error("ResourceTypeId exceeds max length 4096000");
/*     */       }
/* 172 */       pos += VarInt.length(buffer, pos);
/* 173 */       pos += resourceTypeIdLen;
/* 174 */       if (pos > buffer.writerIndex()) {
/* 175 */         return ValidationResult.error("Buffer overflow reading ResourceTypeId");
/*     */       }
/*     */     } 
/* 178 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MaterialQuantity clone() {
/* 182 */     MaterialQuantity copy = new MaterialQuantity();
/* 183 */     copy.itemId = this.itemId;
/* 184 */     copy.itemTag = this.itemTag;
/* 185 */     copy.resourceTypeId = this.resourceTypeId;
/* 186 */     copy.quantity = this.quantity;
/* 187 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MaterialQuantity other;
/* 193 */     if (this == obj) return true; 
/* 194 */     if (obj instanceof MaterialQuantity) { other = (MaterialQuantity)obj; } else { return false; }
/* 195 */      return (Objects.equals(this.itemId, other.itemId) && this.itemTag == other.itemTag && Objects.equals(this.resourceTypeId, other.resourceTypeId) && this.quantity == other.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 200 */     return Objects.hash(new Object[] { this.itemId, Integer.valueOf(this.itemTag), this.resourceTypeId, Integer.valueOf(this.quantity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MaterialQuantity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */