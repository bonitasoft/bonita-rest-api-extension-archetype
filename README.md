#rest-api-extension-archetype

##Disclaimer
* Use a JRE/JDK 1.7
* In BonitaBPM <= 7.1.x only the Index.groovy file can be loaded, please merge AbstractIndex.groovy into Index.groovy if needed. 

##How to build the archetype

```
git clone https://github.com/bonitasoft/rest-api-extension-archetype.git
cd rest-api-extension-archetype
mvn clean install -DskipTests
```

##How to use the archetype

```
mvn archetype:generate \
-DarchetypeGroupId=org.bonitasoft.web \ 
-DarchetypeArtifactId=rest-api-extension-archetype \ 
-DarchetypeVersion=1.0.0-SNAPSHOT \
-DgroupId=org.company.api \
-DartifactId=myRestApi \
-Dversion=0.0.1-SNAPSHOT
```

###Optionnal archetype parameters


| Parameter         | Default value                     | Description                                                                            										   |
| ------------------|-----------------------------------|----------------------------------------------------------------------------------------------------------------------------------|
| -DbonitaVersion   | 7.0.1                             | You can choose the version of the dependent bonita artifacts. Minimum version is 7.0.1.										   |
| -DapiName         | myRestExtApi                      | Set the name of your api extension. You must enter an url friendly name without blanks. 									       |
| -DapiDisplayName  | My Rest API extension             | A display name for your api extension (displayed in the portal for the administrator) 										   |
| -DapiDesc         | My Rest API extension description | A short description of the purpose of your api extension (displayed in the portal for the administrator) 						   |
| -DhttpVerb        | GET                               | The http verb of your api extension 																							   |
| -DpathTemplate    | myRestExtApi                      | URL path template. Resulting url: ../API/extension/myRestExtApi 																   |
| -DpermissionNames | myRestAPIPermission               | Define permission list (comma separated value), specify permissions a user need to have in order access this REST API >extension |
| -DurlParameters   | ! (none)                          | Define a list (comma separated value) of url parameters.                                                                         |
| -DbdmPackage      | ! (none)                          | Define a bdm package name to enable BDM dependencies                                                                             |
 
