/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.HitboxCollisionConfig;
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class UpdateHitboxCollisionConfig
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 74;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 36864011;
/*     */   
/*     */   public int getId() {
/*  27 */     return 74;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, HitboxCollisionConfig> hitboxCollisionConfigs;
/*     */   
/*     */   public UpdateHitboxCollisionConfig(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, HitboxCollisionConfig> hitboxCollisionConfigs) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.hitboxCollisionConfigs = hitboxCollisionConfigs;
/*     */   }
/*     */   
/*     */   public UpdateHitboxCollisionConfig(@Nonnull UpdateHitboxCollisionConfig other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.hitboxCollisionConfigs = other.hitboxCollisionConfigs;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateHitboxCollisionConfig deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateHitboxCollisionConfig obj = new UpdateHitboxCollisionConfig();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int hitboxCollisionConfigsCount = VarInt.peek(buf, pos);
/*  58 */       if (hitboxCollisionConfigsCount < 0) throw ProtocolException.negativeLength("HitboxCollisionConfigs", hitboxCollisionConfigsCount); 
/*  59 */       if (hitboxCollisionConfigsCount > 4096000) throw ProtocolException.dictionaryTooLarge("HitboxCollisionConfigs", hitboxCollisionConfigsCount, 4096000); 
/*  60 */       pos += VarInt.size(hitboxCollisionConfigsCount);
/*  61 */       obj.hitboxCollisionConfigs = new HashMap<>(hitboxCollisionConfigsCount);
/*  62 */       for (int i = 0; i < hitboxCollisionConfigsCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         HitboxCollisionConfig val = HitboxCollisionConfig.deserialize(buf, pos);
/*  65 */         pos += HitboxCollisionConfig.computeBytesConsumed(buf, pos);
/*  66 */         if (obj.hitboxCollisionConfigs.put(Integer.valueOf(key), val) != null)
/*  67 */           throw ProtocolException.duplicateKey("hitboxCollisionConfigs", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 6;
/*  76 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  77 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += HitboxCollisionConfig.computeBytesConsumed(buf, pos); i++; }  }
/*  78 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.hitboxCollisionConfigs != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     buf.writeByte(nullBits);
/*     */     
/*  88 */     buf.writeByte(this.type.getValue());
/*  89 */     buf.writeIntLE(this.maxId);
/*     */     
/*  91 */     if (this.hitboxCollisionConfigs != null) { if (this.hitboxCollisionConfigs.size() > 4096000) throw ProtocolException.dictionaryTooLarge("HitboxCollisionConfigs", this.hitboxCollisionConfigs.size(), 4096000);  VarInt.write(buf, this.hitboxCollisionConfigs.size()); for (Map.Entry<Integer, HitboxCollisionConfig> e : this.hitboxCollisionConfigs.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((HitboxCollisionConfig)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 6;
/*  97 */     if (this.hitboxCollisionConfigs != null) size += VarInt.size(this.hitboxCollisionConfigs.size()) + this.hitboxCollisionConfigs.size() * 9;
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
/* 112 */       int hitboxCollisionConfigsCount = VarInt.peek(buffer, pos);
/* 113 */       if (hitboxCollisionConfigsCount < 0) {
/* 114 */         return ValidationResult.error("Invalid dictionary count for HitboxCollisionConfigs");
/*     */       }
/* 116 */       if (hitboxCollisionConfigsCount > 4096000) {
/* 117 */         return ValidationResult.error("HitboxCollisionConfigs exceeds max length 4096000");
/*     */       }
/* 119 */       pos += VarInt.length(buffer, pos);
/* 120 */       for (int i = 0; i < hitboxCollisionConfigsCount; i++) {
/* 121 */         pos += 4;
/* 122 */         if (pos > buffer.writerIndex()) {
/* 123 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 125 */         pos += 5;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateHitboxCollisionConfig clone() {
/* 133 */     UpdateHitboxCollisionConfig copy = new UpdateHitboxCollisionConfig();
/* 134 */     copy.type = this.type;
/* 135 */     copy.maxId = this.maxId;
/* 136 */     if (this.hitboxCollisionConfigs != null) {
/* 137 */       Map<Integer, HitboxCollisionConfig> m = new HashMap<>();
/* 138 */       for (Map.Entry<Integer, HitboxCollisionConfig> e : this.hitboxCollisionConfigs.entrySet()) m.put(e.getKey(), ((HitboxCollisionConfig)e.getValue()).clone()); 
/* 139 */       copy.hitboxCollisionConfigs = m;
/*     */     } 
/* 141 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateHitboxCollisionConfig other;
/* 147 */     if (this == obj) return true; 
/* 148 */     if (obj instanceof UpdateHitboxCollisionConfig) { other = (UpdateHitboxCollisionConfig)obj; } else { return false; }
/* 149 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.hitboxCollisionConfigs, other.hitboxCollisionConfigs));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.hitboxCollisionConfigs });
/*     */   }
/*     */   
/*     */   public UpdateHitboxCollisionConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateHitboxCollisionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */