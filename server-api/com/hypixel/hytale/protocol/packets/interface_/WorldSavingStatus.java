/*    */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*    */ public class WorldSavingStatus
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 233;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean isWorldSaving;
/*    */   
/*    */   public int getId() {
/* 25 */     return 233;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSavingStatus() {}
/*    */ 
/*    */   
/*    */   public WorldSavingStatus(boolean isWorldSaving) {
/* 34 */     this.isWorldSaving = isWorldSaving;
/*    */   }
/*    */   
/*    */   public WorldSavingStatus(@Nonnull WorldSavingStatus other) {
/* 38 */     this.isWorldSaving = other.isWorldSaving;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static WorldSavingStatus deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     WorldSavingStatus obj = new WorldSavingStatus();
/*    */     
/* 45 */     obj.isWorldSaving = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.isWorldSaving ? 1 : 0);
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
/*    */   public WorldSavingStatus clone() {
/* 77 */     WorldSavingStatus copy = new WorldSavingStatus();
/* 78 */     copy.isWorldSaving = this.isWorldSaving;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     WorldSavingStatus other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof WorldSavingStatus) { other = (WorldSavingStatus)obj; } else { return false; }
/* 87 */      return (this.isWorldSaving == other.isWorldSaving);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.isWorldSaving) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\WorldSavingStatus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */