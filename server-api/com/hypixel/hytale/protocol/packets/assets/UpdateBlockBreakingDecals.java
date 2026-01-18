/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockBreakingDecal;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateBlockBreakingDecals implements Packet {
/*     */   public static final int PACKET_ID = 45;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 45;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, BlockBreakingDecal> blockBreakingDecals;
/*     */ 
/*     */   
/*     */   public UpdateBlockBreakingDecals(@Nonnull UpdateType type, @Nullable Map<String, BlockBreakingDecal> blockBreakingDecals) {
/*  37 */     this.type = type;
/*  38 */     this.blockBreakingDecals = blockBreakingDecals;
/*     */   }
/*     */   
/*     */   public UpdateBlockBreakingDecals(@Nonnull UpdateBlockBreakingDecals other) {
/*  42 */     this.type = other.type;
/*  43 */     this.blockBreakingDecals = other.blockBreakingDecals;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateBlockBreakingDecals deserialize(@Nonnull ByteBuf buf, int offset) {
/*  48 */     UpdateBlockBreakingDecals obj = new UpdateBlockBreakingDecals();
/*  49 */     byte nullBits = buf.getByte(offset);
/*  50 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  52 */     int pos = offset + 2;
/*  53 */     if ((nullBits & 0x1) != 0) { int blockBreakingDecalsCount = VarInt.peek(buf, pos);
/*  54 */       if (blockBreakingDecalsCount < 0) throw ProtocolException.negativeLength("BlockBreakingDecals", blockBreakingDecalsCount); 
/*  55 */       if (blockBreakingDecalsCount > 4096000) throw ProtocolException.dictionaryTooLarge("BlockBreakingDecals", blockBreakingDecalsCount, 4096000); 
/*  56 */       pos += VarInt.size(blockBreakingDecalsCount);
/*  57 */       obj.blockBreakingDecals = new HashMap<>(blockBreakingDecalsCount);
/*  58 */       for (int i = 0; i < blockBreakingDecalsCount; i++) {
/*  59 */         int keyLen = VarInt.peek(buf, pos);
/*  60 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  61 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  62 */         int keyVarLen = VarInt.length(buf, pos);
/*  63 */         String key = PacketIO.readVarString(buf, pos);
/*  64 */         pos += keyVarLen + keyLen;
/*  65 */         BlockBreakingDecal val = BlockBreakingDecal.deserialize(buf, pos);
/*  66 */         pos += BlockBreakingDecal.computeBytesConsumed(buf, pos);
/*  67 */         if (obj.blockBreakingDecals.put(key, val) != null)
/*  68 */           throw ProtocolException.duplicateKey("blockBreakingDecals", key); 
/*     */       }  }
/*     */     
/*  71 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  75 */     byte nullBits = buf.getByte(offset);
/*  76 */     int pos = offset + 2;
/*  77 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  78 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += BlockBreakingDecal.computeBytesConsumed(buf, pos); i++; }  }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     byte nullBits = 0;
/*  86 */     if (this.blockBreakingDecals != null) nullBits = (byte)(nullBits | 0x1); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeByte(this.type.getValue());
/*     */     
/*  91 */     if (this.blockBreakingDecals != null) { if (this.blockBreakingDecals.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BlockBreakingDecals", this.blockBreakingDecals.size(), 4096000);  VarInt.write(buf, this.blockBreakingDecals.size()); for (Map.Entry<String, BlockBreakingDecal> e : this.blockBreakingDecals.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((BlockBreakingDecal)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 2;
/*  97 */     if (this.blockBreakingDecals != null) {
/*  98 */       int blockBreakingDecalsSize = 0;
/*  99 */       for (Map.Entry<String, BlockBreakingDecal> kvp : this.blockBreakingDecals.entrySet()) blockBreakingDecalsSize += PacketIO.stringSize(kvp.getKey()) + ((BlockBreakingDecal)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.blockBreakingDecals.size()) + blockBreakingDecalsSize;
/*     */     } 
/*     */     
/* 103 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 107 */     if (buffer.readableBytes() - offset < 2) {
/* 108 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 111 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 113 */     int pos = offset + 2;
/*     */     
/* 115 */     if ((nullBits & 0x1) != 0) {
/* 116 */       int blockBreakingDecalsCount = VarInt.peek(buffer, pos);
/* 117 */       if (blockBreakingDecalsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for BlockBreakingDecals");
/*     */       }
/* 120 */       if (blockBreakingDecalsCount > 4096000) {
/* 121 */         return ValidationResult.error("BlockBreakingDecals exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < blockBreakingDecalsCount; i++) {
/* 125 */         int keyLen = VarInt.peek(buffer, pos);
/* 126 */         if (keyLen < 0) {
/* 127 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 129 */         if (keyLen > 4096000) {
/* 130 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 132 */         pos += VarInt.length(buffer, pos);
/* 133 */         pos += keyLen;
/* 134 */         if (pos > buffer.writerIndex()) {
/* 135 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 137 */         pos += BlockBreakingDecal.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateBlockBreakingDecals clone() {
/* 145 */     UpdateBlockBreakingDecals copy = new UpdateBlockBreakingDecals();
/* 146 */     copy.type = this.type;
/* 147 */     if (this.blockBreakingDecals != null) {
/* 148 */       Map<String, BlockBreakingDecal> m = new HashMap<>();
/* 149 */       for (Map.Entry<String, BlockBreakingDecal> e : this.blockBreakingDecals.entrySet()) m.put(e.getKey(), ((BlockBreakingDecal)e.getValue()).clone()); 
/* 150 */       copy.blockBreakingDecals = m;
/*     */     } 
/* 152 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateBlockBreakingDecals other;
/* 158 */     if (this == obj) return true; 
/* 159 */     if (obj instanceof UpdateBlockBreakingDecals) { other = (UpdateBlockBreakingDecals)obj; } else { return false; }
/* 160 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.blockBreakingDecals, other.blockBreakingDecals));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     return Objects.hash(new Object[] { this.type, this.blockBreakingDecals });
/*     */   }
/*     */   
/*     */   public UpdateBlockBreakingDecals() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateBlockBreakingDecals.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */