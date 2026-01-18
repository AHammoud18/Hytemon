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
/*    */ public class Vector2i
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 8;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 8;
/*    */   public static final int MAX_SIZE = 8;
/*    */   public int x;
/*    */   public int y;
/*    */   
/*    */   public Vector2i() {}
/*    */   
/*    */   public Vector2i(int x, int y) {
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/*    */   }
/*    */   
/*    */   public Vector2i(@Nonnull Vector2i other) {
/* 32 */     this.x = other.x;
/* 33 */     this.y = other.y;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Vector2i deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Vector2i obj = new Vector2i();
/*    */     
/* 40 */     obj.x = buf.getIntLE(offset + 0);
/* 41 */     obj.y = buf.getIntLE(offset + 4);
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
/* 53 */     buf.writeIntLE(this.x);
/* 54 */     buf.writeIntLE(this.y);
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
/*    */   public Vector2i clone() {
/* 73 */     Vector2i copy = new Vector2i();
/* 74 */     copy.x = this.x;
/* 75 */     copy.y = this.y;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Vector2i other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Vector2i) { other = (Vector2i)obj; } else { return false; }
/* 84 */      return (this.x == other.x && this.y == other.y);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Vector2i.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */