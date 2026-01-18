package com.nimbusds.jose.util.events;

public interface Event<S, C extends com.nimbusds.jose.proc.SecurityContext> {
  S getSource();
  
  C getContext();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\nimbusds\jos\\util\events\Event.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */