/*     */ package org.jline.terminal.impl.jna.win;
/*     */ 
/*     */ import com.sun.jna.LastErrorException;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Union;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.win32.StdCallLibrary;
/*     */ import com.sun.jna.win32.W32APIOptions;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ interface Kernel32
/*     */   extends StdCallLibrary
/*     */ {
/*  22 */   public static final Kernel32 INSTANCE = (Kernel32)Native.load("kernel32", Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
/*     */   
/*  24 */   public static final Pointer INVALID_HANDLE_VALUE = Pointer.createConstant(-1L);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STD_INPUT_HANDLE = -10;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STD_OUTPUT_HANDLE = -11;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STD_ERROR_HANDLE = -12;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_PROCESSED_INPUT = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_LINE_INPUT = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_ECHO_INPUT = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_WINDOW_INPUT = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_MOUSE_INPUT = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_INSERT_MODE = 32;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_QUICK_EDIT_MODE = 64;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int ENABLE_EXTENDED_FLAGS = 128;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int RIGHT_ALT_PRESSED = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LEFT_ALT_PRESSED = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int RIGHT_CTRL_PRESSED = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int LEFT_CTRL_PRESSED = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SHIFT_PRESSED = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FOREGROUND_BLUE = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FOREGROUND_GREEN = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FOREGROUND_RED = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FOREGROUND_INTENSITY = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BACKGROUND_BLUE = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BACKGROUND_GREEN = 32;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BACKGROUND_RED = 64;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int BACKGROUND_INTENSITY = 128;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FROM_LEFT_1ST_BUTTON_PRESSED = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int RIGHTMOST_BUTTON_PRESSED = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FROM_LEFT_2ND_BUTTON_PRESSED = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FROM_LEFT_3RD_BUTTON_PRESSED = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FROM_LEFT_4TH_BUTTON_PRESSED = 16;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MOUSE_MOVED = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DOUBLE_CLICK = 2;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MOUSE_WHEELED = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MOUSE_HWHEELED = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int WaitForSingleObject(Pointer paramPointer, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Pointer GetStdHandle(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void AllocConsole() throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void FreeConsole() throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Pointer GetConsoleWindow();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int GetConsoleCP();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void FillConsoleOutputCharacter(Pointer paramPointer, char paramChar, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void FillConsoleOutputAttribute(Pointer paramPointer, short paramShort, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void GetConsoleCursorInfo(Pointer paramPointer, CONSOLE_CURSOR_INFO.ByReference paramByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void GetConsoleMode(Pointer paramPointer, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void GetConsoleScreenBufferInfo(Pointer paramPointer, CONSOLE_SCREEN_BUFFER_INFO paramCONSOLE_SCREEN_BUFFER_INFO) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void GetNumberOfConsoleInputEvents(Pointer paramPointer, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ReadConsoleInput(Pointer paramPointer, INPUT_RECORD[] paramArrayOfINPUT_RECORD, int paramInt, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleCtrlHandler(Pointer paramPointer, boolean paramBoolean) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ReadConsoleOutput(Pointer paramPointer, CHAR_INFO[] paramArrayOfCHAR_INFO, COORD paramCOORD1, COORD paramCOORD2, SMALL_RECT paramSMALL_RECT) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ReadConsoleOutputA(Pointer paramPointer, CHAR_INFO[] paramArrayOfCHAR_INFO, COORD paramCOORD1, COORD paramCOORD2, SMALL_RECT paramSMALL_RECT) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ReadConsoleOutputCharacter(Pointer paramPointer, char[] paramArrayOfchar, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void ReadConsoleOutputCharacterA(Pointer paramPointer, byte[] paramArrayOfbyte, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleCursorInfo(Pointer paramPointer, CONSOLE_CURSOR_INFO paramCONSOLE_CURSOR_INFO) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleCP(int paramInt) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleOutputCP(int paramInt) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleCursorPosition(Pointer paramPointer, COORD paramCOORD) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleMode(Pointer paramPointer, int paramInt) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleScreenBufferSize(Pointer paramPointer, COORD paramCOORD) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleTextAttribute(Pointer paramPointer, short paramShort) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleTitle(String paramString) throws LastErrorException;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SetConsoleWindowInfo(Pointer paramPointer, boolean paramBoolean, SMALL_RECT paramSMALL_RECT) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void WriteConsoleW(Pointer paramPointer1, char[] paramArrayOfchar, int paramInt, IntByReference paramIntByReference, Pointer paramPointer2) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void WriteConsoleOutput(Pointer paramPointer, CHAR_INFO[] paramArrayOfCHAR_INFO, COORD paramCOORD1, COORD paramCOORD2, SMALL_RECT paramSMALL_RECT) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void WriteConsoleOutputA(Pointer paramPointer, CHAR_INFO[] paramArrayOfCHAR_INFO, COORD paramCOORD1, COORD paramCOORD2, SMALL_RECT paramSMALL_RECT) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void WriteConsoleOutputCharacter(Pointer paramPointer, char[] paramArrayOfchar, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void WriteConsoleOutputCharacterA(Pointer paramPointer, byte[] paramArrayOfbyte, int paramInt, COORD paramCOORD, IntByReference paramIntByReference) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   void ScrollConsoleScreenBuffer(Pointer paramPointer, SMALL_RECT paramSMALL_RECT1, SMALL_RECT paramSMALL_RECT2, COORD paramCOORD, CHAR_INFO paramCHAR_INFO) throws LastErrorException;
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CHAR_INFO
/*     */     extends Structure
/*     */   {
/*     */     public Kernel32.UnionChar uChar;
/*     */ 
/*     */     
/*     */     public short Attributes;
/*     */ 
/*     */ 
/*     */     
/*     */     public CHAR_INFO() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public CHAR_INFO(char c, short attr) {
/* 334 */       this.uChar = new Kernel32.UnionChar(c);
/* 335 */       this.Attributes = attr;
/*     */     }
/*     */     
/*     */     public CHAR_INFO(byte c, short attr) {
/* 339 */       this.uChar = new Kernel32.UnionChar(c);
/* 340 */       this.Attributes = attr;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static CHAR_INFO[] createArray(int size) {
/* 347 */       return (CHAR_INFO[])(new CHAR_INFO()).toArray(size);
/*     */     }
/*     */     
/* 350 */     private static String[] fieldOrder = new String[] { "uChar", "Attributes" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 354 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CONSOLE_CURSOR_INFO
/*     */     extends Structure
/*     */   {
/*     */     public int dwSize;
/*     */     public boolean bVisible;
/*     */     
/*     */     public static class ByReference
/*     */       extends CONSOLE_CURSOR_INFO
/*     */       implements Structure.ByReference {}
/*     */     
/* 368 */     private static String[] fieldOrder = new String[] { "dwSize", "bVisible" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 372 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ByReference
/*     */     extends CONSOLE_CURSOR_INFO
/*     */     implements Structure.ByReference {}
/*     */ 
/*     */   
/*     */   public static class CONSOLE_SCREEN_BUFFER_INFO
/*     */     extends Structure
/*     */   {
/*     */     public Kernel32.COORD dwSize;
/*     */     public Kernel32.COORD dwCursorPosition;
/*     */     public short wAttributes;
/*     */     public Kernel32.SMALL_RECT srWindow;
/*     */     public Kernel32.COORD dwMaximumWindowSize;
/* 390 */     private static String[] fieldOrder = new String[] { "dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 396 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */     
/*     */     public int windowWidth() {
/* 400 */       return this.srWindow.width() + 1;
/*     */     }
/*     */     
/*     */     public int windowHeight() {
/* 404 */       return this.srWindow.height() + 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class COORD
/*     */     extends Structure implements Structure.ByValue {
/*     */     public short X;
/*     */     public short Y;
/*     */     
/*     */     public COORD() {}
/*     */     
/*     */     public COORD(short X, short Y) {
/* 416 */       this.X = X;
/* 417 */       this.Y = Y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 423 */     private static String[] fieldOrder = new String[] { "X", "Y" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 427 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class INPUT_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public static final short KEY_EVENT = 1;
/*     */     
/*     */     public static final short MOUSE_EVENT = 2;
/*     */     
/*     */     public static final short WINDOW_BUFFER_SIZE_EVENT = 4;
/*     */     
/*     */     public static final short MENU_EVENT = 8;
/*     */     
/*     */     public static final short FOCUS_EVENT = 16;
/*     */     
/*     */     public short EventType;
/*     */     
/*     */     public EventUnion Event;
/*     */ 
/*     */     
/*     */     public static class EventUnion
/*     */       extends Union
/*     */     {
/*     */       public Kernel32.KEY_EVENT_RECORD KeyEvent;
/*     */       public Kernel32.MOUSE_EVENT_RECORD MouseEvent;
/*     */       public Kernel32.WINDOW_BUFFER_SIZE_RECORD WindowBufferSizeEvent;
/*     */       public Kernel32.MENU_EVENT_RECORD MenuEvent;
/*     */       public Kernel32.FOCUS_EVENT_RECORD FocusEvent;
/*     */     }
/*     */     
/*     */     public void read() {
/* 461 */       readField("EventType");
/* 462 */       switch (this.EventType) {
/*     */         case 1:
/* 464 */           this.Event.setType(Kernel32.KEY_EVENT_RECORD.class);
/*     */           break;
/*     */         case 2:
/* 467 */           this.Event.setType(Kernel32.MOUSE_EVENT_RECORD.class);
/*     */           break;
/*     */         case 4:
/* 470 */           this.Event.setType(Kernel32.WINDOW_BUFFER_SIZE_RECORD.class);
/*     */           break;
/*     */         case 8:
/* 473 */           this.Event.setType(Kernel32.MENU_EVENT_RECORD.class);
/*     */           break;
/*     */         case 16:
/* 476 */           this.Event.setType(Kernel32.MENU_EVENT_RECORD.class);
/*     */           break;
/*     */       } 
/* 479 */       super.read();
/*     */     }
/*     */     
/* 482 */     private static String[] fieldOrder = new String[] { "EventType", "Event" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 486 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class KEY_EVENT_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public boolean bKeyDown;
/*     */ 
/*     */     
/*     */     public short wRepeatCount;
/*     */ 
/*     */     
/*     */     public short wVirtualKeyCode;
/*     */     
/*     */     public short wVirtualScanCode;
/*     */     
/*     */     public Kernel32.UnionChar uChar;
/*     */     
/*     */     public int dwControlKeyState;
/*     */     
/* 509 */     private static String[] fieldOrder = new String[] { "bKeyDown", "wRepeatCount", "wVirtualKeyCode", "wVirtualScanCode", "uChar", "dwControlKeyState" };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 515 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MOUSE_EVENT_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public Kernel32.COORD dwMousePosition;
/*     */     
/*     */     public int dwButtonState;
/*     */     
/*     */     public int dwControlKeyState;
/*     */     
/*     */     public int dwEventFlags;
/*     */     
/* 531 */     private static String[] fieldOrder = new String[] { "dwMousePosition", "dwButtonState", "dwControlKeyState", "dwEventFlags" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 535 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WINDOW_BUFFER_SIZE_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public Kernel32.COORD dwSize;
/*     */     
/* 545 */     private static String[] fieldOrder = new String[] { "dwSize" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 549 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MENU_EVENT_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public int dwCommandId;
/*     */     
/* 560 */     private static String[] fieldOrder = new String[] { "dwCommandId" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 564 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FOCUS_EVENT_RECORD
/*     */     extends Structure
/*     */   {
/*     */     public boolean bSetFocus;
/*     */     
/* 574 */     private static String[] fieldOrder = new String[] { "bSetFocus" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 578 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SMALL_RECT
/*     */     extends Structure {
/*     */     public short Left;
/*     */     public short Top;
/*     */     public short Right;
/*     */     public short Bottom;
/*     */     
/*     */     public SMALL_RECT() {}
/*     */     
/*     */     public SMALL_RECT(SMALL_RECT org) {
/* 592 */       this(org.Top, org.Left, org.Bottom, org.Right);
/*     */     }
/*     */     
/*     */     public SMALL_RECT(short Top, short Left, short Bottom, short Right) {
/* 596 */       this.Top = Top;
/* 597 */       this.Left = Left;
/* 598 */       this.Bottom = Bottom;
/* 599 */       this.Right = Right;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     private static String[] fieldOrder = new String[] { "Left", "Top", "Right", "Bottom" };
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 611 */       return Arrays.asList(fieldOrder);
/*     */     }
/*     */     
/*     */     public short width() {
/* 615 */       return (short)(this.Right - this.Left);
/*     */     }
/*     */     
/*     */     public short height() {
/* 619 */       return (short)(this.Bottom - this.Top);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class UnionChar extends Union {
/*     */     public char UnicodeChar;
/*     */     
/*     */     public UnionChar(char c) {
/* 627 */       setType(char.class);
/* 628 */       this.UnicodeChar = c;
/*     */     } public byte AsciiChar;
/*     */     public UnionChar() {}
/*     */     public UnionChar(byte c) {
/* 632 */       setType(byte.class);
/* 633 */       this.AsciiChar = c;
/*     */     }
/*     */     
/*     */     public void set(char c) {
/* 637 */       setType(char.class);
/* 638 */       this.UnicodeChar = c;
/*     */     }
/*     */     
/*     */     public void set(byte c) {
/* 642 */       setType(byte.class);
/* 643 */       this.AsciiChar = c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\jna\win\Kernel32.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */