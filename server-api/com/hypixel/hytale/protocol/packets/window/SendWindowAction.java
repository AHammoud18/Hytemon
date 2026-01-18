/*     */ package com.hypixel.hytale.protocol.packets.window;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class SendWindowAction
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 203;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 4;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 4;
/*     */   public static final int MAX_SIZE = 32768027;
/*     */   public int id;
/*     */   @Nonnull
/*     */   public WindowAction action;
/*     */   
/*     */   public int getId() {
/*  25 */     return 203;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SendWindowAction() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SendWindowAction(int id, @Nonnull WindowAction action) {
/*  35 */     this.id = id;
/*  36 */     this.action = action;
/*     */   }
/*     */   
/*     */   public SendWindowAction(@Nonnull SendWindowAction other) {
/*  40 */     this.id = other.id;
/*  41 */     this.action = other.action;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SendWindowAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     SendWindowAction obj = new SendWindowAction();
/*     */     
/*  48 */     obj.id = buf.getIntLE(offset + 0);
/*     */     
/*  50 */     int pos = offset + 4;
/*  51 */     obj.action = WindowAction.deserialize(buf, pos);
/*  52 */     pos += WindowAction.computeBytesConsumed(buf, pos);
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     int pos = offset + 4;
/*  59 */     pos += WindowAction.computeBytesConsumed(buf, pos);
/*  60 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     buf.writeIntLE(this.id);
/*     */     
/*  69 */     this.action.serializeWithTypeId(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 4;
/*  75 */     size += this.action.computeSizeWithTypeId();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 4) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 4 bytes");
/*     */     }
/*     */ 
/*     */     
/*  86 */     int pos = offset + 4;
/*     */     
/*  88 */     ValidationResult actionResult = WindowAction.validateStructure(buffer, pos);
/*  89 */     if (!actionResult.isValid()) {
/*  90 */       return ValidationResult.error("Invalid Action: " + actionResult.error());
/*     */     }
/*  92 */     pos += WindowAction.computeBytesConsumed(buffer, pos);
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SendWindowAction clone() {
/*  97 */     SendWindowAction copy = new SendWindowAction();
/*  98 */     copy.id = this.id;
/*  99 */     copy.action = this.action;
/* 100 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SendWindowAction other;
/* 106 */     if (this == obj) return true; 
/* 107 */     if (obj instanceof SendWindowAction) { other = (SendWindowAction)obj; } else { return false; }
/* 108 */      return (this.id == other.id && Objects.equals(this.action, other.action));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return Objects.hash(new Object[] { Integer.valueOf(this.id), this.action });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\SendWindowAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */