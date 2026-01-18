/*     */ package org.jline.builtins;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jline.utils.StyleResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Styles
/*     */ {
/*     */   public static final String NANORC_THEME = "NANORC_THEME";
/*  41 */   protected static final List<String> ANSI_STYLES = Arrays.asList(new String[] { "blink", "bold", "conceal", "crossed-out", "crossedout", "faint", "hidden", "inverse", "inverse-neg", "inverseneg", "italic", "underline" });
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_LS_COLORS = "di=1;91:ex=1;92:ln=1;96:fi=";
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_HELP_COLORS = "ti=1;34:co=1:ar=3:op=33:de=";
/*     */ 
/*     */   
/*     */   private static final String DEFAULT_PRNT_COLORS = "th=1;34:rn=1;34:rs=,~grey15:mk=1;34:em=31:vs=32";
/*     */   
/*     */   private static final String LS_COLORS = "LS_COLORS";
/*     */   
/*     */   private static final String HELP_COLORS = "HELP_COLORS";
/*     */   
/*     */   private static final String PRNT_COLORS = "PRNT_COLORS";
/*     */   
/*     */   private static final String KEY = "([a-z]{2}|\\*\\.[a-zA-Z0-9]+)";
/*     */   
/*     */   private static final String VALUE = "(([!~#]?[a-zA-Z0-9]+[a-z0-9-;]*)?|[A-Z_]+)";
/*     */   
/*     */   private static final String VALUES = "(([!~#]?[a-zA-Z0-9]+[a-z0-9-;]*)?|[A-Z_]+)(,(([!~#]?[a-zA-Z0-9]+[a-z0-9-;]*)?|[A-Z_]+))*";
/*     */   
/*  64 */   private static final Pattern STYLE_ELEMENT_SEPARATOR = Pattern.compile(":");
/*  65 */   private static final Pattern STYLE_ELEMENT_PATTERN = Pattern.compile("([a-z]{2}|\\*\\.[a-zA-Z0-9]+)=(([!~#]?[a-zA-Z0-9]+[a-z0-9-;]*)?|[A-Z_]+)(,(([!~#]?[a-zA-Z0-9]+[a-z0-9-;]*)?|[A-Z_]+))*");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StyleResolver lsStyle() {
/*  77 */     return style("LS_COLORS", "di=1;91:ex=1;92:ln=1;96:fi=");
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
/*     */   public static StyleResolver helpStyle() {
/*  90 */     return style("HELP_COLORS", "ti=1;34:co=1:ar=3:op=33:de=");
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
/*     */   public static StyleResolver prntStyle() {
/* 103 */     return style("PRNT_COLORS", "th=1;34:rn=1;34:rs=,~grey15:mk=1;34:em=31:vs=32");
/*     */   }
/*     */   
/*     */   public static boolean isStylePattern(String style) {
/* 107 */     String[] styleElements = STYLE_ELEMENT_SEPARATOR.split(style);
/* 108 */     return Arrays.<String>stream(styleElements)
/* 109 */       .allMatch(element -> (element.isEmpty() || STYLE_ELEMENT_PATTERN.matcher(element).matches()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static StyleResolver style(String name, String defStyle) {
/* 114 */     String style = consoleOption(name);
/* 115 */     if (style == null) {
/* 116 */       style = defStyle;
/*     */     }
/* 118 */     return style(style);
/*     */   }
/*     */   
/*     */   private static ConsoleOptionGetter optionGetter() {
/*     */     try {
/* 123 */       return (ConsoleOptionGetter)Class.forName("org.jline.console.SystemRegistry")
/* 124 */         .getDeclaredMethod("get", new Class[0])
/* 125 */         .invoke(null, new Object[0]);
/* 126 */     } catch (Exception exception) {
/*     */       
/* 128 */       return null;
/*     */     } 
/*     */   }
/*     */   private static <T> T consoleOption(String name, T defVal) {
/* 132 */     T out = defVal;
/* 133 */     ConsoleOptionGetter cog = optionGetter();
/* 134 */     if (cog != null) {
/* 135 */       out = cog.consoleOption(name, defVal);
/*     */     }
/* 137 */     return out;
/*     */   }
/*     */   
/*     */   private static String consoleOption(String name) {
/* 141 */     String out = null;
/* 142 */     ConsoleOptionGetter cog = optionGetter();
/* 143 */     if (cog != null) {
/* 144 */       out = (String)cog.consoleOption(name);
/* 145 */       if (out != null && !isStylePattern(out)) {
/* 146 */         out = null;
/*     */       }
/*     */     } 
/* 149 */     if (out == null) {
/* 150 */       out = System.getenv(name);
/* 151 */       if (out != null && !isStylePattern(out)) {
/* 152 */         out = null;
/*     */       }
/*     */     } 
/* 155 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public static StyleResolver style(String style) {
/* 160 */     Map<String, String> colors = (Map<String, String>)Arrays.<String>stream(style.split(":")).collect(Collectors.toMap(s -> s.substring(0, s.indexOf('=')), s -> s.substring(s.indexOf('=') + 1)));
/* 161 */     return new StyleResolver(new StyleCompiler(colors)::getStyle);
/*     */   }
/*     */   
/*     */   public static class StyleCompiler
/*     */   {
/*     */     private static final String ANSI_VALUE = "[0-9]*(;[0-9]+){0,2}";
/*     */     private static final String COLORS_24BIT = "[#x][0-9a-fA-F]{6}";
/* 168 */     private static final List<String> COLORS_8 = Arrays.asList(new String[] { "white", "black", "red", "blue", "green", "yellow", "magenta", "cyan" });
/*     */     
/* 170 */     private static final Map<String, Integer> COLORS_NANO = new HashMap<>(); private final Map<String, String> colors;
/*     */     
/*     */     static {
/* 173 */       COLORS_NANO.put("pink", Integer.valueOf(204));
/* 174 */       COLORS_NANO.put("purple", Integer.valueOf(163));
/* 175 */       COLORS_NANO.put("mauve", Integer.valueOf(134));
/* 176 */       COLORS_NANO.put("lagoon", Integer.valueOf(38));
/* 177 */       COLORS_NANO.put("mint", Integer.valueOf(48));
/* 178 */       COLORS_NANO.put("lime", Integer.valueOf(148));
/* 179 */       COLORS_NANO.put("peach", Integer.valueOf(215));
/* 180 */       COLORS_NANO.put("orange", Integer.valueOf(208));
/* 181 */       COLORS_NANO.put("latte", Integer.valueOf(137));
/*     */     }
/*     */ 
/*     */     
/*     */     private final Map<String, String> tokenColors;
/*     */     private final boolean nanoStyle;
/*     */     
/*     */     public StyleCompiler(Map<String, String> colors) {
/* 189 */       this(colors, false);
/*     */     }
/*     */     
/*     */     public StyleCompiler(Map<String, String> colors, boolean nanoStyle) {
/* 193 */       this.colors = colors;
/* 194 */       this.nanoStyle = nanoStyle;
/* 195 */       this.tokenColors = (Map<String, String>)Styles.consoleOption("NANORC_THEME", (T)new HashMap<>());
/*     */     }
/*     */     
/*     */     public String getStyle(String reference) {
/* 199 */       String rawStyle = this.colors.get(reference);
/* 200 */       if (rawStyle == null)
/* 201 */         return null; 
/* 202 */       if (rawStyle.matches("[A-Z_]+")) {
/* 203 */         rawStyle = this.tokenColors.getOrDefault(rawStyle, "normal");
/* 204 */       } else if (!this.nanoStyle && rawStyle.matches("[0-9]*(;[0-9]+){0,2}")) {
/* 205 */         return rawStyle;
/*     */       } 
/* 207 */       StringBuilder out = new StringBuilder();
/* 208 */       boolean first = true;
/* 209 */       boolean fg = true;
/* 210 */       for (String s : rawStyle.split(",")) {
/* 211 */         if (s.trim().isEmpty()) {
/* 212 */           fg = false;
/*     */         } else {
/*     */           
/* 215 */           if (!first) {
/* 216 */             out.append(",");
/*     */           }
/* 218 */           if (Styles.ANSI_STYLES.contains(s)) {
/* 219 */             out.append(s);
/* 220 */           } else if (COLORS_8.contains(s) || COLORS_NANO
/* 221 */             .containsKey(s) || s
/* 222 */             .startsWith("light") || s
/* 223 */             .startsWith("bright") || s
/* 224 */             .startsWith("~") || s
/* 225 */             .startsWith("!") || s
/* 226 */             .matches("\\d+") || s
/* 227 */             .matches("[#x][0-9a-fA-F]{6}") || s
/* 228 */             .equals("normal") || s
/* 229 */             .equals("default")) {
/* 230 */             if (s.matches("[#x][0-9a-fA-F]{6}")) {
/* 231 */               if (fg) {
/* 232 */                 out.append("fg-rgb:");
/*     */               } else {
/* 234 */                 out.append("bg-rgb:");
/*     */               } 
/* 236 */               out.append(s);
/* 237 */             } else if (s.matches("\\d+") || COLORS_NANO.containsKey(s)) {
/* 238 */               if (fg) {
/* 239 */                 out.append("38;5;");
/*     */               } else {
/* 241 */                 out.append("48;5;");
/*     */               } 
/* 243 */               out.append(s.matches("\\d+") ? s : ((Integer)COLORS_NANO.get(s)).toString());
/*     */             } else {
/* 245 */               if (fg) {
/* 246 */                 out.append("fg:");
/*     */               } else {
/* 248 */                 out.append("bg:");
/*     */               } 
/* 250 */               if (COLORS_8.contains(s) || s.startsWith("~") || s.startsWith("!") || s.startsWith("bright-")) {
/* 251 */                 out.append(s);
/* 252 */               } else if (s.startsWith("light")) {
/* 253 */                 out.append("!").append(s.substring(5));
/* 254 */               } else if (s.startsWith("bright")) {
/* 255 */                 out.append("!").append(s.substring(6));
/*     */               } else {
/* 257 */                 out.append("default");
/*     */               } 
/*     */             } 
/* 260 */             fg = false;
/*     */           } 
/* 262 */           first = false;
/*     */         } 
/* 264 */       }  return out.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\Styles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */