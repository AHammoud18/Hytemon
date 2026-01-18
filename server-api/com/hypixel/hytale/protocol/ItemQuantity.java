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
/*     */ 
/*     */ public class ItemQuantity
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   @Nullable
/*     */   public String itemId;
/*     */   public int quantity;
/*     */   
/*     */   public ItemQuantity() {}
/*     */   
/*     */   public ItemQuantity(@Nullable String itemId, int quantity) {
/*  27 */     this.itemId = itemId;
/*  28 */     this.quantity = quantity;
/*     */   }
/*     */   
/*     */   public ItemQuantity(@Nonnull ItemQuantity other) {
/*  32 */     this.itemId = other.itemId;
/*  33 */     this.quantity = other.quantity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemQuantity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     ItemQuantity obj = new ItemQuantity();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.quantity = buf.getIntLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int itemIdLen = VarInt.peek(buf, pos);
/*  44 */       if (itemIdLen < 0) throw ProtocolException.negativeLength("ItemId", itemIdLen); 
/*  45 */       if (itemIdLen > 4096000) throw ProtocolException.stringTooLong("ItemId", itemIdLen, 4096000); 
/*  46 */       int itemIdVarLen = VarInt.length(buf, pos);
/*  47 */       obj.itemId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += itemIdVarLen + itemIdLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 5;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.itemId != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.quantity);
/*     */     
/*  68 */     if (this.itemId != null) PacketIO.writeVarString(buf, this.itemId, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 5;
/*  74 */     if (this.itemId != null) size += PacketIO.stringSize(this.itemId);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 5) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 5;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int itemIdLen = VarInt.peek(buffer, pos);
/*  90 */       if (itemIdLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for ItemId");
/*     */       }
/*  93 */       if (itemIdLen > 4096000) {
/*  94 */         return ValidationResult.error("ItemId exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += itemIdLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading ItemId");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemQuantity clone() {
/* 106 */     ItemQuantity copy = new ItemQuantity();
/* 107 */     copy.itemId = this.itemId;
/* 108 */     copy.quantity = this.quantity;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemQuantity other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof ItemQuantity) { other = (ItemQuantity)obj; } else { return false; }
/* 117 */      return (Objects.equals(this.itemId, other.itemId) && this.quantity == other.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { this.itemId, Integer.valueOf(this.quantity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemQuantity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */