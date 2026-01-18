/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ItemCategory;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateItemCategories
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 56;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  26 */     return 56;
/*     */   }
/*     */   @Nonnull
/*  29 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public ItemCategory[] itemCategories;
/*     */ 
/*     */   
/*     */   public UpdateItemCategories(@Nonnull UpdateType type, @Nullable ItemCategory[] itemCategories) {
/*  36 */     this.type = type;
/*  37 */     this.itemCategories = itemCategories;
/*     */   }
/*     */   
/*     */   public UpdateItemCategories(@Nonnull UpdateItemCategories other) {
/*  41 */     this.type = other.type;
/*  42 */     this.itemCategories = other.itemCategories;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateItemCategories deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     UpdateItemCategories obj = new UpdateItemCategories();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  51 */     int pos = offset + 2;
/*  52 */     if ((nullBits & 0x1) != 0) { int itemCategoriesCount = VarInt.peek(buf, pos);
/*  53 */       if (itemCategoriesCount < 0) throw ProtocolException.negativeLength("ItemCategories", itemCategoriesCount); 
/*  54 */       if (itemCategoriesCount > 4096000) throw ProtocolException.arrayTooLong("ItemCategories", itemCategoriesCount, 4096000); 
/*  55 */       int itemCategoriesVarLen = VarInt.size(itemCategoriesCount);
/*  56 */       if ((pos + itemCategoriesVarLen) + itemCategoriesCount * 6L > buf.readableBytes())
/*  57 */         throw ProtocolException.bufferTooSmall("ItemCategories", pos + itemCategoriesVarLen + itemCategoriesCount * 6, buf.readableBytes()); 
/*  58 */       pos += itemCategoriesVarLen;
/*  59 */       obj.itemCategories = new ItemCategory[itemCategoriesCount];
/*  60 */       for (int i = 0; i < itemCategoriesCount; i++) {
/*  61 */         obj.itemCategories[i] = ItemCategory.deserialize(buf, pos);
/*  62 */         pos += ItemCategory.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     byte nullBits = buf.getByte(offset);
/*  70 */     int pos = offset + 2;
/*  71 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  72 */       for (int i = 0; i < arrLen; ) { pos += ItemCategory.computeBytesConsumed(buf, pos); i++; }  }
/*  73 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     byte nullBits = 0;
/*  80 */     if (this.itemCategories != null) nullBits = (byte)(nullBits | 0x1); 
/*  81 */     buf.writeByte(nullBits);
/*     */     
/*  83 */     buf.writeByte(this.type.getValue());
/*     */     
/*  85 */     if (this.itemCategories != null) { if (this.itemCategories.length > 4096000) throw ProtocolException.arrayTooLong("ItemCategories", this.itemCategories.length, 4096000);  VarInt.write(buf, this.itemCategories.length); for (ItemCategory item : this.itemCategories) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  90 */     int size = 2;
/*  91 */     if (this.itemCategories != null) {
/*  92 */       int itemCategoriesSize = 0;
/*  93 */       for (ItemCategory elem : this.itemCategories) itemCategoriesSize += elem.computeSize(); 
/*  94 */       size += VarInt.size(this.itemCategories.length) + itemCategoriesSize;
/*     */     } 
/*     */     
/*  97 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 101 */     if (buffer.readableBytes() - offset < 2) {
/* 102 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 105 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 107 */     int pos = offset + 2;
/*     */     
/* 109 */     if ((nullBits & 0x1) != 0) {
/* 110 */       int itemCategoriesCount = VarInt.peek(buffer, pos);
/* 111 */       if (itemCategoriesCount < 0) {
/* 112 */         return ValidationResult.error("Invalid array count for ItemCategories");
/*     */       }
/* 114 */       if (itemCategoriesCount > 4096000) {
/* 115 */         return ValidationResult.error("ItemCategories exceeds max length 4096000");
/*     */       }
/* 117 */       pos += VarInt.length(buffer, pos);
/* 118 */       for (int i = 0; i < itemCategoriesCount; i++) {
/* 119 */         ValidationResult structResult = ItemCategory.validateStructure(buffer, pos);
/* 120 */         if (!structResult.isValid()) {
/* 121 */           return ValidationResult.error("Invalid ItemCategory in ItemCategories[" + i + "]: " + structResult.error());
/*     */         }
/* 123 */         pos += ItemCategory.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 126 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateItemCategories clone() {
/* 130 */     UpdateItemCategories copy = new UpdateItemCategories();
/* 131 */     copy.type = this.type;
/* 132 */     copy.itemCategories = (this.itemCategories != null) ? (ItemCategory[])Arrays.<ItemCategory>stream(this.itemCategories).map(e -> e.clone()).toArray(x$0 -> new ItemCategory[x$0]) : null;
/* 133 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateItemCategories other;
/* 139 */     if (this == obj) return true; 
/* 140 */     if (obj instanceof UpdateItemCategories) { other = (UpdateItemCategories)obj; } else { return false; }
/* 141 */      return (Objects.equals(this.type, other.type) && Arrays.equals((Object[])this.itemCategories, (Object[])other.itemCategories));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     int result = 1;
/* 147 */     result = 31 * result + Objects.hashCode(this.type);
/* 148 */     result = 31 * result + Arrays.hashCode((Object[])this.itemCategories);
/* 149 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateItemCategories() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateItemCategories.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */