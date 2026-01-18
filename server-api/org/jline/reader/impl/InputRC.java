/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import org.jline.keymap.KeyMap;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.Macro;
/*     */ import org.jline.reader.Reference;
/*     */ import org.jline.terminal.Terminal;
/*     */ import org.jline.utils.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class InputRC
/*     */ {
/*     */   private final LineReader reader;
/*     */   
/*     */   public static void configure(LineReader reader, URL url) throws IOException {
/*  61 */     InputStream is = url.openStream(); try {
/*  62 */       configure(reader, is);
/*  63 */       if (is != null) is.close();
/*     */     
/*     */     } catch (Throwable throwable) {
/*     */       if (is != null)
/*     */         try {
/*     */           is.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         }  
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void configure(LineReader reader, InputStream is) throws IOException {
/*  77 */     InputStreamReader r = new InputStreamReader(is); try {
/*  78 */       configure(reader, r);
/*  79 */       r.close();
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         r.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void configure(LineReader reader, Reader r) throws IOException {
/*     */     BufferedReader br;
/*  95 */     if (r instanceof BufferedReader) {
/*  96 */       br = (BufferedReader)r;
/*     */     } else {
/*  98 */       br = new BufferedReader(r);
/*     */     } 
/*     */     
/* 101 */     Terminal terminal = reader.getTerminal();
/*     */     
/* 103 */     if ("dumb".equals(terminal.getType()) || "dumb-color".equals(terminal.getType())) {
/* 104 */       reader.getVariables().putIfAbsent("editing-mode", "dumb");
/*     */     } else {
/* 106 */       reader.getVariables().putIfAbsent("editing-mode", "emacs");
/*     */     } 
/*     */     
/* 109 */     reader.setKeyMap("main");
/* 110 */     (new InputRC(reader)).parse(br);
/* 111 */     if ("vi".equals(reader.getVariable("editing-mode"))) {
/* 112 */       reader.getKeyMaps().put("main", (KeyMap)reader.getKeyMaps().get("viins"));
/* 113 */     } else if ("emacs".equals(reader.getVariable("editing-mode"))) {
/* 114 */       reader.getKeyMaps().put("main", (KeyMap)reader.getKeyMaps().get("emacs"));
/* 115 */     } else if ("dumb".equals(reader.getVariable("editing-mode"))) {
/* 116 */       reader.getKeyMaps().put("main", (KeyMap)reader.getKeyMaps().get("dumb"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private InputRC(LineReader reader) {
/* 123 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parse(BufferedReader br) throws IOException, IllegalArgumentException {
/* 128 */     boolean parsing = true;
/* 129 */     List<Boolean> ifsStack = new ArrayList<>(); String line;
/* 130 */     while ((line = br.readLine()) != null) {
/*     */       try {
/* 132 */         line = line.trim();
/* 133 */         if (line.length() == 0) {
/*     */           continue;
/*     */         }
/* 136 */         if (line.charAt(0) == '#') {
/*     */           continue;
/*     */         }
/* 139 */         int i = 0;
/* 140 */         if (line.charAt(i) == '$') {
/*     */ 
/*     */           
/* 143 */           i++;
/* 144 */           while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
/* 145 */             i++;
/*     */           }
/* 147 */           int s = i;
/* 148 */           while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
/* 149 */             i++;
/*     */           }
/* 151 */           String cmd = line.substring(s, i);
/* 152 */           while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
/* 153 */             i++;
/*     */           }
/* 155 */           s = i;
/* 156 */           while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
/* 157 */             i++;
/*     */           }
/* 159 */           String args = line.substring(s, i);
/* 160 */           if ("if".equalsIgnoreCase(cmd)) {
/* 161 */             ifsStack.add(Boolean.valueOf(parsing));
/* 162 */             if (!parsing) {
/*     */               continue;
/*     */             }
/* 165 */             if (args.startsWith("term="))
/*     */               continue; 
/* 167 */             if (args.startsWith("mode=")) {
/* 168 */               String mode = (String)this.reader.getVariable("editing-mode");
/* 169 */               parsing = args.substring("mode=".length()).equalsIgnoreCase(mode); continue;
/*     */             } 
/* 171 */             parsing = args.equalsIgnoreCase(this.reader.getAppName()); continue;
/*     */           } 
/* 173 */           if ("else".equalsIgnoreCase(cmd)) {
/* 174 */             if (ifsStack.isEmpty()) {
/* 175 */               throw new IllegalArgumentException("$else found without matching $if");
/*     */             }
/* 177 */             boolean invert = true;
/* 178 */             for (Iterator<Boolean> iterator = ifsStack.iterator(); iterator.hasNext(); ) { boolean b = ((Boolean)iterator.next()).booleanValue();
/* 179 */               if (!b) {
/* 180 */                 invert = false;
/*     */                 break;
/*     */               }  }
/*     */             
/* 184 */             if (invert)
/* 185 */               parsing = !parsing;  continue;
/*     */           } 
/* 187 */           if ("endif".equalsIgnoreCase(cmd)) {
/* 188 */             if (ifsStack.isEmpty()) {
/* 189 */               throw new IllegalArgumentException("endif found without matching $if");
/*     */             }
/* 191 */             parsing = ((Boolean)ifsStack.remove(ifsStack.size() - 1)).booleanValue(); continue;
/* 192 */           }  if ("include".equalsIgnoreCase(cmd));
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 197 */         if (!parsing) {
/*     */           continue;
/*     */         }
/* 200 */         if (line.charAt(i++) == '"') {
/* 201 */           boolean esc = false;
/* 202 */           for (;; i++) {
/* 203 */             if (i >= line.length()) {
/* 204 */               throw new IllegalArgumentException("Missing closing quote on line '" + line + "'");
/*     */             }
/* 206 */             if (esc) {
/* 207 */               esc = false;
/* 208 */             } else if (line.charAt(i) == '\\') {
/* 209 */               esc = true;
/* 210 */             } else if (line.charAt(i) == '"') {
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 215 */         while (i < line.length() && line.charAt(i) != ':' && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
/* 216 */           i++;
/*     */         }
/* 218 */         String keySeq = line.substring(0, i);
/* 219 */         boolean equivalency = (i + 1 < line.length() && line.charAt(i) == ':' && line.charAt(i + 1) == '=');
/* 220 */         i++;
/* 221 */         if (equivalency) {
/* 222 */           i++;
/*     */         }
/* 224 */         if (keySeq.equalsIgnoreCase("set")) {
/*     */ 
/*     */           
/* 227 */           while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
/* 228 */             i++;
/*     */           }
/* 230 */           int s = i;
/* 231 */           while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
/* 232 */             i++;
/*     */           }
/* 234 */           String key = line.substring(s, i);
/* 235 */           while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
/* 236 */             i++;
/*     */           }
/* 238 */           s = i;
/* 239 */           while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
/* 240 */             i++;
/*     */           }
/* 242 */           String str1 = line.substring(s, i);
/* 243 */           setVar(this.reader, key, str1); continue;
/*     */         } 
/* 245 */         while (i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
/* 246 */           i++;
/*     */         }
/* 248 */         int start = i;
/* 249 */         if (i < line.length() && (line.charAt(i) == '\'' || line.charAt(i) == '"')) {
/* 250 */           char delim = line.charAt(i++);
/* 251 */           boolean esc = false;
/*     */           
/* 253 */           for (; i < line.length(); i++) {
/*     */ 
/*     */             
/* 256 */             if (esc) {
/* 257 */               esc = false;
/* 258 */             } else if (line.charAt(i) == '\\') {
/* 259 */               esc = true;
/* 260 */             } else if (line.charAt(i) == delim) {
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 265 */         for (; i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t'; i++);
/*     */         
/* 267 */         String val = line.substring(Math.min(start, line.length()), Math.min(i, line.length()));
/* 268 */         if (keySeq.charAt(0) == '"') {
/* 269 */           keySeq = translateQuoted(keySeq);
/*     */         }
/*     */         else {
/*     */           
/* 273 */           String keyName = (keySeq.lastIndexOf('-') > 0) ? keySeq.substring(keySeq.lastIndexOf('-') + 1) : keySeq;
/* 274 */           char key = getKeyFromName(keyName);
/* 275 */           keyName = keySeq.toLowerCase();
/* 276 */           keySeq = "";
/* 277 */           if (keyName.contains("meta-") || keyName.contains("m-")) {
/* 278 */             keySeq = keySeq + "\033";
/*     */           }
/* 280 */           if (keyName.contains("control-") || keyName.contains("c-") || keyName.contains("ctrl-")) {
/* 281 */             key = (char)(Character.toUpperCase(key) & 0x1F);
/*     */           }
/* 283 */           keySeq = keySeq + key;
/*     */         } 
/* 285 */         if (val.length() > 0 && (val.charAt(0) == '\'' || val.charAt(0) == '"')) {
/* 286 */           this.reader.getKeys().bind(new Macro(translateQuoted(val)), keySeq); continue;
/*     */         } 
/* 288 */         this.reader.getKeys().bind(new Reference(val), keySeq);
/*     */       
/*     */       }
/* 291 */       catch (IllegalArgumentException e) {
/* 292 */         Log.warn(new Object[] { "Unable to parse user configuration: ", e });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String translateQuoted(String keySeq) {
/* 299 */     String str = keySeq.substring(1, keySeq.length() - 1);
/* 300 */     StringBuilder sb = new StringBuilder();
/* 301 */     for (int i = 0; i < str.length(); i++) {
/* 302 */       char c = str.charAt(i);
/* 303 */       if (c == '\\') {
/* 304 */         boolean ctrl = (str.regionMatches(i, "\\C-", 0, 3) || str.regionMatches(i, "\\M-\\C-", 0, 6));
/* 305 */         boolean meta = (str.regionMatches(i, "\\M-", 0, 3) || str.regionMatches(i, "\\C-\\M-", 0, 6));
/* 306 */         i += (meta ? 3 : 0) + (ctrl ? 3 : 0) + ((!meta && !ctrl) ? 1 : 0);
/* 307 */         if (i >= str.length()) {
/*     */           break;
/*     */         }
/* 310 */         c = str.charAt(i);
/* 311 */         if (meta) {
/* 312 */           sb.append("\033");
/*     */         }
/* 314 */         if (ctrl) {
/* 315 */           c = (c == '?') ? '' : (char)(Character.toUpperCase(c) & 0x1F);
/*     */         }
/* 317 */         if (!meta && !ctrl) {
/* 318 */           int j; switch (c) {
/*     */             case 'a':
/* 320 */               c = '\007';
/*     */               break;
/*     */             case 'b':
/* 323 */               c = '\b';
/*     */               break;
/*     */             case 'd':
/* 326 */               c = '';
/*     */               break;
/*     */             case 'e':
/* 329 */               c = '\033';
/*     */               break;
/*     */             case 'f':
/* 332 */               c = '\f';
/*     */               break;
/*     */             case 'n':
/* 335 */               c = '\n';
/*     */               break;
/*     */             case 'r':
/* 338 */               c = '\r';
/*     */               break;
/*     */             case 't':
/* 341 */               c = '\t';
/*     */               break;
/*     */             case 'v':
/* 344 */               c = '\013';
/*     */               break;
/*     */             case '\\':
/* 347 */               c = '\\';
/*     */               break;
/*     */             case '0':
/*     */             case '1':
/*     */             case '2':
/*     */             case '3':
/*     */             case '4':
/*     */             case '5':
/*     */             case '6':
/*     */             case '7':
/* 357 */               c = Character.MIN_VALUE;
/* 358 */               for (j = 0; j < 3 && 
/* 359 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 362 */                 int k = Character.digit(str.charAt(i), 8);
/* 363 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 366 */                 c = (char)(c * 8 + k);
/*     */               } 
/* 368 */               c = (char)(c & 0xFF);
/*     */               break;
/*     */             case 'x':
/* 371 */               i++;
/* 372 */               c = Character.MIN_VALUE;
/* 373 */               for (j = 0; j < 2 && 
/* 374 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 377 */                 int k = Character.digit(str.charAt(i), 16);
/* 378 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 381 */                 c = (char)(c * 16 + k);
/*     */               } 
/* 383 */               c = (char)(c & 0xFF);
/*     */               break;
/*     */             case 'u':
/* 386 */               i++;
/* 387 */               c = Character.MIN_VALUE;
/* 388 */               for (j = 0; j < 4 && 
/* 389 */                 i < str.length(); j++, i++) {
/*     */ 
/*     */                 
/* 392 */                 int k = Character.digit(str.charAt(i), 16);
/* 393 */                 if (k < 0) {
/*     */                   break;
/*     */                 }
/* 396 */                 c = (char)(c * 16 + k);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */         } 
/* 401 */         sb.append(c);
/*     */       } else {
/* 403 */         sb.append(c);
/*     */       } 
/*     */     } 
/* 406 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static char getKeyFromName(String name) {
/* 410 */     if ("DEL".equalsIgnoreCase(name) || "Rubout".equalsIgnoreCase(name))
/* 411 */       return ''; 
/* 412 */     if ("ESC".equalsIgnoreCase(name) || "Escape".equalsIgnoreCase(name))
/* 413 */       return '\033'; 
/* 414 */     if ("LFD".equalsIgnoreCase(name) || "NewLine".equalsIgnoreCase(name))
/* 415 */       return '\n'; 
/* 416 */     if ("RET".equalsIgnoreCase(name) || "Return".equalsIgnoreCase(name))
/* 417 */       return '\r'; 
/* 418 */     if ("SPC".equalsIgnoreCase(name) || "Space".equalsIgnoreCase(name))
/* 419 */       return ' '; 
/* 420 */     if ("Tab".equalsIgnoreCase(name)) {
/* 421 */       return '\t';
/*     */     }
/* 423 */     return name.charAt(0);
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
/*     */   static void setVar(LineReader reader, String key, String val) {
/* 438 */     if ("keymap".equalsIgnoreCase(key)) {
/* 439 */       reader.setKeyMap(val);
/*     */       
/*     */       return;
/*     */     } 
/* 443 */     for (LineReader.Option option : LineReader.Option.values()) {
/* 444 */       if (option.name().toLowerCase(Locale.ENGLISH).replace('_', '-').equals(val)) {
/* 445 */         if ("on".equalsIgnoreCase(val)) {
/* 446 */           reader.setOpt(option);
/* 447 */         } else if ("off".equalsIgnoreCase(val)) {
/* 448 */           reader.unsetOpt(option);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 454 */     reader.setVariable(key, val);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\InputRC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */