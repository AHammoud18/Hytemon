/*     */ package com.hypixel.hytale.protocol.packets.interaction;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncInteractionChains
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 290;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 0;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 0;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  25 */     return 290;
/*     */   }
/*     */   @Nonnull
/*  28 */   public SyncInteractionChain[] updates = new SyncInteractionChain[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SyncInteractionChains(@Nonnull SyncInteractionChain[] updates) {
/*  34 */     this.updates = updates;
/*     */   }
/*     */   
/*     */   public SyncInteractionChains(@Nonnull SyncInteractionChains other) {
/*  38 */     this.updates = other.updates;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SyncInteractionChains deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     SyncInteractionChains obj = new SyncInteractionChains();
/*     */ 
/*     */     
/*  46 */     int pos = offset + 0;
/*  47 */     int updatesCount = VarInt.peek(buf, pos);
/*  48 */     if (updatesCount < 0) throw ProtocolException.negativeLength("Updates", updatesCount); 
/*  49 */     if (updatesCount > 4096000) throw ProtocolException.arrayTooLong("Updates", updatesCount, 4096000); 
/*  50 */     int updatesVarLen = VarInt.size(updatesCount);
/*  51 */     if ((pos + updatesVarLen) + updatesCount * 33L > buf.readableBytes())
/*  52 */       throw ProtocolException.bufferTooSmall("Updates", pos + updatesVarLen + updatesCount * 33, buf.readableBytes()); 
/*  53 */     pos += updatesVarLen;
/*  54 */     obj.updates = new SyncInteractionChain[updatesCount];
/*  55 */     for (int i = 0; i < updatesCount; i++) {
/*  56 */       obj.updates[i] = SyncInteractionChain.deserialize(buf, pos);
/*  57 */       pos += SyncInteractionChain.computeBytesConsumed(buf, pos);
/*     */     } 
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     int pos = offset + 0;
/*  65 */     int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  66 */     for (int i = 0; i < arrLen; ) { pos += SyncInteractionChain.computeBytesConsumed(buf, pos); i++; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  75 */     if (this.updates.length > 4096000) throw ProtocolException.arrayTooLong("Updates", this.updates.length, 4096000);  VarInt.write(buf, this.updates.length); for (SyncInteractionChain item : this.updates) item.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  80 */     int size = 0;
/*  81 */     int updatesSize = 0;
/*  82 */     for (SyncInteractionChain elem : this.updates) updatesSize += elem.computeSize(); 
/*  83 */     size += VarInt.size(this.updates.length) + updatesSize;
/*     */     
/*  85 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 0) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 0 bytes");
/*     */     }
/*     */ 
/*     */     
/*  94 */     int pos = offset + 0;
/*     */     
/*  96 */     int updatesCount = VarInt.peek(buffer, pos);
/*  97 */     if (updatesCount < 0) {
/*  98 */       return ValidationResult.error("Invalid array count for Updates");
/*     */     }
/* 100 */     if (updatesCount > 4096000) {
/* 101 */       return ValidationResult.error("Updates exceeds max length 4096000");
/*     */     }
/* 103 */     pos += VarInt.length(buffer, pos);
/* 104 */     for (int i = 0; i < updatesCount; i++) {
/* 105 */       ValidationResult structResult = SyncInteractionChain.validateStructure(buffer, pos);
/* 106 */       if (!structResult.isValid()) {
/* 107 */         return ValidationResult.error("Invalid SyncInteractionChain in Updates[" + i + "]: " + structResult.error());
/*     */       }
/* 109 */       pos += SyncInteractionChain.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SyncInteractionChains clone() {
/* 115 */     SyncInteractionChains copy = new SyncInteractionChains();
/* 116 */     copy.updates = (SyncInteractionChain[])Arrays.<SyncInteractionChain>stream(this.updates).map(e -> e.clone()).toArray(x$0 -> new SyncInteractionChain[x$0]);
/* 117 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SyncInteractionChains other;
/* 123 */     if (this == obj) return true; 
/* 124 */     if (obj instanceof SyncInteractionChains) { other = (SyncInteractionChains)obj; } else { return false; }
/* 125 */      return Arrays.equals((Object[])this.updates, (Object[])other.updates);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     int result = 1;
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.updates);
/* 132 */     return result;
/*     */   }
/*     */   
/*     */   public SyncInteractionChains() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interaction\SyncInteractionChains.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */