/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class DropItemStack
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 174;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 12;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 12;
/*     */   public int inventorySectionId;
/*     */   public int slotId;
/*     */   public int quantity;
/*     */   
/*     */   public int getId() {
/*  25 */     return 174;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropItemStack() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public DropItemStack(int inventorySectionId, int slotId, int quantity) {
/*  36 */     this.inventorySectionId = inventorySectionId;
/*  37 */     this.slotId = slotId;
/*  38 */     this.quantity = quantity;
/*     */   }
/*     */   
/*     */   public DropItemStack(@Nonnull DropItemStack other) {
/*  42 */     this.inventorySectionId = other.inventorySectionId;
/*  43 */     this.slotId = other.slotId;
/*  44 */     this.quantity = other.quantity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static DropItemStack deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     DropItemStack obj = new DropItemStack();
/*     */     
/*  51 */     obj.inventorySectionId = buf.getIntLE(offset + 0);
/*  52 */     obj.slotId = buf.getIntLE(offset + 4);
/*  53 */     obj.quantity = buf.getIntLE(offset + 8);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     buf.writeIntLE(this.inventorySectionId);
/*  67 */     buf.writeIntLE(this.slotId);
/*  68 */     buf.writeIntLE(this.quantity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 12;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 12) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public DropItemStack clone() {
/*  87 */     DropItemStack copy = new DropItemStack();
/*  88 */     copy.inventorySectionId = this.inventorySectionId;
/*  89 */     copy.slotId = this.slotId;
/*  90 */     copy.quantity = this.quantity;
/*  91 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     DropItemStack other;
/*  97 */     if (this == obj) return true; 
/*  98 */     if (obj instanceof DropItemStack) { other = (DropItemStack)obj; } else { return false; }
/*  99 */      return (this.inventorySectionId == other.inventorySectionId && this.slotId == other.slotId && this.quantity == other.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return Objects.hash(new Object[] { Integer.valueOf(this.inventorySectionId), Integer.valueOf(this.slotId), Integer.valueOf(this.quantity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\DropItemStack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */