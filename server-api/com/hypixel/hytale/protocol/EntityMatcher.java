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
/*    */ public class EntityMatcher
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 2;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 2;
/*    */   public static final int MAX_SIZE = 2;
/*    */   @Nonnull
/* 20 */   public EntityMatcherType type = EntityMatcherType.Server;
/*    */ 
/*    */   
/*    */   public boolean invert;
/*    */ 
/*    */   
/*    */   public EntityMatcher(@Nonnull EntityMatcherType type, boolean invert) {
/* 27 */     this.type = type;
/* 28 */     this.invert = invert;
/*    */   }
/*    */   
/*    */   public EntityMatcher(@Nonnull EntityMatcher other) {
/* 32 */     this.type = other.type;
/* 33 */     this.invert = other.invert;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static EntityMatcher deserialize(@Nonnull ByteBuf buf, int offset) {
/* 38 */     EntityMatcher obj = new EntityMatcher();
/*    */     
/* 40 */     obj.type = EntityMatcherType.fromValue(buf.getByte(offset + 0));
/* 41 */     obj.invert = (buf.getByte(offset + 1) != 0);
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
/* 53 */     buf.writeByte(this.type.getValue());
/* 54 */     buf.writeByte(this.invert ? 1 : 0);
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
/*    */   public EntityMatcher clone() {
/* 73 */     EntityMatcher copy = new EntityMatcher();
/* 74 */     copy.type = this.type;
/* 75 */     copy.invert = this.invert;
/* 76 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     EntityMatcher other;
/* 82 */     if (this == obj) return true; 
/* 83 */     if (obj instanceof EntityMatcher) { other = (EntityMatcher)obj; } else { return false; }
/* 84 */      return (Objects.equals(this.type, other.type) && this.invert == other.invert);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Objects.hash(new Object[] { this.type, Boolean.valueOf(this.invert) });
/*    */   }
/*    */   
/*    */   public EntityMatcher() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */