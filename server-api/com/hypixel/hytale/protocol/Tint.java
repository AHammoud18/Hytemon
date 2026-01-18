/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tint
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 24;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 24;
/*     */   public static final int MAX_SIZE = 24;
/*     */   public int top;
/*     */   public int bottom;
/*     */   public int front;
/*     */   public int back;
/*     */   public int left;
/*     */   public int right;
/*     */   
/*     */   public Tint() {}
/*     */   
/*     */   public Tint(int top, int bottom, int front, int back, int left, int right) {
/*  31 */     this.top = top;
/*  32 */     this.bottom = bottom;
/*  33 */     this.front = front;
/*  34 */     this.back = back;
/*  35 */     this.left = left;
/*  36 */     this.right = right;
/*     */   }
/*     */   
/*     */   public Tint(@Nonnull Tint other) {
/*  40 */     this.top = other.top;
/*  41 */     this.bottom = other.bottom;
/*  42 */     this.front = other.front;
/*  43 */     this.back = other.back;
/*  44 */     this.left = other.left;
/*  45 */     this.right = other.right;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Tint deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     Tint obj = new Tint();
/*     */     
/*  52 */     obj.top = buf.getIntLE(offset + 0);
/*  53 */     obj.bottom = buf.getIntLE(offset + 4);
/*  54 */     obj.front = buf.getIntLE(offset + 8);
/*  55 */     obj.back = buf.getIntLE(offset + 12);
/*  56 */     obj.left = buf.getIntLE(offset + 16);
/*  57 */     obj.right = buf.getIntLE(offset + 20);
/*     */ 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     return 24;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     buf.writeIntLE(this.top);
/*  70 */     buf.writeIntLE(this.bottom);
/*  71 */     buf.writeIntLE(this.front);
/*  72 */     buf.writeIntLE(this.back);
/*  73 */     buf.writeIntLE(this.left);
/*  74 */     buf.writeIntLE(this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  80 */     return 24;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 24) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 24 bytes");
/*     */     }
/*     */ 
/*     */     
/*  89 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Tint clone() {
/*  93 */     Tint copy = new Tint();
/*  94 */     copy.top = this.top;
/*  95 */     copy.bottom = this.bottom;
/*  96 */     copy.front = this.front;
/*  97 */     copy.back = this.back;
/*  98 */     copy.left = this.left;
/*  99 */     copy.right = this.right;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Tint other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof Tint) { other = (Tint)obj; } else { return false; }
/* 108 */      return (this.top == other.top && this.bottom == other.bottom && this.front == other.front && this.back == other.back && this.left == other.left && this.right == other.right);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Integer.valueOf(this.top), Integer.valueOf(this.bottom), Integer.valueOf(this.front), Integer.valueOf(this.back), Integer.valueOf(this.left), Integer.valueOf(this.right) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Tint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */