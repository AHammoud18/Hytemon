/*     */ package org.jline.terminal.impl.ffm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.foreign.AddressLayout;
/*     */ import java.lang.foreign.Arena;
/*     */ import java.lang.foreign.FunctionDescriptor;
/*     */ import java.lang.foreign.GroupLayout;
/*     */ import java.lang.foreign.Linker;
/*     */ import java.lang.foreign.MemoryLayout;
/*     */ import java.lang.foreign.MemorySegment;
/*     */ import java.lang.foreign.SymbolLookup;
/*     */ import java.lang.foreign.ValueLayout;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.VarHandle;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Kernel32
/*     */ {
/*     */   public static final int FORMAT_MESSAGE_FROM_SYSTEM = 4096;
/*     */   public static final long INVALID_HANDLE_VALUE = -1L;
/*     */   public static final int STD_INPUT_HANDLE = -10;
/*     */   public static final int STD_OUTPUT_HANDLE = -11;
/*     */   public static final int STD_ERROR_HANDLE = -12;
/*     */   public static final int ENABLE_PROCESSED_INPUT = 1;
/*     */   public static final int ENABLE_LINE_INPUT = 2;
/*     */   public static final int ENABLE_ECHO_INPUT = 4;
/*     */   public static final int ENABLE_WINDOW_INPUT = 8;
/*     */   public static final int ENABLE_MOUSE_INPUT = 16;
/*     */   public static final int ENABLE_INSERT_MODE = 32;
/*     */   public static final int ENABLE_QUICK_EDIT_MODE = 64;
/*     */   public static final int ENABLE_EXTENDED_FLAGS = 128;
/*     */   public static final int RIGHT_ALT_PRESSED = 1;
/*     */   public static final int LEFT_ALT_PRESSED = 2;
/*     */   public static final int RIGHT_CTRL_PRESSED = 4;
/*     */   public static final int LEFT_CTRL_PRESSED = 8;
/*     */   public static final int SHIFT_PRESSED = 16;
/*     */   public static final int FOREGROUND_BLUE = 1;
/*     */   public static final int FOREGROUND_GREEN = 2;
/*     */   public static final int FOREGROUND_RED = 4;
/*     */   public static final int FOREGROUND_INTENSITY = 8;
/*     */   public static final int BACKGROUND_BLUE = 16;
/*     */   public static final int BACKGROUND_GREEN = 32;
/*     */   public static final int BACKGROUND_RED = 64;
/*     */   public static final int BACKGROUND_INTENSITY = 128;
/*     */   public static final int FROM_LEFT_1ST_BUTTON_PRESSED = 1;
/*     */   public static final int RIGHTMOST_BUTTON_PRESSED = 2;
/*     */   public static final int FROM_LEFT_2ND_BUTTON_PRESSED = 4;
/*     */   public static final int FROM_LEFT_3RD_BUTTON_PRESSED = 8;
/*     */   public static final int FROM_LEFT_4TH_BUTTON_PRESSED = 16;
/*     */   public static final int MOUSE_MOVED = 1;
/*     */   public static final int DOUBLE_CLICK = 2;
/*     */   public static final int MOUSE_WHEELED = 4;
/*     */   public static final int MOUSE_HWHEELED = 8;
/*     */   public static final short KEY_EVENT = 1;
/*     */   public static final short MOUSE_EVENT = 2;
/*     */   public static final short WINDOW_BUFFER_SIZE_EVENT = 4;
/*     */   public static final short MENU_EVENT = 8;
/*     */   public static final short FOCUS_EVENT = 16;
/*     */   
/*     */   public static int WaitForSingleObject(MemorySegment hHandle, int dwMilliseconds) {
/*  72 */     MethodHandle mh$ = requireNonNull(WaitForSingleObject$MH, "WaitForSingleObject");
/*     */     try {
/*  74 */       return mh$.invokeExact(hHandle, dwMilliseconds);
/*  75 */     } catch (Throwable ex$) {
/*  76 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MemorySegment GetStdHandle(int nStdHandle) {
/*  81 */     MethodHandle mh$ = requireNonNull(GetStdHandle$MH, "GetStdHandle");
/*     */     try {
/*  83 */       return mh$.invokeExact(nStdHandle);
/*  84 */     } catch (Throwable ex$) {
/*  85 */       throw new AssertionError("should not reach here", ex$);
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
/*     */   public static int FormatMessageW(int dwFlags, MemorySegment lpSource, int dwMessageId, int dwLanguageId, MemorySegment lpBuffer, int nSize, MemorySegment Arguments) {
/*  97 */     MethodHandle mh$ = requireNonNull(FormatMessageW$MH, "FormatMessageW");
/*     */     try {
/*  99 */       return mh$.invokeExact(dwFlags, lpSource, dwMessageId, dwLanguageId, lpBuffer, nSize, Arguments);
/* 100 */     } catch (Throwable ex$) {
/* 101 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int SetConsoleTextAttribute(MemorySegment hConsoleOutput, short wAttributes) {
/* 106 */     MethodHandle mh$ = requireNonNull(SetConsoleTextAttribute$MH, "SetConsoleTextAttribute");
/*     */     try {
/* 108 */       return mh$.invokeExact(hConsoleOutput, wAttributes);
/* 109 */     } catch (Throwable ex$) {
/* 110 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int SetConsoleMode(MemorySegment hConsoleHandle, int dwMode) {
/* 115 */     MethodHandle mh$ = requireNonNull(SetConsoleMode$MH, "SetConsoleMode");
/*     */     try {
/* 117 */       return mh$.invokeExact(hConsoleHandle, dwMode);
/* 118 */     } catch (Throwable ex$) {
/* 119 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int GetConsoleMode(MemorySegment hConsoleHandle, MemorySegment lpMode) {
/* 125 */     MethodHandle mh$ = requireNonNull(GetConsoleMode$MH, "GetConsoleMode");
/*     */     try {
/* 127 */       return mh$.invokeExact(hConsoleHandle, lpMode);
/* 128 */     } catch (Throwable ex$) {
/* 129 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int SetConsoleTitleW(MemorySegment lpConsoleTitle) {
/* 134 */     MethodHandle mh$ = requireNonNull(SetConsoleTitleW$MH, "SetConsoleTitleW");
/*     */     try {
/* 136 */       return mh$.invokeExact(lpConsoleTitle);
/* 137 */     } catch (Throwable ex$) {
/* 138 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int SetConsoleCursorPosition(MemorySegment hConsoleOutput, COORD dwCursorPosition) {
/* 143 */     MethodHandle mh$ = requireNonNull(SetConsoleCursorPosition$MH, "SetConsoleCursorPosition");
/*     */     try {
/* 145 */       return mh$.invokeExact(hConsoleOutput, dwCursorPosition.seg);
/* 146 */     } catch (Throwable ex$) {
/* 147 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int FillConsoleOutputCharacterW(MemorySegment hConsoleOutput, char cCharacter, int nLength, COORD dwWriteCoord, MemorySegment lpNumberOfCharsWritten) {
/* 157 */     MethodHandle mh$ = requireNonNull(FillConsoleOutputCharacterW$MH, "FillConsoleOutputCharacterW");
/*     */     try {
/* 159 */       return mh$.invokeExact(hConsoleOutput, cCharacter, nLength, dwWriteCoord.seg, lpNumberOfCharsWritten);
/* 160 */     } catch (Throwable ex$) {
/* 161 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int FillConsoleOutputAttribute(MemorySegment hConsoleOutput, short wAttribute, int nLength, COORD dwWriteCoord, MemorySegment lpNumberOfAttrsWritten) {
/* 171 */     MethodHandle mh$ = requireNonNull(FillConsoleOutputAttribute$MH, "FillConsoleOutputAttribute");
/*     */     try {
/* 173 */       return mh$.invokeExact(hConsoleOutput, wAttribute, nLength, dwWriteCoord.seg, lpNumberOfAttrsWritten);
/* 174 */     } catch (Throwable ex$) {
/* 175 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int WriteConsoleW(MemorySegment hConsoleOutput, MemorySegment lpBuffer, int nNumberOfCharsToWrite, MemorySegment lpNumberOfCharsWritten, MemorySegment lpReserved) {
/* 185 */     MethodHandle mh$ = requireNonNull(WriteConsoleW$MH, "WriteConsoleW");
/*     */     try {
/* 187 */       return mh$.invokeExact(hConsoleOutput, lpBuffer, nNumberOfCharsToWrite, lpNumberOfCharsWritten, lpReserved);
/*     */     }
/* 189 */     catch (Throwable ex$) {
/* 190 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ReadConsoleInputW(MemorySegment hConsoleInput, MemorySegment lpBuffer, int nLength, MemorySegment lpNumberOfEventsRead) {
/* 199 */     MethodHandle mh$ = requireNonNull(ReadConsoleInputW$MH, "ReadConsoleInputW");
/*     */     try {
/* 201 */       return mh$.invokeExact(hConsoleInput, lpBuffer, nLength, lpNumberOfEventsRead);
/* 202 */     } catch (Throwable ex$) {
/* 203 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int PeekConsoleInputW(MemorySegment hConsoleInput, MemorySegment lpBuffer, int nLength, MemorySegment lpNumberOfEventsRead) {
/* 212 */     MethodHandle mh$ = requireNonNull(PeekConsoleInputW$MH, "PeekConsoleInputW");
/*     */     try {
/* 214 */       return mh$.invokeExact(hConsoleInput, lpBuffer, nLength, lpNumberOfEventsRead);
/* 215 */     } catch (Throwable ex$) {
/* 216 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int GetConsoleScreenBufferInfo(MemorySegment hConsoleOutput, CONSOLE_SCREEN_BUFFER_INFO lpConsoleScreenBufferInfo) {
/* 222 */     MethodHandle mh$ = requireNonNull(GetConsoleScreenBufferInfo$MH, "GetConsoleScreenBufferInfo");
/*     */     try {
/* 224 */       return mh$.invokeExact(hConsoleOutput, lpConsoleScreenBufferInfo.seg);
/* 225 */     } catch (Throwable ex$) {
/* 226 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ScrollConsoleScreenBuffer(MemorySegment hConsoleOutput, SMALL_RECT lpScrollRectangle, SMALL_RECT lpClipRectangle, COORD dwDestinationOrigin, CHAR_INFO lpFill) {
/* 236 */     MethodHandle mh$ = requireNonNull(ScrollConsoleScreenBufferW$MH, "ScrollConsoleScreenBuffer");
/*     */     try {
/* 238 */       return mh$.invokeExact(hConsoleOutput, lpScrollRectangle.seg, lpClipRectangle.seg, dwDestinationOrigin.seg, lpFill.seg);
/*     */     }
/* 240 */     catch (Throwable ex$) {
/* 241 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int GetLastError() {
/* 246 */     MethodHandle mh$ = requireNonNull(GetLastError$MH, "GetLastError");
/*     */     try {
/* 248 */       return mh$.invokeExact();
/* 249 */     } catch (Throwable ex$) {
/* 250 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int GetFileType(MemorySegment hFile) {
/* 255 */     MethodHandle mh$ = requireNonNull(GetFileType$MH, "GetFileType");
/*     */     try {
/* 257 */       return mh$.invokeExact(hFile);
/* 258 */     } catch (Throwable ex$) {
/* 259 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static MemorySegment _get_osfhandle(int fd) {
/* 264 */     MethodHandle mh$ = requireNonNull(_get_osfhandle$MH, "_get_osfhandle");
/*     */     try {
/* 266 */       return mh$.invokeExact(fd);
/* 267 */     } catch (Throwable ex$) {
/* 268 */       throw new AssertionError("should not reach here", ex$);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static INPUT_RECORD[] readConsoleInputHelper(MemorySegment handle, int count, boolean peek) throws IOException {
/* 274 */     return readConsoleInputHelper(Arena.ofAuto(), handle, count, peek);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static INPUT_RECORD[] readConsoleInputHelper(Arena arena, MemorySegment handle, int count, boolean peek) throws IOException {
/* 280 */     MemorySegment inputRecordPtr = arena.allocate(INPUT_RECORD.LAYOUT, count);
/* 281 */     MemorySegment length = arena.allocate(ValueLayout.JAVA_INT, 1L);
/*     */ 
/*     */     
/* 284 */     int res = peek ? PeekConsoleInputW(handle, inputRecordPtr, count, length) : ReadConsoleInputW(handle, inputRecordPtr, count, length);
/* 285 */     if (res == 0) {
/* 286 */       throw new IOException("ReadConsoleInputW failed: " + getLastErrorMessage());
/*     */     }
/* 288 */     int len = length.get(ValueLayout.JAVA_INT, 0L);
/* 289 */     return (INPUT_RECORD[])inputRecordPtr
/* 290 */       .elements(INPUT_RECORD.LAYOUT)
/* 291 */       .map(INPUT_RECORD::new)
/* 292 */       .limit(len)
/* 293 */       .toArray(x$0 -> new INPUT_RECORD[x$0]);
/*     */   }
/*     */   
/*     */   public static String getLastErrorMessage() {
/* 297 */     int errorCode = GetLastError();
/* 298 */     return getErrorMessage(errorCode);
/*     */   }
/*     */   
/*     */   public static String getErrorMessage(int errorCode) {
/* 302 */     int bufferSize = 160;
/* 303 */     Arena arena = Arena.ofConfined(); 
/* 304 */     try { MemorySegment data = arena.allocate(bufferSize);
/* 305 */       FormatMessageW(4096, MemorySegment.NULL, errorCode, 0, data, bufferSize, MemorySegment.NULL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       String str = (new String(data.toArray(ValueLayout.JAVA_BYTE), StandardCharsets.UTF_16LE)).trim();
/* 314 */       if (arena != null) arena.close();  return str; }
/*     */     catch (Throwable throwable) { if (arena != null)
/*     */         try { arena.close(); }
/*     */         catch (Throwable throwable1)
/*     */         { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 320 */      } static { System.loadLibrary("msvcrt");
/* 321 */     System.loadLibrary("Kernel32"); }
/* 322 */    private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup();
/*     */ 
/*     */   
/*     */   static MethodHandle downcallHandle(String name, FunctionDescriptor fdesc) {
/* 326 */     return SYMBOL_LOOKUP
/* 327 */       .find(name)
/* 328 */       .<MethodHandle>map(addr -> Linker.nativeLinker().downcallHandle(addr, fdesc, new Linker.Option[0]))
/* 329 */       .orElse(null);
/*     */   }
/*     */   
/* 332 */   static final ValueLayout.OfBoolean C_BOOL$LAYOUT = ValueLayout.JAVA_BOOLEAN;
/* 333 */   static final ValueLayout.OfByte C_CHAR$LAYOUT = ValueLayout.JAVA_BYTE;
/* 334 */   static final ValueLayout.OfChar C_WCHAR$LAYOUT = ValueLayout.JAVA_CHAR;
/* 335 */   static final ValueLayout.OfShort C_SHORT$LAYOUT = ValueLayout.JAVA_SHORT;
/* 336 */   static final ValueLayout.OfShort C_WORD$LAYOUT = ValueLayout.JAVA_SHORT;
/* 337 */   static final ValueLayout.OfInt C_DWORD$LAYOUT = ValueLayout.JAVA_INT;
/* 338 */   static final ValueLayout.OfInt C_INT$LAYOUT = ValueLayout.JAVA_INT;
/* 339 */   static final ValueLayout.OfLong C_LONG$LAYOUT = ValueLayout.JAVA_LONG;
/* 340 */   static final ValueLayout.OfLong C_LONG_LONG$LAYOUT = ValueLayout.JAVA_LONG;
/* 341 */   static final ValueLayout.OfFloat C_FLOAT$LAYOUT = ValueLayout.JAVA_FLOAT;
/* 342 */   static final ValueLayout.OfDouble C_DOUBLE$LAYOUT = ValueLayout.JAVA_DOUBLE;
/* 343 */   static final AddressLayout C_POINTER$LAYOUT = ValueLayout.ADDRESS;
/*     */   
/* 345 */   static final MethodHandle WaitForSingleObject$MH = downcallHandle("WaitForSingleObject", 
/*     */       
/* 347 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_INT$LAYOUT }));
/*     */   
/* 349 */   static final MethodHandle GetStdHandle$MH = downcallHandle("GetStdHandle", FunctionDescriptor.of(C_POINTER$LAYOUT, new MemoryLayout[] { C_INT$LAYOUT }));
/* 350 */   static final MethodHandle FormatMessageW$MH = downcallHandle("FormatMessageW", 
/*     */       
/* 352 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_INT$LAYOUT, C_POINTER$LAYOUT, C_INT$LAYOUT, C_INT$LAYOUT, C_POINTER$LAYOUT, C_INT$LAYOUT, C_POINTER$LAYOUT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 361 */   static final MethodHandle SetConsoleTextAttribute$MH = downcallHandle("SetConsoleTextAttribute", 
/*     */       
/* 363 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_SHORT$LAYOUT }));
/* 364 */   static final MethodHandle SetConsoleMode$MH = downcallHandle("SetConsoleMode", 
/* 365 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_INT$LAYOUT }));
/* 366 */   static final MethodHandle GetConsoleMode$MH = downcallHandle("GetConsoleMode", 
/*     */       
/* 368 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT }));
/*     */ 
/*     */   
/* 371 */   static final MethodHandle SetConsoleTitleW$MH = downcallHandle("SetConsoleTitleW", FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT }));
/* 372 */   static final MethodHandle SetConsoleCursorPosition$MH = downcallHandle("SetConsoleCursorPosition", 
/*     */       
/* 374 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, COORD.LAYOUT }));
/* 375 */   static final MethodHandle FillConsoleOutputCharacterW$MH = downcallHandle("FillConsoleOutputCharacterW", 
/*     */       
/* 377 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_WCHAR$LAYOUT, C_INT$LAYOUT, COORD.LAYOUT, C_POINTER$LAYOUT }));
/*     */   
/* 379 */   static final MethodHandle FillConsoleOutputAttribute$MH = downcallHandle("FillConsoleOutputAttribute", 
/*     */       
/* 381 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_SHORT$LAYOUT, C_INT$LAYOUT, COORD.LAYOUT, C_POINTER$LAYOUT }));
/*     */   
/* 383 */   static final MethodHandle WriteConsoleW$MH = downcallHandle("WriteConsoleW", 
/*     */       
/* 385 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT, C_INT$LAYOUT, C_POINTER$LAYOUT, C_POINTER$LAYOUT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 393 */   static final MethodHandle ReadConsoleInputW$MH = downcallHandle("ReadConsoleInputW", 
/*     */       
/* 395 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT, C_INT$LAYOUT, C_POINTER$LAYOUT }));
/*     */   
/* 397 */   static final MethodHandle PeekConsoleInputW$MH = downcallHandle("PeekConsoleInputW", 
/*     */       
/* 399 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT, C_INT$LAYOUT, C_POINTER$LAYOUT }));
/*     */ 
/*     */   
/* 402 */   static final MethodHandle GetConsoleScreenBufferInfo$MH = downcallHandle("GetConsoleScreenBufferInfo", 
/*     */       
/* 404 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT }));
/*     */   
/* 406 */   static final MethodHandle ScrollConsoleScreenBufferW$MH = downcallHandle("ScrollConsoleScreenBufferW", 
/*     */       
/* 408 */       FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT, C_POINTER$LAYOUT, C_POINTER$LAYOUT, COORD.LAYOUT, C_POINTER$LAYOUT }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 416 */   static final MethodHandle GetLastError$MH = downcallHandle("GetLastError", FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[0]));
/*     */   
/* 418 */   static final MethodHandle GetFileType$MH = downcallHandle("GetFileType", FunctionDescriptor.of(C_INT$LAYOUT, new MemoryLayout[] { C_POINTER$LAYOUT }));
/*     */   
/* 420 */   static final MethodHandle _get_osfhandle$MH = downcallHandle("_get_osfhandle", FunctionDescriptor.of(C_POINTER$LAYOUT, new MemoryLayout[] { C_INT$LAYOUT }));
/*     */   
/*     */   public static final class INPUT_RECORD {
/* 423 */     static final MemoryLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_SHORT
/* 424 */           .withName("EventType"), ValueLayout.JAVA_SHORT, 
/*     */           
/* 426 */           MemoryLayout.unionLayout(new MemoryLayout[] {
/* 427 */               Kernel32.KEY_EVENT_RECORD.LAYOUT.withName("KeyEvent"), Kernel32.MOUSE_EVENT_RECORD.LAYOUT
/* 428 */               .withName("MouseEvent"), Kernel32.WINDOW_BUFFER_SIZE_RECORD.LAYOUT
/* 429 */               .withName("WindowBufferSizeEvent"), Kernel32.MENU_EVENT_RECORD.LAYOUT
/* 430 */               .withName("MenuEvent"), Kernel32.FOCUS_EVENT_RECORD.LAYOUT
/* 431 */               .withName("FocusEvent")
/* 432 */             }).withName("Event") });
/* 433 */     static final VarHandle EventType$VH = Kernel32.varHandle(LAYOUT, "EventType");
/* 434 */     static final long Event$OFFSET = Kernel32.byteOffset(LAYOUT, "Event");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public INPUT_RECORD() {
/* 439 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public INPUT_RECORD(Arena arena) {
/* 443 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public INPUT_RECORD(MemorySegment seg) {
/* 447 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public short eventType() {
/* 451 */       return EventType$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public Kernel32.KEY_EVENT_RECORD keyEvent() {
/* 455 */       return new Kernel32.KEY_EVENT_RECORD(this.seg, Event$OFFSET);
/*     */     }
/*     */     
/*     */     public Kernel32.MOUSE_EVENT_RECORD mouseEvent() {
/* 459 */       return new Kernel32.MOUSE_EVENT_RECORD(this.seg, Event$OFFSET);
/*     */     }
/*     */     
/*     */     public Kernel32.FOCUS_EVENT_RECORD focusEvent() {
/* 463 */       return new Kernel32.FOCUS_EVENT_RECORD(this.seg, Event$OFFSET);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class MENU_EVENT_RECORD
/*     */   {
/* 470 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.C_DWORD$LAYOUT.withName("dwCommandId") });
/* 471 */     static final VarHandle COMMAND_ID = Kernel32.varHandle(LAYOUT, "dwCommandId");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public MENU_EVENT_RECORD() {
/* 476 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public MENU_EVENT_RECORD(Arena arena) {
/* 480 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public MENU_EVENT_RECORD(MemorySegment seg) {
/* 484 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public int commandId() {
/* 488 */       return COMMAND_ID.get(this.seg);
/*     */     }
/*     */     
/*     */     public void commandId(int commandId) {
/* 492 */       COMMAND_ID.set(this.seg, commandId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class FOCUS_EVENT_RECORD
/*     */   {
/* 499 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.C_INT$LAYOUT.withName("bSetFocus") });
/* 500 */     static final VarHandle SET_FOCUS = Kernel32.varHandle(LAYOUT, "bSetFocus");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public FOCUS_EVENT_RECORD() {
/* 505 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public FOCUS_EVENT_RECORD(Arena arena) {
/* 509 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public FOCUS_EVENT_RECORD(MemorySegment seg) {
/* 513 */       this.seg = Objects.<MemorySegment>requireNonNull(seg);
/*     */     }
/*     */     
/*     */     public FOCUS_EVENT_RECORD(MemorySegment seg, long offset) {
/* 517 */       this.seg = ((MemorySegment)Objects.<MemorySegment>requireNonNull(seg)).asSlice(offset, LAYOUT.byteSize());
/*     */     }
/*     */     
/*     */     public boolean setFocus() {
/* 521 */       return (SET_FOCUS.get(this.seg) != 0);
/*     */     }
/*     */     
/*     */     public void setFocus(boolean setFocus) {
/* 525 */       SET_FOCUS.set(this.seg, setFocus ? 1 : 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class WINDOW_BUFFER_SIZE_RECORD
/*     */   {
/* 532 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.COORD.LAYOUT.withName("size") });
/* 533 */     static final long SIZE_OFFSET = Kernel32.byteOffset(LAYOUT, "size");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public WINDOW_BUFFER_SIZE_RECORD() {
/* 538 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public WINDOW_BUFFER_SIZE_RECORD(Arena arena) {
/* 542 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public WINDOW_BUFFER_SIZE_RECORD(MemorySegment seg) {
/* 546 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public Kernel32.COORD size() {
/* 550 */       return new Kernel32.COORD(this.seg, SIZE_OFFSET);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 554 */       return "WINDOW_BUFFER_SIZE_RECORD{size=" + String.valueOf(size()) + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class MOUSE_EVENT_RECORD
/*     */   {
/* 560 */     static final MemoryLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.COORD.LAYOUT
/* 561 */           .withName("dwMousePosition"), Kernel32.C_DWORD$LAYOUT
/* 562 */           .withName("dwButtonState"), Kernel32.C_DWORD$LAYOUT
/* 563 */           .withName("dwControlKeyState"), Kernel32.C_DWORD$LAYOUT
/* 564 */           .withName("dwEventFlags") });
/* 565 */     static final long MOUSE_POSITION_OFFSET = Kernel32.byteOffset(LAYOUT, "dwMousePosition");
/* 566 */     static final VarHandle BUTTON_STATE = Kernel32.varHandle(LAYOUT, "dwButtonState");
/* 567 */     static final VarHandle CONTROL_KEY_STATE = Kernel32.varHandle(LAYOUT, "dwControlKeyState");
/* 568 */     static final VarHandle EVENT_FLAGS = Kernel32.varHandle(LAYOUT, "dwEventFlags");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public MOUSE_EVENT_RECORD() {
/* 573 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public MOUSE_EVENT_RECORD(Arena arena) {
/* 577 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public MOUSE_EVENT_RECORD(MemorySegment seg) {
/* 581 */       this.seg = Objects.<MemorySegment>requireNonNull(seg);
/*     */     }
/*     */     
/*     */     public MOUSE_EVENT_RECORD(MemorySegment seg, long offset) {
/* 585 */       this.seg = ((MemorySegment)Objects.<MemorySegment>requireNonNull(seg)).asSlice(offset, LAYOUT.byteSize());
/*     */     }
/*     */     
/*     */     public Kernel32.COORD mousePosition() {
/* 589 */       return new Kernel32.COORD(this.seg, MOUSE_POSITION_OFFSET);
/*     */     }
/*     */     
/*     */     public int buttonState() {
/* 593 */       return BUTTON_STATE.get(this.seg);
/*     */     }
/*     */     
/*     */     public int controlKeyState() {
/* 597 */       return CONTROL_KEY_STATE.get(this.seg);
/*     */     }
/*     */     
/*     */     public int eventFlags() {
/* 601 */       return EVENT_FLAGS.get(this.seg);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 605 */       return "MOUSE_EVENT_RECORD{mousePosition=" + String.valueOf(mousePosition()) + ", buttonState=" + buttonState() + ", controlKeyState=" + 
/* 606 */         controlKeyState() + ", eventFlags=" + eventFlags() + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class KEY_EVENT_RECORD
/*     */   {
/* 612 */     static final MemoryLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { ValueLayout.JAVA_INT
/* 613 */           .withName("bKeyDown"), ValueLayout.JAVA_SHORT
/* 614 */           .withName("wRepeatCount"), ValueLayout.JAVA_SHORT
/* 615 */           .withName("wVirtualKeyCode"), ValueLayout.JAVA_SHORT
/* 616 */           .withName("wVirtualScanCode"), 
/* 617 */           MemoryLayout.unionLayout(new MemoryLayout[] {
/* 618 */               ValueLayout.JAVA_CHAR.withName("UnicodeChar"), ValueLayout.JAVA_BYTE
/* 619 */               .withName("AsciiChar")
/* 620 */             }).withName("uChar"), ValueLayout.JAVA_INT
/* 621 */           .withName("dwControlKeyState") });
/* 622 */     static final VarHandle bKeyDown$VH = Kernel32.varHandle(LAYOUT, "bKeyDown");
/* 623 */     static final VarHandle wRepeatCount$VH = Kernel32.varHandle(LAYOUT, "wRepeatCount");
/* 624 */     static final VarHandle wVirtualKeyCode$VH = Kernel32.varHandle(LAYOUT, "wVirtualKeyCode");
/* 625 */     static final VarHandle wVirtualScanCode$VH = Kernel32.varHandle(LAYOUT, "wVirtualScanCode");
/* 626 */     static final VarHandle UnicodeChar$VH = Kernel32.varHandle(LAYOUT, "uChar", "UnicodeChar");
/* 627 */     static final VarHandle AsciiChar$VH = Kernel32.varHandle(LAYOUT, "uChar", "AsciiChar");
/* 628 */     static final VarHandle dwControlKeyState$VH = Kernel32.varHandle(LAYOUT, "dwControlKeyState");
/*     */     
/*     */     final MemorySegment seg;
/*     */     
/*     */     public KEY_EVENT_RECORD() {
/* 633 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public KEY_EVENT_RECORD(Arena arena) {
/* 637 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public KEY_EVENT_RECORD(MemorySegment seg) {
/* 641 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public KEY_EVENT_RECORD(MemorySegment seg, long offset) {
/* 645 */       this.seg = ((MemorySegment)Objects.<MemorySegment>requireNonNull(seg)).asSlice(offset, LAYOUT.byteSize());
/*     */     }
/*     */     
/*     */     public boolean keyDown() {
/* 649 */       return (bKeyDown$VH.get(this.seg) != 0);
/*     */     }
/*     */     
/*     */     public int repeatCount() {
/* 653 */       return wRepeatCount$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short keyCode() {
/* 657 */       return wVirtualKeyCode$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short scanCode() {
/* 661 */       return wVirtualScanCode$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public char uchar() {
/* 665 */       return UnicodeChar$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public int controlKeyState() {
/* 669 */       return dwControlKeyState$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 673 */       return "KEY_EVENT_RECORD{keyDown=" + keyDown() + ", repeatCount=" + repeatCount() + ", keyCode=" + 
/* 674 */         keyCode() + ", scanCode=" + scanCode() + ", uchar=" + uchar() + ", controlKeyState=" + 
/*     */         
/* 676 */         controlKeyState() + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class CHAR_INFO
/*     */   {
/* 682 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] {
/* 683 */           MemoryLayout.unionLayout(new MemoryLayout[] {
/* 684 */               Kernel32.C_WCHAR$LAYOUT.withName("UnicodeChar"), Kernel32.C_CHAR$LAYOUT.withName("AsciiChar")
/* 685 */             }).withName("Char"), Kernel32.C_WORD$LAYOUT
/* 686 */           .withName("Attributes") });
/* 687 */     static final VarHandle UnicodeChar$VH = Kernel32.varHandle(LAYOUT, "Char", "UnicodeChar");
/* 688 */     static final VarHandle Attributes$VH = Kernel32.varHandle(LAYOUT, "Attributes");
/*     */     
/*     */     final MemorySegment seg;
/*     */     
/*     */     public CHAR_INFO() {
/* 693 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public CHAR_INFO(Arena arena) {
/* 697 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public CHAR_INFO(Arena arena, char c, short a) {
/* 701 */       this(arena);
/* 702 */       UnicodeChar$VH.set(this.seg, c);
/* 703 */       Attributes$VH.set(this.seg, a);
/*     */     }
/*     */     
/*     */     public CHAR_INFO(MemorySegment seg) {
/* 707 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public char unicodeChar() {
/* 711 */       return UnicodeChar$VH.get(this.seg);
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class CONSOLE_SCREEN_BUFFER_INFO {
/* 716 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.COORD.LAYOUT
/* 717 */           .withName("dwSize"), Kernel32.COORD.LAYOUT
/* 718 */           .withName("dwCursorPosition"), Kernel32.C_WORD$LAYOUT
/* 719 */           .withName("wAttributes"), Kernel32.SMALL_RECT.LAYOUT
/* 720 */           .withName("srWindow"), Kernel32.COORD.LAYOUT
/* 721 */           .withName("dwMaximumWindowSize") });
/* 722 */     static final long dwSize$OFFSET = Kernel32.byteOffset(LAYOUT, "dwSize");
/* 723 */     static final long dwCursorPosition$OFFSET = Kernel32.byteOffset(LAYOUT, "dwCursorPosition");
/* 724 */     static final VarHandle wAttributes$VH = Kernel32.varHandle(LAYOUT, "wAttributes");
/* 725 */     static final long srWindow$OFFSET = Kernel32.byteOffset(LAYOUT, "srWindow");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public CONSOLE_SCREEN_BUFFER_INFO() {
/* 730 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public CONSOLE_SCREEN_BUFFER_INFO(Arena arena) {
/* 734 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public CONSOLE_SCREEN_BUFFER_INFO(MemorySegment seg) {
/* 738 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public Kernel32.COORD size() {
/* 742 */       return new Kernel32.COORD(this.seg, dwSize$OFFSET);
/*     */     }
/*     */     
/*     */     public Kernel32.COORD cursorPosition() {
/* 746 */       return new Kernel32.COORD(this.seg, dwCursorPosition$OFFSET);
/*     */     }
/*     */     
/*     */     public short attributes() {
/* 750 */       return wAttributes$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public Kernel32.SMALL_RECT window() {
/* 754 */       return new Kernel32.SMALL_RECT(this.seg, srWindow$OFFSET);
/*     */     }
/*     */     
/*     */     public int windowWidth() {
/* 758 */       return window().width() + 1;
/*     */     }
/*     */     
/*     */     public int windowHeight() {
/* 762 */       return window().height() + 1;
/*     */     }
/*     */     
/*     */     public void attributes(short attr) {
/* 766 */       wAttributes$VH.set(this.seg, attr);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class COORD
/*     */   {
/* 773 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.C_SHORT$LAYOUT.withName("x"), Kernel32.C_SHORT$LAYOUT.withName("y") });
/* 774 */     static final VarHandle x$VH = Kernel32.varHandle(LAYOUT, "x");
/* 775 */     static final VarHandle y$VH = Kernel32.varHandle(LAYOUT, "y");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public COORD() {
/* 780 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public COORD(Arena arena) {
/* 784 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public COORD(Arena arena, short x, short y) {
/* 788 */       this(arena.allocate(LAYOUT));
/* 789 */       x(x);
/* 790 */       y(y);
/*     */     }
/*     */     
/*     */     public COORD(MemorySegment seg) {
/* 794 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public COORD(MemorySegment seg, long offset) {
/* 798 */       this.seg = ((MemorySegment)Objects.<MemorySegment>requireNonNull(seg)).asSlice(offset, LAYOUT.byteSize());
/*     */     }
/*     */     
/*     */     public short x() {
/* 802 */       return x$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public void x(short x) {
/* 806 */       x$VH.set(this.seg, x);
/*     */     }
/*     */     
/*     */     public short y() {
/* 810 */       return y$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public void y(short y) {
/* 814 */       y$VH.set(this.seg, y);
/*     */     }
/*     */     
/*     */     public COORD copy(Arena arena) {
/* 818 */       return new COORD(arena.allocate(LAYOUT).copyFrom(this.seg));
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class SMALL_RECT
/*     */   {
/* 824 */     static final GroupLayout LAYOUT = MemoryLayout.structLayout(new MemoryLayout[] { Kernel32.C_SHORT$LAYOUT
/* 825 */           .withName("Left"), Kernel32.C_SHORT$LAYOUT
/* 826 */           .withName("Top"), Kernel32.C_SHORT$LAYOUT
/* 827 */           .withName("Right"), Kernel32.C_SHORT$LAYOUT
/* 828 */           .withName("Bottom") });
/* 829 */     static final VarHandle Left$VH = Kernel32.varHandle(LAYOUT, "Left");
/* 830 */     static final VarHandle Top$VH = Kernel32.varHandle(LAYOUT, "Top");
/* 831 */     static final VarHandle Right$VH = Kernel32.varHandle(LAYOUT, "Right");
/* 832 */     static final VarHandle Bottom$VH = Kernel32.varHandle(LAYOUT, "Bottom");
/*     */     
/*     */     private final MemorySegment seg;
/*     */     
/*     */     public SMALL_RECT() {
/* 837 */       this(Arena.ofAuto());
/*     */     }
/*     */     
/*     */     public SMALL_RECT(Arena arena) {
/* 841 */       this(arena.allocate(LAYOUT));
/*     */     }
/*     */     
/*     */     public SMALL_RECT(Arena arena, SMALL_RECT rect) {
/* 845 */       this(arena);
/* 846 */       left(rect.left());
/* 847 */       right(rect.right());
/* 848 */       top(rect.top());
/* 849 */       bottom(rect.bottom());
/*     */     }
/*     */     
/*     */     public SMALL_RECT(MemorySegment seg, long offset) {
/* 853 */       this(seg.asSlice(offset, LAYOUT.byteSize()));
/*     */     }
/*     */     
/*     */     public SMALL_RECT(MemorySegment seg) {
/* 857 */       this.seg = seg;
/*     */     }
/*     */     
/*     */     public short left() {
/* 861 */       return Left$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short top() {
/* 865 */       return Top$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short right() {
/* 869 */       return Right$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short bottom() {
/* 873 */       return Bottom$VH.get(this.seg);
/*     */     }
/*     */     
/*     */     public short width() {
/* 877 */       return (short)(right() - left());
/*     */     }
/*     */     
/*     */     public short height() {
/* 881 */       return (short)(bottom() - top());
/*     */     }
/*     */     
/*     */     public void left(short l) {
/* 885 */       Left$VH.set(this.seg, l);
/*     */     }
/*     */     
/*     */     public void top(short t) {
/* 889 */       Top$VH.set(this.seg, t);
/*     */     }
/*     */     
/*     */     public void right(short r) {
/* 893 */       Right$VH.set(this.seg, r);
/*     */     }
/*     */     
/*     */     public void bottom(short b) {
/* 897 */       Bottom$VH.set(this.seg, b);
/*     */     }
/*     */     
/*     */     public SMALL_RECT copy(Arena arena) {
/* 901 */       return new SMALL_RECT(arena.allocate(LAYOUT).copyFrom(this.seg));
/*     */     }
/*     */   }
/*     */   
/*     */   static <T> T requireNonNull(T obj, String symbolName) {
/* 906 */     if (obj == null) {
/* 907 */       throw new UnsatisfiedLinkError("unresolved symbol: " + symbolName);
/*     */     }
/* 909 */     return obj;
/*     */   }
/*     */   
/*     */   static VarHandle varHandle(MemoryLayout layout, String name) {
/* 913 */     return FfmTerminalProvider.lookupVarHandle(layout, new MemoryLayout.PathElement[] {
/* 914 */           MemoryLayout.PathElement.groupElement(name) });
/*     */   }
/*     */   
/*     */   static VarHandle varHandle(MemoryLayout layout, String e1, String name) {
/* 918 */     return FfmTerminalProvider.lookupVarHandle(layout, new MemoryLayout.PathElement[] {
/*     */           
/* 920 */           MemoryLayout.PathElement.groupElement(e1), 
/* 921 */           MemoryLayout.PathElement.groupElement(name) });
/*     */   }
/*     */   
/*     */   static long byteOffset(MemoryLayout layout, String name) {
/* 925 */     return layout.byteOffset(new MemoryLayout.PathElement[] { MemoryLayout.PathElement.groupElement(name) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\ffm\Kernel32.class
 * Java compiler version: 22 (66.0)
 * JD-Core Version:       1.1.3
 */