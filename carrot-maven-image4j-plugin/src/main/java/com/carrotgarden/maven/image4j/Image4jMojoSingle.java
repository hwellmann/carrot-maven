package com.carrotgarden.maven.image4j;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICOEncoder;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal single
 * @phase generate-resources
 */
public class Image4jMojoSingle extends AbstractMojo {

	/**
	 * @parameter
	 * @required
	 */
	protected File source;

	/**
	 * @parameter
	 * @required
	 */
	protected File target;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			getLog().info("image4j : source = " + source);
			getLog().info("image4j : target = " + target);

			BufferedImage source = ImageIO.read(this.source);

			this.target.getParentFile().mkdirs();

			ICOEncoder.write(source, this.target);

		} catch (Throwable exception) {
			throw new MojoExecutionException("image4j: convert failed",
					exception);
		}

	}

	protected String getFileExtension(File file) {
		String name = file.getName();
		int pos = name.lastIndexOf('.');
		String extension = name.substring(pos + 1);
		return extension;
	}

}
