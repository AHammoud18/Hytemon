/*    */ package com.hypixel.hytale.server.core.entity.entities.player.pages.choices;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*    */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class ChoiceElement
/*    */ {
/* 20 */   public static final CodecMapCodec<ChoiceElement> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<ChoiceElement> BASE_CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   protected String displayNameKey;
/*    */ 
/*    */   
/*    */   protected String descriptionKey;
/*    */ 
/*    */   
/*    */   protected ChoiceInteraction[] interactions;
/*    */ 
/*    */   
/*    */   protected ChoiceRequirement[] requirements;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 42 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(ChoiceElement.class).append(new KeyedCodec("DisplayNameKey", (Codec)Codec.STRING), (choiceElement, s) -> choiceElement.displayNameKey = s, choiceElement -> choiceElement.displayNameKey).addValidator(Validators.nonEmptyString()).add()).append(new KeyedCodec("DescriptionKey", (Codec)Codec.STRING), (choiceElement, s) -> choiceElement.descriptionKey = s, choiceElement -> choiceElement.descriptionKey).addValidator(Validators.nonEmptyString()).add()).append(new KeyedCodec("Interactions", (Codec)new ArrayCodec((Codec)ChoiceInteraction.CODEC, x$0 -> new ChoiceInteraction[x$0])), (choiceElement, choiceInteractions) -> choiceElement.interactions = choiceInteractions, choiceElement -> choiceElement.interactions).addValidator(Validators.nonEmptyArray()).add()).append(new KeyedCodec("Requirements", (Codec)new ArrayCodec((Codec)ChoiceRequirement.CODEC, x$0 -> new ChoiceRequirement[x$0])), (choiceElement, choiceRequirements) -> choiceElement.requirements = choiceRequirements, choiceElement -> choiceElement.requirements).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChoiceElement(String displayNameKey, String descriptionKey, ChoiceInteraction[] interactions, ChoiceRequirement[] requirements) {
/* 50 */     this.displayNameKey = displayNameKey;
/* 51 */     this.descriptionKey = descriptionKey;
/* 52 */     this.interactions = interactions;
/* 53 */     this.requirements = requirements;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ChoiceElement() {}
/*    */   
/*    */   public String getDisplayNameKey() {
/* 60 */     return this.displayNameKey;
/*    */   }
/*    */   
/*    */   public String getDescriptionKey() {
/* 64 */     return this.descriptionKey;
/*    */   }
/*    */   
/*    */   public ChoiceInteraction[] getInteractions() {
/* 68 */     return this.interactions;
/*    */   }
/*    */   
/*    */   public ChoiceRequirement[] getRequirements() {
/* 72 */     return this.requirements;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canFulfillRequirements(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 78 */     if (this.requirements == null) return true;
/*    */     
/* 80 */     for (ChoiceRequirement requirement : this.requirements) {
/* 81 */       if (!requirement.canFulfillRequirement(store, ref, playerRef)) return false;
/*    */     
/*    */     } 
/* 84 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "ChoiceElement{displayNameKey='" + this.displayNameKey + "', descriptionKey='" + this.descriptionKey + "', interactions=" + 
/*    */ 
/*    */       
/* 93 */       Arrays.toString((Object[])this.interactions) + ", requirements=" + 
/* 94 */       Arrays.toString((Object[])this.requirements) + "}";
/*    */   }
/*    */   
/*    */   public abstract void addButton(UICommandBuilder paramUICommandBuilder, UIEventBuilder paramUIEventBuilder, String paramString, PlayerRef paramPlayerRef);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\choices\ChoiceElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */