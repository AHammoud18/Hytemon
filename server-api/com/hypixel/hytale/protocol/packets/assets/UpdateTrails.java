/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.Trail;
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
/*     */ public class UpdateTrails implements Packet {
/*     */   public static final int PACKET_ID = 48;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 48;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Trail> trails;
/*     */ 
/*     */   
/*     */   public UpdateTrails(@Nonnull UpdateType type, @Nullable Map<String, Trail> trails) {
/*  37 */     this.type = type;
/*  38 */     this.trails = trails;
/*     */   }
/*     */   
/*     */   public UpdateTrails(@Nonnull UpdateTrails other) {
/*  42 */     this.type = other.type;
/*  43 */     this.trails = other.trails;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateTrails deserialize(@Nonnull ByteBuf buf, int offset) {
/*  48 */     UpdateTrails obj = new UpdateTrails();
/*  49 */     byte nullBits = buf.getByte(offset);
/*  50 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  52 */     int pos = offset + 2;
/*  53 */     if ((nullBits & 0x1) != 0) { int trailsCount = VarInt.peek(buf, pos);
/*  54 */       if (trailsCount < 0) throw ProtocolException.negativeLength("Trails", trailsCount); 
/*  55 */       if (trailsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Trails", trailsCount, 4096000); 
/*  56 */       pos += VarInt.size(trailsCount);
/*  57 */       obj.trails = new HashMap<>(trailsCount);
/*  58 */       for (int i = 0; i < trailsCount; i++) {
/*  59 */         int keyLen = VarInt.peek(buf, pos);
/*  60 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  61 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  62 */         int keyVarLen = VarInt.length(buf, pos);
/*  63 */         String key = PacketIO.readVarString(buf, pos);
/*  64 */         pos += keyVarLen + keyLen;
/*  65 */         Trail val = Trail.deserialize(buf, pos);
/*  66 */         pos += Trail.computeBytesConsumed(buf, pos);
/*  67 */         if (obj.trails.put(key, val) != null)
/*  68 */           throw ProtocolException.duplicateKey("trails", key); 
/*     */       }  }
/*     */     
/*  71 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  75 */     byte nullBits = buf.getByte(offset);
/*  76 */     int pos = offset + 2;
/*  77 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  78 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += Trail.computeBytesConsumed(buf, pos); i++; }  }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     byte nullBits = 0;
/*  86 */     if (this.trails != null) nullBits = (byte)(nullBits | 0x1); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeByte(this.type.getValue());
/*     */     
/*  91 */     if (this.trails != null) { if (this.trails.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Trails", this.trails.size(), 4096000);  VarInt.write(buf, this.trails.size()); for (Map.Entry<String, Trail> e : this.trails.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((Trail)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 2;
/*  97 */     if (this.trails != null) {
/*  98 */       int trailsSize = 0;
/*  99 */       for (Map.Entry<String, Trail> kvp : this.trails.entrySet()) trailsSize += PacketIO.stringSize(kvp.getKey()) + ((Trail)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.trails.size()) + trailsSize;
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
/* 116 */       int trailsCount = VarInt.peek(buffer, pos);
/* 117 */       if (trailsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for Trails");
/*     */       }
/* 120 */       if (trailsCount > 4096000) {
/* 121 */         return ValidationResult.error("Trails exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < trailsCount; i++) {
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
/* 137 */         pos += Trail.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateTrails clone() {
/* 145 */     UpdateTrails copy = new UpdateTrails();
/* 146 */     copy.type = this.type;
/* 147 */     if (this.trails != null) {
/* 148 */       Map<String, Trail> m = new HashMap<>();
/* 149 */       for (Map.Entry<String, Trail> e : this.trails.entrySet()) m.put(e.getKey(), ((Trail)e.getValue()).clone()); 
/* 150 */       copy.trails = m;
/*     */     } 
/* 152 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateTrails other;
/* 158 */     if (this == obj) return true; 
/* 159 */     if (obj instanceof UpdateTrails) { other = (UpdateTrails)obj; } else { return false; }
/* 160 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.trails, other.trails));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 165 */     return Objects.hash(new Object[] { this.type, this.trails });
/*     */   }
/*     */   
/*     */   public UpdateTrails() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateTrails.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */