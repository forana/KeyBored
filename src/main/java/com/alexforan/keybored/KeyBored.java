package com.alexforan.keybored;

import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.alexforan.keybored.input.KeyAdapter;
import com.alexforan.keybored.ui.ControlWindow;
import com.alexforan.keybored.ui.OS;

public class KeyBored {
    @SuppressWarnings("serial")
    public static void main(String[] args) {
        try {
            KeyAdapter.getInstance().initialize();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Image iconImage = ImageIO.read(KeyBored.class.getResource("/app.iconset/icon_64x64.png"));
            OS.setDockIcon(iconImage);
            OS.enableSuddenTermination();
            
            final ControlWindow window = new ControlWindow(!SystemTray.isSupported());
            
            final TrayIcon icon = new TrayIcon(iconImage, "KeyBored");
            icon.setPopupMenu(new PopupMenu() {{
                add(new MenuItem("Settings...") {{
                    addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            window.show();
                        }
                    });
                }});
                add(new CheckboxMenuItem("Enabled", true) {{
                    this.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            window.setSuspended(!getState());
                        }
                    });
                }});
                add(new MenuItem("Quit KeyBored") {{
                    addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // let the window and the tray clean themselves up
                            window.dispose();
                            SystemTray.getSystemTray().remove(icon);
                            // OS X won't end the process without some special stuff, and this should be harmless at this point
                            System.exit(0);
                        }
                    });
                }});
            }});
            SystemTray.getSystemTray().add(icon);
            
            window.show();
            
            Thread.sleep(10000);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "KeyBored Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }
}
