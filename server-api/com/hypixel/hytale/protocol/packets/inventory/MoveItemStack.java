/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class MoveItemStack
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 175;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 20;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 20;
/*     */   public static final int MAX_SIZE = 20;
/*     */   public int fromSectionId;
/*     */   public int fromSlotId;
/*     */   public int quantity;
/*     */   public int toSectionId;
/*     */   public int toSlotId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 175;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveItemStack() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveItemStack(int fromSectionId, int fromSlotId, int quantity, int toSectionId, int toSlotId) {
/*  38 */     this.fromSectionId = fromSectionId;
/*  39 */     this.fromSlotId = fromSlotId;
/*  40 */     this.quantity = quantity;
/*  41 */     this.toSectionId = toSectionId;
/*  42 */     this.toSlotId = toSlotId;
/*     */   }
/*     */   
/*     */   public MoveItemStack(@Nonnull MoveItemStack other) {
/*  46 */     this.fromSectionId = other.fromSectionId;
/*  47 */     this.fromSlotId = other.fromSlotId;
/*  48 */     this.quantity = other.quantity;
/*  49 */     this.toSectionId = other.toSectionId;
/*  50 */     this.toSlotId = other.toSlotId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MoveItemStack deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     MoveItemStack obj = new MoveItemStack();
/*     */     
/*  57 */     obj.fromSectionId = buf.getIntLE(offset + 0);
/*  58 */     obj.fromSlotId = buf.getIntLE(offset + 4);
/*  59 */     obj.quantity = buf.getIntLE(offset + 8);
/*  60 */     obj.toSectionId = buf.getIntLE(offset + 12);
/*  61 */     obj.toSlotId = buf.getIntLE(offset + 16);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     buf.writeIntLE(this.fromSectionId);
/*  75 */     buf.writeIntLE(this.fromSlotId);
/*  76 */     buf.writeIntLE(this.quantity);
/*  77 */     buf.writeIntLE(this.toSectionId);
/*  78 */     buf.writeIntLE(this.toSlotId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     return 20;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  88 */     if (buffer.readableBytes() - offset < 20) {
/*  89 */       return ValidationResult.error("Buffer too small: expected at least 20 bytes");
/*     */     }
/*     */ 
/*     */     
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MoveItemStack clone() {
/*  97 */     MoveItemStack copy = new MoveItemStack();
/*  98 */     copy.fromSectionId = this.fromSectionId;
/*  99 */     copy.fromSlotId = this.fromSlotId;
/* 100 */     copy.quantity = this.quantity;
/* 101 */     copy.toSectionId = this.toSectionId;
/* 102 */     copy.toSlotId = this.toSlotId;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MoveItemStack other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof MoveItemStack) { other = (MoveItemStack)obj; } else { return false; }
/* 111 */      return (this.fromSectionId == other.fromSectionId && this.fromSlotId == other.fromSlotId && this.quantity == other.quantity && this.toSectionId == other.toSectionId && this.toSlotId == other.toSlotId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { Integer.valueOf(this.fromSectionId), Integer.valueOf(this.fromSlotId), Integer.valueOf(this.quantity), Integer.valueOf(this.toSectionId), Integer.valueOf(this.toSlotId) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\MoveItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */