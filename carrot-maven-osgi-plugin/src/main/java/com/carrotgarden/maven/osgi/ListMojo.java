package com.carrotgarden.maven.osgi;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * 
 * @goal list
 * @aggregator false
 * 
 * @requiresProject true
 * @requiresDependencyResolution test
 * 
 */
public class ListMojo extends BaseMojo {

	/**
	 * The current Maven reactor.
	 * 
	 * @parameter expression="${reactorProjects}"
	 * @required
	 * @readonly
	 */
	private List<MavenProject> reactorProjects;

	@Override
	public void execute() throws MojoExecutionException {

		try {

			bundleIdList = new ArrayList<String>();

			// for (final MavenProject project : reactorProjects) {
			// getLog().info("project : " + project);
			// }

			addProjectBundles(m_project, TAB);

		} catch (Exception e) {

			throw new MojoExecutionException("bada-boom", e);

		}

	}

}
