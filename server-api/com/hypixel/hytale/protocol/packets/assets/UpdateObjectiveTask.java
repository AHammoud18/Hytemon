/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ObjectiveTask;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class UpdateObjectiveTask
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 71;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 16384035;
/*     */   
/*     */   public int getId() {
/*  26 */     return 71;
/*     */   }
/*     */   @Nonnull
/*  29 */   public UUID objectiveUuid = new UUID(0L, 0L);
/*     */   
/*     */   public int taskIndex;
/*     */   
/*     */   @Nullable
/*     */   public ObjectiveTask task;
/*     */   
/*     */   public UpdateObjectiveTask(@Nonnull UUID objectiveUuid, int taskIndex, @Nullable ObjectiveTask task) {
/*  37 */     this.objectiveUuid = objectiveUuid;
/*  38 */     this.taskIndex = taskIndex;
/*  39 */     this.task = task;
/*     */   }
/*     */   
/*     */   public UpdateObjectiveTask(@Nonnull UpdateObjectiveTask other) {
/*  43 */     this.objectiveUuid = other.objectiveUuid;
/*  44 */     this.taskIndex = other.taskIndex;
/*  45 */     this.task = other.task;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateObjectiveTask deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     UpdateObjectiveTask obj = new UpdateObjectiveTask();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.objectiveUuid = PacketIO.readUUID(buf, offset + 1);
/*  53 */     obj.taskIndex = buf.getIntLE(offset + 17);
/*     */     
/*  55 */     int pos = offset + 21;
/*  56 */     if ((nullBits & 0x1) != 0) { obj.task = ObjectiveTask.deserialize(buf, pos);
/*  57 */       pos += ObjectiveTask.computeBytesConsumed(buf, pos); }
/*     */     
/*  59 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     int pos = offset + 21;
/*  65 */     if ((nullBits & 0x1) != 0) pos += ObjectiveTask.computeBytesConsumed(buf, pos); 
/*  66 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  72 */     byte nullBits = 0;
/*  73 */     if (this.task != null) nullBits = (byte)(nullBits | 0x1); 
/*  74 */     buf.writeByte(nullBits);
/*     */     
/*  76 */     PacketIO.writeUUID(buf, this.objectiveUuid);
/*  77 */     buf.writeIntLE(this.taskIndex);
/*     */     
/*  79 */     if (this.task != null) this.task.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 21;
/*  85 */     if (this.task != null) size += this.task.computeSize();
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 21) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 21;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       ValidationResult taskResult = ObjectiveTask.validateStructure(buffer, pos);
/* 101 */       if (!taskResult.isValid()) {
/* 102 */         return ValidationResult.error("Invalid Task: " + taskResult.error());
/*     */       }
/* 104 */       pos += ObjectiveTask.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 106 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateObjectiveTask clone() {
/* 110 */     UpdateObjectiveTask copy = new UpdateObjectiveTask();
/* 111 */     copy.objectiveUuid = this.objectiveUuid;
/* 112 */     copy.taskIndex = this.taskIndex;
/* 113 */     copy.task = (this.task != null) ? this.task.clone() : null;
/* 114 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateObjectiveTask other;
/* 120 */     if (this == obj) return true; 
/* 121 */     if (obj instanceof UpdateObjectiveTask) { other = (UpdateObjectiveTask)obj; } else { return false; }
/* 122 */      return (Objects.equals(this.objectiveUuid, other.objectiveUuid) && this.taskIndex == other.taskIndex && Objects.equals(this.task, other.task));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 127 */     return Objects.hash(new Object[] { this.objectiveUuid, Integer.valueOf(this.taskIndex), this.task });
/*     */   }
/*     */   
/*     */   public UpdateObjectiveTask() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateObjectiveTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */