/*     */ package io.netty.channel.unix;
/*     */ 
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.NoRouteToHostException;
/*     */ import java.nio.channels.AlreadyConnectedException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ConnectionPendingException;
/*     */ import java.nio.channels.NotYetConnectedException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Errors
/*     */ {
/*  50 */   public static final int ERRNO_ENOENT_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoENOENT();
/*  51 */   public static final int ERRNO_ENOTCONN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoENOTCONN();
/*  52 */   public static final int ERRNO_EBADF_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEBADF();
/*  53 */   public static final int ERRNO_EPIPE_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEPIPE();
/*  54 */   public static final int ERRNO_ECONNRESET_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoECONNRESET();
/*  55 */   public static final int ERRNO_EAGAIN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEAGAIN();
/*  56 */   public static final int ERRNO_EWOULDBLOCK_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEWOULDBLOCK();
/*  57 */   public static final int ERRNO_EINPROGRESS_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errnoEINPROGRESS();
/*  58 */   public static final int ERROR_ECONNREFUSED_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorECONNREFUSED();
/*  59 */   public static final int ERROR_EISCONN_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorEISCONN();
/*  60 */   public static final int ERROR_EALREADY_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorEALREADY();
/*  61 */   public static final int ERROR_ENETUNREACH_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorENETUNREACH();
/*  62 */   public static final int ERROR_EHOSTUNREACH_NEGATIVE = -ErrorsStaticallyReferencedJniMethods.errorEHOSTUNREACH();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final String[] ERRORS = new String[2048];
/*     */ 
/*     */   
/*     */   public static final class NativeIoException
/*     */     extends IOException
/*     */   {
/*     */     private static final long serialVersionUID = 8222160204268655526L;
/*     */     private final int expectedErr;
/*     */     private final boolean fillInStackTrace;
/*     */     
/*     */     public NativeIoException(String method, int expectedErr) {
/*  83 */       this(method, expectedErr, true);
/*     */     }
/*     */     
/*     */     public NativeIoException(String method, int expectedErr, boolean fillInStackTrace) {
/*  87 */       super(method + "(..) failed with error(" + expectedErr + "): " + Errors.errnoString(-expectedErr));
/*  88 */       this.expectedErr = expectedErr;
/*  89 */       this.fillInStackTrace = fillInStackTrace;
/*     */     }
/*     */     
/*     */     public int expectedErr() {
/*  93 */       return this.expectedErr;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized Throwable fillInStackTrace() {
/*  98 */       if (this.fillInStackTrace) {
/*  99 */         return super.fillInStackTrace();
/*     */       }
/* 101 */       return this;
/*     */     } }
/*     */   
/*     */   static final class NativeConnectException extends ConnectException {
/*     */     private static final long serialVersionUID = -5532328671712318161L;
/*     */     private final int expectedErr;
/*     */     
/*     */     NativeConnectException(String method, int expectedErr) {
/* 109 */       super(method + "(..) failed with error(" + expectedErr + "):" + Errors.errnoString(-expectedErr));
/* 110 */       this.expectedErr = expectedErr;
/*     */     }
/*     */     
/*     */     int expectedErr() {
/* 114 */       return this.expectedErr;
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 119 */     for (int i = 0; i < ERRORS.length; i++)
/*     */     {
/* 121 */       ERRORS[i] = ErrorsStaticallyReferencedJniMethods.strError(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean handleConnectErrno(String method, int err) throws IOException {
/* 126 */     if (err == ERRNO_EINPROGRESS_NEGATIVE || err == ERROR_EALREADY_NEGATIVE)
/*     */     {
/*     */       
/* 129 */       return false;
/*     */     }
/* 131 */     throw newConnectException0(method, err);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void throwConnectException(String method, int err) throws IOException {
/* 142 */     if (err == ERROR_EALREADY_NEGATIVE) {
/* 143 */       throw new ConnectionPendingException();
/*     */     }
/* 145 */     throw newConnectException0(method, err);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String errnoString(int err) {
/* 150 */     if (err < ERRORS.length - 1) {
/* 151 */       return ERRORS[err];
/*     */     }
/* 153 */     return ErrorsStaticallyReferencedJniMethods.strError(err);
/*     */   }
/*     */   
/*     */   private static IOException newConnectException0(String method, int err) {
/* 157 */     if (err == ERROR_ENETUNREACH_NEGATIVE || err == ERROR_EHOSTUNREACH_NEGATIVE) {
/* 158 */       return new NoRouteToHostException();
/*     */     }
/* 160 */     if (err == ERROR_EISCONN_NEGATIVE) {
/* 161 */       throw new AlreadyConnectedException();
/*     */     }
/* 163 */     if (err == ERRNO_ENOENT_NEGATIVE) {
/* 164 */       return new FileNotFoundException();
/*     */     }
/* 166 */     return new ConnectException(method + "(..) failed with error(" + err + "): " + errnoString(-err));
/*     */   }
/*     */   
/*     */   public static NativeIoException newConnectionResetException(String method, int errnoNegative) {
/* 170 */     NativeIoException exception = new NativeIoException(method, errnoNegative, false);
/* 171 */     exception.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/* 172 */     return exception;
/*     */   }
/*     */   
/*     */   public static NativeIoException newIOException(String method, int err) {
/* 176 */     return new NativeIoException(method, err);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int ioResult(String method, int err, NativeIoException resetCause, ClosedChannelException closedCause) throws IOException {
/* 183 */     if (err == ERRNO_EAGAIN_NEGATIVE || err == ERRNO_EWOULDBLOCK_NEGATIVE) {
/* 184 */       return 0;
/*     */     }
/* 186 */     if (err == resetCause.expectedErr()) {
/* 187 */       throw resetCause;
/*     */     }
/* 189 */     if (err == ERRNO_EBADF_NEGATIVE) {
/* 190 */       throw closedCause;
/*     */     }
/* 192 */     if (err == ERRNO_ENOTCONN_NEGATIVE) {
/* 193 */       throw new NotYetConnectedException();
/*     */     }
/* 195 */     if (err == ERRNO_ENOENT_NEGATIVE) {
/* 196 */       throw new FileNotFoundException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 201 */     throw newIOException(method, err);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ioResult(String method, int err) throws IOException {
/* 206 */     if (err == ERRNO_EAGAIN_NEGATIVE || err == ERRNO_EWOULDBLOCK_NEGATIVE) {
/* 207 */       return 0;
/*     */     }
/* 209 */     if (err == ERRNO_EBADF_NEGATIVE) {
/* 210 */       throw new ClosedChannelException();
/*     */     }
/* 212 */     if (err == ERRNO_ENOTCONN_NEGATIVE) {
/* 213 */       throw new NotYetConnectedException();
/*     */     }
/* 215 */     if (err == ERRNO_ENOENT_NEGATIVE) {
/* 216 */       throw new FileNotFoundException();
/*     */     }
/*     */     
/* 219 */     throw new NativeIoException(method, err, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channe\\unix\Errors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */