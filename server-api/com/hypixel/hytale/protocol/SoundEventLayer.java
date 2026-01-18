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
/*     */ public class SoundEventLayer {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 42;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 42;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public float volume;
/*     */   public float startDelay;
/*     */   public boolean looping;
/*     */   public int probability;
/*     */   public float probabilityRerollDelay;
/*     */   public int roundRobinHistorySize;
/*     */   @Nullable
/*     */   public SoundEventLayerRandomSettings randomSettings;
/*     */   @Nullable
/*     */   public String[] files;
/*     */   
/*     */   public SoundEventLayer() {}
/*     */   
/*     */   public SoundEventLayer(float volume, float startDelay, boolean looping, int probability, float probabilityRerollDelay, int roundRobinHistorySize, @Nullable SoundEventLayerRandomSettings randomSettings, @Nullable String[] files) {
/*  33 */     this.volume = volume;
/*  34 */     this.startDelay = startDelay;
/*  35 */     this.looping = looping;
/*  36 */     this.probability = probability;
/*  37 */     this.probabilityRerollDelay = probabilityRerollDelay;
/*  38 */     this.roundRobinHistorySize = roundRobinHistorySize;
/*  39 */     this.randomSettings = randomSettings;
/*  40 */     this.files = files;
/*     */   }
/*     */   
/*     */   public SoundEventLayer(@Nonnull SoundEventLayer other) {
/*  44 */     this.volume = other.volume;
/*  45 */     this.startDelay = other.startDelay;
/*  46 */     this.looping = other.looping;
/*  47 */     this.probability = other.probability;
/*  48 */     this.probabilityRerollDelay = other.probabilityRerollDelay;
/*  49 */     this.roundRobinHistorySize = other.roundRobinHistorySize;
/*  50 */     this.randomSettings = other.randomSettings;
/*  51 */     this.files = other.files;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SoundEventLayer deserialize(@Nonnull ByteBuf buf, int offset) {
/*  56 */     SoundEventLayer obj = new SoundEventLayer();
/*  57 */     byte nullBits = buf.getByte(offset);
/*  58 */     obj.volume = buf.getFloatLE(offset + 1);
/*  59 */     obj.startDelay = buf.getFloatLE(offset + 5);
/*  60 */     obj.looping = (buf.getByte(offset + 9) != 0);
/*  61 */     obj.probability = buf.getIntLE(offset + 10);
/*  62 */     obj.probabilityRerollDelay = buf.getFloatLE(offset + 14);
/*  63 */     obj.roundRobinHistorySize = buf.getIntLE(offset + 18);
/*  64 */     if ((nullBits & 0x1) != 0) obj.randomSettings = SoundEventLayerRandomSettings.deserialize(buf, offset + 22);
/*     */     
/*  66 */     int pos = offset + 42;
/*  67 */     if ((nullBits & 0x2) != 0) { int filesCount = VarInt.peek(buf, pos);
/*  68 */       if (filesCount < 0) throw ProtocolException.negativeLength("Files", filesCount); 
/*  69 */       if (filesCount > 4096000) throw ProtocolException.arrayTooLong("Files", filesCount, 4096000); 
/*  70 */       int filesVarLen = VarInt.size(filesCount);
/*  71 */       if ((pos + filesVarLen) + filesCount * 1L > buf.readableBytes())
/*  72 */         throw ProtocolException.bufferTooSmall("Files", pos + filesVarLen + filesCount * 1, buf.readableBytes()); 
/*  73 */       pos += filesVarLen;
/*  74 */       obj.files = new String[filesCount];
/*  75 */       for (int i = 0; i < filesCount; i++) {
/*  76 */         int strLen = VarInt.peek(buf, pos);
/*  77 */         if (strLen < 0) throw ProtocolException.negativeLength("files[" + i + "]", strLen); 
/*  78 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("files[" + i + "]", strLen, 4096000); 
/*  79 */         int strVarLen = VarInt.length(buf, pos);
/*  80 */         obj.files[i] = PacketIO.readVarString(buf, pos);
/*  81 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  84 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  88 */     byte nullBits = buf.getByte(offset);
/*  89 */     int pos = offset + 42;
/*  90 */     if ((nullBits & 0x2) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  91 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  92 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  97 */     byte nullBits = 0;
/*  98 */     if (this.randomSettings != null) nullBits = (byte)(nullBits | 0x1); 
/*  99 */     if (this.files != null) nullBits = (byte)(nullBits | 0x2); 
/* 100 */     buf.writeByte(nullBits);
/*     */     
/* 102 */     buf.writeFloatLE(this.volume);
/* 103 */     buf.writeFloatLE(this.startDelay);
/* 104 */     buf.writeByte(this.looping ? 1 : 0);
/* 105 */     buf.writeIntLE(this.probability);
/* 106 */     buf.writeFloatLE(this.probabilityRerollDelay);
/* 107 */     buf.writeIntLE(this.roundRobinHistorySize);
/* 108 */     if (this.randomSettings != null) { this.randomSettings.serialize(buf); } else { buf.writeZero(20); }
/*     */     
/* 110 */     if (this.files != null) { if (this.files.length > 4096000) throw ProtocolException.arrayTooLong("Files", this.files.length, 4096000);  VarInt.write(buf, this.files.length); for (String item : this.files) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 115 */     int size = 42;
/* 116 */     if (this.files != null) {
/* 117 */       int filesSize = 0;
/* 118 */       for (String elem : this.files) filesSize += PacketIO.stringSize(elem); 
/* 119 */       size += VarInt.size(this.files.length) + filesSize;
/*     */     } 
/*     */     
/* 122 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 126 */     if (buffer.readableBytes() - offset < 42) {
/* 127 */       return ValidationResult.error("Buffer too small: expected at least 42 bytes");
/*     */     }
/*     */     
/* 130 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 132 */     int pos = offset + 42;
/*     */     
/* 134 */     if ((nullBits & 0x2) != 0) {
/* 135 */       int filesCount = VarInt.peek(buffer, pos);
/* 136 */       if (filesCount < 0) {
/* 137 */         return ValidationResult.error("Invalid array count for Files");
/*     */       }
/* 139 */       if (filesCount > 4096000) {
/* 140 */         return ValidationResult.error("Files exceeds max length 4096000");
/*     */       }
/* 142 */       pos += VarInt.length(buffer, pos);
/* 143 */       for (int i = 0; i < filesCount; i++) {
/* 144 */         int strLen = VarInt.peek(buffer, pos);
/* 145 */         if (strLen < 0) {
/* 146 */           return ValidationResult.error("Invalid string length in Files");
/*     */         }
/* 148 */         pos += VarInt.length(buffer, pos);
/* 149 */         pos += strLen;
/* 150 */         if (pos > buffer.writerIndex()) {
/* 151 */           return ValidationResult.error("Buffer overflow reading string in Files");
/*     */         }
/*     */       } 
/*     */     } 
/* 155 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SoundEventLayer clone() {
/* 159 */     SoundEventLayer copy = new SoundEventLayer();
/* 160 */     copy.volume = this.volume;
/* 161 */     copy.startDelay = this.startDelay;
/* 162 */     copy.looping = this.looping;
/* 163 */     copy.probability = this.probability;
/* 164 */     copy.probabilityRerollDelay = this.probabilityRerollDelay;
/* 165 */     copy.roundRobinHistorySize = this.roundRobinHistorySize;
/* 166 */     copy.randomSettings = (this.randomSettings != null) ? this.randomSettings.clone() : null;
/* 167 */     copy.files = (this.files != null) ? Arrays.<String>copyOf(this.files, this.files.length) : null;
/* 168 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SoundEventLayer other;
/* 174 */     if (this == obj) return true; 
/* 175 */     if (obj instanceof SoundEventLayer) { other = (SoundEventLayer)obj; } else { return false; }
/* 176 */      return (this.volume == other.volume && this.startDelay == other.startDelay && this.looping == other.looping && this.probability == other.probability && this.probabilityRerollDelay == other.probabilityRerollDelay && this.roundRobinHistorySize == other.roundRobinHistorySize && Objects.equals(this.randomSettings, other.randomSettings) && Arrays.equals((Object[])this.files, (Object[])other.files));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 181 */     int result = 1;
/* 182 */     result = 31 * result + Float.hashCode(this.volume);
/* 183 */     result = 31 * result + Float.hashCode(this.startDelay);
/* 184 */     result = 31 * result + Boolean.hashCode(this.looping);
/* 185 */     result = 31 * result + Integer.hashCode(this.probability);
/* 186 */     result = 31 * result + Float.hashCode(this.probabilityRerollDelay);
/* 187 */     result = 31 * result + Integer.hashCode(this.roundRobinHistorySize);
/* 188 */     result = 31 * result + Objects.hashCode(this.randomSettings);
/* 189 */     result = 31 * result + Arrays.hashCode((Object[])this.files);
/* 190 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SoundEventLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */