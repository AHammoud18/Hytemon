/*     */ package org.jline.keymap;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Objects;
/*     */ import org.jline.reader.EndOfFileException;
/*     */ import org.jline.utils.ClosedException;
/*     */ import org.jline.utils.NonBlockingReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BindingReader
/*     */ {
/*     */   protected final NonBlockingReader reader;
/*  43 */   protected final StringBuilder opBuffer = new StringBuilder();
/*     */   
/*  45 */   protected final Deque<Integer> pushBackChar = new ArrayDeque<>();
/*     */ 
/*     */ 
/*     */   
/*     */   protected String lastBinding;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BindingReader(NonBlockingReader reader) {
/*  55 */     this.reader = reader;
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
/*     */   public <T> T readBinding(KeyMap<T> keys) {
/*  83 */     return readBinding(keys, null, true);
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
/*     */   public <T> T readBinding(KeyMap<T> keys, KeyMap<T> local) {
/*  98 */     return readBinding(keys, local, true);
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
/*     */   public <T> T readBinding(KeyMap<T> keys, KeyMap<T> local, boolean block) {
/* 115 */     this.lastBinding = null;
/* 116 */     T o = null;
/* 117 */     int[] remaining = new int[1];
/* 118 */     boolean hasRead = false;
/*     */     while (true) {
/* 120 */       if (local != null) {
/* 121 */         o = local.getBound(this.opBuffer, remaining);
/*     */       }
/* 123 */       if (o == null && (local == null || remaining[0] >= 0)) {
/* 124 */         o = keys.getBound(this.opBuffer, remaining);
/*     */       }
/*     */       
/* 127 */       if (o != null) {
/* 128 */         if (remaining[0] >= 0) {
/* 129 */           runMacro(this.opBuffer.substring(this.opBuffer.length() - remaining[0]));
/* 130 */           this.opBuffer.setLength(this.opBuffer.length() - remaining[0]);
/*     */         } else {
/* 132 */           long ambiguousTimeout = keys.getAmbiguousTimeout();
/* 133 */           if (ambiguousTimeout > 0L && peekCharacter(ambiguousTimeout) != -2) {
/* 134 */             o = null;
/*     */           }
/*     */         } 
/* 137 */         if (o != null) {
/* 138 */           this.lastBinding = this.opBuffer.toString();
/* 139 */           this.opBuffer.setLength(0);
/* 140 */           return o;
/*     */         }
/*     */       
/* 143 */       } else if (remaining[0] > 0) {
/* 144 */         int cp = this.opBuffer.codePointAt(0);
/* 145 */         String rem = this.opBuffer.substring(Character.charCount(cp));
/* 146 */         this.lastBinding = this.opBuffer.substring(0, Character.charCount(cp));
/*     */         
/* 148 */         o = (cp >= 128) ? keys.getUnicode() : keys.getNomatch();
/* 149 */         this.opBuffer.setLength(0);
/* 150 */         this.opBuffer.append(rem);
/* 151 */         if (o != null) {
/* 152 */           return o;
/*     */         }
/*     */       } 
/*     */       
/* 156 */       if (!block && hasRead) {
/*     */         break;
/*     */       }
/* 159 */       int c = readCharacter();
/* 160 */       if (c == -1) {
/* 161 */         return null;
/*     */       }
/* 163 */       this.opBuffer.appendCodePoint(c);
/* 164 */       hasRead = true;
/*     */     } 
/* 166 */     return null;
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
/*     */   public String readStringUntil(String sequence) {
/* 181 */     StringBuilder sb = new StringBuilder();
/* 182 */     if (!this.pushBackChar.isEmpty()) {
/* 183 */       Objects.requireNonNull(sb); this.pushBackChar.forEach(sb::appendCodePoint);
/*     */     } 
/*     */     try {
/* 186 */       char[] buf = new char[64];
/*     */       while (true) {
/* 188 */         int idx = sb.indexOf(sequence, Math.max(0, sb.length() - buf.length - sequence.length()));
/* 189 */         if (idx >= 0) {
/* 190 */           String rem = sb.substring(idx + sequence.length());
/* 191 */           runMacro(rem);
/* 192 */           return sb.substring(0, idx);
/*     */         } 
/* 194 */         int l = this.reader.readBuffered(buf);
/* 195 */         if (l < 0) {
/* 196 */           throw new ClosedException();
/*     */         }
/* 198 */         sb.append(buf, 0, l);
/*     */       } 
/* 200 */     } catch (ClosedException e) {
/* 201 */       throw new EndOfFileException(e);
/* 202 */     } catch (IOException e) {
/* 203 */       throw new IOError(e);
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
/*     */   public int readCharacter() {
/* 219 */     if (!this.pushBackChar.isEmpty()) {
/* 220 */       return ((Integer)this.pushBackChar.pop()).intValue();
/*     */     }
/*     */     try {
/* 223 */       int c = -2;
/* 224 */       int s = 0;
/* 225 */       while (c == -2) {
/* 226 */         c = this.reader.read(100L);
/* 227 */         if (c >= 0 && Character.isHighSurrogate((char)c)) {
/* 228 */           s = c;
/* 229 */           c = -2;
/*     */         } 
/*     */       } 
/* 232 */       return (s != 0) ? Character.toCodePoint((char)s, (char)c) : c;
/* 233 */     } catch (ClosedException e) {
/* 234 */       throw new EndOfFileException(e);
/* 235 */     } catch (IOException e) {
/* 236 */       throw new IOError(e);
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
/*     */   public int readCharacterBuffered() {
/*     */     try {
/* 253 */       if (this.pushBackChar.isEmpty()) {
/* 254 */         char[] buf = new char[32];
/* 255 */         int l = this.reader.readBuffered(buf);
/* 256 */         if (l <= 0) {
/* 257 */           return -1;
/*     */         }
/* 259 */         int s = 0;
/* 260 */         for (int i = 0; i < l; ) {
/* 261 */           int c = buf[i++];
/* 262 */           if (Character.isHighSurrogate((char)c)) {
/* 263 */             s = c;
/* 264 */             if (i < l) {
/* 265 */               c = buf[i++];
/* 266 */               this.pushBackChar.addLast(Integer.valueOf(Character.toCodePoint((char)s, (char)c)));
/*     */               continue;
/*     */             } 
/*     */             break;
/*     */           } 
/* 271 */           s = 0;
/* 272 */           this.pushBackChar.addLast(Integer.valueOf(c));
/*     */         } 
/*     */         
/* 275 */         if (s != 0) {
/* 276 */           int c = this.reader.read();
/* 277 */           if (c >= 0) {
/* 278 */             this.pushBackChar.addLast(Integer.valueOf(Character.toCodePoint((char)s, (char)c)));
/*     */           } else {
/* 280 */             return -1;
/*     */           } 
/*     */         } 
/*     */       } 
/* 284 */       return ((Integer)this.pushBackChar.pop()).intValue();
/* 285 */     } catch (ClosedException e) {
/* 286 */       throw new EndOfFileException(e);
/* 287 */     } catch (IOException e) {
/* 288 */       throw new IOError(e);
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
/*     */   public int peekCharacter(long timeout) {
/* 304 */     if (!this.pushBackChar.isEmpty()) {
/* 305 */       return ((Integer)this.pushBackChar.peek()).intValue();
/*     */     }
/*     */     try {
/* 308 */       return this.reader.peek(timeout);
/* 309 */     } catch (IOException e) {
/* 310 */       throw new IOError(e);
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
/*     */   public void runMacro(String macro) {
/* 324 */     Objects.requireNonNull(this.pushBackChar); macro.codePoints().forEachOrdered(this.pushBackChar::addLast);
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
/*     */   public String getCurrentBuffer() {
/* 337 */     return this.opBuffer.toString();
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
/*     */   public String getLastBinding() {
/* 350 */     return this.lastBinding;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\keymap\BindingReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */