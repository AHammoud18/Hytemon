/*     */ package com.hypixel.hytale.server.core.entity.nameplate;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Nameplate
/*     */   implements Component<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<Nameplate> CODEC;
/*     */   
/*     */   static {
/*  32 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(Nameplate.class, Nameplate::new).append(new KeyedCodec("Text", (Codec)Codec.STRING), (nameplate, s) -> nameplate.text = s, nameplate -> nameplate.text).documentation("The contents to display as the nameplate text.").addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static ComponentType<EntityStore, Nameplate> getComponentType() {
/*  39 */     return EntityModule.get().getNameplateComponentType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  45 */   private String text = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNetworkOutdated = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Nameplate(@Nonnull String text) {
/*  67 */     this.text = text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getText() {
/*  75 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(@Nonnull String text) {
/*  84 */     if (this.text.equals(text))
/*  85 */       return;  this.text = text;
/*  86 */     this.isNetworkOutdated = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeNetworkOutdated() {
/*  95 */     boolean temp = this.isNetworkOutdated;
/*  96 */     this.isNetworkOutdated = false;
/*  97 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/* 103 */     Nameplate nameplate = new Nameplate();
/* 104 */     nameplate.text = this.text;
/* 105 */     return nameplate;
/*     */   }
/*     */   
/*     */   public Nameplate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\nameplate\Nameplate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */