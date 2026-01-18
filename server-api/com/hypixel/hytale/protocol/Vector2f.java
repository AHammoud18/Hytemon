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
/*    */ public class Vector2f
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public float x;
/*    */   public float y;
/*    */   
/*    */   public Vector2f() {}
/*    */   
/*    */   public Vector2f(float x, float y) {
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/*    */   }
/*    */   
/*    */   public Vector2f(@Nonnull Vector2f other) {
/* 32 */     this.x = other.x;
/* 33 */     this.y = other.y;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector2f deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Vector2f obj = new Vector2f();
/*    */     
/* 40 */     obj.x = buf.getFloatLE(offset + 0);
/* 41 */     obj.y = buf.getFloatLE(offset + 4);
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
/* 53 */     buf.writeFloatLE(this.x);
/* 54 */     buf.writeFloatLE(this.y);
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
/*    */   public Vector2f clone() {
/* 73 */     Vector2f copy = new Vector2f();
/* 74 */     copy.x = this.x;
/* 75 */     copy.y = this.y;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Vector2f other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Vector2f) { other = (Vector2f)obj; } else { return false; }
/* 84 */      return (this.x == other.x && this.y == other.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Float.valueOf(this.x), Float.valueOf(this.y) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Vector2f.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */