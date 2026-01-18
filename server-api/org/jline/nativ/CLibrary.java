/*     */ package org.jline.nativ;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CLibrary
/*     */ {
/*     */   public static int TCSANOW;
/*     */   public static int TCSADRAIN;
/*     */   public static int TCSAFLUSH;
/*     */   public static long TIOCGWINSZ;
/*     */   public static long TIOCSWINSZ;
/*     */   
/*     */   static {
/*  24 */     if (JLineNativeLoader.initialize()) {
/*  25 */       init();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int ioctl(int paramInt, long paramLong, WinSize paramWinSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int ioctl(int paramInt, long paramLong, int[] paramArrayOfint);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int tcsetattr(int paramInt1, int paramInt2, Termios paramTermios);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int tcgetattr(int paramInt, Termios paramTermios);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native int openpty(int[] paramArrayOfint1, int[] paramArrayOfint2, byte[] paramArrayOfbyte, Termios paramTermios, WinSize paramWinSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static native String ttyname(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short getTerminalWidth(int fd) {
/*  83 */     WinSize sz = new WinSize();
/*  84 */     ioctl(fd, TIOCGWINSZ, sz);
/*  85 */     return sz.ws_col;
/*     */   }
/*     */ 
/*     */   
/*     */   public static native int isatty(int paramInt);
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   public static class WinSize
/*     */   {
/*     */     static {
/*  96 */       if (JLineNativeLoader.initialize()) {
/*  97 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public static int SIZEOF;
/*     */     
/*     */     public short ws_row;
/*     */     
/*     */     public short ws_col;
/*     */     public short ws_xpixel;
/*     */     public short ws_ypixel;
/*     */     
/*     */     public WinSize() {}
/*     */     
/*     */     public WinSize(short ws_row, short ws_col) {
/* 113 */       this.ws_row = ws_row;
/* 114 */       this.ws_col = ws_col;
/*     */     }
/*     */     
/*     */     private static native void init();
/*     */   }
/*     */   
/*     */   public static class Termios
/*     */   {
/*     */     public static int SIZEOF;
/*     */     public long c_iflag;
/*     */     public long c_oflag;
/*     */     
/*     */     static {
/* 127 */       if (JLineNativeLoader.initialize()) {
/* 128 */         init();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long c_cflag;
/*     */     
/*     */     public long c_lflag;
/*     */ 
/*     */     
/*     */     private static native void init();
/*     */     
/* 140 */     public byte[] c_cc = new byte[32];
/*     */     public long c_ispeed;
/*     */     public long c_ospeed;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\nativ\CLibrary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */