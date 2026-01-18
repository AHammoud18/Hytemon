/*     */ package io.netty.handler.codec.http3;
/*     */ 
/*     */ import io.netty.handler.codec.Headers;
/*     */ import io.netty.util.AsciiString;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Http3Headers
/*     */   extends Headers<CharSequence, CharSequence, Http3Headers>
/*     */ {
/*     */   Iterator<Map.Entry<CharSequence, CharSequence>> iterator();
/*     */   
/*     */   Iterator<CharSequence> valueIterator(CharSequence paramCharSequence);
/*     */   
/*     */   Http3Headers method(CharSequence paramCharSequence);
/*     */   
/*     */   Http3Headers scheme(CharSequence paramCharSequence);
/*     */   
/*     */   Http3Headers authority(CharSequence paramCharSequence);
/*     */   
/*     */   Http3Headers path(CharSequence paramCharSequence);
/*     */   
/*     */   Http3Headers status(CharSequence paramCharSequence);
/*     */   
/*     */   public enum PseudoHeaderName
/*     */   {
/*  34 */     METHOD(":method", true, 1),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     SCHEME(":scheme", true, 2),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     AUTHORITY(":authority", true, 4),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  49 */     PATH(":path", true, 8),
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     STATUS(":status", false, 16),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     PROTOCOL(":protocol", true, 32);
/*     */     
/*     */     private static final char PSEUDO_HEADER_PREFIX = ':';
/*     */     
/*     */     private static final byte PSEUDO_HEADER_PREFIX_BYTE = 58;
/*     */     
/*     */     private final AsciiString value;
/*     */     private final boolean requestOnly;
/*     */     private final int flag;
/*  74 */     private static final CharSequenceMap<PseudoHeaderName> PSEUDO_HEADERS = new CharSequenceMap<>();
/*     */     
/*     */     static {
/*  77 */       for (PseudoHeaderName pseudoHeader : values()) {
/*  78 */         PSEUDO_HEADERS.add(pseudoHeader.value(), pseudoHeader);
/*     */       }
/*     */     }
/*     */     
/*     */     PseudoHeaderName(String value, boolean requestOnly, int flag) {
/*  83 */       this.value = AsciiString.cached(value);
/*  84 */       this.requestOnly = requestOnly;
/*  85 */       this.flag = flag;
/*     */     }
/*     */ 
/*     */     
/*     */     public AsciiString value() {
/*  90 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static boolean hasPseudoHeaderFormat(CharSequence headerName) {
/* 100 */       if (headerName instanceof AsciiString) {
/* 101 */         AsciiString asciiHeaderName = (AsciiString)headerName;
/* 102 */         return (asciiHeaderName.length() > 0 && asciiHeaderName.byteAt(0) == 58);
/*     */       } 
/* 104 */       return (headerName.length() > 0 && headerName.charAt(0) == ':');
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static boolean isPseudoHeader(CharSequence name) {
/* 115 */       return PSEUDO_HEADERS.contains(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static PseudoHeaderName getPseudoHeader(CharSequence name) {
/* 126 */       return (PseudoHeaderName)PSEUDO_HEADERS.get(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRequestOnly() {
/* 135 */       return this.requestOnly;
/*     */     }
/*     */     
/*     */     public int getFlag() {
/* 139 */       return this.flag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Http3Headers protocol(CharSequence value) {
/* 209 */     set(PseudoHeaderName.PROTOCOL.value(), value);
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   CharSequence method();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   CharSequence scheme();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   CharSequence authority();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   CharSequence path();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   CharSequence status();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default CharSequence protocol() {
/* 263 */     return (CharSequence)get(PseudoHeaderName.PROTOCOL.value());
/*     */   }
/*     */   
/*     */   boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http3\Http3Headers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */