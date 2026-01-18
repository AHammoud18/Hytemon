/*    */ package com.hypixel.hytale.protocol.packets.inventory;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetActiveSlot
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 177;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int inventorySectionId;
/*    */   public int activeSlot;
/*    */   
/*    */   public int getId() {
/* 25 */     return 177;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetActiveSlot() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public SetActiveSlot(int inventorySectionId, int activeSlot) {
/* 35 */     this.inventorySectionId = inventorySectionId;
/* 36 */     this.activeSlot = activeSlot;
/*    */   }
/*    */   
/*    */   public SetActiveSlot(@Nonnull SetActiveSlot other) {
/* 40 */     this.inventorySectionId = other.inventorySectionId;
/* 41 */     this.activeSlot = other.activeSlot;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetActiveSlot deserialize(@Nonnull ByteBuf buf, int offset) {
/* 46 */     SetActiveSlot obj = new SetActiveSlot();
/*    */     
/* 48 */     obj.inventorySectionId = buf.getIntLE(offset + 0);
/* 49 */     obj.activeSlot = buf.getIntLE(offset + 4);
/*    */ 
/*    */     
/* 52 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 56 */     return 8;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 62 */     buf.writeIntLE(this.inventorySectionId);
/* 63 */     buf.writeIntLE(this.activeSlot);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 8) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public SetActiveSlot clone() {
/* 82 */     SetActiveSlot copy = new SetActiveSlot();
/* 83 */     copy.inventorySectionId = this.inventorySectionId;
/* 84 */     copy.activeSlot = this.activeSlot;
/* 85 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetActiveSlot other;
/* 91 */     if (this == obj) return true; 
/* 92 */     if (obj instanceof SetActiveSlot) { other = (SetActiveSlot)obj; } else { return false; }
/* 93 */      return (this.inventorySectionId == other.inventorySectionId && this.activeSlot == other.activeSlot);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 98 */     return Objects.hash(new Object[] { Integer.valueOf(this.inventorySectionId), Integer.valueOf(this.activeSlot) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\inventory\SetActiveSlot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */