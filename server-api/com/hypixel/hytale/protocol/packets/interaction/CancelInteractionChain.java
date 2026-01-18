/*     */ package com.hypixel.hytale.protocol.packets.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ForkedChainId;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CancelInteractionChain
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 291;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1038;
/*     */   public int chainId;
/*     */   @Nullable
/*     */   public ForkedChainId forkedId;
/*     */   
/*     */   public int getId() {
/*  25 */     return 291;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CancelInteractionChain() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public CancelInteractionChain(int chainId, @Nullable ForkedChainId forkedId) {
/*  35 */     this.chainId = chainId;
/*  36 */     this.forkedId = forkedId;
/*     */   }
/*     */   
/*     */   public CancelInteractionChain(@Nonnull CancelInteractionChain other) {
/*  40 */     this.chainId = other.chainId;
/*  41 */     this.forkedId = other.forkedId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CancelInteractionChain deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     CancelInteractionChain obj = new CancelInteractionChain();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.chainId = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.forkedId = ForkedChainId.deserialize(buf, pos);
/*  52 */       pos += ForkedChainId.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 5;
/*  60 */     if ((nullBits & 0x1) != 0) pos += ForkedChainId.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.forkedId != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     buf.writeIntLE(this.chainId);
/*     */     
/*  73 */     if (this.forkedId != null) this.forkedId.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 5;
/*  79 */     if (this.forkedId != null) size += this.forkedId.computeSize();
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 5) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 5;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       ValidationResult forkedIdResult = ForkedChainId.validateStructure(buffer, pos);
/*  95 */       if (!forkedIdResult.isValid()) {
/*  96 */         return ValidationResult.error("Invalid ForkedId: " + forkedIdResult.error());
/*     */       }
/*  98 */       pos += ForkedChainId.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 100 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CancelInteractionChain clone() {
/* 104 */     CancelInteractionChain copy = new CancelInteractionChain();
/* 105 */     copy.chainId = this.chainId;
/* 106 */     copy.forkedId = (this.forkedId != null) ? this.forkedId.clone() : null;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CancelInteractionChain other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof CancelInteractionChain) { other = (CancelInteractionChain)obj; } else { return false; }
/* 115 */      return (this.chainId == other.chainId && Objects.equals(this.forkedId, other.forkedId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { Integer.valueOf(this.chainId), this.forkedId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interaction\CancelInteractionChain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */