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
/*    */ public class RailPoint
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 25;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 25;
/*    */   public static final int MAX_SIZE = 25;
/*    */   @Nullable
/*    */   public Vector3f point;
/*    */   @Nullable
/*    */   public Vector3f normal;
/*    */   
/*    */   public RailPoint() {}
/*    */   
/*    */   public RailPoint(@Nullable Vector3f point, @Nullable Vector3f normal) {
/* 27 */     this.point = point;
/* 28 */     this.normal = normal;
/*    */   }
/*    */   
/*    */   public RailPoint(@Nonnull RailPoint other) {
/* 32 */     this.point = other.point;
/* 33 */     this.normal = other.normal;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static RailPoint deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     RailPoint obj = new RailPoint();
/* 39 */     byte nullBits = buf.getByte(offset);
/* 40 */     if ((nullBits & 0x1) != 0) obj.point = Vector3f.deserialize(buf, offset + 1); 
/* 41 */     if ((nullBits & 0x2) != 0) obj.normal = Vector3f.deserialize(buf, offset + 13);
/*    */ 
/*    */     
/* 44 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 48 */     return 25;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 52 */     byte nullBits = 0;
/* 53 */     if (this.point != null) nullBits = (byte)(nullBits | 0x1); 
/* 54 */     if (this.normal != null) nullBits = (byte)(nullBits | 0x2); 
/* 55 */     buf.writeByte(nullBits);
/*    */     
/* 57 */     if (this.point != null) { this.point.serialize(buf); } else { buf.writeZero(12); }
/* 58 */      if (this.normal != null) { this.normal.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 25;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 25) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 25 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public RailPoint clone() {
/* 77 */     RailPoint copy = new RailPoint();
/* 78 */     copy.point = (this.point != null) ? this.point.clone() : null;
/* 79 */     copy.normal = (this.normal != null) ? this.normal.clone() : null;
/* 80 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RailPoint other;
/* 86 */     if (this == obj) return true; 
/* 87 */     if (obj instanceof RailPoint) { other = (RailPoint)obj; } else { return false; }
/* 88 */      return (Objects.equals(this.point, other.point) && Objects.equals(this.normal, other.normal));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 93 */     return Objects.hash(new Object[] { this.point, this.normal });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RailPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */