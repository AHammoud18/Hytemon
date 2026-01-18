/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuilderToolOnUseInteraction
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 413;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 57;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 57;
/*     */   public static final int MAX_SIZE = 57;
/*     */   
/*     */   public int getId() {
/*  25 */     return 413;
/*     */   }
/*     */   @Nonnull
/*  28 */   public InteractionType type = InteractionType.Primary;
/*     */   
/*     */   public int x;
/*     */   
/*     */   public int y;
/*     */   
/*     */   public int z;
/*     */   public int offsetForPaintModeX;
/*     */   public int offsetForPaintModeY;
/*     */   public int offsetForPaintModeZ;
/*     */   public boolean isAltPlaySculptBrushModDown;
/*     */   public boolean isHoldDownInteraction;
/*     */   public boolean isDoServerRaytraceForPosition;
/*     */   public boolean isShowEditNotifications;
/*     */   public int maxLengthToolIgnoreHistory;
/*     */   public float raycastOriginX;
/*     */   public float raycastOriginY;
/*     */   public float raycastOriginZ;
/*     */   public float raycastDirectionX;
/*     */   public float raycastDirectionY;
/*     */   public float raycastDirectionZ;
/*     */   
/*     */   public BuilderToolOnUseInteraction(@Nonnull InteractionType type, int x, int y, int z, int offsetForPaintModeX, int offsetForPaintModeY, int offsetForPaintModeZ, boolean isAltPlaySculptBrushModDown, boolean isHoldDownInteraction, boolean isDoServerRaytraceForPosition, boolean isShowEditNotifications, int maxLengthToolIgnoreHistory, float raycastOriginX, float raycastOriginY, float raycastOriginZ, float raycastDirectionX, float raycastDirectionY, float raycastDirectionZ) {
/*  51 */     this.type = type;
/*  52 */     this.x = x;
/*  53 */     this.y = y;
/*  54 */     this.z = z;
/*  55 */     this.offsetForPaintModeX = offsetForPaintModeX;
/*  56 */     this.offsetForPaintModeY = offsetForPaintModeY;
/*  57 */     this.offsetForPaintModeZ = offsetForPaintModeZ;
/*  58 */     this.isAltPlaySculptBrushModDown = isAltPlaySculptBrushModDown;
/*  59 */     this.isHoldDownInteraction = isHoldDownInteraction;
/*  60 */     this.isDoServerRaytraceForPosition = isDoServerRaytraceForPosition;
/*  61 */     this.isShowEditNotifications = isShowEditNotifications;
/*  62 */     this.maxLengthToolIgnoreHistory = maxLengthToolIgnoreHistory;
/*  63 */     this.raycastOriginX = raycastOriginX;
/*  64 */     this.raycastOriginY = raycastOriginY;
/*  65 */     this.raycastOriginZ = raycastOriginZ;
/*  66 */     this.raycastDirectionX = raycastDirectionX;
/*  67 */     this.raycastDirectionY = raycastDirectionY;
/*  68 */     this.raycastDirectionZ = raycastDirectionZ;
/*     */   }
/*     */   
/*     */   public BuilderToolOnUseInteraction(@Nonnull BuilderToolOnUseInteraction other) {
/*  72 */     this.type = other.type;
/*  73 */     this.x = other.x;
/*  74 */     this.y = other.y;
/*  75 */     this.z = other.z;
/*  76 */     this.offsetForPaintModeX = other.offsetForPaintModeX;
/*  77 */     this.offsetForPaintModeY = other.offsetForPaintModeY;
/*  78 */     this.offsetForPaintModeZ = other.offsetForPaintModeZ;
/*  79 */     this.isAltPlaySculptBrushModDown = other.isAltPlaySculptBrushModDown;
/*  80 */     this.isHoldDownInteraction = other.isHoldDownInteraction;
/*  81 */     this.isDoServerRaytraceForPosition = other.isDoServerRaytraceForPosition;
/*  82 */     this.isShowEditNotifications = other.isShowEditNotifications;
/*  83 */     this.maxLengthToolIgnoreHistory = other.maxLengthToolIgnoreHistory;
/*  84 */     this.raycastOriginX = other.raycastOriginX;
/*  85 */     this.raycastOriginY = other.raycastOriginY;
/*  86 */     this.raycastOriginZ = other.raycastOriginZ;
/*  87 */     this.raycastDirectionX = other.raycastDirectionX;
/*  88 */     this.raycastDirectionY = other.raycastDirectionY;
/*  89 */     this.raycastDirectionZ = other.raycastDirectionZ;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolOnUseInteraction deserialize(@Nonnull ByteBuf buf, int offset) {
/*  94 */     BuilderToolOnUseInteraction obj = new BuilderToolOnUseInteraction();
/*     */     
/*  96 */     obj.type = InteractionType.fromValue(buf.getByte(offset + 0));
/*  97 */     obj.x = buf.getIntLE(offset + 1);
/*  98 */     obj.y = buf.getIntLE(offset + 5);
/*  99 */     obj.z = buf.getIntLE(offset + 9);
/* 100 */     obj.offsetForPaintModeX = buf.getIntLE(offset + 13);
/* 101 */     obj.offsetForPaintModeY = buf.getIntLE(offset + 17);
/* 102 */     obj.offsetForPaintModeZ = buf.getIntLE(offset + 21);
/* 103 */     obj.isAltPlaySculptBrushModDown = (buf.getByte(offset + 25) != 0);
/* 104 */     obj.isHoldDownInteraction = (buf.getByte(offset + 26) != 0);
/* 105 */     obj.isDoServerRaytraceForPosition = (buf.getByte(offset + 27) != 0);
/* 106 */     obj.isShowEditNotifications = (buf.getByte(offset + 28) != 0);
/* 107 */     obj.maxLengthToolIgnoreHistory = buf.getIntLE(offset + 29);
/* 108 */     obj.raycastOriginX = buf.getFloatLE(offset + 33);
/* 109 */     obj.raycastOriginY = buf.getFloatLE(offset + 37);
/* 110 */     obj.raycastOriginZ = buf.getFloatLE(offset + 41);
/* 111 */     obj.raycastDirectionX = buf.getFloatLE(offset + 45);
/* 112 */     obj.raycastDirectionY = buf.getFloatLE(offset + 49);
/* 113 */     obj.raycastDirectionZ = buf.getFloatLE(offset + 53);
/*     */ 
/*     */     
/* 116 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 120 */     return 57;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 126 */     buf.writeByte(this.type.getValue());
/* 127 */     buf.writeIntLE(this.x);
/* 128 */     buf.writeIntLE(this.y);
/* 129 */     buf.writeIntLE(this.z);
/* 130 */     buf.writeIntLE(this.offsetForPaintModeX);
/* 131 */     buf.writeIntLE(this.offsetForPaintModeY);
/* 132 */     buf.writeIntLE(this.offsetForPaintModeZ);
/* 133 */     buf.writeByte(this.isAltPlaySculptBrushModDown ? 1 : 0);
/* 134 */     buf.writeByte(this.isHoldDownInteraction ? 1 : 0);
/* 135 */     buf.writeByte(this.isDoServerRaytraceForPosition ? 1 : 0);
/* 136 */     buf.writeByte(this.isShowEditNotifications ? 1 : 0);
/* 137 */     buf.writeIntLE(this.maxLengthToolIgnoreHistory);
/* 138 */     buf.writeFloatLE(this.raycastOriginX);
/* 139 */     buf.writeFloatLE(this.raycastOriginY);
/* 140 */     buf.writeFloatLE(this.raycastOriginZ);
/* 141 */     buf.writeFloatLE(this.raycastDirectionX);
/* 142 */     buf.writeFloatLE(this.raycastDirectionY);
/* 143 */     buf.writeFloatLE(this.raycastDirectionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 149 */     return 57;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 153 */     if (buffer.readableBytes() - offset < 57) {
/* 154 */       return ValidationResult.error("Buffer too small: expected at least 57 bytes");
/*     */     }
/*     */ 
/*     */     
/* 158 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolOnUseInteraction clone() {
/* 162 */     BuilderToolOnUseInteraction copy = new BuilderToolOnUseInteraction();
/* 163 */     copy.type = this.type;
/* 164 */     copy.x = this.x;
/* 165 */     copy.y = this.y;
/* 166 */     copy.z = this.z;
/* 167 */     copy.offsetForPaintModeX = this.offsetForPaintModeX;
/* 168 */     copy.offsetForPaintModeY = this.offsetForPaintModeY;
/* 169 */     copy.offsetForPaintModeZ = this.offsetForPaintModeZ;
/* 170 */     copy.isAltPlaySculptBrushModDown = this.isAltPlaySculptBrushModDown;
/* 171 */     copy.isHoldDownInteraction = this.isHoldDownInteraction;
/* 172 */     copy.isDoServerRaytraceForPosition = this.isDoServerRaytraceForPosition;
/* 173 */     copy.isShowEditNotifications = this.isShowEditNotifications;
/* 174 */     copy.maxLengthToolIgnoreHistory = this.maxLengthToolIgnoreHistory;
/* 175 */     copy.raycastOriginX = this.raycastOriginX;
/* 176 */     copy.raycastOriginY = this.raycastOriginY;
/* 177 */     copy.raycastOriginZ = this.raycastOriginZ;
/* 178 */     copy.raycastDirectionX = this.raycastDirectionX;
/* 179 */     copy.raycastDirectionY = this.raycastDirectionY;
/* 180 */     copy.raycastDirectionZ = this.raycastDirectionZ;
/* 181 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolOnUseInteraction other;
/* 187 */     if (this == obj) return true; 
/* 188 */     if (obj instanceof BuilderToolOnUseInteraction) { other = (BuilderToolOnUseInteraction)obj; } else { return false; }
/* 189 */      return (Objects.equals(this.type, other.type) && this.x == other.x && this.y == other.y && this.z == other.z && this.offsetForPaintModeX == other.offsetForPaintModeX && this.offsetForPaintModeY == other.offsetForPaintModeY && this.offsetForPaintModeZ == other.offsetForPaintModeZ && this.isAltPlaySculptBrushModDown == other.isAltPlaySculptBrushModDown && this.isHoldDownInteraction == other.isHoldDownInteraction && this.isDoServerRaytraceForPosition == other.isDoServerRaytraceForPosition && this.isShowEditNotifications == other.isShowEditNotifications && this.maxLengthToolIgnoreHistory == other.maxLengthToolIgnoreHistory && this.raycastOriginX == other.raycastOriginX && this.raycastOriginY == other.raycastOriginY && this.raycastOriginZ == other.raycastOriginZ && this.raycastDirectionX == other.raycastDirectionX && this.raycastDirectionY == other.raycastDirectionY && this.raycastDirectionZ == other.raycastDirectionZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 194 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.offsetForPaintModeX), Integer.valueOf(this.offsetForPaintModeY), Integer.valueOf(this.offsetForPaintModeZ), Boolean.valueOf(this.isAltPlaySculptBrushModDown), Boolean.valueOf(this.isHoldDownInteraction), Boolean.valueOf(this.isDoServerRaytraceForPosition), Boolean.valueOf(this.isShowEditNotifications), Integer.valueOf(this.maxLengthToolIgnoreHistory), Float.valueOf(this.raycastOriginX), Float.valueOf(this.raycastOriginY), Float.valueOf(this.raycastOriginZ), Float.valueOf(this.raycastDirectionX), Float.valueOf(this.raycastDirectionY), Float.valueOf(this.raycastDirectionZ) });
/*     */   }
/*     */   
/*     */   public BuilderToolOnUseInteraction() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolOnUseInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */