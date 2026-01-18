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
/*    */ public class Rangeb
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   public byte min;
/*    */   public byte max;
/*    */   
/*    */   public Rangeb() {}
/*    */   
/*    */   public Rangeb(byte min, byte max) {
/* 27 */     this.min = min;
/* 28 */     this.max = max;
/*    */   }
/*    */   
/*    */   public Rangeb(@Nonnull Rangeb other) {
/* 32 */     this.min = other.min;
/* 33 */     this.max = other.max;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Rangeb deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Rangeb obj = new Rangeb();
/*    */     
/* 40 */     obj.min = buf.getByte(offset + 0);
/* 41 */     obj.max = buf.getByte(offset + 1);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 53 */     buf.writeByte(this.min);
/* 54 */     buf.writeByte(this.max);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 60 */     return 2;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 64 */     if (buffer.readableBytes() - offset < 2) {
/* 65 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Rangeb clone() {
/* 73 */     Rangeb copy = new Rangeb();
/* 74 */     copy.min = this.min;
/* 75 */     copy.max = this.max;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Rangeb other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof Rangeb) { other = (Rangeb)obj; } else { return false; }
/* 84 */      return (this.min == other.min && this.max == other.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { Byte.valueOf(this.min), Byte.valueOf(this.max) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Rangeb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */