/*     */ package com.hypixel.hytale.protocol.packets.assets;
/*     */ import com.hypixel.hytale.protocol.CraftingRecipe;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.UpdateType;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateRecipes implements Packet {
/*     */   public static final int PACKET_ID = 60;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   
/*     */   public int getId() {
/*  27 */     return 60;
/*     */   }
/*     */   @Nonnull
/*  30 */   public UpdateType type = UpdateType.Init;
/*     */   
/*     */   @Nullable
/*     */   public Map<String, CraftingRecipe> recipes;
/*     */   @Nullable
/*     */   public String[] removedRecipes;
/*     */   
/*     */   public UpdateRecipes(@Nonnull UpdateType type, @Nullable Map<String, CraftingRecipe> recipes, @Nullable String[] removedRecipes) {
/*  38 */     this.type = type;
/*  39 */     this.recipes = recipes;
/*  40 */     this.removedRecipes = removedRecipes;
/*     */   }
/*     */   
/*     */   public UpdateRecipes(@Nonnull UpdateRecipes other) {
/*  44 */     this.type = other.type;
/*  45 */     this.recipes = other.recipes;
/*  46 */     this.removedRecipes = other.removedRecipes;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateRecipes deserialize(@Nonnull ByteBuf buf, int offset) {
/*  51 */     UpdateRecipes obj = new UpdateRecipes();
/*  52 */     byte nullBits = buf.getByte(offset);
/*  53 */     obj.type = UpdateType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  55 */     if ((nullBits & 0x1) != 0) {
/*  56 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  57 */       int recipesCount = VarInt.peek(buf, varPos0);
/*  58 */       if (recipesCount < 0) throw ProtocolException.negativeLength("Recipes", recipesCount); 
/*  59 */       if (recipesCount > 4096000) throw ProtocolException.dictionaryTooLarge("Recipes", recipesCount, 4096000); 
/*  60 */       int varIntLen = VarInt.length(buf, varPos0);
/*  61 */       obj.recipes = new HashMap<>(recipesCount);
/*  62 */       int dictPos = varPos0 + varIntLen;
/*  63 */       for (int i = 0; i < recipesCount; i++) {
/*  64 */         int keyLen = VarInt.peek(buf, dictPos);
/*  65 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  66 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  67 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  68 */         String key = PacketIO.readVarString(buf, dictPos);
/*  69 */         dictPos += keyVarLen + keyLen;
/*  70 */         CraftingRecipe val = CraftingRecipe.deserialize(buf, dictPos);
/*  71 */         dictPos += CraftingRecipe.computeBytesConsumed(buf, dictPos);
/*  72 */         if (obj.recipes.put(key, val) != null)
/*  73 */           throw ProtocolException.duplicateKey("recipes", key); 
/*     */       } 
/*     */     } 
/*  76 */     if ((nullBits & 0x2) != 0) {
/*  77 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  78 */       int removedRecipesCount = VarInt.peek(buf, varPos1);
/*  79 */       if (removedRecipesCount < 0) throw ProtocolException.negativeLength("RemovedRecipes", removedRecipesCount); 
/*  80 */       if (removedRecipesCount > 4096000) throw ProtocolException.arrayTooLong("RemovedRecipes", removedRecipesCount, 4096000); 
/*  81 */       int varIntLen = VarInt.length(buf, varPos1);
/*  82 */       if ((varPos1 + varIntLen) + removedRecipesCount * 1L > buf.readableBytes())
/*  83 */         throw ProtocolException.bufferTooSmall("RemovedRecipes", varPos1 + varIntLen + removedRecipesCount * 1, buf.readableBytes()); 
/*  84 */       obj.removedRecipes = new String[removedRecipesCount];
/*  85 */       int elemPos = varPos1 + varIntLen;
/*  86 */       for (int i = 0; i < removedRecipesCount; i++) {
/*  87 */         int strLen = VarInt.peek(buf, elemPos);
/*  88 */         if (strLen < 0) throw ProtocolException.negativeLength("removedRecipes[" + i + "]", strLen); 
/*  89 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("removedRecipes[" + i + "]", strLen, 4096000); 
/*  90 */         int strVarLen = VarInt.length(buf, elemPos);
/*  91 */         obj.removedRecipes[i] = PacketIO.readVarString(buf, elemPos);
/*  92 */         elemPos += strVarLen + strLen;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 100 */     byte nullBits = buf.getByte(offset);
/* 101 */     int maxEnd = 10;
/* 102 */     if ((nullBits & 0x1) != 0) {
/* 103 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/* 104 */       int pos0 = offset + 10 + fieldOffset0;
/* 105 */       int dictLen = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0);
/* 106 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl; pos0 += CraftingRecipe.computeBytesConsumed(buf, pos0); i++; }
/* 107 */        if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/* 109 */     if ((nullBits & 0x2) != 0) {
/* 110 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/* 111 */       int pos1 = offset + 10 + fieldOffset1;
/* 112 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1);
/* 113 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl; i++; }
/* 114 */        if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/* 116 */     return maxEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 122 */     int startPos = buf.writerIndex();
/* 123 */     byte nullBits = 0;
/* 124 */     if (this.recipes != null) nullBits = (byte)(nullBits | 0x1); 
/* 125 */     if (this.removedRecipes != null) nullBits = (byte)(nullBits | 0x2); 
/* 126 */     buf.writeByte(nullBits);
/*     */     
/* 128 */     buf.writeByte(this.type.getValue());
/*     */     
/* 130 */     int recipesOffsetSlot = buf.writerIndex();
/* 131 */     buf.writeIntLE(0);
/* 132 */     int removedRecipesOffsetSlot = buf.writerIndex();
/* 133 */     buf.writeIntLE(0);
/*     */     
/* 135 */     int varBlockStart = buf.writerIndex();
/* 136 */     if (this.recipes != null)
/* 137 */     { buf.setIntLE(recipesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 138 */       if (this.recipes.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Recipes", this.recipes.size(), 4096000);  VarInt.write(buf, this.recipes.size()); for (Map.Entry<String, CraftingRecipe> e : this.recipes.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); ((CraftingRecipe)e.getValue()).serialize(buf); }
/*     */        }
/* 140 */     else { buf.setIntLE(recipesOffsetSlot, -1); }
/*     */     
/* 142 */     if (this.removedRecipes != null) {
/* 143 */       buf.setIntLE(removedRecipesOffsetSlot, buf.writerIndex() - varBlockStart);
/* 144 */       if (this.removedRecipes.length > 4096000) throw ProtocolException.arrayTooLong("RemovedRecipes", this.removedRecipes.length, 4096000);  VarInt.write(buf, this.removedRecipes.length); for (String item : this.removedRecipes) PacketIO.writeVarString(buf, item, 4096000); 
/*     */     } else {
/* 146 */       buf.setIntLE(removedRecipesOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 152 */     int size = 10;
/* 153 */     if (this.recipes != null) {
/* 154 */       int recipesSize = 0;
/* 155 */       for (Map.Entry<String, CraftingRecipe> kvp : this.recipes.entrySet()) recipesSize += PacketIO.stringSize(kvp.getKey()) + ((CraftingRecipe)kvp.getValue()).computeSize(); 
/* 156 */       size += VarInt.size(this.recipes.size()) + recipesSize;
/*     */     } 
/* 158 */     if (this.removedRecipes != null) {
/* 159 */       int removedRecipesSize = 0;
/* 160 */       for (String elem : this.removedRecipes) removedRecipesSize += PacketIO.stringSize(elem); 
/* 161 */       size += VarInt.size(this.removedRecipes.length) + removedRecipesSize;
/*     */     } 
/*     */     
/* 164 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 168 */     if (buffer.readableBytes() - offset < 10) {
/* 169 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 172 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 175 */     if ((nullBits & 0x1) != 0) {
/* 176 */       int recipesOffset = buffer.getIntLE(offset + 2);
/* 177 */       if (recipesOffset < 0) {
/* 178 */         return ValidationResult.error("Invalid offset for Recipes");
/*     */       }
/* 180 */       int pos = offset + 10 + recipesOffset;
/* 181 */       if (pos >= buffer.writerIndex()) {
/* 182 */         return ValidationResult.error("Offset out of bounds for Recipes");
/*     */       }
/* 184 */       int recipesCount = VarInt.peek(buffer, pos);
/* 185 */       if (recipesCount < 0) {
/* 186 */         return ValidationResult.error("Invalid dictionary count for Recipes");
/*     */       }
/* 188 */       if (recipesCount > 4096000) {
/* 189 */         return ValidationResult.error("Recipes exceeds max length 4096000");
/*     */       }
/* 191 */       pos += VarInt.length(buffer, pos);
/* 192 */       for (int i = 0; i < recipesCount; i++) {
/* 193 */         int keyLen = VarInt.peek(buffer, pos);
/* 194 */         if (keyLen < 0) {
/* 195 */           return ValidationResult.error("Invalid string length for key");
/*     */         }
/* 197 */         if (keyLen > 4096000) {
/* 198 */           return ValidationResult.error("key exceeds max length 4096000");
/*     */         }
/* 200 */         pos += VarInt.length(buffer, pos);
/* 201 */         pos += keyLen;
/* 202 */         if (pos > buffer.writerIndex()) {
/* 203 */           return ValidationResult.error("Buffer overflow reading key");
/*     */         }
/* 205 */         pos += CraftingRecipe.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 210 */     if ((nullBits & 0x2) != 0) {
/* 211 */       int removedRecipesOffset = buffer.getIntLE(offset + 6);
/* 212 */       if (removedRecipesOffset < 0) {
/* 213 */         return ValidationResult.error("Invalid offset for RemovedRecipes");
/*     */       }
/* 215 */       int pos = offset + 10 + removedRecipesOffset;
/* 216 */       if (pos >= buffer.writerIndex()) {
/* 217 */         return ValidationResult.error("Offset out of bounds for RemovedRecipes");
/*     */       }
/* 219 */       int removedRecipesCount = VarInt.peek(buffer, pos);
/* 220 */       if (removedRecipesCount < 0) {
/* 221 */         return ValidationResult.error("Invalid array count for RemovedRecipes");
/*     */       }
/* 223 */       if (removedRecipesCount > 4096000) {
/* 224 */         return ValidationResult.error("RemovedRecipes exceeds max length 4096000");
/*     */       }
/* 226 */       pos += VarInt.length(buffer, pos);
/* 227 */       for (int i = 0; i < removedRecipesCount; i++) {
/* 228 */         int strLen = VarInt.peek(buffer, pos);
/* 229 */         if (strLen < 0) {
/* 230 */           return ValidationResult.error("Invalid string length in RemovedRecipes");
/*     */         }
/* 232 */         pos += VarInt.length(buffer, pos);
/* 233 */         pos += strLen;
/* 234 */         if (pos > buffer.writerIndex()) {
/* 235 */           return ValidationResult.error("Buffer overflow reading string in RemovedRecipes");
/*     */         }
/*     */       } 
/*     */     } 
/* 239 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateRecipes clone() {
/* 243 */     UpdateRecipes copy = new UpdateRecipes();
/* 244 */     copy.type = this.type;
/* 245 */     if (this.recipes != null) {
/* 246 */       Map<String, CraftingRecipe> m = new HashMap<>();
/* 247 */       for (Map.Entry<String, CraftingRecipe> e : this.recipes.entrySet()) m.put(e.getKey(), ((CraftingRecipe)e.getValue()).clone()); 
/* 248 */       copy.recipes = m;
/*     */     } 
/* 250 */     copy.removedRecipes = (this.removedRecipes != null) ? Arrays.<String>copyOf(this.removedRecipes, this.removedRecipes.length) : null;
/* 251 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateRecipes other;
/* 257 */     if (this == obj) return true; 
/* 258 */     if (obj instanceof UpdateRecipes) { other = (UpdateRecipes)obj; } else { return false; }
/* 259 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.recipes, other.recipes) && Arrays.equals((Object[])this.removedRecipes, (Object[])other.removedRecipes));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 264 */     int result = 1;
/* 265 */     result = 31 * result + Objects.hashCode(this.type);
/* 266 */     result = 31 * result + Objects.hashCode(this.recipes);
/* 267 */     result = 31 * result + Arrays.hashCode((Object[])this.removedRecipes);
/* 268 */     return result;
/*     */   }
/*     */   
/*     */   public UpdateRecipes() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\assets\UpdateRecipes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */