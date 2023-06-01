// Run 'mvn install' fisrt and then 'mvn groovy:execute -Dsource=target/test-classes/testJava11SubModuleProject/IT.groovy -Dscope=test' from project root

// Given
println "Testing sub module generation..."

// When
def testPath = '${project.build.testOutputDirectory}/testJava11SubModuleProject/'
def parentFolder = "${testPath}/module-parent"
def moduleArtifactId = "my-rest-api"

def sout = new StringBuilder(), serr = new StringBuilder()
def proc = """mvn archetype:generate -B  \
    -DarchetypeGroupId=org.bonitasoft.archetypes \
    -DarchetypeArtifactId=bonita-rest-api-extension-archetype \
    -DarchetypeVersion=${project.version} \
    -DgroupId=org.company.api \
    -DartifactId=${moduleArtifactId} \
    -Dversion=0.0.1-SNAPSHOT \
    -Dlanguage=java \
    -DbonitaVersion=8.0.0 \
    -DapiName=myRestApi \
    -DapiDisplayName=My-REST-API \
    -DpathTemplate=my-rest-api \
    -DurlParameters=p,c \
    -DhttpVerb=GET
""".execute(null, new File(parentFolder))
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(10 * 60 * 1000)
println "out> $sout\nerr> $serr"

// Then

def modulePomFile = new File("${parentFolder}/${moduleArtifactId}/pom.xml")
def referencePomFile = new File("${testPath}/reference/pom.xml")

assert referencePomFile.text == modulePomFile.text



