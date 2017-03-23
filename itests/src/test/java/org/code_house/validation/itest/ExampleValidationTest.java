package org.code_house.validation.itest;

import org.apache.karaf.features.FeaturesService;
import org.code_house.validation.example.api.MessageValidator;
import org.code_house.validation.example.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.MavenUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import javax.inject.Inject;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFileExtend;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class ExampleValidationTest {

    @Inject
    private BundleContext bundleContext;

    @Inject
    private FeaturesService featuresService;

    @Configuration
    public Option[] config() {
        String featuresUrl = maven("org.code-house.validation", "features").classifier("features").type("xml").versionAsInProject().getURL();
        String karafVersion = MavenUtils.getArtifactVersion("org.apache.karaf", "apache-karaf");
        MavenArtifactUrlReference frameworkURL = maven("org.apache.karaf", "apache-karaf").type("zip").version(karafVersion);

        return new Option[] {
            karafDistributionConfiguration().karafVersion(karafVersion).frameworkUrl(frameworkURL).unpackDirectory(new File("target/pax")),
            keepRuntimeFolder(),
            //debugConfiguration("5010", true),
            mavenBundle("org.code-house.validation.example", "model").versionAsInProject(),
            mavenBundle("org.code-house.validation.example", "api").versionAsInProject(),
            mavenBundle("org.code-house.validation.example", "core").versionAsInProject(),
            editConfigurationFileExtend("etc/org.apache.karaf.features.cfg", "featuresRepositories", "," + featuresUrl),
            editConfigurationFileExtend("etc/org.apache.karaf.features.cfg", "featuresBoot", ",validation-resolver")
        };
    }

    @Test
    public void testValidationWithBval() throws Exception {
        ServiceTracker<MessageValidator, MessageValidator> serviceTracker = new ServiceTracker<>(bundleContext, MessageValidator.class, null);
        serviceTracker.open();

        assertEquals(0, serviceTracker.getTrackingCount());

        featuresService.installFeature("bval-validation-provider");

        assertEquals(1, serviceTracker.getTrackingCount());

        MessageValidator service = serviceTracker.getService();
        System.out.println("Validation results " + service.validate(new Message(0, "", null)));

    }

}
