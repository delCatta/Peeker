package com.sonder.peeker;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = PeekerApp.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface PeekerApp_GeneratedInjector {
  void injectPeekerApp(PeekerApp peekerApp);
}
