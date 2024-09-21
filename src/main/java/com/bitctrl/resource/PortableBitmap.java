package com.bitctrl.resource;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class PortableBitmap {

	private int width;
	private int height;
	private final HashSet<Point> data = new HashSet<>();

	public PortableBitmap() {
		// tut nix
	}

	public PortableBitmap(final String fileContent) {
		init(fileContent);
	}

	protected void init(final String fileContent) {
		// TODO Auto-generated method stub
	}

	public String getFileContent() {
		// TODO
		return null;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		final int oldWidth = this.width;
		this.width = width;
		if (oldWidth > width) {
			final Collection<Point> toRemove = new ArrayList<>();
			for (final Point point : data.toArray(new Point[0])) {
				if (point.x > width) {
					toRemove.add(point);
				}
			}
			data.removeAll(toRemove);
		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		final int oldHeight = this.height;
		this.height = height;
		if (oldHeight > height) {
			final Collection<Point> toRemove = new ArrayList<>();
			for (final Point point : data.toArray(new Point[0])) {
				if (point.y > height) {
					toRemove.add(point);
				}
			}
			data.removeAll(toRemove);
		}
	}

	public int getPixel(final int x, final int y) {
		if (data.contains(new Point(x, y))) {
			return 1;
		}
		return 0;
	}

	public void setPixel(final int x, final int y, final int pixel) {
		if (pixel == 0) {
			data.remove(new Point(x, y));
		} else {
			data.add(new Point(x, y));
		}
	}

}
