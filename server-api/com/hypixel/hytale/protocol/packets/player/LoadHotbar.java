/*    */ package com.hypixel.hytale.protocol.packets.player;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadHotbar
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 106;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public byte inventoryRow;
/*    */   
/*    */   public int getId() {
/* 25 */     return 106;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LoadHotbar() {}
/*    */ 
/*    */   
/*    */   public LoadHotbar(byte inventoryRow) {
/* 34 */     this.inventoryRow = inventoryRow;
/*    */   }
/*    */   
/*    */   public LoadHotbar(@Nonnull LoadHotbar other) {
/* 38 */     this.inventoryRow = other.inventoryRow;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static LoadHotbar deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     LoadHotbar obj = new LoadHotbar();
/*    */     
/* 45 */     obj.inventoryRow = buf.getByte(offset + 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeByte(this.inventoryRow);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 1) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public LoadHotbar clone() {
/* 77 */     LoadHotbar copy = new LoadHotbar();
/* 78 */     copy.inventoryRow = this.inventoryRow;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     LoadHotbar other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof LoadHotbar) { other = (LoadHotbar)obj; } else { return false; }
/* 87 */      return (this.inventoryRow == other.inventoryRow);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Byte.valueOf(this.inventoryRow) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\LoadHotbar.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */