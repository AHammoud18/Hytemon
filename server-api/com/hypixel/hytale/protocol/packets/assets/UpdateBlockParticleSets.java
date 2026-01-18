/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockParticleSet;
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
/*     */ public class UpdateBlockParticleSets implements Packet {
/*     */   public static final int PACKET_ID = 44;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 44;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, BlockParticleSet> blockParticleSets;
/*     */ 
/*     */   
/*     */   public UpdateBlockParticleSets(@Nonnull UpdateType type, @Nullable Map<String, BlockParticleSet> blockParticleSets) {
/*  37 */     this.type = type;
/*  38 */     this.blockParticleSets = blockParticleSets;
/*     */   }
/*     */   
/*     */   public UpdateBlockParticleSets(@Nonnull UpdateBlockParticleSets other) {
/*  42 */     this.type = other.type;
/*  43 */     this.blockParticleSets = other.blockParticleSets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateBlockParticleSets deserialize(@Nonnull ByteBuf buf, int offset) {
/*  48 */     UpdateBlockParticleSets obj = new UpdateBlockParticleSets();
/*  49 */     byte nullBits = buf.getByte(offset);
/*  50 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  52 */     int pos = offset + 2;
/*  53 */     if ((nullBits & 0x1) != 0) { int blockParticleSetsCount = VarInt.peek(buf, pos);
/*  54 */       if (blockParticleSetsCount < 0) throw ProtocolException.negativeLength("BlockParticleSets", blockParticleSetsCount); 
/*  55 */       if (blockParticleSetsCount > 4096000) throw ProtocolException.dictionaryTooLarge("BlockParticleSets", blockParticleSetsCount, 4096000); 
/*  56 */       pos += VarInt.size(blockParticleSetsCount);
/*  57 */       obj.blockParticleSets = new HashMap<>(blockParticleSetsCount);
/*  58 */       for (int i = 0; i < blockParticleSetsCount; i++) {
/*  59 */         int keyLen = VarInt.peek(buf, pos);
/*  60 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  61 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  62 */         int keyVarLen = VarInt.length(buf, pos);
/*  63 */         String key = PacketIO.readVarString(buf, pos);
/*  64 */         pos += keyVarLen + keyLen;
/*  65 */         BlockParticleSet val = BlockParticleSet.deserialize(buf, pos);
/*  66 */         pos += BlockParticleSet.computeBytesConsumed(buf, pos);
/*  67 */         if (obj.blockParticleSets.put(key, val) != null)
/*  68 */           throw ProtocolException.duplicateKey("blockParticleSets", key); 
/*     */       }  }
/*     */     
/*  71 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  75 */     byte nullBits = buf.getByte(offset);
/*  76 */     int pos = offset + 2;
/*  77 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  78 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += BlockParticleSet.computeBytesConsumed(buf, pos); i++; }  }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     byte nullBits = 0;
/*  86 */     if (this.blockParticleSets != null) nullBits = (byte)(nullBits | 0x1); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeByte(this.type.getValue());
/*     */     
/*  91 */     if (this.blockParticleSets != null) { if (this.blockParticleSets.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BlockParticleSets", this.blockParticleSets.size(), 4096000);  VarInt.write(buf, this.blockParticleSets.size()); for (Map.Entry<String, BlockParticleSet> e : this.blockParticleSets.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((BlockParticleSet)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 2;
/*  97 */     if (this.blockParticleSets != null) {
/*  98 */       int blockParticleSetsSize = 0;
/*  99 */       for (Map.Entry<String, BlockParticleSet> kvp : this.blockParticleSets.entrySet()) blockParticleSetsSize += PacketIO.stringSize(kvp.getKey()) + ((BlockParticleSet)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.blockParticleSets.size()) + blockParticleSetsSize;
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
/* 116 */       int blockParticleSetsCount = VarInt.peek(buffer, pos);
/* 117 */       if (blockParticleSetsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for BlockParticleSets");
/*     */       }
/* 120 */       if (blockParticleSetsCount > 4096000) {
/* 121 */         return ValidationResult.error("BlockParticleSets exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < blockParticleSetsCount; i++) {
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
/* 137 */         pos += BlockParticleSet.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateBlockParticleSets clone() {
/* 145 */     UpdateBlockParticleSets copy = new UpdateBlockParticleSets();
/* 146 */     copy.type = this.type;
/* 147 */     if (this.blockParticleSets != null) {
/* 148 */       Map<String, BlockParticleSet> m = new HashMap<>();
/* 149 */       for (Map.Entry<String, BlockParticleSet> e : this.blockParticleSets.entrySet()) m.put(e.getKey(), ((BlockParticleSet)e.getValue()).clone()); 
/* 150 */       copy.blockParticleSets = m;
/*     */     } 
/* 152 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateBlockParticleSets other;
/* 158 */     if (this == obj) return true; 
/* 159 */     if (obj instanceof UpdateBlockParticleSets) { other = (UpdateBlockParticleSets)obj; } else { return false; }
/* 160 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.blockParticleSets, other.blockParticleSets));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     return Objects.hash(new Object[] { this.type, this.blockParticleSets });
/*     */   }
/*     */   
/*     */   public UpdateBlockParticleSets() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateBlockParticleSets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */