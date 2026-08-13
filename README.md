[![Actions Status](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/workflows/Build/badge.svg)](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/actions?query=workflow%3ABuild)
[![GitHub release](https://img.shields.io/github/v/release/bonitasoft/bonita-rest-api-extension-archetype?color=blue&label=Release&include_prereleases)](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/releases)
[![Maven Central](https://img.shields.io/maven-central/v/org.bonitasoft.archetypes/bonita-rest-api-extension-archetype.svg?label=Maven%20Central&color=orange)](https://search.maven.org/search?q=g:%22org.bonitasoft.archetypes%22%20AND%20a:%22bonita-rest-api-extension-archetype%22)
[![License: GPL v2](https://img.shields.io/badge/License-GPL%20v2-yellow.svg)](https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html)

# bonita-rest-api-extension-archetype

## Disclaimer
* Compatible with Bonita 12.0+ (Jakarta EE). Requires a JDK 17 or higher.
* For Bonita 7.12 to 11.x, use the archetype **1.7.x** (maintained on the [`support/1.7.x`](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/tree/support/1.7.x) branch)

## How to build the archetype

```
git clone https://github.com/bonitasoft/bonita-rest-api-extension-archetype.git
cd bonita-rest-api-extension-archetype
./mvnw clean install
```

ℹ️ Until Bonita 12.0 is released on Maven Central, the integration tests build the generated projects against the `12.0-SNAPSHOT` Bonita runtime: this requires access to the Bonitasoft Artifactory snapshot repositories in your Maven `settings.xml`. Use `./mvnw clean install -DskipTests` otherwise.

## How to use the archetype

```
mvn archetype:generate \
    -DarchetypeGroupId=org.bonitasoft.archetypes \
    -DarchetypeArtifactId=bonita-rest-api-extension-archetype \
    -DgroupId=org.company.api \
    -DartifactId=my-rest-api \
    -Dversion=0.0.1-SNAPSHOT \
    -Dlanguage=java \
    -DbonitaVersion=12.0.0 \
    -DapiName=myRestApi \
    -DpathTemplate=my-rest-api \
    -DapiDisplayName="My REST API" \
    -DurlParameters=p,c \
    -DhttpVerb=GET
```

### Optionnal archetype parameters


| Parameter         | Required | Default value                     | Description                                                                            										   |
| ------------------|-------|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| -DbonitaVersion   | __true__  |                                   | You can choose the version of the dependent bonita artifacts. __Minimum version is 12.0.0__ (use archetype 1.7.x for older versions).   								   |
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

## Release this project

The GitHub Action [Release](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/actions/workflows/release.yml) is used to perform a release:

- This action is triggered manually, from the Actions tab
- It sets the release version, tags it, publishes the archetype to the Maven Central Portal, bumps to the next development version, pushes the branch and the tag, then creates the GitHub release with generated notes

So, to release a new version of the project, you have to:
- Open the [Release workflow](https://github.com/bonitasoft/bonita-rest-api-extension-archetype/actions/workflows/release.yml) and click *Run workflow*
- Fill in the version to release (e.g. `2.0.0`) and the next development version (e.g. `2.0.1-SNAPSHOT`)
- Leave the `branch` input to `master`, unless you want to release from another branch

⚠️ The release is performed on the branch given by the `branch` input, not on the branch selected in the *Run workflow* dropdown (which only selects the version of the workflow file to run): that branch is the one checked out and built, tagged with the released version, and updated with the next development version.

⚠️ The deployment is not published automatically (`autoPublish` is set to `false` in the `pom.xml`): once the workflow succeeds, the deployment must be reviewed and published from the [Maven Central Portal](https://central.sonatype.com/publishing/deployments).

⚠️ Nothing is pushed until the deployment succeeded: the release commit, the next development version commit and the tag are all pushed in one go, near the end of the workflow. A run that fails before that step leaves the branch and the tags untouched, but the deployment may already exist in the Maven Central Portal.

⚠️ Make sure the version is final before running the workflow. If you have to fix something afterwards, then you must first:
- Delete the tag and the release on GitHub
- Revert the release and next development version commits on the branch
- Drop the deployment from our Maven Central Portal repository
