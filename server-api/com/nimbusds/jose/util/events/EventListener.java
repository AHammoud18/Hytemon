package com.nimbusds.jose.util.events;

public interface EventListener<S, C extends com.nimbusds.jose.proc.SecurityContext> {
  void notify(Event<S, C> paramEvent);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\events\EventListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */