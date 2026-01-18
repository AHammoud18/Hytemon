/*     */ package io.sentry;
/*     */ 
/*     */ import io.sentry.protocol.Feedback;
/*     */ import io.sentry.protocol.SentryId;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SentryFeedbackOptions
/*     */ {
/*     */   private boolean isNameRequired = false;
/*     */   private boolean showName = true;
/*     */   private boolean isEmailRequired = false;
/*     */   private boolean showEmail = true;
/*     */   private boolean useSentryUser = true;
/*     */   private boolean showBranding = true;
/*     */   @NotNull
/*  40 */   private CharSequence formTitle = "Report a Bug";
/*     */   
/*     */   @NotNull
/*  43 */   private CharSequence submitButtonLabel = "Send Bug Report";
/*     */   
/*     */   @NotNull
/*  46 */   private CharSequence cancelButtonLabel = "Cancel";
/*     */   
/*     */   @NotNull
/*  49 */   private CharSequence nameLabel = "Name";
/*     */   
/*     */   @NotNull
/*  52 */   private CharSequence namePlaceholder = "Your Name";
/*     */   
/*     */   @NotNull
/*  55 */   private CharSequence emailLabel = "Email";
/*     */   
/*     */   @NotNull
/*  58 */   private CharSequence emailPlaceholder = "your.email@example.org";
/*     */   
/*     */   @NotNull
/*  61 */   private CharSequence isRequiredLabel = " (Required)";
/*     */   
/*     */   @NotNull
/*  64 */   private CharSequence messageLabel = "Description";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*  70 */   private CharSequence messagePlaceholder = "What's the bug? What did you expect?";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*  76 */   private CharSequence successMessageText = "Thank you for your report!";
/*     */   
/*     */   @Nullable
/*     */   private Runnable onFormOpen;
/*     */   
/*     */   @Nullable
/*     */   private Runnable onFormClose;
/*     */   
/*     */   @Nullable
/*     */   private SentryFeedbackCallback onSubmitSuccess;
/*     */   
/*     */   @Nullable
/*     */   private SentryFeedbackCallback onSubmitError;
/*     */   
/*     */   @NotNull
/*     */   private IDialogHandler iDialogHandler;
/*     */   
/*     */   public SentryFeedbackOptions(@NotNull IDialogHandler iDialogHandler) {
/*  94 */     this.iDialogHandler = iDialogHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public SentryFeedbackOptions(@NotNull SentryFeedbackOptions other) {
/*  99 */     this.isNameRequired = other.isNameRequired;
/* 100 */     this.showName = other.showName;
/* 101 */     this.isEmailRequired = other.isEmailRequired;
/* 102 */     this.showEmail = other.showEmail;
/* 103 */     this.useSentryUser = other.useSentryUser;
/* 104 */     this.showBranding = other.showBranding;
/* 105 */     this.formTitle = other.formTitle;
/* 106 */     this.submitButtonLabel = other.submitButtonLabel;
/* 107 */     this.cancelButtonLabel = other.cancelButtonLabel;
/* 108 */     this.nameLabel = other.nameLabel;
/* 109 */     this.namePlaceholder = other.namePlaceholder;
/* 110 */     this.emailLabel = other.emailLabel;
/* 111 */     this.emailPlaceholder = other.emailPlaceholder;
/* 112 */     this.isRequiredLabel = other.isRequiredLabel;
/* 113 */     this.messageLabel = other.messageLabel;
/* 114 */     this.messagePlaceholder = other.messagePlaceholder;
/* 115 */     this.successMessageText = other.successMessageText;
/* 116 */     this.onFormOpen = other.onFormOpen;
/* 117 */     this.onFormClose = other.onFormClose;
/* 118 */     this.onSubmitSuccess = other.onSubmitSuccess;
/* 119 */     this.onSubmitError = other.onSubmitError;
/* 120 */     this.iDialogHandler = other.iDialogHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNameRequired() {
/* 129 */     return this.isNameRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNameRequired(boolean isNameRequired) {
/* 138 */     this.isNameRequired = isNameRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowName() {
/* 148 */     return this.showName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowName(boolean showName) {
/* 158 */     this.showName = showName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmailRequired() {
/* 167 */     return this.isEmailRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmailRequired(boolean isEmailRequired) {
/* 176 */     this.isEmailRequired = isEmailRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowEmail() {
/* 186 */     return this.showEmail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowEmail(boolean showEmail) {
/* 196 */     this.showEmail = showEmail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseSentryUser() {
/* 206 */     return this.useSentryUser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUseSentryUser(boolean useSentryUser) {
/* 216 */     this.useSentryUser = useSentryUser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShowBranding() {
/* 225 */     return this.showBranding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShowBranding(boolean showBranding) {
/* 234 */     this.showBranding = showBranding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getFormTitle() {
/* 243 */     return this.formTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormTitle(@NotNull CharSequence formTitle) {
/* 252 */     this.formTitle = formTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getSubmitButtonLabel() {
/* 261 */     return this.submitButtonLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubmitButtonLabel(@NotNull CharSequence submitButtonLabel) {
/* 270 */     this.submitButtonLabel = submitButtonLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getCancelButtonLabel() {
/* 279 */     return this.cancelButtonLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCancelButtonLabel(@NotNull CharSequence cancelButtonLabel) {
/* 288 */     this.cancelButtonLabel = cancelButtonLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getNameLabel() {
/* 297 */     return this.nameLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNameLabel(@NotNull CharSequence nameLabel) {
/* 306 */     this.nameLabel = nameLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getNamePlaceholder() {
/* 315 */     return this.namePlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamePlaceholder(@NotNull CharSequence namePlaceholder) {
/* 324 */     this.namePlaceholder = namePlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getEmailLabel() {
/* 333 */     return this.emailLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmailLabel(@NotNull CharSequence emailLabel) {
/* 342 */     this.emailLabel = emailLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getEmailPlaceholder() {
/* 351 */     return this.emailPlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmailPlaceholder(@NotNull CharSequence emailPlaceholder) {
/* 360 */     this.emailPlaceholder = emailPlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getIsRequiredLabel() {
/* 369 */     return this.isRequiredLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsRequiredLabel(@NotNull CharSequence isRequiredLabel) {
/* 378 */     this.isRequiredLabel = isRequiredLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getMessageLabel() {
/* 387 */     return this.messageLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessageLabel(@NotNull CharSequence messageLabel) {
/* 396 */     this.messageLabel = messageLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getMessagePlaceholder() {
/* 406 */     return this.messagePlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessagePlaceholder(@NotNull CharSequence messagePlaceholder) {
/* 415 */     this.messagePlaceholder = messagePlaceholder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CharSequence getSuccessMessageText() {
/* 425 */     return this.successMessageText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSuccessMessageText(@NotNull CharSequence successMessageText) {
/* 434 */     this.successMessageText = successMessageText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Runnable getOnFormOpen() {
/* 444 */     return this.onFormOpen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnFormOpen(@Nullable Runnable onFormOpen) {
/* 453 */     this.onFormOpen = onFormOpen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Runnable getOnFormClose() {
/* 462 */     return this.onFormClose;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnFormClose(@Nullable Runnable onFormClose) {
/* 471 */     this.onFormClose = onFormClose;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryFeedbackCallback getOnSubmitSuccess() {
/* 480 */     return this.onSubmitSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSubmitSuccess(@Nullable SentryFeedbackCallback onSubmitSuccess) {
/* 491 */     this.onSubmitSuccess = onSubmitSuccess;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SentryFeedbackCallback getOnSubmitError() {
/* 501 */     return this.onSubmitError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnSubmitError(@Nullable SentryFeedbackCallback onSubmitError) {
/* 512 */     this.onSubmitError = onSubmitError;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setDialogHandler(@NotNull IDialogHandler iDialogHandler) {
/* 522 */     this.iDialogHandler = iDialogHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @NotNull
/*     */   public IDialogHandler getDialogHandler() {
/* 532 */     return this.iDialogHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 537 */     return "SentryFeedbackOptions{isNameRequired=" + this.isNameRequired + ", showName=" + this.showName + ", isEmailRequired=" + this.isEmailRequired + ", showEmail=" + this.showEmail + ", useSentryUser=" + this.useSentryUser + ", showBranding=" + this.showBranding + ", formTitle='" + this.formTitle + '\'' + ", submitButtonLabel='" + this.submitButtonLabel + '\'' + ", cancelButtonLabel='" + this.cancelButtonLabel + '\'' + ", nameLabel='" + this.nameLabel + '\'' + ", namePlaceholder='" + this.namePlaceholder + '\'' + ", emailLabel='" + this.emailLabel + '\'' + ", emailPlaceholder='" + this.emailPlaceholder + '\'' + ", isRequiredLabel='" + this.isRequiredLabel + '\'' + ", messageLabel='" + this.messageLabel + '\'' + ", messagePlaceholder='" + this.messagePlaceholder + '\'' + '}';
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public static interface IDialogHandler {
/*     */     void showDialog(@Nullable SentryId param1SentryId, @Nullable SentryFeedbackOptions.OptionsConfigurator param1OptionsConfigurator);
/*     */   }
/*     */   
/*     */   public static interface SentryFeedbackCallback {
/*     */     void call(@NotNull Feedback param1Feedback);
/*     */   }
/*     */   
/*     */   public static interface OptionsConfigurator {
/*     */     void configure(@NotNull SentryFeedbackOptions param1SentryFeedbackOptions);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryFeedbackOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */