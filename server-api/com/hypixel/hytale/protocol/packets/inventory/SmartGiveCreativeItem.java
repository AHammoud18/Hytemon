/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ItemQuantity;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SmartMoveType;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SmartGiveCreativeItem
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 173;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 16384011;
/*     */   
/*     */   public int getId() {
/*  26 */     return 173;
/*     */   }
/*     */   @Nonnull
/*  29 */   public ItemQuantity item = new ItemQuantity(); @Nonnull
/*  30 */   public SmartMoveType moveType = SmartMoveType.EquipOrMergeStack;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SmartGiveCreativeItem(@Nonnull ItemQuantity item, @Nonnull SmartMoveType moveType) {
/*  36 */     this.item = item;
/*  37 */     this.moveType = moveType;
/*     */   }
/*     */   
/*     */   public SmartGiveCreativeItem(@Nonnull SmartGiveCreativeItem other) {
/*  41 */     this.item = other.item;
/*  42 */     this.moveType = other.moveType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SmartGiveCreativeItem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     SmartGiveCreativeItem obj = new SmartGiveCreativeItem();
/*     */     
/*  49 */     obj.moveType = SmartMoveType.fromValue(buf.getByte(offset + 0));
/*     */     
/*  51 */     int pos = offset + 1;
/*  52 */     obj.item = ItemQuantity.deserialize(buf, pos);
/*  53 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*     */     
/*  55 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  59 */     int pos = offset + 1;
/*  60 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  68 */     buf.writeByte(this.moveType.getValue());
/*     */     
/*  70 */     this.item.serialize(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  75 */     int size = 1;
/*  76 */     size += this.item.computeSize();
/*     */     
/*  78 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  82 */     if (buffer.readableBytes() - offset < 1) {
/*  83 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */ 
/*     */     
/*  87 */     int pos = offset + 1;
/*     */     
/*  89 */     ValidationResult itemResult = ItemQuantity.validateStructure(buffer, pos);
/*  90 */     if (!itemResult.isValid()) {
/*  91 */       return ValidationResult.error("Invalid Item: " + itemResult.error());
/*     */     }
/*  93 */     pos += ItemQuantity.computeBytesConsumed(buffer, pos);
/*  94 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SmartGiveCreativeItem clone() {
/*  98 */     SmartGiveCreativeItem copy = new SmartGiveCreativeItem();
/*  99 */     copy.item = this.item.clone();
/* 100 */     copy.moveType = this.moveType;
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SmartGiveCreativeItem other;
/* 107 */     if (this == obj) return true; 
/* 108 */     if (obj instanceof SmartGiveCreativeItem) { other = (SmartGiveCreativeItem)obj; } else { return false; }
/* 109 */      return (Objects.equals(this.item, other.item) && Objects.equals(this.moveType, other.moveType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return Objects.hash(new Object[] { this.item, this.moveType });
/*     */   }
/*     */   
/*     */   public SmartGiveCreativeItem() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\SmartGiveCreativeItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */