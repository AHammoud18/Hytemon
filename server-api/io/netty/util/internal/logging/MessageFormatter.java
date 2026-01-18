/*     */ package io.netty.util.internal.logging;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MessageFormatter
/*     */ {
/*     */   private static final String DELIM_STR = "{}";
/*     */   private static final char ESCAPE_CHAR = '\\';
/*     */   
/*     */   public static FormattingTuple format(String messagePattern, Object arg) {
/* 133 */     return arrayFormat(messagePattern, new Object[] { arg });
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
/*     */   public static FormattingTuple format(String messagePattern, Object argA, Object argB) {
/* 157 */     return arrayFormat(messagePattern, new Object[] { argA, argB });
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
/*     */   public static FormattingTuple arrayFormat(String messagePattern, Object[] argArray) {
/* 172 */     if (argArray == null || argArray.length == 0) {
/* 173 */       return new FormattingTuple(messagePattern, null);
/*     */     }
/*     */     
/* 176 */     int lastArrIdx = argArray.length - 1;
/* 177 */     Object lastEntry = argArray[lastArrIdx];
/* 178 */     Throwable throwable = (lastEntry instanceof Throwable) ? (Throwable)lastEntry : null;
/*     */     
/* 180 */     if (messagePattern == null) {
/* 181 */       return new FormattingTuple(null, throwable);
/*     */     }
/*     */     
/* 184 */     int j = messagePattern.indexOf("{}");
/* 185 */     if (j == -1)
/*     */     {
/* 187 */       return new FormattingTuple(messagePattern, throwable);
/*     */     }
/*     */     
/* 190 */     StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
/* 191 */     int i = 0;
/* 192 */     int L = 0;
/*     */     do {
/* 194 */       boolean notEscaped = (j == 0 || messagePattern.charAt(j - 1) != '\\');
/* 195 */       if (notEscaped) {
/*     */         
/* 197 */         sbuf.append(messagePattern, i, j);
/*     */       } else {
/* 199 */         sbuf.append(messagePattern, i, j - 1);
/*     */         
/* 201 */         notEscaped = (j >= 2 && messagePattern.charAt(j - 2) == '\\');
/*     */       } 
/*     */       
/* 204 */       i = j + 2;
/* 205 */       if (notEscaped) {
/* 206 */         deeplyAppendParameter(sbuf, argArray[L], null);
/* 207 */         L++;
/* 208 */         if (L > lastArrIdx) {
/*     */           break;
/*     */         }
/*     */       } else {
/* 212 */         sbuf.append("{}");
/*     */       } 
/* 214 */       j = messagePattern.indexOf("{}", i);
/* 215 */     } while (j != -1);
/*     */ 
/*     */     
/* 218 */     sbuf.append(messagePattern, i, messagePattern.length());
/* 219 */     return new FormattingTuple(sbuf.toString(), (L <= lastArrIdx) ? throwable : null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Set<Object[]> seenSet) {
/* 225 */     if (o == null) {
/* 226 */       sbuf.append("null");
/*     */       return;
/*     */     } 
/* 229 */     Class<?> objClass = o.getClass();
/* 230 */     if (!objClass.isArray()) {
/* 231 */       if (Number.class.isAssignableFrom(objClass)) {
/*     */         
/* 233 */         if (objClass == Long.class) {
/* 234 */           sbuf.append(((Long)o).longValue());
/* 235 */         } else if (objClass == Integer.class || objClass == Short.class || objClass == Byte.class) {
/* 236 */           sbuf.append(((Number)o).intValue());
/* 237 */         } else if (objClass == Double.class) {
/* 238 */           sbuf.append(((Double)o).doubleValue());
/* 239 */         } else if (objClass == Float.class) {
/* 240 */           sbuf.append(((Float)o).floatValue());
/*     */         } else {
/* 242 */           safeObjectAppend(sbuf, o);
/*     */         } 
/*     */       } else {
/* 245 */         safeObjectAppend(sbuf, o);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 250 */       sbuf.append('[');
/* 251 */       if (objClass == boolean[].class) {
/* 252 */         booleanArrayAppend(sbuf, (boolean[])o);
/* 253 */       } else if (objClass == byte[].class) {
/* 254 */         byteArrayAppend(sbuf, (byte[])o);
/* 255 */       } else if (objClass == char[].class) {
/* 256 */         charArrayAppend(sbuf, (char[])o);
/* 257 */       } else if (objClass == short[].class) {
/* 258 */         shortArrayAppend(sbuf, (short[])o);
/* 259 */       } else if (objClass == int[].class) {
/* 260 */         intArrayAppend(sbuf, (int[])o);
/* 261 */       } else if (objClass == long[].class) {
/* 262 */         longArrayAppend(sbuf, (long[])o);
/* 263 */       } else if (objClass == float[].class) {
/* 264 */         floatArrayAppend(sbuf, (float[])o);
/* 265 */       } else if (objClass == double[].class) {
/* 266 */         doubleArrayAppend(sbuf, (double[])o);
/*     */       } else {
/* 268 */         objectArrayAppend(sbuf, (Object[])o, seenSet);
/*     */       } 
/* 270 */       sbuf.append(']');
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void safeObjectAppend(StringBuilder sbuf, Object o) {
/*     */     try {
/* 276 */       String oAsString = o.toString();
/* 277 */       sbuf.append(oAsString);
/* 278 */     } catch (Throwable t) {
/* 279 */       System.err
/* 280 */         .println("SLF4J: Failed toString() invocation on an object of type [" + o
/* 281 */           .getClass().getName() + ']');
/* 282 */       t.printStackTrace();
/* 283 */       sbuf.append("[FAILED toString()]");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Set<Object[]> seenSet) {
/* 288 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 291 */     if (seenSet == null) {
/* 292 */       seenSet = new HashSet(a.length);
/*     */     }
/* 294 */     if (seenSet.add(a)) {
/* 295 */       deeplyAppendParameter(sbuf, a[0], seenSet);
/* 296 */       for (int i = 1; i < a.length; i++) {
/* 297 */         sbuf.append(", ");
/* 298 */         deeplyAppendParameter(sbuf, a[i], seenSet);
/*     */       } 
/*     */       
/* 301 */       seenSet.remove(a);
/*     */     } else {
/* 303 */       sbuf.append("...");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
/* 308 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 311 */     sbuf.append(a[0]);
/* 312 */     for (int i = 1; i < a.length; i++) {
/* 313 */       sbuf.append(", ");
/* 314 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
/* 319 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 322 */     sbuf.append(a[0]);
/* 323 */     for (int i = 1; i < a.length; i++) {
/* 324 */       sbuf.append(", ");
/* 325 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void charArrayAppend(StringBuilder sbuf, char[] a) {
/* 330 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 333 */     sbuf.append(a[0]);
/* 334 */     for (int i = 1; i < a.length; i++) {
/* 335 */       sbuf.append(", ");
/* 336 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
/* 341 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 344 */     sbuf.append(a[0]);
/* 345 */     for (int i = 1; i < a.length; i++) {
/* 346 */       sbuf.append(", ");
/* 347 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void intArrayAppend(StringBuilder sbuf, int[] a) {
/* 352 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 355 */     sbuf.append(a[0]);
/* 356 */     for (int i = 1; i < a.length; i++) {
/* 357 */       sbuf.append(", ");
/* 358 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void longArrayAppend(StringBuilder sbuf, long[] a) {
/* 363 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 366 */     sbuf.append(a[0]);
/* 367 */     for (int i = 1; i < a.length; i++) {
/* 368 */       sbuf.append(", ");
/* 369 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
/* 374 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 377 */     sbuf.append(a[0]);
/* 378 */     for (int i = 1; i < a.length; i++) {
/* 379 */       sbuf.append(", ");
/* 380 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
/* 385 */     if (a.length == 0) {
/*     */       return;
/*     */     }
/* 388 */     sbuf.append(a[0]);
/* 389 */     for (int i = 1; i < a.length; i++) {
/* 390 */       sbuf.append(", ");
/* 391 */       sbuf.append(a[i]);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\internal\logging\MessageFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */