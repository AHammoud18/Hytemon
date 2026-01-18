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
/*    */ public class CloseWindow
/*    */   implements Packet
/*    */ {
/*    */   public static final int PACKET_ID = 202;
/*    */   public static final boolean IS_COMPRESSED = false;
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*    */   public static final int FIXED_BLOCK_SIZE = 4;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 4;
/*    */   public static final int MAX_SIZE = 4;
/*    */   public int id;
/*    */   
/*    */   public int getId() {
/* 25 */     return 202;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CloseWindow() {}
/*    */ 
/*    */   
/*    */   public CloseWindow(int id) {
/* 34 */     this.id = id;
/*    */   }
/*    */   
/*    */   public CloseWindow(@Nonnull CloseWindow other) {
/* 38 */     this.id = other.id;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static CloseWindow deserialize(@Nonnull ByteBuf buf, int offset) {
/* 43 */     CloseWindow obj = new CloseWindow();
/*    */     
/* 45 */     obj.id = buf.getIntLE(offset + 0);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 58 */     buf.writeIntLE(this.id);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 64 */     return 4;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 68 */     if (buffer.readableBytes() - offset < 4) {
/* 69 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*    */     }
/*    */ 
/*    */     
/* 73 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public CloseWindow clone() {
/* 77 */     CloseWindow copy = new CloseWindow();
/* 78 */     copy.id = this.id;
/* 79 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     CloseWindow other;
/* 85 */     if (this == obj) return true; 
/* 86 */     if (obj instanceof CloseWindow) { other = (CloseWindow)obj; } else { return false; }
/* 87 */      return (this.id == other.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return Objects.hash(new Object[] { Integer.valueOf(this.id) });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\CloseWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */