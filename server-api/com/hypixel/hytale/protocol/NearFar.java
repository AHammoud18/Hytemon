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
/*    */ public class NearFar
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float near;
/*    */   public float far;
/*    */   
/*    */   public NearFar() {}
/*    */   
/*    */   public NearFar(float near, float far) {
/* 27 */     this.near = near;
/* 28 */     this.far = far;
/*    */   }
/*    */   
/*    */   public NearFar(@Nonnull NearFar other) {
/* 32 */     this.near = other.near;
/* 33 */     this.far = other.far;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static NearFar deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     NearFar obj = new NearFar();
/*    */     
/* 40 */     obj.near = buf.getFloatLE(offset + 0);
/* 41 */     obj.far = buf.getFloatLE(offset + 4);
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
/* 53 */     buf.writeFloatLE(this.near);
/* 54 */     buf.writeFloatLE(this.far);
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
/*    */   public NearFar clone() {
/* 73 */     NearFar copy = new NearFar();
/* 74 */     copy.near = this.near;
/* 75 */     copy.far = this.far;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     NearFar other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof NearFar) { other = (NearFar)obj; } else { return false; }
/* 84 */      return (this.near == other.near && this.far == other.far);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Float.valueOf(this.near), Float.valueOf(this.far) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\NearFar.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */