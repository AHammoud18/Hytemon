/*     */ package com.hypixel.hytale.builtin.hytalegenerator.fields.noise;
/*     */ 
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
/*     */ class Simplex
/*     */ {
/*  24 */   private static final double F2 = 0.5D * (Math.sqrt(3.0D) - 1.0D);
/*  25 */   private static final double G2 = (3.0D - Math.sqrt(3.0D)) / 6.0D;
/*     */   private static final double F3 = 0.3333333333333333D;
/*     */   private static final double G3 = 0.16666666666666666D;
/*  28 */   private static final double F4 = (Math.sqrt(5.0D) - 1.0D) / 4.0D;
/*  29 */   private static final double G4 = (5.0D - Math.sqrt(5.0D)) / 20.0D;
/*  30 */   private static final Grad[] grad3 = new Grad[] { new Grad(1.0D, 1.0D, 0.0D), new Grad(-1.0D, 1.0D, 0.0D), new Grad(1.0D, -1.0D, 0.0D), new Grad(-1.0D, -1.0D, 0.0D), new Grad(1.0D, 0.0D, 1.0D), new Grad(-1.0D, 0.0D, 1.0D), new Grad(1.0D, 0.0D, -1.0D), new Grad(-1.0D, 0.0D, -1.0D), new Grad(0.0D, 1.0D, 1.0D), new Grad(0.0D, -1.0D, 1.0D), new Grad(0.0D, 1.0D, -1.0D), new Grad(0.0D, -1.0D, -1.0D) };
/*     */ 
/*     */   
/*  33 */   private static final Grad[] grad4 = new Grad[] { new Grad(0.0D, 1.0D, 1.0D, 1.0D), new Grad(0.0D, 1.0D, 1.0D, -1.0D), new Grad(0.0D, 1.0D, -1.0D, 1.0D), new Grad(0.0D, 1.0D, -1.0D, -1.0D), new Grad(0.0D, -1.0D, 1.0D, 1.0D), new Grad(0.0D, -1.0D, 1.0D, -1.0D), new Grad(0.0D, -1.0D, -1.0D, 1.0D), new Grad(0.0D, -1.0D, -1.0D, -1.0D), new Grad(1.0D, 0.0D, 1.0D, 1.0D), new Grad(1.0D, 0.0D, 1.0D, -1.0D), new Grad(1.0D, 0.0D, -1.0D, 1.0D), new Grad(1.0D, 0.0D, -1.0D, -1.0D), new Grad(-1.0D, 0.0D, 1.0D, 1.0D), new Grad(-1.0D, 0.0D, 1.0D, -1.0D), new Grad(-1.0D, 0.0D, -1.0D, 1.0D), new Grad(-1.0D, 0.0D, -1.0D, -1.0D), new Grad(1.0D, 1.0D, 0.0D, 1.0D), new Grad(1.0D, 1.0D, 0.0D, -1.0D), new Grad(1.0D, -1.0D, 0.0D, 1.0D), new Grad(1.0D, -1.0D, 0.0D, -1.0D), new Grad(-1.0D, 1.0D, 0.0D, 1.0D), new Grad(-1.0D, 1.0D, 0.0D, -1.0D), new Grad(-1.0D, -1.0D, 0.0D, 1.0D), new Grad(-1.0D, -1.0D, 0.0D, -1.0D), new Grad(1.0D, 1.0D, 1.0D, 0.0D), new Grad(1.0D, 1.0D, -1.0D, 0.0D), new Grad(1.0D, -1.0D, 1.0D, 0.0D), new Grad(1.0D, -1.0D, -1.0D, 0.0D), new Grad(-1.0D, 1.0D, 1.0D, 0.0D), new Grad(-1.0D, 1.0D, -1.0D, 0.0D), new Grad(-1.0D, -1.0D, 1.0D, 0.0D), new Grad(-1.0D, -1.0D, -1.0D, 0.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private static final short[] p = new short[] { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final short[] perm = new short[512];
/*  56 */   private static final short[] permMod12 = new short[512];
/*     */   
/*     */   static {
/*  59 */     for (int i = 0; i < 512; i++) {
/*  60 */       perm[i] = p[i & 0xFF];
/*  61 */       permMod12[i] = (short)(perm[i] % 12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int fastfloor(double x) {
/*  67 */     int xi = (int)x;
/*  68 */     return (x < xi) ? (xi - 1) : xi;
/*     */   }
/*     */   
/*     */   private static double dot(@Nonnull Grad g, double x, double y) {
/*  72 */     return g.x * x + g.y * y;
/*     */   }
/*     */   
/*     */   private static double dot(@Nonnull Grad g, double x, double y, double z) {
/*  76 */     return g.x * x + g.y * y + g.z * z;
/*     */   }
/*     */   
/*     */   private static double dot(@Nonnull Grad g, double x, double y, double z, double w) {
/*  80 */     return g.x * x + g.y * y + g.z * z + g.w * w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double noise(double xin, double yin) {
/*     */     double n0, n1, n2;
/*     */     int i1, j1;
/*  88 */     double s = (xin + yin) * F2;
/*  89 */     int i = fastfloor(xin + s);
/*  90 */     int j = fastfloor(yin + s);
/*  91 */     double t = (i + j) * G2;
/*  92 */     double X0 = i - t;
/*  93 */     double Y0 = j - t;
/*  94 */     double x0 = xin - X0;
/*  95 */     double y0 = yin - Y0;
/*     */ 
/*     */ 
/*     */     
/*  99 */     if (x0 > y0) {
/* 100 */       i1 = 1;
/* 101 */       j1 = 0;
/*     */     } else {
/*     */       
/* 104 */       i1 = 0;
/* 105 */       j1 = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     double x1 = x0 - i1 + G2;
/* 111 */     double y1 = y0 - j1 + G2;
/* 112 */     double x2 = x0 - 1.0D + 2.0D * G2;
/* 113 */     double y2 = y0 - 1.0D + 2.0D * G2;
/*     */     
/* 115 */     int ii = i & 0xFF;
/* 116 */     int jj = j & 0xFF;
/* 117 */     int gi0 = permMod12[ii + perm[jj]];
/* 118 */     int gi1 = permMod12[ii + i1 + perm[jj + j1]];
/* 119 */     int gi2 = permMod12[ii + 1 + perm[jj + 1]];
/*     */     
/* 121 */     double t0 = 0.5D - x0 * x0 - y0 * y0;
/* 122 */     if (t0 < 0.0D) { n0 = 0.0D; }
/*     */     else
/* 124 */     { t0 *= t0;
/* 125 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0); }
/*     */     
/* 127 */     double t1 = 0.5D - x1 * x1 - y1 * y1;
/* 128 */     if (t1 < 0.0D) { n1 = 0.0D; }
/*     */     else
/* 130 */     { t1 *= t1;
/* 131 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1); }
/*     */     
/* 133 */     double t2 = 0.5D - x2 * x2 - y2 * y2;
/* 134 */     if (t2 < 0.0D) { n2 = 0.0D; }
/*     */     else
/* 136 */     { t2 *= t2;
/* 137 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2); }
/*     */ 
/*     */ 
/*     */     
/* 141 */     return 70.0D * (n0 + n1 + n2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double noise(double xin, double yin, double zin) {
/*     */     double n0, n1, n2, n3;
/*     */     int i1, j1, k1, i2, j2, k2;
/* 149 */     double s = (xin + yin + zin) * 0.3333333333333333D;
/* 150 */     int i = fastfloor(xin + s);
/* 151 */     int j = fastfloor(yin + s);
/* 152 */     int k = fastfloor(zin + s);
/* 153 */     double t = (i + j + k) * 0.16666666666666666D;
/* 154 */     double X0 = i - t;
/* 155 */     double Y0 = j - t;
/* 156 */     double Z0 = k - t;
/* 157 */     double x0 = xin - X0;
/* 158 */     double y0 = yin - Y0;
/* 159 */     double z0 = zin - Z0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     if (x0 >= y0) {
/* 165 */       if (y0 >= z0) {
/* 166 */         i1 = 1;
/* 167 */         j1 = 0;
/* 168 */         k1 = 0;
/* 169 */         i2 = 1;
/* 170 */         j2 = 1;
/* 171 */         k2 = 0;
/*     */       }
/* 173 */       else if (x0 >= z0) {
/* 174 */         i1 = 1;
/* 175 */         j1 = 0;
/* 176 */         k1 = 0;
/* 177 */         i2 = 1;
/* 178 */         j2 = 0;
/* 179 */         k2 = 1;
/*     */       } else {
/*     */         
/* 182 */         i1 = 0;
/* 183 */         j1 = 0;
/* 184 */         k1 = 1;
/* 185 */         i2 = 1;
/* 186 */         j2 = 0;
/* 187 */         k2 = 1;
/*     */       }
/*     */     
/* 190 */     } else if (y0 < z0) {
/* 191 */       i1 = 0;
/* 192 */       j1 = 0;
/* 193 */       k1 = 1;
/* 194 */       i2 = 0;
/* 195 */       j2 = 1;
/* 196 */       k2 = 1;
/*     */     }
/* 198 */     else if (x0 < z0) {
/* 199 */       i1 = 0;
/* 200 */       j1 = 1;
/* 201 */       k1 = 0;
/* 202 */       i2 = 0;
/* 203 */       j2 = 1;
/* 204 */       k2 = 1;
/*     */     } else {
/*     */       
/* 207 */       i1 = 0;
/* 208 */       j1 = 1;
/* 209 */       k1 = 0;
/* 210 */       i2 = 1;
/* 211 */       j2 = 1;
/* 212 */       k2 = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     double x1 = x0 - i1 + 0.16666666666666666D;
/* 220 */     double y1 = y0 - j1 + 0.16666666666666666D;
/* 221 */     double z1 = z0 - k1 + 0.16666666666666666D;
/* 222 */     double x2 = x0 - i2 + 0.3333333333333333D;
/* 223 */     double y2 = y0 - j2 + 0.3333333333333333D;
/* 224 */     double z2 = z0 - k2 + 0.3333333333333333D;
/* 225 */     double x3 = x0 - 1.0D + 0.5D;
/* 226 */     double y3 = y0 - 1.0D + 0.5D;
/* 227 */     double z3 = z0 - 1.0D + 0.5D;
/*     */     
/* 229 */     int ii = i & 0xFF;
/* 230 */     int jj = j & 0xFF;
/* 231 */     int kk = k & 0xFF;
/* 232 */     int gi0 = permMod12[ii + perm[jj + perm[kk]]];
/* 233 */     int gi1 = permMod12[ii + i1 + perm[jj + j1 + perm[kk + k1]]];
/* 234 */     int gi2 = permMod12[ii + i2 + perm[jj + j2 + perm[kk + k2]]];
/* 235 */     int gi3 = permMod12[ii + 1 + perm[jj + 1 + perm[kk + 1]]];
/*     */     
/* 237 */     double t0 = 0.6D - x0 * x0 - y0 * y0 - z0 * z0;
/* 238 */     if (t0 < 0.0D) { n0 = 0.0D; }
/*     */     else
/* 240 */     { t0 *= t0;
/* 241 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0); }
/*     */     
/* 243 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1;
/* 244 */     if (t1 < 0.0D) { n1 = 0.0D; }
/*     */     else
/* 246 */     { t1 *= t1;
/* 247 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1); }
/*     */     
/* 249 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2;
/* 250 */     if (t2 < 0.0D) { n2 = 0.0D; }
/*     */     else
/* 252 */     { t2 *= t2;
/* 253 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2); }
/*     */     
/* 255 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3;
/* 256 */     if (t3 < 0.0D) { n3 = 0.0D; }
/*     */     else
/* 258 */     { t3 *= t3;
/* 259 */       n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3); }
/*     */ 
/*     */ 
/*     */     
/* 263 */     return 32.0D * (n0 + n1 + n2 + n3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double noise(double x, double y, double z, double w) {
/* 272 */     double n0, n1, n2, n3, n4, s = (x + y + z + w) * F4;
/* 273 */     int i = fastfloor(x + s);
/* 274 */     int j = fastfloor(y + s);
/* 275 */     int k = fastfloor(z + s);
/* 276 */     int l = fastfloor(w + s);
/* 277 */     double t = (i + j + k + l) * G4;
/* 278 */     double X0 = i - t;
/* 279 */     double Y0 = j - t;
/* 280 */     double Z0 = k - t;
/* 281 */     double W0 = l - t;
/* 282 */     double x0 = x - X0;
/* 283 */     double y0 = y - Y0;
/* 284 */     double z0 = z - Z0;
/* 285 */     double w0 = w - W0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     int rankx = 0;
/* 292 */     int ranky = 0;
/* 293 */     int rankz = 0;
/* 294 */     int rankw = 0;
/* 295 */     if (x0 > y0) { rankx++; }
/* 296 */     else { ranky++; }
/* 297 */      if (x0 > z0) { rankx++; }
/* 298 */     else { rankz++; }
/* 299 */      if (x0 > w0) { rankx++; }
/* 300 */     else { rankw++; }
/* 301 */      if (y0 > z0) { ranky++; }
/* 302 */     else { rankz++; }
/* 303 */      if (y0 > w0) { ranky++; }
/* 304 */     else { rankw++; }
/* 305 */      if (z0 > w0) { rankz++; }
/* 306 */     else { rankw++; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     int i1 = (rankx >= 3) ? 1 : 0;
/* 314 */     int j1 = (ranky >= 3) ? 1 : 0;
/* 315 */     int k1 = (rankz >= 3) ? 1 : 0;
/* 316 */     int l1 = (rankw >= 3) ? 1 : 0;
/*     */     
/* 318 */     int i2 = (rankx >= 2) ? 1 : 0;
/* 319 */     int j2 = (ranky >= 2) ? 1 : 0;
/* 320 */     int k2 = (rankz >= 2) ? 1 : 0;
/* 321 */     int l2 = (rankw >= 2) ? 1 : 0;
/*     */     
/* 323 */     int i3 = (rankx >= 1) ? 1 : 0;
/* 324 */     int j3 = (ranky >= 1) ? 1 : 0;
/* 325 */     int k3 = (rankz >= 1) ? 1 : 0;
/* 326 */     int l3 = (rankw >= 1) ? 1 : 0;
/*     */     
/* 328 */     double x1 = x0 - i1 + G4;
/* 329 */     double y1 = y0 - j1 + G4;
/* 330 */     double z1 = z0 - k1 + G4;
/* 331 */     double w1 = w0 - l1 + G4;
/* 332 */     double x2 = x0 - i2 + 2.0D * G4;
/* 333 */     double y2 = y0 - j2 + 2.0D * G4;
/* 334 */     double z2 = z0 - k2 + 2.0D * G4;
/* 335 */     double w2 = w0 - l2 + 2.0D * G4;
/* 336 */     double x3 = x0 - i3 + 3.0D * G4;
/* 337 */     double y3 = y0 - j3 + 3.0D * G4;
/* 338 */     double z3 = z0 - k3 + 3.0D * G4;
/* 339 */     double w3 = w0 - l3 + 3.0D * G4;
/* 340 */     double x4 = x0 - 1.0D + 4.0D * G4;
/* 341 */     double y4 = y0 - 1.0D + 4.0D * G4;
/* 342 */     double z4 = z0 - 1.0D + 4.0D * G4;
/* 343 */     double w4 = w0 - 1.0D + 4.0D * G4;
/*     */     
/* 345 */     int ii = i & 0xFF;
/* 346 */     int jj = j & 0xFF;
/* 347 */     int kk = k & 0xFF;
/* 348 */     int ll = l & 0xFF;
/* 349 */     int gi0 = perm[ii + perm[jj + perm[kk + perm[ll]]]] % 32;
/* 350 */     int gi1 = perm[ii + i1 + perm[jj + j1 + perm[kk + k1 + perm[ll + l1]]]] % 32;
/* 351 */     int gi2 = perm[ii + i2 + perm[jj + j2 + perm[kk + k2 + perm[ll + l2]]]] % 32;
/* 352 */     int gi3 = perm[ii + i3 + perm[jj + j3 + perm[kk + k3 + perm[ll + l3]]]] % 32;
/* 353 */     int gi4 = perm[ii + 1 + perm[jj + 1 + perm[kk + 1 + perm[ll + 1]]]] % 32;
/*     */     
/* 355 */     double t0 = 0.5D - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
/* 356 */     if (t0 < 0.0D) { n0 = 0.0D; }
/*     */     else
/* 358 */     { t0 *= t0;
/* 359 */       n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0); }
/*     */     
/* 361 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
/* 362 */     if (t1 < 0.0D) { n1 = 0.0D; }
/*     */     else
/* 364 */     { t1 *= t1;
/* 365 */       n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1); }
/*     */     
/* 367 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
/* 368 */     if (t2 < 0.0D) { n2 = 0.0D; }
/*     */     else
/* 370 */     { t2 *= t2;
/* 371 */       n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2); }
/*     */     
/* 373 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
/* 374 */     if (t3 < 0.0D) { n3 = 0.0D; }
/*     */     else
/* 376 */     { t3 *= t3;
/* 377 */       n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3); }
/*     */     
/* 379 */     double t4 = 0.6D - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
/* 380 */     if (t4 < 0.0D) { n4 = 0.0D; }
/*     */     else
/* 382 */     { t4 *= t4;
/* 383 */       n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4); }
/*     */ 
/*     */     
/* 386 */     return 27.0D * (n0 + n1 + n2 + n3 + n4);
/*     */   }
/*     */   
/*     */   private static class Grad { double x;
/*     */     double y;
/*     */     double z;
/*     */     double w;
/*     */     
/*     */     Grad(double x, double y, double z) {
/* 395 */       this.x = x;
/* 396 */       this.y = y;
/* 397 */       this.z = z;
/*     */     }
/*     */     
/*     */     Grad(double x, double y, double z, double w) {
/* 401 */       this.x = x;
/* 402 */       this.y = y;
/* 403 */       this.z = z;
/* 404 */       this.w = w;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\fields\noise\Simplex.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */