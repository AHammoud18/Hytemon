/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InteractionType;
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
/*     */ public class UpdateUnarmedInteractions
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 68;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 20480007;
/*     */   
/*     */   public int getId() {
/*  27 */     return 68;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<InteractionType, Integer> interactions;
/*     */ 
/*     */   
/*     */   public UpdateUnarmedInteractions(@Nonnull UpdateType type, @Nullable Map<InteractionType, Integer> interactions) {
/*  37 */     this.type = type;
/*  38 */     this.interactions = interactions;
/*     */   }
/*     */   
/*     */   public UpdateUnarmedInteractions(@Nonnull UpdateUnarmedInteractions other) {
/*  42 */     this.type = other.type;
/*  43 */     this.interactions = other.interactions;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateUnarmedInteractions deserialize(@Nonnull ByteBuf buf, int offset) {
/*  48 */     UpdateUnarmedInteractions obj = new UpdateUnarmedInteractions();
/*  49 */     byte nullBits = buf.getByte(offset);
/*  50 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  52 */     int pos = offset + 2;
/*  53 */     if ((nullBits & 0x1) != 0) { int interactionsCount = VarInt.peek(buf, pos);
/*  54 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/*  55 */       if (interactionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", interactionsCount, 4096000); 
/*  56 */       pos += VarInt.size(interactionsCount);
/*  57 */       obj.interactions = new HashMap<>(interactionsCount);
/*  58 */       for (int i = 0; i < interactionsCount; i++) {
/*  59 */         InteractionType key = InteractionType.fromValue(buf.getByte(pos)); pos++;
/*  60 */         int val = buf.getIntLE(pos); pos += 4;
/*  61 */         if (obj.interactions.put(key, Integer.valueOf(val)) != null)
/*  62 */           throw ProtocolException.duplicateKey("interactions", key); 
/*     */       }  }
/*     */     
/*  65 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  69 */     byte nullBits = buf.getByte(offset);
/*  70 */     int pos = offset + 2;
/*  71 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  72 */       for (int i = 0; i < dictLen; ) { pos++; pos += 4; i++; }  }
/*  73 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  79 */     byte nullBits = 0;
/*  80 */     if (this.interactions != null) nullBits = (byte)(nullBits | 0x1); 
/*  81 */     buf.writeByte(nullBits);
/*     */     
/*  83 */     buf.writeByte(this.type.getValue());
/*     */     
/*  85 */     if (this.interactions != null) { if (this.interactions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", this.interactions.size(), 4096000);  VarInt.write(buf, this.interactions.size()); for (Map.Entry<InteractionType, Integer> e : this.interactions.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  90 */     int size = 2;
/*  91 */     if (this.interactions != null) size += VarInt.size(this.interactions.size()) + this.interactions.size() * 5;
/*     */     
/*  93 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  97 */     if (buffer.readableBytes() - offset < 2) {
/*  98 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/* 101 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 103 */     int pos = offset + 2;
/*     */     
/* 105 */     if ((nullBits & 0x1) != 0) {
/* 106 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 107 */       if (interactionsCount < 0) {
/* 108 */         return ValidationResult.error("Invalid dictionary count for Interactions");
/*     */       }
/* 110 */       if (interactionsCount > 4096000) {
/* 111 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*     */       }
/* 113 */       pos += VarInt.length(buffer, pos);
/* 114 */       for (int i = 0; i < interactionsCount; i++) {
/* 115 */         pos++;
/*     */         
/* 117 */         pos += 4;
/* 118 */         if (pos > buffer.writerIndex()) {
/* 119 */           return ValidationResult.error("Buffer overflow reading value");
/*     */         }
/*     */       } 
/*     */     } 
/* 123 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateUnarmedInteractions clone() {
/* 127 */     UpdateUnarmedInteractions copy = new UpdateUnarmedInteractions();
/* 128 */     copy.type = this.type;
/* 129 */     copy.interactions = (this.interactions != null) ? new HashMap<>(this.interactions) : null;
/* 130 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateUnarmedInteractions other;
/* 136 */     if (this == obj) return true; 
/* 137 */     if (obj instanceof UpdateUnarmedInteractions) { other = (UpdateUnarmedInteractions)obj; } else { return false; }
/* 138 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.interactions, other.interactions));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     return Objects.hash(new Object[] { this.type, this.interactions });
/*     */   }
/*     */   
/*     */   public UpdateUnarmedInteractions() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateUnarmedInteractions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */