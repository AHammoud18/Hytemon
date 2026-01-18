/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MovementStates
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 22;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 22;
/*     */   public static final int MAX_SIZE = 22;
/*     */   public boolean idle;
/*     */   public boolean horizontalIdle;
/*     */   public boolean jumping;
/*     */   public boolean flying;
/*     */   public boolean walking;
/*     */   public boolean running;
/*     */   public boolean sprinting;
/*     */   public boolean crouching;
/*     */   public boolean forcedCrouching;
/*     */   public boolean falling;
/*     */   public boolean climbing;
/*     */   public boolean inFluid;
/*     */   public boolean swimming;
/*     */   public boolean swimJumping;
/*     */   public boolean onGround;
/*     */   public boolean mantling;
/*     */   public boolean sliding;
/*     */   public boolean mounting;
/*     */   public boolean rolling;
/*     */   public boolean sitting;
/*     */   public boolean gliding;
/*     */   public boolean sleeping;
/*     */   
/*     */   public MovementStates() {}
/*     */   
/*     */   public MovementStates(boolean idle, boolean horizontalIdle, boolean jumping, boolean flying, boolean walking, boolean running, boolean sprinting, boolean crouching, boolean forcedCrouching, boolean falling, boolean climbing, boolean inFluid, boolean swimming, boolean swimJumping, boolean onGround, boolean mantling, boolean sliding, boolean mounting, boolean rolling, boolean sitting, boolean gliding, boolean sleeping) {
/*  47 */     this.idle = idle;
/*  48 */     this.horizontalIdle = horizontalIdle;
/*  49 */     this.jumping = jumping;
/*  50 */     this.flying = flying;
/*  51 */     this.walking = walking;
/*  52 */     this.running = running;
/*  53 */     this.sprinting = sprinting;
/*  54 */     this.crouching = crouching;
/*  55 */     this.forcedCrouching = forcedCrouching;
/*  56 */     this.falling = falling;
/*  57 */     this.climbing = climbing;
/*  58 */     this.inFluid = inFluid;
/*  59 */     this.swimming = swimming;
/*  60 */     this.swimJumping = swimJumping;
/*  61 */     this.onGround = onGround;
/*  62 */     this.mantling = mantling;
/*  63 */     this.sliding = sliding;
/*  64 */     this.mounting = mounting;
/*  65 */     this.rolling = rolling;
/*  66 */     this.sitting = sitting;
/*  67 */     this.gliding = gliding;
/*  68 */     this.sleeping = sleeping;
/*     */   }
/*     */   
/*     */   public MovementStates(@Nonnull MovementStates other) {
/*  72 */     this.idle = other.idle;
/*  73 */     this.horizontalIdle = other.horizontalIdle;
/*  74 */     this.jumping = other.jumping;
/*  75 */     this.flying = other.flying;
/*  76 */     this.walking = other.walking;
/*  77 */     this.running = other.running;
/*  78 */     this.sprinting = other.sprinting;
/*  79 */     this.crouching = other.crouching;
/*  80 */     this.forcedCrouching = other.forcedCrouching;
/*  81 */     this.falling = other.falling;
/*  82 */     this.climbing = other.climbing;
/*  83 */     this.inFluid = other.inFluid;
/*  84 */     this.swimming = other.swimming;
/*  85 */     this.swimJumping = other.swimJumping;
/*  86 */     this.onGround = other.onGround;
/*  87 */     this.mantling = other.mantling;
/*  88 */     this.sliding = other.sliding;
/*  89 */     this.mounting = other.mounting;
/*  90 */     this.rolling = other.rolling;
/*  91 */     this.sitting = other.sitting;
/*  92 */     this.gliding = other.gliding;
/*  93 */     this.sleeping = other.sleeping;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MovementStates deserialize(@Nonnull ByteBuf buf, int offset) {
/*  98 */     MovementStates obj = new MovementStates();
/*     */     
/* 100 */     obj.idle = (buf.getByte(offset + 0) != 0);
/* 101 */     obj.horizontalIdle = (buf.getByte(offset + 1) != 0);
/* 102 */     obj.jumping = (buf.getByte(offset + 2) != 0);
/* 103 */     obj.flying = (buf.getByte(offset + 3) != 0);
/* 104 */     obj.walking = (buf.getByte(offset + 4) != 0);
/* 105 */     obj.running = (buf.getByte(offset + 5) != 0);
/* 106 */     obj.sprinting = (buf.getByte(offset + 6) != 0);
/* 107 */     obj.crouching = (buf.getByte(offset + 7) != 0);
/* 108 */     obj.forcedCrouching = (buf.getByte(offset + 8) != 0);
/* 109 */     obj.falling = (buf.getByte(offset + 9) != 0);
/* 110 */     obj.climbing = (buf.getByte(offset + 10) != 0);
/* 111 */     obj.inFluid = (buf.getByte(offset + 11) != 0);
/* 112 */     obj.swimming = (buf.getByte(offset + 12) != 0);
/* 113 */     obj.swimJumping = (buf.getByte(offset + 13) != 0);
/* 114 */     obj.onGround = (buf.getByte(offset + 14) != 0);
/* 115 */     obj.mantling = (buf.getByte(offset + 15) != 0);
/* 116 */     obj.sliding = (buf.getByte(offset + 16) != 0);
/* 117 */     obj.mounting = (buf.getByte(offset + 17) != 0);
/* 118 */     obj.rolling = (buf.getByte(offset + 18) != 0);
/* 119 */     obj.sitting = (buf.getByte(offset + 19) != 0);
/* 120 */     obj.gliding = (buf.getByte(offset + 20) != 0);
/* 121 */     obj.sleeping = (buf.getByte(offset + 21) != 0);
/*     */ 
/*     */     
/* 124 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 128 */     return 22;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/* 133 */     buf.writeByte(this.idle ? 1 : 0);
/* 134 */     buf.writeByte(this.horizontalIdle ? 1 : 0);
/* 135 */     buf.writeByte(this.jumping ? 1 : 0);
/* 136 */     buf.writeByte(this.flying ? 1 : 0);
/* 137 */     buf.writeByte(this.walking ? 1 : 0);
/* 138 */     buf.writeByte(this.running ? 1 : 0);
/* 139 */     buf.writeByte(this.sprinting ? 1 : 0);
/* 140 */     buf.writeByte(this.crouching ? 1 : 0);
/* 141 */     buf.writeByte(this.forcedCrouching ? 1 : 0);
/* 142 */     buf.writeByte(this.falling ? 1 : 0);
/* 143 */     buf.writeByte(this.climbing ? 1 : 0);
/* 144 */     buf.writeByte(this.inFluid ? 1 : 0);
/* 145 */     buf.writeByte(this.swimming ? 1 : 0);
/* 146 */     buf.writeByte(this.swimJumping ? 1 : 0);
/* 147 */     buf.writeByte(this.onGround ? 1 : 0);
/* 148 */     buf.writeByte(this.mantling ? 1 : 0);
/* 149 */     buf.writeByte(this.sliding ? 1 : 0);
/* 150 */     buf.writeByte(this.mounting ? 1 : 0);
/* 151 */     buf.writeByte(this.rolling ? 1 : 0);
/* 152 */     buf.writeByte(this.sitting ? 1 : 0);
/* 153 */     buf.writeByte(this.gliding ? 1 : 0);
/* 154 */     buf.writeByte(this.sleeping ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 160 */     return 22;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 164 */     if (buffer.readableBytes() - offset < 22) {
/* 165 */       return ValidationResult.error("Buffer too small: expected at least 22 bytes");
/*     */     }
/*     */ 
/*     */     
/* 169 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MovementStates clone() {
/* 173 */     MovementStates copy = new MovementStates();
/* 174 */     copy.idle = this.idle;
/* 175 */     copy.horizontalIdle = this.horizontalIdle;
/* 176 */     copy.jumping = this.jumping;
/* 177 */     copy.flying = this.flying;
/* 178 */     copy.walking = this.walking;
/* 179 */     copy.running = this.running;
/* 180 */     copy.sprinting = this.sprinting;
/* 181 */     copy.crouching = this.crouching;
/* 182 */     copy.forcedCrouching = this.forcedCrouching;
/* 183 */     copy.falling = this.falling;
/* 184 */     copy.climbing = this.climbing;
/* 185 */     copy.inFluid = this.inFluid;
/* 186 */     copy.swimming = this.swimming;
/* 187 */     copy.swimJumping = this.swimJumping;
/* 188 */     copy.onGround = this.onGround;
/* 189 */     copy.mantling = this.mantling;
/* 190 */     copy.sliding = this.sliding;
/* 191 */     copy.mounting = this.mounting;
/* 192 */     copy.rolling = this.rolling;
/* 193 */     copy.sitting = this.sitting;
/* 194 */     copy.gliding = this.gliding;
/* 195 */     copy.sleeping = this.sleeping;
/* 196 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MovementStates other;
/* 202 */     if (this == obj) return true; 
/* 203 */     if (obj instanceof MovementStates) { other = (MovementStates)obj; } else { return false; }
/* 204 */      return (this.idle == other.idle && this.horizontalIdle == other.horizontalIdle && this.jumping == other.jumping && this.flying == other.flying && this.walking == other.walking && this.running == other.running && this.sprinting == other.sprinting && this.crouching == other.crouching && this.forcedCrouching == other.forcedCrouching && this.falling == other.falling && this.climbing == other.climbing && this.inFluid == other.inFluid && this.swimming == other.swimming && this.swimJumping == other.swimJumping && this.onGround == other.onGround && this.mantling == other.mantling && this.sliding == other.sliding && this.mounting == other.mounting && this.rolling == other.rolling && this.sitting == other.sitting && this.gliding == other.gliding && this.sleeping == other.sleeping);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 209 */     return Objects.hash(new Object[] { Boolean.valueOf(this.idle), Boolean.valueOf(this.horizontalIdle), Boolean.valueOf(this.jumping), Boolean.valueOf(this.flying), Boolean.valueOf(this.walking), Boolean.valueOf(this.running), Boolean.valueOf(this.sprinting), Boolean.valueOf(this.crouching), Boolean.valueOf(this.forcedCrouching), Boolean.valueOf(this.falling), Boolean.valueOf(this.climbing), Boolean.valueOf(this.inFluid), Boolean.valueOf(this.swimming), Boolean.valueOf(this.swimJumping), Boolean.valueOf(this.onGround), Boolean.valueOf(this.mantling), Boolean.valueOf(this.sliding), Boolean.valueOf(this.mounting), Boolean.valueOf(this.rolling), Boolean.valueOf(this.sitting), Boolean.valueOf(this.gliding), Boolean.valueOf(this.sleeping) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\MovementStates.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */