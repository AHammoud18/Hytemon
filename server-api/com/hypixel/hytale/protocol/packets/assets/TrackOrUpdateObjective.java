/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Objective;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TrackOrUpdateObjective
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 69;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Objective objective;
/*     */   
/*     */   public int getId() {
/*  25 */     return 69;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TrackOrUpdateObjective() {}
/*     */ 
/*     */   
/*     */   public TrackOrUpdateObjective(@Nullable Objective objective) {
/*  34 */     this.objective = objective;
/*     */   }
/*     */   
/*     */   public TrackOrUpdateObjective(@Nonnull TrackOrUpdateObjective other) {
/*  38 */     this.objective = other.objective;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static TrackOrUpdateObjective deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     TrackOrUpdateObjective obj = new TrackOrUpdateObjective();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.objective = Objective.deserialize(buf, pos);
/*  48 */       pos += Objective.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 1;
/*  56 */     if ((nullBits & 0x1) != 0) pos += Objective.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  63 */     byte nullBits = 0;
/*  64 */     if (this.objective != null) nullBits = (byte)(nullBits | 0x1); 
/*  65 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  68 */     if (this.objective != null) this.objective.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 1;
/*  74 */     if (this.objective != null) size += this.objective.computeSize();
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 1) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 1;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       ValidationResult objectiveResult = Objective.validateStructure(buffer, pos);
/*  90 */       if (!objectiveResult.isValid()) {
/*  91 */         return ValidationResult.error("Invalid Objective: " + objectiveResult.error());
/*     */       }
/*  93 */       pos += Objective.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  95 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public TrackOrUpdateObjective clone() {
/*  99 */     TrackOrUpdateObjective copy = new TrackOrUpdateObjective();
/* 100 */     copy.objective = (this.objective != null) ? this.objective.clone() : null;
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     TrackOrUpdateObjective other;
/* 107 */     if (this == obj) return true; 
/* 108 */     if (obj instanceof TrackOrUpdateObjective) { other = (TrackOrUpdateObjective)obj; } else { return false; }
/* 109 */      return Objects.equals(this.objective, other.objective);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return Objects.hash(new Object[] { this.objective });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\TrackOrUpdateObjective.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */