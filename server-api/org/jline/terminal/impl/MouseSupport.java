/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.function.IntSupplier;
/*     */ import org.jline.terminal.MouseEvent;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.utils.InfoCmp;
/*     */ import org.jline.utils.InputStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MouseSupport
/*     */ {
/*     */   public static boolean hasMouseSupport(Terminal terminal) {
/*  78 */     return (terminal.getStringCapability(InfoCmp.Capability.key_mouse) != null);
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
/*     */   public static boolean trackMouse(Terminal terminal, Terminal.MouseTracking tracking) {
/* 116 */     if (hasMouseSupport(terminal)) {
/* 117 */       switch (tracking) {
/*     */         case Off:
/* 119 */           terminal.writer()
/* 120 */             .write("\033[?1000l\033[?1002l\033[?1003l\033[?1005l\033[?1006l\033[?1015l\033[?1016l");
/*     */           break;
/*     */         case Normal:
/* 123 */           terminal.writer().write("\033[?1005h\033[?1006h\033[?1000h");
/*     */           break;
/*     */         case Button:
/* 126 */           terminal.writer().write("\033[?1005h\033[?1006h\033[?1002h");
/*     */           break;
/*     */         case Any:
/* 129 */           terminal.writer().write("\033[?1005h\033[?1006h\033[?1003h");
/*     */           break;
/*     */       } 
/* 132 */       terminal.flush();
/* 133 */       return true;
/*     */     } 
/* 135 */     return false;
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
/*     */   public static MouseEvent readMouse(Terminal terminal, MouseEvent last) {
/* 154 */     return readMouse(() -> readExt(terminal), last, (String)null);
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
/*     */   public static MouseEvent readMouse(Terminal terminal, MouseEvent last, String prefix) {
/* 173 */     return readMouse(() -> readExt(terminal), last, prefix);
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
/*     */   public static MouseEvent readMouse(IntSupplier reader, MouseEvent last) {
/* 208 */     return readMouse(reader, last, (String)null);
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
/*     */   public static MouseEvent readMouse(IntSupplier reader, MouseEvent last, String prefix) {
/* 229 */     if (prefix != null && !prefix.isEmpty()) {
/*     */       
/* 231 */       if (prefix.equals("\033[<")) {
/*     */         
/* 233 */         IntSupplier prefixReader = createReaderFromString("<");
/* 234 */         return readMouse(chainReaders(prefixReader, reader), last, (String)null);
/* 235 */       }  if (prefix.equals("\033[M")) {
/*     */         
/* 237 */         IntSupplier prefixReader = createReaderFromString("M");
/* 238 */         return readMouse(chainReaders(prefixReader, reader), last, (String)null);
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     int c = reader.getAsInt();
/*     */ 
/*     */     
/* 245 */     if (c == 60)
/*     */     {
/* 247 */       return readMouseSGR(reader, last); } 
/* 248 */     if (c >= 48 && c <= 57)
/*     */     {
/* 250 */       return readMouseURXVT(c, reader, last); } 
/* 251 */     if (c == 77) {
/*     */ 
/*     */       
/* 254 */       int cb = reader.getAsInt();
/*     */       
/* 256 */       int cx = reader.getAsInt();
/* 257 */       int cy = reader.getAsInt();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 262 */       if ((cx & 0x80) != 0 || (cy & 0x80) != 0) {
/* 263 */         return readMouseUTF8(cb, cx, cy, reader, last);
/*     */       }
/*     */       
/* 266 */       return readMouseX10(cb - 32, cx - 32 - 1, cy - 32 - 1, last);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return readMouseX10(c - 32, reader, last);
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
/*     */   private static MouseEvent readMouseX10(int cb, IntSupplier reader, MouseEvent last) {
/* 283 */     int cx = reader.getAsInt() - 32 - 1;
/* 284 */     int cy = reader.getAsInt() - 32 - 1;
/* 285 */     return parseMouseEvent(cb, cx, cy, false, last);
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
/*     */   private static MouseEvent readMouseX10(int cb, int cx, int cy, MouseEvent last) {
/* 298 */     return parseMouseEvent(cb, cx, cy, false, last);
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
/*     */   private static MouseEvent readMouseUTF8(int cb, int cx, int cy, IntSupplier reader, MouseEvent last) {
/* 314 */     int x = decodeUtf8Coordinate(cx, reader);
/* 315 */     int y = decodeUtf8Coordinate(cy, reader);
/*     */ 
/*     */     
/* 318 */     x--;
/* 319 */     y--;
/*     */     
/* 321 */     return parseMouseEvent(cb - 32, x, y, false, last);
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
/*     */   private static int decodeUtf8Coordinate(int firstByte, IntSupplier reader) {
/* 337 */     if ((firstByte & 0x80) == 0)
/*     */     {
/* 339 */       return firstByte - 32; } 
/* 340 */     if ((firstByte & 0xE0) == 192) {
/*     */       
/* 342 */       int secondByte = reader.getAsInt();
/* 343 */       int value = (firstByte & 0x1F) << 6 | secondByte & 0x3F;
/* 344 */       return value - 32;
/* 345 */     }  if ((firstByte & 0xF0) == 224) {
/*     */       
/* 347 */       int secondByte = reader.getAsInt();
/* 348 */       int thirdByte = reader.getAsInt();
/* 349 */       int value = (firstByte & 0xF) << 12 | (secondByte & 0x3F) << 6 | thirdByte & 0x3F;
/* 350 */       return value - 32;
/*     */     } 
/*     */ 
/*     */     
/* 354 */     return firstByte - 32;
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
/*     */   private static MouseEvent readMouseSGR(IntSupplier reader, MouseEvent last) {
/* 378 */     StringBuilder sb = new StringBuilder();
/* 379 */     int[] params = new int[3];
/* 380 */     int paramIndex = 0;
/* 381 */     boolean isPixels = false;
/* 382 */     boolean isRelease = false;
/*     */     
/*     */     int c;
/*     */     
/* 386 */     while ((c = reader.getAsInt()) != -1) {
/* 387 */       if (c == 77 || c == 109) {
/* 388 */         isRelease = (c == 109); break;
/*     */       } 
/* 390 */       if (c == 59) {
/* 391 */         if (paramIndex < params.length) {
/*     */           try {
/* 393 */             params[paramIndex++] = Integer.parseInt(sb.toString());
/* 394 */           } catch (NumberFormatException e) {
/*     */             
/* 396 */             params[paramIndex++] = 0;
/*     */           } 
/* 398 */           sb.setLength(0);
/*     */         }  continue;
/* 400 */       }  if (c >= 48 && c <= 57) {
/* 401 */         sb.append((char)c);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 406 */     if (sb.length() > 0 && paramIndex < params.length) {
/*     */       try {
/* 408 */         params[paramIndex] = Integer.parseInt(sb.toString());
/* 409 */       } catch (NumberFormatException e) {
/*     */         
/* 411 */         params[paramIndex] = 0;
/*     */       } 
/*     */     }
/*     */     
/* 415 */     int cb = params[0];
/* 416 */     int cx = params[1] - 1;
/* 417 */     int cy = params[2] - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     return parseMouseEvent(cb, cx, cy, isRelease, last);
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
/*     */   private static MouseEvent readMouseURXVT(int firstDigit, IntSupplier reader, MouseEvent last) {
/* 438 */     StringBuilder sb = (new StringBuilder()).append((char)firstDigit);
/* 439 */     int[] params = new int[3];
/* 440 */     int paramIndex = 0;
/*     */     
/*     */     int c;
/*     */     
/* 444 */     while ((c = reader.getAsInt()) != -1 && 
/* 445 */       c != 77) {
/*     */       
/* 447 */       if (c == 59) {
/* 448 */         if (paramIndex < params.length) {
/*     */           try {
/* 450 */             params[paramIndex++] = Integer.parseInt(sb.toString());
/* 451 */           } catch (NumberFormatException e) {
/*     */             
/* 453 */             params[paramIndex++] = 0;
/*     */           } 
/* 455 */           sb.setLength(0);
/*     */         }  continue;
/* 457 */       }  if (c >= 48 && c <= 57) {
/* 458 */         sb.append((char)c);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 463 */     if (sb.length() > 0 && paramIndex < params.length) {
/*     */       try {
/* 465 */         params[paramIndex] = Integer.parseInt(sb.toString());
/* 466 */       } catch (NumberFormatException e) {
/*     */         
/* 468 */         params[paramIndex] = 0;
/*     */       } 
/*     */     }
/*     */     
/* 472 */     int cb = params[0];
/* 473 */     int cx = params[1] - 1;
/* 474 */     int cy = params[2] - 1;
/*     */     
/* 476 */     return parseMouseEvent(cb, cx, cy, false, last);
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
/*     */   private static MouseEvent parseMouseEvent(int cb, int cx, int cy, boolean isRelease, MouseEvent last) {
/*     */     MouseEvent.Type type;
/*     */     MouseEvent.Button button;
/* 492 */     EnumSet<MouseEvent.Modifier> modifiers = EnumSet.noneOf(MouseEvent.Modifier.class);
/*     */ 
/*     */     
/* 495 */     if ((cb & 0x4) == 4) {
/* 496 */       modifiers.add(MouseEvent.Modifier.Shift);
/*     */     }
/* 498 */     if ((cb & 0x8) == 8) {
/* 499 */       modifiers.add(MouseEvent.Modifier.Alt);
/*     */     }
/* 501 */     if ((cb & 0x10) == 16) {
/* 502 */       modifiers.add(MouseEvent.Modifier.Control);
/*     */     }
/*     */ 
/*     */     
/* 506 */     if ((cb & 0x40) == 64)
/* 507 */     { type = MouseEvent.Type.Wheel;
/* 508 */       button = ((cb & 0x1) == 1) ? MouseEvent.Button.WheelDown : MouseEvent.Button.WheelUp;
/*     */        }
/*     */     
/* 511 */     else if (isRelease)
/*     */     
/* 513 */     { button = getButtonForCode(cb & 0x3);
/* 514 */       type = MouseEvent.Type.Released; }
/*     */     else
/* 516 */     { int b = cb & 0x3;
/* 517 */       switch (b)
/*     */       { case 0:
/* 519 */           button = MouseEvent.Button.Button1;
/* 520 */           if (last.getButton() == button && (last
/* 521 */             .getType() == MouseEvent.Type.Pressed || last
/* 522 */             .getType() == MouseEvent.Type.Dragged)) {
/* 523 */             type = MouseEvent.Type.Dragged;
/*     */           } else {
/* 525 */             type = MouseEvent.Type.Pressed;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 561 */           return new MouseEvent(type, button, modifiers, cx, cy);case 1: button = MouseEvent.Button.Button2; if (last.getButton() == button && (last.getType() == MouseEvent.Type.Pressed || last.getType() == MouseEvent.Type.Dragged)) { type = MouseEvent.Type.Dragged; } else { type = MouseEvent.Type.Pressed; }  return new MouseEvent(type, button, modifiers, cx, cy);case 2: button = MouseEvent.Button.Button3; if (last.getButton() == button && (last.getType() == MouseEvent.Type.Pressed || last.getType() == MouseEvent.Type.Dragged)) { type = MouseEvent.Type.Dragged; } else { type = MouseEvent.Type.Pressed; }  return new MouseEvent(type, button, modifiers, cx, cy); }  if (last.getType() == MouseEvent.Type.Pressed || last.getType() == MouseEvent.Type.Dragged) { button = last.getButton(); type = MouseEvent.Type.Released; } else { button = MouseEvent.Button.NoButton; type = MouseEvent.Type.Moved; }  }  return new MouseEvent(type, button, modifiers, cx, cy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MouseEvent.Button getButtonForCode(int code) {
/* 571 */     switch (code) {
/*     */       case 0:
/* 573 */         return MouseEvent.Button.Button1;
/*     */       case 1:
/* 575 */         return MouseEvent.Button.Button2;
/*     */       case 2:
/* 577 */         return MouseEvent.Button.Button3;
/*     */     } 
/* 579 */     return MouseEvent.Button.NoButton;
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
/*     */   public static String[] keys() {
/* 598 */     return new String[] { "\033[<", "\033[M" };
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
/*     */   public static String[] keys(Terminal terminal) {
/* 619 */     String keyMouse = terminal.getStringCapability(InfoCmp.Capability.key_mouse);
/* 620 */     if (keyMouse != null) {
/*     */       
/* 622 */       if (Arrays.<String>asList(keys()).contains(keyMouse))
/*     */       {
/* 624 */         return keys();
/*     */       }
/*     */       
/* 627 */       return new String[] { keyMouse, "\033[<", "\033[M" };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 634 */     return keys();
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
/*     */   private static int readExt(Terminal terminal) {
/*     */     try {
/*     */       int c;
/* 657 */       if (terminal.encoding() != StandardCharsets.UTF_8) {
/* 658 */         c = (new InputStreamReader(terminal.input(), StandardCharsets.UTF_8)).read();
/*     */       } else {
/* 660 */         c = terminal.reader().read();
/*     */       } 
/* 662 */       if (c < 0) {
/* 663 */         throw new EOFException();
/*     */       }
/* 665 */       return c;
/* 666 */     } catch (IOException e) {
/* 667 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntSupplier createReaderFromString(String s) {
/* 678 */     int[] chars = s.chars().toArray();
/* 679 */     int[] index = { 0 };
/*     */     
/* 681 */     return () -> {
/*     */         if (index[0] < chars.length) {
/*     */           index[0] = index[0] + 1;
/*     */           return chars[index[0]];
/*     */         } 
/*     */         return -1;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntSupplier chainReaders(final IntSupplier first, final IntSupplier second) {
/* 698 */     return new IntSupplier()
/*     */       {
/*     */         private boolean firstExhausted = false;
/*     */         
/*     */         public int getAsInt() {
/* 703 */           if (!this.firstExhausted) {
/* 704 */             int c = first.getAsInt();
/* 705 */             if (c != -1) {
/* 706 */               return c;
/*     */             }
/* 708 */             this.firstExhausted = true;
/*     */           } 
/* 710 */           return second.getAsInt();
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\MouseSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */