/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
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
/*     */ public class UpdateWorldMapSettings
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 240;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 16;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 16;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  25 */     return 240;
/*     */   }
/*     */   public boolean enabled = true;
/*     */   @Nullable
/*     */   public Map<Short, BiomeData> biomeDataMap;
/*     */   public boolean allowTeleportToCoordinates;
/*     */   public boolean allowTeleportToMarkers;
/*  32 */   public float defaultScale = 32.0F;
/*  33 */   public float minScale = 2.0F;
/*  34 */   public float maxScale = 256.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateWorldMapSettings(boolean enabled, @Nullable Map<Short, BiomeData> biomeDataMap, boolean allowTeleportToCoordinates, boolean allowTeleportToMarkers, float defaultScale, float minScale, float maxScale) {
/*  40 */     this.enabled = enabled;
/*  41 */     this.biomeDataMap = biomeDataMap;
/*  42 */     this.allowTeleportToCoordinates = allowTeleportToCoordinates;
/*  43 */     this.allowTeleportToMarkers = allowTeleportToMarkers;
/*  44 */     this.defaultScale = defaultScale;
/*  45 */     this.minScale = minScale;
/*  46 */     this.maxScale = maxScale;
/*     */   }
/*     */   
/*     */   public UpdateWorldMapSettings(@Nonnull UpdateWorldMapSettings other) {
/*  50 */     this.enabled = other.enabled;
/*  51 */     this.biomeDataMap = other.biomeDataMap;
/*  52 */     this.allowTeleportToCoordinates = other.allowTeleportToCoordinates;
/*  53 */     this.allowTeleportToMarkers = other.allowTeleportToMarkers;
/*  54 */     this.defaultScale = other.defaultScale;
/*  55 */     this.minScale = other.minScale;
/*  56 */     this.maxScale = other.maxScale;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateWorldMapSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  61 */     UpdateWorldMapSettings obj = new UpdateWorldMapSettings();
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     obj.enabled = (buf.getByte(offset + 1) != 0);
/*  64 */     obj.allowTeleportToCoordinates = (buf.getByte(offset + 2) != 0);
/*  65 */     obj.allowTeleportToMarkers = (buf.getByte(offset + 3) != 0);
/*  66 */     obj.defaultScale = buf.getFloatLE(offset + 4);
/*  67 */     obj.minScale = buf.getFloatLE(offset + 8);
/*  68 */     obj.maxScale = buf.getFloatLE(offset + 12);
/*     */     
/*  70 */     int pos = offset + 16;
/*  71 */     if ((nullBits & 0x1) != 0) { int biomeDataMapCount = VarInt.peek(buf, pos);
/*  72 */       if (biomeDataMapCount < 0) throw ProtocolException.negativeLength("BiomeDataMap", biomeDataMapCount); 
/*  73 */       if (biomeDataMapCount > 4096000) throw ProtocolException.dictionaryTooLarge("BiomeDataMap", biomeDataMapCount, 4096000); 
/*  74 */       pos += VarInt.size(biomeDataMapCount);
/*  75 */       obj.biomeDataMap = new HashMap<>(biomeDataMapCount);
/*  76 */       for (int i = 0; i < biomeDataMapCount; i++) {
/*  77 */         short key = buf.getShortLE(pos); pos += 2;
/*  78 */         BiomeData val = BiomeData.deserialize(buf, pos);
/*  79 */         pos += BiomeData.computeBytesConsumed(buf, pos);
/*  80 */         if (obj.biomeDataMap.put(Short.valueOf(key), val) != null)
/*  81 */           throw ProtocolException.duplicateKey("biomeDataMap", Short.valueOf(key)); 
/*     */       }  }
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int pos = offset + 16;
/*  90 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  91 */       for (int i = 0; i < dictLen; ) { pos += 2; pos += BiomeData.computeBytesConsumed(buf, pos); i++; }  }
/*  92 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  98 */     byte nullBits = 0;
/*  99 */     if (this.biomeDataMap != null) nullBits = (byte)(nullBits | 0x1); 
/* 100 */     buf.writeByte(nullBits);
/*     */     
/* 102 */     buf.writeByte(this.enabled ? 1 : 0);
/* 103 */     buf.writeByte(this.allowTeleportToCoordinates ? 1 : 0);
/* 104 */     buf.writeByte(this.allowTeleportToMarkers ? 1 : 0);
/* 105 */     buf.writeFloatLE(this.defaultScale);
/* 106 */     buf.writeFloatLE(this.minScale);
/* 107 */     buf.writeFloatLE(this.maxScale);
/*     */     
/* 109 */     if (this.biomeDataMap != null) { if (this.biomeDataMap.size() > 4096000) throw ProtocolException.dictionaryTooLarge("BiomeDataMap", this.biomeDataMap.size(), 4096000);  VarInt.write(buf, this.biomeDataMap.size()); for (Map.Entry<Short, BiomeData> e : this.biomeDataMap.entrySet()) { buf.writeShortLE(((Short)e.getKey()).shortValue()); ((BiomeData)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/* 114 */     int size = 16;
/* 115 */     if (this.biomeDataMap != null) {
/* 116 */       int biomeDataMapSize = 0;
/* 117 */       for (Map.Entry<Short, BiomeData> kvp : this.biomeDataMap.entrySet()) biomeDataMapSize += 2 + ((BiomeData)kvp.getValue()).computeSize(); 
/* 118 */       size += VarInt.size(this.biomeDataMap.size()) + biomeDataMapSize;
/*     */     } 
/*     */     
/* 121 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 125 */     if (buffer.readableBytes() - offset < 16) {
/* 126 */       return ValidationResult.error("Buffer too small: expected at least 16 bytes");
/*     */     }
/*     */     
/* 129 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 131 */     int pos = offset + 16;
/*     */     
/* 133 */     if ((nullBits & 0x1) != 0) {
/* 134 */       int biomeDataMapCount = VarInt.peek(buffer, pos);
/* 135 */       if (biomeDataMapCount < 0) {
/* 136 */         return ValidationResult.error("Invalid dictionary count for BiomeDataMap");
/*     */       }
/* 138 */       if (biomeDataMapCount > 4096000) {
/* 139 */         return ValidationResult.error("BiomeDataMap exceeds max length 4096000");
/*     */       }
/* 141 */       pos += VarInt.length(buffer, pos);
/* 142 */       for (int i = 0; i < biomeDataMapCount; i++) {
/* 143 */         pos += 2;
/* 144 */         if (pos > buffer.writerIndex()) {
/* 145 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 147 */         pos += BiomeData.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateWorldMapSettings clone() {
/* 155 */     UpdateWorldMapSettings copy = new UpdateWorldMapSettings();
/* 156 */     copy.enabled = this.enabled;
/* 157 */     if (this.biomeDataMap != null) {
/* 158 */       Map<Short, BiomeData> m = new HashMap<>();
/* 159 */       for (Map.Entry<Short, BiomeData> e : this.biomeDataMap.entrySet()) m.put(e.getKey(), ((BiomeData)e.getValue()).clone()); 
/* 160 */       copy.biomeDataMap = m;
/*     */     } 
/* 162 */     copy.allowTeleportToCoordinates = this.allowTeleportToCoordinates;
/* 163 */     copy.allowTeleportToMarkers = this.allowTeleportToMarkers;
/* 164 */     copy.defaultScale = this.defaultScale;
/* 165 */     copy.minScale = this.minScale;
/* 166 */     copy.maxScale = this.maxScale;
/* 167 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateWorldMapSettings other;
/* 173 */     if (this == obj) return true; 
/* 174 */     if (obj instanceof UpdateWorldMapSettings) { other = (UpdateWorldMapSettings)obj; } else { return false; }
/* 175 */      return (this.enabled == other.enabled && Objects.equals(this.biomeDataMap, other.biomeDataMap) && this.allowTeleportToCoordinates == other.allowTeleportToCoordinates && this.allowTeleportToMarkers == other.allowTeleportToMarkers && this.defaultScale == other.defaultScale && this.minScale == other.minScale && this.maxScale == other.maxScale);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 180 */     return Objects.hash(new Object[] { Boolean.valueOf(this.enabled), this.biomeDataMap, Boolean.valueOf(this.allowTeleportToCoordinates), Boolean.valueOf(this.allowTeleportToMarkers), Float.valueOf(this.defaultScale), Float.valueOf(this.minScale), Float.valueOf(this.maxScale) });
/*     */   }
/*     */   
/*     */   public UpdateWorldMapSettings() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\UpdateWorldMapSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */