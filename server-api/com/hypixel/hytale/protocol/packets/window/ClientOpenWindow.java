/*    */ package com.hypixel.hytale.protocol.packets.window;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientOpenWindow
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 204;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 1;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 1;
/*    */   public static final int MAX_SIZE = 1;
/*    */   
/*    */   public int getId() {
/* 25 */     return 204;
/*    */   }
/*    */   @Nonnull
/* 28 */   public WindowType type = WindowType.Container;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientOpenWindow(@Nonnull WindowType type) {
/* 34 */     this.type = type;
/*    */   }
/*    */   
/*    */   public ClientOpenWindow(@Nonnull ClientOpenWindow other) {
/* 38 */     this.type = other.type;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static ClientOpenWindow deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     ClientOpenWindow obj = new ClientOpenWindow();
/*    */     
/* 45 */     obj.type = WindowType.fromValue(buf.getByte(offset + 0));
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeByte(this.type.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 1;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 1) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public ClientOpenWindow clone() {
/* 77 */     ClientOpenWindow copy = new ClientOpenWindow();
/* 78 */     copy.type = this.type;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     ClientOpenWindow other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof ClientOpenWindow) { other = (ClientOpenWindow)obj; } else { return false; }
/* 87 */      return Objects.equals(this.type, other.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { this.type });
/*    */   }
/*    */   
/*    */   public ClientOpenWindow() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\ClientOpenWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */