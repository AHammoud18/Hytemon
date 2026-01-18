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
/*    */ public class Rangef
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float min;
/*    */   public float max;
/*    */   
/*    */   public Rangef() {}
/*    */   
/*    */   public Rangef(float min, float max) {
/* 27 */     this.min = min;
/* 28 */     this.max = max;
/*    */   }
/*    */   
/*    */   public Rangef(@Nonnull Rangef other) {
/* 32 */     this.min = other.min;
/* 33 */     this.max = other.max;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Rangef deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Rangef obj = new Rangef();
/*    */     
/* 40 */     obj.min = buf.getFloatLE(offset + 0);
/* 41 */     obj.max = buf.getFloatLE(offset + 4);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeFloatLE(this.min);
/* 54 */     buf.writeFloatLE(this.max);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 8;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 8) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Rangef clone() {
/* 73 */     Rangef copy = new Rangef();
/* 74 */     copy.min = this.min;
/* 75 */     copy.max = this.max;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Rangef other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Rangef) { other = (Rangef)obj; } else { return false; }
/* 84 */      return (this.min == other.min && this.max == other.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Float.valueOf(this.min), Float.valueOf(this.max) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Rangef.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */