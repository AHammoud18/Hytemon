/*    */ package com.hypixel.hytale.protocol.packets.camera;
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
/*    */ public class SetFlyCameraMode
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 283;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean entering;
/*    */   
/*    */   public int getId() {
/* 25 */     return 283;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SetFlyCameraMode() {}
/*    */ 
/*    */   
/*    */   public SetFlyCameraMode(boolean entering) {
/* 34 */     this.entering = entering;
/*    */   }
/*    */   
/*    */   public SetFlyCameraMode(@Nonnull SetFlyCameraMode other) {
/* 38 */     this.entering = other.entering;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static SetFlyCameraMode deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     SetFlyCameraMode obj = new SetFlyCameraMode();
/*    */     
/* 45 */     obj.entering = (buf.getByte(offset + 0) != 0);
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
/* 58 */     buf.writeByte(this.entering ? 1 : 0);
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
/*    */   public SetFlyCameraMode clone() {
/* 77 */     SetFlyCameraMode copy = new SetFlyCameraMode();
/* 78 */     copy.entering = this.entering;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     SetFlyCameraMode other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof SetFlyCameraMode) { other = (SetFlyCameraMode)obj; } else { return false; }
/* 87 */      return (this.entering == other.entering);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Boolean.valueOf(this.entering) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\camera\SetFlyCameraMode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */