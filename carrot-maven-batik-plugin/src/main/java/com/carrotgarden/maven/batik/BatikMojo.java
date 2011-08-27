package com.carrotgarden.maven.batik;

/*
 * The MIT License
 *
 * Copyright 2006-2008 The Codehaus.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.batik.apps.rasterizer.Main;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterSource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal rasterize
 * @phase generate-resources
 */
public class BatikMojo extends AbstractMojo {

	/**
	 * @parameter
	 * @required
	 */
	protected String arguments;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			String[] args = arguments.split("\\s+");

			getLog().info("batik: args = " + Arrays.toString(args));

			Main main = new Main(args) {

				@Override
				public boolean proceedOnSourceTranscodingFailure(
						SVGConverterSource source, File dest, String errorCode) {

					super.proceedOnSourceTranscodingFailure(source, dest,
							errorCode);

					String message = "batik: convert failed";
					getLog().error(message);
					throw new RuntimeException(message);

				}

				@Override
				public void validateConverterConfig(SVGConverter c) {

					@SuppressWarnings("unchecked")
					List<String> expandedSources = c.getSources();

					if ((expandedSources == null)
							|| (expandedSources.size() < 1)) {
						System.out.println(USAGE);
						System.out.flush();

						String message = "batik: invalid config";
						getLog().error(message);
						throw new RuntimeException(message);

					}

				}

			};

			main.execute();

		} catch (Throwable exception) {
			String help = "http://xmlgraphics.apache.org/batik/tools/rasterizer.html";
			String message = "batik: help @ " + help;
			getLog().error(message);
			throw new MojoExecutionException("batik: rasterize failed",
					exception);
		}

	}
}
