/*     */ package com.hypixel.hytale.protocol.packets.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InventoryActionType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryAction
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 179;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 6;
/*     */   public int inventorySectionId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 179;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  29 */   public InventoryActionType inventoryActionType = InventoryActionType.TakeAll;
/*     */ 
/*     */   
/*     */   public byte actionData;
/*     */ 
/*     */   
/*     */   public InventoryAction(int inventorySectionId, @Nonnull InventoryActionType inventoryActionType, byte actionData) {
/*  36 */     this.inventorySectionId = inventorySectionId;
/*  37 */     this.inventoryActionType = inventoryActionType;
/*  38 */     this.actionData = actionData;
/*     */   }
/*     */   
/*     */   public InventoryAction(@Nonnull InventoryAction other) {
/*  42 */     this.inventorySectionId = other.inventorySectionId;
/*  43 */     this.inventoryActionType = other.inventoryActionType;
/*  44 */     this.actionData = other.actionData;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InventoryAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     InventoryAction obj = new InventoryAction();
/*     */     
/*  51 */     obj.inventorySectionId = buf.getIntLE(offset + 0);
/*  52 */     obj.inventoryActionType = InventoryActionType.fromValue(buf.getByte(offset + 4));
/*  53 */     obj.actionData = buf.getByte(offset + 5);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     buf.writeIntLE(this.inventorySectionId);
/*  67 */     buf.writeByte(this.inventoryActionType.getValue());
/*  68 */     buf.writeByte(this.actionData);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 6;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 6) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InventoryAction clone() {
/*  87 */     InventoryAction copy = new InventoryAction();
/*  88 */     copy.inventorySectionId = this.inventorySectionId;
/*  89 */     copy.inventoryActionType = this.inventoryActionType;
/*  90 */     copy.actionData = this.actionData;
/*  91 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InventoryAction other;
/*  97 */     if (this == obj) return true; 
/*  98 */     if (obj instanceof InventoryAction) { other = (InventoryAction)obj; } else { return false; }
/*  99 */      return (this.inventorySectionId == other.inventorySectionId && Objects.equals(this.inventoryActionType, other.inventoryActionType) && this.actionData == other.actionData);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return Objects.hash(new Object[] { Integer.valueOf(this.inventorySectionId), this.inventoryActionType, Byte.valueOf(this.actionData) });
/*     */   }
/*     */   
/*     */   public InventoryAction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\InventoryAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */