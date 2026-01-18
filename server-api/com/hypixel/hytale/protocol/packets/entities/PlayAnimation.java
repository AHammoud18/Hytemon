/*     */ package com.hypixel.hytale.protocol.packets.entities;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class PlayAnimation
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 162;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   
/*     */   public int getId() {
/*  25 */     return 162;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 14; public static final int MAX_SIZE = 32768024; public int entityId; @Nullable
/*     */   public String itemAnimationsId; @Nullable
/*     */   public String animationId;
/*     */   @Nonnull
/*  31 */   public AnimationSlot slot = AnimationSlot.Movement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayAnimation(int entityId, @Nullable String itemAnimationsId, @Nullable String animationId, @Nonnull AnimationSlot slot) {
/*  37 */     this.entityId = entityId;
/*  38 */     this.itemAnimationsId = itemAnimationsId;
/*  39 */     this.animationId = animationId;
/*  40 */     this.slot = slot;
/*     */   }
/*     */   
/*     */   public PlayAnimation(@Nonnull PlayAnimation other) {
/*  44 */     this.entityId = other.entityId;
/*  45 */     this.itemAnimationsId = other.itemAnimationsId;
/*  46 */     this.animationId = other.animationId;
/*  47 */     this.slot = other.slot;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlayAnimation deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     PlayAnimation obj = new PlayAnimation();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.entityId = buf.getIntLE(offset + 1);
/*  55 */     obj.slot = AnimationSlot.fromValue(buf.getByte(offset + 5));
/*     */     
/*  57 */     if ((nullBits & 0x1) != 0) {
/*  58 */       int varPos0 = offset + 14 + buf.getIntLE(offset + 6);
/*  59 */       int itemAnimationsIdLen = VarInt.peek(buf, varPos0);
/*  60 */       if (itemAnimationsIdLen < 0) throw ProtocolException.negativeLength("ItemAnimationsId", itemAnimationsIdLen); 
/*  61 */       if (itemAnimationsIdLen > 4096000) throw ProtocolException.stringTooLong("ItemAnimationsId", itemAnimationsIdLen, 4096000); 
/*  62 */       obj.itemAnimationsId = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  64 */     if ((nullBits & 0x2) != 0) {
/*  65 */       int varPos1 = offset + 14 + buf.getIntLE(offset + 10);
/*  66 */       int animationIdLen = VarInt.peek(buf, varPos1);
/*  67 */       if (animationIdLen < 0) throw ProtocolException.negativeLength("AnimationId", animationIdLen); 
/*  68 */       if (animationIdLen > 4096000) throw ProtocolException.stringTooLong("AnimationId", animationIdLen, 4096000); 
/*  69 */       obj.animationId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int maxEnd = 14;
/*  78 */     if ((nullBits & 0x1) != 0) {
/*  79 */       int fieldOffset0 = buf.getIntLE(offset + 6);
/*  80 */       int pos0 = offset + 14 + fieldOffset0;
/*  81 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  82 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int fieldOffset1 = buf.getIntLE(offset + 10);
/*  86 */       int pos1 = offset + 14 + fieldOffset1;
/*  87 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  88 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  90 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  96 */     int startPos = buf.writerIndex();
/*  97 */     byte nullBits = 0;
/*  98 */     if (this.itemAnimationsId != null) nullBits = (byte)(nullBits | 0x1); 
/*  99 */     if (this.animationId != null) nullBits = (byte)(nullBits | 0x2); 
/* 100 */     buf.writeByte(nullBits);
/*     */     
/* 102 */     buf.writeIntLE(this.entityId);
/* 103 */     buf.writeByte(this.slot.getValue());
/*     */     
/* 105 */     int itemAnimationsIdOffsetSlot = buf.writerIndex();
/* 106 */     buf.writeIntLE(0);
/* 107 */     int animationIdOffsetSlot = buf.writerIndex();
/* 108 */     buf.writeIntLE(0);
/*     */     
/* 110 */     int varBlockStart = buf.writerIndex();
/* 111 */     if (this.itemAnimationsId != null) {
/* 112 */       buf.setIntLE(itemAnimationsIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 113 */       PacketIO.writeVarString(buf, this.itemAnimationsId, 4096000);
/*     */     } else {
/* 115 */       buf.setIntLE(itemAnimationsIdOffsetSlot, -1);
/*     */     } 
/* 117 */     if (this.animationId != null) {
/* 118 */       buf.setIntLE(animationIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 119 */       PacketIO.writeVarString(buf, this.animationId, 4096000);
/*     */     } else {
/* 121 */       buf.setIntLE(animationIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 127 */     int size = 14;
/* 128 */     if (this.itemAnimationsId != null) size += PacketIO.stringSize(this.itemAnimationsId); 
/* 129 */     if (this.animationId != null) size += PacketIO.stringSize(this.animationId);
/*     */     
/* 131 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 135 */     if (buffer.readableBytes() - offset < 14) {
/* 136 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */     
/* 139 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 142 */     if ((nullBits & 0x1) != 0) {
/* 143 */       int itemAnimationsIdOffset = buffer.getIntLE(offset + 6);
/* 144 */       if (itemAnimationsIdOffset < 0) {
/* 145 */         return ValidationResult.error("Invalid offset for ItemAnimationsId");
/*     */       }
/* 147 */       int pos = offset + 14 + itemAnimationsIdOffset;
/* 148 */       if (pos >= buffer.writerIndex()) {
/* 149 */         return ValidationResult.error("Offset out of bounds for ItemAnimationsId");
/*     */       }
/* 151 */       int itemAnimationsIdLen = VarInt.peek(buffer, pos);
/* 152 */       if (itemAnimationsIdLen < 0) {
/* 153 */         return ValidationResult.error("Invalid string length for ItemAnimationsId");
/*     */       }
/* 155 */       if (itemAnimationsIdLen > 4096000) {
/* 156 */         return ValidationResult.error("ItemAnimationsId exceeds max length 4096000");
/*     */       }
/* 158 */       pos += VarInt.length(buffer, pos);
/* 159 */       pos += itemAnimationsIdLen;
/* 160 */       if (pos > buffer.writerIndex()) {
/* 161 */         return ValidationResult.error("Buffer overflow reading ItemAnimationsId");
/*     */       }
/*     */     } 
/*     */     
/* 165 */     if ((nullBits & 0x2) != 0) {
/* 166 */       int animationIdOffset = buffer.getIntLE(offset + 10);
/* 167 */       if (animationIdOffset < 0) {
/* 168 */         return ValidationResult.error("Invalid offset for AnimationId");
/*     */       }
/* 170 */       int pos = offset + 14 + animationIdOffset;
/* 171 */       if (pos >= buffer.writerIndex()) {
/* 172 */         return ValidationResult.error("Offset out of bounds for AnimationId");
/*     */       }
/* 174 */       int animationIdLen = VarInt.peek(buffer, pos);
/* 175 */       if (animationIdLen < 0) {
/* 176 */         return ValidationResult.error("Invalid string length for AnimationId");
/*     */       }
/* 178 */       if (animationIdLen > 4096000) {
/* 179 */         return ValidationResult.error("AnimationId exceeds max length 4096000");
/*     */       }
/* 181 */       pos += VarInt.length(buffer, pos);
/* 182 */       pos += animationIdLen;
/* 183 */       if (pos > buffer.writerIndex()) {
/* 184 */         return ValidationResult.error("Buffer overflow reading AnimationId");
/*     */       }
/*     */     } 
/* 187 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlayAnimation clone() {
/* 191 */     PlayAnimation copy = new PlayAnimation();
/* 192 */     copy.entityId = this.entityId;
/* 193 */     copy.itemAnimationsId = this.itemAnimationsId;
/* 194 */     copy.animationId = this.animationId;
/* 195 */     copy.slot = this.slot;
/* 196 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlayAnimation other;
/* 202 */     if (this == obj) return true; 
/* 203 */     if (obj instanceof PlayAnimation) { other = (PlayAnimation)obj; } else { return false; }
/* 204 */      return (this.entityId == other.entityId && Objects.equals(this.itemAnimationsId, other.itemAnimationsId) && Objects.equals(this.animationId, other.animationId) && Objects.equals(this.slot, other.slot));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.itemAnimationsId, this.animationId, this.slot });
/*     */   }
/*     */   
/*     */   public PlayAnimation() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\entities\PlayAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */