package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.shortcuts.ActionShortcut;

import com.google.gwt.user.client.ui.KeyboardListener;

public class ActionShortcutTest {

    @Test
    public void altS() {
        ActionShortcut shortcut = new ActionShortcut(false, true, false, 'S', null);
        assertEquals(" (Alt+S)", shortcut.toString());
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Before
    public void before() {
    }

    @Test
    public void ctrl_coma() {
        ActionShortcut shortcut = new ActionShortcut(true, ',');
        assertEquals(" (Ctrl+,)", shortcut.toString());
        assertTrue(shortcut.is(',', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is(',', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is(',', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrl_s() {
        ActionShortcut shortcut = new ActionShortcut(true, 's');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('s', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('s', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlS() {
        ActionShortcut shortcut = new ActionShortcut(true, 'S');
        assertEquals(" (Ctrl+S)", shortcut.toString());
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_CTRL));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_SHIFT));
    }

    @Test
    public void ctrlShiftS() {
        ActionShortcut shortcut = new ActionShortcut(true, false, true, 'S', null);
        assertEquals(" (Ctrl+Shift+S)", shortcut.toString());
        assertTrue(!shortcut.is('S', KeyboardListener.MODIFIER_ALT));
        assertTrue(shortcut.is('S', KeyboardListener.MODIFIER_SHIFT | KeyboardListener.MODIFIER_CTRL));
    }
}