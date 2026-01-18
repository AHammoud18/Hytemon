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
/*    */ public class Vector3f
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 12;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 12;
/*    */   public static final int MAX_SIZE = 12;
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public Vector3f() {}
/*    */   
/*    */   public Vector3f(float x, float y, float z) {
/* 28 */     this.x = x;
/* 29 */     this.y = y;
/* 30 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Vector3f(@Nonnull Vector3f other) {
/* 34 */     this.x = other.x;
/* 35 */     this.y = other.y;
/* 36 */     this.z = other.z;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector3f deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     Vector3f obj = new Vector3f();
/*    */     
/* 43 */     obj.x = buf.getFloatLE(offset + 0);
/* 44 */     obj.y = buf.getFloatLE(offset + 4);
/* 45 */     obj.z = buf.getFloatLE(offset + 8);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeFloatLE(this.x);
/* 58 */     buf.writeFloatLE(this.y);
/* 59 */     buf.writeFloatLE(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 12;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 12) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Vector3f clone() {
/* 78 */     Vector3f copy = new Vector3f();
/* 79 */     copy.x = this.x;
/* 80 */     copy.y = this.y;
/* 81 */     copy.z = this.z;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Vector3f other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof Vector3f) { other = (Vector3f)obj; } else { return false; }
/* 90 */      return (this.x == other.x && this.y == other.y && this.z == other.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Vector3f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */