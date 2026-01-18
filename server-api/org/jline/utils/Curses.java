/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.Flushable;
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.util.ArrayDeque;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Curses
/*     */ {
/*  51 */   private static final Object[] sv = new Object[26];
/*  52 */   private static final Object[] dv = new Object[26];
/*     */ 
/*     */   
/*     */   private static final int IFTE_NONE = 0;
/*     */ 
/*     */   
/*     */   private static final int IFTE_IF = 1;
/*     */ 
/*     */   
/*     */   private static final int IFTE_THEN = 2;
/*     */ 
/*     */   
/*     */   private static final int IFTE_ELSE = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   public static String tputs(String cap, Object... params) {
/*  69 */     if (cap != null) {
/*  70 */       StringWriter sw = new StringWriter();
/*  71 */       tputs(sw, cap, params);
/*  72 */       return sw.toString();
/*     */     } 
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tputs(Appendable out, String str, Object... params) {
/*     */     try {
/*  86 */       doTputs(out, str, params);
/*  87 */     } catch (Exception e) {
/*  88 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void doTputs(Appendable out, String str, Object... params) throws IOException {
/*  93 */     int index = 0;
/*  94 */     int length = str.length();
/*  95 */     int ifte = 0;
/*  96 */     boolean exec = true;
/*  97 */     ArrayDeque<Object> stack = new ArrayDeque();
/*  98 */     while (index < length) {
/*  99 */       int start; boolean alternate, left, space, plus; int width, prec, cnv; char ch = str.charAt(index++);
/* 100 */       switch (ch) {
/*     */         case '\\':
/* 102 */           ch = str.charAt(index++);
/* 103 */           if (ch >= '0' && ch <= '7') {
/* 104 */             int val = ch - 48;
/* 105 */             for (int i = 0; i < 2; i++) {
/* 106 */               ch = str.charAt(index++);
/* 107 */               if (ch < '0' || ch > '7') {
/* 108 */                 throw new IllegalStateException();
/*     */               }
/* 110 */               val = val * 8 + ch - 48;
/*     */             } 
/* 112 */             out.append((char)val); continue;
/*     */           } 
/* 114 */           switch (ch) {
/*     */             case 'E':
/*     */             case 'e':
/* 117 */               if (exec) {
/* 118 */                 out.append('\033');
/*     */               }
/*     */               continue;
/*     */             case 'n':
/* 122 */               out.append('\n');
/*     */               continue;
/*     */ 
/*     */ 
/*     */             
/*     */             case 'r':
/* 128 */               if (exec) {
/* 129 */                 out.append('\r');
/*     */               }
/*     */               continue;
/*     */             case 't':
/* 133 */               if (exec) {
/* 134 */                 out.append('\t');
/*     */               }
/*     */               continue;
/*     */             case 'b':
/* 138 */               if (exec) {
/* 139 */                 out.append('\b');
/*     */               }
/*     */               continue;
/*     */             case 'f':
/* 143 */               if (exec) {
/* 144 */                 out.append('\f');
/*     */               }
/*     */               continue;
/*     */             case 's':
/* 148 */               if (exec) {
/* 149 */                 out.append(' ');
/*     */               }
/*     */               continue;
/*     */             case ':':
/*     */             case '\\':
/*     */             case '^':
/* 155 */               if (exec) {
/* 156 */                 out.append(ch);
/*     */               }
/*     */               continue;
/*     */           } 
/* 160 */           throw new IllegalArgumentException();
/*     */ 
/*     */ 
/*     */         
/*     */         case '^':
/* 165 */           ch = str.charAt(index++);
/* 166 */           if (exec) {
/* 167 */             out.append((char)(ch - 64));
/*     */           }
/*     */           continue;
/*     */         case '%':
/* 171 */           ch = str.charAt(index++);
/* 172 */           switch (ch) {
/*     */             case '%':
/* 174 */               if (exec) {
/* 175 */                 out.append('%');
/*     */               }
/*     */               continue;
/*     */             case 'p':
/* 179 */               ch = str.charAt(index++);
/* 180 */               if (exec) {
/* 181 */                 stack.push(params[ch - 49]);
/*     */               }
/*     */               continue;
/*     */             case 'P':
/* 185 */               ch = str.charAt(index++);
/* 186 */               if (ch >= 'a' && ch <= 'z') {
/* 187 */                 if (exec)
/* 188 */                   dv[ch - 97] = stack.pop();  continue;
/*     */               } 
/* 190 */               if (ch >= 'A' && ch <= 'Z') {
/* 191 */                 if (exec)
/* 192 */                   sv[ch - 65] = stack.pop(); 
/*     */                 continue;
/*     */               } 
/* 195 */               throw new IllegalArgumentException();
/*     */ 
/*     */             
/*     */             case 'g':
/* 199 */               ch = str.charAt(index++);
/* 200 */               if (ch >= 'a' && ch <= 'z') {
/* 201 */                 if (exec)
/* 202 */                   stack.push(dv[ch - 97]);  continue;
/*     */               } 
/* 204 */               if (ch >= 'A' && ch <= 'Z') {
/* 205 */                 if (exec)
/* 206 */                   stack.push(sv[ch - 65]); 
/*     */                 continue;
/*     */               } 
/* 209 */               throw new IllegalArgumentException();
/*     */ 
/*     */             
/*     */             case '\'':
/* 213 */               ch = str.charAt(index++);
/* 214 */               if (exec) {
/* 215 */                 stack.push(Integer.valueOf(ch));
/*     */               }
/* 217 */               ch = str.charAt(index++);
/* 218 */               if (ch != '\'') {
/* 219 */                 throw new IllegalArgumentException();
/*     */               }
/*     */               continue;
/*     */             case '{':
/* 223 */               start = index;
/* 224 */               while (str.charAt(index++) != '}');
/*     */               
/* 226 */               if (exec) {
/* 227 */                 int v = Integer.parseInt(str.substring(start, index - 1));
/* 228 */                 stack.push(Integer.valueOf(v));
/*     */               } 
/*     */               continue;
/*     */             case 'l':
/* 232 */               if (exec) {
/* 233 */                 stack.push(Integer.valueOf(stack.pop().toString().length()));
/*     */               }
/*     */               continue;
/*     */             case '+':
/* 237 */               if (exec) {
/* 238 */                 int v2 = toInteger(stack.pop());
/* 239 */                 int v1 = toInteger(stack.pop());
/* 240 */                 stack.push(Integer.valueOf(v1 + v2));
/*     */               } 
/*     */               continue;
/*     */             case '-':
/* 244 */               if (exec) {
/* 245 */                 int v2 = toInteger(stack.pop());
/* 246 */                 int v1 = toInteger(stack.pop());
/* 247 */                 stack.push(Integer.valueOf(v1 - v2));
/*     */               } 
/*     */               continue;
/*     */             case '*':
/* 251 */               if (exec) {
/* 252 */                 int v2 = toInteger(stack.pop());
/* 253 */                 int v1 = toInteger(stack.pop());
/* 254 */                 stack.push(Integer.valueOf(v1 * v2));
/*     */               } 
/*     */               continue;
/*     */             case '/':
/* 258 */               if (exec) {
/* 259 */                 int v2 = toInteger(stack.pop());
/* 260 */                 int v1 = toInteger(stack.pop());
/* 261 */                 stack.push(Integer.valueOf(v1 / v2));
/*     */               } 
/*     */               continue;
/*     */             case 'm':
/* 265 */               if (exec) {
/* 266 */                 int v2 = toInteger(stack.pop());
/* 267 */                 int v1 = toInteger(stack.pop());
/* 268 */                 stack.push(Integer.valueOf(v1 % v2));
/*     */               } 
/*     */               continue;
/*     */             case '&':
/* 272 */               if (exec) {
/* 273 */                 int v2 = toInteger(stack.pop());
/* 274 */                 int v1 = toInteger(stack.pop());
/* 275 */                 stack.push(Integer.valueOf(v1 & v2));
/*     */               } 
/*     */               continue;
/*     */             case '|':
/* 279 */               if (exec) {
/* 280 */                 int v2 = toInteger(stack.pop());
/* 281 */                 int v1 = toInteger(stack.pop());
/* 282 */                 stack.push(Integer.valueOf(v1 | v2));
/*     */               } 
/*     */               continue;
/*     */             case '^':
/* 286 */               if (exec) {
/* 287 */                 int v2 = toInteger(stack.pop());
/* 288 */                 int v1 = toInteger(stack.pop());
/* 289 */                 stack.push(Integer.valueOf(v1 ^ v2));
/*     */               } 
/*     */               continue;
/*     */             case '=':
/* 293 */               if (exec) {
/* 294 */                 int v2 = toInteger(stack.pop());
/* 295 */                 int v1 = toInteger(stack.pop());
/* 296 */                 stack.push(Boolean.valueOf((v1 == v2)));
/*     */               } 
/*     */               continue;
/*     */             case '>':
/* 300 */               if (exec) {
/* 301 */                 int v2 = toInteger(stack.pop());
/* 302 */                 int v1 = toInteger(stack.pop());
/* 303 */                 stack.push(Boolean.valueOf((v1 > v2)));
/*     */               } 
/*     */               continue;
/*     */             case '<':
/* 307 */               if (exec) {
/* 308 */                 int v2 = toInteger(stack.pop());
/* 309 */                 int v1 = toInteger(stack.pop());
/* 310 */                 stack.push(Boolean.valueOf((v1 < v2)));
/*     */               } 
/*     */               continue;
/*     */             case 'A':
/* 314 */               if (exec) {
/* 315 */                 int v2 = toInteger(stack.pop());
/* 316 */                 int v1 = toInteger(stack.pop());
/* 317 */                 stack.push(Boolean.valueOf((v1 != 0 && v2 != 0)));
/*     */               } 
/*     */               continue;
/*     */             case '!':
/* 321 */               if (exec) {
/* 322 */                 int v1 = toInteger(stack.pop());
/* 323 */                 stack.push(Boolean.valueOf((v1 == 0)));
/*     */               } 
/*     */               continue;
/*     */             case '~':
/* 327 */               if (exec) {
/* 328 */                 int v1 = toInteger(stack.pop());
/* 329 */                 stack.push(Integer.valueOf(v1 ^ 0xFFFFFFFF));
/*     */               } 
/*     */               continue;
/*     */             case 'O':
/* 333 */               if (exec) {
/* 334 */                 int v2 = toInteger(stack.pop());
/* 335 */                 int v1 = toInteger(stack.pop());
/* 336 */                 stack.push(Boolean.valueOf((v1 != 0 || v2 != 0)));
/*     */               } 
/*     */               continue;
/*     */             case '?':
/* 340 */               if (ifte != 0) {
/* 341 */                 throw new IllegalArgumentException();
/*     */               }
/* 343 */               ifte = 1;
/*     */               continue;
/*     */             
/*     */             case 't':
/* 347 */               if (ifte != 1 && ifte != 3) {
/* 348 */                 throw new IllegalArgumentException();
/*     */               }
/* 350 */               ifte = 2;
/*     */               
/* 352 */               exec = (toInteger(stack.pop()) != 0);
/*     */               continue;
/*     */             case 'e':
/* 355 */               if (ifte != 2) {
/* 356 */                 throw new IllegalArgumentException();
/*     */               }
/* 358 */               ifte = 3;
/*     */               
/* 360 */               exec = !exec;
/*     */               continue;
/*     */             case ';':
/* 363 */               if (ifte == 0 || ifte == 1) {
/* 364 */                 throw new IllegalArgumentException();
/*     */               }
/* 366 */               ifte = 0;
/*     */               
/* 368 */               exec = true;
/*     */               continue;
/*     */             case 'i':
/* 371 */               if (params.length >= 1) {
/* 372 */                 params[0] = Integer.valueOf(toInteger(params[0]) + 1);
/*     */               }
/* 374 */               if (params.length >= 2) {
/* 375 */                 params[1] = Integer.valueOf(toInteger(params[1]) + 1);
/*     */               }
/*     */               continue;
/*     */             case 'd':
/* 379 */               out.append(Integer.toString(toInteger(stack.pop())));
/*     */               continue;
/*     */           } 
/* 382 */           if (ch == ':') {
/* 383 */             ch = str.charAt(index++);
/*     */           }
/* 385 */           alternate = false;
/* 386 */           left = false;
/* 387 */           space = false;
/* 388 */           plus = false;
/* 389 */           width = 0;
/* 390 */           prec = -1;
/*     */           
/* 392 */           while ("-+# ".indexOf(ch) >= 0) {
/* 393 */             switch (ch) {
/*     */               case '-':
/* 395 */                 left = true;
/*     */                 break;
/*     */               case '+':
/* 398 */                 plus = true;
/*     */                 break;
/*     */               case '#':
/* 401 */                 alternate = true;
/*     */                 break;
/*     */               case ' ':
/* 404 */                 space = true;
/*     */                 break;
/*     */             } 
/* 407 */             ch = str.charAt(index++);
/*     */           } 
/* 409 */           if ("123456789".indexOf(ch) >= 0) {
/*     */             do {
/* 411 */               width = width * 10 + ch - 48;
/* 412 */               ch = str.charAt(index++);
/* 413 */             } while ("0123456789".indexOf(ch) >= 0);
/*     */           }
/* 415 */           if (ch == '.') {
/* 416 */             prec = 0;
/* 417 */             ch = str.charAt(index++);
/*     */           } 
/* 419 */           if ("0123456789".indexOf(ch) >= 0) {
/*     */             do {
/* 421 */               prec = prec * 10 + ch - 48;
/* 422 */               ch = str.charAt(index++);
/* 423 */             } while ("0123456789".indexOf(ch) >= 0);
/*     */           }
/* 425 */           if ("cdoxXs".indexOf(ch) < 0) {
/* 426 */             throw new IllegalArgumentException();
/*     */           }
/* 428 */           cnv = ch;
/* 429 */           if (exec) {
/*     */             String res;
/* 431 */             if (cnv == 115) {
/* 432 */               res = (String)stack.pop();
/* 433 */               if (prec >= 0) {
/* 434 */                 res = res.substring(0, prec);
/*     */               }
/*     */             } else {
/* 437 */               int p = toInteger(stack.pop());
/* 438 */               StringBuilder fmt = new StringBuilder(16);
/* 439 */               fmt.append('%');
/* 440 */               if (alternate) {
/* 441 */                 fmt.append('#');
/*     */               }
/* 443 */               if (plus) {
/* 444 */                 fmt.append('+');
/*     */               }
/* 446 */               if (space) {
/* 447 */                 fmt.append(' ');
/*     */               }
/* 449 */               if (prec >= 0) {
/* 450 */                 fmt.append('0');
/* 451 */                 fmt.append(prec);
/*     */               } 
/* 453 */               fmt.append((char)cnv);
/* 454 */               res = String.format(fmt.toString(), new Object[] { Integer.valueOf(p) });
/*     */             } 
/* 456 */             if (width > res.length()) {
/* 457 */               res = String.format("%" + (left ? "-" : "") + width + "s", new Object[] { res });
/*     */             }
/* 459 */             out.append(res);
/*     */           } 
/*     */           continue;
/*     */ 
/*     */         
/*     */         case '$':
/* 465 */           if (index < length && str.charAt(index) == '<') {
/*     */             
/* 467 */             int nb = 0;
/* 468 */             while ((ch = str.charAt(++index)) != '>') {
/* 469 */               if (ch >= '0' && ch <= '9') {
/* 470 */                 nb = nb * 10 + ch - 48; continue;
/* 471 */               }  if (ch == '*')
/*     */                 continue; 
/* 473 */               if (ch == '/');
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 479 */             index++;
/*     */             try {
/* 481 */               if (out instanceof Flushable) {
/* 482 */                 ((Flushable)out).flush();
/*     */               }
/* 484 */               Thread.sleep(nb);
/* 485 */             } catch (InterruptedException interruptedException) {}
/*     */             continue;
/*     */           } 
/* 488 */           if (exec) {
/* 489 */             out.append(ch);
/*     */           }
/*     */           continue;
/*     */       } 
/*     */       
/* 494 */       if (exec) {
/* 495 */         out.append(ch);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int toInteger(Object pop) {
/* 503 */     if (pop instanceof Number)
/* 504 */       return ((Number)pop).intValue(); 
/* 505 */     if (pop instanceof Boolean) {
/* 506 */       return ((Boolean)pop).booleanValue() ? 1 : 0;
/*     */     }
/* 508 */     return Integer.parseInt(pop.toString());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Curses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */