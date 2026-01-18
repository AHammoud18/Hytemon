/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.IntArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.IntArrayValidator;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Animation
/*     */ {
/*     */   public static final BuilderCodec<Animation> CODEC;
/*     */   protected String animation;
/*     */   
/*     */   static {
/* 638 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(Animation.class, Animation::new).append(new KeyedCodec("Animation", (Codec)Codec.STRING), (animation, s) -> animation.animation = s, animation -> animation.animation).addValidator(Validators.nonNull()).addValidator((Validator)CommonAssetValidator.ANIMATION_CHARACTER).add()).append(new KeyedCodec("Speed", (Codec)Codec.DOUBLE), (animation, s) -> animation.speed = s.floatValue(), animation -> Double.valueOf(animation.speed)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("BlendingDuration", (Codec)Codec.DOUBLE), (animation, s) -> animation.blendingDuration = s.floatValue(), animation -> Double.valueOf(animation.blendingDuration)).addValidator(Validators.min(Double.valueOf(0.0D))).add()).addField(new KeyedCodec("Looping", (Codec)Codec.BOOLEAN), (animation, s) -> animation.looping = s.booleanValue(), animation -> Boolean.valueOf(animation.looping))).addField(new KeyedCodec("Weight", (Codec)Codec.DOUBLE), (animation, aDouble) -> animation.weight = aDouble.floatValue(), animation -> Double.valueOf(animation.weight))).append(new KeyedCodec("FootstepIntervals", (Codec)Codec.INT_ARRAY), (animation, a) -> animation.footstepIntervals = a, animation -> animation.footstepIntervals).documentation("The intervals (in percentage of the animation duration) at which footsteps are supposed to occur. Only relevant for movement animations (used for timing footstep sound effects).").addValidator((Validator)new IntArrayValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(100)))).add()).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (animation, s) -> animation.soundEventId = s, animation -> animation.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).add()).append(new KeyedCodec("PassiveLoopCount", (Codec)Codec.INTEGER), (animation, integer) -> animation.passiveLoopCount = integer.intValue(), animation -> Integer.valueOf(animation.passiveLoopCount)).addValidator(Validators.greaterThan(Integer.valueOf(0))).add()).afterDecode(Animation::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 648 */   protected float speed = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 653 */   protected float blendingDuration = 0.2F;
/*     */   protected boolean looping = true;
/* 655 */   protected float weight = 1.0F;
/* 656 */   protected int[] footstepIntervals = IntArrayCodec.EMPTY_INT_ARRAY;
/*     */   protected String soundEventId;
/*     */   protected transient int soundEventIndex;
/* 659 */   protected int passiveLoopCount = 1;
/*     */   
/*     */   public Animation(String id, String animation, float speed, float blendingDuration, boolean looping, float weight, int[] footstepIntervals, String soundEventId) {
/* 662 */     this.animation = animation;
/* 663 */     this.speed = speed;
/* 664 */     this.blendingDuration = blendingDuration;
/* 665 */     this.looping = looping;
/* 666 */     this.weight = weight;
/* 667 */     this.footstepIntervals = footstepIntervals;
/* 668 */     this.soundEventId = soundEventId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAnimation() {
/* 675 */     return this.animation;
/*     */   }
/*     */   
/*     */   public float getSpeed() {
/* 679 */     return this.speed;
/*     */   }
/*     */   
/*     */   public float getBlendingDuration() {
/* 683 */     return this.blendingDuration;
/*     */   }
/*     */   
/*     */   public boolean isLooping() {
/* 687 */     return this.looping;
/*     */   }
/*     */   
/*     */   public double getWeight() {
/* 691 */     return this.weight;
/*     */   }
/*     */   
/*     */   public String getSoundEventId() {
/* 695 */     return this.soundEventId;
/*     */   }
/*     */   
/*     */   public int getSoundEventIndex() {
/* 699 */     return this.soundEventIndex;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public com.hypixel.hytale.protocol.Animation toPacket() {
/* 704 */     com.hypixel.hytale.protocol.Animation packet = new com.hypixel.hytale.protocol.Animation();
/* 705 */     packet.name = this.animation;
/* 706 */     packet.speed = this.speed;
/* 707 */     packet.blendingDuration = this.blendingDuration;
/* 708 */     packet.looping = this.looping;
/* 709 */     packet.weight = this.weight;
/* 710 */     packet.footstepIntervals = this.footstepIntervals;
/* 711 */     packet.soundEventIndex = this.soundEventIndex;
/* 712 */     packet.passiveLoopCount = this.passiveLoopCount;
/* 713 */     return packet;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 717 */     if (this.soundEventId != null) {
/* 718 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 725 */     return "Animation{animation='" + this.animation + "', speed=" + this.speed + ", blendingDuration=" + this.blendingDuration + ", looping=" + this.looping + ", weight=" + this.weight + ", footstepIntervals=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 731 */       Arrays.toString(this.footstepIntervals) + ", soundEventId='" + this.soundEventId + "', soundEventIndex=" + this.soundEventIndex + ", passiveLoopCount=" + this.passiveLoopCount + "}";
/*     */   }
/*     */   
/*     */   protected Animation() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\ModelAsset$Animation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */