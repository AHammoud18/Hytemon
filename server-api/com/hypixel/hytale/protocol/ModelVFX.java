/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ModelVFX {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 49;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 49;
/*     */   public static final int MAX_SIZE = 16384054;
/*     */   @Nullable
/*     */   public String id;
/*     */   @Nonnull
/*  21 */   public SwitchTo switchTo = SwitchTo.Disappear; @Nonnull
/*  22 */   public EffectDirection effectDirection = EffectDirection.None;
/*     */   public float animationDuration;
/*     */   @Nonnull
/*  25 */   public LoopOption loopOption = LoopOption.PlayOnce; @Nullable public Vector2f animationRange; @Nonnull
/*  26 */   public CurveType curveType = CurveType.Linear; @Nullable
/*     */   public Color highlightColor;
/*     */   public float highlightThickness;
/*     */   public boolean useBloomOnHighlight;
/*     */   public boolean useProgessiveHighlight;
/*     */   @Nullable
/*     */   public Vector2f noiseScale;
/*     */   @Nullable
/*     */   public Vector2f noiseScrollSpeed;
/*     */   @Nullable
/*     */   public Color postColor;
/*     */   public float postColorOpacity;
/*     */   
/*     */   public ModelVFX(@Nullable String id, @Nonnull SwitchTo switchTo, @Nonnull EffectDirection effectDirection, float animationDuration, @Nullable Vector2f animationRange, @Nonnull LoopOption loopOption, @Nonnull CurveType curveType, @Nullable Color highlightColor, float highlightThickness, boolean useBloomOnHighlight, boolean useProgessiveHighlight, @Nullable Vector2f noiseScale, @Nullable Vector2f noiseScrollSpeed, @Nullable Color postColor, float postColorOpacity) {
/*  40 */     this.id = id;
/*  41 */     this.switchTo = switchTo;
/*  42 */     this.effectDirection = effectDirection;
/*  43 */     this.animationDuration = animationDuration;
/*  44 */     this.animationRange = animationRange;
/*  45 */     this.loopOption = loopOption;
/*  46 */     this.curveType = curveType;
/*  47 */     this.highlightColor = highlightColor;
/*  48 */     this.highlightThickness = highlightThickness;
/*  49 */     this.useBloomOnHighlight = useBloomOnHighlight;
/*  50 */     this.useProgessiveHighlight = useProgessiveHighlight;
/*  51 */     this.noiseScale = noiseScale;
/*  52 */     this.noiseScrollSpeed = noiseScrollSpeed;
/*  53 */     this.postColor = postColor;
/*  54 */     this.postColorOpacity = postColorOpacity;
/*     */   }
/*     */   
/*     */   public ModelVFX(@Nonnull ModelVFX other) {
/*  58 */     this.id = other.id;
/*  59 */     this.switchTo = other.switchTo;
/*  60 */     this.effectDirection = other.effectDirection;
/*  61 */     this.animationDuration = other.animationDuration;
/*  62 */     this.animationRange = other.animationRange;
/*  63 */     this.loopOption = other.loopOption;
/*  64 */     this.curveType = other.curveType;
/*  65 */     this.highlightColor = other.highlightColor;
/*  66 */     this.highlightThickness = other.highlightThickness;
/*  67 */     this.useBloomOnHighlight = other.useBloomOnHighlight;
/*  68 */     this.useProgessiveHighlight = other.useProgessiveHighlight;
/*  69 */     this.noiseScale = other.noiseScale;
/*  70 */     this.noiseScrollSpeed = other.noiseScrollSpeed;
/*  71 */     this.postColor = other.postColor;
/*  72 */     this.postColorOpacity = other.postColorOpacity;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ModelVFX deserialize(@Nonnull ByteBuf buf, int offset) {
/*  77 */     ModelVFX obj = new ModelVFX();
/*  78 */     byte nullBits = buf.getByte(offset);
/*  79 */     obj.switchTo = SwitchTo.fromValue(buf.getByte(offset + 1));
/*  80 */     obj.effectDirection = EffectDirection.fromValue(buf.getByte(offset + 2));
/*  81 */     obj.animationDuration = buf.getFloatLE(offset + 3);
/*  82 */     if ((nullBits & 0x2) != 0) obj.animationRange = Vector2f.deserialize(buf, offset + 7); 
/*  83 */     obj.loopOption = LoopOption.fromValue(buf.getByte(offset + 15));
/*  84 */     obj.curveType = CurveType.fromValue(buf.getByte(offset + 16));
/*  85 */     if ((nullBits & 0x4) != 0) obj.highlightColor = Color.deserialize(buf, offset + 17); 
/*  86 */     obj.highlightThickness = buf.getFloatLE(offset + 20);
/*  87 */     obj.useBloomOnHighlight = (buf.getByte(offset + 24) != 0);
/*  88 */     obj.useProgessiveHighlight = (buf.getByte(offset + 25) != 0);
/*  89 */     if ((nullBits & 0x8) != 0) obj.noiseScale = Vector2f.deserialize(buf, offset + 26); 
/*  90 */     if ((nullBits & 0x10) != 0) obj.noiseScrollSpeed = Vector2f.deserialize(buf, offset + 34); 
/*  91 */     if ((nullBits & 0x20) != 0) obj.postColor = Color.deserialize(buf, offset + 42); 
/*  92 */     obj.postColorOpacity = buf.getFloatLE(offset + 45);
/*     */     
/*  94 */     int pos = offset + 49;
/*  95 */     if ((nullBits & 0x1) != 0) { int idLen = VarInt.peek(buf, pos);
/*  96 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  97 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  98 */       int idVarLen = VarInt.length(buf, pos);
/*  99 */       obj.id = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/* 100 */       pos += idVarLen + idLen; }
/*     */     
/* 102 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 106 */     byte nullBits = buf.getByte(offset);
/* 107 */     int pos = offset + 49;
/* 108 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/* 109 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 114 */     byte nullBits = 0;
/* 115 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/* 116 */     if (this.animationRange != null) nullBits = (byte)(nullBits | 0x2); 
/* 117 */     if (this.highlightColor != null) nullBits = (byte)(nullBits | 0x4); 
/* 118 */     if (this.noiseScale != null) nullBits = (byte)(nullBits | 0x8); 
/* 119 */     if (this.noiseScrollSpeed != null) nullBits = (byte)(nullBits | 0x10); 
/* 120 */     if (this.postColor != null) nullBits = (byte)(nullBits | 0x20); 
/* 121 */     buf.writeByte(nullBits);
/*     */     
/* 123 */     buf.writeByte(this.switchTo.getValue());
/* 124 */     buf.writeByte(this.effectDirection.getValue());
/* 125 */     buf.writeFloatLE(this.animationDuration);
/* 126 */     if (this.animationRange != null) { this.animationRange.serialize(buf); } else { buf.writeZero(8); }
/* 127 */      buf.writeByte(this.loopOption.getValue());
/* 128 */     buf.writeByte(this.curveType.getValue());
/* 129 */     if (this.highlightColor != null) { this.highlightColor.serialize(buf); } else { buf.writeZero(3); }
/* 130 */      buf.writeFloatLE(this.highlightThickness);
/* 131 */     buf.writeByte(this.useBloomOnHighlight ? 1 : 0);
/* 132 */     buf.writeByte(this.useProgessiveHighlight ? 1 : 0);
/* 133 */     if (this.noiseScale != null) { this.noiseScale.serialize(buf); } else { buf.writeZero(8); }
/* 134 */      if (this.noiseScrollSpeed != null) { this.noiseScrollSpeed.serialize(buf); } else { buf.writeZero(8); }
/* 135 */      if (this.postColor != null) { this.postColor.serialize(buf); } else { buf.writeZero(3); }
/* 136 */      buf.writeFloatLE(this.postColorOpacity);
/*     */     
/* 138 */     if (this.id != null) PacketIO.writeVarString(buf, this.id, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/* 143 */     int size = 49;
/* 144 */     if (this.id != null) size += PacketIO.stringSize(this.id);
/*     */     
/* 146 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 150 */     if (buffer.readableBytes() - offset < 49) {
/* 151 */       return ValidationResult.error("Buffer too small: expected at least 49 bytes");
/*     */     }
/*     */     
/* 154 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 156 */     int pos = offset + 49;
/*     */     
/* 158 */     if ((nullBits & 0x1) != 0) {
/* 159 */       int idLen = VarInt.peek(buffer, pos);
/* 160 */       if (idLen < 0) {
/* 161 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 163 */       if (idLen > 4096000) {
/* 164 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 166 */       pos += VarInt.length(buffer, pos);
/* 167 */       pos += idLen;
/* 168 */       if (pos > buffer.writerIndex()) {
/* 169 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/* 172 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ModelVFX clone() {
/* 176 */     ModelVFX copy = new ModelVFX();
/* 177 */     copy.id = this.id;
/* 178 */     copy.switchTo = this.switchTo;
/* 179 */     copy.effectDirection = this.effectDirection;
/* 180 */     copy.animationDuration = this.animationDuration;
/* 181 */     copy.animationRange = (this.animationRange != null) ? this.animationRange.clone() : null;
/* 182 */     copy.loopOption = this.loopOption;
/* 183 */     copy.curveType = this.curveType;
/* 184 */     copy.highlightColor = (this.highlightColor != null) ? this.highlightColor.clone() : null;
/* 185 */     copy.highlightThickness = this.highlightThickness;
/* 186 */     copy.useBloomOnHighlight = this.useBloomOnHighlight;
/* 187 */     copy.useProgessiveHighlight = this.useProgessiveHighlight;
/* 188 */     copy.noiseScale = (this.noiseScale != null) ? this.noiseScale.clone() : null;
/* 189 */     copy.noiseScrollSpeed = (this.noiseScrollSpeed != null) ? this.noiseScrollSpeed.clone() : null;
/* 190 */     copy.postColor = (this.postColor != null) ? this.postColor.clone() : null;
/* 191 */     copy.postColorOpacity = this.postColorOpacity;
/* 192 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ModelVFX other;
/* 198 */     if (this == obj) return true; 
/* 199 */     if (obj instanceof ModelVFX) { other = (ModelVFX)obj; } else { return false; }
/* 200 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.switchTo, other.switchTo) && Objects.equals(this.effectDirection, other.effectDirection) && this.animationDuration == other.animationDuration && Objects.equals(this.animationRange, other.animationRange) && Objects.equals(this.loopOption, other.loopOption) && Objects.equals(this.curveType, other.curveType) && Objects.equals(this.highlightColor, other.highlightColor) && this.highlightThickness == other.highlightThickness && this.useBloomOnHighlight == other.useBloomOnHighlight && this.useProgessiveHighlight == other.useProgessiveHighlight && Objects.equals(this.noiseScale, other.noiseScale) && Objects.equals(this.noiseScrollSpeed, other.noiseScrollSpeed) && Objects.equals(this.postColor, other.postColor) && this.postColorOpacity == other.postColorOpacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 205 */     return Objects.hash(new Object[] { this.id, this.switchTo, this.effectDirection, Float.valueOf(this.animationDuration), this.animationRange, this.loopOption, this.curveType, this.highlightColor, Float.valueOf(this.highlightThickness), Boolean.valueOf(this.useBloomOnHighlight), Boolean.valueOf(this.useProgessiveHighlight), this.noiseScale, this.noiseScrollSpeed, this.postColor, Float.valueOf(this.postColorOpacity) });
/*     */   }
/*     */   
/*     */   public ModelVFX() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ModelVFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */