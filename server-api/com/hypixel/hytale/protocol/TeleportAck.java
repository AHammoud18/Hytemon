/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeleportAck
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public byte teleportId;
/*    */   
/*    */   public TeleportAck() {}
/*    */   
/*    */   public TeleportAck(byte teleportId) {
/* 26 */     this.teleportId = teleportId;
/*    */   }
/*    */   
/*    */   public TeleportAck(@Nonnull TeleportAck other) {
/* 30 */     this.teleportId = other.teleportId;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TeleportAck deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     TeleportAck obj = new TeleportAck();
/*    */     
/* 37 */     obj.teleportId = buf.getByte(offset + 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 49 */     buf.writeByte(this.teleportId);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 55 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 59 */     if (buffer.readableBytes() - offset < 1) {
/* 60 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 64 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public TeleportAck clone() {
/* 68 */     TeleportAck copy = new TeleportAck();
/* 69 */     copy.teleportId = this.teleportId;
/* 70 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     TeleportAck other;
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj instanceof TeleportAck) { other = (TeleportAck)obj; } else { return false; }
/* 78 */      return (this.teleportId == other.teleportId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return Objects.hash(new Object[] { Byte.valueOf(this.teleportId) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\TeleportAck.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */