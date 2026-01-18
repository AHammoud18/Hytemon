/*     */ package com.hypixel.hytale.server.core.asset.util;
/*     */ 
/*     */ import com.hypixel.hytale.codec.util.RawJsonReader;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ColorAlpha;
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.Objects;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorParseUtil
/*     */ {
/*  21 */   public static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^\\s*#([0-9a-fA-F]{3}){1,2}\\s*$");
/*  22 */   public static final Pattern HEX_ALPHA_COLOR_PATTERN = Pattern.compile("^\\s*#([0-9a-fA-F]{4}){1,2}\\s*$");
/*  23 */   public static final Pattern RGB_COLOR_PATTERN = Pattern.compile("^\\s*rgb\\((\\s*[0-9]{1,3}\\s*,){2}\\s*[0-9]{1,3}\\s*\\)\\s*$");
/*  24 */   public static final Pattern RGBA_COLOR_PATTERN = Pattern.compile("^\\s*rgba\\((\\s*[0-9]{1,3}\\s*,){3}\\s*[0,1](.[0-9]*)?\\s*\\)\\s*$");
/*  25 */   public static final Pattern RGBA_HEX_COLOR_PATTERN = Pattern.compile("^\\s*rgba\\(\\s*#([0-9a-fA-F]{3}){1,2}\\s*,\\s*[0,1](.[0-9]*)?\\s*\\)\\s*$");
/*     */   
/*     */   @Nullable
/*     */   public static ColorAlpha readColorAlpha(@Nonnull RawJsonReader reader) throws IOException {
/*  29 */     reader.consumeWhiteSpace();
/*  30 */     switch (reader.peek()) { case 35: case 114:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  37 */       null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ColorAlpha parseColorAlpha(@Nonnull String stringValue) {
/*  44 */     if (HEX_ALPHA_COLOR_PATTERN.matcher(stringValue).matches()) {
/*  45 */       return hexStringToColorAlpha(stringValue);
/*     */     }
/*     */ 
/*     */     
/*  49 */     if (RGBA_HEX_COLOR_PATTERN.matcher(stringValue).matches()) {
/*  50 */       return rgbaHexStringToColor(stringValue);
/*     */     }
/*     */ 
/*     */     
/*  54 */     if (RGBA_COLOR_PATTERN.matcher(stringValue).matches()) {
/*  55 */       return rgbaDecimalStringToColor(stringValue);
/*     */     }
/*     */     
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Color readColor(@Nonnull RawJsonReader reader) throws IOException {
/*  63 */     reader.consumeWhiteSpace();
/*  64 */     switch (reader.peek()) { case 35: case 114:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Color parseColor(@Nonnull String stringValue) {
/*  76 */     if (HEX_COLOR_PATTERN.matcher(stringValue).matches()) {
/*  77 */       return hexStringToColor(stringValue);
/*     */     }
/*     */ 
/*     */     
/*  81 */     if (RGB_COLOR_PATTERN.matcher(stringValue).matches()) {
/*  82 */       return rgbStringToColor(stringValue);
/*     */     }
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Color readHexStringToColor(@Nonnull RawJsonReader reader) throws IOException {
/*  90 */     int rgba = readHexAlphaStringToRGBAInt(reader);
/*  91 */     return new Color((byte)(rgba >> 24 & 0xFF), (byte)(rgba >> 16 & 0xFF), (byte)(rgba >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Color hexStringToColor(String color) {
/* 100 */     int rgba = hexAlphaStringToRGBAInt(color);
/* 101 */     return new Color((byte)(rgba >> 24 & 0xFF), (byte)(rgba >> 16 & 0xFF), (byte)(rgba >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readHexStringToColorAlpha(@Nonnull RawJsonReader reader) throws IOException {
/* 110 */     int rgba = readHexAlphaStringToRGBAInt(reader);
/* 111 */     return new ColorAlpha((byte)(rgba & 0xFF), (byte)(rgba >> 24 & 0xFF), (byte)(rgba >> 16 & 0xFF), (byte)(rgba >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha hexStringToColorAlpha(String color) {
/* 121 */     int rgba = hexAlphaStringToRGBAInt(color);
/* 122 */     return new ColorAlpha((byte)(rgba & 0xFF), (byte)(rgba >> 24 & 0xFF), (byte)(rgba >> 16 & 0xFF), (byte)(rgba >> 8 & 0xFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int readHexAlphaStringToRGBAInt(@Nonnull RawJsonReader reader) throws IOException {
/* 131 */     reader.consumeWhiteSpace();
/* 132 */     reader.expect('#');
/*     */ 
/*     */     
/* 135 */     reader.mark();
/*     */     
/*     */     try {
/* 138 */       int red, green, blue, alpha, i, value = reader.readIntValue(16);
/* 139 */       int size = reader.getMarkDistance();
/* 140 */       switch (size) {
/*     */         case 3:
/* 142 */           value <<= 4;
/* 143 */           value |= 0xF;
/*     */         
/*     */         case 4:
/* 146 */           red = value >> 12 & 0xF;
/* 147 */           green = value >> 8 & 0xF;
/* 148 */           blue = value >> 4 & 0xF;
/* 149 */           alpha = value & 0xF;
/* 150 */           i = red << 28 | red << 24 | green << 20 | green << 16 | blue << 12 | blue << 8 | alpha << 4 | alpha; return i;
/*     */ 
/*     */ 
/*     */         
/*     */         case 6:
/* 155 */           i = value << 8 | 0xFF; return i;
/*     */         case 8:
/* 157 */           i = value; return i;
/*     */       } 
/* 159 */       throw new IllegalArgumentException("Invalid hex color size: " + size);
/*     */     } finally {
/*     */       
/* 162 */       reader.unmark();
/* 163 */       reader.consumeWhiteSpace();
/*     */     } 
/*     */   }
/*     */   public static int hexAlphaStringToRGBAInt(String color) {
/*     */     int red, green, blue, alpha;
/* 168 */     Objects.requireNonNull(color, "Color must not be null");
/* 169 */     color = color.trim();
/* 170 */     if (color.isEmpty() || color.charAt(0) != '#') throw new IllegalArgumentException("Hex color must start with '#'"); 
/* 171 */     color = color.substring(1);
/*     */ 
/*     */     
/* 174 */     int value = (int)Long.parseLong(color, 16);
/* 175 */     switch (color.length()) {
/*     */       case 3:
/* 177 */         value <<= 4;
/* 178 */         value |= 0xF;
/*     */       
/*     */       case 4:
/* 181 */         red = value >> 12 & 0xF;
/* 182 */         green = value >> 8 & 0xF;
/* 183 */         blue = value >> 4 & 0xF;
/* 184 */         alpha = value & 0xF;
/* 185 */         return red << 28 | red << 24 | green << 20 | green << 16 | blue << 12 | blue << 8 | alpha << 4 | alpha;
/*     */ 
/*     */ 
/*     */       
/*     */       case 6:
/* 190 */         return value << 8 | 0xFF;
/*     */       case 8:
/* 192 */         return value;
/*     */     } 
/* 194 */     throw new IllegalArgumentException("Invalid hex color format: '" + color + "'");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readHexStringToRGBInt(@Nonnull RawJsonReader reader) throws IOException {
/* 199 */     return readHexAlphaStringToRGBAInt(reader) >>> 8;
/*     */   }
/*     */   
/*     */   public static int hexStringToRGBInt(String color) {
/* 203 */     return hexAlphaStringToRGBAInt(color) >>> 8;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String colorToHexString(@Nullable Color color) {
/* 208 */     return (color == null) ? "#FFFFFF" : toHexString(color.red, color.green, color.blue);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String colorToHexAlphaString(@Nullable ColorAlpha color) {
/* 213 */     return (color == null) ? "#FFFFFFFF" : toHexAlphaString(color.red, color.green, color.blue, color.alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Color readRgbStringToColor(@Nonnull RawJsonReader reader) throws IOException {
/* 219 */     reader.consumeWhiteSpace();
/* 220 */     reader.expect("rgb(", 0);
/* 221 */     reader.consumeWhiteSpace();
/* 222 */     byte red = reader.readByteValue();
/* 223 */     reader.consumeWhiteSpace();
/* 224 */     reader.expect(',');
/* 225 */     reader.consumeWhiteSpace();
/* 226 */     byte green = reader.readByteValue();
/* 227 */     reader.consumeWhiteSpace();
/* 228 */     reader.expect(',');
/* 229 */     reader.consumeWhiteSpace();
/* 230 */     byte blue = reader.readByteValue();
/* 231 */     reader.consumeWhiteSpace();
/* 232 */     reader.expect(')');
/* 233 */     reader.consumeWhiteSpace();
/*     */     
/* 235 */     return new Color(red, green, blue);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Color rgbStringToColor(String color) {
/* 241 */     Objects.requireNonNull(color, "Color must not be null");
/* 242 */     color = color.trim();
/* 243 */     if (!color.startsWith("rgb(") || color.charAt(color.length() - 1) != ')') {
/* 244 */       throw new IllegalArgumentException("Color must start with 'rgb(' and end with ')'");
/*     */     }
/* 246 */     color = color.substring(4, color.length() - 1);
/*     */     
/* 248 */     String[] channels = color.split(",");
/* 249 */     int channelLength = channels.length;
/*     */     
/* 251 */     if (channelLength != 3) throw new IllegalArgumentException("rgb() but contain all 3 channels; r, g and b");
/*     */     
/* 253 */     byte red = (byte)Integer.parseInt(channels[0].trim());
/* 254 */     byte green = (byte)Integer.parseInt(channels[1].trim());
/* 255 */     byte blue = (byte)Integer.parseInt(channels[2].trim());
/*     */     
/* 257 */     return new Color(red, green, blue);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readRgbaStringToColorAlpha(@Nonnull RawJsonReader reader) throws IOException {
/* 262 */     reader.consumeWhiteSpace();
/* 263 */     reader.expect("rgba(", 0);
/* 264 */     reader.consumeWhiteSpace();
/* 265 */     if (reader.peek() == 35)
/*     */     {
/* 267 */       return readRgbaHexStringToColor(reader, false);
/*     */     }
/*     */     
/* 270 */     return readRgbaDecimalStringToColor(reader, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readRgbaDecimalStringToColor(@Nonnull RawJsonReader reader) throws IOException {
/* 277 */     return readRgbaDecimalStringToColor(reader, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readRgbaDecimalStringToColor(@Nonnull RawJsonReader reader, boolean readStart) throws IOException {
/* 282 */     if (readStart) {
/* 283 */       reader.consumeWhiteSpace();
/* 284 */       reader.expect("rgba(", 0);
/* 285 */       reader.consumeWhiteSpace();
/*     */     } 
/* 287 */     byte red = reader.readByteValue();
/* 288 */     reader.consumeWhiteSpace();
/* 289 */     reader.expect(',');
/* 290 */     reader.consumeWhiteSpace();
/* 291 */     byte green = reader.readByteValue();
/* 292 */     reader.consumeWhiteSpace();
/* 293 */     reader.expect(',');
/* 294 */     reader.consumeWhiteSpace();
/* 295 */     byte blue = reader.readByteValue();
/* 296 */     reader.consumeWhiteSpace();
/* 297 */     reader.expect(',');
/* 298 */     reader.consumeWhiteSpace();
/* 299 */     byte alpha = (byte)(int)MathUtil.clamp(reader.readFloatValue() * 255.0F, 0.0F, 255.0F);
/* 300 */     reader.consumeWhiteSpace();
/* 301 */     reader.expect(')');
/* 302 */     reader.consumeWhiteSpace();
/*     */     
/* 304 */     return new ColorAlpha(alpha, red, green, blue);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha rgbaDecimalStringToColor(String color) {
/* 310 */     Objects.requireNonNull(color, "Color must not be null");
/* 311 */     color = color.trim();
/* 312 */     if (!color.startsWith("rgba(") || color.charAt(color.length() - 1) != ')') {
/* 313 */       throw new IllegalArgumentException("Color must start with 'rgba(' and end with ')'");
/*     */     }
/* 315 */     color = color.substring(5, color.length() - 1);
/*     */     
/* 317 */     String[] channels = color.split(",");
/* 318 */     int channelLength = channels.length;
/*     */     
/* 320 */     if (channelLength != 4) throw new IllegalArgumentException("rgba() but contain all 4 channels; r, g, b and a");
/*     */     
/* 322 */     byte red = (byte)MathUtil.clamp(Integer.parseInt(channels[0].trim()), 0, 255);
/* 323 */     byte green = (byte)MathUtil.clamp(Integer.parseInt(channels[1].trim()), 0, 255);
/* 324 */     byte blue = (byte)MathUtil.clamp(Integer.parseInt(channels[2].trim()), 0, 255);
/* 325 */     byte alpha = (byte)(int)MathUtil.clamp(Float.parseFloat(channels[3]) * 255.0F, 0.0F, 255.0F);
/*     */     
/* 327 */     return new ColorAlpha(alpha, red, green, blue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readRgbaHexStringToColor(@Nonnull RawJsonReader reader) throws IOException {
/* 334 */     return readRgbaHexStringToColor(reader, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha readRgbaHexStringToColor(@Nonnull RawJsonReader reader, boolean readStart) throws IOException {
/* 339 */     if (readStart) {
/* 340 */       reader.consumeWhiteSpace();
/* 341 */       reader.expect("rgba(", 0);
/* 342 */       reader.consumeWhiteSpace();
/*     */     } 
/* 344 */     long val = readHexAlphaStringToRGBAInt(reader);
/* 345 */     reader.consumeWhiteSpace();
/* 346 */     reader.expect(',');
/* 347 */     reader.consumeWhiteSpace();
/* 348 */     byte alpha = (byte)(int)MathUtil.clamp(reader.readFloatValue() * 255.0F, 0.0F, 255.0F);
/* 349 */     reader.consumeWhiteSpace();
/* 350 */     reader.expect(')');
/* 351 */     reader.consumeWhiteSpace();
/*     */     
/* 353 */     return new ColorAlpha(alpha, (byte)(int)(val >> 24L & 0xFFL), (byte)(int)(val >> 16L & 0xFFL), (byte)(int)(val >> 8L & 0xFFL));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ColorAlpha rgbaHexStringToColor(String color) {
/* 365 */     Objects.requireNonNull(color, "Color must not be null");
/* 366 */     color = color.trim();
/* 367 */     if (!color.startsWith("rgba(") || color.charAt(color.length() - 1) != ')') {
/* 368 */       throw new IllegalArgumentException("Color must start with 'rgba(' and end with ')'");
/*     */     }
/* 370 */     color = color.substring(5, color.length() - 1);
/*     */     
/* 372 */     String[] channels = color.split(",");
/* 373 */     int channelLength = channels.length;
/*     */     
/* 375 */     if (channelLength != 2) throw new IllegalArgumentException("rgba() but contain both #rgb and a");
/*     */     
/* 377 */     long val = hexAlphaStringToRGBAInt(channels[0].trim());
/* 378 */     byte alpha = (byte)(int)MathUtil.clamp(Float.parseFloat(channels[1]) * 255.0F, 0.0F, 255.0F);
/*     */     
/* 380 */     return new ColorAlpha(alpha, (byte)(int)(val >> 24L & 0xFFL), (byte)(int)(val >> 16L & 0xFFL), (byte)(int)(val >> 8L & 0xFFL));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static String colorToHex(@Nullable Color color) {
/* 391 */     if (color == null) return "#FFFFFF"; 
/* 392 */     int argb = color.getRGB();
/* 393 */     int rgb = argb & 0xFFFFFF;
/* 394 */     return toHexString(rgb);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String colorToHexAlpha(@Nullable Color color) {
/* 399 */     if (color == null) return "#FFFFFFFF"; 
/* 400 */     int argb = color.getRGB();
/* 401 */     int alpha = argb >> 24 & 0xFF;
/* 402 */     int rgb = argb & 0xFFFFFF;
/* 403 */     int rgba = rgb << 8 | alpha;
/* 404 */     return toHexAlphaString(rgba);
/*     */   }
/*     */   
/*     */   public static int colorToARGBInt(@Nullable Color color) {
/* 408 */     if (color == null) return -1; 
/* 409 */     return 0xFF000000 | (color.red & 0xFF) << 16 | (color.green & 0xFF) << 8 | color.blue & 0xFF;
/*     */   }
/*     */   
/*     */   public static void hexStringToColorLightDirect(@Nonnull ColorLight colorLight, @Nonnull String color) {
/* 413 */     if (color.length() == 4) {
/* 414 */       colorLight.red = Byte.parseByte(color.substring(1, 2), 16);
/* 415 */       colorLight.green = Byte.parseByte(color.substring(2, 3), 16);
/* 416 */       colorLight.blue = Byte.parseByte(color.substring(3, 4), 16);
/*     */     } else {
/* 418 */       colorLight.red = (byte)(Integer.parseInt(color.substring(1, 3), 16) / 17);
/* 419 */       colorLight.green = (byte)(Integer.parseInt(color.substring(3, 5), 16) / 17);
/* 420 */       colorLight.blue = (byte)(Integer.parseInt(color.substring(5, 7), 16) / 17);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String colorLightToHexString(@Nonnull ColorLight colorLight) {
/* 426 */     return toHexString((byte)(colorLight.red * 17), (byte)(colorLight.green * 17), (byte)(colorLight.blue * 17));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String toHexString(byte red, byte green, byte blue) {
/* 431 */     return toHexString((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String toHexString(int rgb) {
/* 436 */     String hexString = Integer.toHexString(rgb);
/* 437 */     return "#" + "0".repeat(6 - hexString.length()) + hexString;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String toHexAlphaString(byte red, byte green, byte blue, byte alpha) {
/* 442 */     return toHexAlphaString((red & 0xFF) << 24 | (green & 0xFF) << 16 | (blue & 0xFF) << 8 | alpha & 0xFF);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static String toHexAlphaString(int rgba) {
/* 447 */     String hexString = Integer.toHexString(rgba);
/* 448 */     return "#" + "0".repeat(8 - hexString.length()) + hexString;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asse\\util\ColorParseUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */