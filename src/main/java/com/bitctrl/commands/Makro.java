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

package com.bitctrl.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Fasst mehrere Befehle zu einem zusammen. Die einzelnen Befehle werden streng
 * nacheinander ausgeführt. Makros können auch verschachtelt werden.
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public class Makro extends AbstractCommand {

	private final List<Command> script = new ArrayList<>();

	/**
	 * Fügt dem Makro einen auszuführenden Befehl hinzu.
	 * 
	 * @param cmd ein Befehl,
	 */
	public void add(final Command cmd) {
		script.add(cmd);
	}

	@Override
	public void run() {
		for (final Command cmd : script) {
			cmd.run();
		}
	}

}
