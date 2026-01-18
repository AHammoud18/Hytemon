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
/*    */ public class AngledWielding
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 9;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 9;
/*    */   public static final int MAX_SIZE = 9;
/*    */   public float angleRad;
/*    */   public float angleDistanceRad;
/*    */   public boolean hasModifiers;
/*    */   
/*    */   public AngledWielding() {}
/*    */   
/*    */   public AngledWielding(float angleRad, float angleDistanceRad, boolean hasModifiers) {
/* 28 */     this.angleRad = angleRad;
/* 29 */     this.angleDistanceRad = angleDistanceRad;
/* 30 */     this.hasModifiers = hasModifiers;
/*    */   }
/*    */   
/*    */   public AngledWielding(@Nonnull AngledWielding other) {
/* 34 */     this.angleRad = other.angleRad;
/* 35 */     this.angleDistanceRad = other.angleDistanceRad;
/* 36 */     this.hasModifiers = other.hasModifiers;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AngledWielding deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     AngledWielding obj = new AngledWielding();
/*    */     
/* 43 */     obj.angleRad = buf.getFloatLE(offset + 0);
/* 44 */     obj.angleDistanceRad = buf.getFloatLE(offset + 4);
/* 45 */     obj.hasModifiers = (buf.getByte(offset + 8) != 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 9;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeFloatLE(this.angleRad);
/* 58 */     buf.writeFloatLE(this.angleDistanceRad);
/* 59 */     buf.writeByte(this.hasModifiers ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 9;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 9) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AngledWielding clone() {
/* 78 */     AngledWielding copy = new AngledWielding();
/* 79 */     copy.angleRad = this.angleRad;
/* 80 */     copy.angleDistanceRad = this.angleDistanceRad;
/* 81 */     copy.hasModifiers = this.hasModifiers;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AngledWielding other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof AngledWielding) { other = (AngledWielding)obj; } else { return false; }
/* 90 */      return (this.angleRad == other.angleRad && this.angleDistanceRad == other.angleDistanceRad && this.hasModifiers == other.hasModifiers);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { Float.valueOf(this.angleRad), Float.valueOf(this.angleDistanceRad), Boolean.valueOf(this.hasModifiers) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AngledWielding.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */