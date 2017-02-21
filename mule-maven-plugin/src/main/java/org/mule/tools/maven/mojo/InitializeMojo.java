/*
 * Mule ESB Maven Tools
 * <p>
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * <p>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.maven.mojo;


import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.shared.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * It creates all the required folders in the project.build.directory
 */
@Mojo(name = "initialize",
    defaultPhase = LifecyclePhase.INITIALIZE,
    requiresDependencyResolution = ResolutionScope.RUNTIME)
public class InitializeMojo extends AbstractMuleMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().debug("Initializing Mule Maven Plugin...");

        String groupId = project.getGroupId();
        String artifactId = project.getArtifactId();
        String targetFolder = project.getBuild().getDirectory();

        createFolderIfNecessary(targetFolder);
        createFolderIfNecessary(targetFolder, MULE);

        createFolderIfNecessary(targetFolder, TEST_MULE);
        createFolderIfNecessary(targetFolder, TEST_MULE, MUNIT);

        createFolderIfNecessary(targetFolder, META_INF);

        createFolderIfNecessary(targetFolder, META_INF, MULE_SRC);
        createFolderIfNecessary(targetFolder, META_INF, MULE_SRC, artifactId);

        createFolderIfNecessary(targetFolder, META_INF, MAVEN);
        createFolderIfNecessary(targetFolder, META_INF, MAVEN, groupId);
        createFolderIfNecessary(targetFolder, META_INF, MAVEN, groupId, artifactId);

        createFolderIfNecessary(targetFolder, META_INF, MULE_ARTIFACT);

        createFolderIfNecessary(targetFolder, REPOSITORY);

        //        try {
        //            createFileIfNecessary(targetFolder, MULE_APPLICATION_JSON);
        //        } catch (IOException e) {
        //            throw new MojoFailureException("Cannot create mule-app.json file", e);
        //        }
        getLog().debug("Mule Maven Plugin Initialize done");
    }

    private void createFileIfNecessary(String... filePath) throws IOException {
        String path = StringUtils.join(filePath, File.separator);
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private void createFolderIfNecessary(String... folderPath) {
        String path = StringUtils.join(folderPath, File.separator);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }
}
