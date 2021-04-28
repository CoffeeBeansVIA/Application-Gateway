package com.group4.gateway.util;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject {
    void addPropertyChangeListener(String name, PropertyChangeListener listener);
}
