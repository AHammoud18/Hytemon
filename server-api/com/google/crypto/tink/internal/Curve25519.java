/*     */ package com.google.crypto.tink.internal;
/*     */ 
/*     */ import com.google.crypto.tink.annotations.Alpha;
/*     */ import com.google.crypto.tink.subtle.Bytes;
/*     */ import com.google.crypto.tink.subtle.Hex;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Alpha
/*     */ public final class Curve25519
/*     */ {
/*  43 */   static final byte[][] BANNED_PUBLIC_KEYS = new byte[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { -32, -21, 122, 124, 59, 65, -72, -82, 22, 86, -29, -6, -15, -97, -60, 106, -38, 9, -115, -21, -100, 50, -79, -3, -122, 98, 5, 22, 95, 73, -72, 0 }, { 95, -100, -107, -68, -93, 80, -116, 36, -79, -48, -79, 85, -100, -125, -17, 91, 4, 68, 92, -60, 88, 28, -114, -122, -40, 34, 78, -35, -48, -97, 17, 87 }, { -20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Byte.MAX_VALUE }, { -19, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Byte.MAX_VALUE }, { -18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, Byte.MAX_VALUE } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void monty(long[] x2, long[] z2, long[] x3, long[] z3, long[] x, long[] z, long[] xprime, long[] zprime, long[] qmqp) {
/* 148 */     long[] origx = Arrays.copyOf(x, 10);
/* 149 */     long[] zzz = new long[19];
/* 150 */     long[] xx = new long[19];
/* 151 */     long[] zz = new long[19];
/* 152 */     long[] xxprime = new long[19];
/* 153 */     long[] zzprime = new long[19];
/* 154 */     long[] zzzprime = new long[19];
/* 155 */     long[] xxxprime = new long[19];
/*     */     
/* 157 */     Field25519.sum(x, z);
/*     */     
/* 159 */     Field25519.sub(z, origx);
/*     */ 
/*     */     
/* 162 */     long[] origxprime = Arrays.copyOf(xprime, 10);
/* 163 */     Field25519.sum(xprime, zprime);
/*     */     
/* 165 */     Field25519.sub(zprime, origxprime);
/*     */     
/* 167 */     Field25519.product(xxprime, xprime, z);
/*     */ 
/*     */ 
/*     */     
/* 171 */     Field25519.product(zzprime, x, zprime);
/*     */     
/* 173 */     Field25519.reduceSizeByModularReduction(xxprime);
/* 174 */     Field25519.reduceCoefficients(xxprime);
/*     */     
/* 176 */     Field25519.reduceSizeByModularReduction(zzprime);
/* 177 */     Field25519.reduceCoefficients(zzprime);
/*     */     
/* 179 */     System.arraycopy(xxprime, 0, origxprime, 0, 10);
/* 180 */     Field25519.sum(xxprime, zzprime);
/*     */     
/* 182 */     Field25519.sub(zzprime, origxprime);
/*     */     
/* 184 */     Field25519.square(xxxprime, xxprime);
/*     */     
/* 186 */     Field25519.square(zzzprime, zzprime);
/*     */     
/* 188 */     Field25519.product(zzprime, zzzprime, qmqp);
/*     */     
/* 190 */     Field25519.reduceSizeByModularReduction(zzprime);
/* 191 */     Field25519.reduceCoefficients(zzprime);
/*     */     
/* 193 */     System.arraycopy(xxxprime, 0, x3, 0, 10);
/* 194 */     System.arraycopy(zzprime, 0, z3, 0, 10);
/*     */     
/* 196 */     Field25519.square(xx, x);
/*     */     
/* 198 */     Field25519.square(zz, z);
/*     */     
/* 200 */     Field25519.product(x2, xx, zz);
/*     */     
/* 202 */     Field25519.reduceSizeByModularReduction(x2);
/* 203 */     Field25519.reduceCoefficients(x2);
/*     */     
/* 205 */     Field25519.sub(zz, xx);
/*     */     
/* 207 */     Arrays.fill(zzz, 10, zzz.length - 1, 0L);
/* 208 */     Field25519.scalarProduct(zzz, zz, 121665L);
/*     */ 
/*     */ 
/*     */     
/* 212 */     Field25519.reduceCoefficients(zzz);
/*     */     
/* 214 */     Field25519.sum(zzz, xx);
/*     */     
/* 216 */     Field25519.product(z2, zz, zzz);
/*     */     
/* 218 */     Field25519.reduceSizeByModularReduction(z2);
/* 219 */     Field25519.reduceCoefficients(z2);
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
/*     */   static void swapConditional(long[] a, long[] b, int iswap) {
/* 233 */     int swap = -iswap;
/* 234 */     for (int i = 0; i < 10; i++) {
/* 235 */       int x = swap & ((int)a[i] ^ (int)b[i]);
/* 236 */       a[i] = ((int)a[i] ^ x);
/* 237 */       b[i] = ((int)b[i] ^ x);
/*     */     } 
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
/*     */   static void copyConditional(long[] a, long[] b, int icopy) {
/* 252 */     int copy = -icopy;
/* 253 */     for (int i = 0; i < 10; i++) {
/* 254 */       int x = copy & ((int)a[i] ^ (int)b[i]);
/* 255 */       a[i] = ((int)a[i] ^ x);
/*     */     } 
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
/*     */   public static void curveMult(long[] resultx, byte[] n, byte[] qBytes) throws InvalidKeyException {
/* 270 */     byte[] qBytesWithoutMsb = validatePubKeyAndClearMsb(qBytes);
/*     */     
/* 272 */     long[] q = Field25519.expand(qBytesWithoutMsb);
/* 273 */     long[] nqpqx = new long[19];
/* 274 */     long[] nqpqz = new long[19];
/* 275 */     nqpqz[0] = 1L;
/* 276 */     long[] nqx = new long[19];
/* 277 */     nqx[0] = 1L;
/* 278 */     long[] nqz = new long[19];
/* 279 */     long[] nqpqx2 = new long[19];
/* 280 */     long[] nqpqz2 = new long[19];
/* 281 */     nqpqz2[0] = 1L;
/* 282 */     long[] nqx2 = new long[19];
/* 283 */     long[] nqz2 = new long[19];
/* 284 */     nqz2[0] = 1L;
/* 285 */     long[] t = new long[19];
/*     */     
/* 287 */     System.arraycopy(q, 0, nqpqx, 0, 10);
/*     */     
/* 289 */     for (int i = 0; i < 32; i++) {
/* 290 */       int b = n[32 - i - 1] & 0xFF;
/* 291 */       for (int j = 0; j < 8; j++) {
/* 292 */         int bit = b >> 7 - j & 0x1;
/*     */         
/* 294 */         swapConditional(nqx, nqpqx, bit);
/* 295 */         swapConditional(nqz, nqpqz, bit);
/* 296 */         monty(nqx2, nqz2, nqpqx2, nqpqz2, nqx, nqz, nqpqx, nqpqz, q);
/* 297 */         swapConditional(nqx2, nqpqx2, bit);
/* 298 */         swapConditional(nqz2, nqpqz2, bit);
/*     */         
/* 300 */         t = nqx;
/* 301 */         nqx = nqx2;
/* 302 */         nqx2 = t;
/* 303 */         t = nqz;
/* 304 */         nqz = nqz2;
/* 305 */         nqz2 = t;
/* 306 */         t = nqpqx;
/* 307 */         nqpqx = nqpqx2;
/* 308 */         nqpqx2 = t;
/* 309 */         t = nqpqz;
/* 310 */         nqpqz = nqpqz2;
/* 311 */         nqpqz2 = t;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 316 */     long[] zmone = new long[10];
/* 317 */     Field25519.inverse(zmone, nqz);
/* 318 */     Field25519.mult(resultx, nqx, zmone);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (!isCollinear(q, resultx, nqpqx, nqpqz)) {
/* 332 */       throw new IllegalStateException("Arithmetic error in curve multiplication with the public key: " + 
/* 333 */           Hex.encode(qBytes));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] validatePubKeyAndClearMsb(byte[] pubKey) throws InvalidKeyException {
/* 344 */     if (pubKey.length != 32) {
/* 345 */       throw new InvalidKeyException("Public key length is not 32-byte");
/*     */     }
/*     */     
/* 348 */     byte[] pubKeyWithoutMsb = Arrays.copyOf(pubKey, pubKey.length);
/* 349 */     pubKeyWithoutMsb[31] = (byte)(pubKeyWithoutMsb[31] & Byte.MAX_VALUE);
/*     */     
/* 351 */     for (int i = 0; i < BANNED_PUBLIC_KEYS.length; i++) {
/* 352 */       if (Bytes.equal(BANNED_PUBLIC_KEYS[i], pubKeyWithoutMsb)) {
/* 353 */         throw new InvalidKeyException("Banned public key: " + Hex.encode(BANNED_PUBLIC_KEYS[i]));
/*     */       }
/*     */     } 
/* 356 */     return pubKeyWithoutMsb;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isCollinear(long[] x1, long[] x2, long[] x3, long[] z3) {
/* 398 */     long[] x1multx2 = new long[10];
/* 399 */     long[] x1addx2 = new long[10];
/* 400 */     long[] lhs = new long[11];
/* 401 */     long[] t = new long[11];
/* 402 */     long[] t2 = new long[11];
/* 403 */     Field25519.mult(x1multx2, x1, x2);
/* 404 */     Field25519.sum(x1addx2, x1, x2);
/* 405 */     long[] a = new long[10];
/* 406 */     a[0] = 486662L;
/*     */     
/* 408 */     Field25519.sum(t, x1addx2, a);
/*     */     
/* 410 */     Field25519.mult(t, t, z3);
/*     */     
/* 412 */     Field25519.sum(t, x3);
/*     */     
/* 414 */     Field25519.mult(t, t, x1multx2);
/*     */     
/* 416 */     Field25519.mult(t, t, x3);
/* 417 */     Field25519.scalarProduct(lhs, t, 4L);
/* 418 */     Field25519.reduceCoefficients(lhs);
/*     */ 
/*     */     
/* 421 */     Field25519.mult(t, x1multx2, z3);
/*     */     
/* 423 */     Field25519.sub(t, t, z3);
/*     */     
/* 425 */     Field25519.mult(t2, x1addx2, x3);
/*     */     
/* 427 */     Field25519.sum(t, t, t2);
/*     */     
/* 429 */     Field25519.square(t, t);
/* 430 */     return Bytes.equal(Field25519.contract(lhs), Field25519.contract(t));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\internal\Curve25519.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */