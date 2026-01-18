/*     */ package com.hypixel.hytale.procedurallib.logic;
/*     */ 
/*     */ import com.hypixel.hytale.procedurallib.NoiseFunction;
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
/*     */ public class OldSimplexNoise
/*     */   implements NoiseFunction
/*     */ {
/*  19 */   public static final OldSimplexNoise INSTANCE = new OldSimplexNoise();
/*     */   
/*     */   private static final double STRETCH_CONSTANT_2D = -0.211324865405187D;
/*     */   
/*     */   private static final double SQUISH_CONSTANT_2D = 0.366025403784439D;
/*     */   
/*     */   private static final double STRETCH_CONSTANT_3D = -0.16666666666666666D;
/*     */   
/*     */   private static final double SQUISH_CONSTANT_3D = 0.3333333333333333D;
/*     */   
/*     */   private static final double NORM_CONSTANT_2D = 47.0D;
/*     */   
/*     */   private static final double NORM_CONSTANT_3D = 103.0D;
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y) {
/*     */     double dx_ext, dy_ext;
/*     */     int xsv_ext, ysv_ext;
/*  37 */     double stretchOffset = (x + y) * -0.211324865405187D;
/*  38 */     double xs = x + stretchOffset;
/*  39 */     double ys = y + stretchOffset;
/*     */ 
/*     */     
/*  42 */     int xsb = fastFloor(xs);
/*  43 */     int ysb = fastFloor(ys);
/*     */ 
/*     */     
/*  46 */     double squishOffset = (xsb + ysb) * 0.366025403784439D;
/*  47 */     double xb = xsb + squishOffset;
/*  48 */     double yb = ysb + squishOffset;
/*     */ 
/*     */     
/*  51 */     double xins = xs - xsb;
/*  52 */     double yins = ys - ysb;
/*     */ 
/*     */     
/*  55 */     double inSum = xins + yins;
/*     */ 
/*     */     
/*  58 */     double dx0 = x - xb;
/*  59 */     double dy0 = y - yb;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     double value = 0.0D;
/*     */ 
/*     */     
/*  68 */     double dx1 = dx0 - 1.0D - 0.366025403784439D;
/*  69 */     double dy1 = dy0 - 0.0D - 0.366025403784439D;
/*  70 */     double attn1 = 2.0D - dx1 * dx1 - dy1 * dy1;
/*  71 */     if (attn1 > 0.0D) {
/*  72 */       attn1 *= attn1;
/*  73 */       value += attn1 * attn1 * extrapolate(offsetSeed, xsb + 1, ysb + 0, dx1, dy1);
/*     */     } 
/*     */ 
/*     */     
/*  77 */     double dx2 = dx0 - 0.0D - 0.366025403784439D;
/*  78 */     double dy2 = dy0 - 1.0D - 0.366025403784439D;
/*  79 */     double attn2 = 2.0D - dx2 * dx2 - dy2 * dy2;
/*  80 */     if (attn2 > 0.0D) {
/*  81 */       attn2 *= attn2;
/*  82 */       value += attn2 * attn2 * extrapolate(offsetSeed, xsb + 0, ysb + 1, dx2, dy2);
/*     */     } 
/*     */     
/*  85 */     if (inSum <= 1.0D) {
/*  86 */       double zins = 1.0D - inSum;
/*  87 */       if (zins > xins || zins > yins) {
/*  88 */         if (xins > yins) {
/*  89 */           xsv_ext = xsb + 1;
/*  90 */           ysv_ext = ysb - 1;
/*  91 */           dx_ext = dx0 - 1.0D;
/*  92 */           dy_ext = dy0 + 1.0D;
/*     */         } else {
/*  94 */           xsv_ext = xsb - 1;
/*  95 */           ysv_ext = ysb + 1;
/*  96 */           dx_ext = dx0 + 1.0D;
/*  97 */           dy_ext = dy0 - 1.0D;
/*     */         } 
/*     */       } else {
/* 100 */         xsv_ext = xsb + 1;
/* 101 */         ysv_ext = ysb + 1;
/* 102 */         dx_ext = dx0 - 1.0D - 0.732050807568878D;
/* 103 */         dy_ext = dy0 - 1.0D - 0.732050807568878D;
/*     */       } 
/*     */     } else {
/* 106 */       double zins = 2.0D - inSum;
/* 107 */       if (zins < xins || zins < yins) {
/* 108 */         if (xins > yins) {
/* 109 */           xsv_ext = xsb + 2;
/* 110 */           ysv_ext = ysb + 0;
/* 111 */           dx_ext = dx0 - 2.0D - 0.732050807568878D;
/* 112 */           dy_ext = dy0 + 0.0D - 0.732050807568878D;
/*     */         } else {
/* 114 */           xsv_ext = xsb + 0;
/* 115 */           ysv_ext = ysb + 2;
/* 116 */           dx_ext = dx0 + 0.0D - 0.732050807568878D;
/* 117 */           dy_ext = dy0 - 2.0D - 0.732050807568878D;
/*     */         } 
/*     */       } else {
/* 120 */         dx_ext = dx0;
/* 121 */         dy_ext = dy0;
/* 122 */         xsv_ext = xsb;
/* 123 */         ysv_ext = ysb;
/*     */       } 
/* 125 */       xsb++;
/* 126 */       ysb++;
/* 127 */       dx0 = dx0 - 1.0D - 0.732050807568878D;
/* 128 */       dy0 = dy0 - 1.0D - 0.732050807568878D;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     double attn0 = 2.0D - dx0 * dx0 - dy0 * dy0;
/* 133 */     if (attn0 > 0.0D) {
/* 134 */       attn0 *= attn0;
/* 135 */       value += attn0 * attn0 * extrapolate(offsetSeed, xsb, ysb, dx0, dy0);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     double attn_ext = 2.0D - dx_ext * dx_ext - dy_ext * dy_ext;
/* 140 */     if (attn_ext > 0.0D) {
/* 141 */       attn_ext *= attn_ext;
/* 142 */       value += attn_ext * attn_ext * extrapolate(offsetSeed, xsv_ext, ysv_ext, dx_ext, dy_ext);
/*     */     } 
/*     */     
/* 145 */     return value / 47.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/*     */     double dx_ext0, dy_ext0, dz_ext0, dx_ext1, dy_ext1, dz_ext1;
/*     */     int xsv_ext0, ysv_ext0, zsv_ext0, xsv_ext1, ysv_ext1, zsv_ext1;
/* 152 */     double stretchOffset = (x + y + z) * -0.16666666666666666D;
/* 153 */     double xs = x + stretchOffset;
/* 154 */     double ys = y + stretchOffset;
/* 155 */     double zs = z + stretchOffset;
/*     */ 
/*     */     
/* 158 */     int xsb = fastFloor(xs);
/* 159 */     int ysb = fastFloor(ys);
/* 160 */     int zsb = fastFloor(zs);
/*     */ 
/*     */     
/* 163 */     double squishOffset = (xsb + ysb + zsb) * 0.3333333333333333D;
/* 164 */     double xb = xsb + squishOffset;
/* 165 */     double yb = ysb + squishOffset;
/* 166 */     double zb = zsb + squishOffset;
/*     */ 
/*     */     
/* 169 */     double xins = xs - xsb;
/* 170 */     double yins = ys - ysb;
/* 171 */     double zins = zs - zsb;
/*     */ 
/*     */     
/* 174 */     double inSum = xins + yins + zins;
/*     */ 
/*     */     
/* 177 */     double dx0 = x - xb;
/* 178 */     double dy0 = y - yb;
/* 179 */     double dz0 = z - zb;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     double value = 0.0D;
/* 188 */     if (inSum <= 1.0D) {
/*     */ 
/*     */       
/* 191 */       byte aPoint = 1;
/* 192 */       double aScore = xins;
/* 193 */       byte bPoint = 2;
/* 194 */       double bScore = yins;
/* 195 */       if (aScore >= bScore && zins > bScore) {
/* 196 */         bScore = zins;
/* 197 */         bPoint = 4;
/* 198 */       } else if (aScore < bScore && zins > aScore) {
/* 199 */         aScore = zins;
/* 200 */         aPoint = 4;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 205 */       double wins = 1.0D - inSum;
/* 206 */       if (wins > aScore || wins > bScore) {
/* 207 */         byte c = (bScore > aScore) ? bPoint : aPoint;
/*     */         
/* 209 */         if ((c & 0x1) == 0) {
/* 210 */           xsv_ext0 = xsb - 1;
/* 211 */           xsv_ext1 = xsb;
/* 212 */           dx_ext0 = dx0 + 1.0D;
/* 213 */           dx_ext1 = dx0;
/*     */         } else {
/* 215 */           xsv_ext0 = xsv_ext1 = xsb + 1;
/* 216 */           dx_ext0 = dx_ext1 = dx0 - 1.0D;
/*     */         } 
/*     */         
/* 219 */         if ((c & 0x2) == 0) {
/* 220 */           ysv_ext0 = ysv_ext1 = ysb;
/* 221 */           dy_ext0 = dy_ext1 = dy0;
/* 222 */           if ((c & 0x1) == 0) {
/* 223 */             ysv_ext1--;
/* 224 */             dy_ext1++;
/*     */           } else {
/* 226 */             ysv_ext0--;
/* 227 */             dy_ext0++;
/*     */           } 
/*     */         } else {
/* 230 */           ysv_ext0 = ysv_ext1 = ysb + 1;
/* 231 */           dy_ext0 = dy_ext1 = dy0 - 1.0D;
/*     */         } 
/*     */         
/* 234 */         if ((c & 0x4) == 0) {
/* 235 */           zsv_ext0 = zsb;
/* 236 */           zsv_ext1 = zsb - 1;
/* 237 */           dz_ext0 = dz0;
/* 238 */           dz_ext1 = dz0 + 1.0D;
/*     */         } else {
/* 240 */           zsv_ext0 = zsv_ext1 = zsb + 1;
/* 241 */           dz_ext0 = dz_ext1 = dz0 - 1.0D;
/*     */         } 
/*     */       } else {
/* 244 */         byte c = (byte)(aPoint | bPoint);
/*     */         
/* 246 */         if ((c & 0x1) == 0) {
/* 247 */           xsv_ext0 = xsb;
/* 248 */           xsv_ext1 = xsb - 1;
/* 249 */           dx_ext0 = dx0 - 0.6666666666666666D;
/* 250 */           dx_ext1 = dx0 + 1.0D - 0.3333333333333333D;
/*     */         } else {
/* 252 */           xsv_ext0 = xsv_ext1 = xsb + 1;
/* 253 */           dx_ext0 = dx0 - 1.0D - 0.6666666666666666D;
/* 254 */           dx_ext1 = dx0 - 1.0D - 0.3333333333333333D;
/*     */         } 
/*     */         
/* 257 */         if ((c & 0x2) == 0) {
/* 258 */           ysv_ext0 = ysb;
/* 259 */           ysv_ext1 = ysb - 1;
/* 260 */           dy_ext0 = dy0 - 0.6666666666666666D;
/* 261 */           dy_ext1 = dy0 + 1.0D - 0.3333333333333333D;
/*     */         } else {
/* 263 */           ysv_ext0 = ysv_ext1 = ysb + 1;
/* 264 */           dy_ext0 = dy0 - 1.0D - 0.6666666666666666D;
/* 265 */           dy_ext1 = dy0 - 1.0D - 0.3333333333333333D;
/*     */         } 
/*     */         
/* 268 */         if ((c & 0x4) == 0) {
/* 269 */           zsv_ext0 = zsb;
/* 270 */           zsv_ext1 = zsb - 1;
/* 271 */           dz_ext0 = dz0 - 0.6666666666666666D;
/* 272 */           dz_ext1 = dz0 + 1.0D - 0.3333333333333333D;
/*     */         } else {
/* 274 */           zsv_ext0 = zsv_ext1 = zsb + 1;
/* 275 */           dz_ext0 = dz0 - 1.0D - 0.6666666666666666D;
/* 276 */           dz_ext1 = dz0 - 1.0D - 0.3333333333333333D;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 281 */       double attn0 = 2.0D - dx0 * dx0 - dy0 * dy0 - dz0 * dz0;
/* 282 */       if (attn0 > 0.0D) {
/* 283 */         attn0 *= attn0;
/* 284 */         value += attn0 * attn0 * extrapolate(offsetSeed, xsb + 0, ysb + 0, zsb + 0, dx0, dy0, dz0);
/*     */       } 
/*     */ 
/*     */       
/* 288 */       double dx1 = dx0 - 1.0D - 0.3333333333333333D;
/* 289 */       double dy1 = dy0 - 0.0D - 0.3333333333333333D;
/* 290 */       double dz1 = dz0 - 0.0D - 0.3333333333333333D;
/* 291 */       double attn1 = 2.0D - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
/* 292 */       if (attn1 > 0.0D) {
/* 293 */         attn1 *= attn1;
/* 294 */         value += attn1 * attn1 * extrapolate(offsetSeed, xsb + 1, ysb + 0, zsb + 0, dx1, dy1, dz1);
/*     */       } 
/*     */ 
/*     */       
/* 298 */       double dx2 = dx0 - 0.0D - 0.3333333333333333D;
/* 299 */       double dy2 = dy0 - 1.0D - 0.3333333333333333D;
/* 300 */       double dz2 = dz1;
/* 301 */       double attn2 = 2.0D - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
/* 302 */       if (attn2 > 0.0D) {
/* 303 */         attn2 *= attn2;
/* 304 */         value += attn2 * attn2 * extrapolate(offsetSeed, xsb + 0, ysb + 1, zsb + 0, dx2, dy2, dz2);
/*     */       } 
/*     */ 
/*     */       
/* 308 */       double dx3 = dx2;
/* 309 */       double dy3 = dy1;
/* 310 */       double dz3 = dz0 - 1.0D - 0.3333333333333333D;
/* 311 */       double attn3 = 2.0D - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
/* 312 */       if (attn3 > 0.0D) {
/* 313 */         attn3 *= attn3;
/* 314 */         value += attn3 * attn3 * extrapolate(offsetSeed, xsb + 0, ysb + 0, zsb + 1, dx3, dy3, dz3);
/*     */       } 
/* 316 */     } else if (inSum >= 2.0D) {
/*     */ 
/*     */       
/* 319 */       byte aPoint = 6;
/* 320 */       double aScore = xins;
/* 321 */       byte bPoint = 5;
/* 322 */       double bScore = yins;
/* 323 */       if (aScore <= bScore && zins < bScore) {
/* 324 */         bScore = zins;
/* 325 */         bPoint = 3;
/* 326 */       } else if (aScore > bScore && zins < aScore) {
/* 327 */         aScore = zins;
/* 328 */         aPoint = 3;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 333 */       double wins = 3.0D - inSum;
/* 334 */       if (wins < aScore || wins < bScore) {
/* 335 */         byte c = (bScore < aScore) ? bPoint : aPoint;
/*     */         
/* 337 */         if ((c & 0x1) != 0) {
/* 338 */           xsv_ext0 = xsb + 2;
/* 339 */           xsv_ext1 = xsb + 1;
/* 340 */           dx_ext0 = dx0 - 2.0D - 1.0D;
/* 341 */           dx_ext1 = dx0 - 1.0D - 1.0D;
/*     */         } else {
/* 343 */           xsv_ext0 = xsv_ext1 = xsb;
/* 344 */           dx_ext0 = dx_ext1 = dx0 - 1.0D;
/*     */         } 
/*     */         
/* 347 */         if ((c & 0x2) != 0) {
/* 348 */           ysv_ext0 = ysv_ext1 = ysb + 1;
/* 349 */           dy_ext0 = dy_ext1 = dy0 - 1.0D - 1.0D;
/* 350 */           if ((c & 0x1) != 0) {
/* 351 */             ysv_ext1++;
/* 352 */             dy_ext1--;
/*     */           } else {
/* 354 */             ysv_ext0++;
/* 355 */             dy_ext0--;
/*     */           } 
/*     */         } else {
/* 358 */           ysv_ext0 = ysv_ext1 = ysb;
/* 359 */           dy_ext0 = dy_ext1 = dy0 - 1.0D;
/*     */         } 
/*     */         
/* 362 */         if ((c & 0x4) != 0) {
/* 363 */           zsv_ext0 = zsb + 1;
/* 364 */           zsv_ext1 = zsb + 2;
/* 365 */           dz_ext0 = dz0 - 1.0D - 1.0D;
/* 366 */           dz_ext1 = dz0 - 2.0D - 1.0D;
/*     */         } else {
/* 368 */           zsv_ext0 = zsv_ext1 = zsb;
/* 369 */           dz_ext0 = dz_ext1 = dz0 - 1.0D;
/*     */         } 
/*     */       } else {
/* 372 */         byte c = (byte)(aPoint & bPoint);
/*     */         
/* 374 */         if ((c & 0x1) != 0) {
/* 375 */           xsv_ext0 = xsb + 1;
/* 376 */           xsv_ext1 = xsb + 2;
/* 377 */           dx_ext0 = dx0 - 1.0D - 0.3333333333333333D;
/* 378 */           dx_ext1 = dx0 - 2.0D - 0.6666666666666666D;
/*     */         } else {
/* 380 */           xsv_ext0 = xsv_ext1 = xsb;
/* 381 */           dx_ext0 = dx0 - 0.3333333333333333D;
/* 382 */           dx_ext1 = dx0 - 0.6666666666666666D;
/*     */         } 
/*     */         
/* 385 */         if ((c & 0x2) != 0) {
/* 386 */           ysv_ext0 = ysb + 1;
/* 387 */           ysv_ext1 = ysb + 2;
/* 388 */           dy_ext0 = dy0 - 1.0D - 0.3333333333333333D;
/* 389 */           dy_ext1 = dy0 - 2.0D - 0.6666666666666666D;
/*     */         } else {
/* 391 */           ysv_ext0 = ysv_ext1 = ysb;
/* 392 */           dy_ext0 = dy0 - 0.3333333333333333D;
/* 393 */           dy_ext1 = dy0 - 0.6666666666666666D;
/*     */         } 
/*     */         
/* 396 */         if ((c & 0x4) != 0) {
/* 397 */           zsv_ext0 = zsb + 1;
/* 398 */           zsv_ext1 = zsb + 2;
/* 399 */           dz_ext0 = dz0 - 1.0D - 0.3333333333333333D;
/* 400 */           dz_ext1 = dz0 - 2.0D - 0.6666666666666666D;
/*     */         } else {
/* 402 */           zsv_ext0 = zsv_ext1 = zsb;
/* 403 */           dz_ext0 = dz0 - 0.3333333333333333D;
/* 404 */           dz_ext1 = dz0 - 0.6666666666666666D;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 409 */       double dx3 = dx0 - 1.0D - 0.6666666666666666D;
/* 410 */       double dy3 = dy0 - 1.0D - 0.6666666666666666D;
/* 411 */       double dz3 = dz0 - 0.0D - 0.6666666666666666D;
/* 412 */       double attn3 = 2.0D - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
/* 413 */       if (attn3 > 0.0D) {
/* 414 */         attn3 *= attn3;
/* 415 */         value += attn3 * attn3 * extrapolate(offsetSeed, xsb + 1, ysb + 1, zsb + 0, dx3, dy3, dz3);
/*     */       } 
/*     */ 
/*     */       
/* 419 */       double dx2 = dx3;
/* 420 */       double dy2 = dy0 - 0.0D - 0.6666666666666666D;
/* 421 */       double dz2 = dz0 - 1.0D - 0.6666666666666666D;
/* 422 */       double attn2 = 2.0D - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
/* 423 */       if (attn2 > 0.0D) {
/* 424 */         attn2 *= attn2;
/* 425 */         value += attn2 * attn2 * extrapolate(offsetSeed, xsb + 1, ysb + 0, zsb + 1, dx2, dy2, dz2);
/*     */       } 
/*     */ 
/*     */       
/* 429 */       double dx1 = dx0 - 0.0D - 0.6666666666666666D;
/* 430 */       double dy1 = dy3;
/* 431 */       double dz1 = dz2;
/* 432 */       double attn1 = 2.0D - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
/* 433 */       if (attn1 > 0.0D) {
/* 434 */         attn1 *= attn1;
/* 435 */         value += attn1 * attn1 * extrapolate(offsetSeed, xsb + 0, ysb + 1, zsb + 1, dx1, dy1, dz1);
/*     */       } 
/*     */ 
/*     */       
/* 439 */       dx0 = dx0 - 1.0D - 1.0D;
/* 440 */       dy0 = dy0 - 1.0D - 1.0D;
/* 441 */       dz0 = dz0 - 1.0D - 1.0D;
/* 442 */       double attn0 = 2.0D - dx0 * dx0 - dy0 * dy0 - dz0 * dz0;
/* 443 */       if (attn0 > 0.0D) {
/* 444 */         attn0 *= attn0;
/* 445 */         value += attn0 * attn0 * extrapolate(offsetSeed, xsb + 1, ysb + 1, zsb + 1, dx0, dy0, dz0);
/*     */       } 
/*     */     } else {
/*     */       double aScore;
/*     */       
/*     */       boolean bool1, bool2;
/*     */       
/*     */       double bScore;
/*     */       
/*     */       boolean bool3, bool4;
/*     */       
/* 456 */       double p1 = xins + yins;
/* 457 */       if (p1 > 1.0D) {
/* 458 */         aScore = p1 - 1.0D;
/* 459 */         bool1 = true;
/* 460 */         bool2 = true;
/*     */       } else {
/* 462 */         aScore = 1.0D - p1;
/* 463 */         bool1 = true;
/* 464 */         bool2 = false;
/*     */       } 
/*     */ 
/*     */       
/* 468 */       double p2 = xins + zins;
/* 469 */       if (p2 > 1.0D) {
/* 470 */         bScore = p2 - 1.0D;
/* 471 */         bool3 = true;
/* 472 */         bool4 = true;
/*     */       } else {
/* 474 */         bScore = 1.0D - p2;
/* 475 */         bool3 = true;
/* 476 */         bool4 = false;
/*     */       } 
/*     */ 
/*     */       
/* 480 */       double p3 = yins + zins;
/* 481 */       if (p3 > 1.0D) {
/* 482 */         double score = p3 - 1.0D;
/* 483 */         if (aScore <= bScore && aScore < score) {
/* 484 */           aScore = score;
/* 485 */           bool1 = true;
/* 486 */           bool2 = true;
/* 487 */         } else if (aScore > bScore && bScore < score) {
/* 488 */           bScore = score;
/* 489 */           bool3 = true;
/* 490 */           bool4 = true;
/*     */         } 
/*     */       } else {
/* 493 */         double score = 1.0D - p3;
/* 494 */         if (aScore <= bScore && aScore < score) {
/* 495 */           aScore = score;
/* 496 */           bool1 = true;
/* 497 */           bool2 = false;
/* 498 */         } else if (aScore > bScore && bScore < score) {
/* 499 */           bScore = score;
/* 500 */           bool3 = true;
/* 501 */           bool4 = false;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 506 */       if (bool2 == bool4) {
/* 507 */         if (bool2) {
/*     */ 
/*     */           
/* 510 */           dx_ext0 = dx0 - 1.0D - 1.0D;
/* 511 */           dy_ext0 = dy0 - 1.0D - 1.0D;
/* 512 */           dz_ext0 = dz0 - 1.0D - 1.0D;
/* 513 */           xsv_ext0 = xsb + 1;
/* 514 */           ysv_ext0 = ysb + 1;
/* 515 */           zsv_ext0 = zsb + 1;
/*     */ 
/*     */           
/* 518 */           byte c = (byte)(bool1 & bool3);
/* 519 */           if ((c & 0x1) != 0) {
/* 520 */             dx_ext1 = dx0 - 2.0D - 0.6666666666666666D;
/* 521 */             dy_ext1 = dy0 - 0.6666666666666666D;
/* 522 */             dz_ext1 = dz0 - 0.6666666666666666D;
/* 523 */             xsv_ext1 = xsb + 2;
/* 524 */             ysv_ext1 = ysb;
/* 525 */             zsv_ext1 = zsb;
/* 526 */           } else if ((c & 0x2) != 0) {
/* 527 */             dx_ext1 = dx0 - 0.6666666666666666D;
/* 528 */             dy_ext1 = dy0 - 2.0D - 0.6666666666666666D;
/* 529 */             dz_ext1 = dz0 - 0.6666666666666666D;
/* 530 */             xsv_ext1 = xsb;
/* 531 */             ysv_ext1 = ysb + 2;
/* 532 */             zsv_ext1 = zsb;
/*     */           } else {
/* 534 */             dx_ext1 = dx0 - 0.6666666666666666D;
/* 535 */             dy_ext1 = dy0 - 0.6666666666666666D;
/* 536 */             dz_ext1 = dz0 - 2.0D - 0.6666666666666666D;
/* 537 */             xsv_ext1 = xsb;
/* 538 */             ysv_ext1 = ysb;
/* 539 */             zsv_ext1 = zsb + 2;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 544 */           dx_ext0 = dx0;
/* 545 */           dy_ext0 = dy0;
/* 546 */           dz_ext0 = dz0;
/* 547 */           xsv_ext0 = xsb;
/* 548 */           ysv_ext0 = ysb;
/* 549 */           zsv_ext0 = zsb;
/*     */ 
/*     */           
/* 552 */           byte c = (byte)(bool1 | bool3);
/* 553 */           if ((c & 0x1) == 0) {
/* 554 */             dx_ext1 = dx0 + 1.0D - 0.3333333333333333D;
/* 555 */             dy_ext1 = dy0 - 1.0D - 0.3333333333333333D;
/* 556 */             dz_ext1 = dz0 - 1.0D - 0.3333333333333333D;
/* 557 */             xsv_ext1 = xsb - 1;
/* 558 */             ysv_ext1 = ysb + 1;
/* 559 */             zsv_ext1 = zsb + 1;
/* 560 */           } else if ((c & 0x2) == 0) {
/* 561 */             dx_ext1 = dx0 - 1.0D - 0.3333333333333333D;
/* 562 */             dy_ext1 = dy0 + 1.0D - 0.3333333333333333D;
/* 563 */             dz_ext1 = dz0 - 1.0D - 0.3333333333333333D;
/* 564 */             xsv_ext1 = xsb + 1;
/* 565 */             ysv_ext1 = ysb - 1;
/* 566 */             zsv_ext1 = zsb + 1;
/*     */           } else {
/* 568 */             dx_ext1 = dx0 - 1.0D - 0.3333333333333333D;
/* 569 */             dy_ext1 = dy0 - 1.0D - 0.3333333333333333D;
/* 570 */             dz_ext1 = dz0 + 1.0D - 0.3333333333333333D;
/* 571 */             xsv_ext1 = xsb + 1;
/* 572 */             ysv_ext1 = ysb + 1;
/* 573 */             zsv_ext1 = zsb - 1;
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         byte c1, c2;
/* 578 */         if (bool2) {
/* 579 */           c1 = bool1;
/* 580 */           c2 = bool3;
/*     */         } else {
/* 582 */           c1 = bool3;
/* 583 */           c2 = bool1;
/*     */         } 
/*     */ 
/*     */         
/* 587 */         if ((c1 & 0x1) == 0) {
/* 588 */           dx_ext0 = dx0 + 1.0D - 0.3333333333333333D;
/* 589 */           dy_ext0 = dy0 - 1.0D - 0.3333333333333333D;
/* 590 */           dz_ext0 = dz0 - 1.0D - 0.3333333333333333D;
/* 591 */           xsv_ext0 = xsb - 1;
/* 592 */           ysv_ext0 = ysb + 1;
/* 593 */           zsv_ext0 = zsb + 1;
/* 594 */         } else if ((c1 & 0x2) == 0) {
/* 595 */           dx_ext0 = dx0 - 1.0D - 0.3333333333333333D;
/* 596 */           dy_ext0 = dy0 + 1.0D - 0.3333333333333333D;
/* 597 */           dz_ext0 = dz0 - 1.0D - 0.3333333333333333D;
/* 598 */           xsv_ext0 = xsb + 1;
/* 599 */           ysv_ext0 = ysb - 1;
/* 600 */           zsv_ext0 = zsb + 1;
/*     */         } else {
/* 602 */           dx_ext0 = dx0 - 1.0D - 0.3333333333333333D;
/* 603 */           dy_ext0 = dy0 - 1.0D - 0.3333333333333333D;
/* 604 */           dz_ext0 = dz0 + 1.0D - 0.3333333333333333D;
/* 605 */           xsv_ext0 = xsb + 1;
/* 606 */           ysv_ext0 = ysb + 1;
/* 607 */           zsv_ext0 = zsb - 1;
/*     */         } 
/*     */ 
/*     */         
/* 611 */         dx_ext1 = dx0 - 0.6666666666666666D;
/* 612 */         dy_ext1 = dy0 - 0.6666666666666666D;
/* 613 */         dz_ext1 = dz0 - 0.6666666666666666D;
/* 614 */         xsv_ext1 = xsb;
/* 615 */         ysv_ext1 = ysb;
/* 616 */         zsv_ext1 = zsb;
/* 617 */         if ((c2 & 0x1) != 0) {
/* 618 */           dx_ext1 -= 2.0D;
/* 619 */           xsv_ext1 += 2;
/* 620 */         } else if ((c2 & 0x2) != 0) {
/* 621 */           dy_ext1 -= 2.0D;
/* 622 */           ysv_ext1 += 2;
/*     */         } else {
/* 624 */           dz_ext1 -= 2.0D;
/* 625 */           zsv_ext1 += 2;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 630 */       double dx1 = dx0 - 1.0D - 0.3333333333333333D;
/* 631 */       double dy1 = dy0 - 0.0D - 0.3333333333333333D;
/* 632 */       double dz1 = dz0 - 0.0D - 0.3333333333333333D;
/* 633 */       double attn1 = 2.0D - dx1 * dx1 - dy1 * dy1 - dz1 * dz1;
/* 634 */       if (attn1 > 0.0D) {
/* 635 */         attn1 *= attn1;
/* 636 */         value += attn1 * attn1 * extrapolate(offsetSeed, xsb + 1, ysb + 0, zsb + 0, dx1, dy1, dz1);
/*     */       } 
/*     */ 
/*     */       
/* 640 */       double dx2 = dx0 - 0.0D - 0.3333333333333333D;
/* 641 */       double dy2 = dy0 - 1.0D - 0.3333333333333333D;
/* 642 */       double dz2 = dz1;
/* 643 */       double attn2 = 2.0D - dx2 * dx2 - dy2 * dy2 - dz2 * dz2;
/* 644 */       if (attn2 > 0.0D) {
/* 645 */         attn2 *= attn2;
/* 646 */         value += attn2 * attn2 * extrapolate(offsetSeed, xsb + 0, ysb + 1, zsb + 0, dx2, dy2, dz2);
/*     */       } 
/*     */ 
/*     */       
/* 650 */       double dx3 = dx2;
/* 651 */       double dy3 = dy1;
/* 652 */       double dz3 = dz0 - 1.0D - 0.3333333333333333D;
/* 653 */       double attn3 = 2.0D - dx3 * dx3 - dy3 * dy3 - dz3 * dz3;
/* 654 */       if (attn3 > 0.0D) {
/* 655 */         attn3 *= attn3;
/* 656 */         value += attn3 * attn3 * extrapolate(offsetSeed, xsb + 0, ysb + 0, zsb + 1, dx3, dy3, dz3);
/*     */       } 
/*     */ 
/*     */       
/* 660 */       double dx4 = dx0 - 1.0D - 0.6666666666666666D;
/* 661 */       double dy4 = dy0 - 1.0D - 0.6666666666666666D;
/* 662 */       double dz4 = dz0 - 0.0D - 0.6666666666666666D;
/* 663 */       double attn4 = 2.0D - dx4 * dx4 - dy4 * dy4 - dz4 * dz4;
/* 664 */       if (attn4 > 0.0D) {
/* 665 */         attn4 *= attn4;
/* 666 */         value += attn4 * attn4 * extrapolate(offsetSeed, xsb + 1, ysb + 1, zsb + 0, dx4, dy4, dz4);
/*     */       } 
/*     */ 
/*     */       
/* 670 */       double dx5 = dx4;
/* 671 */       double dy5 = dy0 - 0.0D - 0.6666666666666666D;
/* 672 */       double dz5 = dz0 - 1.0D - 0.6666666666666666D;
/* 673 */       double attn5 = 2.0D - dx5 * dx5 - dy5 * dy5 - dz5 * dz5;
/* 674 */       if (attn5 > 0.0D) {
/* 675 */         attn5 *= attn5;
/* 676 */         value += attn5 * attn5 * extrapolate(offsetSeed, xsb + 1, ysb + 0, zsb + 1, dx5, dy5, dz5);
/*     */       } 
/*     */ 
/*     */       
/* 680 */       double dx6 = dx0 - 0.0D - 0.6666666666666666D;
/* 681 */       double dy6 = dy4;
/* 682 */       double dz6 = dz5;
/* 683 */       double attn6 = 2.0D - dx6 * dx6 - dy6 * dy6 - dz6 * dz6;
/* 684 */       if (attn6 > 0.0D) {
/* 685 */         attn6 *= attn6;
/* 686 */         value += attn6 * attn6 * extrapolate(offsetSeed, xsb + 0, ysb + 1, zsb + 1, dx6, dy6, dz6);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 691 */     double attn_ext0 = 2.0D - dx_ext0 * dx_ext0 - dy_ext0 * dy_ext0 - dz_ext0 * dz_ext0;
/* 692 */     if (attn_ext0 > 0.0D) {
/* 693 */       attn_ext0 *= attn_ext0;
/* 694 */       value += attn_ext0 * attn_ext0 * extrapolate(offsetSeed, xsv_ext0, ysv_ext0, zsv_ext0, dx_ext0, dy_ext0, dz_ext0);
/*     */     } 
/*     */ 
/*     */     
/* 698 */     double attn_ext1 = 2.0D - dx_ext1 * dx_ext1 - dy_ext1 * dy_ext1 - dz_ext1 * dz_ext1;
/* 699 */     if (attn_ext1 > 0.0D) {
/* 700 */       attn_ext1 *= attn_ext1;
/* 701 */       value += attn_ext1 * attn_ext1 * extrapolate(offsetSeed, xsv_ext1, ysv_ext1, zsv_ext1, dx_ext1, dy_ext1, dz_ext1);
/*     */     } 
/*     */     
/* 704 */     return value / 103.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 710 */     return "OldSimplexNoise{}";
/*     */   }
/*     */   
/*     */   private static double extrapolate(int seed, int x, int y, double xd, double yd) {
/* 714 */     int hash = seed;
/* 715 */     hash ^= 1619 * x;
/* 716 */     hash ^= 31337 * y;
/*     */     
/* 718 */     hash = hash * hash * hash * 60493;
/* 719 */     hash = hash >> 13 ^ hash;
/*     */     
/* 721 */     DoubleArray.Double2 g = gradients2D[hash & 0x7];
/*     */     
/* 723 */     return xd * g.x + yd * g.y;
/*     */   }
/*     */   
/*     */   private static double extrapolate(int seed, int x, int y, int z, double xd, double yd, double zd) {
/* 727 */     int hash = seed;
/* 728 */     hash ^= 1619 * x;
/* 729 */     hash ^= 31337 * y;
/* 730 */     hash ^= 6971 * z;
/*     */     
/* 732 */     hash = hash * hash * hash * 60493;
/* 733 */     hash = hash >> 13 ^ hash;
/*     */     
/* 735 */     DoubleArray.Double3 g = gradients3D[hash % gradients3D.length];
/*     */     
/* 737 */     return xd * g.x + yd * g.y + zd * g.z;
/*     */   }
/*     */   
/*     */   private static int fastFloor(double x) {
/* 741 */     int xi = (int)x;
/* 742 */     return (x < xi) ? (xi - 1) : xi;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 748 */   private static DoubleArray.Double2[] gradients2D = new DoubleArray.Double2[] { new DoubleArray.Double2(5.0D, 2.0D), new DoubleArray.Double2(2.0D, 5.0D), new DoubleArray.Double2(-5.0D, 2.0D), new DoubleArray.Double2(-2.0D, 5.0D), new DoubleArray.Double2(5.0D, -2.0D), new DoubleArray.Double2(2.0D, -5.0D), new DoubleArray.Double2(-5.0D, -2.0D), new DoubleArray.Double2(-2.0D, -5.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 760 */   private static DoubleArray.Double3[] gradients3D = new DoubleArray.Double3[] { new DoubleArray.Double3(-11.0D, 4.0D, 4.0D), new DoubleArray.Double3(-4.0D, 11.0D, 4.0D), new DoubleArray.Double3(-4.0D, 4.0D, 11.0D), new DoubleArray.Double3(11.0D, 4.0D, 4.0D), new DoubleArray.Double3(4.0D, 11.0D, 4.0D), new DoubleArray.Double3(4.0D, 4.0D, 11.0D), new DoubleArray.Double3(-11.0D, -4.0D, 4.0D), new DoubleArray.Double3(-4.0D, -11.0D, 4.0D), new DoubleArray.Double3(-4.0D, -4.0D, 11.0D), new DoubleArray.Double3(11.0D, -4.0D, 4.0D), new DoubleArray.Double3(4.0D, -11.0D, 4.0D), new DoubleArray.Double3(4.0D, -4.0D, 11.0D), new DoubleArray.Double3(-11.0D, 4.0D, -4.0D), new DoubleArray.Double3(-4.0D, 11.0D, -4.0D), new DoubleArray.Double3(-4.0D, 4.0D, -11.0D), new DoubleArray.Double3(11.0D, 4.0D, -4.0D), new DoubleArray.Double3(4.0D, 11.0D, -4.0D), new DoubleArray.Double3(4.0D, 4.0D, -11.0D), new DoubleArray.Double3(-11.0D, -4.0D, -4.0D), new DoubleArray.Double3(-4.0D, -11.0D, -4.0D), new DoubleArray.Double3(-4.0D, -4.0D, -11.0D), new DoubleArray.Double3(11.0D, -4.0D, -4.0D), new DoubleArray.Double3(4.0D, -11.0D, -4.0D), new DoubleArray.Double3(4.0D, -4.0D, -11.0D) };
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\OldSimplexNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */