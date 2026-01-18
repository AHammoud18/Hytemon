/*     */ package org.jline.terminal.impl;
/*     */ 
/*     */ import java.io.IOError;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntConsumer;
/*     */ import org.jline.terminal.Attributes;
/*     */ import org.jline.terminal.Cursor;
/*     */ import org.jline.terminal.Size;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.terminal.spi.Pty;
/*     */ import org.jline.terminal.spi.SystemStream;
/*     */ import org.jline.terminal.spi.TerminalProvider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPosixTerminal
/*     */   extends AbstractTerminal
/*     */ {
/*     */   protected final Pty pty;
/*     */   protected final Attributes originalAttributes;
/*     */   
/*     */   public AbstractPosixTerminal(String name, String type, Pty pty) throws IOException {
/*  69 */     this(name, type, pty, (Charset)null, Terminal.SignalHandler.SIG_DFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractPosixTerminal(String name, String type, Pty pty, Charset encoding, Terminal.SignalHandler signalHandler) throws IOException {
/*  74 */     this(name, type, pty, encoding, encoding, encoding, signalHandler);
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
/*     */   public AbstractPosixTerminal(String name, String type, Pty pty, Charset encoding, Charset inputEncoding, Charset outputEncoding, Terminal.SignalHandler signalHandler) throws IOException {
/*  86 */     super(name, type, encoding, inputEncoding, outputEncoding, signalHandler);
/*  87 */     Objects.requireNonNull(pty);
/*  88 */     this.pty = pty;
/*  89 */     this.originalAttributes = this.pty.getAttr();
/*     */   }
/*     */   
/*     */   public Pty getPty() {
/*  93 */     return this.pty;
/*     */   }
/*     */   
/*     */   public Attributes getAttributes() {
/*     */     try {
/*  98 */       return this.pty.getAttr();
/*  99 */     } catch (IOException e) {
/* 100 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setAttributes(Attributes attr) {
/*     */     try {
/* 106 */       this.pty.setAttr(attr);
/* 107 */     } catch (IOException e) {
/* 108 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Size getSize() {
/*     */     try {
/* 114 */       return this.pty.getSize();
/* 115 */     } catch (IOException e) {
/* 116 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSize(Size size) {
/*     */     try {
/* 122 */       this.pty.setSize(size);
/* 123 */     } catch (IOException e) {
/* 124 */       throw new IOError(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doClose() throws IOException {
/* 129 */     super.doClose();
/* 130 */     this.pty.setAttr(this.originalAttributes);
/* 131 */     this.pty.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public Cursor getCursorPosition(IntConsumer discarded) {
/* 136 */     return CursorSupport.getCursorPosition((Terminal)this, discarded);
/*     */   }
/*     */ 
/*     */   
/*     */   public TerminalProvider getProvider() {
/* 141 */     return getPty().getProvider();
/*     */   }
/*     */ 
/*     */   
/*     */   public SystemStream getSystemStream() {
/* 146 */     return getPty().getSystemStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 151 */     return getKind() + "[name='" + this.name + '\'' + ", pty='" + this.pty + '\'' + ", type='" + this.type + '\'' + ", size='" + 
/*     */ 
/*     */ 
/*     */       
/* 155 */       getSize() + '\'' + ']';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultForegroundColor() {
/*     */     try {
/* 162 */       writer().write("\033]10;?\033\\");
/* 163 */       writer().flush();
/*     */ 
/*     */       
/* 166 */       return parseColorResponse(reader(), 10);
/* 167 */     } catch (IOException e) {
/* 168 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultBackgroundColor() {
/*     */     try {
/* 176 */       writer().write("\033]11;?\033\\");
/* 177 */       writer().flush();
/*     */ 
/*     */       
/* 180 */       return parseColorResponse(reader(), 11);
/* 181 */     } catch (IOException e) {
/* 182 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private int parseColorResponse(NonBlockingReader reader, int colorType) throws IOException {
/* 187 */     if (reader.peek(50L) < 0) {
/* 188 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 192 */     if (reader.read(10L) != 27 || reader.read(10L) != 93) {
/* 193 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 197 */     int tens = reader.read(10L);
/* 198 */     int ones = reader.read(10L);
/* 199 */     if (tens != 49 || (ones != 48 && ones != 49)) {
/* 200 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 204 */     int type = ones - 48 + 10;
/* 205 */     if (type != colorType) {
/* 206 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 210 */     if (reader.read(10L) != 59) {
/* 211 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 215 */     if (reader.read(10L) != 114 || reader.read(10L) != 103 || reader.read(10L) != 98 || reader.read(10L) != 58) {
/* 216 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 220 */     StringBuilder sb = new StringBuilder(16);
/* 221 */     List<String> rgb = new ArrayList<>();
/*     */     while (true) {
/* 223 */       int c = reader.read(10L);
/* 224 */       if (c == 7) {
/* 225 */         rgb.add(sb.toString()); break;
/*     */       } 
/* 227 */       if (c == 27) {
/* 228 */         int next = reader.read(10L);
/* 229 */         if (next == 92) {
/* 230 */           rgb.add(sb.toString());
/*     */           break;
/*     */         } 
/* 233 */         return -1;
/*     */       } 
/* 235 */       if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
/* 236 */         sb.append((char)c); continue;
/* 237 */       }  if (c == 47) {
/* 238 */         rgb.add(sb.toString());
/* 239 */         sb.setLength(0);
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if (rgb.size() != 3) {
/* 244 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 248 */     double r = Integer.parseInt(rgb.get(0), 16) / ((1 << 4 * ((String)rgb.get(0)).length()) - 1.0D);
/* 249 */     double g = Integer.parseInt(rgb.get(1), 16) / ((1 << 4 * ((String)rgb.get(1)).length()) - 1.0D);
/* 250 */     double b = Integer.parseInt(rgb.get(2), 16) / ((1 << 4 * ((String)rgb.get(2)).length()) - 1.0D);
/*     */     
/* 252 */     return (int)((Math.round(r * 255.0D) << 16L) + (Math.round(g * 255.0D) << 8L) + Math.round(b * 255.0D));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\terminal\impl\AbstractPosixTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */