# Upgrade Analyzer

This project aims to accelerate the upgrade compile phase duration by developing a strategic roadmap.
By analyzing the project's dependency graph, it will prioritize module optimization to maximize build
efficiency and developer productivity in the compile phase at least.

For now, the output looks like this:

```
// Game Plan //
This project contains 2 level(s) of project dependencies.
Level 1 count: 4
	liferay-profile-service-builder-api 6
	liferay-pdf-service-builder-api 5
	liferay-team-service-builder-api 4	
	liferay-visor-pdf-preview-web 0
Level 2 count: 9
	liferay-pdf-service-builder-web 0 (liferay-team-service-builder-api, liferay-profile-service-builder-api, liferay-pdf-service-builder-api)
	liferay-profile-service-builder-web 0 (liferay-profile-service-builder-api)
	liferay-visor-pdf-modal-web 0 (liferay-pdf-service-builder-api)
	liferay-pdf-service-builder-service 0 (liferay-team-service-builder-api, liferay-profile-service-builder-api, liferay-pdf-service-builder-api)
	liferay-visor-pdf-web 0 (liferay-profile-service-builder-api, liferay-pdf-service-builder-api)
	liferay-team-service-builder-service 0 (liferay-team-service-builder-api)
	liferay-team-service-builder-web 0 (liferay-team-service-builder-api)
	liferay-pdf-reciente-web 0 (liferay-profile-service-builder-api, liferay-pdf-service-builder-api)
	liferay-profile-service-builder-service 0 (liferay-profile-service-builder-api)
```

Each line into a level represents a project and follows this structure:
`${project-name} ${number-of-consumers} (${list-of-dependencies})`

Note: the developers must work on the lowest level first following
the projects from the top. 

1 - To build the project from source, run:

```
./gradlew clean build
```

To analyze an upgrade project using your local build run:

```
java -jar build/libs/upgrade-analyzer-*.jar /path/to/client/workspace
```
2 - To use the latest published version, run:

```
curl -o- https://raw.githubusercontent.com/liferay-upgrades/upgrade-analyzer/master/install.sh | sh
```
to download and install the latest version. It will include a bash function named "analyze_upgrade_project" and an alias called "aup" to execute the tool. You might run the tool without parameters to get the available options.
To test the installation, after reloading your `.bashrc`, run in your terminal: 
```
aup -f /path/to/client/workspace
```
**Requirements to build and run**

JDK 11+
