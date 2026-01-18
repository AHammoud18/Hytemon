/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.SoundEvent;
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
/*     */ public class UpdateSoundEvents
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 65;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 65;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   public int maxId;
/*     */   
/*     */   @Nullable
/*     */   public Map<Integer, SoundEvent> soundEvents;
/*     */   
/*     */   public UpdateSoundEvents(@Nonnull UpdateType type, int maxId, @Nullable Map<Integer, SoundEvent> soundEvents) {
/*  38 */     this.type = type;
/*  39 */     this.maxId = maxId;
/*  40 */     this.soundEvents = soundEvents;
/*     */   }
/*     */   
/*     */   public UpdateSoundEvents(@Nonnull UpdateSoundEvents other) {
/*  44 */     this.type = other.type;
/*  45 */     this.maxId = other.maxId;
/*  46 */     this.soundEvents = other.soundEvents;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateSoundEvents deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateSoundEvents obj = new UpdateSoundEvents();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*  54 */     obj.maxId = buf.getIntLE(offset + 2);
/*     */     
/*  56 */     int pos = offset + 6;
/*  57 */     if ((nullBits & 0x1) != 0) { int soundEventsCount = VarInt.peek(buf, pos);
/*  58 */       if (soundEventsCount < 0) throw ProtocolException.negativeLength("SoundEvents", soundEventsCount); 
/*  59 */       if (soundEventsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEvents", soundEventsCount, 4096000); 
/*  60 */       pos += VarInt.size(soundEventsCount);
/*  61 */       obj.soundEvents = new HashMap<>(soundEventsCount);
/*  62 */       for (int i = 0; i < soundEventsCount; i++) {
/*  63 */         int key = buf.getIntLE(pos); pos += 4;
/*  64 */         SoundEvent val = SoundEvent.deserialize(buf, pos);
/*  65 */         pos += SoundEvent.computeBytesConsumed(buf, pos);
/*  66 */         if (obj.soundEvents.put(Integer.valueOf(key), val) != null)
/*  67 */           throw ProtocolException.duplicateKey("soundEvents", Integer.valueOf(key)); 
/*     */       }  }
/*     */     
/*  70 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  74 */     byte nullBits = buf.getByte(offset);
/*  75 */     int pos = offset + 6;
/*  76 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  77 */       for (int i = 0; i < dictLen; ) { pos += 4; pos += SoundEvent.computeBytesConsumed(buf, pos); i++; }  }
/*  78 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  84 */     byte nullBits = 0;
/*  85 */     if (this.soundEvents != null) nullBits = (byte)(nullBits | 0x1); 
/*  86 */     buf.writeByte(nullBits);
/*     */     
/*  88 */     buf.writeByte(this.type.getValue());
/*  89 */     buf.writeIntLE(this.maxId);
/*     */     
/*  91 */     if (this.soundEvents != null) { if (this.soundEvents.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SoundEvents", this.soundEvents.size(), 4096000);  VarInt.write(buf, this.soundEvents.size()); for (Map.Entry<Integer, SoundEvent> e : this.soundEvents.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); ((SoundEvent)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  96 */     int size = 6;
/*  97 */     if (this.soundEvents != null) {
/*  98 */       int soundEventsSize = 0;
/*  99 */       for (Map.Entry<Integer, SoundEvent> kvp : this.soundEvents.entrySet()) soundEventsSize += 4 + ((SoundEvent)kvp.getValue()).computeSize(); 
/* 100 */       size += VarInt.size(this.soundEvents.size()) + soundEventsSize;
/*     */     } 
/*     */     
/* 103 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 107 */     if (buffer.readableBytes() - offset < 6) {
/* 108 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/* 111 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 113 */     int pos = offset + 6;
/*     */     
/* 115 */     if ((nullBits & 0x1) != 0) {
/* 116 */       int soundEventsCount = VarInt.peek(buffer, pos);
/* 117 */       if (soundEventsCount < 0) {
/* 118 */         return ValidationResult.error("Invalid dictionary count for SoundEvents");
/*     */       }
/* 120 */       if (soundEventsCount > 4096000) {
/* 121 */         return ValidationResult.error("SoundEvents exceeds max length 4096000");
/*     */       }
/* 123 */       pos += VarInt.length(buffer, pos);
/* 124 */       for (int i = 0; i < soundEventsCount; i++) {
/* 125 */         pos += 4;
/* 126 */         if (pos > buffer.writerIndex()) {
/* 127 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 129 */         pos += SoundEvent.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateSoundEvents clone() {
/* 137 */     UpdateSoundEvents copy = new UpdateSoundEvents();
/* 138 */     copy.type = this.type;
/* 139 */     copy.maxId = this.maxId;
/* 140 */     if (this.soundEvents != null) {
/* 141 */       Map<Integer, SoundEvent> m = new HashMap<>();
/* 142 */       for (Map.Entry<Integer, SoundEvent> e : this.soundEvents.entrySet()) m.put(e.getKey(), ((SoundEvent)e.getValue()).clone()); 
/* 143 */       copy.soundEvents = m;
/*     */     } 
/* 145 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateSoundEvents other;
/* 151 */     if (this == obj) return true; 
/* 152 */     if (obj instanceof UpdateSoundEvents) { other = (UpdateSoundEvents)obj; } else { return false; }
/* 153 */      return (Objects.equals(this.type, other.type) && this.maxId == other.maxId && Objects.equals(this.soundEvents, other.soundEvents));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 158 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.maxId), this.soundEvents });
/*     */   }
/*     */   
/*     */   public UpdateSoundEvents() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateSoundEvents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */