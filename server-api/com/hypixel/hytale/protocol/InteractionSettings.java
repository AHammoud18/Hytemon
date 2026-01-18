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
/*    */ public class InteractionSettings
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   public boolean allowSkipOnClick;
/*    */   
/*    */   public InteractionSettings() {}
/*    */   
/*    */   public InteractionSettings(boolean allowSkipOnClick) {
/* 26 */     this.allowSkipOnClick = allowSkipOnClick;
/*    */   }
/*    */   
/*    */   public InteractionSettings(@Nonnull InteractionSettings other) {
/* 30 */     this.allowSkipOnClick = other.allowSkipOnClick;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static InteractionSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 35 */     InteractionSettings obj = new InteractionSettings();
/*    */     
/* 37 */     obj.allowSkipOnClick = (buf.getByte(offset + 0) != 0);
/*    */ 
/*    */     
/* 40 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 44 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 49 */     buf.writeByte(this.allowSkipOnClick ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 55 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 59 */     if (buffer.readableBytes() - offset < 1) {
/* 60 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 64 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public InteractionSettings clone() {
/* 68 */     InteractionSettings copy = new InteractionSettings();
/* 69 */     copy.allowSkipOnClick = this.allowSkipOnClick;
/* 70 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     InteractionSettings other;
/* 76 */     if (this == obj) return true; 
/* 77 */     if (obj instanceof InteractionSettings) { other = (InteractionSettings)obj; } else { return false; }
/* 78 */      return (this.allowSkipOnClick == other.allowSkipOnClick);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     return Objects.hash(new Object[] { Boolean.valueOf(this.allowSkipOnClick) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */