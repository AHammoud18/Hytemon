/*     */ package org.jline.nativ;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ 
/*     */ 
/*     */ public class Kernel32
/*     */ {
/*     */   public static short FOREGROUND_BLUE;
/*     */   public static short FOREGROUND_GREEN;
/*     */   public static short FOREGROUND_RED;
/*     */   public static short FOREGROUND_INTENSITY;
/*     */   public static short BACKGROUND_BLUE;
/*     */   public static short BACKGROUND_GREEN;
/*     */   public static short BACKGROUND_RED;
/*     */   public static short BACKGROUND_INTENSITY;
/*     */   public static short COMMON_LVB_LEADING_BYTE;
/*     */   public static short COMMON_LVB_TRAILING_BYTE;
/*     */   
/*     */   static {
/*  21 */     if (JLineNativeLoader.initialize()) {
/*  22 */       init();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static short COMMON_LVB_GRID_HORIZONTAL;
/*     */   
/*     */   public static short COMMON_LVB_GRID_LVERTICAL;
/*     */   
/*     */   public static short COMMON_LVB_GRID_RVERTICAL;
/*     */   
/*     */   public static short COMMON_LVB_REVERSE_VIDEO;
/*     */   
/*     */   public static short COMMON_LVB_UNDERSCORE;
/*     */   
/*     */   public static int FORMAT_MESSAGE_FROM_SYSTEM;
/*     */   
/*     */   public static int STD_INPUT_HANDLE;
/*     */   
/*     */   public static int STD_OUTPUT_HANDLE;
/*     */   
/*     */   public static int STD_ERROR_HANDLE;
/*     */   
/*     */   public static long INVALID_HANDLE_VALUE;
/*     */ 
/*     */   
/*     */   public static class SMALL_RECT
/*     */   {
/*     */     public static int SIZEOF;
/*     */     
/*     */     public short left;
/*     */     public short top;
/*     */     public short right;
/*     */     public short bottom;
/*     */     
/*     */     static {
/*  58 */       if (JLineNativeLoader.initialize()) {
/*  59 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short width() {
/*  73 */       return (short)(this.right - this.left);
/*     */     }
/*     */     
/*     */     public short height() {
/*  77 */       return (short)(this.bottom - this.top);
/*     */     }
/*     */     
/*     */     public SMALL_RECT copy() {
/*  81 */       SMALL_RECT rc = new SMALL_RECT();
/*  82 */       rc.left = this.left;
/*  83 */       rc.top = this.top;
/*  84 */       rc.right = this.right;
/*  85 */       rc.bottom = this.bottom;
/*  86 */       return rc;
/*     */     }
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */   
/*     */   public static class COORD {
/*     */     public static int SIZEOF;
/*     */     public short x;
/*     */     public short y;
/*     */     
/*     */     static {
/*  98 */       if (JLineNativeLoader.initialize()) {
/*  99 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public COORD copy() {
/* 111 */       COORD rc = new COORD();
/* 112 */       rc.x = this.x;
/* 113 */       rc.y = this.y;
/* 114 */       return rc;
/*     */     }
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */   
/*     */   public static class CONSOLE_SCREEN_BUFFER_INFO {
/*     */     public static int SIZEOF;
/*     */     
/*     */     static {
/* 124 */       if (JLineNativeLoader.initialize()) {
/* 125 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     public Kernel32.COORD size = new Kernel32.COORD();
/* 134 */     public Kernel32.COORD cursorPosition = new Kernel32.COORD();
/*     */     public short attributes;
/* 136 */     public Kernel32.SMALL_RECT window = new Kernel32.SMALL_RECT();
/* 137 */     public Kernel32.COORD maximumWindowSize = new Kernel32.COORD();
/*     */     
/*     */     public int windowWidth() {
/* 140 */       return this.window.width() + 1;
/*     */     }
/*     */     
/*     */     public int windowHeight() {
/* 144 */       return this.window.height() + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static native void init();
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
/*     */   public static class CHAR_INFO
/*     */   {
/*     */     public static int SIZEOF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char unicodeChar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static native void init();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 243 */       JLineNativeLoader.initialize();
/* 244 */       init();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class KEY_EVENT_RECORD
/*     */   {
/*     */     public static int SIZEOF;
/*     */     
/*     */     public static int CAPSLOCK_ON;
/*     */     
/*     */     public static int NUMLOCK_ON;
/*     */     public static int SCROLLLOCK_ON;
/*     */     public static int ENHANCED_KEY;
/*     */     public static int LEFT_ALT_PRESSED;
/*     */     public static int LEFT_CTRL_PRESSED;
/*     */     public static int RIGHT_ALT_PRESSED;
/*     */     public static int RIGHT_CTRL_PRESSED;
/*     */     public static int SHIFT_PRESSED;
/*     */     public boolean keyDown;
/*     */     public short repeatCount;
/*     */     public short keyCode;
/*     */     public short scanCode;
/*     */     public char uchar;
/*     */     public int controlKeyState;
/*     */     
/*     */     static {
/* 271 */       if (JLineNativeLoader.initialize()) {
/* 272 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 297 */       return "KEY_EVENT_RECORD{keyDown=" + this.keyDown + ", repeatCount=" + this.repeatCount + ", keyCode=" + this.keyCode + ", scanCode=" + this.scanCode + ", uchar=" + this.uchar + ", controlKeyState=" + this.controlKeyState + '}';
/*     */     }
/*     */ 
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MOUSE_EVENT_RECORD
/*     */   {
/*     */     public static int SIZEOF;
/*     */     public static int FROM_LEFT_1ST_BUTTON_PRESSED;
/*     */     public static int FROM_LEFT_2ND_BUTTON_PRESSED;
/*     */     public static int FROM_LEFT_3RD_BUTTON_PRESSED;
/*     */     
/*     */     static {
/* 313 */       if (JLineNativeLoader.initialize()) {
/* 314 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public static int FROM_LEFT_4TH_BUTTON_PRESSED;
/*     */     
/*     */     public static int RIGHTMOST_BUTTON_PRESSED;
/*     */     
/*     */     public static int CAPSLOCK_ON;
/*     */     
/*     */     public static int NUMLOCK_ON;
/*     */     
/*     */     public static int SCROLLLOCK_ON;
/*     */     
/*     */     public static int ENHANCED_KEY;
/*     */     
/*     */     public static int LEFT_ALT_PRESSED;
/*     */     
/*     */     public static int LEFT_CTRL_PRESSED;
/*     */     
/*     */     public static int RIGHT_ALT_PRESSED;
/*     */     public static int RIGHT_CTRL_PRESSED;
/*     */     public static int SHIFT_PRESSED;
/*     */     public static int DOUBLE_CLICK;
/*     */     public static int MOUSE_HWHEELED;
/*     */     public static int MOUSE_MOVED;
/*     */     public static int MOUSE_WHEELED;
/* 342 */     public Kernel32.COORD mousePosition = new Kernel32.COORD();
/*     */     public int buttonState;
/*     */     public int controlKeyState;
/*     */     public int eventFlags;
/*     */     
/*     */     public String toString() {
/* 348 */       return "MOUSE_EVENT_RECORD{mousePosition=" + this.mousePosition + ", buttonState=" + this.buttonState + ", controlKeyState=" + this.controlKeyState + ", eventFlags=" + this.eventFlags + '}';
/*     */     }
/*     */ 
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WINDOW_BUFFER_SIZE_RECORD
/*     */   {
/*     */     public static int SIZEOF;
/*     */ 
/*     */     
/*     */     static {
/* 362 */       if (JLineNativeLoader.initialize()) {
/* 363 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     public Kernel32.COORD size = new Kernel32.COORD();
/*     */     
/*     */     public String toString() {
/* 374 */       return "WINDOW_BUFFER_SIZE_RECORD{size=" + this.size + '}';
/*     */     }
/*     */     
/*     */     private static native void init(); }
/*     */   
/*     */   public static class FOCUS_EVENT_RECORD {
/*     */     public static int SIZEOF;
/*     */     
/*     */     static {
/* 383 */       if (JLineNativeLoader.initialize())
/* 384 */         init(); 
/*     */     }
/*     */     
/*     */     public boolean setFocus;
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */   
/*     */   public static class MENU_EVENT_RECORD {
/*     */     public static int SIZEOF;
/*     */     public int commandId;
/*     */     
/*     */     private static native void init();
/*     */     
/*     */     static {
/* 399 */       if (JLineNativeLoader.initialize()) {
/* 400 */         init();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class INPUT_RECORD
/*     */   {
/*     */     public static int SIZEOF;
/*     */     
/*     */     public static short KEY_EVENT;
/*     */     
/*     */     public static short MOUSE_EVENT;
/*     */     public static short WINDOW_BUFFER_SIZE_EVENT;
/*     */     
/*     */     static {
/* 416 */       if (JLineNativeLoader.initialize()) {
/* 417 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public static short FOCUS_EVENT;
/*     */     public static short MENU_EVENT;
/*     */     public short eventType;
/*     */     
/*     */     private static native void init();
/*     */     
/*     */     public static native void memmove(INPUT_RECORD param1INPUT_RECORD, long param1Long1, long param1Long2);
/*     */     
/* 430 */     public Kernel32.KEY_EVENT_RECORD keyEvent = new Kernel32.KEY_EVENT_RECORD();
/* 431 */     public Kernel32.MOUSE_EVENT_RECORD mouseEvent = new Kernel32.MOUSE_EVENT_RECORD();
/* 432 */     public Kernel32.WINDOW_BUFFER_SIZE_RECORD windowBufferSizeEvent = new Kernel32.WINDOW_BUFFER_SIZE_RECORD();
/* 433 */     public Kernel32.MENU_EVENT_RECORD menuEvent = new Kernel32.MENU_EVENT_RECORD();
/* 434 */     public Kernel32.FOCUS_EVENT_RECORD focusEvent = new Kernel32.FOCUS_EVENT_RECORD();
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
/*     */   public static INPUT_RECORD[] readConsoleInputHelper(long handle, int count, boolean peek) throws IOException {
/* 463 */     int[] length = new int[1];
/*     */     
/* 465 */     long inputRecordPtr = 0L;
/*     */     try {
/* 467 */       inputRecordPtr = malloc((INPUT_RECORD.SIZEOF * count));
/* 468 */       if (inputRecordPtr == 0L) {
/* 469 */         throw new IOException("cannot allocate memory with JNI");
/*     */       }
/*     */ 
/*     */       
/* 473 */       int res = peek ? PeekConsoleInputW(handle, inputRecordPtr, count, length) : ReadConsoleInputW(handle, inputRecordPtr, count, length);
/* 474 */       if (res == 0) {
/* 475 */         int bufferSize = 160;
/* 476 */         byte[] data = new byte[bufferSize];
/* 477 */         FormatMessageW(FORMAT_MESSAGE_FROM_SYSTEM, 0L, GetLastError(), 0, data, bufferSize, null);
/* 478 */         String lastErrorMessage = (new String(data, StandardCharsets.UTF_16LE)).trim();
/* 479 */         throw new IOException("ReadConsoleInputW failed: " + lastErrorMessage);
/*     */       } 
/* 481 */       if (length[0] <= 0) {
/* 482 */         return new INPUT_RECORD[0];
/*     */       }
/* 484 */       INPUT_RECORD[] records = new INPUT_RECORD[length[0]];
/* 485 */       for (int i = 0; i < records.length; i++) {
/* 486 */         records[i] = new INPUT_RECORD();
/* 487 */         INPUT_RECORD.memmove(records[i], inputRecordPtr + (i * INPUT_RECORD.SIZEOF), INPUT_RECORD.SIZEOF);
/*     */       } 
/* 489 */       return records;
/*     */     } finally {
/* 491 */       if (inputRecordPtr != 0L) {
/* 492 */         free(inputRecordPtr);
/*     */       }
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
/*     */   public static INPUT_RECORD[] readConsoleKeyInput(long handle, int count, boolean peek) throws IOException {
/*     */     while (true) {
/* 507 */       INPUT_RECORD[] evts = readConsoleInputHelper(handle, count, peek);
/* 508 */       int keyEvtCount = 0;
/* 509 */       for (INPUT_RECORD evt : evts) {
/* 510 */         if (evt.eventType == INPUT_RECORD.KEY_EVENT) keyEvtCount++; 
/*     */       } 
/* 512 */       if (keyEvtCount > 0) {
/* 513 */         INPUT_RECORD[] res = new INPUT_RECORD[keyEvtCount];
/* 514 */         int i = 0;
/* 515 */         for (INPUT_RECORD evt : evts) {
/* 516 */           if (evt.eventType == INPUT_RECORD.KEY_EVENT) {
/* 517 */             res[i++] = evt;
/*     */           }
/*     */         } 
/* 520 */         return res;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getLastErrorMessage() {
/* 526 */     int bufferSize = 160;
/* 527 */     byte[] data = new byte[bufferSize];
/* 528 */     FormatMessageW(FORMAT_MESSAGE_FROM_SYSTEM, 0L, GetLastError(), 0, data, bufferSize, null);
/* 529 */     return (new String(data, StandardCharsets.UTF_16LE)).trim();
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   public static native long malloc(long paramLong);
/*     */   
/*     */   public static native void free(long paramLong);
/*     */   
/*     */   public static native int SetConsoleTextAttribute(long paramLong, short paramShort);
/*     */   
/*     */   public static native int WaitForSingleObject(long paramLong, int paramInt);
/*     */   
/*     */   public static native int CloseHandle(long paramLong);
/*     */   
/*     */   public static native int GetLastError();
/*     */   
/*     */   public static native int FormatMessageW(int paramInt1, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4, long[] paramArrayOflong);
/*     */   
/*     */   public static native int GetConsoleScreenBufferInfo(long paramLong, CONSOLE_SCREEN_BUFFER_INFO paramCONSOLE_SCREEN_BUFFER_INFO);
/*     */   
/*     */   public static native long GetStdHandle(int paramInt);
/*     */   
/*     */   public static native int SetConsoleCursorPosition(long paramLong, COORD paramCOORD);
/*     */   
/*     */   public static native int FillConsoleOutputCharacterW(long paramLong, char paramChar, int paramInt, COORD paramCOORD, int[] paramArrayOfint);
/*     */   
/*     */   public static native int FillConsoleOutputAttribute(long paramLong, short paramShort, int paramInt, COORD paramCOORD, int[] paramArrayOfint);
/*     */   
/*     */   public static native int WriteConsoleW(long paramLong1, char[] paramArrayOfchar, int paramInt, int[] paramArrayOfint, long paramLong2);
/*     */   
/*     */   public static native int GetConsoleMode(long paramLong, int[] paramArrayOfint);
/*     */   
/*     */   public static native int SetConsoleMode(long paramLong, int paramInt);
/*     */   
/*     */   public static native int _getch();
/*     */   
/*     */   public static native int SetConsoleTitle(String paramString);
/*     */   
/*     */   public static native int GetConsoleOutputCP();
/*     */   
/*     */   public static native int SetConsoleOutputCP(int paramInt);
/*     */   
/*     */   public static native int ScrollConsoleScreenBuffer(long paramLong, SMALL_RECT paramSMALL_RECT1, SMALL_RECT paramSMALL_RECT2, COORD paramCOORD, CHAR_INFO paramCHAR_INFO);
/*     */   
/*     */   private static native int ReadConsoleInputW(long paramLong1, long paramLong2, int paramInt, int[] paramArrayOfint);
/*     */   
/*     */   private static native int PeekConsoleInputW(long paramLong1, long paramLong2, int paramInt, int[] paramArrayOfint);
/*     */   
/*     */   public static native int GetNumberOfConsoleInputEvents(long paramLong, int[] paramArrayOfint);
/*     */   
/*     */   public static native int FlushConsoleInputBuffer(long paramLong);
/*     */   
/*     */   public static native int isatty(int paramInt);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\nativ\Kernel32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */