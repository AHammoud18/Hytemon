/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class ObjectiveTask
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384014;
/*     */   @Nullable
/*     */   public String taskDescriptionKey;
/*     */   public int currentCompletion;
/*     */   public int completionNeeded;
/*     */   
/*     */   public ObjectiveTask() {}
/*     */   
/*     */   public ObjectiveTask(@Nullable String taskDescriptionKey, int currentCompletion, int completionNeeded) {
/*  28 */     this.taskDescriptionKey = taskDescriptionKey;
/*  29 */     this.currentCompletion = currentCompletion;
/*  30 */     this.completionNeeded = completionNeeded;
/*     */   }
/*     */   
/*     */   public ObjectiveTask(@Nonnull ObjectiveTask other) {
/*  34 */     this.taskDescriptionKey = other.taskDescriptionKey;
/*  35 */     this.currentCompletion = other.currentCompletion;
/*  36 */     this.completionNeeded = other.completionNeeded;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ObjectiveTask deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ObjectiveTask obj = new ObjectiveTask();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.currentCompletion = buf.getIntLE(offset + 1);
/*  44 */     obj.completionNeeded = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { int taskDescriptionKeyLen = VarInt.peek(buf, pos);
/*  48 */       if (taskDescriptionKeyLen < 0) throw ProtocolException.negativeLength("TaskDescriptionKey", taskDescriptionKeyLen); 
/*  49 */       if (taskDescriptionKeyLen > 4096000) throw ProtocolException.stringTooLong("TaskDescriptionKey", taskDescriptionKeyLen, 4096000); 
/*  50 */       int taskDescriptionKeyVarLen = VarInt.length(buf, pos);
/*  51 */       obj.taskDescriptionKey = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += taskDescriptionKeyVarLen + taskDescriptionKeyLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 9;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.taskDescriptionKey != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeIntLE(this.currentCompletion);
/*  71 */     buf.writeIntLE(this.completionNeeded);
/*     */     
/*  73 */     if (this.taskDescriptionKey != null) PacketIO.writeVarString(buf, this.taskDescriptionKey, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 9;
/*  79 */     if (this.taskDescriptionKey != null) size += PacketIO.stringSize(this.taskDescriptionKey);
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 9) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 9;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       int taskDescriptionKeyLen = VarInt.peek(buffer, pos);
/*  95 */       if (taskDescriptionKeyLen < 0) {
/*  96 */         return ValidationResult.error("Invalid string length for TaskDescriptionKey");
/*     */       }
/*  98 */       if (taskDescriptionKeyLen > 4096000) {
/*  99 */         return ValidationResult.error("TaskDescriptionKey exceeds max length 4096000");
/*     */       }
/* 101 */       pos += VarInt.length(buffer, pos);
/* 102 */       pos += taskDescriptionKeyLen;
/* 103 */       if (pos > buffer.writerIndex()) {
/* 104 */         return ValidationResult.error("Buffer overflow reading TaskDescriptionKey");
/*     */       }
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ObjectiveTask clone() {
/* 111 */     ObjectiveTask copy = new ObjectiveTask();
/* 112 */     copy.taskDescriptionKey = this.taskDescriptionKey;
/* 113 */     copy.currentCompletion = this.currentCompletion;
/* 114 */     copy.completionNeeded = this.completionNeeded;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ObjectiveTask other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof ObjectiveTask) { other = (ObjectiveTask)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.taskDescriptionKey, other.taskDescriptionKey) && this.currentCompletion == other.currentCompletion && this.completionNeeded == other.completionNeeded);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.taskDescriptionKey, Integer.valueOf(this.currentCompletion), Integer.valueOf(this.completionNeeded) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */