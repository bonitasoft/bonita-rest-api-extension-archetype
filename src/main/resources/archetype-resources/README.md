#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#set( $bonitaVersionParts = $bonitaVersion.split('\.') )
#set( $shortBonitaVersion = $bonitaVersionParts.get(0) +'.'+ $bonitaVersionParts.get(1) )

![Logo of the project](https://avatars2.githubusercontent.com/u/4619712?s=200&v=4)

$symbol_pound ${apiDisplayName}
> ${apiDesc}

The project **$apiName** is a Bonita REST API extension for **Bonita $bonitaVersion** version #if("sp" == true)**entreprise** #else **community**#end written in `$language`

TODO: A brief description of your project, what it is used for and how does life get
awesome when someone starts to use it.

$symbol_pound$symbol_pound Getting started

TODO: A quick introduction of the minimal setup you need to get a hello world up &
running.

For more details on Bonita REST API extension please refer to [documentation](https://documentation.bonitasoft.com/bonita/${shortBonitaVersion}/rest-api-extensions#)

$symbol_pound$symbol_pound Developing
TODO: Here's a brief intro about what a developer must do in order to start developing
the project further:

Prerequisite:

- a git client
- a java (jdk8 or higher)
- maven (optional if you choosed to use [maven wrapper script](https://github.com/takari/maven-wrapper) as archetype option)

$symbol_pound$symbol_pound$symbol_pound Building

TODO: If your project needs some additional steps for the developer to build the
project after some code changes, state them here:

```shell
git clone https://your.github.com/${apiName}.git
cd $apiName/
#if($wrapper=="true")
./mwnw package
#else
mvn package
#end 
```

The build should produce a zip archive under the `target/` folder named `${artifactId}-${version}.zip`

TODO: Here again you should state what actually happens when the code above gets
executed.

For more details about apache maven, please refer to the [documentation](https://maven.apache.org/guides/getting-started/)

$symbol_pound$symbol_pound$symbol_pound Deploying / Publishing

TODO: In case there's some step you have to take that publishes this project to a
server, this is the right time to state it.

Take the built artifact `${artifactId}-${version}.zip` and upload it to your Bonita plateform (see https://documentation.bonitasoft.com/bonita/${shortBonitaVersion}/rest-api-extensions#toc7)

$symbol_pound$symbol_pound Features

TODO: What's all the bells and whistles this project can perform?
* What's the main functionality
* You can also do another thing
* If you get really randy, you can even do this

$symbol_pound$symbol_pound Configuration

TODO: Here you should write what are all of the configurations a user can enter when
using the project.

$symbol_pound$symbol_pound$symbol_pound$symbol_pound page.properties

```properties
#The technical name of the REST API extension
#Must be URL compliant (alpha-numeric characters with no whitespace) and be prefixed by "custompage_"
name=custompage_${apiName}

#Name displayed in the Portal
displayName=${apiDisplayName}

#Description displayed in the Portal
description=${apiDesc}

#Must be apiExtension for a REST API extension
contentType=apiExtension

#Declare at least one API extension here (comma-separated list)
apiExtensions=${apiName}

#For each declared API extension,  specify the
#following properties: method,pathTemplate,classFileName and permissions

#Specify one HTTP verb from GET|POST|PUT|PATCH|DELETE|HEAD|OPTIONS|TRACE
#GET is the recommended value for a REST API extension.
#Write operations should be performed by a process.
${apiName}.method=${httpVerb}

#Define the URL path template
#Resulting URL: ../API/extension/${pathTemplate}
${apiName}.pathTemplate=${pathTemplate}

#Declare the associated RestAPIController class name
${apiName}.className=${groupId}.Index

#Declare the permissions list (comma-separated list)
#For each permission declared, you must map it either to a profile (for example User, Administrator, or a custom profile) or to a specific user.
#Edit the custom-permissions-mapping.properties configuration file.
#For example: user|john=[${permissionNames}] or profile|User=[${permissionNames}]
#In production, use the platform-setup tool.
#In the studio, go to menu Development > REST API Extension > Edit permissions mapping.
${apiName}.permissions=${permissionNames}
```

$symbol_pound$symbol_pound$symbol_pound$symbol_pound configuration.properties

```properties
#An example of a configuration file containing some parameters
myParameterKey=bonitasoft.com
```

$symbol_pound$symbol_pound Contributing

TODO: Make easy to your team to jump in and start contributing to your project.

These paragraphs are meant to welcome those kind souls to feel that they are
needed. You should state something like:

"If you'd like to contribute, please fork the repository and use a feature
branch. Pull requests are warmly welcome."

If there's anything else the developer needs to know (e.g. the code style
guide), you should link it here. If there's a lot of things to take into
consideration, it is common to separate this section to its own file called
`CONTRIBUTING.md` (or similar). If so, you should say that it exists here.

$symbol_pound$symbol_pound Links

TODO: Even though this information can be found inside the project on machine-readable
format like in a .json file, it's good to include a summary of most useful
links to humans using your project. You can include links like:

- Project homepage: https://your.github.com/awesome-project/
- Repository: https://github.com/your/awesome-project/
- Issue tracker: https://github.com/your/awesome-project/issues
  - In case of sensitive bugs like security vulnerabilities, please contact
    my@email.com directly instead of using issue tracker. We value your effort
    to improve the security and privacy of this project!
