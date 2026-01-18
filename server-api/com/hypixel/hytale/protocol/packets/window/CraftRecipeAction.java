/*     */ package com.hypixel.hytale.protocol.packets.window;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class CraftRecipeAction
/*     */   extends WindowAction
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 16384010;
/*     */   @Nullable
/*     */   public String recipeId;
/*     */   public int quantity;
/*     */   
/*     */   public CraftRecipeAction() {}
/*     */   
/*     */   public CraftRecipeAction(@Nullable String recipeId, int quantity) {
/*  27 */     this.recipeId = recipeId;
/*  28 */     this.quantity = quantity;
/*     */   }
/*     */   
/*     */   public CraftRecipeAction(@Nonnull CraftRecipeAction other) {
/*  32 */     this.recipeId = other.recipeId;
/*  33 */     this.quantity = other.quantity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CraftRecipeAction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     CraftRecipeAction obj = new CraftRecipeAction();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.quantity = buf.getIntLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 5;
/*  43 */     if ((nullBits & 0x1) != 0) { int recipeIdLen = VarInt.peek(buf, pos);
/*  44 */       if (recipeIdLen < 0) throw ProtocolException.negativeLength("RecipeId", recipeIdLen); 
/*  45 */       if (recipeIdLen > 4096000) throw ProtocolException.stringTooLong("RecipeId", recipeIdLen, 4096000); 
/*  46 */       int recipeIdVarLen = VarInt.length(buf, pos);
/*  47 */       obj.recipeId = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += recipeIdVarLen + recipeIdLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 5;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  63 */     int startPos = buf.writerIndex();
/*  64 */     byte nullBits = 0;
/*  65 */     if (this.recipeId != null) nullBits = (byte)(nullBits | 0x1); 
/*  66 */     buf.writeByte(nullBits);
/*     */     
/*  68 */     buf.writeIntLE(this.quantity);
/*     */     
/*  70 */     if (this.recipeId != null) PacketIO.writeVarString(buf, this.recipeId, 4096000); 
/*  71 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  77 */     int size = 5;
/*  78 */     if (this.recipeId != null) size += PacketIO.stringSize(this.recipeId);
/*     */     
/*  80 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  84 */     if (buffer.readableBytes() - offset < 5) {
/*  85 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  88 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  90 */     int pos = offset + 5;
/*     */     
/*  92 */     if ((nullBits & 0x1) != 0) {
/*  93 */       int recipeIdLen = VarInt.peek(buffer, pos);
/*  94 */       if (recipeIdLen < 0) {
/*  95 */         return ValidationResult.error("Invalid string length for RecipeId");
/*     */       }
/*  97 */       if (recipeIdLen > 4096000) {
/*  98 */         return ValidationResult.error("RecipeId exceeds max length 4096000");
/*     */       }
/* 100 */       pos += VarInt.length(buffer, pos);
/* 101 */       pos += recipeIdLen;
/* 102 */       if (pos > buffer.writerIndex()) {
/* 103 */         return ValidationResult.error("Buffer overflow reading RecipeId");
/*     */       }
/*     */     } 
/* 106 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CraftRecipeAction clone() {
/* 110 */     CraftRecipeAction copy = new CraftRecipeAction();
/* 111 */     copy.recipeId = this.recipeId;
/* 112 */     copy.quantity = this.quantity;
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CraftRecipeAction other;
/* 119 */     if (this == obj) return true; 
/* 120 */     if (obj instanceof CraftRecipeAction) { other = (CraftRecipeAction)obj; } else { return false; }
/* 121 */      return (Objects.equals(this.recipeId, other.recipeId) && this.quantity == other.quantity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return Objects.hash(new Object[] { this.recipeId, Integer.valueOf(this.quantity) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\window\CraftRecipeAction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */