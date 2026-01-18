/*     */ package com.hypixel.hytale.protocol.packets.setup;
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ public class UpdateFeatures implements Packet {
/*     */   public static final int PACKET_ID = 31;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 8192006;
/*     */   @Nullable
/*     */   public Map<ClientFeature, Boolean> features;
/*     */   
/*     */   public int getId() {
/*  25 */     return 31;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateFeatures() {}
/*     */ 
/*     */   
/*     */   public UpdateFeatures(@Nullable Map<ClientFeature, Boolean> features) {
/*  34 */     this.features = features;
/*     */   }
/*     */   
/*     */   public UpdateFeatures(@Nonnull UpdateFeatures other) {
/*  38 */     this.features = other.features;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateFeatures deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     UpdateFeatures obj = new UpdateFeatures();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int featuresCount = VarInt.peek(buf, pos);
/*  48 */       if (featuresCount < 0) throw ProtocolException.negativeLength("Features", featuresCount); 
/*  49 */       if (featuresCount > 4096000) throw ProtocolException.dictionaryTooLarge("Features", featuresCount, 4096000); 
/*  50 */       pos += VarInt.size(featuresCount);
/*  51 */       obj.features = new HashMap<>(featuresCount);
/*  52 */       for (int i = 0; i < featuresCount; i++) {
/*  53 */         ClientFeature key = ClientFeature.fromValue(buf.getByte(pos)); pos++;
/*  54 */         boolean val = (buf.getByte(pos) != 0); pos++;
/*  55 */         if (obj.features.put(key, Boolean.valueOf(val)) != null)
/*  56 */           throw ProtocolException.duplicateKey("features", key); 
/*     */       }  }
/*     */     
/*  59 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  63 */     byte nullBits = buf.getByte(offset);
/*  64 */     int pos = offset + 1;
/*  65 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  66 */       for (int i = 0; i < dictLen; ) { pos++; pos++; i++; }  }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.features != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  78 */     if (this.features != null) { if (this.features.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Features", this.features.size(), 4096000);  VarInt.write(buf, this.features.size()); for (Map.Entry<ClientFeature, Boolean> e : this.features.entrySet()) { buf.writeByte(((ClientFeature)e.getKey()).getValue()); buf.writeByte(((Boolean)e.getValue()).booleanValue() ? 1 : 0); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  83 */     int size = 1;
/*  84 */     if (this.features != null) size += VarInt.size(this.features.size()) + this.features.size() * 2;
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 1) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 1;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       int featuresCount = VarInt.peek(buffer, pos);
/* 100 */       if (featuresCount < 0) {
/* 101 */         return ValidationResult.error("Invalid dictionary count for Features");
/*     */       }
/* 103 */       if (featuresCount > 4096000) {
/* 104 */         return ValidationResult.error("Features exceeds max length 4096000");
/*     */       }
/* 106 */       pos += VarInt.length(buffer, pos);
/* 107 */       for (int i = 0; i < featuresCount; i++) {
/* 108 */         pos++;
/*     */         
/* 110 */         pos++;
/* 111 */         if (pos > buffer.writerIndex()) {
/* 112 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 116 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateFeatures clone() {
/* 120 */     UpdateFeatures copy = new UpdateFeatures();
/* 121 */     copy.features = (this.features != null) ? new HashMap<>(this.features) : null;
/* 122 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateFeatures other;
/* 128 */     if (this == obj) return true; 
/* 129 */     if (obj instanceof UpdateFeatures) { other = (UpdateFeatures)obj; } else { return false; }
/* 130 */      return Objects.equals(this.features, other.features);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 135 */     return Objects.hash(new Object[] { this.features });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\UpdateFeatures.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */