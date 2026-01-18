/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SmartMoveType;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SmartMoveItemStack
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 176;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 13;
/*     */   public int fromSectionId;
/*     */   public int fromSlotId;
/*     */   public int quantity;
/*     */   
/*     */   public int getId() {
/*  25 */     return 176;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  31 */   public SmartMoveType moveType = SmartMoveType.EquipOrMergeStack;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SmartMoveItemStack(int fromSectionId, int fromSlotId, int quantity, @Nonnull SmartMoveType moveType) {
/*  37 */     this.fromSectionId = fromSectionId;
/*  38 */     this.fromSlotId = fromSlotId;
/*  39 */     this.quantity = quantity;
/*  40 */     this.moveType = moveType;
/*     */   }
/*     */   
/*     */   public SmartMoveItemStack(@Nonnull SmartMoveItemStack other) {
/*  44 */     this.fromSectionId = other.fromSectionId;
/*  45 */     this.fromSlotId = other.fromSlotId;
/*  46 */     this.quantity = other.quantity;
/*  47 */     this.moveType = other.moveType;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SmartMoveItemStack deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     SmartMoveItemStack obj = new SmartMoveItemStack();
/*     */     
/*  54 */     obj.fromSectionId = buf.getIntLE(offset + 0);
/*  55 */     obj.fromSlotId = buf.getIntLE(offset + 4);
/*  56 */     obj.quantity = buf.getIntLE(offset + 8);
/*  57 */     obj.moveType = SmartMoveType.fromValue(buf.getByte(offset + 12));
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 13;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  70 */     buf.writeIntLE(this.fromSectionId);
/*  71 */     buf.writeIntLE(this.fromSlotId);
/*  72 */     buf.writeIntLE(this.quantity);
/*  73 */     buf.writeByte(this.moveType.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  79 */     return 13;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  83 */     if (buffer.readableBytes() - offset < 13) {
/*  84 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */ 
/*     */     
/*  88 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SmartMoveItemStack clone() {
/*  92 */     SmartMoveItemStack copy = new SmartMoveItemStack();
/*  93 */     copy.fromSectionId = this.fromSectionId;
/*  94 */     copy.fromSlotId = this.fromSlotId;
/*  95 */     copy.quantity = this.quantity;
/*  96 */     copy.moveType = this.moveType;
/*  97 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SmartMoveItemStack other;
/* 103 */     if (this == obj) return true; 
/* 104 */     if (obj instanceof SmartMoveItemStack) { other = (SmartMoveItemStack)obj; } else { return false; }
/* 105 */      return (this.fromSectionId == other.fromSectionId && this.fromSlotId == other.fromSlotId && this.quantity == other.quantity && Objects.equals(this.moveType, other.moveType));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     return Objects.hash(new Object[] { Integer.valueOf(this.fromSectionId), Integer.valueOf(this.fromSlotId), Integer.valueOf(this.quantity), this.moveType });
/*     */   }
/*     */   
/*     */   public SmartMoveItemStack() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\SmartMoveItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */