/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class InteractionChainData
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 61;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 61;
/*     */   public static final int MAX_SIZE = 16384066;
/*  20 */   public int entityId = -1; @Nonnull
/*  21 */   public UUID proxyId = new UUID(0L, 0L);
/*     */   
/*     */   @Nullable
/*     */   public Vector3f hitLocation;
/*  25 */   public int targetSlot = Integer.MIN_VALUE;
/*     */   
/*     */   @Nullable
/*     */   public String hitDetail;
/*     */ 
/*     */   
/*     */   public InteractionChainData(int entityId, @Nonnull UUID proxyId, @Nullable Vector3f hitLocation, @Nullable String hitDetail, @Nullable BlockPosition blockPosition, int targetSlot, @Nullable Vector3f hitNormal) {
/*  32 */     this.entityId = entityId;
/*  33 */     this.proxyId = proxyId;
/*  34 */     this.hitLocation = hitLocation;
/*  35 */     this.hitDetail = hitDetail;
/*  36 */     this.blockPosition = blockPosition;
/*  37 */     this.targetSlot = targetSlot;
/*  38 */     this.hitNormal = hitNormal;
/*     */   } @Nullable
/*     */   public BlockPosition blockPosition; @Nullable
/*     */   public Vector3f hitNormal; public InteractionChainData(@Nonnull InteractionChainData other) {
/*  42 */     this.entityId = other.entityId;
/*  43 */     this.proxyId = other.proxyId;
/*  44 */     this.hitLocation = other.hitLocation;
/*  45 */     this.hitDetail = other.hitDetail;
/*  46 */     this.blockPosition = other.blockPosition;
/*  47 */     this.targetSlot = other.targetSlot;
/*  48 */     this.hitNormal = other.hitNormal;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static InteractionChainData deserialize(@Nonnull ByteBuf buf, int offset) {
/*  53 */     InteractionChainData obj = new InteractionChainData();
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     obj.entityId = buf.getIntLE(offset + 1);
/*  56 */     obj.proxyId = PacketIO.readUUID(buf, offset + 5);
/*  57 */     if ((nullBits & 0x1) != 0) obj.hitLocation = Vector3f.deserialize(buf, offset + 21); 
/*  58 */     if ((nullBits & 0x4) != 0) obj.blockPosition = BlockPosition.deserialize(buf, offset + 33); 
/*  59 */     obj.targetSlot = buf.getIntLE(offset + 45);
/*  60 */     if ((nullBits & 0x8) != 0) obj.hitNormal = Vector3f.deserialize(buf, offset + 49);
/*     */     
/*  62 */     int pos = offset + 61;
/*  63 */     if ((nullBits & 0x2) != 0) { int hitDetailLen = VarInt.peek(buf, pos);
/*  64 */       if (hitDetailLen < 0) throw ProtocolException.negativeLength("HitDetail", hitDetailLen); 
/*  65 */       if (hitDetailLen > 4096000) throw ProtocolException.stringTooLong("HitDetail", hitDetailLen, 4096000); 
/*  66 */       int hitDetailVarLen = VarInt.length(buf, pos);
/*  67 */       obj.hitDetail = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  68 */       pos += hitDetailVarLen + hitDetailLen; }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 61;
/*  76 */     if ((nullBits & 0x2) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  77 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  82 */     byte nullBits = 0;
/*  83 */     if (this.hitLocation != null) nullBits = (byte)(nullBits | 0x1); 
/*  84 */     if (this.hitDetail != null) nullBits = (byte)(nullBits | 0x2); 
/*  85 */     if (this.blockPosition != null) nullBits = (byte)(nullBits | 0x4); 
/*  86 */     if (this.hitNormal != null) nullBits = (byte)(nullBits | 0x8); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeIntLE(this.entityId);
/*  90 */     PacketIO.writeUUID(buf, this.proxyId);
/*  91 */     if (this.hitLocation != null) { this.hitLocation.serialize(buf); } else { buf.writeZero(12); }
/*  92 */      if (this.blockPosition != null) { this.blockPosition.serialize(buf); } else { buf.writeZero(12); }
/*  93 */      buf.writeIntLE(this.targetSlot);
/*  94 */     if (this.hitNormal != null) { this.hitNormal.serialize(buf); } else { buf.writeZero(12); }
/*     */     
/*  96 */     if (this.hitDetail != null) PacketIO.writeVarString(buf, this.hitDetail, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 101 */     int size = 61;
/* 102 */     if (this.hitDetail != null) size += PacketIO.stringSize(this.hitDetail);
/*     */     
/* 104 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 108 */     if (buffer.readableBytes() - offset < 61) {
/* 109 */       return ValidationResult.error("Buffer too small: expected at least 61 bytes");
/*     */     }
/*     */     
/* 112 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 114 */     int pos = offset + 61;
/*     */     
/* 116 */     if ((nullBits & 0x2) != 0) {
/* 117 */       int hitDetailLen = VarInt.peek(buffer, pos);
/* 118 */       if (hitDetailLen < 0) {
/* 119 */         return ValidationResult.error("Invalid string length for HitDetail");
/*     */       }
/* 121 */       if (hitDetailLen > 4096000) {
/* 122 */         return ValidationResult.error("HitDetail exceeds max length 4096000");
/*     */       }
/* 124 */       pos += VarInt.length(buffer, pos);
/* 125 */       pos += hitDetailLen;
/* 126 */       if (pos > buffer.writerIndex()) {
/* 127 */         return ValidationResult.error("Buffer overflow reading HitDetail");
/*     */       }
/*     */     } 
/* 130 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public InteractionChainData clone() {
/* 134 */     InteractionChainData copy = new InteractionChainData();
/* 135 */     copy.entityId = this.entityId;
/* 136 */     copy.proxyId = this.proxyId;
/* 137 */     copy.hitLocation = (this.hitLocation != null) ? this.hitLocation.clone() : null;
/* 138 */     copy.hitDetail = this.hitDetail;
/* 139 */     copy.blockPosition = (this.blockPosition != null) ? this.blockPosition.clone() : null;
/* 140 */     copy.targetSlot = this.targetSlot;
/* 141 */     copy.hitNormal = (this.hitNormal != null) ? this.hitNormal.clone() : null;
/* 142 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     InteractionChainData other;
/* 148 */     if (this == obj) return true; 
/* 149 */     if (obj instanceof InteractionChainData) { other = (InteractionChainData)obj; } else { return false; }
/* 150 */      return (this.entityId == other.entityId && Objects.equals(this.proxyId, other.proxyId) && Objects.equals(this.hitLocation, other.hitLocation) && Objects.equals(this.hitDetail, other.hitDetail) && Objects.equals(this.blockPosition, other.blockPosition) && this.targetSlot == other.targetSlot && Objects.equals(this.hitNormal, other.hitNormal));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 155 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.proxyId, this.hitLocation, this.hitDetail, this.blockPosition, Integer.valueOf(this.targetSlot), this.hitNormal });
/*     */   }
/*     */   
/*     */   public InteractionChainData() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\InteractionChainData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */