/**
 * Copyright (C) 2010-2012 Andrei Pozolotin <Andrei.Pozolotin@gmail.com>
 *
 * All rights reserved. Licensed under the OSI BSD License.
 *
 * http://www.opensource.org/licenses/bsd-license.php
 */
package com.carrotgarden.maven.aws.dns;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import com.amazonaws.services.route53.model.HostedZone;

/**
 * route53:
 * 
 * <b><a href=
 * "http://docs.amazonwebservices.com/Route53/latest/DeveloperGuide/ListInfoOnHostedZone.html"
 * >find dns zone name from dns host name</a></b>
 * 
 * @goal route53-find-zone
 * 
 * @phase prepare-package
 * 
 * @inheritByDefault true
 * 
 * @requiresDependencyResolution test
 * 
 */
public class CarrotAwsNameServFindZone extends CarrotAwsNameServ {

	/**
	 * dns host name which should be resolved into dns zone name
	 * 
	 * @required
	 * @parameter default-value="default.example.com"
	 */
	protected String dnsHostName;

	/**
	 * name of the maven project.property that will contain dns zone name after
	 * execution of this maven goal
	 * 
	 * @required
	 * @parameter default-value="dnsZoneName"
	 */
	protected String dnsResultProperty;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		try {

			getLog().info("dns zone init [" + dnsHostName + "]");

			final Route53 route53 = newRoute53();

			final HostedZone zone = route53.findZone( //
					route53.canonical(dnsHostName));

			if (zone == null) {
				throw new IllegalStateException("can not find zone for "
						+ dnsHostName);
			}

			final String dnsResultZoneName = zone.getName();

			getLog().info("dns zone name : " + dnsResultZoneName);

			project().getProperties().put(dnsResultProperty, dnsResultZoneName);

			getLog().info("dns zone done [" + dnsHostName + "]");

		} catch (final Exception e) {

			throw new MojoFailureException("bada-boom", e);

		}

	}

}
