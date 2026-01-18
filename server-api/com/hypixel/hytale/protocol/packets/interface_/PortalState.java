/*    */ package com.hypixel.hytale.protocol.packets.interface_;
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
/*    */ public class PortalState
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 5;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 5;
/*    */   public static final int MAX_SIZE = 5;
/*    */   public int remainingSeconds;
/*    */   public boolean breaching;
/*    */   
/*    */   public PortalState() {}
/*    */   
/*    */   public PortalState(int remainingSeconds, boolean breaching) {
/* 27 */     this.remainingSeconds = remainingSeconds;
/* 28 */     this.breaching = breaching;
/*    */   }
/*    */   
/*    */   public PortalState(@Nonnull PortalState other) {
/* 32 */     this.remainingSeconds = other.remainingSeconds;
/* 33 */     this.breaching = other.breaching;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static PortalState deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     PortalState obj = new PortalState();
/*    */     
/* 40 */     obj.remainingSeconds = buf.getIntLE(offset + 0);
/* 41 */     obj.breaching = (buf.getByte(offset + 4) != 0);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeIntLE(this.remainingSeconds);
/* 54 */     buf.writeByte(this.breaching ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 5;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 5) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public PortalState clone() {
/* 73 */     PortalState copy = new PortalState();
/* 74 */     copy.remainingSeconds = this.remainingSeconds;
/* 75 */     copy.breaching = this.breaching;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     PortalState other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof PortalState) { other = (PortalState)obj; } else { return false; }
/* 84 */      return (this.remainingSeconds == other.remainingSeconds && this.breaching == other.breaching);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Integer.valueOf(this.remainingSeconds), Boolean.valueOf(this.breaching) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\PortalState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */