/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StyleResolver
/*     */ {
/*  55 */   private static final Logger log = Logger.getLogger(StyleResolver.class.getName());
/*     */   
/*     */   private final Function<String, String> source;
/*     */   
/*     */   public StyleResolver(Function<String, String> source) {
/*  60 */     this.source = Objects.<Function<String, String>>requireNonNull(source);
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
/*     */   private static Integer colorRgb(String name) {
/*  76 */     name = name.toLowerCase(Locale.US);
/*     */     
/*  78 */     if (name.charAt(0) == 'x' || name.charAt(0) == '#') {
/*     */       try {
/*  80 */         return Integer.valueOf(Integer.parseInt(name.substring(1), 16));
/*  81 */       } catch (NumberFormatException e) {
/*  82 */         log.warning("Invalid hexadecimal color: " + name);
/*  83 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*  87 */     Integer color = color(name);
/*  88 */     if (color != null && color.intValue() != -1) {
/*  89 */       color = Integer.valueOf(Colors.DEFAULT_COLORS_256[color.intValue()]);
/*     */     }
/*  91 */     return color;
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
/*     */   private static Integer color(String name) {
/* 106 */     int flags = 0;
/*     */     
/* 108 */     if (name.equals("default")) {
/* 109 */       return Integer.valueOf(-1);
/*     */     }
/*     */     
/* 112 */     if (name.charAt(0) == '!') {
/* 113 */       name = name.substring(1);
/* 114 */       flags = 8;
/* 115 */     } else if (name.startsWith("bright-")) {
/* 116 */       name = name.substring(7);
/* 117 */       flags = 8;
/* 118 */     } else if (name.charAt(0) == '~') {
/* 119 */       name = name.substring(1);
/*     */       try {
/* 121 */         return Colors.rgbColor(name);
/* 122 */       } catch (IllegalArgumentException e) {
/* 123 */         log.warning("Invalid style-color name: " + name);
/* 124 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     switch (name) {
/*     */       case "black":
/*     */       case "k":
/* 131 */         return Integer.valueOf(flags + 0);
/*     */       
/*     */       case "red":
/*     */       case "r":
/* 135 */         return Integer.valueOf(flags + 1);
/*     */       
/*     */       case "green":
/*     */       case "g":
/* 139 */         return Integer.valueOf(flags + 2);
/*     */       
/*     */       case "yellow":
/*     */       case "y":
/* 143 */         return Integer.valueOf(flags + 3);
/*     */       
/*     */       case "blue":
/*     */       case "b":
/* 147 */         return Integer.valueOf(flags + 4);
/*     */       
/*     */       case "magenta":
/*     */       case "m":
/* 151 */         return Integer.valueOf(flags + 5);
/*     */       
/*     */       case "cyan":
/*     */       case "c":
/* 155 */         return Integer.valueOf(flags + 6);
/*     */       
/*     */       case "white":
/*     */       case "w":
/* 159 */         return Integer.valueOf(flags + 7);
/*     */     } 
/*     */     
/* 162 */     return null;
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
/*     */   public AttributedStyle resolve(String spec) {
/* 176 */     Objects.requireNonNull(spec);
/*     */     
/* 178 */     if (log.isLoggable(Level.FINEST)) {
/* 179 */       log.finest("Resolve: " + spec);
/*     */     }
/*     */     
/* 182 */     int i = spec.indexOf(":-");
/* 183 */     if (i != -1) {
/* 184 */       String[] parts = spec.split(":-");
/* 185 */       return resolve(parts[0].trim(), parts[1].trim());
/*     */     } 
/*     */     
/* 188 */     return apply(AttributedStyle.DEFAULT, spec);
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
/*     */   public AttributedStyle resolve(String spec, String defaultSpec) {
/* 201 */     Objects.requireNonNull(spec);
/*     */     
/* 203 */     if (log.isLoggable(Level.FINEST)) {
/* 204 */       log.finest(String.format("Resolve: %s; default: %s", new Object[] { spec, defaultSpec }));
/*     */     }
/*     */     
/* 207 */     AttributedStyle style = apply(AttributedStyle.DEFAULT, spec);
/* 208 */     if (style == AttributedStyle.DEFAULT && defaultSpec != null) {
/* 209 */       style = apply(style, defaultSpec);
/*     */     }
/* 211 */     return style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributedStyle apply(AttributedStyle style, String spec) {
/* 222 */     if (log.isLoggable(Level.FINEST)) {
/* 223 */       log.finest("Apply: " + spec);
/*     */     }
/*     */     
/* 226 */     for (String item : spec.split(",")) {
/* 227 */       item = item.trim();
/* 228 */       if (!item.isEmpty())
/*     */       {
/*     */ 
/*     */         
/* 232 */         if (item.startsWith(".")) {
/* 233 */           style = applyReference(style, item);
/* 234 */         } else if (item.contains(":")) {
/* 235 */           style = applyColor(style, item);
/* 236 */         } else if (item.matches("[0-9]+(;[0-9]+)*")) {
/* 237 */           style = applyAnsi(style, item);
/*     */         } else {
/* 239 */           style = applyNamed(style, item);
/*     */         } 
/*     */       }
/*     */     } 
/* 243 */     return style;
/*     */   }
/*     */   
/*     */   private AttributedStyle applyAnsi(AttributedStyle style, String spec) {
/* 247 */     if (log.isLoggable(Level.FINEST)) {
/* 248 */       log.finest("Apply-ansi: " + spec);
/*     */     }
/*     */     
/* 251 */     return (new AttributedStringBuilder())
/* 252 */       .style(style)
/* 253 */       .ansiAppend("\033[" + spec + "m")
/* 254 */       .style();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributedStyle applyReference(AttributedStyle style, String spec) {
/* 265 */     if (log.isLoggable(Level.FINEST)) {
/* 266 */       log.finest("Apply-reference: " + spec);
/*     */     }
/*     */     
/* 269 */     if (spec.length() == 1) {
/* 270 */       log.warning("Invalid style-reference; missing discriminator: " + spec);
/*     */     } else {
/* 272 */       String name = spec.substring(1);
/* 273 */       String resolvedSpec = this.source.apply(name);
/* 274 */       if (resolvedSpec != null) {
/* 275 */         return apply(style, resolvedSpec);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 280 */     return style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributedStyle applyNamed(AttributedStyle style, String name) {
/* 291 */     if (log.isLoggable(Level.FINEST)) {
/* 292 */       log.finest("Apply-named: " + name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 297 */     switch (name.toLowerCase(Locale.US)) {
/*     */       case "default":
/* 299 */         return AttributedStyle.DEFAULT;
/*     */       
/*     */       case "bold":
/* 302 */         return style.bold();
/*     */       
/*     */       case "faint":
/* 305 */         return style.faint();
/*     */       
/*     */       case "italic":
/* 308 */         return style.italic();
/*     */       
/*     */       case "underline":
/* 311 */         return style.underline();
/*     */       
/*     */       case "blink":
/* 314 */         return style.blink();
/*     */       
/*     */       case "inverse":
/* 317 */         return style.inverse();
/*     */       
/*     */       case "inverse-neg":
/*     */       case "inverseneg":
/* 321 */         return style.inverseNeg();
/*     */       
/*     */       case "conceal":
/* 324 */         return style.conceal();
/*     */       
/*     */       case "crossed-out":
/*     */       case "crossedout":
/* 328 */         return style.crossedOut();
/*     */       
/*     */       case "hidden":
/* 331 */         return style.hidden();
/*     */     } 
/*     */     
/* 334 */     log.warning("Unknown style: " + name);
/* 335 */     return style;
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
/*     */   private AttributedStyle applyColor(AttributedStyle style, String spec) {
/*     */     Integer color;
/* 349 */     if (log.isLoggable(Level.FINEST)) {
/* 350 */       log.finest("Apply-color: " + spec);
/*     */     }
/*     */ 
/*     */     
/* 354 */     String[] parts = spec.split(":", 2);
/* 355 */     String colorMode = parts[0].trim();
/* 356 */     String colorName = parts[1].trim();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     switch (colorMode.toLowerCase(Locale.US))
/*     */     { case "foreground":
/*     */       case "fg":
/*     */       case "f":
/* 365 */         color = color(colorName);
/* 366 */         if (color == null) {
/* 367 */           log.warning("Invalid color-name: " + colorName);
/*     */         } else {
/*     */           
/* 370 */           return (color.intValue() >= 0) ? style.foreground(color.intValue()) : style.foregroundDefault();
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 405 */         return style;case "background": case "bg": case "b": color = color(colorName); if (color == null) { log.warning("Invalid color-name: " + colorName); } else { return (color.intValue() >= 0) ? style.background(color.intValue()) : style.backgroundDefault(); }  return style;case "foreground-rgb": case "fg-rgb": case "f-rgb": color = colorRgb(colorName); if (color == null) { log.warning("Invalid color-name: " + colorName); } else { return (color.intValue() >= 0) ? style.foregroundRgb(color.intValue()) : style.foregroundDefault(); }  return style;case "background-rgb": case "bg-rgb": case "b-rgb": color = colorRgb(colorName); if (color == null) { log.warning("Invalid color-name: " + colorName); } else { return (color.intValue() >= 0) ? style.backgroundRgb(color.intValue()) : style.backgroundDefault(); }  return style; }  log.warning("Invalid color-mode: " + colorMode); return style;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\StyleResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */