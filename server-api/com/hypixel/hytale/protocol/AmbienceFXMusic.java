/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class AmbienceFXMusic
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public String[] tracks;
/*     */   public float volume;
/*     */   
/*     */   public AmbienceFXMusic() {}
/*     */   
/*     */   public AmbienceFXMusic(@Nullable String[] tracks, float volume) {
/*  27 */     this.tracks = tracks;
/*  28 */     this.volume = volume;
/*     */   }
/*     */   
/*     */   public AmbienceFXMusic(@Nonnull AmbienceFXMusic other) {
/*  32 */     this.tracks = other.tracks;
/*  33 */     this.volume = other.volume;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AmbienceFXMusic deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     AmbienceFXMusic obj = new AmbienceFXMusic();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.volume = buf.getFloatLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int tracksCount = VarInt.peek(buf, pos);
/*  44 */       if (tracksCount < 0) throw ProtocolException.negativeLength("Tracks", tracksCount); 
/*  45 */       if (tracksCount > 4096000) throw ProtocolException.arrayTooLong("Tracks", tracksCount, 4096000); 
/*  46 */       int tracksVarLen = VarInt.size(tracksCount);
/*  47 */       if ((pos + tracksVarLen) + tracksCount * 1L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Tracks", pos + tracksVarLen + tracksCount * 1, buf.readableBytes()); 
/*  49 */       pos += tracksVarLen;
/*  50 */       obj.tracks = new String[tracksCount];
/*  51 */       for (int i = 0; i < tracksCount; i++) {
/*  52 */         int strLen = VarInt.peek(buf, pos);
/*  53 */         if (strLen < 0) throw ProtocolException.negativeLength("tracks[" + i + "]", strLen); 
/*  54 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("tracks[" + i + "]", strLen, 4096000); 
/*  55 */         int strVarLen = VarInt.length(buf, pos);
/*  56 */         obj.tracks[i] = PacketIO.readVarString(buf, pos);
/*  57 */         pos += strVarLen + strLen;
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 5;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  73 */     byte nullBits = 0;
/*  74 */     if (this.tracks != null) nullBits = (byte)(nullBits | 0x1); 
/*  75 */     buf.writeByte(nullBits);
/*     */     
/*  77 */     buf.writeFloatLE(this.volume);
/*     */     
/*  79 */     if (this.tracks != null) { if (this.tracks.length > 4096000) throw ProtocolException.arrayTooLong("Tracks", this.tracks.length, 4096000);  VarInt.write(buf, this.tracks.length); for (String item : this.tracks) PacketIO.writeVarString(buf, item, 4096000);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 5;
/*  85 */     if (this.tracks != null) {
/*  86 */       int tracksSize = 0;
/*  87 */       for (String elem : this.tracks) tracksSize += PacketIO.stringSize(elem); 
/*  88 */       size += VarInt.size(this.tracks.length) + tracksSize;
/*     */     } 
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 5) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 5;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int tracksCount = VarInt.peek(buffer, pos);
/* 105 */       if (tracksCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Tracks");
/*     */       }
/* 108 */       if (tracksCount > 4096000) {
/* 109 */         return ValidationResult.error("Tracks exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < tracksCount; i++) {
/* 113 */         int strLen = VarInt.peek(buffer, pos);
/* 114 */         if (strLen < 0) {
/* 115 */           return ValidationResult.error("Invalid string length in Tracks");
/*     */         }
/* 117 */         pos += VarInt.length(buffer, pos);
/* 118 */         pos += strLen;
/* 119 */         if (pos > buffer.writerIndex()) {
/* 120 */           return ValidationResult.error("Buffer overflow reading string in Tracks");
/*     */         }
/*     */       } 
/*     */     } 
/* 124 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AmbienceFXMusic clone() {
/* 128 */     AmbienceFXMusic copy = new AmbienceFXMusic();
/* 129 */     copy.tracks = (this.tracks != null) ? Arrays.<String>copyOf(this.tracks, this.tracks.length) : null;
/* 130 */     copy.volume = this.volume;
/* 131 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AmbienceFXMusic other;
/* 137 */     if (this == obj) return true; 
/* 138 */     if (obj instanceof AmbienceFXMusic) { other = (AmbienceFXMusic)obj; } else { return false; }
/* 139 */      return (Arrays.equals((Object[])this.tracks, (Object[])other.tracks) && this.volume == other.volume);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 144 */     int result = 1;
/* 145 */     result = 31 * result + Arrays.hashCode((Object[])this.tracks);
/* 146 */     result = 31 * result + Float.hashCode(this.volume);
/* 147 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AmbienceFXMusic.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */