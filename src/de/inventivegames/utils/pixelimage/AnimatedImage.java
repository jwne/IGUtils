package de.inventivegames.utils.pixelimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class AnimatedImage implements Cloneable {
	private PixelImage[]	images;
	private int				index	= 0;

	public AnimatedImage(PixelImage... images) {
		this.images = images;
	}

	public AnimatedImage(File gifFile, int height, char imgChar) throws IOException {
		List<BufferedImage> frames = getFrames(gifFile);
		images = new PixelImage[frames.size()];
		for (int i = 0; i < frames.size(); i++) {
			images[i] = new PixelImage(frames.get(i), height, imgChar);
		}
	}

	public List<BufferedImage> getFrames(File input) {
		List<BufferedImage> images = new ArrayList<BufferedImage>();
		try {
			ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
			ImageInputStream in = ImageIO.createImageInputStream(input);
			reader.setInput(in);
			for (int i = 0, count = reader.getNumImages(true); i < count; i++) {
				BufferedImage image = reader.read(i);
				images.add(image);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return images;
	}

	public PixelImage current() {
		return images[index];
	}

	public PixelImage next() {
		++index;
		if (index >= images.length) {
			index = 0;
			return images[index];
		} else
			return images[index];
	}

	public PixelImage previous() {
		--index;
		if (index <= 0) {
			index = images.length - 1;
			return images[index];
		} else
			return images[index];
	}

	public PixelImage getIndex(int index) {
		return images[index];
	}

	@Override
	public AnimatedImage clone() {
		try {
			return ((AnimatedImage) super.clone());
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
}
