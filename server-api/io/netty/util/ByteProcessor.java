/*     */ package io.netty.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ByteProcessor
/*     */ {
/*     */   public static class IndexOfProcessor
/*     */     implements ByteProcessor
/*     */   {
/*     */     private final byte byteToFind;
/*     */     
/*     */     public IndexOfProcessor(byte byteToFind) {
/*  33 */       this.byteToFind = byteToFind;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) {
/*  38 */       return (value != this.byteToFind);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IndexNotOfProcessor
/*     */     implements ByteProcessor
/*     */   {
/*     */     private final byte byteToNotFind;
/*     */     
/*     */     public IndexNotOfProcessor(byte byteToNotFind) {
/*  49 */       this.byteToNotFind = byteToNotFind;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean process(byte value) {
/*  54 */       return (value == this.byteToNotFind);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final ByteProcessor FIND_NUL = new IndexOfProcessor((byte)0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   public static final ByteProcessor FIND_NON_NUL = new IndexNotOfProcessor((byte)0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final ByteProcessor FIND_CR = new IndexOfProcessor((byte)13);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final ByteProcessor FIND_NON_CR = new IndexNotOfProcessor((byte)13);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final ByteProcessor FIND_LF = new IndexOfProcessor((byte)10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final ByteProcessor FIND_NON_LF = new IndexNotOfProcessor((byte)10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final ByteProcessor FIND_SEMI_COLON = new IndexOfProcessor((byte)59);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final ByteProcessor FIND_COMMA = new IndexOfProcessor((byte)44);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final ByteProcessor FIND_ASCII_SPACE = new IndexOfProcessor((byte)32);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public static final ByteProcessor FIND_CRLF = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) {
/* 109 */         return (value != 13 && value != 10);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final ByteProcessor FIND_NON_CRLF = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) {
/* 119 */         return (value == 13 || value == 10);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static final ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) {
/* 129 */         return (value != 32 && value != 9);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static final ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor()
/*     */     {
/*     */       public boolean process(byte value) {
/* 139 */         return (value == 32 || value == 9);
/*     */       }
/*     */     };
/*     */   
/*     */   boolean process(byte paramByte) throws Exception;
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\ByteProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */