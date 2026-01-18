/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SoundEvent {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 34;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String id;
/*     */   public float volume;
/*     */   public float pitch;
/*     */   public float musicDuckingVolume;
/*     */   public float ambientDuckingVolume;
/*     */   public int maxInstance;
/*     */   public boolean preventSoundInterruption;
/*     */   public float startAttenuationDistance;
/*     */   public float maxDistance;
/*     */   @Nullable
/*     */   public SoundEventLayer[] layers;
/*     */   public int audioCategory;
/*     */   
/*     */   public SoundEvent() {}
/*     */   
/*     */   public SoundEvent(@Nullable String id, float volume, float pitch, float musicDuckingVolume, float ambientDuckingVolume, int maxInstance, boolean preventSoundInterruption, float startAttenuationDistance, float maxDistance, @Nullable SoundEventLayer[] layers, int audioCategory) {
/*  36 */     this.id = id;
/*  37 */     this.volume = volume;
/*  38 */     this.pitch = pitch;
/*  39 */     this.musicDuckingVolume = musicDuckingVolume;
/*  40 */     this.ambientDuckingVolume = ambientDuckingVolume;
/*  41 */     this.maxInstance = maxInstance;
/*  42 */     this.preventSoundInterruption = preventSoundInterruption;
/*  43 */     this.startAttenuationDistance = startAttenuationDistance;
/*  44 */     this.maxDistance = maxDistance;
/*  45 */     this.layers = layers;
/*  46 */     this.audioCategory = audioCategory;
/*     */   }
/*     */   
/*     */   public SoundEvent(@Nonnull SoundEvent other) {
/*  50 */     this.id = other.id;
/*  51 */     this.volume = other.volume;
/*  52 */     this.pitch = other.pitch;
/*  53 */     this.musicDuckingVolume = other.musicDuckingVolume;
/*  54 */     this.ambientDuckingVolume = other.ambientDuckingVolume;
/*  55 */     this.maxInstance = other.maxInstance;
/*  56 */     this.preventSoundInterruption = other.preventSoundInterruption;
/*  57 */     this.startAttenuationDistance = other.startAttenuationDistance;
/*  58 */     this.maxDistance = other.maxDistance;
/*  59 */     this.layers = other.layers;
/*  60 */     this.audioCategory = other.audioCategory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SoundEvent deserialize(@Nonnull ByteBuf buf, int offset) {
/*  65 */     SoundEvent obj = new SoundEvent();
/*  66 */     byte nullBits = buf.getByte(offset);
/*  67 */     obj.volume = buf.getFloatLE(offset + 1);
/*  68 */     obj.pitch = buf.getFloatLE(offset + 5);
/*  69 */     obj.musicDuckingVolume = buf.getFloatLE(offset + 9);
/*  70 */     obj.ambientDuckingVolume = buf.getFloatLE(offset + 13);
/*  71 */     obj.maxInstance = buf.getIntLE(offset + 17);
/*  72 */     obj.preventSoundInterruption = (buf.getByte(offset + 21) != 0);
/*  73 */     obj.startAttenuationDistance = buf.getFloatLE(offset + 22);
/*  74 */     obj.maxDistance = buf.getFloatLE(offset + 26);
/*  75 */     obj.audioCategory = buf.getIntLE(offset + 30);
/*     */     
/*  77 */     if ((nullBits & 0x1) != 0) {
/*  78 */       int varPos0 = offset + 42 + buf.getIntLE(offset + 34);
/*  79 */       int idLen = VarInt.peek(buf, varPos0);
/*  80 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  81 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  82 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*     */     } 
/*  84 */     if ((nullBits & 0x2) != 0) {
/*  85 */       int varPos1 = offset + 42 + buf.getIntLE(offset + 38);
/*  86 */       int layersCount = VarInt.peek(buf, varPos1);
/*  87 */       if (layersCount < 0) throw ProtocolException.negativeLength("Layers", layersCount); 
/*  88 */       if (layersCount > 4096000) throw ProtocolException.arrayTooLong("Layers", layersCount, 4096000); 
/*  89 */       int varIntLen = VarInt.length(buf, varPos1);
/*  90 */       if ((varPos1 + varIntLen) + layersCount * 42L > buf.readableBytes())
/*  91 */         throw ProtocolException.bufferTooSmall("Layers", varPos1 + varIntLen + layersCount * 42, buf.readableBytes()); 
/*  92 */       obj.layers = new SoundEventLayer[layersCount];
/*  93 */       int elemPos = varPos1 + varIntLen;
/*  94 */       for (int i = 0; i < layersCount; i++) {
/*  95 */         obj.layers[i] = SoundEventLayer.deserialize(buf, elemPos);
/*  96 */         elemPos += SoundEventLayer.computeBytesConsumed(buf, elemPos);
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 104 */     byte nullBits = buf.getByte(offset);
/* 105 */     int maxEnd = 42;
/* 106 */     if ((nullBits & 0x1) != 0) {
/* 107 */       int fieldOffset0 = buf.getIntLE(offset + 34);
/* 108 */       int pos0 = offset + 42 + fieldOffset0;
/* 109 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/* 110 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 112 */     if ((nullBits & 0x2) != 0) {
/* 113 */       int fieldOffset1 = buf.getIntLE(offset + 38);
/* 114 */       int pos1 = offset + 42 + fieldOffset1;
/* 115 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 116 */       for (int i = 0; i < arrLen; ) { pos1 += SoundEventLayer.computeBytesConsumed(buf, pos1); i++; }
/* 117 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 119 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 124 */     int startPos = buf.writerIndex();
/* 125 */     byte nullBits = 0;
/* 126 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 127 */     if (this.layers != null) nullBits = (byte)(nullBits | 0x2); 
/* 128 */     buf.writeByte(nullBits);
/*     */     
/* 130 */     buf.writeFloatLE(this.volume);
/* 131 */     buf.writeFloatLE(this.pitch);
/* 132 */     buf.writeFloatLE(this.musicDuckingVolume);
/* 133 */     buf.writeFloatLE(this.ambientDuckingVolume);
/* 134 */     buf.writeIntLE(this.maxInstance);
/* 135 */     buf.writeByte(this.preventSoundInterruption ? 1 : 0);
/* 136 */     buf.writeFloatLE(this.startAttenuationDistance);
/* 137 */     buf.writeFloatLE(this.maxDistance);
/* 138 */     buf.writeIntLE(this.audioCategory);
/*     */     
/* 140 */     int idOffsetSlot = buf.writerIndex();
/* 141 */     buf.writeIntLE(0);
/* 142 */     int layersOffsetSlot = buf.writerIndex();
/* 143 */     buf.writeIntLE(0);
/*     */     
/* 145 */     int varBlockStart = buf.writerIndex();
/* 146 */     if (this.id != null) {
/* 147 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/* 148 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*     */     } else {
/* 150 */       buf.setIntLE(idOffsetSlot, -1);
/*     */     } 
/* 152 */     if (this.layers != null) {
/* 153 */       buf.setIntLE(layersOffsetSlot, buf.writerIndex() - varBlockStart);
/* 154 */       if (this.layers.length > 4096000) throw ProtocolException.arrayTooLong("Layers", this.layers.length, 4096000);  VarInt.write(buf, this.layers.length); for (SoundEventLayer item : this.layers) item.serialize(buf); 
/*     */     } else {
/* 156 */       buf.setIntLE(layersOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 162 */     int size = 42;
/* 163 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/* 164 */     if (this.layers != null) {
/* 165 */       int layersSize = 0;
/* 166 */       for (SoundEventLayer elem : this.layers) layersSize += elem.computeSize(); 
/* 167 */       size += VarInt.size(this.layers.length) + layersSize;
/*     */     } 
/*     */     
/* 170 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 174 */     if (buffer.readableBytes() - offset < 42) {
/* 175 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 178 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 181 */     if ((nullBits & 0x1) != 0) {
/* 182 */       int idOffset = buffer.getIntLE(offset + 34);
/* 183 */       if (idOffset < 0) {
/* 184 */         return ValidationResult.error("Invalid offset for Id");
/*     */       }
/* 186 */       int pos = offset + 42 + idOffset;
/* 187 */       if (pos >= buffer.writerIndex()) {
/* 188 */         return ValidationResult.error("Offset out of bounds for Id");
/*     */       }
/* 190 */       int idLen = VarInt.peek(buffer, pos);
/* 191 */       if (idLen < 0) {
/* 192 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 194 */       if (idLen > 4096000) {
/* 195 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 197 */       pos += VarInt.length(buffer, pos);
/* 198 */       pos += idLen;
/* 199 */       if (pos > buffer.writerIndex()) {
/* 200 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/*     */     
/* 204 */     if ((nullBits & 0x2) != 0) {
/* 205 */       int layersOffset = buffer.getIntLE(offset + 38);
/* 206 */       if (layersOffset < 0) {
/* 207 */         return ValidationResult.error("Invalid offset for Layers");
/*     */       }
/* 209 */       int pos = offset + 42 + layersOffset;
/* 210 */       if (pos >= buffer.writerIndex()) {
/* 211 */         return ValidationResult.error("Offset out of bounds for Layers");
/*     */       }
/* 213 */       int layersCount = VarInt.peek(buffer, pos);
/* 214 */       if (layersCount < 0) {
/* 215 */         return ValidationResult.error("Invalid array count for Layers");
/*     */       }
/* 217 */       if (layersCount > 4096000) {
/* 218 */         return ValidationResult.error("Layers exceeds max length 4096000");
/*     */       }
/* 220 */       pos += VarInt.length(buffer, pos);
/* 221 */       for (int i = 0; i < layersCount; i++) {
/* 222 */         ValidationResult structResult = SoundEventLayer.validateStructure(buffer, pos);
/* 223 */         if (!structResult.isValid()) {
/* 224 */           return ValidationResult.error("Invalid SoundEventLayer in Layers[" + i + "]: " + structResult.error());
/*     */         }
/* 226 */         pos += SoundEventLayer.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 229 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SoundEvent clone() {
/* 233 */     SoundEvent copy = new SoundEvent();
/* 234 */     copy.id = this.id;
/* 235 */     copy.volume = this.volume;
/* 236 */     copy.pitch = this.pitch;
/* 237 */     copy.musicDuckingVolume = this.musicDuckingVolume;
/* 238 */     copy.ambientDuckingVolume = this.ambientDuckingVolume;
/* 239 */     copy.maxInstance = this.maxInstance;
/* 240 */     copy.preventSoundInterruption = this.preventSoundInterruption;
/* 241 */     copy.startAttenuationDistance = this.startAttenuationDistance;
/* 242 */     copy.maxDistance = this.maxDistance;
/* 243 */     copy.layers = (this.layers != null) ? (SoundEventLayer[])Arrays.<SoundEventLayer>stream(this.layers).map(e -> e.clone()).toArray(x$0 -> new SoundEventLayer[x$0]) : null;
/* 244 */     copy.audioCategory = this.audioCategory;
/* 245 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SoundEvent other;
/* 251 */     if (this == obj) return true; 
/* 252 */     if (obj instanceof SoundEvent) { other = (SoundEvent)obj; } else { return false; }
/* 253 */      return (Objects.equals(this.id, other.id) && this.volume == other.volume && this.pitch == other.pitch && this.musicDuckingVolume == other.musicDuckingVolume && this.ambientDuckingVolume == other.ambientDuckingVolume && this.maxInstance == other.maxInstance && this.preventSoundInterruption == other.preventSoundInterruption && this.startAttenuationDistance == other.startAttenuationDistance && this.maxDistance == other.maxDistance && Arrays.equals((Object[])this.layers, (Object[])other.layers) && this.audioCategory == other.audioCategory);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 258 */     int result = 1;
/* 259 */     result = 31 * result + Objects.hashCode(this.id);
/* 260 */     result = 31 * result + Float.hashCode(this.volume);
/* 261 */     result = 31 * result + Float.hashCode(this.pitch);
/* 262 */     result = 31 * result + Float.hashCode(this.musicDuckingVolume);
/* 263 */     result = 31 * result + Float.hashCode(this.ambientDuckingVolume);
/* 264 */     result = 31 * result + Integer.hashCode(this.maxInstance);
/* 265 */     result = 31 * result + Boolean.hashCode(this.preventSoundInterruption);
/* 266 */     result = 31 * result + Float.hashCode(this.startAttenuationDistance);
/* 267 */     result = 31 * result + Float.hashCode(this.maxDistance);
/* 268 */     result = 31 * result + Arrays.hashCode((Object[])this.layers);
/* 269 */     result = 31 * result + Integer.hashCode(this.audioCategory);
/* 270 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SoundEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */