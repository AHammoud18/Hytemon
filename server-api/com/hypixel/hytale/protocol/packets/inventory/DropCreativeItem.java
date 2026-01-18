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
/*     */ 
/*     */ 
/*     */ public class DropCreativeItem
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 172;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 0;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 0;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   
/*     */   public int getId() {
/*  25 */     return 172;
/*     */   }
/*     */   @Nonnull
/*  28 */   public ItemQuantity item = new ItemQuantity();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropCreativeItem(@Nonnull ItemQuantity item) {
/*  34 */     this.item = item;
/*     */   }
/*     */   
/*     */   public DropCreativeItem(@Nonnull DropCreativeItem other) {
/*  38 */     this.item = other.item;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DropCreativeItem deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     DropCreativeItem obj = new DropCreativeItem();
/*     */ 
/*     */     
/*  46 */     int pos = offset + 0;
/*  47 */     obj.item = ItemQuantity.deserialize(buf, pos);
/*  48 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     int pos = offset + 0;
/*  55 */     pos += ItemQuantity.computeBytesConsumed(buf, pos);
/*  56 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  64 */     this.item.serialize(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  69 */     int size = 0;
/*  70 */     size += this.item.computeSize();
/*     */     
/*  72 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 0) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 0 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     int pos = offset + 0;
/*     */     
/*  83 */     ValidationResult itemResult = ItemQuantity.validateStructure(buffer, pos);
/*  84 */     if (!itemResult.isValid()) {
/*  85 */       return ValidationResult.error("Invalid Item: " + itemResult.error());
/*     */     }
/*  87 */     pos += ItemQuantity.computeBytesConsumed(buffer, pos);
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DropCreativeItem clone() {
/*  92 */     DropCreativeItem copy = new DropCreativeItem();
/*  93 */     copy.item = this.item.clone();
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DropCreativeItem other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof DropCreativeItem) { other = (DropCreativeItem)obj; } else { return false; }
/* 102 */      return Objects.equals(this.item, other.item);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { this.item });
/*     */   }
/*     */   
/*     */   public DropCreativeItem() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\DropCreativeItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */