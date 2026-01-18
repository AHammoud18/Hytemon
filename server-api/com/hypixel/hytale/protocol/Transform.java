/*    */ package com.hypixel.hytale.protocol;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Transform
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 37;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 37;
/*    */   public static final int MAX_SIZE = 37;
/*    */   @Nullable
/*    */   public Position position;
/*    */   @Nullable
/*    */   public Direction orientation;
/*    */   
/*    */   public Transform() {}
/*    */   
/*    */   public Transform(@Nullable Position position, @Nullable Direction orientation) {
/* 27 */     this.position = position;
/* 28 */     this.orientation = orientation;
/*    */   }
/*    */   
/*    */   public Transform(@Nonnull Transform other) {
/* 32 */     this.position = other.position;
/* 33 */     this.orientation = other.orientation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Transform deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     Transform obj = new Transform();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     if ((nullBits & 0x1) != 0) obj.position = Position.deserialize(buf, offset + 1); 
/* 41 */     if ((nullBits & 0x2) != 0) obj.orientation = Direction.deserialize(buf, offset + 25);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 37;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     if (this.orientation != null) nullBits = (byte)(nullBits | 0x2); 
/* 55 */     buf.writeByte(nullBits);
/*    */     
/* 57 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/* 58 */      if (this.orientation != null) { this.orientation.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 37;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 37) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 37 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public Transform clone() {
/* 77 */     Transform copy = new Transform();
/* 78 */     copy.position = (this.position != null) ? this.position.clone() : null;
/* 79 */     copy.orientation = (this.orientation != null) ? this.orientation.clone() : null;
/* 80 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     Transform other;
/* 86 */     if (this == obj) return true; 
/* 87 */     if (obj instanceof Transform) { other = (Transform)obj; } else { return false; }
/* 88 */      return (Objects.equals(this.position, other.position) && Objects.equals(this.orientation, other.orientation));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 93 */     return Objects.hash(new Object[] { this.position, this.orientation });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Transform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */