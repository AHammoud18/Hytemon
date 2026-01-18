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
/*    */ public class InstantData
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public long seconds;
/*    */   public int nanos;
/*    */   
/*    */   public InstantData() {}
/*    */   
/*    */   public InstantData(long seconds, int nanos) {
/* 27 */     this.seconds = seconds;
/* 28 */     this.nanos = nanos;
/*    */   }
/*    */   
/*    */   public InstantData(@Nonnull InstantData other) {
/* 32 */     this.seconds = other.seconds;
/* 33 */     this.nanos = other.nanos;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static InstantData deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     InstantData obj = new InstantData();
/*    */     
/* 40 */     obj.seconds = buf.getLongLE(offset + 0);
/* 41 */     obj.nanos = buf.getIntLE(offset + 8);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeLongLE(this.seconds);
/* 54 */     buf.writeIntLE(this.nanos);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 12;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 12) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public InstantData clone() {
/* 73 */     InstantData copy = new InstantData();
/* 74 */     copy.seconds = this.seconds;
/* 75 */     copy.nanos = this.nanos;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     InstantData other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof InstantData) { other = (InstantData)obj; } else { return false; }
/* 84 */      return (this.seconds == other.seconds && this.nanos == other.nanos);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Long.valueOf(this.seconds), Integer.valueOf(this.nanos) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InstantData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */