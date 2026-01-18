/*     */ package com.hypixel.hytale.protocol.packets.window;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class UpdateCategoryAction
/*     */   extends WindowAction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 0;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 8;
/*     */   public static final int MAX_SIZE = 32768018;
/*     */   @Nonnull
/*  20 */   public String category = ""; @Nonnull
/*  21 */   public String itemCategory = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateCategoryAction(@Nonnull String category, @Nonnull String itemCategory) {
/*  27 */     this.category = category;
/*  28 */     this.itemCategory = itemCategory;
/*     */   }
/*     */   
/*     */   public UpdateCategoryAction(@Nonnull UpdateCategoryAction other) {
/*  32 */     this.category = other.category;
/*  33 */     this.itemCategory = other.itemCategory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateCategoryAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     UpdateCategoryAction obj = new UpdateCategoryAction();
/*     */ 
/*     */ 
/*     */     
/*  42 */     int varPos0 = offset + 8 + buf.getIntLE(offset + 0);
/*  43 */     int categoryLen = VarInt.peek(buf, varPos0);
/*  44 */     if (categoryLen < 0) throw ProtocolException.negativeLength("Category", categoryLen); 
/*  45 */     if (categoryLen > 4096000) throw ProtocolException.stringTooLong("Category", categoryLen, 4096000); 
/*  46 */     obj.category = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */ 
/*     */     
/*  49 */     int varPos1 = offset + 8 + buf.getIntLE(offset + 4);
/*  50 */     int itemCategoryLen = VarInt.peek(buf, varPos1);
/*  51 */     if (itemCategoryLen < 0) throw ProtocolException.negativeLength("ItemCategory", itemCategoryLen); 
/*  52 */     if (itemCategoryLen > 4096000) throw ProtocolException.stringTooLong("ItemCategory", itemCategoryLen, 4096000); 
/*  53 */     obj.itemCategory = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     int maxEnd = 8;
/*     */     
/*  62 */     int fieldOffset0 = buf.getIntLE(offset + 0);
/*  63 */     int pos0 = offset + 8 + fieldOffset0;
/*  64 */     int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  65 */     if (pos0 - offset > maxEnd) maxEnd = pos0 - offset;
/*     */ 
/*     */     
/*  68 */     int fieldOffset1 = buf.getIntLE(offset + 4);
/*  69 */     int pos1 = offset + 8 + fieldOffset1;
/*  70 */     sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  71 */     if (pos1 - offset > maxEnd) maxEnd = pos1 - offset;
/*     */     
/*  73 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  79 */     int startPos = buf.writerIndex();
/*     */ 
/*     */     
/*  82 */     int categoryOffsetSlot = buf.writerIndex();
/*  83 */     buf.writeIntLE(0);
/*  84 */     int itemCategoryOffsetSlot = buf.writerIndex();
/*  85 */     buf.writeIntLE(0);
/*     */     
/*  87 */     int varBlockStart = buf.writerIndex();
/*  88 */     buf.setIntLE(categoryOffsetSlot, buf.writerIndex() - varBlockStart);
/*  89 */     PacketIO.writeVarString(buf, this.category, 4096000);
/*  90 */     buf.setIntLE(itemCategoryOffsetSlot, buf.writerIndex() - varBlockStart);
/*  91 */     PacketIO.writeVarString(buf, this.itemCategory, 4096000);
/*  92 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  98 */     int size = 8;
/*  99 */     size += PacketIO.stringSize(this.category);
/* 100 */     size += PacketIO.stringSize(this.itemCategory);
/*     */     
/* 102 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 106 */     if (buffer.readableBytes() - offset < 8) {
/* 107 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     int categoryOffset = buffer.getIntLE(offset + 0);
/* 114 */     if (categoryOffset < 0) {
/* 115 */       return ValidationResult.error("Invalid offset for Category");
/*     */     }
/* 117 */     int pos = offset + 8 + categoryOffset;
/* 118 */     if (pos >= buffer.writerIndex()) {
/* 119 */       return ValidationResult.error("Offset out of bounds for Category");
/*     */     }
/* 121 */     int categoryLen = VarInt.peek(buffer, pos);
/* 122 */     if (categoryLen < 0) {
/* 123 */       return ValidationResult.error("Invalid string length for Category");
/*     */     }
/* 125 */     if (categoryLen > 4096000) {
/* 126 */       return ValidationResult.error("Category exceeds max length 4096000");
/*     */     }
/* 128 */     pos += VarInt.length(buffer, pos);
/* 129 */     pos += categoryLen;
/* 130 */     if (pos > buffer.writerIndex()) {
/* 131 */       return ValidationResult.error("Buffer overflow reading Category");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 136 */     int itemCategoryOffset = buffer.getIntLE(offset + 4);
/* 137 */     if (itemCategoryOffset < 0) {
/* 138 */       return ValidationResult.error("Invalid offset for ItemCategory");
/*     */     }
/* 140 */     pos = offset + 8 + itemCategoryOffset;
/* 141 */     if (pos >= buffer.writerIndex()) {
/* 142 */       return ValidationResult.error("Offset out of bounds for ItemCategory");
/*     */     }
/* 144 */     int itemCategoryLen = VarInt.peek(buffer, pos);
/* 145 */     if (itemCategoryLen < 0) {
/* 146 */       return ValidationResult.error("Invalid string length for ItemCategory");
/*     */     }
/* 148 */     if (itemCategoryLen > 4096000) {
/* 149 */       return ValidationResult.error("ItemCategory exceeds max length 4096000");
/*     */     }
/* 151 */     pos += VarInt.length(buffer, pos);
/* 152 */     pos += itemCategoryLen;
/* 153 */     if (pos > buffer.writerIndex()) {
/* 154 */       return ValidationResult.error("Buffer overflow reading ItemCategory");
/*     */     }
/*     */     
/* 157 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateCategoryAction clone() {
/* 161 */     UpdateCategoryAction copy = new UpdateCategoryAction();
/* 162 */     copy.category = this.category;
/* 163 */     copy.itemCategory = this.itemCategory;
/* 164 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateCategoryAction other;
/* 170 */     if (this == obj) return true; 
/* 171 */     if (obj instanceof UpdateCategoryAction) { other = (UpdateCategoryAction)obj; } else { return false; }
/* 172 */      return (Objects.equals(this.category, other.category) && Objects.equals(this.itemCategory, other.itemCategory));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 177 */     return Objects.hash(new Object[] { this.category, this.itemCategory });
/*     */   }
/*     */   
/*     */   public UpdateCategoryAction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\UpdateCategoryAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */