/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 *
 * Contact Information:
 * BitCtrl Systems GmbH
 * Weißenfelser Straße 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */

package com.bitctrl.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.logging.Logger;

import javax.swing.UIDefaults;
import javax.swing.UIManager;

/**
 * Enthält Hilfsmethoden für GUI-Applikationen.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public final class UiUtilities {

	private static final Logger LOG = Logger.getLogger(UiUtilities.class.getName());

	/**
	 * Zentriert ein beliebiges Fenster auf dem Bildschirm.
	 * 
	 * @param window ein (J)Dialog oder (J)Frame
	 */
	public static void centerWindow(final Window window) {
		Dimension screen;
		Point topLeftCorner;

		screen = Toolkit.getDefaultToolkit().getScreenSize();
		topLeftCorner = new Point();
		topLeftCorner.x = (screen.width - window.getSize().width) / 2;
		topLeftCorner.y = (screen.height - window.getSize().height) / 2;
		window.setLocation(topLeftCorner);
	}

	/**
	 * Ändert die verwendete Standardschrift aller GUI-Komponenten. Die Methode wird
	 * am besten aufgerufen, bevor das erste Fenster der Applikation instanziiert
	 * wird. Andernfalls müssen bereits gezeichneten Komponenten manuell zum
	 * neuzeichen veranlasst werden.
	 * 
	 * @param font die neue Standardschrift der Applikation.
	 */
	public static void setDefaultFont(final Font font) {
		final UIDefaults uiDefaults = UIManager.getDefaults();
		uiDefaults.put("TabbedPane.font", font);
		uiDefaults.put("Button.font", font);
		uiDefaults.put("List.font", font);
		uiDefaults.put("PasswordField.font", font);
		uiDefaults.put("TableHeader.font", font);
		uiDefaults.put("Table.font", font);
		uiDefaults.put("ToggleButton.font", font);
		uiDefaults.put("Checkbox.font", font);
		uiDefaults.put("Menu.font", font);
		uiDefaults.put("Text.font", font);
		uiDefaults.put("TextField.font", font);
		uiDefaults.put("Label.font", font);
		uiDefaults.put("Panel.font", font);
		uiDefaults.put("PopupMenu.font", font);
		uiDefaults.put("ToolBar.font", font);
		uiDefaults.put("ColorChooser.font", font);
		uiDefaults.put("MenuBar.font", font);
		uiDefaults.put("ProgressBar.font", font);
		uiDefaults.put("TextArea.font", font);
		uiDefaults.put("JTextArea.font", font);
		uiDefaults.put("ToolTip.font", font);
		uiDefaults.put("ComboBox.font", font);
		uiDefaults.put("MenuItem.font", font);
		uiDefaults.put("RadioButton.font", font);
		uiDefaults.put("Tree.font", font);
		uiDefaults.put("EditorPane.font", font);
		uiDefaults.put("JOptionPane", font);
		uiDefaults.put("ScrollPane.font", font);
		uiDefaults.put("TextPane.font", font);
		uiDefaults.put("TitledBorder.font", font);
		uiDefaults.put("FormattedTextField.font", font);
		uiDefaults.put("Dialog.font", font);
		uiDefaults.put("Frame.font", font);
		uiDefaults.put("Window.font", font);
		uiDefaults.put("Component.font", font);
		uiDefaults.put("OptionPane.font", font);
	}

	/**
	 * Versucht das Look-and-Feel der Anwendung auf das Look-and-Feel der aktuellen
	 * Plattform zu setzen.
	 */
	public static void setSystemLookAndFeel() {
		if ("1.4".equals(System.getProperty("java.specification.version"))) {
			// Workaround, weil sonst die Stile Win2k und WinXP vermischt werden
			System.setProperty("swing.noxp", "true");
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			LOG.config("Vewende Look and Feel: " + UIManager.getLookAndFeel());
		} catch (final Exception ex) {
			LOG.warning("Look and Feel des Systems steht nicht zur Verfügung.");
		}
	}

	private UiUtilities() {
		// Konstruktor verstecken
	}

}
