/*     */ package com.hypixel.hytale.protocol.packets.interface_;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.CraftingRecipe;
/*     */ import com.hypixel.hytale.protocol.Packet;
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
/*     */ 
/*     */ public class UpdateKnownRecipes
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 228;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   
/*     */   public int getId() {
/*  26 */     return 228;
/*     */   }
/*     */   public static final int VARIABLE_FIELD_COUNT = 1; public static final int VARIABLE_BLOCK_START = 1; public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public Map<String, CraftingRecipe> known;
/*     */   
/*     */   public UpdateKnownRecipes() {}
/*     */   
/*     */   public UpdateKnownRecipes(@Nullable Map<String, CraftingRecipe> known) {
/*  35 */     this.known = known;
/*     */   }
/*     */   
/*     */   public UpdateKnownRecipes(@Nonnull UpdateKnownRecipes other) {
/*  39 */     this.known = other.known;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateKnownRecipes deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     UpdateKnownRecipes obj = new UpdateKnownRecipes();
/*  45 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  47 */     int pos = offset + 1;
/*  48 */     if ((nullBits & 0x1) != 0) { int knownCount = VarInt.peek(buf, pos);
/*  49 */       if (knownCount < 0) throw ProtocolException.negativeLength("Known", knownCount); 
/*  50 */       if (knownCount > 4096000) throw ProtocolException.dictionaryTooLarge("Known", knownCount, 4096000); 
/*  51 */       pos += VarInt.size(knownCount);
/*  52 */       obj.known = new HashMap<>(knownCount);
/*  53 */       for (int i = 0; i < knownCount; i++) {
/*  54 */         int keyLen = VarInt.peek(buf, pos);
/*  55 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  56 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  57 */         int keyVarLen = VarInt.length(buf, pos);
/*  58 */         String key = PacketIO.readVarString(buf, pos);
/*  59 */         pos += keyVarLen + keyLen;
/*  60 */         CraftingRecipe val = CraftingRecipe.deserialize(buf, pos);
/*  61 */         pos += CraftingRecipe.computeBytesConsumed(buf, pos);
/*  62 */         if (obj.known.put(key, val) != null)
/*  63 */           throw ProtocolException.duplicateKey("known", key); 
/*     */       }  }
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int pos = offset + 1;
/*  72 */     if ((nullBits & 0x1) != 0) { int dictLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  73 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; pos += CraftingRecipe.computeBytesConsumed(buf, pos); i++; }  }
/*  74 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  80 */     byte nullBits = 0;
/*  81 */     if (this.known != null) nullBits = (byte)(nullBits | 0x1); 
/*  82 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  85 */     if (this.known != null) { if (this.known.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Known", this.known.size(), 4096000);  VarInt.write(buf, this.known.size()); for (Map.Entry<String, CraftingRecipe> e : this.known.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((CraftingRecipe)e.getValue()).serialize(buf); }
/*     */        }
/*     */   
/*     */   }
/*     */   public int computeSize() {
/*  90 */     int size = 1;
/*  91 */     if (this.known != null) {
/*  92 */       int knownSize = 0;
/*  93 */       for (Map.Entry<String, CraftingRecipe> kvp : this.known.entrySet()) knownSize += PacketIO.stringSize(kvp.getKey()) + ((CraftingRecipe)kvp.getValue()).computeSize(); 
/*  94 */       size += VarInt.size(this.known.size()) + knownSize;
/*     */     } 
/*     */     
/*  97 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 101 */     if (buffer.readableBytes() - offset < 1) {
/* 102 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/* 105 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 107 */     int pos = offset + 1;
/*     */     
/* 109 */     if ((nullBits & 0x1) != 0) {
/* 110 */       int knownCount = VarInt.peek(buffer, pos);
/* 111 */       if (knownCount < 0) {
/* 112 */         return ValidationResult.error("Invalid dictionary count for Known");
/*     */       }
/* 114 */       if (knownCount > 4096000) {
/* 115 */         return ValidationResult.error("Known exceeds max length 4096000");
/*     */       }
/* 117 */       pos += VarInt.length(buffer, pos);
/* 118 */       for (int i = 0; i < knownCount; i++) {
/* 119 */         int keyLen = VarInt.peek(buffer, pos);
/* 120 */         if (keyLen < 0) {
/* 121 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 123 */         if (keyLen > 4096000) {
/* 124 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 126 */         pos += VarInt.length(buffer, pos);
/* 127 */         pos += keyLen;
/* 128 */         if (pos > buffer.writerIndex()) {
/* 129 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 131 */         pos += CraftingRecipe.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateKnownRecipes clone() {
/* 139 */     UpdateKnownRecipes copy = new UpdateKnownRecipes();
/* 140 */     if (this.known != null) {
/* 141 */       Map<String, CraftingRecipe> m = new HashMap<>();
/* 142 */       for (Map.Entry<String, CraftingRecipe> e : this.known.entrySet()) m.put(e.getKey(), ((CraftingRecipe)e.getValue()).clone()); 
/* 143 */       copy.known = m;
/*     */     } 
/* 145 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateKnownRecipes other;
/* 151 */     if (this == obj) return true; 
/* 152 */     if (obj instanceof UpdateKnownRecipes) { other = (UpdateKnownRecipes)obj; } else { return false; }
/* 153 */      return Objects.equals(this.known, other.known);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 158 */     return Objects.hash(new Object[] { this.known });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\interface_\UpdateKnownRecipes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */