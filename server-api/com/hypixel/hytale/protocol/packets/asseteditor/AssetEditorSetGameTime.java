/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InstantData;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorSetGameTime
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 352;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 14;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 14;
/*     */   public static final int MAX_SIZE = 14;
/*     */   @Nullable
/*     */   public InstantData gameTime;
/*     */   public boolean paused;
/*     */   
/*     */   public int getId() {
/*  25 */     return 352;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorSetGameTime() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorSetGameTime(@Nullable InstantData gameTime, boolean paused) {
/*  35 */     this.gameTime = gameTime;
/*  36 */     this.paused = paused;
/*     */   }
/*     */   
/*     */   public AssetEditorSetGameTime(@Nonnull AssetEditorSetGameTime other) {
/*  40 */     this.gameTime = other.gameTime;
/*  41 */     this.paused = other.paused;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorSetGameTime deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorSetGameTime obj = new AssetEditorSetGameTime();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     if ((nullBits & 0x1) != 0) obj.gameTime = InstantData.deserialize(buf, offset + 1); 
/*  49 */     obj.paused = (buf.getByte(offset + 13) != 0);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 14;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     byte nullBits = 0;
/*  62 */     if (this.gameTime != null) nullBits = (byte)(nullBits | 0x1); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     if (this.gameTime != null) { this.gameTime.serialize(buf); } else { buf.writeZero(12); }
/*  66 */      buf.writeByte(this.paused ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  72 */     return 14;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 14) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 14 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorSetGameTime clone() {
/*  85 */     AssetEditorSetGameTime copy = new AssetEditorSetGameTime();
/*  86 */     copy.gameTime = (this.gameTime != null) ? this.gameTime.clone() : null;
/*  87 */     copy.paused = this.paused;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorSetGameTime other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof AssetEditorSetGameTime) { other = (AssetEditorSetGameTime)obj; } else { return false; }
/*  96 */      return (Objects.equals(this.gameTime, other.gameTime) && this.paused == other.paused);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { this.gameTime, Boolean.valueOf(this.paused) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorSetGameTime.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */