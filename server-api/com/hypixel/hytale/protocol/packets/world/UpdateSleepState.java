/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateSleepState implements Packet {
/*     */   public static final int PACKET_ID = 157;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 36;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 36;
/*     */   public static final int MAX_SIZE = 65536050;
/*     */   public boolean grayFade;
/*     */   public boolean sleepUi;
/*     */   @Nullable
/*     */   public SleepClock clock;
/*     */   @Nullable
/*     */   public SleepMultiplayer multiplayer;
/*     */   
/*     */   public int getId() {
/*  25 */     return 157;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateSleepState() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateSleepState(boolean grayFade, boolean sleepUi, @Nullable SleepClock clock, @Nullable SleepMultiplayer multiplayer) {
/*  37 */     this.grayFade = grayFade;
/*  38 */     this.sleepUi = sleepUi;
/*  39 */     this.clock = clock;
/*  40 */     this.multiplayer = multiplayer;
/*     */   }
/*     */   
/*     */   public UpdateSleepState(@Nonnull UpdateSleepState other) {
/*  44 */     this.grayFade = other.grayFade;
/*  45 */     this.sleepUi = other.sleepUi;
/*  46 */     this.clock = other.clock;
/*  47 */     this.multiplayer = other.multiplayer;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateSleepState deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     UpdateSleepState obj = new UpdateSleepState();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.grayFade = (buf.getByte(offset + 1) != 0);
/*  55 */     obj.sleepUi = (buf.getByte(offset + 2) != 0);
/*  56 */     if ((nullBits & 0x1) != 0) obj.clock = SleepClock.deserialize(buf, offset + 3);
/*     */     
/*  58 */     int pos = offset + 36;
/*  59 */     if ((nullBits & 0x2) != 0) { obj.multiplayer = SleepMultiplayer.deserialize(buf, pos);
/*  60 */       pos += SleepMultiplayer.computeBytesConsumed(buf, pos); }
/*     */     
/*  62 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     int pos = offset + 36;
/*  68 */     if ((nullBits & 0x2) != 0) pos += SleepMultiplayer.computeBytesConsumed(buf, pos); 
/*  69 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  75 */     byte nullBits = 0;
/*  76 */     if (this.clock != null) nullBits = (byte)(nullBits | 0x1); 
/*  77 */     if (this.multiplayer != null) nullBits = (byte)(nullBits | 0x2); 
/*  78 */     buf.writeByte(nullBits);
/*     */     
/*  80 */     buf.writeByte(this.grayFade ? 1 : 0);
/*  81 */     buf.writeByte(this.sleepUi ? 1 : 0);
/*  82 */     if (this.clock != null) { this.clock.serialize(buf); } else { buf.writeZero(33); }
/*     */     
/*  84 */     if (this.multiplayer != null) this.multiplayer.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 36;
/*  90 */     if (this.multiplayer != null) size += this.multiplayer.computeSize();
/*     */     
/*  92 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  96 */     if (buffer.readableBytes() - offset < 36) {
/*  97 */       return ValidationResult.error("Buffer too small: expected at least 36 bytes");
/*     */     }
/*     */     
/* 100 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 102 */     int pos = offset + 36;
/*     */     
/* 104 */     if ((nullBits & 0x2) != 0) {
/* 105 */       ValidationResult multiplayerResult = SleepMultiplayer.validateStructure(buffer, pos);
/* 106 */       if (!multiplayerResult.isValid()) {
/* 107 */         return ValidationResult.error("Invalid Multiplayer: " + multiplayerResult.error());
/*     */       }
/* 109 */       pos += SleepMultiplayer.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateSleepState clone() {
/* 115 */     UpdateSleepState copy = new UpdateSleepState();
/* 116 */     copy.grayFade = this.grayFade;
/* 117 */     copy.sleepUi = this.sleepUi;
/* 118 */     copy.clock = (this.clock != null) ? this.clock.clone() : null;
/* 119 */     copy.multiplayer = (this.multiplayer != null) ? this.multiplayer.clone() : null;
/* 120 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateSleepState other;
/* 126 */     if (this == obj) return true; 
/* 127 */     if (obj instanceof UpdateSleepState) { other = (UpdateSleepState)obj; } else { return false; }
/* 128 */      return (this.grayFade == other.grayFade && this.sleepUi == other.sleepUi && Objects.equals(this.clock, other.clock) && Objects.equals(this.multiplayer, other.multiplayer));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     return Objects.hash(new Object[] { Boolean.valueOf(this.grayFade), Boolean.valueOf(this.sleepUi), this.clock, this.multiplayer });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateSleepState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */