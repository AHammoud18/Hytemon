/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.RepulsionConfig;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
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
/*     */ public class UpdateRepulsionConfig
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 75;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 65536011;
/*     */   
/*     */   public int getId() {
/*  27 */     return 75;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, RepulsionConfig> repulsionConfigs;
/*     */   
/*     */   public UpdateRepulsionConfig(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, RepulsionConfig> repulsionConfigs) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.repulsionConfigs = repulsionConfigs;
/*     */   }
/*     */   
/*     */   public UpdateRepulsionConfig(@Nonnull UpdateRepulsionConfig other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.repulsionConfigs = other.repulsionConfigs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateRepulsionConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateRepulsionConfig obj = new UpdateRepulsionConfig();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int repulsionConfigsCount = VarInt.peek(buf, pos);
/*  58 */       if (repulsionConfigsCount < 0) throw ProtocolException.negativeLength("RepulsionConfigs", repulsionConfigsCount); 
/*  59 */       if (repulsionConfigsCount > 4096000) throw ProtocolException.dictionaryTooLarge("RepulsionConfigs", repulsionConfigsCount, 4096000); 
/*  60 */       pos += VarInt.size(repulsionConfigsCount);
/*  61 */       obj.repulsionConfigs = new HashMap<>(repulsionConfigsCount);
/*  62 */       for (int i = 0; i < repulsionConfigsCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         RepulsionConfig val = RepulsionConfig.deserialize(buf, pos);
/*  65 */         pos += RepulsionConfig.computeBytesConsumed(buf, pos);
/*  66 */         if (obj.repulsionConfigs.put(Integer.valueOf(key), val) != null)
/*  67 */           throw ProtocolException.duplicateKey("repulsionConfigs", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 6;
/*  76 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  77 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += RepulsionConfig.computeBytesConsumed(buf, pos); i++; }  }
/*  78 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.repulsionConfigs != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     buf.writeByte(nullBits);
/*     */     
/*  88 */     buf.writeByte(this.type.getValue());
/*  89 */     buf.writeIntLE(this.maxId);
/*     */     
/*  91 */     if (this.repulsionConfigs != null) { if (this.repulsionConfigs.size() > 4096000) throw ProtocolException.dictionaryTooLarge("RepulsionConfigs", this.repulsionConfigs.size(), 4096000);  VarInt.write(buf, this.repulsionConfigs.size()); for (Map.Entry<Integer, RepulsionConfig> e : this.repulsionConfigs.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((RepulsionConfig)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 6;
/*  97 */     if (this.repulsionConfigs != null) size += VarInt.size(this.repulsionConfigs.size()) + this.repulsionConfigs.size() * 16;
/*     */     
/*  99 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 103 */     if (buffer.readableBytes() - offset < 6) {
/* 104 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 107 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 109 */     int pos = offset + 6;
/*     */     
/* 111 */     if ((nullBits & 0x1) != 0) {
/* 112 */       int repulsionConfigsCount = VarInt.peek(buffer, pos);
/* 113 */       if (repulsionConfigsCount < 0) {
/* 114 */         return ValidationResult.error("Invalid dictionary count for RepulsionConfigs");
/*     */       }
/* 116 */       if (repulsionConfigsCount > 4096000) {
/* 117 */         return ValidationResult.error("RepulsionConfigs exceeds max length 4096000");
/*     */       }
/* 119 */       pos += VarInt.length(buffer, pos);
/* 120 */       for (int i = 0; i < repulsionConfigsCount; i++) {
/* 121 */         pos += 4;
/* 122 */         if (pos > buffer.writerIndex()) {
/* 123 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 125 */         pos += 12;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateRepulsionConfig clone() {
/* 133 */     UpdateRepulsionConfig copy = new UpdateRepulsionConfig();
/* 134 */     copy.type = this.type;
/* 135 */     copy.maxId = this.maxId;
/* 136 */     if (this.repulsionConfigs != null) {
/* 137 */       Map<Integer, RepulsionConfig> m = new HashMap<>();
/* 138 */       for (Map.Entry<Integer, RepulsionConfig> e : this.repulsionConfigs.entrySet()) m.put(e.getKey(), ((RepulsionConfig)e.getValue()).clone()); 
/* 139 */       copy.repulsionConfigs = m;
/*     */     } 
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateRepulsionConfig other;
/* 147 */     if (this == obj) return true; 
/* 148 */     if (obj instanceof UpdateRepulsionConfig) { other = (UpdateRepulsionConfig)obj; } else { return false; }
/* 149 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.repulsionConfigs, other.repulsionConfigs));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.repulsionConfigs });
/*     */   }
/*     */   
/*     */   public UpdateRepulsionConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateRepulsionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */