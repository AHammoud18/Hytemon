/*     */ package com.hypixel.hytale.protocol.packets.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
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
/*     */ public class PlayInteractionFor
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 292;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 19;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 27;
/*     */   
/*     */   public int getId() {
/*  26 */     return 292;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MAX_SIZE = 16385065;
/*     */   
/*     */   public int entityId;
/*     */   public int chainId;
/*     */   @Nonnull
/*  35 */   public InteractionType interactionType = InteractionType.Primary;
/*     */   
/*     */   @Nullable
/*     */   public ForkedChainId forkedId;
/*     */   public int operationIndex;
/*     */   
/*     */   public PlayInteractionFor(int entityId, int chainId, @Nullable ForkedChainId forkedId, int operationIndex, int interactionId, @Nullable String interactedItemId, @Nonnull InteractionType interactionType, boolean cancel) {
/*  42 */     this.entityId = entityId;
/*  43 */     this.chainId = chainId;
/*  44 */     this.forkedId = forkedId;
/*  45 */     this.operationIndex = operationIndex;
/*  46 */     this.interactionId = interactionId;
/*  47 */     this.interactedItemId = interactedItemId;
/*  48 */     this.interactionType = interactionType;
/*  49 */     this.cancel = cancel;
/*     */   } public int interactionId; @Nullable
/*     */   public String interactedItemId; public boolean cancel;
/*     */   public PlayInteractionFor(@Nonnull PlayInteractionFor other) {
/*  53 */     this.entityId = other.entityId;
/*  54 */     this.chainId = other.chainId;
/*  55 */     this.forkedId = other.forkedId;
/*  56 */     this.operationIndex = other.operationIndex;
/*  57 */     this.interactionId = other.interactionId;
/*  58 */     this.interactedItemId = other.interactedItemId;
/*  59 */     this.interactionType = other.interactionType;
/*  60 */     this.cancel = other.cancel;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static PlayInteractionFor deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     PlayInteractionFor obj = new PlayInteractionFor();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.entityId = buf.getIntLE(offset + 1);
/*  68 */     obj.chainId = buf.getIntLE(offset + 5);
/*  69 */     obj.operationIndex = buf.getIntLE(offset + 9);
/*  70 */     obj.interactionId = buf.getIntLE(offset + 13);
/*  71 */     obj.interactionType = InteractionType.fromValue(buf.getByte(offset + 17));
/*  72 */     obj.cancel = (buf.getByte(offset + 18) != 0);
/*     */     
/*  74 */     if ((nullBits & 0x1) != 0) {
/*  75 */       int varPos0 = offset + 27 + buf.getIntLE(offset + 19);
/*  76 */       obj.forkedId = ForkedChainId.deserialize(buf, varPos0);
/*     */     } 
/*  78 */     if ((nullBits & 0x2) != 0) {
/*  79 */       int varPos1 = offset + 27 + buf.getIntLE(offset + 23);
/*  80 */       int interactedItemIdLen = VarInt.peek(buf, varPos1);
/*  81 */       if (interactedItemIdLen < 0) throw ProtocolException.negativeLength("InteractedItemId", interactedItemIdLen); 
/*  82 */       if (interactedItemIdLen > 4096000) throw ProtocolException.stringTooLong("InteractedItemId", interactedItemIdLen, 4096000); 
/*  83 */       obj.interactedItemId = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*     */     } 
/*     */     
/*  86 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  90 */     byte nullBits = buf.getByte(offset);
/*  91 */     int maxEnd = 27;
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int fieldOffset0 = buf.getIntLE(offset + 19);
/*  94 */       int pos0 = offset + 27 + fieldOffset0;
/*  95 */       pos0 += ForkedChainId.computeBytesConsumed(buf, pos0);
/*  96 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  98 */     if ((nullBits & 0x2) != 0) {
/*  99 */       int fieldOffset1 = buf.getIntLE(offset + 23);
/* 100 */       int pos1 = offset + 27 + fieldOffset1;
/* 101 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/* 102 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 104 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 110 */     int startPos = buf.writerIndex();
/* 111 */     byte nullBits = 0;
/* 112 */     if (this.forkedId != null) nullBits = (byte)(nullBits | 0x1); 
/* 113 */     if (this.interactedItemId != null) nullBits = (byte)(nullBits | 0x2); 
/* 114 */     buf.writeByte(nullBits);
/*     */     
/* 116 */     buf.writeIntLE(this.entityId);
/* 117 */     buf.writeIntLE(this.chainId);
/* 118 */     buf.writeIntLE(this.operationIndex);
/* 119 */     buf.writeIntLE(this.interactionId);
/* 120 */     buf.writeByte(this.interactionType.getValue());
/* 121 */     buf.writeByte(this.cancel ? 1 : 0);
/*     */     
/* 123 */     int forkedIdOffsetSlot = buf.writerIndex();
/* 124 */     buf.writeIntLE(0);
/* 125 */     int interactedItemIdOffsetSlot = buf.writerIndex();
/* 126 */     buf.writeIntLE(0);
/*     */     
/* 128 */     int varBlockStart = buf.writerIndex();
/* 129 */     if (this.forkedId != null) {
/* 130 */       buf.setIntLE(forkedIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 131 */       this.forkedId.serialize(buf);
/*     */     } else {
/* 133 */       buf.setIntLE(forkedIdOffsetSlot, -1);
/*     */     } 
/* 135 */     if (this.interactedItemId != null) {
/* 136 */       buf.setIntLE(interactedItemIdOffsetSlot, buf.writerIndex() - varBlockStart);
/* 137 */       PacketIO.writeVarString(buf, this.interactedItemId, 4096000);
/*     */     } else {
/* 139 */       buf.setIntLE(interactedItemIdOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 145 */     int size = 27;
/* 146 */     if (this.forkedId != null) size += this.forkedId.computeSize(); 
/* 147 */     if (this.interactedItemId != null) size += PacketIO.stringSize(this.interactedItemId);
/*     */     
/* 149 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 153 */     if (buffer.readableBytes() - offset < 27) {
/* 154 */       return ValidationResult.error("Buffer too small: expected at least 27 bytes");
/*     */     }
/*     */     
/* 157 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 160 */     if ((nullBits & 0x1) != 0) {
/* 161 */       int forkedIdOffset = buffer.getIntLE(offset + 19);
/* 162 */       if (forkedIdOffset < 0) {
/* 163 */         return ValidationResult.error("Invalid offset for ForkedId");
/*     */       }
/* 165 */       int pos = offset + 27 + forkedIdOffset;
/* 166 */       if (pos >= buffer.writerIndex()) {
/* 167 */         return ValidationResult.error("Offset out of bounds for ForkedId");
/*     */       }
/* 169 */       ValidationResult forkedIdResult = ForkedChainId.validateStructure(buffer, pos);
/* 170 */       if (!forkedIdResult.isValid()) {
/* 171 */         return ValidationResult.error("Invalid ForkedId: " + forkedIdResult.error());
/*     */       }
/* 173 */       pos += ForkedChainId.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 176 */     if ((nullBits & 0x2) != 0) {
/* 177 */       int interactedItemIdOffset = buffer.getIntLE(offset + 23);
/* 178 */       if (interactedItemIdOffset < 0) {
/* 179 */         return ValidationResult.error("Invalid offset for InteractedItemId");
/*     */       }
/* 181 */       int pos = offset + 27 + interactedItemIdOffset;
/* 182 */       if (pos >= buffer.writerIndex()) {
/* 183 */         return ValidationResult.error("Offset out of bounds for InteractedItemId");
/*     */       }
/* 185 */       int interactedItemIdLen = VarInt.peek(buffer, pos);
/* 186 */       if (interactedItemIdLen < 0) {
/* 187 */         return ValidationResult.error("Invalid string length for InteractedItemId");
/*     */       }
/* 189 */       if (interactedItemIdLen > 4096000) {
/* 190 */         return ValidationResult.error("InteractedItemId exceeds max length 4096000");
/*     */       }
/* 192 */       pos += VarInt.length(buffer, pos);
/* 193 */       pos += interactedItemIdLen;
/* 194 */       if (pos > buffer.writerIndex()) {
/* 195 */         return ValidationResult.error("Buffer overflow reading InteractedItemId");
/*     */       }
/*     */     } 
/* 198 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public PlayInteractionFor clone() {
/* 202 */     PlayInteractionFor copy = new PlayInteractionFor();
/* 203 */     copy.entityId = this.entityId;
/* 204 */     copy.chainId = this.chainId;
/* 205 */     copy.forkedId = (this.forkedId != null) ? this.forkedId.clone() : null;
/* 206 */     copy.operationIndex = this.operationIndex;
/* 207 */     copy.interactionId = this.interactionId;
/* 208 */     copy.interactedItemId = this.interactedItemId;
/* 209 */     copy.interactionType = this.interactionType;
/* 210 */     copy.cancel = this.cancel;
/* 211 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     PlayInteractionFor other;
/* 217 */     if (this == obj) return true; 
/* 218 */     if (obj instanceof PlayInteractionFor) { other = (PlayInteractionFor)obj; } else { return false; }
/* 219 */      return (this.entityId == other.entityId && this.chainId == other.chainId && Objects.equals(this.forkedId, other.forkedId) && this.operationIndex == other.operationIndex && this.interactionId == other.interactionId && Objects.equals(this.interactedItemId, other.interactedItemId) && Objects.equals(this.interactionType, other.interactionType) && this.cancel == other.cancel);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 224 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), Integer.valueOf(this.chainId), this.forkedId, Integer.valueOf(this.operationIndex), Integer.valueOf(this.interactionId), this.interactedItemId, this.interactionType, Boolean.valueOf(this.cancel) });
/*     */   }
/*     */   
/*     */   public PlayInteractionFor() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interaction\PlayInteractionFor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */