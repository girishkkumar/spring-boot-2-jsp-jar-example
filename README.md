# spring-boot-2-jsp-jar-example

This sample code explains how to render JSPs in executable Jar files. I have gone through n no.of forums, blogs and there were few which were close to what I was expecting and finally found 2 references that helped me in getting the expected result. 

I wanted to create a spring boot jar application using JSP as views. This was earlier possible only if we created Spring boot war applications but not jar.

If we were to put our JSP's under `webapps` or `src/main/resources/public` or `rc/main/resources/static` doesn't work.

From one of the reference it became apparent that if we put the JSP's in a folder like` /src/main/resources/META-INF/resources/WEB-INF/jsp` and the expected result it achieved.

I have created this application using Spring Boot 2.1.4.RELEASE with embedded tomcat by following both the references.

We need to add the below dependencies
```
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-jasper</artifactId>
  <scope>provided</scope>
</dependency>
  <dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>jstl</artifactId>
</dependency>
```
The main configurtion classes that play a major role in getting the jsp rendered in jar application.
### TomcatConfiguration.java
```
package com.gk.bootjsp.config;

import org.apache.catalina.Context;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "tomcat.staticResourceCustomizer.enabled", matchIfMissing = true)
@EnableConfigurationProperties(ServerProperties.class)
public class TomcatConfiguration {
	@Bean
	public ServletWebServerFactoryCustomizer staticResourceCustomizer() {

		ServerProperties serverProperties = null;
		return new ServletWebServerFactoryCustomizer(serverProperties) {
			@Override
			public void customize(ConfigurableServletWebServerFactory container) {
				System.out.println("container" + container.toString());
				if (container instanceof TomcatServletWebServerFactory) {
					System.out.println("TomcatServletWebServerFactory");
					((TomcatServletWebServerFactory) container).addContextCustomizers(new TomcatContextCustomizer() {
						@Override
						public void customize(Context context) {
							System.out.println("Customizing Tomcat");
							context.addLifecycleListener(new StaticResourceConfigurer(context));
						}
					});
				}
			}
		};
	}
}
```
### StaticResourceConfigurer.java
```
package com.gk.bootjsp.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.springframework.util.ResourceUtils;

public class StaticResourceConfigurer implements LifecycleListener {

	private final Context context;

	StaticResourceConfigurer(Context context) {
		this.context = context;
	}

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		if (event.getType().equals(Lifecycle.CONFIGURE_START_EVENT)) {
			URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();

			if (ResourceUtils.isFileURL(location)) {
				// when run as exploded directory
				System.out.println("Inside LifeCycleEvent");
				String rootFile = location.getFile();
				if (rootFile.endsWith("/BOOT-INF/classes/")) {
					rootFile = rootFile.substring(0, rootFile.length() - "/BOOT-INF/classes/".length() + 1);
				}
				if (!new File(rootFile, "META-INF" + File.separator + "resources").isDirectory()) {
					return;
				}

				try {
					location = new File(rootFile).toURI().toURL();
				} catch (MalformedURLException e) {
					throw new IllegalStateException("Can not add tomcat resources", e);
				}
			}

			String locationStr = location.toString();
			if (locationStr.endsWith("/BOOT-INF/classes!/")) {
				// when run as fat jar
				locationStr = locationStr.substring(0, locationStr.length() - "/BOOT-INF/classes!/".length() + 1);
				try {
					location = new URL(locationStr);
				} catch (MalformedURLException e) {
					throw new IllegalStateException("Can not add tomcat resources", e);
				}
			}
			this.context.getResources().createWebResourceSet(ResourceSetType.RESOURCE_JAR, "/", location,
					"/META-INF/resources");

		}
	}
}
```
### Project Directory Structure

```
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── gk
    │   │           └── bootjsp
    │   │               ├── BootJspApplication.java
    │   │               ├── config
    │   │               │   ├── StaticResourceConfigurer.java
    │   │               │   └── TomcatConfiguration.java
    │   │               ├── controller
    │   │               │   ├── EmployeeController.java
    │   │               │   └── LoginController.java
    │   │               ├── model
    │   │               │   └── Employee.java
    │   │               └── service
    │   │                   ├── EmployeeService.java
    │   │                   ├── impl
    │   │                   │   ├── EmployeeServiceImpl.java
    │   │                   │   └── LoginServiceImpl.java
    │   │                   └── LoginService.java
    │   └── resources
    │       ├── application.properties
    │       ├── messages.properties
    │       ├── META-INF
    │       │   └── resources
    │       │       └── views
    │       │           └── pages
    │       │               ├── emp-list.jsp
    │       │               ├── login.jsp
    │       │               └── welcome.jsp
    │       ├── static
    │       └── templates
    └── test
        └── java
            └── com
                └── gk
                    └── bootjsp
                        └── BootJspApplicationTests.java
```

### Run the application as below:
```
$ mvn clean package

$ java -jar target/bootjsp-0.0.1-SNAPSHOT.jar
```

Once the application starts open the below url in the browser
`http://localhost:8080/login`

###### Enter the credentials as below 
```
username: administrator
password: admin
```

:+1: :+1: :+1: :metal: :metal: :metal:

### References
https://dzone.com/articles/spring-boot-with-jsps-in-executable-jars-1

https://github.com/hengyunabc/spring-boot-fat-jar-jsp-sample
