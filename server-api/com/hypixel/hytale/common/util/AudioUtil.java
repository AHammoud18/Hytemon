/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AudioUtil
/*    */ {
/*    */   public static final float MIN_DECIBEL_VOLUME = -100.0F;
/*    */   public static final float MAX_DECIBEL_VOLUME = 10.0F;
/*    */   public static final float MIN_SEMITONE_PITCH = -12.0F;
/*    */   public static final float MAX_SEMITONE_PITCH = 12.0F;
/*    */   
/*    */   public static float decibelsToLinearGain(float decibels) {
/* 41 */     if (decibels <= -100.0F) return 0.0F; 
/* 42 */     return (float)Math.pow(10.0D, (decibels / 20.0F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float linearGainToDecibels(float linearGain) {
/* 53 */     if (linearGain <= 0.0F) return -100.0F; 
/* 54 */     return (float)(Math.log(linearGain) / Math.log(10.0D) * 20.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float semitonesToLinearPitch(float semitones) {
/* 65 */     return (float)(1.0D / Math.pow(2.0D, (-semitones / 12.0F)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float linearPitchToSemitones(float linearPitch) {
/* 76 */     return (float)(Math.log(linearPitch) / Math.log(2.0D) * 12.0D);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\AudioUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */