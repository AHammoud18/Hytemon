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
/*    */ public class MouseButtonEvent
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 3;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 3;
/*    */   public static final int MAX_SIZE = 3;
/*    */   @Nonnull
/* 20 */   public MouseButtonType mouseButtonType = MouseButtonType.Left; @Nonnull
/* 21 */   public MouseButtonState state = MouseButtonState.Pressed;
/*    */ 
/*    */   
/*    */   public byte clicks;
/*    */ 
/*    */   
/*    */   public MouseButtonEvent(@Nonnull MouseButtonType mouseButtonType, @Nonnull MouseButtonState state, byte clicks) {
/* 28 */     this.mouseButtonType = mouseButtonType;
/* 29 */     this.state = state;
/* 30 */     this.clicks = clicks;
/*    */   }
/*    */   
/*    */   public MouseButtonEvent(@Nonnull MouseButtonEvent other) {
/* 34 */     this.mouseButtonType = other.mouseButtonType;
/* 35 */     this.state = other.state;
/* 36 */     this.clicks = other.clicks;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static MouseButtonEvent deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     MouseButtonEvent obj = new MouseButtonEvent();
/*    */     
/* 43 */     obj.mouseButtonType = MouseButtonType.fromValue(buf.getByte(offset + 0));
/* 44 */     obj.state = MouseButtonState.fromValue(buf.getByte(offset + 1));
/* 45 */     obj.clicks = buf.getByte(offset + 2);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 57 */     buf.writeByte(this.mouseButtonType.getValue());
/* 58 */     buf.writeByte(this.state.getValue());
/* 59 */     buf.writeByte(this.clicks);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 65 */     return 3;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 69 */     if (buffer.readableBytes() - offset < 3) {
/* 70 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*    */     }
/*    */ 
/*    */     
/* 74 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public MouseButtonEvent clone() {
/* 78 */     MouseButtonEvent copy = new MouseButtonEvent();
/* 79 */     copy.mouseButtonType = this.mouseButtonType;
/* 80 */     copy.state = this.state;
/* 81 */     copy.clicks = this.clicks;
/* 82 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     MouseButtonEvent other;
/* 88 */     if (this == obj) return true; 
/* 89 */     if (obj instanceof MouseButtonEvent) { other = (MouseButtonEvent)obj; } else { return false; }
/* 90 */      return (Objects.equals(this.mouseButtonType, other.mouseButtonType) && Objects.equals(this.state, other.state) && this.clicks == other.clicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return Objects.hash(new Object[] { this.mouseButtonType, this.state, Byte.valueOf(this.clicks) });
/*    */   }
/*    */   
/*    */   public MouseButtonEvent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MouseButtonEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */