[![Actions Status](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/actions?query=workflow%3ABuild)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-rest-api-extension-archetype?color=blue&label=Release&include_prereleases)](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.archetypes/bonita-rest-api-extension-archetype.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.archetypes%22%20AND%20a:%22bonita-rest-api-extension-archetype%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# bonita-rest-api-extension-archetype

## Disclaimer
* Use a JRE/JDK 1.8 or Java 11 for Bonita 7.13+ 
* Compatible with Bonita 7.12+

## How to build the archetype

```
git clone https://github.com/bonitasoft/bonita-rest-api-extension-archetype.git
cd bonita-rest-api-extension-archetype
./mvnw clean install
```

## How to use the archetype

```
mvn archetype:generate \
    -DarchetypeGroupId=org.bonitasoft.archetypes \
    -DarchetypeArtifactId=bonita-rest-api-extension-archetype \
    -DgroupId=org.company.api \
    -DartifactId=my-rest-api \
    -Dversion=0.0.1-SNAPSHOT \
    -Dlanguage=java \
    -DbonitaVersion=7.12.0 \
    -DapiName=myRestApi \
    -DpathTemplate=my-rest-api \
    -DapiDisplayName="My REST API" \
    -DurlParameters=p,c \
    -DhttpVerb=GET
```

### Optionnal archetype parameters


| Parameter         | Required | Default value                     | Description                                                                            										   |
| ------------------|-------|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| -DbonitaVersion   | __true__  |                                   | You can choose the version of the dependent bonita artifacts. __Minimum version is 7.12.0.__   								   |
| -Dsp              | false | false                             | If set to true, project will use Bonita subscription dependencies. __This implies you have made bonita subscription artifacts available for maven (in your local repository or enterprise repository)__ |
| -Dlanguage        | __true__  |                                   | You can choose between `groovy`, `java` or `kotlin`.                                        										   |
| -Dwrapper         | false | true                              | If set to true, project will setup a [maven wrapper](https://github.com/takari/maven-wrapper)                                    |
| -DapiName         | __true__  |                                   | Set the name of your api extension. You must enter an url friendly name without blanks. 									       |
| -DapiDisplayName  | __true__  |                                   | A display name for your api extension (displayed in the portal for the administrator) 										   |
| -DapiDesc         | false | My Rest API extension description | A short description of the purpose of your api extension (displayed in the portal for the administrator) 						   |
| -DhttpVerb        | __true__  |                                   | The http verb of your api extension 																							   |
| -DpathTemplate    | __true__  |                                   | URL path template. Resulting url: ../API/extension/myRestExtApi 																   |
| -DpermissionNames | false | myRestAPIPermission               | Define permission list (comma separated value), specify permissions a user need to have in order access this REST API extension  |
| -DurlParameters   | false | !                                 | Define a list (comma separated value) of url parameters.                                                                         |
| -DbdmGroupId      | false | !                                 | Define a BDM groupId name to enable BDM dependencies                                                                             |
| -DbdmVersion      | false | !                                 | Define a BDM version name to enable BDM dependencies                                                                             |
 
