/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Colors
/*     */ {
/*  54 */   public static final int[] DEFAULT_COLORS_256 = new int[] { 0, 8388608, 32768, 8421376, 128, 8388736, 32896, 12632256, 8421504, 16711680, 65280, 16776960, 255, 16711935, 65535, 16777215, 0, 95, 135, 175, 215, 255, 24320, 24415, 24455, 24495, 24535, 24575, 34560, 34655, 34695, 34735, 34775, 34815, 44800, 44895, 44935, 44975, 45015, 45055, 55040, 55135, 55175, 55215, 55255, 55295, 65280, 65375, 65415, 65455, 65495, 65535, 6225920, 6226015, 6226055, 6226095, 6226135, 6226175, 6250240, 6250335, 6250375, 6250415, 6250455, 6250495, 6260480, 6260575, 6260615, 6260655, 6260695, 6260735, 6270720, 6270815, 6270855, 6270895, 6270935, 6270975, 6280960, 6281055, 6281095, 6281135, 6281175, 6281215, 6291200, 6291295, 6291335, 6291375, 6291415, 6291455, 8847360, 8847455, 8847495, 8847535, 8847575, 8847615, 8871680, 8871775, 8871815, 8871855, 8871895, 8871935, 8881920, 8882015, 8882055, 8882095, 8882135, 8882175, 8892160, 8892255, 8892295, 8892335, 8892375, 8892415, 8902400, 8902495, 8902535, 8902575, 8902615, 8902655, 8912640, 8912735, 8912775, 8912815, 8912855, 8912895, 11468800, 11468895, 11468935, 11468975, 11469015, 11469055, 11493120, 11493215, 11493255, 11493295, 11493335, 11493375, 11503360, 11503455, 11503495, 11503535, 11503575, 11503615, 11513600, 11513695, 11513735, 11513775, 11513815, 11513855, 11523840, 11523935, 11523975, 11524015, 11524055, 11524095, 11534080, 11534175, 11534215, 11534255, 11534295, 11534335, 14090240, 14090335, 14090375, 14090415, 14090455, 14090495, 14114560, 14114655, 14114695, 14114735, 14114775, 14114815, 14124800, 14124895, 14124935, 14124975, 14125015, 14125055, 14135040, 14135135, 14135175, 14135215, 14135255, 14135295, 14145280, 14145375, 14145415, 14145455, 14145495, 14145535, 14155520, 14155615, 14155655, 14155695, 14155735, 14155775, 16711680, 16711775, 16711815, 16711855, 16711895, 16711935, 16736000, 16736095, 16736135, 16736175, 16736215, 16736255, 16746240, 16746335, 16746375, 16746415, 16746455, 16746495, 16756480, 16756575, 16756615, 16756655, 16756695, 16756735, 16766720, 16766815, 16766855, 16766895, 16766935, 16766975, 16776960, 16777055, 16777095, 16777135, 16777175, 16777215, 526344, 1184274, 1842204, 2500134, 3158064, 3815994, 4473924, 5131854, 5789784, 6447714, 7105644, 7763574, 8421504, 9079434, 9737364, 10395294, 11053224, 11711154, 12369084, 13027014, 13684944, 14342874, 15000804, 15658734 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final int[] DEFAULT_COLORS_88 = new int[] { 0, 8388608, 32768, 8421376, 128, 8388736, 32896, 12632256, 8421504, 16711680, 65280, 16776960, 255, 16711935, 65535, 16777215, 0, 139, 205, 255, 35584, 35723, 35789, 35839, 52480, 52619, 52685, 52735, 65280, 65419, 65485, 65535, 9109504, 9109643, 9109709, 9109759, 9145088, 9145227, 9145293, 9145343, 9161984, 9162123, 9162189, 9162239, 9174784, 9174923, 9174989, 9175039, 13434880, 13435019, 13435085, 13435135, 13470464, 13470603, 13470669, 13470719, 13487360, 13487499, 13487565, 13487615, 13500160, 13500299, 13500365, 13500415, 16711680, 16711819, 16711885, 16711935, 16747264, 16747403, 16747469, 16747519, 16764160, 16764299, 16764365, 16764415, 16776960, 16777099, 16777165, 16777215, 3026478, 6052956, 7566195, 9145227, 10658466, 12171705, 13684944, 15198183 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final double[] D50 = new double[] { 96.4219970703125D, 100.0D, 82.52100372314453D };
/*     */   
/* 146 */   public static final double[] D65 = new double[] { 95.047D, 100.0D, 108.883D };
/*     */ 
/*     */   
/* 149 */   public static final double[] averageSurrounding = new double[] { 1.0D, 0.69D, 1.0D };
/*     */   
/* 151 */   public static final double[] dimSurrounding = new double[] { 0.9D, 0.59D, 0.9D };
/*     */   
/* 153 */   public static final double[] darkSurrounding = new double[] { 0.8D, 0.525D, 0.8D };
/*     */ 
/*     */   
/* 156 */   public static final double[] sRGB_encoding_environment = vc(D50, 64.0D, 12.8D, dimSurrounding);
/*     */   
/* 158 */   public static final double[] sRGB_typical_environment = vc(D50, 200.0D, 40.0D, averageSurrounding);
/*     */   
/* 160 */   public static final double[] AdobeRGB_environment = vc(D65, 160.0D, 32.0D, averageSurrounding);
/*     */   
/* 162 */   private static int[] COLORS_256 = DEFAULT_COLORS_256; private static Map<String, Integer> COLOR_NAMES; private static final int L = 0; private static final int A = 1; private static final int B = 2; private static final int X = 0; private static final int Y = 1; private static final int Z = 2; private static final double kl = 2.0D; private static final double kc = 1.0D; private static final double kh = 1.0D; private static final double k1 = 0.045D; private static final double k2 = 0.015D; public static final int J = 0; public static final int Q = 1; public static final int C = 2; public static final int M = 3; public static final int s = 4; public static final int H = 5;
/*     */   public static final int h = 6;
/*     */   static final int SUR_F = 0;
/*     */   
/*     */   public static void setRgbColors(int[] colors) {
/* 167 */     if (colors == null || colors.length != 256) {
/* 168 */       throw new IllegalArgumentException();
/*     */     }
/* 170 */     COLORS_256 = colors;
/*     */   }
/*     */   static final int SUR_C = 1; static final int SUR_N_C = 2; static final int VC_X_W = 0; static final int VC_Y_W = 1; static final int VC_Z_W = 2; static final int VC_L_A = 3; static final int VC_Y_B = 4; static final int VC_F = 5; static final int VC_C = 6; static final int VC_N_C = 7; static final int VC_Z = 8; static final int VC_N = 9; static final int VC_N_BB = 10; static final int VC_N_CB = 11; static final int VC_A_W = 12; static final int VC_F_L = 13; static final int VC_D_RGB_R = 14; static final int VC_D_RGB_G = 15; static final int VC_D_RGB_B = 16; private static final double epsilon = 0.008856451679035631D; private static final double kappa = 903.2962962962963D;
/*     */   public static int rgbColor(int col) {
/* 174 */     return COLORS_256[col];
/*     */   }
/*     */   
/*     */   public static Integer rgbColor(String name) {
/* 178 */     if (COLOR_NAMES == null) {
/* 179 */       Map<String, Integer> colors = new LinkedHashMap<>(); 
/* 180 */       try { InputStream is = InfoCmp.class.getResourceAsStream("colors.txt"); 
/* 181 */         try { BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)); 
/* 182 */           try { br.lines()
/* 183 */               .map(String::trim)
/* 184 */               .filter(s -> !s.startsWith("#"))
/* 185 */               .filter(s -> !s.isEmpty())
/* 186 */               .forEachOrdered(s -> colors.put(s, Integer.valueOf(colors.size())));
/*     */ 
/*     */             
/* 189 */             COLOR_NAMES = colors;
/* 190 */             br.close(); } catch (Throwable throwable) { try { br.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (is != null) is.close();  } catch (Throwable throwable) { if (is != null) try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 191 */       { throw new IOError(e); }
/*     */     
/*     */     } 
/* 194 */     return COLOR_NAMES.get(name);
/*     */   }
/*     */   
/*     */   public static int roundColor(int col, int max) {
/* 198 */     return roundColor(col, max, null);
/*     */   }
/*     */   
/*     */   public static int roundColor(int col, int max, String dist) {
/* 202 */     if (col >= max) {
/* 203 */       int c = COLORS_256[col];
/* 204 */       col = roundColor(c, COLORS_256, max, dist);
/*     */     } 
/* 206 */     return col;
/*     */   }
/*     */   
/*     */   public static int roundRgbColor(int r, int g, int b, int max) {
/* 210 */     return roundColor((r << 16) + (g << 8) + b, COLORS_256, max, (String)null);
/*     */   }
/*     */   
/*     */   static int roundColor(int color, int[] colors, int max, String dist) {
/* 214 */     return roundColor(color, colors, max, getDistance(dist));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int roundColor(int color, int[] colors, int max, Distance distance) {
/* 223 */     double best_distance = 2.147483647E9D;
/* 224 */     int best_index = Integer.MAX_VALUE;
/* 225 */     for (int idx = 0; idx < max; idx++) {
/* 226 */       double d = distance.compute(color, colors[idx]);
/* 227 */       if (d <= best_distance) {
/* 228 */         best_index = idx;
/* 229 */         best_distance = d;
/*     */       } 
/*     */     } 
/* 232 */     return best_index;
/*     */   }
/*     */   
/*     */   static Distance getDistance(String dist) {
/* 236 */     if (dist == null) {
/* 237 */       dist = System.getProperty("org.jline.utils.colorDistance", "cie76");
/*     */     }
/* 239 */     return new NamedDistance(dist, doGetDistance(dist));
/*     */   }
/*     */   
/*     */   private static Distance doGetDistance(String dist) {
/* 243 */     if (dist.equals("rgb")) {
/* 244 */       return (p1, p2) -> {
/*     */           double[] c1 = rgb(p1);
/*     */           
/*     */           double[] c2 = rgb(p2);
/*     */           double rmean = (c1[0] + c2[0]) / 2.0D;
/*     */           double[] w = { 2.0D + rmean, 4.0D, 3.0D - rmean };
/*     */           return scalar(c1, c2, w);
/*     */         };
/*     */     }
/* 253 */     if (dist.matches("rgb\\(([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?)\\)")) {
/* 254 */       return (p1, p2) -> scalar(rgb(p1), rgb(p2), getWeights(dist));
/*     */     }
/* 256 */     if (dist.equals("lab") || dist.equals("cie76")) {
/* 257 */       return (p1, p2) -> scalar(rgb2cielab(p1), rgb2cielab(p2));
/*     */     }
/* 259 */     if (dist.matches("lab\\(([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?)\\)")) {
/* 260 */       double[] w = getWeights(dist);
/* 261 */       return (p1, p2) -> scalar(rgb2cielab(p1), rgb2cielab(p2), new double[] { w[0], w[1], w[1] });
/*     */     } 
/* 263 */     if (dist.equals("cie94")) {
/* 264 */       return (p1, p2) -> cie94(rgb2cielab(p1), rgb2cielab(p2));
/*     */     }
/* 266 */     if (dist.equals("cie00") || dist.equals("cie2000")) {
/* 267 */       return (p1, p2) -> cie00(rgb2cielab(p1), rgb2cielab(p2));
/*     */     }
/* 269 */     if (dist.equals("cam02")) {
/* 270 */       return (p1, p2) -> cam02(p1, p2, sRGB_typical_environment);
/*     */     }
/* 272 */     if (dist.equals("camlab")) {
/* 273 */       return (p1, p2) -> {
/*     */           double[] c1 = camlab(p1, sRGB_typical_environment);
/*     */           double[] c2 = camlab(p2, sRGB_typical_environment);
/*     */           return scalar(c1, c2);
/*     */         };
/*     */     }
/* 279 */     if (dist.matches("camlab\\(([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?)\\)")) {
/* 280 */       return (p1, p2) -> {
/*     */           double[] c1 = camlab(p1, sRGB_typical_environment);
/*     */           double[] c2 = camlab(p2, sRGB_typical_environment);
/*     */           double[] w = getWeights(dist);
/*     */           return scalar(c1, c2, new double[] { w[0], w[1], w[1] });
/*     */         };
/*     */     }
/* 287 */     if (dist.matches("camlch")) {
/* 288 */       return (p1, p2) -> {
/*     */           double[] c1 = camlch(p1, sRGB_typical_environment);
/*     */           double[] c2 = camlch(p2, sRGB_typical_environment);
/*     */           return camlch(c1, c2);
/*     */         };
/*     */     }
/* 294 */     if (dist.matches("camlch\\(([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?),([0-9]+(\\.[0-9]+)?)\\)")) {
/* 295 */       return (p1, p2) -> {
/*     */           double[] c1 = camlch(p1, sRGB_typical_environment);
/*     */           double[] c2 = camlch(p2, sRGB_typical_environment);
/*     */           double[] w = getWeights(dist);
/*     */           return camlch(c1, c2, w);
/*     */         };
/*     */     }
/* 302 */     throw new IllegalArgumentException("Unsupported distance function: " + dist);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[] getWeights(String dist) {
/* 307 */     String[] weights = dist.substring(dist.indexOf('(') + 1, dist.length() - 1).split(",");
/* 308 */     return Stream.<String>of(weights).mapToDouble(Double::parseDouble).toArray();
/*     */   }
/*     */   
/*     */   private static double scalar(double[] c1, double[] c2, double[] w) {
/* 312 */     return sqr((c1[0] - c2[0]) * w[0]) + sqr((c1[1] - c2[1]) * w[1]) + sqr((c1[2] - c2[2]) * w[2]);
/*     */   }
/*     */   
/*     */   private static double scalar(double[] c1, double[] c2) {
/* 316 */     return sqr(c1[0] - c2[0]) + sqr(c1[1] - c2[1]) + sqr(c1[2] - c2[2]);
/*     */   }
/*     */   @FunctionalInterface
/*     */   static interface Distance {
/*     */     double compute(int param1Int1, int param1Int2); }
/*     */   private static class NamedDistance implements Distance { private final String name; private final Colors.Distance delegate;
/*     */     
/*     */     public NamedDistance(String name, Colors.Distance delegate) {
/* 324 */       this.name = name;
/* 325 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(int c1, int c2) {
/* 330 */       return this.delegate.compute(c1, c2);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 335 */       return this.name;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double cie94(double[] lab1, double[] lab2) {
/* 352 */     double dl = lab1[0] - lab2[0];
/* 353 */     double da = lab1[1] - lab2[1];
/* 354 */     double db = lab1[2] - lab2[2];
/* 355 */     double c1 = Math.sqrt(lab1[1] * lab1[1] + lab1[2] * lab1[2]);
/* 356 */     double c2 = Math.sqrt(lab2[1] * lab2[1] + lab2[2] * lab2[2]);
/* 357 */     double dc = c1 - c2;
/* 358 */     double dh = da * da + db * db - dc * dc;
/* 359 */     dh = (dh < 0.0D) ? 0.0D : Math.sqrt(dh);
/* 360 */     double sl = 1.0D;
/* 361 */     double sc = 1.0D + 0.045D * c1;
/* 362 */     double sh = 1.0D + 0.015D * c1;
/* 363 */     double dLKlsl = dl / 2.0D * sl;
/* 364 */     double dCkcsc = dc / 1.0D * sc;
/* 365 */     double dHkhsh = dh / 1.0D * sh;
/* 366 */     return dLKlsl * dLKlsl + dCkcsc * dCkcsc + dHkhsh * dHkhsh;
/*     */   }
/*     */   
/*     */   private static double cie00(double[] lab1, double[] lab2) {
/* 370 */     double delta_h_prime, h_prime_average, c_star_1_ab = Math.sqrt(lab1[1] * lab1[1] + lab1[2] * lab1[2]);
/* 371 */     double c_star_2_ab = Math.sqrt(lab2[1] * lab2[1] + lab2[2] * lab2[2]);
/* 372 */     double c_star_average_ab = (c_star_1_ab + c_star_2_ab) / 2.0D;
/* 373 */     double c_star_average_ab_pot_3 = c_star_average_ab * c_star_average_ab * c_star_average_ab;
/* 374 */     double c_star_average_ab_pot_7 = c_star_average_ab_pot_3 * c_star_average_ab_pot_3 * c_star_average_ab;
/* 375 */     double G = 0.5D * (1.0D - Math.sqrt(c_star_average_ab_pot_7 / (c_star_average_ab_pot_7 + 6.103515625E9D)));
/* 376 */     double a1_prime = (1.0D + G) * lab1[1];
/* 377 */     double a2_prime = (1.0D + G) * lab2[1];
/* 378 */     double C_prime_1 = Math.sqrt(a1_prime * a1_prime + lab1[2] * lab1[2]);
/* 379 */     double C_prime_2 = Math.sqrt(a2_prime * a2_prime + lab2[2] * lab2[2]);
/* 380 */     double h_prime_1 = (Math.toDegrees(Math.atan2(lab1[2], a1_prime)) + 360.0D) % 360.0D;
/* 381 */     double h_prime_2 = (Math.toDegrees(Math.atan2(lab2[2], a2_prime)) + 360.0D) % 360.0D;
/* 382 */     double delta_L_prime = lab2[0] - lab1[0];
/* 383 */     double delta_C_prime = C_prime_2 - C_prime_1;
/* 384 */     double h_bar = Math.abs(h_prime_1 - h_prime_2);
/*     */     
/* 386 */     if (C_prime_1 * C_prime_2 == 0.0D) {
/* 387 */       delta_h_prime = 0.0D;
/* 388 */     } else if (h_bar <= 180.0D) {
/* 389 */       delta_h_prime = h_prime_2 - h_prime_1;
/* 390 */     } else if (h_prime_2 <= h_prime_1) {
/* 391 */       delta_h_prime = h_prime_2 - h_prime_1 + 360.0D;
/*     */     } else {
/* 393 */       delta_h_prime = h_prime_2 - h_prime_1 - 360.0D;
/*     */     } 
/* 395 */     double delta_H_prime = 2.0D * Math.sqrt(C_prime_1 * C_prime_2) * Math.sin(Math.toRadians(delta_h_prime / 2.0D));
/* 396 */     double L_prime_average = (lab1[0] + lab2[0]) / 2.0D;
/* 397 */     double C_prime_average = (C_prime_1 + C_prime_2) / 2.0D;
/*     */     
/* 399 */     if (C_prime_1 * C_prime_2 == 0.0D) {
/* 400 */       h_prime_average = 0.0D;
/* 401 */     } else if (h_bar <= 180.0D) {
/* 402 */       h_prime_average = (h_prime_1 + h_prime_2) / 2.0D;
/* 403 */     } else if (h_prime_1 + h_prime_2 < 360.0D) {
/* 404 */       h_prime_average = (h_prime_1 + h_prime_2 + 360.0D) / 2.0D;
/*     */     } else {
/* 406 */       h_prime_average = (h_prime_1 + h_prime_2 - 360.0D) / 2.0D;
/*     */     } 
/* 408 */     double L_prime_average_minus_50 = L_prime_average - 50.0D;
/* 409 */     double L_prime_average_minus_50_square = L_prime_average_minus_50 * L_prime_average_minus_50;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 414 */     double T = 1.0D - 0.17D * Math.cos(Math.toRadians(h_prime_average - 30.0D)) + 0.24D * Math.cos(Math.toRadians(h_prime_average * 2.0D)) + 0.32D * Math.cos(Math.toRadians(h_prime_average * 3.0D + 6.0D)) - 0.2D * Math.cos(Math.toRadians(h_prime_average * 4.0D - 63.0D));
/*     */     
/* 416 */     double S_L = 1.0D + 0.015D * L_prime_average_minus_50_square / Math.sqrt(20.0D + L_prime_average_minus_50_square);
/* 417 */     double S_C = 1.0D + 0.045D * C_prime_average;
/* 418 */     double S_H = 1.0D + 0.015D * T * C_prime_average;
/* 419 */     double h_prime_average_minus_275_div_25 = (h_prime_average - 275.0D) / 25.0D;
/* 420 */     double h_prime_average_minus_275_div_25_square = h_prime_average_minus_275_div_25 * h_prime_average_minus_275_div_25;
/*     */     
/* 422 */     double delta_theta = 30.0D * Math.exp(-h_prime_average_minus_275_div_25_square);
/* 423 */     double C_prime_average_pot_3 = C_prime_average * C_prime_average * C_prime_average;
/* 424 */     double C_prime_average_pot_7 = C_prime_average_pot_3 * C_prime_average_pot_3 * C_prime_average;
/* 425 */     double R_C = 2.0D * Math.sqrt(C_prime_average_pot_7 / (C_prime_average_pot_7 + 6.103515625E9D));
/* 426 */     double R_T = -Math.sin(Math.toRadians(2.0D * delta_theta)) * R_C;
/* 427 */     double dLKlsl = delta_L_prime / 2.0D * S_L;
/* 428 */     double dCkcsc = delta_C_prime / 1.0D * S_C;
/* 429 */     double dHkhsh = delta_H_prime / 1.0D * S_H;
/* 430 */     return dLKlsl * dLKlsl + dCkcsc * dCkcsc + dHkhsh * dHkhsh + R_T * dCkcsc * dHkhsh;
/*     */   }
/*     */   
/*     */   private static double cam02(int p1, int p2, double[] vc) {
/* 434 */     double[] c1 = jmh2ucs(camlch(p1, vc));
/* 435 */     double[] c2 = jmh2ucs(camlch(p2, vc));
/* 436 */     return scalar(c1, c2);
/*     */   }
/*     */   
/*     */   private static double[] jmh2ucs(double[] lch) {
/* 440 */     double sJ = 1.7000000000000002D * lch[0] / (1.0D + 0.007D * lch[0]);
/* 441 */     double sM = 43.859649122807014D * Math.log(1.0D + 0.0228D * lch[1]);
/* 442 */     double a = sM * Math.cos(Math.toRadians(lch[2]));
/* 443 */     double b = sM * Math.sin(Math.toRadians(lch[2]));
/* 444 */     return new double[] { sJ, a, b };
/*     */   }
/*     */   
/*     */   static double camlch(double[] c1, double[] c2) {
/* 448 */     return camlch(c1, c2, new double[] { 1.0D, 1.0D, 1.0D });
/*     */   }
/*     */ 
/*     */   
/*     */   static double camlch(double[] c1, double[] c2, double[] w) {
/* 453 */     double lightnessWeight = w[0] / 100.0D;
/* 454 */     double colorfulnessWeight = w[1] / 120.0D;
/* 455 */     double hueWeight = w[2] / 360.0D;
/*     */     
/* 457 */     double dl = (c1[0] - c2[0]) * lightnessWeight;
/* 458 */     double dc = (c1[1] - c2[1]) * colorfulnessWeight;
/* 459 */     double dh = hueDifference(c1[2], c2[2], 360.0D) * hueWeight;
/* 460 */     return dl * dl + dc * dc + dh * dh;
/*     */   }
/*     */   
/*     */   private static double hueDifference(double hue1, double hue2, double c) {
/* 464 */     double difference = (hue2 - hue1) % c;
/* 465 */     double ch = c / 2.0D;
/* 466 */     if (difference > ch) difference -= c; 
/* 467 */     if (difference < -ch) difference += c; 
/* 468 */     return difference;
/*     */   }
/*     */   
/*     */   private static double[] rgb(int color) {
/* 472 */     int r = color >> 16 & 0xFF;
/* 473 */     int g = color >> 8 & 0xFF;
/* 474 */     int b = color >> 0 & 0xFF;
/* 475 */     return new double[] { r / 255.0D, g / 255.0D, b / 255.0D };
/*     */   }
/*     */   
/*     */   static double[] rgb2xyz(int color) {
/* 479 */     return rgb2xyz(rgb(color));
/*     */   }
/*     */   
/*     */   static double[] rgb2cielab(int color) {
/* 483 */     return rgb2cielab(rgb(color));
/*     */   }
/*     */   
/*     */   static double[] camlch(int color) {
/* 487 */     return camlch(color, sRGB_typical_environment);
/*     */   }
/*     */   
/*     */   static double[] camlch(int color, double[] vc) {
/* 491 */     return xyz2camlch(rgb2xyz(color), vc);
/*     */   }
/*     */   
/*     */   static double[] camlab(int color) {
/* 495 */     return camlab(color, sRGB_typical_environment);
/*     */   }
/*     */   
/*     */   static double[] camlab(int color, double[] vc) {
/* 499 */     return lch2lab(camlch(color, vc));
/*     */   }
/*     */   
/*     */   static double[] lch2lab(double[] lch) {
/* 503 */     double toRad = 0.017453292519943295D;
/* 504 */     return new double[] { lch[0], lch[1] * Math.cos(lch[2] * toRad), lch[1] * Math.sin(lch[2] * toRad) };
/*     */   }
/*     */   
/*     */   private static double[] xyz2camlch(double[] xyz, double[] vc) {
/* 508 */     double[] XYZ = { xyz[0] * 100.0D, xyz[1] * 100.0D, xyz[2] * 100.0D };
/* 509 */     double[] cam = forwardTransform(XYZ, vc);
/* 510 */     return new double[] { cam[0], cam[3], cam[6] };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] forwardTransform(double[] XYZ, double[] vc) {
/* 531 */     double[] RGB = forwardPreAdaptationConeResponse(XYZ);
/*     */ 
/*     */     
/* 534 */     double[] RGB_c = forwardPostAdaptationConeResponse(RGB, vc);
/*     */     
/* 536 */     double[] RGBPrime = CAT02toHPE(RGB_c);
/*     */     
/* 538 */     double[] RGBPrime_a = forwardResponseCompression(RGBPrime, vc);
/*     */     
/* 540 */     double A = (2.0D * RGBPrime_a[0] + RGBPrime_a[1] + RGBPrime_a[2] / 20.0D - 0.305D) * vc[10];
/*     */     
/* 542 */     double J = 100.0D * Math.pow(A / vc[12], vc[8] * vc[6]);
/*     */     
/* 544 */     double a = RGBPrime_a[0] + (-12.0D * RGBPrime_a[1] + RGBPrime_a[2]) / 11.0D;
/* 545 */     double b = (RGBPrime_a[0] + RGBPrime_a[1] - 2.0D * RGBPrime_a[2]) / 9.0D;
/*     */     
/* 547 */     double h = (Math.toDegrees(Math.atan2(b, a)) + 360.0D) % 360.0D;
/*     */     
/* 549 */     double e = 961.5384615384615D * vc[7] * vc[11] * (Math.cos(Math.toRadians(h) + 2.0D) + 3.8D);
/*     */ 
/*     */     
/* 552 */     double t = e * Math.sqrt(Math.pow(a, 2.0D) + Math.pow(b, 2.0D)) / (RGBPrime_a[0] + RGBPrime_a[1] + 1.05D * RGBPrime_a[2]);
/*     */ 
/*     */     
/* 555 */     double Q = 4.0D / vc[6] * Math.sqrt(J / 100.0D) * (vc[12] + 4.0D) * Math.pow(vc[13], 0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 560 */     double C = Math.signum(t) * Math.pow(Math.abs(t), 0.9D) * Math.sqrt(J / 100.0D) * Math.pow(1.64D - Math.pow(0.29D, vc[9]), 0.73D);
/* 561 */     double M = C * Math.pow(vc[13], 0.25D);
/* 562 */     double s = 100.0D * Math.sqrt(M / Q);
/*     */     
/* 564 */     double H = calculateH(h);
/* 565 */     return new double[] { J, Q, C, M, s, H, h };
/*     */   }
/*     */   
/*     */   private static double calculateH(double h) {
/* 569 */     if (h < 20.14D) h += 360.0D;
/*     */     
/* 571 */     if (h >= 20.14D && h < 90.0D) {
/* 572 */       double i = (h - 20.14D) / 0.8D;
/* 573 */       return 100.0D * i / (i + (90.0D - h) / 0.7D);
/* 574 */     }  if (h < 164.25D) {
/* 575 */       double i = (h - 90.0D) / 0.7D;
/* 576 */       return 100.0D + 100.0D * i / (i + (164.25D - h) / 1.0D);
/* 577 */     }  if (h < 237.53D) {
/* 578 */       double i = (h - 164.25D) / 1.0D;
/* 579 */       return 200.0D + 100.0D * i / (i + (237.53D - h) / 1.2D);
/* 580 */     }  if (h <= 380.14D) {
/* 581 */       double i = (h - 237.53D) / 1.2D;
/* 582 */       double H = 300.0D + 100.0D * i / (i + (380.14D - h) / 0.8D);
/*     */       
/* 584 */       if (H <= 400.0D && H >= 399.999D) H = 0.0D; 
/* 585 */       return H;
/*     */     } 
/* 587 */     throw new IllegalArgumentException("h outside assumed range 0..360: " + h);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[] forwardResponseCompression(double[] RGB, double[] vc) {
/* 592 */     double[] result = new double[3];
/* 593 */     for (int channel = 0; channel < RGB.length; channel++) {
/* 594 */       if (RGB[channel] >= 0.0D) {
/* 595 */         double n = Math.pow(vc[13] * RGB[channel] / 100.0D, 0.42D);
/* 596 */         result[channel] = 400.0D * n / (n + 27.13D) + 0.1D;
/*     */       } else {
/* 598 */         double n = Math.pow(-1.0D * vc[13] * RGB[channel] / 100.0D, 0.42D);
/* 599 */         result[channel] = -400.0D * n / (n + 27.13D) + 0.1D;
/*     */       } 
/*     */     } 
/* 602 */     return result;
/*     */   }
/*     */   
/*     */   private static double[] forwardPostAdaptationConeResponse(double[] RGB, double[] vc) {
/* 606 */     return new double[] { vc[14] * RGB[0], vc[15] * RGB[1], vc[16] * RGB[2] };
/*     */   }
/*     */   
/*     */   public static double[] CAT02toHPE(double[] RGB) {
/* 610 */     double[] RGBPrime = new double[3];
/* 611 */     RGBPrime[0] = 0.7409792D * RGB[0] + 0.218025D * RGB[1] + 0.0410058D * RGB[2];
/* 612 */     RGBPrime[1] = 0.2853532D * RGB[0] + 0.6242014D * RGB[1] + 0.0904454D * RGB[2];
/* 613 */     RGBPrime[2] = -0.009628D * RGB[0] - 0.005698D * RGB[1] + 1.015326D * RGB[2];
/* 614 */     return RGBPrime;
/*     */   }
/*     */   
/*     */   private static double[] forwardPreAdaptationConeResponse(double[] XYZ) {
/* 618 */     double[] RGB = new double[3];
/* 619 */     RGB[0] = 0.7328D * XYZ[0] + 0.4296D * XYZ[1] - 0.1624D * XYZ[2];
/* 620 */     RGB[1] = -0.7036D * XYZ[0] + 1.6975D * XYZ[1] + 0.0061D * XYZ[2];
/* 621 */     RGB[2] = 0.003D * XYZ[0] + 0.0136D * XYZ[1] + 0.9834D * XYZ[2];
/* 622 */     return RGB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double[] vc(double[] xyz_w, double L_A, double Y_b, double[] surrounding) {
/* 649 */     double[] vc = new double[17];
/* 650 */     vc[0] = xyz_w[0];
/* 651 */     vc[1] = xyz_w[1];
/* 652 */     vc[2] = xyz_w[2];
/* 653 */     vc[3] = L_A;
/* 654 */     vc[4] = Y_b;
/* 655 */     vc[5] = surrounding[0];
/* 656 */     vc[6] = surrounding[1];
/* 657 */     vc[7] = surrounding[2];
/*     */     
/* 659 */     double[] RGB_w = forwardPreAdaptationConeResponse(xyz_w);
/*     */     
/* 661 */     double D = Math.max(0.0D, Math.min(1.0D, vc[5] * (1.0D - 0.2777777777777778D * Math.pow(Math.E, (-L_A - 42.0D) / 92.0D))));
/* 662 */     double Yw = xyz_w[1];
/* 663 */     double[] RGB_c = { D * Yw / RGB_w[0] + 1.0D - D, D * Yw / RGB_w[1] + 1.0D - D, D * Yw / RGB_w[2] + 1.0D - D };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 668 */     double L_Ax5 = 5.0D * L_A;
/* 669 */     double k = 1.0D / (L_Ax5 + 1.0D);
/* 670 */     double kpow4 = Math.pow(k, 4.0D);
/* 671 */     vc[13] = 0.2D * kpow4 * L_Ax5 + 0.1D * Math.pow(1.0D - kpow4, 2.0D) * Math.pow(L_Ax5, 0.3333333333333333D);
/*     */ 
/*     */     
/* 674 */     vc[9] = Y_b / Yw;
/* 675 */     vc[8] = 1.48D + Math.sqrt(vc[9]);
/*     */     
/* 677 */     vc[10] = 0.725D * Math.pow(1.0D / vc[9], 0.2D);
/* 678 */     vc[11] = vc[10];
/*     */ 
/*     */ 
/*     */     
/* 682 */     double[] RGB_wc = { RGB_c[0] * RGB_w[0], RGB_c[1] * RGB_w[1], RGB_c[2] * RGB_w[2] };
/* 683 */     double[] RGBPrime_w = CAT02toHPE(RGB_wc);
/* 684 */     double[] RGBPrime_aw = new double[3];
/* 685 */     for (int channel = 0; channel < RGBPrime_w.length; channel++) {
/* 686 */       if (RGBPrime_w[channel] >= 0.0D) {
/* 687 */         double n = Math.pow(vc[13] * RGBPrime_w[channel] / 100.0D, 0.42D);
/* 688 */         RGBPrime_aw[channel] = 400.0D * n / (n + 27.13D) + 0.1D;
/*     */       } else {
/* 690 */         double n = Math.pow(-1.0D * vc[13] * RGBPrime_w[channel] / 100.0D, 0.42D);
/* 691 */         RGBPrime_aw[channel] = -400.0D * n / (n + 27.13D) + 0.1D;
/*     */       } 
/*     */     } 
/* 694 */     vc[12] = (2.0D * RGBPrime_aw[0] + RGBPrime_aw[1] + RGBPrime_aw[2] / 20.0D - 0.305D) * vc[10];
/* 695 */     vc[14] = RGB_c[0];
/* 696 */     vc[15] = RGB_c[1];
/* 697 */     vc[16] = RGB_c[2];
/* 698 */     return vc;
/*     */   }
/*     */   
/*     */   public static double[] rgb2cielab(double[] rgb) {
/* 702 */     return xyz2lab(rgb2xyz(rgb));
/*     */   }
/*     */   
/*     */   private static double[] rgb2xyz(double[] rgb) {
/* 706 */     double vr = pivotRgb(rgb[0]);
/* 707 */     double vg = pivotRgb(rgb[1]);
/* 708 */     double vb = pivotRgb(rgb[2]);
/*     */     
/* 710 */     double x = vr * 0.4124564D + vg * 0.3575761D + vb * 0.1804375D;
/* 711 */     double y = vr * 0.2126729D + vg * 0.7151522D + vb * 0.072175D;
/* 712 */     double z = vr * 0.0193339D + vg * 0.119192D + vb * 0.9503041D;
/* 713 */     return new double[] { x, y, z };
/*     */   }
/*     */   
/*     */   private static double pivotRgb(double n) {
/* 717 */     return (n > 0.04045D) ? Math.pow((n + 0.055D) / 1.055D, 2.4D) : (n / 12.92D);
/*     */   }
/*     */   
/*     */   private static double[] xyz2lab(double[] xyz) {
/* 721 */     double fx = pivotXyz(xyz[0]);
/* 722 */     double fy = pivotXyz(xyz[1]);
/* 723 */     double fz = pivotXyz(xyz[2]);
/* 724 */     double l = 116.0D * fy - 16.0D;
/* 725 */     double a = 500.0D * (fx - fy);
/* 726 */     double b = 200.0D * (fy - fz);
/* 727 */     return new double[] { l, a, b };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double pivotXyz(double n) {
/* 734 */     return (n > 0.008856451679035631D) ? Math.cbrt(n) : ((903.2962962962963D * n + 16.0D) / 116.0D);
/*     */   }
/*     */   
/*     */   private static double sqr(double n) {
/* 738 */     return n * n;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Colors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */