[![Actions Status](https://github.com/bonitasoft/rest-api-extension-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/rest-api-extension-archetype/actions?query=workflow%3ABuild)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/rest-api-extension-archetype?color=blue&label=Release&include_prereleases)](https://github.com/bonitasoft/rest-api-extension-archetype/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.archetypes/rest-api-extension-archetype.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.archetypes%22%20AND%20a:%22rest-api-extension-archetype%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# rest-api-extension-archetype

## Disclaimer
* Use a JRE/JDK 1.8
* In BonitaBPM <= 7.1.x only the Index.groovy file can be loaded, please merge AbstractIndex.groovy into Index.groovy if needed. 

## How to build the archetype

```
git clone https://github.com/bonitasoft/rest-api-extension-archetype.git
cd rest-api-extension-archetype
./mvnw clean install
```

## How to use the archetype

```
mvn archetype:generate \
-DarchetypeGroupId=org.bonitasoft.archetypes \
-DarchetypeArtifactId=rest-api-extension-archetype \
-DgroupId=org.company.api \
-DartifactId=myRestApi \
-Dversion=0.0.1-SNAPSHOT
```

### Optionnal archetype parameters


| Parameter         | Default value                     | Description                                                                            										   |
| ------------------|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| -DbonitaVersion   | (none)                            | You can choose the version of the dependent bonita artifacts. Minimum version is 7.0.1.										   |
| -Dsp              | false                             | If set to true, project will use Bonita subscription dependencies.                    										   |
| -Dlanguage        | (none)                            | You can choose between groovy, java or kotlin.                                        										   |
| -Dwrapper         | true                              | If set to true, project will setup a [maven wrapper](https://github.com/takari/maven-wrapper)                                    |
| -DapiName         | (none)                            | Set the name of your api extension. You must enter an url friendly name without blanks. 									       |
| -DapiDisplayName  | (none)                            | A display name for your api extension (displayed in the portal for the administrator) 										   |
| -DapiDesc         | My Rest API extension description | A short description of the purpose of your api extension (displayed in the portal for the administrator) 						   |
| -DhttpVerb        | (none)                            | The http verb of your api extension 																							   |
| -DpathTemplate    | (none)                            | URL path template. Resulting url: ../API/extension/myRestExtApi 																   |
| -DpermissionNames | myRestAPIPermission               | Define permission list (comma separated value), specify permissions a user need to have in order access this REST API >extension |
| -DurlParameters   | ! (none)                          | Define a list (comma separated value) of url parameters.                                                                         |
| -DbdmGroupId      | ! (none)                          | Define a BDM groupId name to enable BDM dependencies                                                                             |
| -DbdmVersion      | ! (none)                          | Define a BDM version name to enable BDM dependencies                                                                             |
 
