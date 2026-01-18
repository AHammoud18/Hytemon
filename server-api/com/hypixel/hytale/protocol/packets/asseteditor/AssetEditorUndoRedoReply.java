/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorUndoRedoReply
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 351;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int token;
/*     */   @Nullable
/*     */   public JsonUpdateCommand command;
/*     */   
/*     */   public int getId() {
/*  25 */     return 351;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorUndoRedoReply() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorUndoRedoReply(int token, @Nullable JsonUpdateCommand command) {
/*  35 */     this.token = token;
/*  36 */     this.command = command;
/*     */   }
/*     */   
/*     */   public AssetEditorUndoRedoReply(@Nonnull AssetEditorUndoRedoReply other) {
/*  40 */     this.token = other.token;
/*  41 */     this.command = other.command;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorUndoRedoReply deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorUndoRedoReply obj = new AssetEditorUndoRedoReply();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.command = JsonUpdateCommand.deserialize(buf, pos);
/*  52 */       pos += JsonUpdateCommand.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 5;
/*  60 */     if ((nullBits & 0x1) != 0) pos += JsonUpdateCommand.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.command != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     buf.writeIntLE(this.token);
/*     */     
/*  73 */     if (this.command != null) this.command.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 5;
/*  79 */     if (this.command != null) size += this.command.computeSize();
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 5) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 5;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       ValidationResult commandResult = JsonUpdateCommand.validateStructure(buffer, pos);
/*  95 */       if (!commandResult.isValid()) {
/*  96 */         return ValidationResult.error("Invalid Command: " + commandResult.error());
/*     */       }
/*  98 */       pos += JsonUpdateCommand.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 100 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorUndoRedoReply clone() {
/* 104 */     AssetEditorUndoRedoReply copy = new AssetEditorUndoRedoReply();
/* 105 */     copy.token = this.token;
/* 106 */     copy.command = (this.command != null) ? this.command.clone() : null;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorUndoRedoReply other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof AssetEditorUndoRedoReply) { other = (AssetEditorUndoRedoReply)obj; } else { return false; }
/* 115 */      return (this.token == other.token && Objects.equals(this.command, other.command));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.command });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorUndoRedoReply.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */