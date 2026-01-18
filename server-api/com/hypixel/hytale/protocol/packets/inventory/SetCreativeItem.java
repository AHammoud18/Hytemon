/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ItemQuantity;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class SetCreativeItem
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 171;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384019;
/*     */   public int inventorySectionId;
/*     */   public int slotId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 171;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  30 */   public ItemQuantity item = new ItemQuantity();
/*     */ 
/*     */   
/*     */   public boolean override;
/*     */ 
/*     */   
/*     */   public SetCreativeItem(int inventorySectionId, int slotId, @Nonnull ItemQuantity item, boolean override) {
/*  37 */     this.inventorySectionId = inventorySectionId;
/*  38 */     this.slotId = slotId;
/*  39 */     this.item = item;
/*  40 */     this.override = override;
/*     */   }
/*     */   
/*     */   public SetCreativeItem(@Nonnull SetCreativeItem other) {
/*  44 */     this.inventorySectionId = other.inventorySectionId;
/*  45 */     this.slotId = other.slotId;
/*  46 */     this.item = other.item;
/*  47 */     this.override = other.override;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetCreativeItem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     SetCreativeItem obj = new SetCreativeItem();
/*     */     
/*  54 */     obj.inventorySectionId = buf.getIntLE(offset + 0);
/*  55 */     obj.slotId = buf.getIntLE(offset + 4);
/*  56 */     obj.override = (buf.getByte(offset + 8) != 0);
/*     */     
/*  58 */     int pos = offset + 9;
/*  59 */     obj.item = ItemQuantity.deserialize(buf, pos);
/*  60 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     int pos = offset + 9;
/*  67 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*  68 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  75 */     buf.writeIntLE(this.inventorySectionId);
/*  76 */     buf.writeIntLE(this.slotId);
/*  77 */     buf.writeByte(this.override ? 1 : 0);
/*     */     
/*  79 */     this.item.serialize(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 9;
/*  85 */     size += this.item.computeSize();
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 9) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */ 
/*     */     
/*  96 */     int pos = offset + 9;
/*     */     
/*  98 */     ValidationResult itemResult = ItemQuantity.validateStructure(buffer, pos);
/*  99 */     if (!itemResult.isValid()) {
/* 100 */       return ValidationResult.error("Invalid Item: " + itemResult.error());
/*     */     }
/* 102 */     pos += ItemQuantity.computeBytesConsumed(buffer, pos);
/* 103 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetCreativeItem clone() {
/* 107 */     SetCreativeItem copy = new SetCreativeItem();
/* 108 */     copy.inventorySectionId = this.inventorySectionId;
/* 109 */     copy.slotId = this.slotId;
/* 110 */     copy.item = this.item.clone();
/* 111 */     copy.override = this.override;
/* 112 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetCreativeItem other;
/* 118 */     if (this == obj) return true; 
/* 119 */     if (obj instanceof SetCreativeItem) { other = (SetCreativeItem)obj; } else { return false; }
/* 120 */      return (this.inventorySectionId == other.inventorySectionId && this.slotId == other.slotId && Objects.equals(this.item, other.item) && this.override == other.override);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 125 */     return Objects.hash(new Object[] { Integer.valueOf(this.inventorySectionId), Integer.valueOf(this.slotId), this.item, Boolean.valueOf(this.override) });
/*     */   }
/*     */   
/*     */   public SetCreativeItem() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\SetCreativeItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */