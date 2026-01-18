/*   */ package com.hypixel.hytale.builtin.hytalegenerator.framework.math;
/*   */ 
/*   */ import java.util.Random;
/*   */ 
/*   */ public class Probability
/*   */ {
/*   */   public static boolean of(double chance, long seed) {
/* 8 */     Random rand = new Random(seed);
/* 9 */     return (rand.nextDouble() < chance);
/*   */   }
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\framework\math\Probability.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */